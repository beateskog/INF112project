package mavenless.ronasurvivors.Game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.utils.Pool.Poolable;

import mavenless.ronasurvivors.Utils.LevelUtil;

/* Class for displaying enemies */
public class Enemy implements Poolable {

    private Rectangle size;
    private Sprite sprite;
    private float speed;
    private Body body;
    private Fixture enemyFix;
    private LevelUtil levelUtil;
    private Texture enemyText;
    private boolean alive;
    private float health;
    Texture coin;

    /**
     * Creates an enemy. 
     * 
     * @param size the size of the Rectangle
     * @param speed the speed of the enemy
     * @param levelUtil the levelUtil 
     */
    public Enemy(Rectangle size, float speed, LevelUtil levelUtil) {
        this.size = size;
        this.speed = speed;
        this.levelUtil = levelUtil;
        this.enemyText = new Texture(Gdx.files.internal("sprites/Skeleton/SkeletonSingle.png"));
        
        alive = false;
        
    }

    /* Helper method for defining the body, box and fixture of the enemy */
    private void defineEnemy() {
        // Defining the body of the enemy:
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyType.DynamicBody;
        bodyDef.position.set(size.x, size.y);
        body = levelUtil.world.createBody(bodyDef);

        // Defining the box of the enemy body:  
        PolygonShape box = new PolygonShape();
        box.setAsBox(
            (size.width / 2)-5,     // width of the box
            (size.height / 2)-5,    // height of the box
            new Vector2(
                (size.width / 2),   // center of the box in local coordinates (width)
                (size.height / 2)-1// center of the box in local coordinates (Height)
            ),
            0f                // Rotation (Example if we want to add dodge ability)
        );

        // Defining the fixture of our box (not colliding with objs)
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = box;                     //Setting the shape of the fixture to our box
        fixtureDef.density = 0.0f; 
        fixtureDef.friction = 0.0f;
        fixtureDef.restitution = 0.1f;
        fixtureDef.filter.categoryBits = CollisionBits.CATEGORY_ENEMY;  
        fixtureDef.filter.maskBits = CollisionBits.MASK_ENEMY; 

        enemyFix = getBody().createFixture(fixtureDef);
        enemyFix.setUserData("Enemy");
        
        box.dispose();

        getBody().setFixedRotation(true);
        this.sprite = new Sprite(enemyText);

    }


    /**
     * Sets the enemy to alive and sets the position
     * of the enemy to go in the direction of 
     * the player position. The userdata of the Enemy 
     * is set to the time it was created. 
     * Calls the defineEnemy method. 
     * 
     * @param player_pos the position of the player
     */
    public void init(Vector2 player_pos, int enemyHealth){
        Vector2 randomLocation = levelUtil.getRandomLocation();
        do {
            randomLocation = levelUtil.getRandomLocation();
        } while (randomLocation.dst(player_pos) < 200);
        
        alive = true;
        this.size = new Rectangle(randomLocation.x, randomLocation.y, 18, 25);
        defineEnemy();
        this.health = enemyHealth;
        
        Vector2 enemyPos = getBody().getPosition();
        Vector2 direction = player_pos.sub(enemyPos).nor();

        getFixture().setUserData(System.currentTimeMillis()); // FOR COLLISION HANDLING DONT CHANGE!!
        
        /* Set movement to equal direction (scale by speed) */
        getBody().setLinearVelocity(direction.scl(speed));

        /* Flip sprite */
        if (direction.x < 0) {
            getSprite().setFlip(true, false);
        } else if (direction.x > 0) {
            getSprite().setFlip(false, false);
        }

    }

     /**
     * Move the enemy's position
     * to follow the player position. 
     * This function also updates the sprite
     * image in the way the enemy is moving. 
     * 
     * @param player_pos
     */
    public void update(Vector2 player_pos, float enemySpeed) {
        /* Get enemy position, and get dir-vector */
        Vector2 enemyPos = getBody().getPosition();
        Vector2 direction = player_pos.sub(enemyPos).nor();
        this.speed = enemySpeed;
        /* Set movement to equal direction (scale by speed) */
        getBody().setLinearVelocity(direction.scl(speed));
        
        getSize().x = enemyPos.x;
        getSize().y = enemyPos.y;

        /* Flip sprite */
        if (direction.x < 0) {
            getSprite().setFlip(true, true);
        } else if (direction.x > 0) {
            getSprite().setFlip(false, false);
        }
        
    }

    /**
     * Renders the enemy
     * 
     * @param batch
     */
    public void render(SpriteBatch batch) {
        batch.draw(enemyText, size.x, size.y, size.width, size.height);
    }

    /* Getter methods */ 

    /**
     * 
     * @return rectangle size 
     */
    public Rectangle getSize() {
        return this.size;
    }

    /**
     * 
     * @return the body of the enemy
     */
    public Body getBody() {
        return this.body;
    }

    /**
     * 
     * @return the current healt of the enemy
     */
    public float getHealth(){
        return health;
    }

    /**
     * Sets the health of the enemy. If the 
     * healt is less than or equal to 0, the enemy
     * alive status is set to false.
     * 
     * @param newHealth the healt of the enemy
     */
    public void setHealth(float newHealth){
        if (newHealth <= 0) {
            this.alive = false;
            this.health = 0;

        } else {
            this.health = newHealth;
        }
    }

    /**
     * Returns false if the enemy is dead
     * and true otherwise. 
     * @return if the enemy is alive or not
     */
    public boolean isAlive(){
        return alive;
    }

    /**
     * 
     * @return the fixture of the enemy
     */
    public Fixture getFixture(){
        return enemyFix;
    }

    /**
     * 
     * @return the sprite of the enemy 
     */
    public Sprite getSprite() {
        return this.sprite;
    }

    public void increaseHealt(float increase){
        this.health += increase;
    }
    
    /**
     * Disposes the enemy
     */
    public void dispose() {
        enemyText.dispose();
    }

    @Override
    public void reset() {
        this.body.destroyFixture(enemyFix); 
    }
        
    

}
