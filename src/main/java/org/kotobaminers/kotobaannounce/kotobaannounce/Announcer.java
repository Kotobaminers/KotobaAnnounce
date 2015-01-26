package org.kotobaminers.kotobaannounce.kotobaannounce;

import java.util.List;
import java.util.zip.CRC32;
import java.util.zip.Checksum;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * 
 * @author Thasan
 *
 */
public class Announcer {
	private KotobaAnnounce plugin;
	private List<String> messages;
	private static int index;
	private static String format = "<message>";

	public Announcer(KotobaAnnounce plugin) {
		this.plugin = plugin;
		format = plugin.config.getString("format");
		reloadMessages();
	}

	/**
	 * Broadcast next message
	 */
	public void broadcast() {
		if(!KotobaAnnounce.enabled)
			return;
		String message = getNextMessage();
		KotobaAnnounce.printDebug("#" + (Announcer.index + 1) + ": " + message);

		if(message == null)
			return;
		long checksum = getChecksum(message);

		for(Player player : Bukkit.getServer().getOnlinePlayers()) {
			if(!hasIgnored(player, checksum))
				player.sendMessage(parseMessage(message, player));
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
	 *            target player
	 * @return Returns parsed message, ready to minecraft
	 */
	private static String parseMessage(String message, Player player) {
		message = message.replace("<playername>", player.getName());
		return ChatColor.translateAlternateColorCodes('&', format.replace("<message>", message));
	}

	/**
	 * Check has player ignored message with entered checksum
	 * 
	 * @param player
	 *            player to check
	 * @param checksum
	 *            message checksum
	 * @return
	 */
	private static boolean hasIgnored(Player player, long checksum) {
		// TODO: check has player ignored message or disabled all messages
		// player.getUniqueId().toString()
		// Long.toString(checksum)
		KotobaAnnounce.printDebug("hasIgnored( " + player.getUniqueId().toString() + ", " + Long.toString(checksum) + " )");
		return false;
	}

	/**
	 * Returns message checksum
	 * 
	 * @param message
	 *            to calculate
	 * @return checksum calculated checksum
	 */
	private static long getChecksum(String message) {
		byte bytes[] = message.getBytes();
		Checksum checksum = new CRC32();
		checksum.update(bytes, 0, bytes.length);
		return checksum.getValue();
	}

	/**
	 * Reload messages from messages.yml
	 */
	// TODO: rename to reloadAnnouces()
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

	/**
	 * Add new message to messages.yml
	 * 
	 * @param announce
	 *            message to add
	 */
	public void addAnnounce(String announce) {
		Config messagesConfig = null;
		try {
			messagesConfig = new Config(plugin, "messages.yml");
		} catch(Exception e) {
			KotobaAnnounce.printDebug("Somethin went wrong whne loading messages. Disabling plugin", e);
			KotobaAnnounce.enabled = false;
		}
		messages = messagesConfig.getStringList("messages");
		messages.add(announce);
		messagesConfig.set("messages", messages);
		messagesConfig.saveConfig();
	}

	/**
	 * Deletes last announce from messages.yml
	 * 
	 * @return true if successfully deleted
	 */
	public boolean delLastAnnounce() {
		boolean returnvalue = delAnnounce(Announcer.index);
		if(returnvalue && Announcer.index > 0)
			Announcer.index--;
		return returnvalue;
	}

	/**
	 * Delete announce id from messages.yml
	 * 
	 * @param id
	 *            message id to delete
	 * @return true if successfully deleted
	 */
	public boolean delAnnounce(int id) {
		KotobaAnnounce.printDebug("size: " + messages.size() + " id: " + id);
		if(messages.size() - 1 < id || 0 > id) {
			KotobaAnnounce.printInfo("Cannot delete message #" + id + ", because only " + messages.size() + " messages saved");
			return false;
		}

		Config messagesConfig = null;
		try {
			messagesConfig = new Config(plugin, "messages.yml");
		} catch(Exception e) {
			KotobaAnnounce.printDebug("Somethin went wrong whne loading messages. Disabling plugin", e);
			KotobaAnnounce.enabled = false;
		}
		messages = messagesConfig.getStringList("messages");
		messages.remove(id);
		messagesConfig.set("messages", messages);
		return messagesConfig.saveConfig();
	}

	/**
	 * List all announces from messages.yml, no parsed colors
	 * 
	 * @param sender
	 *            request sender
	 */
	public void listAnnounce(CommandSender sender) {
		int i = 1;
		if(messages.size() == 0)
			sender.sendMessage("No announces. Add some /announce add message_here");
		for(String s : messages) {
			sender.sendMessage("#" + i + ": " + s);
			i++;
		}

	}
}
