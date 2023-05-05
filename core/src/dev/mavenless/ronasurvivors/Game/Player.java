package mavenless.ronasurvivors.Game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;

import mavenless.ronasurvivors.Screens.GameScreen;
import mavenless.ronasurvivors.Utils.InputUtil;
import mavenless.ronasurvivors.Utils.LevelUtil;

/**
 * Class for displaying and handling the player
 */
public class Player {
    private enum State {STANDING, RUNNINGHORIZONTAL, STANDINGLEFT, STANDINGRIGHT, RUNNINGUP, RUNNINGDOWN};
    private LevelUtil levelUtil;
    private TextureAtlas atlas;
    private State currentState, previousState;
    private Rectangle size;
    private float speed, stateTimer;
    private Body body;
    private Fixture playerFix;
    private Animation<TextureRegion> runHorizontal, runUp, runDown;
    private TextureRegion standing;
    private Boolean isRunningLeft;
    private Boolean isAudioPlaying;
    private InputUtil input;
    private Sound sound;
    private Vector2 lastMovementDirection;
    private int killcount; 
    private int killsForNextLevel;
    private float shootInterval;
    private GameScreen gameScreen;
    
    /**
     * Constructor for constructing a new player object
     * @param screen - Screen to display player on
     * @param size - Size of the player
     * @param speed - Predefined speed of player
     * @param levelUtil - levelutil for defining body in world obj
     * @param input - inpututil for handling user input
     */
    public Player(GameScreen gameScreen, Rectangle size, float speed, LevelUtil levelUtil, InputUtil input) {
        this.gameScreen = gameScreen;
        this.killcount = 0;
        this.killsForNextLevel = 10;
        this.shootInterval = 1.0f;
        this.size = size;
        this.input = input;
        this.speed = speed;
        this.levelUtil = levelUtil;
        this.sound = Gdx.audio.newSound(Gdx.files.internal("soundEffects/running-in-grass-6237.ogg"));
        this.sound.play();
        sound.stop();
        atlas = gameScreen.getAtlas();
        runHorizontal = new Animation<TextureRegion>(5,atlas.findRegions("doctor_white_walk-left"));
        runUp = new Animation<TextureRegion>(5, atlas.findRegions("doctor_white_walk-up"));
        runDown = new Animation<TextureRegion>(5, atlas.findRegions("doctor_white_walk-down"));
        standing = new TextureRegion(atlas.findRegion("doctor_white_idle-down"));
        currentState = State.STANDING;
        previousState = State.STANDING;
        isRunningLeft = true;
        stateTimer = 0;
        lastMovementDirection = new Vector2(0,0);
        definePlayer();
    }


    /* Helper method for defining the body, box and fixture of the player */
    private void definePlayer(){
        // Set up Box2D
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyType.DynamicBody;
        bodyDef.position.set(size.x, size.y);
        body = levelUtil.world.createBody(bodyDef);

        // Sets the hitbox around our player
        PolygonShape box = new PolygonShape();
        box.setAsBox(
            (size.width / 2)-3,     // width of the box
            (size.height / 2)-3,    // height of the box
            new Vector2(
                (size.width / 2),   // center of the box in local coordinates (width)
                (size.height / 2)-7 // center of the box in local coordinates (Height)
            ),
            0f                // Rotation (Example if we want to add dodge ability)
        );

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = box;
        fixtureDef.density = 0.5f;
        fixtureDef.friction = 0;
        fixtureDef.restitution = 0.0f; 
        fixtureDef.filter.categoryBits = CollisionBits.CATEGORY_PLAYER;  
        fixtureDef.filter.maskBits = CollisionBits.MASK_PLAYER; 
        
        playerFix = body.createFixture(fixtureDef);
        playerFix.setUserData("Player");
        box.dispose();

        // Body settings
        body.setFixedRotation(true);
    }

    /**
     * Moves the player, and makes the 
     * camera follow the player. 
     * 
     * @param camera the camera 
     * @param worldWidth the world width
     * @param worldHeight the world height
     */
    public void move(Camera camera, float worldWidth, float worldHeight) {
        // Move size box
        float movementX = input.moveX();
        body.setLinearVelocity(
            movementX * speed,
            input.moveY() * speed
        );

        Vector2 direction = new Vector2(input.moveY(), input.moveX());
        if (direction.len()>0){
            lastMovementDirection = direction;
            
        }
        Vector2 bodyPos = body.getPosition();
        size.x = bodyPos.x;
        size.y = bodyPos.y;
        

        // Follow camera
        float halfWorldWidth = worldWidth / 2;
        float halfWorldHeight = worldHeight / 2;
        camera.position.set(
            Math.min(
                Math.max(
                    size.x,
                    halfWorldWidth
                ),
                (levelUtil.getMapWidth() * levelUtil.getTileWidth()) - halfWorldWidth
            ),
            Math.min(
                Math.max(
                    size.y,
                    halfWorldHeight
                ),
                (levelUtil.getMapHeight() * levelUtil.getTileHeight()) - halfWorldHeight
            ),
            1
        );
        camera.update();
    }
    
    
    /** 
     * Returns a TextureRegion that contains the next frame of the player to be shown on the screen.
     * If the player is currently standing we want to display our player as standing, as goes for the other states our player could be in.
     * This method also checks which way the player is RUNNINGHORIZONTAL - thus it will flip the animation depending on the movement of the player.
     * {@code stateTimer} is incremented by 1 for every frame as long as the {@code previousState} is the {@code currentState}. 
     * If the player State changes - the timer resets - which will starts the next animation (the new state) on frame index 0.
     * @return TextureRegion
     */
    private TextureRegion getFrame(){
        TextureRegion region;
        currentState = getState();
        getAudio(currentState);
        switch(currentState){
            case STANDING:
                region = standing;
                break;
            case RUNNINGHORIZONTAL:
                region = runHorizontal.getKeyFrame(stateTimer, true);
                break;
            case RUNNINGUP:
                region = runUp.getKeyFrame(stateTimer, true);
                break;
            case RUNNINGDOWN:
                region = runDown.getKeyFrame(stateTimer, true);
                break;
            default:
                region = standing;
        }

        if((input.moveX() < 0 || isRunningLeft) && region.isFlipX()){
            region.flip(true, false);
            isRunningLeft = true;
        }
        else if((input.moveX() > 0 || !isRunningLeft) && !region.isFlipX()){
            region.flip(true,false);
            isRunningLeft = false;
        }

        stateTimer = currentState == previousState ? stateTimer + 1 : 0;
        
        previousState = currentState;
        return region;
    }

    private void getAudio(State state){
        if (state == State.STANDING || gameScreen.getHp_bar().getHealth() <= 0){
            sound.stop();
            isAudioPlaying = false;
        } 
        else { 
            if (!isAudioPlaying){
                isAudioPlaying = true;
                sound.loop();
            }
            
        }
    }
    

    /**
     * Based on how the player is moving in X and/or Y- direction we define a State
     * of the player which relates to the animation we want to be displayed.
     * @return State 
     */
    private State getState(){
        if(input.moveX() != 0) return State.RUNNINGHORIZONTAL;
        if (input.moveY() > 0) return State.RUNNINGUP;
        if(input.moveY() < 0) return State.RUNNINGDOWN;
        else return State.STANDING;
    }
    
    /**
     * Renders the player 
     * @param batch
     */
    public void render(SpriteBatch batch) {
        batch.draw(getFrame(), size.x-5, size.y-5, size.width+10, size.height+10);
    }

    /* -- GETTER and SETTER functions -- */
    /**
     * Get size of the body
     * @return rectangle size 
     */
    public Rectangle getSize() {
        return this.size;
    }

    /**
     * Get predefined player speed
     * @return player speed
     */
    public float getSpeed() {
        return this.speed;
    }

    /**
     * Get position of the player
     * @return vector containing position
     */
    public Vector2 getPosition(){
        return body.getPosition();
    }

    /**
     * Get the last direction the player moved in
     * @return vector containing the last direction
     */
    public Vector2 getLastMovementDirection(){
        return lastMovementDirection;
    }

    public Boolean checkPlayerUpgrade(){
        if(killcount == killsForNextLevel){
            increaseKillsForNextLevel();
            decreaseShootInterval();
            return true;
        }
        return false;
    }


    /**
     * Updating the speed of the player
     * @param speed - updated speed
     */
    public void setSpeed(float speed) {
        this.speed = speed;
    }

    // Dispose
    public void dispose() {
        sound.dispose();
    }
    public void increaseKillcount(){
        this.killcount += 1;
    }
    public int getKillcount(){
        return this.killcount;
    }

    public void decreaseShootInterval(){
        this.shootInterval *= 0.9f;
    }

    public float getShootInterval(){
        return this.shootInterval;
    }

    public void increaseKillsForNextLevel(){
        this.killsForNextLevel *= 2;
    }

    public int getKillsForNextLevel(){
        return this.killsForNextLevel;
    }

    public void setAudioPlaying(boolean isAudioPlaying){
        this.isAudioPlaying = isAudioPlaying;
    }
}
