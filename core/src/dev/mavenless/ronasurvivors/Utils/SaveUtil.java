package mavenless.ronasurvivors.Utils;

import java.io.Serializable;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.zip.DataFormatException;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

import org.apache.commons.lang3.SerializationUtils;
import org.apache.commons.lang3.SystemUtils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.ByteArray;
import com.badlogic.gdx.utils.GdxRuntimeException;

/**
 * A static utillity class that gives methods
 * to read and write to files, as well as to
 * get general filesystem information.
 */
public class SaveUtil {
    
    /**
     * Writes a byte array to a file
     * 
     * @param bytes the byte array to be written
     * @param path the absolute path to the file
     * @return true if file write was successfull
     */
    public static boolean writeBytesToFile(String path, byte[] bytes) {
        try {
            Gdx.files.absolute(path).writeBytes(bytes, false);
        } catch (GdxRuntimeException e) {
            Gdx.app.debug("Save Error", e.getMessage());
            return false;
        }

        return true;
    }

    /**
     * Reads a byte array from a file
     * 
     * @param path the absolute path to the file
     * @return byte array from a file
     */
    public static byte[] readBytesFromFile(String path) {
        try {
            return Gdx.files.absolute(path).readBytes();
        } catch (GdxRuntimeException e) {
            Gdx.app.debug("Save Error", e.getMessage());
            return null;
        }
    }

    /**
     * Writes a {@link java.io.Serializable} Object to a custom byte
     * format file with an appropriate file header, and
     * compressed data by serializing the object to bytes. 
     * <p>
     * Uses <a href="https://www.baeldung.com/java-md5">MD5</a> for hashing.
     * 
     * @param signature a file signature with exactly <b>3</b> characters
     * @param version the version of the file. Should be changed
     * when the Object type is changed
     * @param object the {@link java.io.Serializable} Object to be written
     * @param saveLocation absolute path to the the save file
     * @throws Exception for errors that should panic the program
     */
    public static void writeObject(String signature, short version, Object object, String saveLocation) throws Exception {
        // Signature check
        if (signature.length() != 3) {
            throw new Exception("Signature must be 3 characters long!");
        }

        ByteArray file = new ByteArray();

        ByteArray header = new ByteArray();
        ByteArray data = new ByteArray();

        // Add header data
        header.addAll(new BitUtil(signature).getBytes()); // 8 * 3
        header.addAll(new BitUtil(version).getBytes()); // 8 * 2

        // Add data
        data.addAll(SerializationUtils.serialize((Serializable) object));

        // Compress data
        byte[] rawCompressedData = new byte[data.items.length * 2]; // Compressed data can be bigger
        Deflater compresser = new Deflater();
        compresser.setInput(data.toArray());
        compresser.finish();
        int compressedDataLength = compresser.deflate(rawCompressedData);
        compresser.end();

        // Trim compressed data
        byte[] compressedData = Arrays.copyOfRange(rawCompressedData, 0, compressedDataLength);

        // Add data hash to header
        MessageDigest md;
        try {
            md = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            throw new Exception("Couldn't generate a hash");
        }
        md.update(compressedData);
        BitUtil hash = new BitUtil(md.digest());

        header.addAll(hash.toLengthBytes());
        header.addAll(hash.getBytes());

        // Add data length to header
        header.addAll(new BitUtil(data.items.length).getBytes());

        // Add to file
        file.addAll(header);
        file.addAll(compressedData);

        SaveUtil.writeBytesToFile(saveLocation, file.toArray());
    }

    /**
     * Retrieves a {@link java.io.Serializable} Object from a custom
     * byte format file.
     * 
     * @param signature a file signature with exactly <b>3</b> characters
     * @param version the version of the file
     * @param saveLocation absolute path to the the save file
     * @return the decompressed and deserialized Object that can then
     * be cast to your serializable Object
     * @throws Exception for errors that should panic the program
     */
    public static Object readObject(String signature, short version, String saveLocation) throws Exception {
        // Signature check
        if (signature.length() != 3) {
            throw new Exception("Signature must be 3 characters long!");
        }

        byte[] bytes = SaveUtil.readBytesFromFile(saveLocation);

        // Get header signature
        String fileSignature = new BitUtil(Arrays.copyOfRange(bytes, 0, 3)).toString();
        if (!signature.equals(fileSignature)) {
            throw new Exception("Wrong file signature");
        }

        // Get header version
        short fileVersion = new BitUtil(Arrays.copyOfRange(bytes, 3, 5)).toShort();
        if (version != fileVersion) {
            throw new Exception("Wrong file version");
        }

        // Get hash length
        int hashLength = new BitUtil(Arrays.copyOfRange(bytes, 5, 9)).toInt();

        // Get hash
        byte[] hash = Arrays.copyOfRange(bytes, 9, hashLength + 9);

        // Get header data length
        int dataLength = new BitUtil(Arrays.copyOfRange(bytes, hashLength + 9, hashLength + 13)).toInt();
        
        // Get the zLIB compressed data
        byte[] compressedData = Arrays.copyOfRange(bytes, hashLength + 13, bytes.length);
        
        // Chech hash
        MessageDigest md;
        try {
            md = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            throw new Exception("Couldn't generate a hash");
        }
        md.update(compressedData);
        BitUtil newHash = new BitUtil(md.digest());

        if (!Arrays.equals(hash, newHash.getBytes())) {
            throw new Exception("The file is corrupt!");
        }
        
        // Decompress data
        byte[] data = new byte[dataLength];
        Inflater decompresser = new Inflater();
        decompresser.setInput(compressedData, 0, compressedData.length);
        try {
            decompresser.inflate(data);
        } catch (DataFormatException e) {
            e.printStackTrace();
            throw new Exception("Couldn't decompress data");
        }
        decompresser.end();

        // Get save data
        return SerializationUtils.deserialize(data);
    }

    /**
     * Gets the default save location of the running OS.
     * <p>
     * <b>P.S. This function will exit the program if
     * it's run on an unsupported OS like BSD!</b>
     * 
     * @return absolute path of default save data
     * location with trailing slash
     */
    public static String getDefaultSavePath() {
        String path = "";

        if (SystemUtils.IS_OS_MAC) {
            path = SystemUtils.USER_HOME;
            path += "/Library/Application Support/Rona Survivors/";
        } else if (SystemUtils.IS_OS_LINUX) {
            path = SystemUtils.USER_HOME;
            path += "/.rona-survivors/";
        } else if (SystemUtils.IS_OS_WINDOWS) {
            path = System.getenv("APPDATA");
            path += "\\.rona-survivors\\";
        } else {
            System.out.println("Your OS is unsupported!");
            Gdx.app.exit();
        }

        return path;
    }

}
