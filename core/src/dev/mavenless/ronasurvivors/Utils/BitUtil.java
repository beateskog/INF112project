package mavenless.ronasurvivors.Utils;

import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.stream.IntStream;

/**
 * A instance utility class that helps with
 * converting Java types to bytes and bytes
 * to Java types again.
 * 
 * @see <a href="https://www.w3schools.com/java/java_data_types.asp">Java data types in bytes</a>
 */
public class BitUtil {
    
    private byte[] bytes;

    // Constructors
    /**
     * Takes a byte array
     * 
     * @param bytes the byte array to be stored
     */
    public BitUtil(byte[] bytes) {
        this.bytes = bytes;
    }

    /**
     * Converts a string to an UTF-8
     * encoded byte array
     * 
     * @param string the string to be converted
     */
    public BitUtil(String string) {
        try {
            bytes = string.getBytes("UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    /**
     * Converts a short to a byte array with
     * a set size of 2 bytes
     * 
     * @param value the short to be converted
     * @see <a href="https://www.w3schools.com/java/java_data_types.asp">Java data types in bytes</a>
     */
    public BitUtil(short value) {
        ByteBuffer buffer = ByteBuffer.allocate(2);
        buffer.putShort(value);
        bytes = buffer.array();
    }

    /**
     * Converts an int to a byte array with
     * a set size of 4 bytes
     * 
     * @param value the int to be converted
     */
    public BitUtil(int value) {
        ByteBuffer buffer = ByteBuffer.allocate(4);
        buffer.putInt(value);
        bytes = buffer.array();
    }

    // Getters
    public byte[] getBytes() {
        return bytes;
    }

    // Convertion getters
    /**
     * Converts the stored byte array to a
     * UTF-8 encoded string
     */
    @Override
    public String toString() {
        char[] chars = new char[bytes.length];

        for (int i = 0; i < bytes.length; i++) {
            chars[i] = (char) bytes[i];
        }

        return new String(chars);
    }

    /**
     * Converts the stored byte array to a
     * short if the byte array is 2 bytes
     * long
     * 
     * @return the converted short
     */
    public short toShort() {
        ByteBuffer bb = ByteBuffer.allocate(2);
        bb.order(ByteOrder.BIG_ENDIAN);
        bb.put(bytes[0]);
        bb.put(bytes[1]);
        return bb.getShort(0);
    }

    /**
     * Converts the stored byte array to an
     * int if the byte array is 4 bytes
     * long
     * 
     * @return the converted integer
     */
    public int toInt() {
        ByteBuffer bb = ByteBuffer.allocate(4);
        bb.order(ByteOrder.BIG_ENDIAN);
        bb.put(bytes[0]);
        bb.put(bytes[1]);
        bb.put(bytes[2]);
        bb.put(bytes[3]);
        return bb.getInt(0);
    }

    /**
     * Takes the length of the bytes array
     * and returns it as a Java int in the
     * format for a byte array with the length
     * of 4 bytes.
     * 
     * @return a byte array with 4 bytes
     * @see <a href="https://www.w3schools.com/java/java_data_types.asp">Java types to bytes</a>
     */
    public byte[] toLengthBytes() {
        ByteBuffer buffer = ByteBuffer.allocate(4);
        buffer.putInt(bytes.length);
        return buffer.array();
    }

    /**
     * Converts the byte array to a string
     * representation in the hex format
     * 
     * @return a hexadecimal string
     */
    public String toHex() {
        return IntStream.range(0, bytes.length)
            .mapToObj(i -> String.format("%02x", bytes[i]))
            .reduce((acc, v) -> acc + " " + v)
            .get();
    }

}
