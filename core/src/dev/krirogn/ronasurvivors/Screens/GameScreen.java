package dev.krirogn.ronasurvivors.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.ExtendViewport;

import dev.krirogn.ronasurvivors.RonaSurvivors;
import dev.krirogn.ronasurvivors.Utils.LevelUtil;

/* NOT FULLY IMPLEMENTED YET!! */
public class GameScreen extends ScreenAdapter {

    private final RonaSurvivors game;
    private OrthographicCamera camera;

    private Stage stage;
    private Table table;
    private World world;

    private Box2DDebugRenderer box2dDebugRenderer;
    private OrthogonalTiledMapRenderer orthogonalTiledMapRenderer;
    private LevelUtil levelUtil;
    private ExtendViewport extendViewport;

    public GameScreen(final RonaSurvivors game) {
        this.game = game;

        this.extendViewport = new ExtendViewport(400f, 400f);
        this.extendViewport.getCamera().position.set(200f, 200f,1f); 


        this.world = new World(new Vector2(0,0), false);

        this.box2dDebugRenderer = new Box2DDebugRenderer();

        this.levelUtil = new LevelUtil();
        this.orthogonalTiledMapRenderer = levelUtil.setupMap("maps/map0.tmx");

        /* Setting the stage as main input processor */
        /* this.stage = new Stage();
        Gdx.input.setInputProcessor(stage);
    
        this.table = new Table(); */
    }

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

        /* Setting a black screen */
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        orthogonalTiledMapRenderer.render();

      

        box2dDebugRenderer.render(world, extendViewport.getCamera().invProjectionView);
    }

}
