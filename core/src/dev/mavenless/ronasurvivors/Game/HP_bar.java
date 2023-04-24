package mavenless.ronasurvivors.Game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

/**
 * Class for displaying and handling a
 * healthpoint bar
 */
public class HP_bar{
    private final int MAX_HEALTH;
    private int health;
    private ProgressBar HpBar;

    /**
     * Constructor for creating a new healthbar
     * @param initHealth - Start and maxhealth
     * @param stage      - stage to display on
     */
    public HP_bar(int initHealth, Stage stage) {
        this.MAX_HEALTH = initHealth;
        this.health = initHealth;
        init_HpBar(stage);
    }

    /* Help method for initializing the hp-bar */
    private void init_HpBar(Stage stage) {
        Skin skin = new Skin(Gdx.files.internal("ui/HP_bar/HP_Bar.json"));
        this.HpBar = new ProgressBar(0, MAX_HEALTH, 1, false, skin);
        this.HpBar.setValue(health);
        this.HpBar.setPosition(stage.getWidth()/2-(this.getWidth()/2), stage.getHeight()-50);
        stage.addActor(HpBar);
    }

    /* -- GETTER and SETTER functions -- */
    /**
     * Setter function for setting health
     * @param h
     */
    public void setHealth(int h) {
        this.health = h;
        HpBar.setValue(h);
    }
    /**
     * Getter function for retrieving current health
     * @return current health
     */
    public int getHealth() {
        return health;
    }
    /**
     * Getter function for retrieving width of the healthpoint bar
     * @return width of healthpoint bar
     */
    public float getWidth() {
        return HpBar.getWidth();
    }

    

}
