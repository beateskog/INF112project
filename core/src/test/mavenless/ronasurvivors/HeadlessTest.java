package mavenless.ronasurvivors;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.mockito.Mockito;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.headless.HeadlessApplication;
import com.badlogic.gdx.graphics.GL20;

public abstract class HeadlessTest {

    private static Application application;

    @BeforeAll
    public static void init() {
        application = new HeadlessApplication(new ApplicationAdapter() {});

        Gdx.gl20 = Mockito.mock(GL20.class);
        Gdx.gl = Gdx.gl20;
    }

    @AfterAll
    public static void cleanUp() {
        application.exit();
        application = null;
    }

}
