package org.kotobaminers.kotobaannounce.kotobaannounce;

import org.apache.commons.lang.NotImplementedException;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class KotobaAnnounceCommandExecutor implements CommandExecutor {
	private KotobaAnnounce plugin;

	public KotobaAnnounceCommandExecutor(KotobaAnnounce plugin) {
		this.plugin = plugin;
	}

	public enum Commands {
		NONE, ANNOUNCE, KOTOBAANNOUNCE, KA;
		public static Commands lookup(String name) {
			try {
				return Commands.valueOf(name.toUpperCase());
			} catch(IllegalArgumentException e) {
				return Commands.NONE;
			}
		}
	}

	public enum KotobaAnnounceCommands {
		NONE, RELOAD, INTERVAL, ADD, DEL, TOGGLE, HELP, DEBUG, ;
		public static KotobaAnnounceCommands lookup(String name) {
			try {
				return KotobaAnnounceCommands.valueOf(name.toUpperCase());
			} catch(IllegalArgumentException e) {
				return KotobaAnnounceCommands.NONE;
			}
		}
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		Player player = null;
		if(sender instanceof Player) {
			player = (Player) sender;
			if(player.isOp() == false)
				return false; // Failing if not OP
		}

		Commands commands = Commands.lookup(label);
		switch(commands) {
			case KOTOBAANNOUNCE:
			case ANNOUNCE:
			case KA:
				runKotobaAnnounceCommand(sender, command, args);
				return true;
			case NONE:
				sender.sendMessage("Type \"/announce help\" to see announce commands1");
				break;
			default:
				sender.sendMessage("Type \"/announce help\" to see announce commands2");
				break;
		}
		return false;
	}

	public boolean runKotobaAnnounceCommand(CommandSender sender, Command command, String[] args) {
		KotobaAnnounce.printDebug(command.toString(), new Exception());
		if(0 < args.length) {
			KotobaAnnounceCommands commands = KotobaAnnounceCommands.lookup(args[0]);
			int lenght = args.length - 1;
			String[] args_new = new String[lenght];
			System.arraycopy(args, 1, args_new, 0, lenght);
			switch(commands) {
				case INTERVAL:
					if(1 < args.length) {
						commandInterval(sender, args_new[0]);
						return true;
					}
				case RELOAD:
					commandReload();
					return true;
				case TOGGLE:
					commandToggle(sender);
					return true;
				case HELP:
					commandHelp(sender);
					return true;
				case DEBUG:
					commandDebug(sender);
					return true;
				case ADD:
					commandAddAnnounce(sender, implode(" ", args_new));
					return true;
				case DEL:
					commandDelAnnounce(sender, args_new);
				case NONE:
					sender.sendMessage("Unknown command. Type \"/announce help\" to see subcommands3");
					break;
				default:
					sender.sendMessage("Unknown command. Type \"/announce help\" to see subcommands4");
					break;
			}
		}
		return false;
	}

	private void commandReload() {
		KotobaAnnounce.printDebug("", new Exception());
		plugin.reload();
	}

	private void commandInterval(CommandSender sender, String arg) {
		KotobaAnnounce.printDebug("", new Exception());
		Integer interval = 100;
		try {
			interval = Integer.valueOf(arg);
		} catch(NumberFormatException e) {
			KotobaAnnounce.printDebug(e.toString(), new Exception());
			return;
		}
		plugin.config.saveInterval(interval);
		if(sender instanceof Player)
			KotobaAnnounce.printInfo("Interval is se to " + interval);
		sender.sendMessage("Interval is se to " + interval);

	}

	private void commandAddAnnounce(CommandSender sender, String announce) {
		//plugin.announcer.AddAnnounce(announce);
		// TODO Auto-generated method stub
		throw new NotImplementedException();
	}

	private void commandDelAnnounce(CommandSender sender, String[] args_new) {
		// TODO Auto-generated method stub
		throw new NotImplementedException();
	}

	private void commandToggle(CommandSender sender) {
		KotobaAnnounce.toggleEnabled();

	}

	private void commandHelp(CommandSender sender) {
		sender.sendMessage("/announce interval <seconds>");
		sender.sendMessage("/announce reload");
		sender.sendMessage("/announce toggle");
		sender.sendMessage("/announce add <message>");
		sender.sendMessage("/announce del <message_id>");
		sender.sendMessage("/announce help");
		sender.sendMessage("/announce debug");

	}

	private void commandDebug(CommandSender sender) {
		KotobaAnnounce.toggleDebug();
	}

	/**
	 * Join array elements to string
	 * 
	 * @param glue
	 *            glue between string array pieces
	 * @param args_new
	 *            The array of strings to implode
	 * @return
	 */
	private String implode(String glue, String[] args_new) {
		if(args_new.length == 0)
			return null;
		StringBuilder builder = new StringBuilder();
		boolean first = true;
		for(String s : args_new) {
			if(!first)
				builder.append(glue);
			first = false;
			builder.append(s);
		}
		return builder.toString();
	}

}