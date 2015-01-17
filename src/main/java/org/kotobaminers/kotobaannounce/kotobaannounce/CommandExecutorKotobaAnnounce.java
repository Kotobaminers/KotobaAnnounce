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
			if(player.isOp() == false) return false;	// Failing if not OP
		}
		
		Player player = null;
		Commands commands = Commands.lookup(label);
		switch(commands) {
		case ANNOUNCE:
			runKotobaAnnounceCommand(player, command, args);
			return true;
		case KOTOBAANNOUNCE:
			runKotobaAnnounceCommand(player, command, args);
			return true;
		case NONE:
			break;
		default:
			break;
		}
		return false;
	}
	
	
	public enum KotobaAnnounceCommands {NONE, RELOAD, INTERVAL, TOGGLE, DEBUG;
		public static KotobaAnnounceCommands lookup(String name) {
			try {
				return KotobaAnnounceCommands.valueOf(name.toUpperCase());
			} catch (IllegalArgumentException e) {
				return KotobaAnnounceCommands.NONE;
			}
		}
	}
	public boolean runKotobaAnnounceCommand(Player player, Command command, String[] args) {
		Utilities.prindDebug("", new Exception());
		if(0 < args.length) {
			KotobaAnnounceCommands commands = KotobaAnnounceCommands.lookup(args[0]);
			switch(commands) {
			case INTERVAL:
				if(1 < args.length) {
					commandInterval(args[1]);
					return true;
				}
			case RELOAD:
				commandReload();
				return true;
			case TOGGLE:
				commandToggle(player);
				return true;
			case DEBUG:
				Utilities.toggleDebug();
				return true;
			case NONE:
				break;
			default:
				break;
			}
		}
		return false;
	}
	private void commandToggle(Player player) {
		Utilities.prindDebug("", new Exception());
		Config.reverseToggle();
		Config.printToggle(player);
	}
	private void commandReload() {
		Utilities.prindDebug("", new Exception());
		StaticFields.initialize(StaticFields.plugin);
	}
	private void commandInterval(String arg) {
		Utilities.prindDebug("", new Exception());
		Integer interval = 100;
		try{
			interval = Integer.valueOf(arg);
		} catch(NumberFormatException e) {
			Utilities.prindDebug(e.toString(), new Exception());
			return;
		}
		Config.saveInterval(interval);
	}
	
}