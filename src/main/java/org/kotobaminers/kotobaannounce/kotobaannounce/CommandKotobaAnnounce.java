package org.kotobaminers.kotobaannounce.kotobaannounce;

import org.bukkit.command.Command;
import org.bukkit.entity.Player;

public class CommandKotobaAnnounce extends MyCommand {
	public enum Commands {NONE, RELOAD, INTERVAL, TOGGLE;
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
		Commands commands = Commands.lookup(command.getName());
		switch(commands) {
		case INTERVAL:
			runInterval();
			return true;
		case RELOAD:
			runReload();
			return true;
		case TOGGLE:
			runToggle();
			return true;
		case NONE:
			break;
		default:
			break;
		}
		return false;
	}

	private void runToggle() {
		Utilities.prindDebug("", new Exception());
	}

	private void runReload() {
		Utilities.prindDebug("", new Exception());
	}

	private void runInterval() {
		Utilities.prindDebug("", new Exception());
	}
}