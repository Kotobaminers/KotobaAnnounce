package org.kotobaminers.kotobaannounce.kotobaannounce;

import java.io.File;
import java.util.List;
import java.util.zip.CRC32;
import java.util.zip.Checksum;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.Configuration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

public final class Announcer {
    private final KotobaAnnounce plugin;
    static List<String> messages;
    private static int index;
    
    public Announcer(KotobaAnnounce plugin) {
        this.plugin = plugin;
    }


	public static void announce() {
		String message = getNextMessage();

		//TODO: if(debug) Bukkit.getLogger().info(message);

		if(message == null) return;
		long checksum = getChecksum(message);

	    for (Player player : Bukkit.getServer().getOnlinePlayers()) {
	    	if(!hasIgnored(checksum, player))
	    		player.sendMessage(parseMessage(message, player));
	    }
		return;
	}

	private static String getNextMessage() {
		if(messages.isEmpty()) return null;
		
		if(index >= messages.size()-1)
			index = 0;
		else
			index++;
		
		return messages.get(index);
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
	
	static void reloadMessages(KotobaAnnounce kotobaAnnounce) {
	    File messagesFile = null;
		if (messagesFile == null) {
	        messagesFile = new File(kotobaAnnounce.getDataFolder(), "messages.yml");
	    }
	    if (!messagesFile.exists()) {            
	    	kotobaAnnounce.saveResource("messages.yml", false);
	     }
	    
		Configuration messagesConfig = YamlConfiguration.loadConfiguration(messagesFile);
		messages = messagesConfig.getStringList("messages");
		for(String s : messages) {
			System.out.println("Announce message loaded: " + s);
		}
	}

}
