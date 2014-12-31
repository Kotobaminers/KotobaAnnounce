package org.kotobaminers.kotobaannounce.kotobaannounce;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

public class ConfigHandler {
	private static FileConfiguration configPlugin = StaticFields.plugin.getConfig();
	private static FileConfiguration configMessages = StaticFields.configMessages;

	private enum Path {INTERVAL, TOGGLE}

	public static void saveConfig() {
		StaticFields.plugin.saveConfig();;
	}
	public static void saveInterval(Integer interval) {
		String path = Path.INTERVAL.toString();
		configPlugin.set(path, interval);
		saveConfig();
	}
	public static Integer loadInterval() {
		Integer interval = 100;
		String path = Path.INTERVAL.toString();
		if(configPlugin.isInt(path)) {
			interval = configPlugin.getInt(path);
		}
		return interval;
	}
	public static void reverseToggle() {
		boolean toggle = true;
		String path = Path.TOGGLE.toString();
		if(configPlugin.isBoolean(path)) {
			toggle = !configPlugin.getBoolean(path);
		}
		configPlugin.set(path, toggle);
		saveConfig();
	}
	public static boolean loadToggle() {
		boolean toggle = true;
		String path = Path.TOGGLE.toString();
		if(configPlugin.isBoolean(path)) {
			toggle = !configPlugin.getBoolean(path);
		}
		return toggle;
	}
	public static void printToggle(Player player) {
		boolean toggle = loadToggle();
		player.sendMessage("Toggle is now " + toggle);
	}
}