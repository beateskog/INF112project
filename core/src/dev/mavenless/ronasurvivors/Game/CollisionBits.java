package mavenless.ronasurvivors.Game;

public class CollisionBits {

    /**
     * Category bits for the different types of objects.
     */
    public static final short CATEGORY_PLAYER = 0x0001;
    public static final short CATEGORY_ENEMY = 0x0002; 
    public static final short CATEGORY_PROJECTILE = 0x0003; 
    public static final short CATEGORY_SCENERY = 0x0004; 
    public static final short CATEGORY_PICKUP = 0x0005;

    

    /**
     * Group index for projectiles.
     * Makes it so that projectiles don't collide with each other.
     */
    public static final short GROUP_PROJECTILE = -2; 

    /**
     * Mask for pickups.
     * Player collides with pickups and scenery.
     */
    public static final short MASK_PICKUP = CATEGORY_PLAYER;

    /**
     * Mask for player.
     * Player collides with enemies and scenery.
     */
    public static final short MASK_PLAYER = CATEGORY_ENEMY | CATEGORY_SCENERY;

    /**
     * Mask for enemies.
     * Enemies collide with player, scenery and projectiles.
     */
    public static final short MASK_ENEMY = CATEGORY_PLAYER | CATEGORY_SCENERY | CATEGORY_PROJECTILE;

    /**
     * Mask for projectiles.
     * Projectiles collide with enemies and scenery.
     */
    public static final short MASK_PROJECTILE = CATEGORY_ENEMY | CATEGORY_SCENERY;

    /**
     * Mask for scenery.
     * Scenery collides with player, enemies and projectiles.
     */
    public static final short MASK_SCENERY = -1; //Collides with everything. 
    
}
