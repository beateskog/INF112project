package mavenless.ronasurvivors.Utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;

/**
 * A UI helper class that gives static enums with
 * pre-defined styles and fonts, and functions that
 * help generate styles
 */
public class UiHandler {

    /**
     * An enum that gives pre-defined libGDX skins
     */
    public enum UiStyle {
        Debug {
            public Skin getSkin() {
                return new Skin(Gdx.files.internal("ui/debug/uiskin.json"));
            }

            public Drawable getButtonUp() {
                return this.getSkin().getDrawable("default-round");
            }

            public Drawable getButtonDown() {
                return this.getSkin().getDrawable("default-round-down");
            }
        };

        /**
         * Gives the libGDX skin from the pre-defined UiStyle
         * 
         * @return a libGDX skin
         */
        public abstract Skin getSkin();

        /**
         * Gives the drawable that TextButtonStyle uses
         * to render the up position of the text button.
         * 
         * @return drawable to be given to a TextButtonStyle
         */
        public abstract Drawable getButtonUp();

        /**
         * Gives the drawable that TextButtonStyle uses
         * to render the down position of the text button.
         * 
         * @return drawable to be given to a TextButtonStyle
         */
        public abstract Drawable getButtonDown();
    }

    private static BitmapFont loadFont(String path, int size, Color color) {
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal(path));
        FreeTypeFontParameter parameter = new FreeTypeFontParameter();

        parameter.size = size;
        parameter.color = color;

        BitmapFont font = generator.generateFont(parameter);
        generator.dispose();

        return font;
    }

    private static BitmapFont loadFontWithBorder(String path, int size, Color color, Color borderColor,
            int borderSize) {
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal(path));
        FreeTypeFontParameter parameter = new FreeTypeFontParameter();

        parameter.size = size;
        parameter.color = color;
        parameter.borderColor = borderColor;
        parameter.borderWidth = borderSize;

        BitmapFont font = generator.generateFont(parameter);
        generator.dispose();

        return font;
    }

    /**
     * An enum that gives pre-defined fonts
     */
    public enum UiFont {
        /**
         * A pixelated retro styled font
         * <p>
         * The font has a free
         * <a href="https://typodermicfonts.com/proportional-joystix/">license</a>
         * for desktop use, but
         * not embedding, so this font <i>CANNOT</i> be shipped with the game itself!
         *  
         * @see <a href="https://www.1001fonts.com/joystix-font.html">Source</a>
         */
        Pixelated {
            public BitmapFont getFont(int size, Color color) {
                return loadFont("fonts/joystix.ttf", size, color);
            }

            public BitmapFont getFontBorder(int size, Color color, Color borderColor, int borderSize) {
                return loadFontWithBorder("fonts/joystix.ttf", size, color, borderColor, borderSize);
            }
        };

        /**
         * Generates a BitmapFont from pre-defined fonts
         * 
         * @param size  the size of the font
         * @param color the color of the font
         * @return the BitmapFont that libGDX uses
         */
        public abstract BitmapFont getFont(int size, Color color);

        /**
         * Generates a BitmapFont with an outline from pre-defined fonts
         * 
         * @param size        the size of the font
         * @param color       the color of the font
         * @param borderColor the color of the border outline
         * @param borderSize  the thickness of the border outline
         * @return the BitmapFont that libGDX uses
         */
        public abstract BitmapFont getFontBorder(int size, Color color, Color borderColor, int borderSize);
    }

    /**
     * Generates a LabelStyle with a specified
     * pre-defined font
     * 
     * @param font the generated font from UiFont
     * @return a LabelStyle with the font
     */
    public static LabelStyle labelStyle(BitmapFont font) {
        LabelStyle labelStyle = new LabelStyle();
        labelStyle.font = font;

        return labelStyle;
    }

    /**
     * Generates a TextButtonStyle with a specified
     * pre-defined font and style
     * 
     * @param style the UiStyle enum
     * @param font  the generated font fra UiFont
     * @return a TextButtonStyle with the style and font
     */
    public static TextButtonStyle textButtonStyle(UiStyle style, BitmapFont font) {
        TextButtonStyle textButtonStyle = new TextButtonStyle();
        textButtonStyle.font = font;
        textButtonStyle.up = style.getButtonUp();
        textButtonStyle.down = style.getButtonDown();

        return textButtonStyle;
    }

}