package dev.krirogn.ronasurvivors.Utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapLayers;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;

public class LevelUtil {
    
    private TiledMap tiledMap;
    
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
    public void LoadTileMap(String tilePath) {
        tiledMap = new TmxMapLoader().load(tilePath);
    }

    public void GetLayers() {
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
    public OrthogonalTiledMapRenderer GetTileRenderer() {
        return new OrthogonalTiledMapRenderer(tiledMap);
    }
}
