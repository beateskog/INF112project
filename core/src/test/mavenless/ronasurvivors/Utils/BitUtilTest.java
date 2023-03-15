package mavenless.ronasurvivors.Utils;

import java.util.Arrays;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class BitUtilTest {
    
    @Test
    public void testByteConstructor() {
        // Test data
        byte[] bytes = { 'T', 'O', 'A', 'S', 'T' };

        BitUtil bitUtil = new BitUtil(bytes);

        Assertions.assertTrue(Arrays.equals(bytes, bitUtil.getBytes()));
    }

    @Test
    public void testStringConstructor() {
        // Test data
        String data = "Hello, World!";

        BitUtil bitUtil = new BitUtil(data);

        Assertions.assertEquals(data, bitUtil.toString());
    }

    @Test
    public void testShortConstructor() {
        // Test data
        short data = 03125;

        BitUtil bitUtil = new BitUtil(data);

        Assertions.assertEquals(data, bitUtil.toShort());
    }

    @Test
    public void testIntConstructor() {
        // Test data
        int data = 03122005;

        BitUtil bitUtil = new BitUtil(data);

        Assertions.assertEquals(data, bitUtil.toInt());
    }

    @Test
    public void testHexConvertor() {
        // Test data
        byte[] bytes = { 'T', 'O', 'A', 'S', 'T' };
        String hex = "54 4f 41 53 54";

        Assertions.assertEquals(hex, new BitUtil(bytes).toHex());
    }

    @Test
    public void testLengthBytes() {
        // Test data
        String string = "Appreciate";
        int integer = 33;
        short number = 8;
        byte[] bytes = { 0x55, 0x76, 0x55, 0x34, 0x7f };

        Assertions.assertEquals(
            new BitUtil(new BitUtil(string).toLengthBytes()).toHex(),
            "00 00 00 0a"
        );
        Assertions.assertEquals(
            new BitUtil(new BitUtil(integer).toLengthBytes()).toHex(),
            "00 00 00 04"
        );
        Assertions.assertEquals(
            new BitUtil(new BitUtil(number).toLengthBytes()).toHex(),
            "00 00 00 02"
        );
        Assertions.assertEquals(
            new BitUtil(new BitUtil(bytes).toLengthBytes()).toHex(),
            "00 00 00 05"
        );
    }

}
