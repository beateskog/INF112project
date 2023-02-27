package dev.krirogn.ronasurvivors.Screens;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ExtendViewport;

import dev.krirogn.ronasurvivors.RonaSurvivors;
import dev.krirogn.ronasurvivors.Utils.InputUtil;
import dev.krirogn.ronasurvivors.Utils.LevelUtil;

public class GameScreen implements Screen {

    final RonaSurvivors game;

    private InputUtil inputUtil;
    private LevelUtil levelUtil;

    private ExtendViewport extendViewport;
    private Box2DDebugRenderer box2dDebugRenderer;

    private float speed = 200f;
    private Rectangle player;
    private Sprite playerSprite;

    public GameScreen(final RonaSurvivors game) {
        this.game = game;

        // Render setup
        float viewSize = 200f;
        extendViewport = new ExtendViewport(viewSize, viewSize);
        extendViewport.getCamera().position.set(viewSize / 2f, viewSize / 2f, 1f);

        // Input
        inputUtil = new InputUtil();

        // World
        box2dDebugRenderer = new Box2DDebugRenderer();
        levelUtil = new LevelUtil();
        levelUtil.loadTileMap("maps/debugLevel2/debugLevel2.tmx");

        MapObjects mapObjs = levelUtil.tiledMap.getLayers().get("Collisions").getObjects();
        for (int i = 0; mapObjs.getCount() > i; i++) {
            List<String> keys = new ArrayList<>();
            mapObjs.get(i).getProperties().getKeys().forEachRemaining((key) -> keys.add(key));

            int k = 0;
            Iterator<Object> vals = mapObjs.get(i).getProperties().getValues();
            while (vals.hasNext()) {
                Gdx.app.debug(keys.get(k).toString(), vals.next().toString());
                k = k + 1;
            }

            System.out.println();
        }

        // Player
        player = new Rectangle(
            (levelUtil.getMapWidth() * levelUtil.getTileWidth()) / 2,
            (levelUtil.getMapHeight() * levelUtil.getTileHeight()) / 2,
            16,
            16
        );
        playerSprite = new Sprite(new Texture("sprites/player.png"));
    }

    @Override
    public void show() {}

    private void update() {
        // Update physics
        levelUtil.world.step(1/60f, 6, 2);

        // Move player
        float movementX = inputUtil.moveX();
        player.setPosition(
            player.x + movementX * speed * Gdx.graphics.getDeltaTime(),
            player.y + inputUtil.moveY() * speed * Gdx.graphics.getDeltaTime()
        );
        // Flip the sprite
        if (movementX < 0) {
            playerSprite.setFlip(true, false);
        } else if (movementX > 0) {
            playerSprite.setFlip(false, false);
        }

        // Update camera
        float halfWorldWidth = extendViewport.getWorldWidth() / 2;
        float halfWorldHeight = extendViewport.getWorldHeight() / 2;
        extendViewport.getCamera().position.set(
            Math.min(
                Math.max(
                    player.x,
                    halfWorldWidth
                ),
                (levelUtil.getMapWidth() * levelUtil.getTileWidth()) - halfWorldWidth
            ),
            Math.min(
                Math.max(
                    player.y,
                    halfWorldHeight
                ),
                (levelUtil.getMapHeight() * levelUtil.getTileHeight()) - halfWorldHeight
            ),
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
        
        // Polls input system
        inputUtil.update();

        // Applies the camera
        extendViewport.apply();

        // Run game loop
        this.update();

        // Render
        game.batch.setProjectionMatrix(extendViewport.getCamera().combined);
        levelUtil.render((OrthographicCamera) extendViewport.getCamera());

        // Debug renderer for development!
        box2dDebugRenderer.render(levelUtil.world, extendViewport.getCamera().combined);

        // Draw sprites
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
