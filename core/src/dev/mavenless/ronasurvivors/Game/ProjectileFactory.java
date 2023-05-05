package mavenless.ronasurvivors.Game;

import com.badlogic.gdx.math.Rectangle;

import mavenless.ronasurvivors.Screens.GameScreen;
import mavenless.ronasurvivors.Utils.LevelUtil;

public interface ProjectileFactory {
    Projectile createProjectile(String filename, GameScreen game);
}
