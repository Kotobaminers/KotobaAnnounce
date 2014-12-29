package org.kotobaminers.kotobaannounce.kotobaannounce;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandExecutorKotobaAnnounce implements CommandExecutor {
	public enum Commands {NONE, ANNOUNCE, KOTOBAANNOUNCE;
		public static Commands lookup(String name) {
			try {
				return Commands.valueOf(name.toUpperCase());
			} catch (IllegalArgumentException e) {
				return Commands.NONE;
			}
		}
	}
	public CommandExecutorKotobaAnnounce(KotobaAnnounce plugin) {
	}
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label,String[] args) {
		if(sender instanceof Player) {
			Player player = (Player) sender;
			Commands commands = Commands.lookup(label);
			switch(commands) {
			case ANNOUNCE:
				return true;
			case KOTOBAANNOUNCE:
				return true;
			case NONE:
				break;
			default:
				break;
			}
		}
		return false;
	}

}
