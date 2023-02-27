package dev.krirogn.ronasurvivors.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
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

    private float speed = 300f;

    private LevelUtil levelUtil;
    private World world;
    private OrthogonalTiledMapRenderer orthogonalTiledMapRenderer;
    private Box2DDebugRenderer box2dDebugRenderer;

    public GameScreen(final RonaSurvivors game) {
        this.game = game;

        // Render setup
        extendViewport = new ExtendViewport(400f, 400f);
        extendViewport.getCamera().position.set(200f, 200f, 1f);

        // Input
        inputUtil = new InputUtil();

        // Tiles
        world = new World(new Vector2(0,0), true);
        box2dDebugRenderer = new Box2DDebugRenderer();
        levelUtil = new LevelUtil();
        levelUtil.LoadTileMap("maps/debugLevel1/map0.tmx");
        orthogonalTiledMapRenderer = levelUtil.GetTileRenderer();
        levelUtil.GetLayers();
    }

    @Override
    public void show() {}

    private void update() {
        // Update physics
        world.step(1/60f, 6, 2);

        // Move camera
        Vector3 camPos = extendViewport.getCamera().position;
        extendViewport.getCamera().position.set(
            camPos.x + inputUtil.moveX() * speed * Gdx.graphics.getDeltaTime(),
            camPos.y + inputUtil.moveY() * speed * Gdx.graphics.getDeltaTime(),
            camPos.z
        );
        // Update camera
        extendViewport.getCamera().update();
    }

    @Override
    public void render(float delta) {
        // Clear screen
        ScreenUtils.clear(Color.BLACK);
        
        // Updates
        inputUtil.update();

        // Setup Render
        extendViewport.apply();

        // Run physics loop
        this.update();

        // Render
        game.batch.setProjectionMatrix(extendViewport.getCamera().combined);
        orthogonalTiledMapRenderer.setView((OrthographicCamera) extendViewport.getCamera());
        orthogonalTiledMapRenderer.render();

        box2dDebugRenderer.render(world, extendViewport.getCamera().invProjectionView);
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
