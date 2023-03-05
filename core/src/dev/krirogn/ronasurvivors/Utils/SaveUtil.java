package dev.krirogn.ronasurvivors.Utils;

import org.apache.commons.lang3.SystemUtils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.GdxRuntimeException;

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

    public static String getDefaultSavePath() {
        String path = "";

        if (SystemUtils.IS_OS_MAC) {
            System.out.println("OS is Mac");
            path = SystemUtils.USER_HOME;
            path += "/Library/Application Support/Rona Survivors/";
        } else if (SystemUtils.IS_OS_LINUX) {
            System.out.println("OS is Linux");
            path = SystemUtils.USER_HOME;
            path += "/.rona-survivors/";
        } else if (SystemUtils.IS_OS_WINDOWS) {
            System.out.println("OS is Windows");
            path = System.getenv("APPDATA");
            path += "\\.rona-survivors\\";
        } else {
            System.out.println("Your OS is unsupported!");
            Gdx.app.exit();
        }

        return path;
    }

}
