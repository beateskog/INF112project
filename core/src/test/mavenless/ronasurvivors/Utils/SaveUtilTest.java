package mavenless.ronasurvivors.Utils;

import java.io.IOException;
import java.io.Serializable;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.Objects;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterAll;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.badlogic.gdx.Gdx;

import mavenless.ronasurvivors.HeadlessTest;

public class SaveUtilTest extends HeadlessTest {

    private static String tmpDir = null;

    /**
     * A setup function that handles libGDX
     * and creates a temporary directory
     * for the test
     */
    @BeforeAll
    public static void start() {
        System.out.println("Starting SaveUtilTest");

        // Get temp directory
        try {
            tmpDir = Files.createTempDirectory("rona-survivors").toFile().getAbsolutePath() + "/";
        } catch (IOException e) {
            System.out.println(e.getMessage());
            Assertions.fail("Couldn't make a temp dir");
            return;
        }
        Assertions.assertNotNull(tmpDir);
        Assertions.assertNotEquals(tmpDir, "");
    }

    /**
     * A cleanup function that handles libGDX
     * and removes the temporary directory
     */
    @AfterAll
    public static void end() {
        System.out.println("Ending SaveUtilTest");

        // Remove temp dir
        Assertions.assertTrue(
            Gdx.files.absolute(tmpDir).deleteDirectory(),
            "Couldn't delete the temp dir"
        );
    }

    /**
     * A function that tests wether the SaveUtil
     * can correctly write some data to a save file
     */
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

    /**
     * A function that tests wether the SaveUtil
     * can correctly read some data to a save file
     */
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

    /**
     * A function that tests wether the SaveUtil
     * can correctly write and read an object
     * to a save file
     */
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

    /**
     * A function that tests wether the SaveUtil
     * will return a default save path by asserting
     * that it does return something
     */
    @Test
    public void testDefaultSavePath() {
        // Check that getDefaultSavePath() returns a value
        Assertions.assertNotNull(SaveUtil.getDefaultSavePath());
        Assertions.assertNotEquals(SaveUtil.getDefaultSavePath(), "");
    }

}


/**
 * A test class of an object SaveUtil
 * can write to a file
 */
class TestData implements Serializable {

    public String name;
    public float value;

    /**
     * The constructor
     * 
     * @param name the string test data name
     * @param value the float test data value
     */
    public TestData(String name, float value) {
        this.name = name;
        this.value = value;
    }
    
    /**
     * A custom equals function to correctly
     * assert wether SaveUtil read the object
     * correctly from file
     */
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