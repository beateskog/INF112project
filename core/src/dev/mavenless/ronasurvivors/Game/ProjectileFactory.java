package mavenless.ronasurvivors.Game;


import mavenless.ronasurvivors.Screens.GameScreen;
    /**
     * Interface for projectile factory that creates a new `Projectile` object.
     */
public interface ProjectileFactory {
    Projectile createProjectile(String filename, GameScreen game);
}
