package dev.mavenless.ronasurvivors;

import com.badlogic.gdx.Files.FileType;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;

import mavenless.ronasurvivors.RonaSurvivors;

// Please note that on macOS your application needs to be started with the -XstartOnFirstThread JVM argument
public class DesktopLauncher {
	public static void main(String[] arg) {
		Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();

		config.setTitle("Rona Survivors");
		config.setForegroundFPS(60);
		config.useVsync(true);
		config.setWindowedMode(1280, 720);
		config.setWindowIcon(FileType.Internal, "icons/icon.png");

		new Lwjgl3Application(new RonaSurvivors(), config);
	}
}
