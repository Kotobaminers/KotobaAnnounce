package org.kotobaminers.kotobaannounce.kotobaannounce;

import java.io.IOException;
import java.util.List;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

public class ConfigHandler {
	public static FileConfiguration configPlugin = StaticFields.plugin.getConfig();

	private enum PathPlugin {INTERVAL, TOGGLE}
	private enum PathMessages {MESSAGES}

	public static void saveConfig() {
		StaticFields.plugin.saveConfig();;
	}
	public static void saveInterval(Integer interval) {
		String path = PathPlugin.INTERVAL.toString();
		configPlugin.set(path, interval);
		saveConfig();
	}
	public static Integer loadInterval() {
		Integer interval = 100;
		String path = PathPlugin.INTERVAL.toString();
		if(configPlugin.isInt(path)) {
			interval = configPlugin.getInt(path);
		}
		return interval;
	}
	public static void reverseToggle() {
		boolean toggle = true;
		String path = PathPlugin.TOGGLE.toString();
		if(configPlugin.isBoolean(path)) {
			toggle = !configPlugin.getBoolean(path);
		}
		configPlugin.set(path, toggle);
		saveConfig();
	}
	public static boolean loadToggle() {
		boolean toggle = true;
		String path = PathPlugin.TOGGLE.toString();
		if(configPlugin.isBoolean(path)) {
			toggle = !configPlugin.getBoolean(path);
		}
		return toggle;
	}
	public static void printToggle(Player player) {
		boolean toggle = loadToggle();
		player.sendMessage("Toggle is now " + toggle);
	}
	public static void saveMessages(List<String> messages) {
		StaticFields.configMessages.set(PathMessages.MESSAGES.toString(), messages);
		try {
			StaticFields.configMessages.save(StaticFields.fileMessages);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}