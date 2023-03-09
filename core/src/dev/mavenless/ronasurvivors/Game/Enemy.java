package dev.mavenless.ronasurvivors.Game;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;

import dev.mavenless.ronasurvivors.Utils.LevelUtil;

/* Class for displaying enemies */
public class Enemy {
    private Rectangle size;
    private Sprite sprite;
    private float speed;
    private Body body;

    public Enemy(Rectangle size, Sprite sprite, float speed, LevelUtil levelUtil) {
        this.size = size;
        this.sprite = sprite;
        this.speed = speed;

        // Defining the body of the enemy:
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyType.DynamicBody;
        bodyDef.position.set(size.x, size.y);
        body = levelUtil.world.createBody(bodyDef);

        // Defining the box of the enemy body:  
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

        // Defining the fixture of our box (not colliding with objs)
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = box;                     //Setting the shape of the fixture to our box
        fixtureDef.density = 0.5f;
        fixtureDef.friction = 0.4f;
        fixtureDef.restitution = 0.0f;

        body.createFixture(fixtureDef);
        box.dispose();

        body.setFixedRotation(true);
    }

    public void move(Vector2 player_pos) {
        /* Get enemy position, and get dir-vector */
        Vector2 enemyPos = body.getPosition();
        Vector2 direction = player_pos.sub(enemyPos).nor();

        /* Set movement to equal direction (scale by speed) */
        body.setLinearVelocity(direction.scl(speed));

        /* Sprite image move */
        size.x = enemyPos.x;
        size.y = enemyPos.y;
    }

    public void render(SpriteBatch batch) {
        batch.draw(sprite, size.x-5, size.y-5, size.width+10, size.height+10);
    }

    /* Getter methods */ 
    public Rectangle getSize() {
        return this.size;
    }
    public Sprite getSprite() {
        return this.sprite;
    }
    public float getSpeed() {
        return this.speed;
    }
    
    /* Dispose */
    public void dispose() {
    
    }
}