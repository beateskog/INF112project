package mavenless.ronasurvivors.Game;


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
            hp_bar.setHealth(hp_bar.getHealth()-gameScreen.getEnemyDamage());

        /* Collision between projectile and scenery */
        } else if ((fixA.getFilterData().categoryBits == CollisionBits.CATEGORY_PROJECTILE &&
                    fixB.getFilterData().categoryBits == CollisionBits.CATEGORY_SCENERY) ||
                    (fixB.getFilterData().categoryBits == CollisionBits.CATEGORY_PROJECTILE &&
                    fixA.getFilterData().categoryBits == CollisionBits.CATEGORY_SCENERY)) {

            /* Locate the projectile and destroy it */
            if (fixA.getFilterData().categoryBits == CollisionBits.CATEGORY_PROJECTILE) { 
                 
                for (Projectile pro : gameScreen.getActiveProjectiles()) {
                    if (fixA.getUserData() == pro.getFixture().getUserData()) {
                        pro.setAlive(false);
                    }
                }
            }
             else { 
                for (Projectile pro : gameScreen.getActiveProjectiles()) {
                    if (fixB.getUserData() == pro.getFixture().getUserData()) {
                        pro.setAlive(false);
                    }
                }
             }

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
                        enemy.setHealth(enemy.getHealth()-gameScreen.getProjectileDamage());
                    }
                }
                for (Projectile pro : gameScreen.getActiveProjectiles()) {
                    if (fixA.getUserData() == pro.getFixture().getUserData()) {
                        pro.setAlive(false);
                    }
                }
            } else {
                //Enemy take damage, remove proj
                for (Enemy enemy : gameScreen.getActiveEnemies()) {
                    if (fixA.getUserData() == enemy.getFixture().getUserData()) {
                        enemy.setHealth(enemy.getHealth()-gameScreen.getProjectileDamage());
                    }
                }
                for (Projectile pro : gameScreen.getActiveProjectiles()) {
                    if (fixB.getUserData() == pro.getFixture().getUserData()) {
                        pro.setAlive(false);
                    }
                }

            } 
        } else if( // Check for collision between Player and Pickup
            ((fixA.getFilterData().categoryBits == CollisionBits.CATEGORY_PLAYER && 
             fixB.getFilterData().categoryBits == CollisionBits.CATEGORY_PICKUP) ||
            (fixB.getFilterData().categoryBits == CollisionBits.CATEGORY_PLAYER && 
              fixA.getFilterData().categoryBits == CollisionBits.CATEGORY_PICKUP))){
                
                // We want to give +1 hp for each coin picked up
                HP_bar hp_bar = gameScreen.getHp_bar();
                hp_bar.setHealth(hp_bar.getHealth()+1);
                
                // If pickup-item has collided with the player, pickup item must be removed from rendering. 
                if(fixA.getFilterData().categoryBits == CollisionBits.CATEGORY_PICKUP){
                    for(Pickup pickup : gameScreen.getActivePickups()){
                        if(fixA.getUserData() == pickup.getFixture().getUserData()){
                            pickup.setAlive(false);
                        }
                    }
                } else {
                    for(Pickup pickup : gameScreen.getActivePickups()){
                        if(fixB.getUserData() == pickup.getFixture().getUserData()){
                            pickup.setAlive(false);
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