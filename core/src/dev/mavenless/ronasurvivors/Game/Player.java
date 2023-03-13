package mavenless.ronasurvivors.Game;



import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;

import mavenless.ronasurvivors.Screens.GameScreen;
import mavenless.ronasurvivors.Utils.InputUtil;
import mavenless.ronasurvivors.Utils.LevelUtil;

public class Player {
    private LevelUtil levelUtil;
    private TextureAtlas atlas;
    public enum State {STANDING, RUNNINGHORIZONTAL, STANDINGLEFT, STANDINGRIGHT, RUNNINGUP, RUNNINGDOWN};
    private State currentState;
    private State previousState;
    private Rectangle size;
    private float speed;
    private Body body;
    private Animation<TextureRegion> runHorizontal;
    private Animation<TextureRegion> runUp;
    private Animation<TextureRegion> runDown;
    private TextureRegion standing;
    private TextureRegion standingLeft;
    private Boolean isRunningLeft;
    private float stateTimer;
    private InputUtil input;
    
    public Player(GameScreen screen, Rectangle size, float speed, LevelUtil levelUtil, InputUtil input) {
        this.size = size;
        this.input = input;
        this.speed = speed;
        this.levelUtil = levelUtil;
        atlas = screen.getAtlas();
        runHorizontal = new Animation<TextureRegion>(5,atlas.findRegions("doctor_white_walk-left"));
        runUp = new Animation<TextureRegion>(5, atlas.findRegions("doctor_white_walk-up"));
        runDown = new Animation<TextureRegion>(5, atlas.findRegions("doctor_white_walk-down"));
        standingLeft = new TextureRegion(atlas.findRegion("doctor_white_idle-left"));
        standing = new TextureRegion(atlas.findRegion("doctor_white_idle-down"));
        currentState = State.STANDING;
        previousState = State.STANDING;
        isRunningLeft = true;
        stateTimer = 0;
    
        definePlayer();
       
    }

    // Define the players body, box and fixture
    private void definePlayer(){
         // Set up Box2D
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyType.DynamicBody;
        bodyDef.position.set(size.x, size.y);
        body = levelUtil.world.createBody(bodyDef);

        // Sets the hitbox around our player
        PolygonShape box = new PolygonShape();
        box.setAsBox(
            (size.width / 2)-3, // width of the box
            (size.height / 2)-3, // height of the box
            new Vector2(
                (size.width / 2), // center of the box in local coordinates (width)
                (size.height / 2)-7 // center of the box in local coordinates (Height)
            ),
            0f // Rotation (Example if we want to add dodge ability)
        );

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = box;
        fixtureDef.density = 0.5f;
        fixtureDef.friction = 0.4f;
        fixtureDef.restitution = 0.0f; // Make it bounce a little bit

        body.createFixture(fixtureDef);
       
        box.dispose();

        // Body settings
        body.setFixedRotation(true);
    }

    public void move(Camera camera, float worldWidth, float worldHeight) {
        // Move size box
        float movementX = input.moveX();
        body.setLinearVelocity(
            movementX * speed,
            input.moveY() * speed
        );
        
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

    
    public void render(SpriteBatch batch) {
        batch.draw(getFrame(), size.x-5, size.y-5, size.width+10, size.height+10);
        //System.out.println(this.stateTimer);
    }



    // Getters and Setters
    public Rectangle getSize() {
        return this.size;
    }

    public float getSpeed() {
        return this.speed;
    }

    public Vector2 getPosition(){
        return body.getPosition();
    }

    // Setters
    public void setSpeed(float speed) {
        this.speed = speed;
    }

    // Dispose
    public void dispose() {

    }

}
