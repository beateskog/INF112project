package mavenless.ronasurvivors;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.mockito.Mockito;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.headless.HeadlessApplication;
import com.badlogic.gdx.graphics.GL20;

/**
 * @see <a href="https://github.com/eskalon/pancake/blob/8817f3800391a89d15c4b657d565df22bd68b119/core/src/test/java/de/eskalon/commons/LibgdxUnitTest.java">
 * Source</a>
 */
public abstract class HeadlessTest {

    private static Application application;

    /**
     * A setup function that sets up
     * libGDX for test and creates a
     * mock instance of the rendering
     * context
     */
    @BeforeAll
    public static void init() {
        application = new HeadlessApplication(new ApplicationAdapter() {});

        // Mocks GL since headless can't create
        // an actual graphical context
        Gdx.gl20 = Mockito.mock(GL20.class);
        Gdx.gl = Gdx.gl20;
    }

    /**
     * A cleanup function that exits
     * the libGDX instance
     */
    @AfterAll
    public static void cleanUp() {
        application.exit();
        application = null;
    }

}
