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

        BitUtil bitUtil = new BitUtil(bytes);

        Assertions.assertEquals(hex, bitUtil.toHex());
    }

}
