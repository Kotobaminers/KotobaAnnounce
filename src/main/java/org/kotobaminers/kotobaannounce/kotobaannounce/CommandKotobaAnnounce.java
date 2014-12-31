package org.kotobaminers.kotobaannounce.kotobaannounce;

import org.bukkit.command.Command;
import org.bukkit.entity.Player;

public class CommandKotobaAnnounce extends MyCommand {
	public enum Commands {NONE, RELOAD, INTERVAL, TOGGLE, DEBUG, IGNORE;
		public static Commands lookup(String name) {
			try {
				return Commands.valueOf(name.toUpperCase());
			} catch (IllegalArgumentException e) {
				return Commands.NONE;
			}
		}
	}
	@Override
	public boolean runCommand(Player player, Command command, String[] args) {
		Utilities.prindDebug("", new Exception());
		if(0 < args.length) {
			Commands commands = Commands.lookup(args[0]);
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
			case IGNORE:
				commandIgnore();
				break;
			default:
				break;
			}
		}
		return false;
	}
	private void commandToggle(Player player) {
		Utilities.prindDebug("", new Exception());
		ConfigHandler.reverseToggle();
		ConfigHandler.printToggle(player);
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
		ConfigHandler.saveInterval(interval);
	}
	private void commandIgnore() {

	}
}