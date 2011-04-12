package no.ntnu.stud.flatcraft.settings;

import java.util.prefs.Preferences;

public class Settings {
	private Preferences settings;
	
	public Settings() {
		settings = Preferences.userNodeForPackage(Settings.class);
	}
	
	public boolean getFullScreen() {
		return settings.getBoolean("fullscreen", false);
	}
	
	public boolean getSound() {
		return settings.getBoolean("sound", true);
	}
	
	public void setFullScreen(boolean value) {
		settings.putBoolean("fullscreen", value);
	}
	
	public void setSound(boolean value) {
		settings.putBoolean("sound", value);
	}
}
