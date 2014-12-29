package org.kotobaminers.kotobaannounce.kotobaannounce;

import java.util.zip.CRC32;
import java.util.zip.Checksum;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public final class Announcer {

	public static void announce() {
		// TODO Auto-generated method stub
		String message = getNextMessage();
		long checksum = getChecksum(message);

		//TODO: if(debug) Bukkit.getLogger().info(message);
		
	    for (Player player : Bukkit.getServer().getOnlinePlayers()) {
	    	if(!hasIgnored(checksum, player))
	    		player.sendMessage(parseMessage(message, player));
	    }
		return;
	}

	private static String getNextMessage() {
		// TODO Get next message
		return "This is a &4test &fmessage for <playername>";
	}
	
	private static String parseMessage(String message, Player player) {
		message = message.replace("<playername>", player.getName());
		message = ChatColor.translateAlternateColorCodes('&', message);
		return message;
	}

	private static boolean hasIgnored(long checksum, Player player) {
		//TODO: check has player ignored message or disabled all messages
		//player.getUniqueId().toString()
		//Long.toString(checksum)
		return false;
	}

	private static long getChecksum(String message) {
		byte bytes[] = message.getBytes();
		Checksum checksum = new CRC32();
		checksum.update(bytes, 0, bytes.length);
		return checksum.getValue();
	}

}
