package mavenless.ronasurvivors.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
// import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Pool;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.ui.Label;

import mavenless.ronasurvivors.RonaSurvivors;
import mavenless.ronasurvivors.Game.CollisionHandler;
import mavenless.ronasurvivors.Game.ConcreteProjectileFactory;
import mavenless.ronasurvivors.Game.Enemy;
import mavenless.ronasurvivors.Game.HP_bar;
import mavenless.ronasurvivors.Game.Pickup;
import mavenless.ronasurvivors.Game.Player;
import mavenless.ronasurvivors.Game.Projectile;
import mavenless.ronasurvivors.Game.ProjectileFactory;
import mavenless.ronasurvivors.Game.Save;
import mavenless.ronasurvivors.Utils.LevelUtil;


public class GameScreen implements Screen {
    private GameScreen gameScreen = this;
    final RonaSurvivors game;
    private LevelUtil levelUtil;
    private ExtendViewport extendViewport;
    // private Box2DDebugRenderer box2dDebugRenderer;
    private Stage stage;
    
    private Player player;
    private HP_bar hp_bar;
    private TextureAtlas playerAtlas;
    
    private float timeSinceLastShot = 0;
    private float projectileDamage = 5f; 

    //Enemy
    private float timeSinceLastEnemy = 0;
    private float enemySpawnInterval = 1.0f;
    private float enemySpeed = 3.0f;
    private int enemyHealth = 5; 
    private int enemyDamage = 3;
    private int start = 0;
    
    private Label killCountLabel = new Label("Kills: 0", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
    private Label timeSurvived = new Label("Timer: 0", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
    private Label coinLabel = new Label("Coins: 0", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
    
    private int coinCount = 0;
    public final long startTime = System.currentTimeMillis();
    private Array<Pickup> activePickups = new Array<Pickup>();
    private final Array<Enemy> activeEnemies = new Array<Enemy>();
    private final Pool<Enemy> enemyPool = new Pool<Enemy>() {
        @Override
        protected Enemy newObject() {
            return new Enemy(
                new Rectangle(
                    (0), 
                    (0),
                    20,
                    20
                ),
                enemySpeed,
                levelUtil);
        }
    };
    private final Pool<Pickup> pickupsPool = new Pool<Pickup>(){
        @Override 
        protected Pickup newObject(){
            return new Pickup("coin.png", levelUtil);
        }
    };
    ProjectileFactory projectileFactory = new ConcreteProjectileFactory(); 
    private final Array<Projectile> activeProjectiles= new Array<Projectile>();
    private final Pool<Projectile> projectilePool;
    

    public GameScreen(final RonaSurvivors game) {

        this.game = game;
        // Render setup
        float viewSize = 200f; // size of viewable area
        extendViewport = new ExtendViewport(viewSize, viewSize);
        extendViewport.getCamera().position.set(viewSize / 2f, viewSize / 2f, 1f); // center camera position at center
        stage = new Stage(new ScreenViewport());

        // World
        // box2dDebugRenderer = new Box2DDebugRenderer();
        levelUtil = new LevelUtil();
        levelUtil.loadTileMap("maps/debugLevel2/debugLevel2.tmx");
        levelUtil.world.setContactListener(new CollisionHandler(this));

        // Texture for player-sprite
        String playerName = "doctor"; // game.getSelectedPLayer()
        definePlayer(playerName);

        killCountLabel.setFontScale(2f, 2f);
        timeSurvived.setFontScale(2f, 2f);
        coinLabel.setFontScale(2f,2f);
        
        

        // Save data
        Save save = new Save();
        try {
            save.write();
            save.read();
        } catch (Exception e) {
            e.printStackTrace();
            dispose();
            Gdx.app.exit();
        }
        projectilePool = new Pool<Projectile>() {
            @Override
            protected Projectile newObject() {
                return projectileFactory.createProjectile("shuriken.png", gameScreen);
                   
            }
            };
    
    }

    @Override
    public void show() {}

    private void definePlayer(String playerName){
        this.playerAtlas = new TextureAtlas("character/doctor/doctor_white.atlas"); // newTextureAtlas("sprites/"++playerName)
        player = new Player(this,
            new Rectangle(
                (levelUtil.getMapWidth() * levelUtil.getTileWidth()) / 2, //posision at the center of the map
                (levelUtil.getMapHeight() * levelUtil.getTileHeight()) / 2,
                16,
                16
            ),
            11f,
            levelUtil, 
            game.input
        );
         //Healthbar
         this.hp_bar = new HP_bar(100, stage);

        // Save data
        Save save = new Save();
        try {
            save.write();
            save.read();
        } catch (Exception e) {
            e.printStackTrace();
            dispose();
            Gdx.app.exit();
        }
    }

    private float degreeOffset(float val, float offset) {
        float ret = val; 

        ret -= offset;

        if (ret < 0) {
            ret *= -1;
            ret = offset - ret;
            ret += 360 - offset; 
        }

        return ret;
    }

    private void update() {

        if (player.checkPlayerUpgrade()){
            enemySpawnInterval *= 0.9;
            enemySpeed *= 1.05f;
            enemyHealth *= 1.6; 
            projectileDamage *= 1.3f;
            enemyDamage *= 1.2f;
        }

        // Update physics
        levelUtil.world.step(1/16f, 6, 2);

        // Move player and follow camera
        player.move(
            extendViewport.getCamera(),
            extendViewport.getWorldWidth(),
            extendViewport.getWorldHeight()
        );

        
        // Inputs
        if (game.input.up("pause")) {
            Gdx.app.exit();
        }

        if (game.input.down("select")) {
            game.input.vibrate(1000, 1f);
        }

        Vector2 playerLastMovement = player.getLastMovementDirection();
        
        //New projectile
        timeSinceLastShot += Gdx.graphics.getDeltaTime();
        if (timeSinceLastShot >= player.getShootInterval()) {
            
            float angle = degreeOffset(
            (
                (float) Math.atan2(
                    -playerLastMovement.x,
                    playerLastMovement.y
                )
            )
            * (180 / (float) Math.PI)
            + 180,
            90
            );
            Projectile projectile1 = projectilePool.obtain();
            projectile1.init(angle, this);
            activeProjectiles.add(projectile1);
            timeSinceLastShot = 0;
        }

        //more enemies when game starts 
        if (start == 0) {
            start+= 1;
            for (int i = 0; i < 20; i++) {
                Enemy enemy1 = enemyPool.obtain();
                enemy1.init(player.getPosition(), enemyHealth);
                activeEnemies.add(enemy1);
            }
        }
        //New enemy 
        timeSinceLastEnemy += Gdx.graphics.getDeltaTime();
        if (timeSinceLastEnemy >= enemySpawnInterval){
            Enemy enemy1 = enemyPool.obtain();
            enemy1.init(player.getPosition(), enemyHealth);
            activeEnemies.add(enemy1);
            timeSinceLastEnemy = 0; 
        }
        
        // Check if projectile should be removed 
        for (Projectile pro : activeProjectiles){
            if ((System.currentTimeMillis() - pro.getActiveTime()) >= 2000){
                projectilePool.free(pro);
                activeProjectiles.removeValue(pro,true);
            } else if (pro.IsAlive()==false){
                projectilePool.free(pro);
                activeProjectiles.removeValue(pro,true);
            }
        }

        //Check if enemies should me removed 
        for (Enemy enemy : activeEnemies){
            if (!enemy.isAlive()){
                player.increaseKillcount();
                int killCount = player.getKillcount();
                killCountLabel.setText("Kills: " + killCount);
                enemyPool.free(enemy);
                
                activeEnemies.removeValue(enemy, true);

                Pickup droppedXP = pickupsPool.obtain(); 
                droppedXP.init(enemy.getBody().getPosition());
                activePickups.add(droppedXP);
            }
        }

        //check if pickups are collected
        for (Pickup pickupState : activePickups){
            if(!pickupState.isAlive()){
                coinCount += 1;
                coinLabel.setText("Coins: " + coinCount);
                pickupsPool.free(pickupState);
                
                activePickups.removeValue(pickupState, true);
            }
        }

        if (hp_bar.getHealth() <= 0) {
            gameOver();
        }

        timeSurvived.setText("Timer: " + TimeUtils.timeSinceMillis(startTime)/1000);
    }

    @Override
    public void render(float delta) {
        // Clear screen
        ScreenUtils.clear(Color.BLACK);

        // Applies the camera
        extendViewport.apply();
        // Run game loop
        this.update();
        
        // Render setup
        game.batch.setProjectionMatrix(extendViewport.getCamera().combined);
        levelUtil.render((OrthographicCamera) extendViewport.getCamera());

        // Debug renderer for development!
        // box2dDebugRenderer.render(levelUtil.world, extendViewport.getCamera().combined);

        // Draw & render sprites, enemies, projectiles, etc..
        game.batch.begin();
        player.render(game.batch);
        for (Enemy enemy : activeEnemies){
            enemy.update(player.getPosition(), enemySpeed);
            enemy.render(game.batch);
        }
        for (Projectile projectile : activeProjectiles) {
            projectile.update();
            projectile.render(game.batch);
        }
        for (Pickup pickup : activePickups){
            pickup.render(game.batch);
        }
        game.batch.end();

        // Stage render
        stage.getViewport().apply();
        stage.addActor(killCountLabel);
        stage.addActor(timeSurvived);
        stage.addActor(coinLabel);

        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();
    }

    /**
     * Function for initiating
     * gameOverScreen class
     */
    private void gameOver() {
        game.setScreen(new GameOverScreen(game, player.getKillcount()));
    }

    public TextureAtlas getAtlas(){
        return this.playerAtlas;
    }

    public Array<Projectile> getActiveProjectiles(){
        return activeProjectiles;
    }

    public Array<Enemy> getActiveEnemies(){
        return activeEnemies;
    }

    public Vector2 getPlayerPos(){
        return this.player.getPosition();
    }

    public LevelUtil getLevelUtil(){
        return this.levelUtil;
    }

    public HP_bar getHp_bar(){
        return this.hp_bar;
    }

    public float getProjectileDamage(){
        return this.projectileDamage;
    }

    public int getEnemyDamage(){
        return this.enemyDamage;
    }

    public Array<Pickup> getActivePickups(){
        return this.activePickups;
    }

    public Player getPlayer(){
        return this.player;
    }

    @Override
    public void resize(int width, int height) {
        extendViewport.update(width, height, true);
        stage.getViewport().update(width, height, true);
        // Resizing hp-bar + labels
        stage.getActors().get(0).setPosition(stage.getWidth()/2-(this.hp_bar.getWidth()/2), stage.getHeight()-50);
        killCountLabel.setPosition(200, (stage.getViewport().getScreenHeight() - killCountLabel.getHeight())-10);
        timeSurvived.setPosition(5, (stage.getViewport().getScreenHeight() - timeSurvived.getHeight())-10);
        coinLabel.setPosition(400, (stage.getViewport().getScreenHeight() - coinLabel.getHeight())-10);

    }

    @Override
    public void pause() {
        Gdx.app.debug("App", "Pause");
    }

    @Override
    public void resume() {
        Gdx.app.debug("App", "Resume");
    }

    @Override
    public void hide() {
        Gdx.app.debug("App", "Hidden");
    }

    @Override
    public void dispose() {
        // box2dDebugRenderer.dispose();
        player.dispose();
    }    
}
