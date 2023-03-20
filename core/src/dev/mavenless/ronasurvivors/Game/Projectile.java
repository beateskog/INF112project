package mavenless.ronasurvivors.Game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
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
    
    private Sprite sprite;
    private Rectangle size;
    private Body body;
    private float speed;
    private Fixture projectileFix;
    private LevelUtil levelUtil;
    private TextureAtlas atlas;
    private Texture projectile;

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
        fixtureDef.shape = circle;                     //Setting the shape of the fixture to our box
        fixtureDef.density = 0.5f;
        fixtureDef.friction = 0.4f;
        fixtureDef.restitution = 0.0f;

        projectileFix = getBody().createFixture(fixtureDef);
        projectileFix.setUserData("Projectile");
        box.dispose();

        getBody().setFixedRotation(true);
    }

    public void shoot(Vector2 playerPosition) {
        Vector2 projectilePos = getBody().getPosition();
        Vector2 velocity = new Vector2(speed, 0);
        velocity.rotateRad(body.getAngle());
        body.setLinearVelocity(velocity);
            
        /* Sprite image move */
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
