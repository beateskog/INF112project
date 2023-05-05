package mavenless.ronasurvivors.Game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.utils.Pool.Poolable;

import mavenless.ronasurvivors.Utils.LevelUtil;

public class Pickup implements Poolable {

    public Fixture getFixture() {
        return this.fixture;
    }

    public void setFixture(Fixture fixture) {
        this.fixture = fixture;
    }
    private String filename; 
    private Vector2 position;
    private Sprite sprite;
    private Texture text;
    private Boolean alive;
    private Rectangle size;
    private LevelUtil levelUtil;
    private Fixture fixture;
    private Body body;


    
    public Pickup(String filename, LevelUtil levelUtil){
        this.filename = filename;
        text = new Texture(Gdx.files.internal("icons/"+filename));
        sprite = new Sprite(text);
        alive = false;
        this.levelUtil = levelUtil;
    }

    private void definePickup() {
        // Defining the body of the pickup:
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyType.StaticBody;
        bodyDef.position.set(position.x, position.y);
        body = levelUtil.world.createBody(bodyDef);

        // Defining the circle of the pickup body:  
        CircleShape circle  = new CircleShape();
        circle.setRadius(6f);
        // Defining the fixture of our circle, used for collisions
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = circle;                     //Setting the shape of the fixture to our circle
        fixtureDef.filter.categoryBits = CollisionBits.CATEGORY_PICKUP;  
        fixtureDef.filter.maskBits = CollisionBits.MASK_PICKUP; 
        fixtureDef.isSensor = true;

        fixture = getBody().createFixture(fixtureDef);
        fixture.setUserData("Coin");
        circle.dispose();

        this.sprite = new Sprite(text);

    }


    public void render(SpriteBatch batch) {
        batch.draw(sprite, position.x-sprite.getWidth()/4, position.y-sprite.getHeight()/4, sprite.getWidth()/2, sprite.getHeight()/2);
    }

    public void init(Vector2 position){
        this.position = position;
        alive = true;
        definePickup();
        getFixture().setUserData(position);
    }

    @Override
    public void reset() {
        this.alive = false;
        this.body.destroyFixture(fixture);
    }   

    

    // Getters and Setters
    public String getFilename() {
        return this.filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public Vector2 getPosition() {
        return this.position;
    }

    public void setPosition(Vector2 position) {
        this.position = position;
    }

    public Sprite getSprite() {
        return this.sprite;
    }

    public void setSprite(Sprite sprite) {
        this.sprite = sprite;
    }

    public Texture getText() {
        return this.text;
    }

    public void setText(Texture text) {
        this.text = text;
    }

    public Boolean isAlive() {
        return this.alive;
    }

    public Boolean getAlive() {
        return this.alive;
    }

    public void setAlive(Boolean alive) {
        this.alive = alive;
    }
    public Rectangle getSize() {
        return this.size;
    }

    public void setSize(Rectangle size) {
        this.size = size;
    }

    public LevelUtil getLevelUtil() {
        return this.levelUtil;
    }

    public void setLevelUtil(LevelUtil levelUtil) {
        this.levelUtil = levelUtil;
    }

    public void setfixture(Fixture fixture) {
        this.fixture = fixture;
    }

    public Body getBody() {
        return this.body;
    }

    public void setBody(Body body) {
        this.body = body;
    }
    

}
