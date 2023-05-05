package mavenless.ronasurvivors.Game;

import java.io.Serializable;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;

import mavenless.ronasurvivors.Utils.SaveUtil;

public class Save {

    private String saveLocationSaves;

    // Header
    private String signature = "SAV";
    private short version = 4;

    // Data
    private SaveData saveData;

    public Save() {
        // Get the save location
        String saveLocation = SaveUtil.getDefaultSavePath();

        // Check if the save location dir exists
        FileHandle saveLocationHandle = Gdx.files.absolute(saveLocation);
        if (!saveLocationHandle.exists()) saveLocationHandle.mkdirs();

        saveLocationSaves = saveLocation + "saves/";
        FileHandle saveLocationSavesHandle = Gdx.files.absolute(saveLocationSaves);
        if (!saveLocationSavesHandle.exists()) saveLocationSavesHandle.mkdirs();

        // Init test data
        saveData = new SaveData("Cathyrina", 4.798f);
    }

    // File utilities
    public void write() throws Exception {
        SaveUtil.writeObject(signature, version, saveData, saveLocationSaves + "6.sv");
    }

    public void read() throws Exception {
        SaveData sd = (SaveData) SaveUtil.readObject(signature, version, saveLocationSaves + "6.sv");

        Gdx.app.debug("Save file read", signature + " | " + version + " | " + sd.name + " | " + sd.strength);
    }

    // Getters

}

class SaveData implements Serializable {

    public String name;
    public float strength;

    public SaveData(String name, float strength) {
        this.name = name;
        this.strength = strength;
    }
    
}