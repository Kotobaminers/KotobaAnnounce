package org.kotobaminers.kotobaannounce.kotobaannounce;

import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitTask;
import org.kotobaminers.kotobaannounce.kotobaannounce.KotobaAnnounceCommandExecutor.Commands;

public class KotobaAnnounce extends JavaPlugin {
	private static BukkitTask scheduler = null;
	static boolean enabled = true;
	/** is plugin enabled */
	static boolean debug = false;
	/** is debugging enabled */
	static Logger log = Bukkit.getLogger();
	Config config;
	Announcer announcer;

	@Override
	public void onEnable() {
		config = null;
		try {
			config = new Config(this, "config.yml");
		} catch(Exception e) {
			printDebug("Loading config failed", e);
			return;
		}

		for(Commands command : Commands.values()) {
			this.getCommand(command.toString()).setExecutor(new KotobaAnnounceCommandExecutor(this));
		}

		announcer = new Announcer(this);
		updateScheduler(config.getInt("interval", 100) * 20L);
	}

	@Override
	public void onDisable() {
		System.out.println("Disabling KotobaAnnounce");
		saveConfig();
	}

	/**
	 * Stopping old scheduler and restarting (with new settings)
	 * 
	 * @param period
	 *            how long to wait between message
	 */
	private void updateScheduler(long period) {
		if(scheduler != null)
			scheduler.cancel();
		scheduler = getServer().getScheduler().runTaskTimer(this, new Runnable() {
			@Override
			public void run() {
				announcer.broadcast();
			}
		}, period, period); // Waiting 10sec before first run, then run every
		                    // 10sec
	}

	/**
	 * Toggle debug on/off
	 * 
	 */
	public static void toggleDebug() {
		debug = !debug;
		if(debug)
			printInfo("Debug enabled");
		else
			printInfo("Debug disabled");
	}

	/**
	 * Toggle plugin on/off
	 */
	public static void toggleEnabled() {
		enabled = !enabled;
		if(debug)
			printInfo("Plugin enabled");
		else
			printInfo("Plugin disabled");
	}

	/**
	 * Reload plugin
	 */
	public void reload() {
		// TODO Auto-generated method stub

	}

	/**
	 * Print info message to server console
	 * 
	 * @param message
	 *            message
	 */
	public static void printInfo(String message) {
		log.info("[KotobaAnnounce] " + message);
	}

	/**
	 * Print Debug message to server console
	 * 
	 * @param message
	 *            debug message
	 */
	public static void printDebug(String message) {
		if(debug) {
			log.info("[KotobaAnnounce Debug] " + message);
		}
	}

	/**
	 * Print Debug message and StackTrace to server console and Example usage:
	 * KotobaAnnounce.printDebug("", new Exception());
	 * 
	 * @param message
	 *            debug message
	 * @param e
	 *            Exception
	 */
	public static void printDebug(String message, Exception e) {
		if(debug) {
			StackTraceElement element = e.getStackTrace()[0];
			String[] splited = element.getClassName().split("\\.");
			String nameClass = splited[splited.length - 1];
			String nameMethod = element.getMethodName();
			String line = String.valueOf(element.getLineNumber());
			log.info("[KotobaAnnounce Debug] " + message + " " + nameClass + " " + nameMethod + " " + line);
		}
	}
}