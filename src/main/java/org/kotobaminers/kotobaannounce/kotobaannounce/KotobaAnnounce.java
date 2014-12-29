package org.kotobaminers.kotobaannounce.kotobaannounce;

import org.bukkit.plugin.java.JavaPlugin;

public class KotobaAnnounce extends JavaPlugin {
	@Override
	public void onEnable() {
		System.out.println("Enabling KotobaAnnounce");
		saveConfig();
		
        getServer().getScheduler().runTaskTimer(this, new Runnable() {
            @Override
            public void run() {
                // Do something
            	Scheduler.run();
            }
        }, 200L, 200L); // Waiting 10sec before first run, then run every 10sec
        
	}

	@Override
	public void onDisable() {
		System.out.println("Disabling KotobaAnnounce");
		saveConfig();
	}
}