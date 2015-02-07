package org.kotobaminers.kotobaannounce.kotobaannounce;

import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitTask;
import org.kotobaminers.kotobaannounce.kotobaannounce.KotobaAnnounceCommandExecutor.Commands;

/**
 * Announce plugin for minecraft
 * 
 * @author Thasan, Kai_f
 *
 */
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
		announcer = null;

		reload();

		for(Commands command : Commands.values()) {
			if(command == Commands.NONE) continue;
			this.getCommand(command.toString()).setExecutor(new KotobaAnnounceCommandExecutor(this));
			this.getCommand(command.toString()).setTabCompleter(new KotobaAnnouncethisTabCompleter(this));
		}

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
	void updateScheduler(long period) {
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
	}

	/**
	 * Toggle plugin on/off
	 */
	public static void toggleEnabled() {
		enabled = !enabled;
	}

	/**
	 * Reload plugin
	 */
	public void reload() {
		try {
			config = new Config(this, "config.yml");
		} catch(Exception e) {
			printDebug("Loading config failed", e);
			return;
		}

		announcer = new Announcer(this);
		updateScheduler(config.getInt("interval", 100) * 20L);

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
	 * Print info message to server console and sender if any
	 * 
	 * @param sender
	 *            command sender
	 * @param message
	 *            message to send
	 */
	public static void printInfo(CommandSender sender, String message) {
		log.info("[KotobaAnnounce] " + message);
		if(sender instanceof Player)
			sender.sendMessage(message);

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