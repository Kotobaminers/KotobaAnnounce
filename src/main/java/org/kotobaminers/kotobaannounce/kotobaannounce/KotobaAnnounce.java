package org.kotobaminers.kotobaannounce.kotobaannounce;

import org.bukkit.plugin.java.JavaPlugin;
import org.kotobaminers.kotobaannounce.kotobaannounce.CommandExecutorKotobaAnnounce.Commands;

public class KotobaAnnounce extends JavaPlugin {
	@Override
	public void onEnable() {
		System.out.println("Enabling KotobaAnnounce");

		StaticFields.initialize(this);

		for(Commands command : Commands.values()) {
			this.getCommand(command.toString()).setExecutor(new CommandExecutorKotobaAnnounce(this));
		}

		saveConfig();
	}

	@Override
	public void onDisable() {
		System.out.println("Disabling KotobaAnnounce");
		saveConfig();
	}
}