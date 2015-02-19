package org.kotobaminers.kotobaannounce.kotobaannounce;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

public class KotobaAnnouncethisTabCompleter implements TabCompleter {

	public KotobaAnnouncethisTabCompleter(KotobaAnnounce kotobaAnnounce) {
	}

	@Override
	public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
		List<String> al = new ArrayList<String>();
		if(command.getName().equals("kotobaannounce") && args.length == 1) {
			al.add("add");
			al.add("del");
			al.add("list");
			al.add("toggle");
			al.add("reload");
			al.add("help");
			al.add("debug");
		}
		return al;
	}

}
