package mavenless.ronasurvivors.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Pool;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import mavenless.ronasurvivors.RonaSurvivors;
import mavenless.ronasurvivors.Game.CollisionHandler;
import mavenless.ronasurvivors.Game.Enemy;
import mavenless.ronasurvivors.Game.HP_bar;
import mavenless.ronasurvivors.Game.Player;
import mavenless.ronasurvivors.Game.Projectile;
import mavenless.ronasurvivors.Game.Save;
import mavenless.ronasurvivors.Utils.LevelUtil;

public class GameScreen implements Screen {
    final RonaSurvivors game;
    private LevelUtil levelUtil;
    private ExtendViewport extendViewport;
    private Box2DDebugRenderer box2dDebugRenderer;
    private Stage stage;

    private Player player;
    private HP_bar hp_bar;
    private Enemy enemy;
    private TextureAtlas playerAtlas;
    
    private float timeSinceLastShot = 0;
    private float timeSinceLastEnemy = 0;
    
    public final long startTime = System.currentTimeMillis();

    private final Array<Enemy> activeEnemies = new Array<Enemy>();
    private final Pool<Enemy> enemyPool = new Pool<Enemy>() {
        @Override
        protected Enemy newObject() {
            return new Enemy(
                new Rectangle(
                    (player.getPosition().x + 100), //make the enemys posision a little bit away from the player
                    (player.getPosition().y + 100),
                    20,
                    20
                ),
                3f,
                levelUtil);
        }
    };

    private final Array<Projectile> activeProjectiles= new Array<Projectile>();
    private final Pool<Projectile> projectilePool = new Pool<Projectile>() {
        @Override
        protected Projectile newObject() {
            return new Projectile(
                new Rectangle(player.getPosition().x,player.getPosition().y,10,10),
                2000f,
                levelUtil);
        }
        };


    public GameScreen(final RonaSurvivors game) {

        this.game = game;

        // Render setup
        float viewSize = 200f; // size of viewable area
        extendViewport = new ExtendViewport(viewSize, viewSize);
        extendViewport.getCamera().position.set(viewSize / 2f, viewSize / 2f, 1f); // center camera position at center
        stage = new Stage(new ScreenViewport());

        // World
        box2dDebugRenderer = new Box2DDebugRenderer();
        levelUtil = new LevelUtil();
        levelUtil.loadTileMap("maps/debugLevel2/debugLevel2.tmx");
        levelUtil.world.setContactListener(new CollisionHandler(this));

        // Texture for player-sprite
        String playerName = "doctor"; // game.getSelectedPLayer()
        definePlayer(playerName);

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
        
        timeSinceLastShot += Gdx.graphics.getDeltaTime();
        if (timeSinceLastShot >= 1f) {
            
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
            projectile1.init(angle, player.getPosition().x,player.getPosition().y);
            activeProjectiles.add(projectile1);
            timeSinceLastShot = 0;
        }

        timeSinceLastEnemy += Gdx.graphics.getDeltaTime();
        if (timeSinceLastEnemy >= 4f){
            Enemy enemy1 = enemyPool.obtain();
            enemy1.init(player.getPosition());
            activeEnemies.add(enemy1);
            timeSinceLastEnemy = 0; 
        }
        
    
        for (Projectile pro : activeProjectiles){
            if ((System.currentTimeMillis() - pro.getActiveTime()) >= 2000){
                projectilePool.free(pro);
                activeProjectiles.removeValue(pro,true);
            } else if (pro.IsAlive()==false){
                projectilePool.free(pro);
                activeProjectiles.removeValue(pro,true);
            }
        }

        //Destroy enemies:
        for (Enemy enemy : activeEnemies){
            if (!enemy.isAlive()){
                enemyPool.free(enemy);
                activeEnemies.removeValue(enemy, true);
            }
        }
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
        box2dDebugRenderer.render(levelUtil.world, extendViewport.getCamera().combined);

        // Draw & render sprites, enemies, projectiles, etc..
        game.batch.begin();
        player.render(game.batch);
        for (Enemy enemy : activeEnemies){
            enemy.update(player.getPosition());
            enemy.render(game.batch);
        }
        for (Projectile projectile : activeProjectiles) {
            projectile.update();
            projectile.render(game.batch);
        }
        game.batch.end();

        // Stage render
        stage.getViewport().apply();
        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();
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

    public Pool<Enemy> getEnemyPool(){
        return enemyPool;
    }

    public HP_bar getHp_bar(){
        return this.hp_bar;
    }

    @Override
    public void resize(int width, int height) {
        extendViewport.update(width, height, true);
        stage.getViewport().update(width, height, true);
        // Resizing hp-bar
        stage.getActors().get(0).setPosition(stage.getWidth()/2-(this.hp_bar.getWidth()/2), stage.getHeight()-50);
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
        box2dDebugRenderer.dispose();
        player.dispose();
        enemy.dispose();
        
    }    
}
