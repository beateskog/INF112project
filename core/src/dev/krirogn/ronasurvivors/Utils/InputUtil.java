package dev.krirogn.ronasurvivors.Utils;

import java.util.ArrayList;
import java.util.HashMap;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.controllers.Controller;
import com.badlogic.gdx.controllers.Controllers;

public class InputUtil {

    private Controller controller = null;

    private ArrayList<Integer> buttons;
    private HashMap<Integer, Boolean> buttonOn;
    private HashMap<Integer, Boolean> buttonJustOn;
    private HashMap<Integer, Boolean> buttonJustOff;

    private float deadzone = 0.125f;

    public InputUtil() {
        controller = Controllers.getCurrent();

        buttonOn = new HashMap<Integer, Boolean>();
        buttonJustOn = new HashMap<Integer, Boolean>();
        buttonJustOff = new HashMap<Integer, Boolean>();

        buttons = new ArrayList<Integer>();

        if (controller == null) return;
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

    private boolean buttonDown(int buttonCode) {
        if (controller == null) return false;
        
        return (controller.getButton(buttonCode) && buttonJustOn.get(buttonCode));
    }

    private boolean buttonUp(int buttonCode) {
        if (controller == null) return false;
        
        return buttonJustOff.getOrDefault(buttonCode, false);
    }

    public boolean confirm() {
        // Handle keyboard
        boolean input = Gdx.input.isKeyJustPressed(Keys.ENTER) || Gdx.input.isKeyJustPressed(Keys.SPACE);
        if (input) return true;

        // Handle controller
        if (controller == null) return false;
        return buttonDown(controller.getMapping().buttonA);
    }

    public boolean cancel() {
        // Handle keyboard
        boolean input = Gdx.input.isKeyJustPressed(Keys.ESCAPE);
        if (input) return true;

        // Handle controller
        if (controller == null) return false;
        return buttonUp(controller.getMapping().buttonA);
    }

    public float moveX() {
        float axis = 0.0f;

        float stick = controller.getAxis(controller.getMapping().axisLeftX);
        if (!(stick < deadzone && stick > -deadzone))
            axis = Math.max(stick, -1.0f);
        
        if (Gdx.input.isKeyPressed(Keys.D)) axis += 1.0f;
        if (Gdx.input.isKeyPressed(Keys.A)) axis -= 1.0f;

        return Math.max(Math.min(axis, 1.0f), -1.0f);
    }

    public float moveY() {
        float axis = 0.0f;

        float stick = controller.getAxis(controller.getMapping().axisLeftY) * -1.0f;
        if (!(stick < deadzone && stick > -deadzone))
            axis = Math.min(stick, 1.0f);
        
        if (Gdx.input.isKeyPressed(Keys.W)) axis += 1.0f;
        if (Gdx.input.isKeyPressed(Keys.S)) axis -= 1.0f;

        return Math.max(Math.min(axis, 1.0f), -1.0f);
    }

}
