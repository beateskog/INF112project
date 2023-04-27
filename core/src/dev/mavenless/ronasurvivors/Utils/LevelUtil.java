package mavenless.ronasurvivors.Utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapLayers;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;

import mavenless.ronasurvivors.Game.CollisionBits;


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
        World.setVelocityThreshold(0f);
        mapRenderer = new OrthogonalTiledMapRenderer(tiledMap);

        // Get colliders
        MapObjects mapObjs = tiledMap.getLayers().get("Collisions").getObjects();
        for (int i = 0; mapObjs.getCount() > i; i++) {
            HashMap<String, Float> prop = new HashMap<>();

            List<String> keys = new ArrayList<>();
            mapObjs.get(i).getProperties().getKeys().forEachRemaining((key) -> keys.add(key));

            int k = 0;
            Iterator<Object> vals = mapObjs.get(i).getProperties().getValues();
            while (vals.hasNext()) {
                prop.put(keys.get(k).toString(), Float.parseFloat(vals.next().toString()));
                k = k + 1;
            }

            // Create colliders
            BodyDef groundBodyDef = new BodyDef();  
            groundBodyDef.type = BodyType.StaticBody;
            groundBodyDef.position.set(
                new Vector2(
                    prop.get("x"),
                    prop.get("y")
                )
            );  

            Body groundBody = world.createBody(groundBodyDef);  

            PolygonShape groundBox = new PolygonShape();
            groundBox.setAsBox(
                prop.get("width") / 2,
                prop.get("height") / 2,
                new Vector2(
                    prop.get("width") / 2,
                    prop.get("height") / 2
                ),
                0
            );

            FixtureDef fixtureDef = new FixtureDef();
            fixtureDef.shape = groundBox;                     //Setting the shape of the fixture to our box
            fixtureDef.density = 0.0f;
            fixtureDef.filter.categoryBits = CollisionBits.CATEGORY_SCENERY;
            fixtureDef.filter.maskBits = CollisionBits.MASK_SCENERY; 


            groundBody.createFixture(fixtureDef);
            groundBox.dispose();
        }
    }

    /**
     * Calcualtes a random location on the edges of the map.  
     * @return a vector2 with the x and y posistion
     */
    public Vector2 getRandomLocation(){
        int x_bound = getMapWidth() * getTileWidth()-30;
        int y_bound = getMapHeight() * getTileHeight()-30;
        Random rand = new Random();
        int[] x_posistions = rand.ints(40, x_bound/16, x_bound).toArray();
        int[] y_posistions = rand.ints(40, y_bound/16, y_bound).toArray();

        int x = (int) rand.nextInt(x_posistions.length);
        int y = (int) rand.nextInt(y_posistions.length);
        
        return new Vector2(x_posistions[x], y_posistions[y]);
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
