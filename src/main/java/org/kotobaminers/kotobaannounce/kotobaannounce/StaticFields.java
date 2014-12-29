package org.kotobaminers.kotobaannounce.kotobaannounce;

import java.io.File;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

public class StaticFields {
	public static String namePlugin;
	public static String directoryBase;
	public static KotobaAnnounce plugin;
	public static File file;
	public static FileConfiguration config;

	public static Integer interval;
	public static boolean toggle;

	public static void initialize(KotobaAnnounce plugin) {
		String[] names = plugin.getClass().getName().split("\\.");
		namePlugin = names[names.length-1];
		directoryBase = plugin.getDataFolder().getAbsolutePath() + "//";
		file = new File(directoryBase + "config.yml");
		config = YamlConfiguration.loadConfiguration(file);
		interval = Config.loadInterval();
		toggle = Config.loadToggle();
	}
}