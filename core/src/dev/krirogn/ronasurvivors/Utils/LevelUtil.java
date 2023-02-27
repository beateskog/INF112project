package dev.krirogn.ronasurvivors.Utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapLayers;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;

public class LevelUtil {
    
    /**
     * The tile map object
     */
    public TiledMap tiledMap;
    /**
     * The Box2D world, witch is
     * the object you add the player to,
     * as well as enemies and colliders
     */
    public World world;
    /**
     * The tile map renderer
     */
    public OrthogonalTiledMapRenderer mapRenderer;
    
    private int tileWidth;
    private int tileHeight;
    private int mapWidth;
    private int mapHeight;
    
    /**
     * Loads a tile map from a local asset file
     * 
     * @param tilePath the path to the
     * *.tmx file.
     * <p>
     * "assets/" is the path root
     * @see <a href="https://libgdx.com/wiki/graphics/2d/tile-maps">Tile Maps libGDX docs</a>
     * @see <a href="https://www.mapeditor.org/">Tiled Website</a>
     */
    public void loadTileMap(String tilePath) {
        tiledMap = new TmxMapLoader().load(tilePath);

        MapProperties props = tiledMap.getProperties();
        tileWidth = (int) props.get("tilewidth");
        tileHeight = (int) props.get("tileheight");
        mapWidth = (int) props.get("width");
        mapHeight = (int) props.get("height");

        world = new World(new Vector2(0,0), true);
        mapRenderer = new OrthogonalTiledMapRenderer(tiledMap);
    }

    /**
     * Renders the world with
     * the tile map
     * 
     * @param camera
     */
    public void render(OrthographicCamera camera) {
        mapRenderer.setView(camera);
        mapRenderer.render();
    }

    /**
     * Prints the layers to console.
     * <p>
     * THIS IS ONLY FOR DEBUGGING
     * AND DEVELOPMENT!!!
     */
    public void getLayers() {
        MapLayers ml = tiledMap.getLayers();
        
        for (MapLayer layer : ml)
            Gdx.app.debug("Tiles", layer.getName());
    }

    /**
     * Gives the tile map as an
     * OrthogonalTiledMapRenderer
     * 
     * @return and OrthogonalTiledMapRenderer
     */
    public OrthogonalTiledMapRenderer getTileRenderer() {
        return mapRenderer;
    }

    // Getters
    /**
     * @return the width of a tile
     */
    public int getTileWidth() {
        return this.tileWidth;
    }

    /**
     * @return the height of a tile
     */
    public int getTileHeight() {
        return this.tileHeight;
    }

    /**
     * @return how many tiles there are horizontally
     */
    public int getMapWidth() {
        return this.mapWidth;
    }

    /**
     * @return how many tiles there are vertically
     */
    public int getMapHeight() {
        return this.mapHeight;
    }

}
