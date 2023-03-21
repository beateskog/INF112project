package mavenless.ronasurvivors.Game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ai.fsm.State;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;


import mavenless.ronasurvivors.Utils.LevelUtil;


public class Projectile {

    //public enum State {STANDING, RUNNINGHORIZONTAL, STANDINGLEFT, STANDINGRIGHT, RUNNINGUP, RUNNINGDOWN};
    private Sprite sprite;
    private Rectangle size;
    private Body body;
    private float speed;
    private Fixture projectileFix;
    private LevelUtil levelUtil;
    private Texture projectile;
    private Vector2 direction = new Vector2(0,1);

    public Projectile(Rectangle size,  float speed, LevelUtil levelUtil, Vector2 posistion) {
        this.size = size;
        this.speed = speed;
        this.levelUtil = levelUtil;
        projectile = new Texture(Gdx.files.internal("sprites/Projectile/projectile.png"));

        defineProjectile();
    }

    private void defineProjectile() {
        // Defining the body of the projectile:
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyType.DynamicBody;
        bodyDef.position.set(size.x, size.y);
        body = levelUtil.world.createBody(bodyDef);

        // Defining the box of the projectiler body:  
        PolygonShape box = new PolygonShape();
        box.setAsBox(
            (size.width / 6),
            (size.height / 6), 
            new Vector2(
                (size.width / 6),
                (size.height / 6)
            ),
            0f
        );

        // Create a circle shape and set its radius to 6
        CircleShape circle = new CircleShape();
        circle.setRadius(6f);

        // Defining the fixture of our box (not colliding with objs)
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = box;                     //Setting the shape of the fixture to our box
        fixtureDef.density = 0.5f;
        fixtureDef.friction = 0.4f;
        fixtureDef.restitution = 0.0f;

        projectileFix = getBody().createFixture(fixtureDef);
        projectileFix.setUserData("Projectile");
        box.dispose();

        getBody().setFixedRotation(true);
    }


    public void shoot(Vector2 playerPosition, Player.State currentPosistion, float timeSinceLastShot, Boolean runLeft) {
        // Set the direction vector based on the player state
        if (timeSinceLastShot == 0){
            if (currentPosistion == Player.State.RUNNINGUP) {
                direction = new Vector2(0, 1);
            } else if (currentPosistion == Player.State.RUNNINGHORIZONTAL && runLeft == true) {
                direction = new Vector2(-1, 0);
            } else if (currentPosistion == Player.State.RUNNINGHORIZONTAL && runLeft == false) {
                direction = new Vector2(1, 0);
            } else if (currentPosistion == Player.State.RUNNINGDOWN) {
                direction = new Vector2(0, -1);
            }}
        
        // Set the velocity of the projectile body based on the direction and a speed value
        body.setLinearVelocity(direction.nor().scl(100));
        
        //Get current projectile position 
        Vector2 projectilePos = getBody().getPosition();
        //Move sprite image to that position
        getSize().x = projectilePos.x;
        getSize().y = projectilePos.y;
    }

    public void render(SpriteBatch batch) {
        batch.draw(projectile, size.x, size.y, size.width, size.height);
    }

     /* Getter methods */ 
     public Rectangle getSize() {
        return this.size;
    }
    public Body getBody() {
        return this.body;
    }
    public Sprite getSprite() {
        return this.sprite;
    }
    public float getSpeed() {
        return this.speed;
    }
    public Vector2 getPosition() {
        return body.getPosition();
    }

    public Texture getTexture() { 
        return projectile;
    }
    
    // Dispose 
    public void dispose() {

    }
}
