package mavenless.ronasurvivors.Utils;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.Serializable;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.Objects;

import org.junit.jupiter.api.AfterAll;

import com.badlogic.gdx.backends.headless.HeadlessApplication;
import com.badlogic.gdx.backends.headless.HeadlessApplicationConfiguration;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;

import mavenless.ronasurvivors.HeadlessApp;

public class SaveUtilTest extends ApplicationAdapter {

    private static HeadlessApplication headlessApplication = null;
    private static String tmpDir = null;

    // libGDX and temp dir setup
    @BeforeAll
    public static void start() {
        System.out.println("Starting SaveUtilTest");

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
        System.out.println("Temp dir is: " + tmpDir);
    }

    // libGDX and temp dir cleanup
    @AfterAll
    public static void end() {
        System.out.println("Ending SaveUtilTest");

        // Remove temp dir
        Assertions.assertTrue(
            Gdx.files.absolute(tmpDir).deleteDirectory(),
            "Couldn't delete the temp dir"
        );

        // Quit libGDX
        Gdx.app.exit();
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

    @Test
    public void testByteRead() {
        // Test data
        byte[] bytes = { 'M', 'E', 'L', 'O', 'N' };
        
        // Get the test file path
        String tmpPath = tmpDir + "testByteRead.test";

        // Check that write works
        try {
            Gdx.files.absolute(tmpPath).writeBytes(bytes, false);
        } catch (GdxRuntimeException e) {
            Assertions.fail("Write Error: " + e.getMessage());
        }

        // Check that the read bytes are correct
        Assertions.assertTrue(
            Arrays.equals(SaveUtil.readBytesFromFile(tmpPath), bytes),
            "The written bytes are not the same as the read ones"
        );
    }

    @Test
    public void testObjectWriteRead() {
        // Test data
        TestData testData = new TestData("Coconut", 537.88256f);
        
        // Get the test file path
        String tmpPath = tmpDir + "testObjectWrite.test";

        // Check that write is successfull
        try {
            SaveUtil.writeObject("JTS", (short) 2, testData, tmpPath);
        } catch (Exception e) {
            Assertions.fail("Write Error: " + e.getMessage());
        }

        // Check that the read bytes are correct
        try {
            TestData readData = (TestData) SaveUtil.readObject("JTS", (short) 2, tmpPath);
            Assertions.assertTrue(readData.equals(testData));
        } catch (Exception e) {
            Assertions.fail("Read Error: " + e.getMessage());
        }
    }

}


class TestData implements Serializable {

    public String name;
    public float value;

    public TestData(String name, float value) {
        this.name = name;
        this.value = value;
    }
    
    @Override
    public boolean equals(Object obj) {
        // Self check
        if (this == obj)
            return true;

        // Null check
        if (obj == null)
            return false;

        // Type check and cast
        if (getClass() != obj.getClass())
            return false;

        TestData testData = (TestData) obj;

        // Field comparison
        return Objects.equals(name, testData.name) && Objects.equals(value, testData.value);
    }

}