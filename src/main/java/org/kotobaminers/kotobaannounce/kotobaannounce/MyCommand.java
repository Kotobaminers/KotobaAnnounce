package org.kotobaminers.kotobaannounce.kotobaannounce;

import org.bukkit.command.Command;
import org.bukkit.entity.Player;


public abstract class MyCommand {
	public abstract boolean runCommand(Player player, Command command, String[] args);
}
