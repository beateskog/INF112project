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
    private TextureRegion textureReg; 
    private boolean alive;
    private int health;

    /**
     * Creates an enemy. 
     * 
     * @param size the size of the Rectangle
     * @param sprite the sprite to display 
     * @param speed the speed of the enemy
     * @param levelUtil the levelUtil 
     */
    public Enemy(Rectangle size, float speed, LevelUtil levelUtil) {
        this.size = size;
        this.speed = speed;
        this.levelUtil = levelUtil;
        enemyText = new Texture(Gdx.files.internal("sprites/Projectile/projectile.png"));
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
            (size.width / 2)-4,     // width of the box
            (size.height / 2)-4,    // height of the box
            new Vector2(
                (size.width / 2),   // center of the box in local coordinates (width)
                (size.height / 2)-4 // center of the box in local coordinates (Height)
            ),
            0f                // Rotation (Example if we want to add dodge ability)
        );

        // Defining the fixture of our box (not colliding with objs)
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = box;                     //Setting the shape of the fixture to our box
        fixtureDef.density = 0.5f;
        fixtureDef.friction = 0.4f;
        fixtureDef.restitution = 0.0f;
        fixtureDef.filter.groupIndex = CollisionBits.GROUP_ENEMY;
        fixtureDef.filter.categoryBits = CollisionBits.CATEGORY_ENEMY;  
        fixtureDef.filter.maskBits = CollisionBits.MASK_ENEMY; 

        enemyFix = getBody().createFixture(fixtureDef);
        enemyFix.setUserData("Enemy");
        box.dispose();

        getBody().setFixedRotation(true);
        this.health = 30;
    }


    /**
     * Move the enemy's position
     * to follow the player position. 
     * This function also updates the sprite
     * image in the way the enemy is moving. 
     * 
     * @param player_pos
     */
    public void update(Vector2 player_pos) {
        /* Get enemy position, and get dir-vector */
        Vector2 enemyPos = getBody().getPosition();
        Vector2 direction = player_pos.sub(enemyPos).nor();

        /* Set movement to equal direction (scale by speed) */
        getBody().setLinearVelocity(direction.scl(speed));

        getSize().x = enemyPos.x;
        getSize().y = enemyPos.y;

        /* 
        if (direction.x < 0) {
            getSprite().setFlip(true, false);
        } else if (direction.x > 0) {
            getSprite().setFlip(false, false); 
        }*/
    }

    public void init(Vector2 player_pos){
        defineEnemy();
        alive = true;
        this.size = new Rectangle(player_pos.x + 100, player_pos.y+100, 20, 20);
        Vector2 enemyPos = getBody().getPosition();
        Vector2 direction = player_pos.sub(enemyPos).nor();
        
        /* Set movement to equal direction (scale by speed) */
        getBody().setLinearVelocity(direction.scl(speed));

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

    public int getHealth(){
        return health;
    }

    public void setHealth(int newHealth){
        if (newHealth <= 0) {
            //Alive == false
            this.health = 0;
        } else {
            this.health = newHealth;
        }
    }

    /**
     * 
     * @return the sprite of the enemy 
     */
    public Sprite getSprite() {
        return this.sprite;
    }
    
    /* Dispose */
    public void dispose() {
    
    }

    @Override
    public void reset() {
        this.alive = false;
        this.body.destroyFixture(enemyFix);
    }
        
    

}
