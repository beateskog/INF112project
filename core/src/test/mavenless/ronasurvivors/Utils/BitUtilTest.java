package mavenless.ronasurvivors.Utils;

import java.util.Arrays;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class BitUtilTest {
    
    /**
     * A function to test wether BitUtils
     * constructor correctly stores bytes
     */
    @Test
    public void testByteConstructor() {
        // Test data
        byte[] bytes = { 'T', 'O', 'A', 'S', 'T' };

        BitUtil bitUtil = new BitUtil(bytes);

        Assertions.assertTrue(Arrays.equals(bytes, bitUtil.getBytes()));
    }

    /**
     * A function to test wether BitUils
     * correctly handles strings
     */
    @Test
    public void testStringConstructor() {
        // Test data
        String data = "Hello, World!";

        BitUtil bitUtil = new BitUtil(data);

        Assertions.assertEquals(data, bitUtil.toString());
    }

    /**
     * A function to test wether BitUtil
     * correctly handles short numbers
     */
    @Test
    public void testShortConstructor() {
        // Test data
        short data = 03125;

        BitUtil bitUtil = new BitUtil(data);

        Assertions.assertEquals(data, bitUtil.toShort());
    }

    /**
     * A function to test wether BitUtil
     * correctly handles integers
     */
    @Test
    public void testIntConstructor() {
        // Test data
        int data = 03122005;

        BitUtil bitUtil = new BitUtil(data);

        Assertions.assertEquals(data, bitUtil.toInt());
    }

    /**
     * A function to test wether BitUtil
     * correctly converts bytes to their
     * hexadesimal string representation
     */
    @Test
    public void testHexConvertor() {
        // Test data
        byte[] bytes = { 'T', 'O', 'A', 'S', 'T' };
        String hex = "54 4f 41 53 54";

        Assertions.assertEquals(hex, new BitUtil(bytes).toHex());
    }

    /**
     * A function to test wether BitUtil
     * returns the correct length of bytes
     * in the format of a 4 byte array
     */
    @Test
    public void testLengthBytes() {
        // Test data
        String string = "Appreciate";
        int integer = 33;
        short number = 8;
        byte[] bytes = { 0x55, 0x76, 0x55, 0x34, 0x7f };

        Assertions.assertEquals(
            new BitUtil(new BitUtil(string).toLengthBytes()).toHex(),
            "00 00 00 0a" // 10
        );
        Assertions.assertEquals(
            new BitUtil(new BitUtil(integer).toLengthBytes()).toHex(),
            "00 00 00 04" // 4
        );
        Assertions.assertEquals(
            new BitUtil(new BitUtil(number).toLengthBytes()).toHex(),
            "00 00 00 02" // 2
        );
        Assertions.assertEquals(
            new BitUtil(new BitUtil(bytes).toLengthBytes()).toHex(),
            "00 00 00 05" // 5
        );
    }

}
