package dev.krirogn.ronasurvivors.Game;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.zip.DataFormatException;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.ByteArray;

import dev.krirogn.ronasurvivors.Utils.BitUtil;
import dev.krirogn.ronasurvivors.Utils.SaveUtil;

public class Save {

    public String saveLocation;

    private String saveLocationSaves;

    // Header
    private BitUtil signature;
    private BitUtil version;
    private BitUtil hash;

    // Data
    private BitUtil name;

    public Save() {
        // Get the save location
        saveLocation = SaveUtil.getDefaultSavePath();

        // Check if the save location dir exists
        FileHandle saveLocationHandle = Gdx.files.absolute(saveLocation);
        if (!saveLocationHandle.exists()) saveLocationHandle.mkdirs();

        saveLocationSaves = saveLocation + "saves/";
        FileHandle saveLocationSavesHandle = Gdx.files.absolute(saveLocationSaves);
        if (!saveLocationSavesHandle.exists()) saveLocationSavesHandle.mkdirs();

        // Init test data
        signature = new BitUtil("SAV");
        version = new BitUtil((short) 2);
        name = new BitUtil("Cathy");
    }

    // File utilities
    public void write() {
        ByteArray file = new ByteArray();

        ByteArray header = new ByteArray();
        ByteArray data = new ByteArray();

        // Add header data
        header.addAll(signature.getBytes()); // 8 * 3
        header.addAll(version.getBytes()); // 8 * 2

        // Add data
        data.addAll(name.toLengthBytes()); // 8 * 4
        data.addAll(name.getBytes()); // 8 * length

        // Compress data
        byte[] compressedData = new byte[data.items.length];
        Deflater compresser = new Deflater();
        compresser.setInput(data.toArray());
        compresser.finish();
        int compressedDataLength = compresser.deflate(compressedData);
        compresser.end();

        header.addAll(new BitUtil(compressedDataLength).getBytes());

        // Add to file
        file.addAll(header);
        file.addAll(compressedData);

        SaveUtil.writeBytesToFile(saveLocationSaves + "4.sv", file.toArray());
    }

    public void read() {
        byte[] bytes = SaveUtil.readBytesFromFile(saveLocationSaves + "4.sv");

        // Get header signature
        String signature = new BitUtil(Arrays.copyOfRange(bytes, 0, 3)).toString(); // B24

        // Get header version
        short version = new BitUtil(Arrays.copyOfRange(bytes, 3, 5)).toShort(); //(short) bytes[3]; // B8

        // Get header data length
        int dataLength = new BitUtil(Arrays.copyOfRange(bytes, 5, 9)).toInt();

        // Decompress data
        byte[] data = new byte[dataLength];
        Inflater decompresser = new Inflater();
        decompresser.setInput(Arrays.copyOfRange(bytes, 9, bytes.length), 0, dataLength);
        try {
            decompresser.inflate(data);
        } catch (DataFormatException e) {
            e.printStackTrace();
        }
        decompresser.end();

        // Get name
        int nameLength = new BitUtil(Arrays.copyOfRange(data, 0, 4)).toInt(); //new B16(bytes[5], bytes[9]).toShort(); // B16 / short
        String name = new String(Arrays.copyOfRange(data, 4, nameLength + 4), StandardCharsets.UTF_8);

        System.out.println(signature + " | " + version + " | " + name);
    }

    // Getters

}