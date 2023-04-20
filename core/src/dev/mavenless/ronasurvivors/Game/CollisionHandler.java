package mavenless.ronasurvivors.Game;

import java.util.Random;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;


import mavenless.ronasurvivors.Screens.GameScreen;


/**
 * Class for handling collision between physics objects
 */
public class CollisionHandler implements ContactListener{

    private GameScreen gameScreen;
    
    /**
     * Constructor for collision handler
     * @param gameScreen - current gamescreen for
     *                     collisionhandling
     */
    public CollisionHandler(GameScreen gameScreen) {
        this.gameScreen = gameScreen;
    }
    
    @Override
    public void beginContact(Contact contact) {
        /* Retrieving fixtures currently touching eachother */
        Fixture fixA = contact.getFixtureA();
        Fixture fixB = contact.getFixtureB();

        /* Collision between player and enemy */
        if ((fixA.getFilterData().categoryBits == CollisionBits.CATEGORY_PLAYER && 
             fixB.getFilterData().categoryBits == CollisionBits.CATEGORY_ENEMY) ||
            (fixB.getFilterData().categoryBits == CollisionBits.CATEGORY_PLAYER && 
              fixA.getFilterData().categoryBits == CollisionBits.CATEGORY_ENEMY)) {
            
            /* Update player health after encounter with enemy */
            HP_bar hp_bar = gameScreen.getHp_bar();
            Random r = new Random();
            hp_bar.setHealth(hp_bar.getHealth()-r.nextInt(10));

        /* Collision between projectile and scenery */
        } else if ((fixA.getFilterData().categoryBits == CollisionBits.CATEGORY_PROJECTILE &&
                    fixB.getFilterData().categoryBits == CollisionBits.CATEGORY_SCENERY) ||
                    (fixB.getFilterData().categoryBits == CollisionBits.CATEGORY_PROJECTILE &&
                    fixA.getFilterData().categoryBits == CollisionBits.CATEGORY_SCENERY)) {

            /* Locate the projectile and destroy it */
            if (fixA.getFilterData().categoryBits == CollisionBits.CATEGORY_PROJECTILE) { } else { }

        /* Collision between enemy and projectile */
        } else if ((fixA.getFilterData().categoryBits == CollisionBits.CATEGORY_PROJECTILE &&
                    fixB.getFilterData().categoryBits == CollisionBits.CATEGORY_ENEMY) ||
                    (fixB.getFilterData().categoryBits == CollisionBits.CATEGORY_PROJECTILE &&
                    fixA.getFilterData().categoryBits == CollisionBits.CATEGORY_ENEMY)) {
                
            /* Locate the projectile and destroy, -health on enemy */
            if (fixA.getFilterData().categoryBits == CollisionBits.CATEGORY_PROJECTILE) {
                //Enemy take damage, remove proj
                for (Enemy enemy : gameScreen.getActiveEnemies()) {
                    if (fixB.getUserData() == enemy.getFixture().getUserData()) {
                        enemy.setHealth(enemy.getHealth()-10);
                    }
                }
            } else {
                //Enemy take damage, remove proj
                for (Enemy enemy : gameScreen.getActiveEnemies()) {
                    if (fixA.getUserData() == enemy.getFixture().getUserData()) {
                        enemy.setHealth(enemy.getHealth()-10);
                    }
                }
            } 
        } 
    }
    

    // Methods not relevant for our game at the moment
    @Override
    public void endContact(Contact contact) {
    }
    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {
    }
    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {
    }
    
}