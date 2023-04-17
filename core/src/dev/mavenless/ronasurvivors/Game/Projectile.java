package mavenless.ronasurvivors.Game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Pool.Poolable;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;


import mavenless.ronasurvivors.Utils.LevelUtil;


public class Projectile implements Poolable {
    private Rectangle size;
    private Body body;
    private float speed;
    private Fixture projectileFix;
    private LevelUtil levelUtil;
    private Texture projectileText;
    private boolean alive;
    private long activeTime; 
    private BodyDef bodyDef;
    private FixtureDef fixtureDef;
    
    /**
     * Creates a projectile. 
     * 
     * @param size the size of the Rectangle
     * @param speed the speed of the Projectile
     * @param levelUtil the levelUtil 
     */
    public Projectile(Rectangle size, float speed, LevelUtil levelUtil) {
        this.size = size;
        this.speed = speed * 100f;
        this.levelUtil = levelUtil;
        projectileText = new Texture(Gdx.files.internal("sprites/Projectile/projectile.png"));
        alive = false;
    }

    private void defineProjectile() {
        // Defining the body of the projectile:
        this.bodyDef = new BodyDef();
        bodyDef.type = BodyType.DynamicBody;
        bodyDef.bullet = true;
        bodyDef.position.set(size.x, size.y);
        body = levelUtil.world.createBody(bodyDef);

        // Create a circle shape and set its radius to 6
        CircleShape circle = new CircleShape();
        circle.setRadius(6f);

        // Defining the fixture of our box (not colliding with objs)
        this.fixtureDef = new FixtureDef();
        fixtureDef.shape = circle;                     //Setting the shape of the fixture to our box
        fixtureDef.density = 0.5f;
        fixtureDef.friction = 0.0f;
        fixtureDef.restitution = 0.0f;
        fixtureDef.filter.groupIndex = -1;
        fixtureDef.filter.categoryBits = CollisionBits.CATEGORY_PROJECTILE;  
        fixtureDef.filter.maskBits = CollisionBits.MASK_PROJECTILE; 
        

        projectileFix = getBody().createFixture(fixtureDef);
        projectileFix.setUserData("Projectile");
        
        circle.dispose();

        getBody().setFixedRotation(true);
    }

    /**
     * Renders the projectile
     * 
     * @param batch
     */
    public void render(SpriteBatch batch) {
        batch.draw(projectileText, size.x, size.y, size.width, size.height);
    }

    /**
     * Updates the posistion of the sprite
     * based on the projectiles position
     */
    public void update(){ 
        Vector2 projectilePos = getBody().getPosition();
        getSize().x = projectilePos.x;
        getSize().y = projectilePos.y;
        System.out.println("CategoryBits: " + this.projectileFix.getFilterData().categoryBits + "    |  Mask: " + this.projectileFix.getFilterData().maskBits);
    }

    /**
     * Initializes the projectile
     * 
     * Sets the time the projectile was initialized, 
     * sets it as alive and
     * applies linear impulse in a direction based
     * on the player's position. 
     * Calls the defineProjectile method 
     * 
     * @param angle the angle of the direction the
     * projectile should move in
     */
    public void init(float angle, float playerX, float playerY){
        activeTime = System.currentTimeMillis();
        alive = true;
        this.size = new Rectangle (playerX,playerY,10,10);
        defineProjectile();
        
        body.applyLinearImpulse(this.speed*(float)(Math.sin(Math.toRadians(angle))),
                        this.speed*(float)(Math.cos(Math.toRadians(angle))),
                        0,
                        0,
                        true);
        
    }

    /* Getter methods */ 
    /**
     * @return the size of a rectangle
     */
    public Rectangle getSize() {
        return this.size;
    }
    /**
     * @return the body of the projectile
     */
    public Body getBody() {
        return this.body;
    }
    /**
     * Returns false if the porjectile is dead
     * and true if the projectile is alive
     * @return the status of the projectile 
     */
    public boolean getIsAlive() {
        return this.alive;
    }

    /**
     * Returns the time 
     * the projectile was 
     * initialized. Is given 
     * in milliseconds
     * @return the time the projectile was initialized
     */
    public void setAlive(boolean input) {
        alive = input;
    }

    public long getActiveTime() {
        return this.activeTime;
    }
    
    public void dispose() {
    }

    @Override
    public void reset() {
        this.alive = false;
        this.activeTime = 0;
        this.body.destroyFixture(projectileFix);
    }
}
