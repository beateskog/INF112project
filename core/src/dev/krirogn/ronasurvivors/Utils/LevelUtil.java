package dev.krirogn.ronasurvivors.Utils;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;

public class LevelUtil {
    
    private TiledMap tiledMap;

    public OrthogonalTiledMapRenderer setupMap(String mapName){
        tiledMap = new TmxMapLoader().load(mapName);
        return new OrthogonalTiledMapRenderer(tiledMap);
    }

    
    /**
     * Loads a tile map from a local asset file
     * 
     * @param tilePath the path to the
     * *.tmx file.
     * <p>
     * "assets/" is the path root
     * @return a TiledMap object from the file
     * @see <a href="https://libgdx.com/wiki/graphics/2d/tile-maps">Tile Maps libGDX docs</a>
     * @see <a href="https://www.mapeditor.org/">Tiled Website</a>
     */
    /*
    public static TiledMap LoadTileMap(String tilePath) {
        return new TmxMapLoader().load(tilePath);
    }
    */
}
