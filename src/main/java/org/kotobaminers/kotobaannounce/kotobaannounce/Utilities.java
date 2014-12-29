package org.kotobaminers.kotobaannounce.kotobaannounce;

import org.bukkit.Bukkit;

public class Utilities {
	private static boolean debug = true;

	public static void toggleDebug() {
		debug = !debug;
		if(debug)
			System.out.println("Debug enabled");
		else
			System.out.println("Debug disabled");
	}
	
	public static void broadcastWithPrefix(String message) {
		String prefix = "";//TODO load prefix from the config.yml or hold it as static?
		Bukkit.broadcastMessage(prefix + " " + message);
	}

	public static void prindDebug(String message, Exception e) {
		if(debug) {
			StackTraceElement element = e.getStackTrace()[0];
			String[] splited = element.getClassName().split("\\.");
			String nameClass = splited[splited.length-1];
			String nameMethod = element.getMethodName();
			String line = String.valueOf(element.getLineNumber());
			System.out.println(message + " " + nameClass + " " + nameMethod + " " + line);
		}
	}
}