package mavenless.ronasurvivors.Game;

import mavenless.ronasurvivors.Screens.GameScreen;

public class ConcreteProjectileFactory implements ProjectileFactory {
    @Override
    public Projectile createProjectile(String filename, GameScreen game){
            return new Projectile(filename, game);
    }


    
}
