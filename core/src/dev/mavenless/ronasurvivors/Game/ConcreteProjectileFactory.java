package mavenless.ronasurvivors.Game;

import com.badlogic.gdx.math.Rectangle;

import mavenless.ronasurvivors.Screens.GameScreen;
import mavenless.ronasurvivors.Utils.LevelUtil;

public class ConcreteProjectileFactory implements ProjectileFactory {
    @Override
    public Projectile createProjectile(String filename, GameScreen game){
            return new Projectile(filename, game);
    }


    
}
