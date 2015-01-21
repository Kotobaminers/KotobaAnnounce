package org.kotobaminers.kotobaannounce.kotobaannounce;

import java.io.File;
import java.io.IOException;

import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;

/**
 * Handling configfiles automatcily
 * 
 * @author Thasan
 *
 */
public class Config extends YamlConfiguration {
	private static KotobaAnnounce plugin;
	private String filename;
	private File filehandler;

	public Config(KotobaAnnounce plugin, String filename) throws Exception {
		Config.plugin = plugin;
		this.filename = filename;

		if(!loadConfig()) {
			KotobaAnnounce.printInfo("Cannot load " + filename + ", loading default.");
			plugin.saveResource(filename, true);
			if(!loadConfig()) {
				KotobaAnnounce.printInfo("Even that failed =(");
				throw new Exception();
			}
		}
	}

	/**
	 * Loading configs from file
	 * 
	 * @return true if successfully loaded
	 */
	boolean loadConfig() {

		if(this.filehandler == null) {
			this.filehandler = new File(plugin.getDataFolder() + "/" + filename);
		}
		if(!this.filehandler.exists()) {
			plugin.saveResource(filename, false);
		}

		try {
			load(this.filehandler);
			return true;
		} catch(IOException | InvalidConfigurationException e) {
			KotobaAnnounce.printDebug("Load failed", e);
			return false;
		}
	}

	/**
	 * Saving config file
	 * 
	 * @return true if successfully saved
	 */
	boolean saveConfig() {
		if(this.filehandler == null) {
			this.filehandler = new File(plugin.getDataFolder() + "/" + filename);
		}
		try {
			this.save(this.filehandler);
			return true;
		} catch(IOException e) {
			KotobaAnnounce.printDebug("Save failed", e);
			return false;
		}
	}

	public void saveInterval(Integer interval) {
		KotobaAnnounce.printDebug("saving new interval: " + interval + "");
		this.set("interval", interval);
		saveConfig();
	}

}