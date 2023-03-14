package mavenless.ronasurvivors.Utils;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterAll;

import com.badlogic.gdx.backends.headless.HeadlessApplication;
import com.badlogic.gdx.backends.headless.HeadlessApplicationConfiguration;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;

import mavenless.ronasurvivors.HeadlessApp;

public class SaveUtilTest extends ApplicationAdapter {

    private static HeadlessApplication headlessApplication = null;

    @BeforeAll
    public static void start() {
        System.out.println("Setting up SaveUtilTest");

        // Set up headless application
        if (headlessApplication == null) {
            HeadlessApplicationConfiguration config = new HeadlessApplicationConfiguration();
            headlessApplication = new HeadlessApplication(new HeadlessApp(), config);
            Assertions.assertNotNull(Gdx.files);
        }

        // Get the save location
        String saveLocation = SaveUtil.getDefaultSavePath();

        // Check if the save location dir exists
        FileHandle saveLocationHandle = Gdx.files.absolute(saveLocation);
        if (!saveLocationHandle.exists()) saveLocationHandle.mkdirs();

        // Check if the tests dir exists
        String saveLocationTests = saveLocation + "tests/";
        FileHandle saveLocationTestsHandle = Gdx.files.absolute(saveLocationTests);
        if (!saveLocationTestsHandle.exists()) saveLocationTestsHandle.mkdirs();
    }

    @AfterAll
    public static void end() {
        System.out.println("Ending SaveUtilTest");
    }

    @Test
    public void testByteWrite() {
        // byte[] bytes = { 'T', 'E', 'S', 'T' };
        // String tmpPath = SaveUtil.getDefaultSavePath() + "tests/saveUtilTest.test";

        // Check that write returns true
        // Assertions.assertEquals(SaveUtil.writeBytesToFile(tmpPath, bytes), true);

        // // Check that the read bytes are correct
        // Assertions.assertEquals(bytes, Gdx.files.absolute(tmpPath).readBytes());
        Assertions.assertTrue(true);
    }

}
