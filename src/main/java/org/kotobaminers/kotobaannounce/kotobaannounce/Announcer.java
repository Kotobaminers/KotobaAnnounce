package org.kotobaminers.kotobaannounce.kotobaannounce;

import java.util.List;
import java.util.zip.CRC32;
import java.util.zip.Checksum;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class Announcer {
	private KotobaAnnounce plugin;
	private List<String> messages;
	private static int index;

	public Announcer(KotobaAnnounce plugin) {
		this.plugin = plugin;

		reloadMessages();
	}

	public void broadcast() {
		if(!KotobaAnnounce.enabled)
			return;
		String message = getNextMessage();
		String prefix = ""; // TODO load prefix from the config.yml or hold it
		                    // as static?

		if(message == null)
			return;
		long checksum = getChecksum(message);

		for(Player player : Bukkit.getServer().getOnlinePlayers()) {
			if(!hasIgnored(player, checksum))
				player.sendMessage(parseMessage(prefix + " " + message, player));
		}
		return;
	}

	/**
	 * Get next plain message
	 * 
	 * @return Returns next message, if not available, returns first message, if
	 *         not available, returns null
	 */
	private String getNextMessage() {
		if(messages.isEmpty())
			return null;

		if(index >= messages.size() - 1)
			index = 0;
		else
			index++;

		return messages.get(index);
	}

	/**
	 * 
	 * @param message
	 *            Message with & color codes and <tags>
	 * @param player
	 * @return Returns parsed message, ready to minecraft
	 */
	private static String parseMessage(String message, Player player) {
		message = message.replace("<playername>", player.getName());
		message = ChatColor.translateAlternateColorCodes('&', message);
		return message;
	}

	private static boolean hasIgnored(Player player, long checksum) {
		// TODO: check has player ignored message or disabled all messages
		// player.getUniqueId().toString()
		// Long.toString(checksum)
		System.out.println("hasIgnored( " + player.getUniqueId().toString() + ", " + Long.toString(checksum) + " )");
		return false;
	}

	private static long getChecksum(String message) {
		byte bytes[] = message.getBytes();
		Checksum checksum = new CRC32();
		checksum.update(bytes, 0, bytes.length);
		return checksum.getValue();
	}

	public void reloadMessages() {
		Config messagesConfig = null;
		try {
			messagesConfig = new Config(plugin, "messages.yml");
		} catch(Exception e) {
			KotobaAnnounce.printDebug("Somethin went wrong whne loading messages. Disabling plugin", e);
			KotobaAnnounce.enabled = false;
		}
		messages = messagesConfig.getStringList("messages");
		for(String s : messages) {
			System.out.println("Announce message loaded: " + s);
		}
	}

	public void AddAnnounce(String announce) {
		Config messagesConfig = null;
		try {
			messagesConfig = new Config(plugin, "messages.yml");
		} catch(Exception e) {
			KotobaAnnounce.printDebug("Somethin went wrong whne loading messages. Disabling plugin", e);
			KotobaAnnounce.enabled = false;
		}
		messagesConfig.getStringList("messages").add(announce);
		messages.add(announce);
		messagesConfig.saveConfig();
	}

}
