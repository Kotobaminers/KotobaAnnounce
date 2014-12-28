package org.kotobaminers.kotobaannounce.kotobaannounce;

import org.bukkit.plugin.java.JavaPlugin;

public class KotobaAnnounce extends JavaPlugin {
	@Override
	public void onEnable() {
		System.out.println("Enabling KotobaAnnounce");
		saveConfig();
	}

	@Override
	public void onDisable() {
		System.out.println("Disabling KotobaAnnounce");
		saveConfig();
	}
}