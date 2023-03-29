package mavenless.ronasurvivors.Game;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.physics.box2d.World;

import mavenless.ronasurvivors.Screens.GameScreen;



public class CollisionHandler implements ContactListener{

    private GameScreen gameScreen;
    

    public CollisionHandler(GameScreen gameScreen) {
        this.gameScreen = gameScreen;
        
        
    }

    @Override
    public void beginContact(Contact contact) {
        Fixture fixA = contact.getFixtureA();
        Fixture fixB = contact.getFixtureB();

        if ((fixA.getFilterData().categoryBits == CollisionBits.CATEGORY_PLAYER && 
             fixB.getFilterData().categoryBits == CollisionBits.CATEGORY_ENEMY) ||
            (fixB.getFilterData().categoryBits == CollisionBits.CATEGORY_PLAYER && 
              fixA.getFilterData().categoryBits == CollisionBits.CATEGORY_ENEMY)) {
            // Do something if collision;
            System.out.println("A collision was detected: Enemy hit Player");
        } else if ((fixA.getFilterData().categoryBits == CollisionBits.CATEGORY_PROJECTILE &&
                    fixB.getFilterData().categoryBits == CollisionBits.CATEGORY_SCENERY) ||
                    (fixB.getFilterData().categoryBits == CollisionBits.CATEGORY_PROJECTILE &&
                    fixA.getFilterData().categoryBits == CollisionBits.CATEGORY_SCENERY)) {
            System.out.println("A collision was detected projectile hit da wall :(");

            if (fixA.getFilterData().categoryBits == CollisionBits.CATEGORY_PROJECTILE) {
                fixA.getBody();
                World world = fixA.getBody().getWorld();
                Projectile projectile = (Projectile)fixA.getBody().getUserData();
            
                /* if (!world.isLocked()){
                    gameScreen.getProjectiles().remove(projectile);
                    world.destroyBody(fixA.getBody());
                } */

            } else if (fixB.getFilterData().categoryBits == CollisionBits.CATEGORY_PROJECTILE) {
                World world = fixB.getBody().getWorld();
                Projectile projectile = (Projectile)fixB.getBody().getUserData();
                
                /* if (!world.isLocked()){
                    gameScreen.getProjectiles().remove(projectile);
                    world.destroyBody(fixB.getBody());
                } */
                
            }
        } else if ((fixA.getFilterData().categoryBits == CollisionBits.CATEGORY_PROJECTILE &&
                    fixB.getFilterData().categoryBits == CollisionBits.CATEGORY_ENEMY) ||
                    (fixB.getFilterData().categoryBits == CollisionBits.CATEGORY_PROJECTILE &&
                    fixA.getFilterData().categoryBits == CollisionBits.CATEGORY_ENEMY)) {

                System.out.println("A collision was detected: projectile hit enemy");
                if (fixA.getFilterData().categoryBits == CollisionBits.CATEGORY_PROJECTILE) {
                    
                } else if (fixB.getFilterData().categoryBits == CollisionBits.CATEGORY_PROJECTILE) {
                   
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
