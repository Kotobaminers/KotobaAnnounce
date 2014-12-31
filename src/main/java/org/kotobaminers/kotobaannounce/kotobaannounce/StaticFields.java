package org.kotobaminers.kotobaannounce.kotobaannounce;

import java.io.File;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;


public class StaticFields {
	public static KotobaAnnounce plugin;
	public static FileConfiguration configMessages;
	public static File fileMessages;

	public static Integer interval;
	public static boolean toggle;

	public static void initialize(KotobaAnnounce plugin) {
		StaticFields.plugin = plugin;
		interval = ConfigHandler.loadInterval();
		toggle = ConfigHandler.loadToggle();
		String path = plugin.getDataFolder() + "\\messages.yml";
		fileMessages = new File(path);
		configMessages = YamlConfiguration.loadConfiguration(fileMessages);
	}
}