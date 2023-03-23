package mavenless.ronasurvivors.Screens;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
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
    private Projectile projectile;
    private TextureAtlas playerAtlas;
    private TextureAtlas tmpEnemyAtlas;
    
    private float timeSinceLastShot = 0;
    private float deltaTime = 0;
    private boolean fired = false;
    private List<Projectile> projectiles = new ArrayList<Projectile>();
    public final long startTime = System.currentTimeMillis();

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
        levelUtil.world.setContactListener(new CollisionHandler());

        // Texture for player-sprite
        String playerName = "doctor"; // game.getSelectedPLayer()
        definePlayer(playerName);

        // Enemy
        defineEnemy();

        //Projectile
        projectiles.add(defineProjectile(player.getPosition().x, player.getPosition().y));

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
            200f,
            levelUtil, 
            game.input
        );
    }

    private void defineEnemy(){
        this.tmpEnemyAtlas = new TextureAtlas("sprites/Skeleton/Skeleton.atlas");
        TextureRegion skeleton = new TextureRegion(tmpEnemyAtlas.findRegion("Skeleton_idleDown"));
        enemy = new Enemy(
            new Rectangle(
                (player.getPosition().x + 100), //make the enemys posision a little bit away from the player
                (player.getPosition().y + 100),
                20,
                20
            ),
            new Sprite(skeleton),
            10f,
            levelUtil);

        //Healthbar
        this.hp_bar = new HP_bar(100, stage);
        hp_bar.setHealth(50);


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

    private Projectile defineProjectile(float x, float y){
        float angle = degreeOffset(
            (
                (float) Math.atan2(
                    -game.input.moveY(),
                    game.input.moveX()
                )
            )
            * (180 / (float) Math.PI)
            + 180,
            90
        );
        projectile = new Projectile(
            new Rectangle(x,y,10,10),
            10f,
            levelUtil,
            angle);
        return projectile;
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

        // Move enemy
        enemy.move(player.getPosition());
        
        Long seconds = TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis()- startTime);
        System.out.println(seconds);
        //Shoot 
        //projectile.shoot(player.getPosition(), player.getCurrentState(), timeSinceLastShot, player.isRunningLeft());
        timeSinceLastShot += Gdx.graphics.getDeltaTime();
        if (!fired && (timeSinceLastShot >= 2f)) {
            timeSinceLastShot = 0;
            fired = true;
        }
        else if (fired) {
            if (timeSinceLastShot >= 2f) {
                timeSinceLastShot = 0;
                levelUtil.world.destroyBody(projectile.getBody());
                projectile.dispose();
                projectiles.remove(0);
                projectiles.add(defineProjectile(player.getPosition().x,player.getPosition().y));
                fired = false;
            }
        } 

        // Inputs
        if (game.input.up("pause")) {
            Gdx.app.exit();
        }

        if (game.input.down("select")) {
            game.input.vibrate(1000, 1f);
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
        enemy.render(game.batch);
        for (Projectile projectile : projectiles){
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

    @Override
    public void resize(int width, int height) {
        extendViewport.update(width, height, true);
        stage.getViewport().update(width, height, true);
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
        projectile.dispose();
    }    
}
