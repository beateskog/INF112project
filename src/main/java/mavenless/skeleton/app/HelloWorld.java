package mavenless.skeleton.app;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;

public class HelloWorld implements ApplicationListener {
    public static final String FONT_CHARACTERS = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789][_!$%#@|\\/?-+=()*&.;,{}\"´`'<>";

    private SpriteBatch batch;
    private BitmapFont font;
    private Sprite sprite;

    @Override
    public void create() {
        batch = new SpriteBatch();

        // FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("mavenless/skeleton/assets/fonts/OpenSans/OpenSansRegular.ttf"));
        // FreeTypeFontParameter parameter = new FreeTypeFontParameter();
        // parameter.size = 30;
        // font = generator.generateFont(parameter);
        // font.setColor(Color.BLACK);

        font = new BitmapFont();
        font.setColor(Color.RED);

        Texture texture = new Texture(Gdx.files.internal("mavenless/skeleton/assets/sprites/enemies/corona.png"));
        sprite = new Sprite(texture, 0, 0, 32, 32);
        sprite.setPosition(400, 100);
        sprite.setRotation(0);
        sprite.setSize(400, 400);
    }

    @Override
    public void dispose() {
        batch.dispose();
        font.dispose();
    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(0, 1, 1, 1);
        Gdx.gl.glClear(GL30.GL_COLOR_BUFFER_BIT);

        batch.begin();
        font.draw(batch, "Hello World", 200, 200);

        sprite.draw(batch);

        batch.end();
    }

    @Override
    public void resize(int width, int height) {
        System.out.println("The program is resized: " + width + " | " + height);
    }

    @Override
    public void pause() {
        System.out.println("The program is paused");
    }

    @Override
    public void resume() {
        System.out.println("The program is resumed");
    }
}
