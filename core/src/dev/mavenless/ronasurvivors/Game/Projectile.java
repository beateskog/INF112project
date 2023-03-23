package mavenless.ronasurvivors.Game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.Fixture;
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
    private Texture projectile;
    

    public Projectile(Rectangle size, float speed, LevelUtil levelUtil, float angle) {
        this.size = size;
        this.speed = speed * 100000000;
        this.levelUtil = levelUtil;
        projectile = new Texture(Gdx.files.internal("sprites/Projectile/projectile.png"));

        defineProjectile();

        //body.setLinearVelocity(direction.nor().scl(10000));

        body.applyLinearImpulse(this.speed*(float)(Math.sin(Math.toRadians(angle))),this.speed*(float)(Math.cos(Math.toRadians(angle))),0,0,true);
        
    }

    private void defineProjectile() {
        // Defining the body of the projectile:
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyType.DynamicBody;
        bodyDef.bullet = true;
        bodyDef.position.set(size.x, size.y);
        body = levelUtil.world.createBody(bodyDef);

        // Create a circle shape and set its radius to 6
        CircleShape circle = new CircleShape();
        circle.setRadius(6f/50);

        // Defining the fixture of our box (not colliding with objs)
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = circle;                     //Setting the shape of the fixture to our box
        fixtureDef.density = 0.5f;
        fixtureDef.friction = 0.0f;
        fixtureDef.restitution = 0.0f;
        fixtureDef.filter.categoryBits = CollisionBits.CATEGORY_PROJECTILE;  
        fixtureDef.filter.maskBits = CollisionBits.MASK_PROJECTILE; 

        projectileFix = getBody().createFixture(fixtureDef);
        projectileFix.setUserData("Projectile");
        circle.dispose();

        getBody().setFixedRotation(true);
    }

    public void render(SpriteBatch batch) {
        batch.draw(projectile, size.x, size.y, size.width, size.height);
        Vector2 projectilePos = getBody().getPosition();
        //Move sprite image to that position
        getSize().x = projectilePos.x;
        getSize().y = projectilePos.y;
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
