package dev.krirogn.ronasurvivors.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
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

    private World world;
    private Box2DDebugRenderer box2dDebugRenderer;
    private OrthogonalTiledMapRenderer orthogonalTiledMapRenderer;
    private LevelUtil levelUtil;

    public GameScreen(final RonaSurvivors game) {
        this.game = game;

        // Render setup
        extendViewport = new ExtendViewport(400f, 400f);
        extendViewport.getCamera().position.set(200f, 200f, 1f);

        // Input
        inputUtil = new InputUtil();

        // Tiles
        world = new World(new Vector2(0,0), false);
        box2dDebugRenderer = new Box2DDebugRenderer();
        levelUtil = new LevelUtil();
        orthogonalTiledMapRenderer = levelUtil.setupMap("maps/map0.tmx");
    }

    @Override
    public void show() {}

    private void update() {
        world.step(1/60f, 6, 2);
        extendViewport.apply();  /* Not implemented yet (camera follows player) */

       
        game.batch.setProjectionMatrix(extendViewport.getCamera().combined);
        orthogonalTiledMapRenderer.setView((OrthographicCamera) extendViewport.getCamera());
        if (Gdx.input.isKeyPressed(Input.Keys.ESCAPE)) {
            Gdx.app.exit();
        }
    }

    @Override
    public void render(float delta) {
        this.update();
        
        ScreenUtils.clear(Color.CYAN);

        // Input
        inputUtil.update();

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
    public void dispose() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'dispose'");
    }
    
}
