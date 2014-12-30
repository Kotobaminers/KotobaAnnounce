package org.kotobaminers.kotobaannounce.kotobaannounce;

import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitTask;

public class KotobaAnnounce extends JavaPlugin {
	private static BukkitTask scheduler = null;

	@Override
	public void onEnable() {
		System.out.println("Enabling KotobaAnnounce");
		saveConfig();
		updateScheduler();

		Announcer.reloadMessages(this);
	}

	private void updateScheduler() {
		if(scheduler != null) scheduler.cancel();
		scheduler = getServer().getScheduler().runTaskTimer(this, new Runnable() {
            @Override
            public void run() {
            	Announcer.announce();
            }
        }, 200L, 200L); // Waiting 10sec before first run, then run every 10sec
	}

	@Override
	public void onDisable() {
		System.out.println("Disabling KotobaAnnounce");
		saveConfig();
	}
}