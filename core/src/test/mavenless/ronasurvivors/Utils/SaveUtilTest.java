package mavenless.ronasurvivors.Utils;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.util.Arrays;

import org.junit.jupiter.api.AfterAll;

import com.badlogic.gdx.backends.headless.HeadlessApplication;
import com.badlogic.gdx.backends.headless.HeadlessApplicationConfiguration;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;

import mavenless.ronasurvivors.HeadlessApp;

public class SaveUtilTest extends ApplicationAdapter {

    private static HeadlessApplication headlessApplication = null;

    private static String tmpDir = null;

    @BeforeAll
    public static void start() {
        System.out.println("Setting up SaveUtilTest");

        // Set up headless application
        if (headlessApplication == null) {
            HeadlessApplicationConfiguration config = new HeadlessApplicationConfiguration();
            headlessApplication = new HeadlessApplication(new HeadlessApp(), config);
            Assertions.assertNotNull(Gdx.files);
        }

        // Get temp directory
        try {
            tmpDir = Files.createTempDirectory("rona-survivors").toFile().getAbsolutePath() + "/";
        } catch (IOException e) {
            System.out.println(e.getMessage());
            Assertions.fail("Couldn't make a temp dir");
            return;
        }
        System.out.println(tmpDir);
    }

    @AfterAll
    public static void end() {
        System.out.println("Ending SaveUtilTest");

        // Remove temp dir
        Gdx.files.absolute(tmpDir).deleteDirectory();
    }

    @Test
    public void testByteWrite() {
        // Test data
        byte[] bytes = { 'T', 'E', 'S', 'T' };
        
        // Get the test file path
        String tmpPath = tmpDir + "testByteWrite.test";

        // Check that write returns true
        Assertions.assertEquals(
            SaveUtil.writeBytesToFile(tmpPath, bytes),
            true,
            "The file couldn't be written"
        );

        // Check that the read bytes are correct
        Assertions.assertTrue(
            Arrays.equals(Gdx.files.absolute(tmpPath).readBytes(), bytes),
            "The written bytes are not the same as the read ones"
        );
    }

}
