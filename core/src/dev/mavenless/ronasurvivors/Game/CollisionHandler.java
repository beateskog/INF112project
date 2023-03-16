package mavenless.ronasurvivors.Game;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;

public class CollisionHandler implements ContactListener{
    @Override
    public void beginContact(Contact contact) {
        Fixture fixA = contact.getFixtureA();
        Fixture fixB = contact.getFixtureB();

        if ((fixA.getUserData() == "Player" && fixB.getUserData()== "Enemy") ||
            (fixB.getUserData() == "Player" && fixA.getUserData()== "Enemy")) {
            // Do something if collision;
            System.out.println("A collision was detected");
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
