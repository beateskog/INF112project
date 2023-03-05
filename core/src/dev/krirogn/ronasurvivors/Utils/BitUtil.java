package dev.krirogn.ronasurvivors.Utils;

import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.stream.IntStream;

public class BitUtil {
    
    private byte[] bytes;

    // Constructors
    public BitUtil(byte[] bytes) {
        this.bytes = bytes;
    }

    public BitUtil(String string) {
        try {
            bytes = string.getBytes("UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    public BitUtil(short value) {
        ByteBuffer buffer = ByteBuffer.allocate(2);
        buffer.putShort(value);
        bytes = buffer.array();
    }

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
    @Override
    public String toString() {
        char[] chars = new char[bytes.length];

        for (int i = 0; i < bytes.length; i++) {
            chars[i] = (char) bytes[i];
        }

        return new String(chars);
    }

    public short toShort() {
        ByteBuffer bb = ByteBuffer.allocate(2);
        bb.order(ByteOrder.BIG_ENDIAN);
        bb.put(bytes[0]);
        bb.put(bytes[1]);
        return bb.getShort(0);
    }

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
        System.out.println("toLengthBytes " + buffer.array().length);
        return buffer.array();
    }

    public String toHex() {
        return IntStream.range(0, bytes.length)
            .mapToObj(i -> String.format("%02x", bytes[i]))
            .reduce((acc, v) -> acc + " " + v)
            .get();
    }

}
