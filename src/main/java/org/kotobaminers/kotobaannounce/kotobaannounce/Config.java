package org.kotobaminers.kotobaannounce.kotobaannounce;

import java.io.File;
import java.io.IOException;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

public abstract class Config {
	private static FileConfiguration config = StaticFields.config;
	private static File file = StaticFields.file;

	private enum Path {INTERVAL, TOGGLE}

	public static void saveConfig() {
		try {
			config.save(file);
		}
		catch(IOException e) {
			Utilities.prindDebug("Error: saveConfig", new Exception());
		}
	}

	public static void saveInterval(Integer interval) {
		String path = Path.INTERVAL.toString();
		config.set(path, interval);
		saveConfig();
	}
	public static Integer loadInterval() {
		Integer interval = 100;
		String path = Path.INTERVAL.toString();
		if(config.isInt(path)) {
			interval = config.getInt(path);
		}
		return interval;
	}
	public static void reverseToggle() {
		boolean toggle = true;
		String path = Path.TOGGLE.toString();
		if(config.getBoolean(path)) {
			toggle = !config.getBoolean(path);
		}
		config.set(path, toggle);
		saveConfig();
	}
	public static boolean loadToggle() {
		boolean toggle = true;
		String path = Path.TOGGLE.toString();
		if(config.getBoolean(path)) {
			toggle = !config.getBoolean(path);
		}
		return toggle;
	}
	public static void printToggle(Player player) {
		boolean toggle = loadToggle();
		player.sendMessage("Toggle is now " + toggle);
	}
}