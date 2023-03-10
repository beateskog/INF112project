package mavenless.ronasurvivors.Utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.controllers.Controller;
import com.badlogic.gdx.controllers.Controllers;
import com.google.gson.Gson;

public class InputUtil {

    private Controller controller = null;

    private ArrayList<Integer> buttons;
    private HashMap<Integer, Boolean> buttonOn;
    private HashMap<Integer, Boolean> buttonJustOn;
    private HashMap<Integer, Boolean> buttonJustOff;

    private InputIndex inputIndex;
    private ArrayList<InputProfile> inputProfiles;

    public InputUtil() {
        // Init
        controller = Controllers.getCurrent();

        buttonOn = new HashMap<Integer, Boolean>();
        buttonJustOn = new HashMap<Integer, Boolean>();
        buttonJustOff = new HashMap<Integer, Boolean>();

        buttons = new ArrayList<Integer>();

        // Setting up buttons to be polled
        if (controller != null) {
            buttons.add(controller.getMapping().buttonA);
            buttons.add(controller.getMapping().buttonB);
            buttons.add(controller.getMapping().buttonX);
            buttons.add(controller.getMapping().buttonY);
            buttons.add(controller.getMapping().buttonDpadUp);
            buttons.add(controller.getMapping().buttonDpadDown);
            buttons.add(controller.getMapping().buttonDpadLeft);
            buttons.add(controller.getMapping().buttonDpadRight);
            buttons.add(controller.getMapping().buttonL1);
            buttons.add(controller.getMapping().buttonR1);
            buttons.add(controller.getMapping().buttonLeftStick);
            buttons.add(controller.getMapping().buttonRightStick);
            buttons.add(controller.getMapping().buttonStart);
        }

        // Import and parse profiles
        Gson gson = new Gson();
        inputIndex = gson.fromJson(Gdx.files.internal("engine/input/index.json").readString(), InputIndex.class);

        inputProfiles = new ArrayList<>();
        for (String profile : inputIndex.getProfiles()) {
            inputProfiles.add(gson.fromJson(Gdx.files.internal("engine/input/" + profile + ".json").readString(), InputProfile.class));
        }
    }

    private boolean buttonDown(int buttonCode) {
        if (controller == null) return false;
        
        return (controller.getButton(buttonCode) && buttonJustOn.get(buttonCode));
    }

    private boolean buttonUp(int buttonCode) {
        if (controller == null) return false;
        
        return buttonJustOff.getOrDefault(buttonCode, false);
    }

    private int controllerButtonMapping(String button) {
        switch (button) {
            case "A":
                return controller.getMapping().buttonA;
            case "B":
                return controller.getMapping().buttonB;
            case "X":
                return controller.getMapping().buttonX;
            case "Y":
                return controller.getMapping().buttonY;
            case "DpadUp":
                return controller.getMapping().buttonDpadUp;
            case "DpadDown":
                return controller.getMapping().buttonDpadDown;
            case "DpadLeft":
                return controller.getMapping().buttonDpadLeft;
            case "DpadRight":
                return controller.getMapping().buttonDpadRight;
            case "L1":
                return controller.getMapping().buttonL1;
            case "R1":
                return controller.getMapping().buttonR1;
            case "LeftStick":
                return controller.getMapping().buttonLeftStick;
            case "RightStick":
                return controller.getMapping().buttonRightStick;
            case "Start":
                return controller.getMapping().buttonStart;
            default:
                return -1;
        }
    }

    private InputProfile getInputProfile(String name) {
        for (InputProfile ip : inputProfiles) {
            if (ip.getName().equals(name)) return ip;
        }

        return null;
    }

    public void update() {
        if (controller == null) return;

        // Check for button pressed down
        for (Integer button : buttons) {
            if (controller.getButton(button)) {
                buttonJustOn.put(button, !buttonOn.get(button));
                buttonOn.put(button, true);
            } else {
                buttonJustOff.put(button, buttonOn.getOrDefault(button, false));
                buttonOn.put(button, false);
            }
        }
    }

    public boolean down(String profile) {
        InputProfile ip = getInputProfile(profile);

        // Handle keyboard
        ArrayList<Boolean> keyboardInputs = new ArrayList<>();
        for (String keyboard : ip.getKeyboard()) {
            keyboardInputs.add(Gdx.input.isKeyJustPressed(Keys.valueOf(keyboard)));
        }
        if (keyboardInputs.contains(true)) return true;

        // Handle controller
        if (controller == null) return false;

        ArrayList<Boolean> controllerInputs = new ArrayList<>();
        for (String controller : ip.getController()) {
            controllerInputs.add(buttonDown(controllerButtonMapping(controller)));
        }
        return controllerInputs.contains(true);
    }

    public boolean up(String profile) {
        InputProfile ip = getInputProfile(profile);

        // Handle keyboard
        ArrayList<Boolean> keyboardInputs = new ArrayList<>();
        for (String keyboard : ip.getKeyboard()) {
            keyboardInputs.add(Gdx.input.isKeyJustPressed(Keys.valueOf(keyboard)));
        }
        if (keyboardInputs.contains(true)) return true;

        // Handle controller
        if (controller == null) return false;

        ArrayList<Boolean> controllerInputs = new ArrayList<>();
        for (String controller : ip.getController()) {
            controllerInputs.add(buttonUp(controllerButtonMapping(controller)));
        }
        return controllerInputs.contains(true);
    }

    public float moveX() {
        float axis = 0.0f;
        float deadzone = inputIndex.getDeadzone();

        if (controller != null) {
            float stick = controller.getAxis(controller.getMapping().axisLeftX);
            if (!(stick < deadzone && stick > -deadzone))
                axis = Math.max(stick, -1.0f);
        }
        
        if (Gdx.input.isKeyPressed(Keys.D)) axis += 1.0f;
        if (Gdx.input.isKeyPressed(Keys.A)) axis -= 1.0f;

        return Math.max(Math.min(axis, 1.0f), -1.0f);
    }

    public float moveY() {
        float axis = 0.0f;
        float deadzone = inputIndex.getDeadzone();

        if (controller != null) {
            float stick = controller.getAxis(controller.getMapping().axisLeftY) * -1.0f;
            if (!(stick < deadzone && stick > -deadzone))
                axis = Math.min(stick, 1.0f);
        }
        
        if (Gdx.input.isKeyPressed(Keys.W)) axis += 1.0f;
        if (Gdx.input.isKeyPressed(Keys.S)) axis -= 1.0f;

        return Math.max(Math.min(axis, 1.0f), -1.0f);
    }

    public void vibrate(int milliseconds, float strength) {
        if (controller == null) return;

        if (!controller.canVibrate()) {
            Gdx.app.debug("Controller", controller.getName() + " - can't vibrate");
            return;
        }

        controller.startVibration(milliseconds, strength);
    }

}

class InputIndex {
    private float deadzone;
    private List<String> profiles;

    public InputIndex() {}

    public float getDeadzone() {
        return deadzone;
    }

    public List<String> getProfiles() {
        return profiles;
    }

    public String toString() {
        return "InputIndex [ deadzone: " + deadzone + ", profiles: " + profiles + " ]";
    }
}

class InputProfile {
    private String name;
    private List<String> keyboard;
    private List<String> controller;

    public InputProfile() {}

    public String getName() {
        return name;
    }

    public List<String> getKeyboard() {
        return keyboard;
    }

    public List<String> getController() {
        return controller;
    }

    public String toString() {
        return "InputProfile [ name: " + name + ", keyboard: " + keyboard + ", controller: " + controller +" ]";
    }
}