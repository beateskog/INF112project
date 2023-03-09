package dev.mavenless.ronasurvivors.Game;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;

import dev.mavenless.ronasurvivors.Utils.InputUtil;
import dev.mavenless.ronasurvivors.Utils.LevelUtil;

public class Player {

    private LevelUtil levelUtil;

    private Rectangle size;
    private Sprite sprite;
    private float speed;
    private Body body;
    
    public Player(Rectangle size, Sprite sprite, float speed, LevelUtil levelUtil) {
        this.size = size;
        this.sprite = sprite;
        this.speed = speed;
        this.levelUtil = levelUtil;

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

    public void move(InputUtil input, Camera camera, float worldWidth, float worldHeight) {
        // Move size box
        float movementX = input.moveX();
        body.setLinearVelocity(
            movementX * speed,
            input.moveY() * speed
        );
        
        Vector2 bodyPos = body.getPosition();
        size.x = bodyPos.x;
        size.y = bodyPos.y;
        // Flip the sprite
        if (movementX < 0) {
            sprite.setFlip(true, false);
        } else if (movementX > 0) {
            sprite.setFlip(false, false);
        }

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

    public void render(SpriteBatch batch) {
        batch.draw(sprite, size.x-5, size.y-5, size.width+10, size.height+10);
    }

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
