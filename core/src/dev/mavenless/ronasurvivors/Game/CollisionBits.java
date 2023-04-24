package mavenless.ronasurvivors.Game;

public class CollisionBits {

    public static final short CATEGORY_PLAYER = 0x0001; 
    public static final short CATEGORY_ENEMY = 0x0002; 
    public static final short CATEGORY_PROJECTILE = 0x0003; 
    public static final short CATEGORY_SCENERY = 0x0004; 

    public static final short GROUP_PROJECTILE = -2; 

    public static final short MASK_PLAYER = CATEGORY_ENEMY | CATEGORY_SCENERY;
    public static final short MASK_ENEMY = CATEGORY_PLAYER | CATEGORY_SCENERY | CATEGORY_PROJECTILE;
    public static final short MASK_PROJECTILE = CATEGORY_ENEMY | CATEGORY_SCENERY;
    public static final short MASK_SCENERY = -1; //Collides with everything. 
    
}
