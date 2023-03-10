package dev.mavenless.ronasurvivors.Game;

import java.nio.charset.Charset;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
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

import dev.mavenless.ronasurvivors.Screens.GameScreen;
import dev.mavenless.ronasurvivors.Utils.InputUtil;
import dev.mavenless.ronasurvivors.Utils.LevelUtil;

public class Player {

    private LevelUtil levelUtil;
    private TextureAtlas atlas;
    public enum State {STANDING, RUNNING, STANDINGLEFT, STANDINGRIGHT};
    private State currentState;
    private State previousState;
    private Rectangle size;
    private Sprite sprite;
    private float speed;
    private Body body;
    private Charset charset;
    private Animation<TextureRegion> run;
    private TextureRegion standing;
    private TextureRegion standingLeft;
    private Boolean isRunningLeft;
    private float stateTimer;
    private InputUtil input;
    
    public Player(GameScreen screen, Rectangle size, float speed, LevelUtil levelUtil, InputUtil input) {
        this.size = size;
        this.atlas = screen.getAtlas();
        this.run = new Animation<TextureRegion>(5,atlas.findRegions("doctor_white_walk-left"));
        this.standing = new TextureRegion(atlas.findRegion("doctor_white_idle-down"));
        this.standingLeft = new TextureRegion(atlas.findRegion("doctor_white_idle-left"));
        this.speed = speed;
        this.levelUtil = levelUtil;
        //this.sprite = new Sprite(standing);
        currentState = State.STANDING;
        previousState = State.STANDING;
        this.isRunningLeft = true;
        stateTimer = 0;
        this.input = input;
        

        // Set up Box2D
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyType.DynamicBody;
        bodyDef.position.set(size.x, size.y);

        body = levelUtil.world.createBody(bodyDef);

        PolygonShape box = new PolygonShape();
        box.setAsBox(
            (size.width / 2)-3,
            (size.height / 2)-3, 
            new Vector2(
                (size.width / 2),
                (size.height / 2)-7
            ),
            0f
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

    private TextureRegion getFrame(){
        TextureRegion region;
        currentState = getState();
        switch(currentState){
            case STANDING:
                region = standing;
                break;
            case RUNNING:
                region = run.getKeyFrame(stateTimer, true);
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
    

    // Is player moving or standing still?
    private State getState(){
        if(input.moveX() != 0) return State.RUNNING;
        else return State.STANDING;
    }

    public void render(SpriteBatch batch) {
        batch.draw(getFrame(), size.x-5, size.y-5, size.width+10, size.height+10);
        System.out.println(this.stateTimer);
    }

    //private TextureRegion getCurrentFrame(float delta){
    //    
    //}


    // Getters
    public Rectangle getSize() {
        return this.size;
    }

    public Sprite getSprite() {
        return this.sprite;
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
