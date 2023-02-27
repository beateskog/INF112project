package dev.krirogn.ronasurvivors.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ExtendViewport;

import dev.krirogn.ronasurvivors.RonaSurvivors;
import dev.krirogn.ronasurvivors.Utils.InputUtil;
import dev.krirogn.ronasurvivors.Utils.LevelUtil;

public class GameScreen implements Screen {

    final RonaSurvivors game;

    private InputUtil inputUtil;

    private ExtendViewport extendViewport;

    private float speed = 200f;
    private Rectangle player;
    private Sprite playerSprite;

    private LevelUtil levelUtil;
    private World world;
    private OrthogonalTiledMapRenderer orthogonalTiledMapRenderer;
    private Box2DDebugRenderer box2dDebugRenderer;

    public GameScreen(final RonaSurvivors game) {
        this.game = game;

        // Render setup
        float viewSize = 200f;
        extendViewport = new ExtendViewport(viewSize, viewSize);
        extendViewport.getCamera().position.set(viewSize / 2f, viewSize / 2f, 1f);

        // Input
        inputUtil = new InputUtil();

        player = new Rectangle(400, 400, 16, 16);
        playerSprite = new Sprite(new Texture("sprites/player.png"));

        // Tiles
        world = new World(new Vector2(0,0), true);
        box2dDebugRenderer = new Box2DDebugRenderer();
        levelUtil = new LevelUtil();
        levelUtil.LoadTileMap("maps/debugLevel2/debugLevel2.tmx");
        orthogonalTiledMapRenderer = levelUtil.GetTileRenderer();
        levelUtil.GetLayers();
    }

    @Override
    public void show() {}

    private void update() {
        // Update physics
        world.step(1/60f, 6, 8);

        // Move player
        float movementX = inputUtil.moveX();
        player.setPosition(
            player.x + movementX * speed * Gdx.graphics.getDeltaTime(),
            player.y + inputUtil.moveY() * speed * Gdx.graphics.getDeltaTime()
        );
        if (movementX < 0) {
            playerSprite.setFlip(true, false);
        } else {
            playerSprite.setFlip(false, false);
        }

        // Update camera
        float halfWorldWidth = extendViewport.getWorldWidth() / 2;
        float halfWorldHeight = extendViewport.getWorldHeight() / 2;
        extendViewport.getCamera().position.set(
            Math.min(Math.max(player.x, halfWorldWidth), 960 - halfWorldWidth),
            Math.min(Math.max(player.y, halfWorldHeight), 960 - halfWorldHeight),
            1
        );
        extendViewport.getCamera().update();

        // Inputs
        if (inputUtil.pause()) {
            Gdx.app.exit();
        }
    }

    @Override
    public void render(float delta) {
        // Clear screen
        ScreenUtils.clear(Color.BLACK);
        
        // Updates
        inputUtil.update();

        // Setup Render
        extendViewport.apply();

        // Run game loop
        this.update();

        // Render
        extendViewport.apply();
        game.batch.setProjectionMatrix(extendViewport.getCamera().combined);
        orthogonalTiledMapRenderer.setView((OrthographicCamera) extendViewport.getCamera());
        orthogonalTiledMapRenderer.render();

        box2dDebugRenderer.render(world, extendViewport.getCamera().invProjectionView);

        game.batch.begin();
        game.batch.draw(playerSprite, player.x, player.y, player.width, player.height);
        game.batch.end();
    }

    @Override
    public void resize(int width, int height) {
        extendViewport.update(width, height, true);
    }

    @Override
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void hide() {}

    @Override
    public void dispose() {}
    
}
