package dev.mavenless.ronasurvivors.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ExtendViewport;

import dev.mavenless.ronasurvivors.RonaSurvivors;
import dev.mavenless.ronasurvivors.Game.Enemy;
import dev.mavenless.ronasurvivors.Game.Player;
import dev.mavenless.ronasurvivors.Game.Save;
import dev.mavenless.ronasurvivors.Utils.LevelUtil;

public class GameScreen implements Screen {
    final RonaSurvivors game;
    private LevelUtil levelUtil;
    private ExtendViewport extendViewport;
    private Box2DDebugRenderer box2dDebugRenderer;

    private Player player;
    private Enemy enemy;
    private TextureAtlas atlas;

    public GameScreen(final RonaSurvivors game) {

        this.game = game;

        // Render setup
        float viewSize = 200f; // size of viewable area
        extendViewport = new ExtendViewport(viewSize, viewSize);
        extendViewport.getCamera().position.set(viewSize / 2f, viewSize / 2f, 1f); // center camera position at center

        // World
        box2dDebugRenderer = new Box2DDebugRenderer();
        levelUtil = new LevelUtil();
        levelUtil.loadTileMap("maps/debugLevel2/debugLevel2.tmx");

        // Texture for player-sprite
        this.atlas = new TextureAtlas("sprites/doctor_white.atlas");

        // Player
        player = new Player(this,
            new Rectangle(
                (levelUtil.getMapWidth() * levelUtil.getTileWidth()) / 2,
                (levelUtil.getMapHeight() * levelUtil.getTileHeight()) / 2,
                16,
                16
            ),
            200f,
            levelUtil, 
            game.input
        );

        // Enemy
        enemy = new Enemy(
            new Rectangle(
                ((levelUtil.getMapWidth() * levelUtil.getTileWidth()) / 2)-100,
                ((levelUtil.getMapHeight() * levelUtil.getTileHeight()) / 2)-10,
                16,
                16
            ),
            new Sprite(new Texture("sprites/player.png")),
            30f,
            levelUtil);



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
        game.batch.end();
    }


    public TextureAtlas getAtlas(){
        return this.atlas;
    }

    @Override
    public void resize(int width, int height) {
        extendViewport.update(width, height, true);
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
