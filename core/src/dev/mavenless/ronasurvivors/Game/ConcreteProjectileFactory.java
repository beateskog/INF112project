package mavenless.ronasurvivors.Game;

import mavenless.ronasurvivors.Screens.GameScreen;

public class ConcreteProjectileFactory implements ProjectileFactory {
    
    /** This class works as a factory, its job is to create instances of Projectiles based on the filename given as input.
     * @param filename
     * @param game
     * @return Projectile
     */
    @Override
    public Projectile createProjectile(String filename, GameScreen game){
            return new Projectile(filename, game);
    }


    
}
