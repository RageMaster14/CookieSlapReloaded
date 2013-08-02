package rageteam.cookieslap.util;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

import rageteam.cookieslap.objects.CPlayer;
import rageteam.cookieslap.main.CS;

public class Chat {
	CS plugin;
	public Chat(CS instance) { this.plugin = instance; }
	
	// Colours Utilities
		/**
		 * Gets an input string and replaces the &(0-9a-z) color codes into colours.
		 * @param message Input string to colourize.
		 * @return Returns the input string with colour-formats applied.
		 */
		public String Colourize(String message) {
			message = message
					.replaceAll("&0", ChatColor.BLACK + "")
					.replaceAll("&1", ChatColor.DARK_BLUE + "")
					.replaceAll("&2", ChatColor.DARK_GREEN + "")
					.replaceAll("&3", ChatColor.DARK_AQUA + "")
					.replaceAll("&4", ChatColor.DARK_RED + "")
					.replaceAll("&5", ChatColor.LIGHT_PURPLE + "")
					.replaceAll("&6", ChatColor.GOLD + "")
					.replaceAll("&7", ChatColor.GRAY + "")
					.replaceAll("&8", ChatColor.DARK_GRAY + "")
					.replaceAll("&9", ChatColor.BLUE + "")
					.replaceAll("&a", ChatColor.GREEN + "")
					.replaceAll("&b", ChatColor.AQUA + "")
					.replaceAll("&c", ChatColor.RED + "")
					.replaceAll("&d", ChatColor.LIGHT_PURPLE + "")
					.replaceAll("&e", ChatColor.YELLOW + "")
					.replaceAll("&f", ChatColor.WHITE + "")
					.replaceAll("&k", ChatColor.MAGIC + "")
					.replaceAll("&l", ChatColor.BOLD + "")
					.replaceAll("&m", ChatColor.STRIKETHROUGH + "")
					.replaceAll("&n", ChatColor.UNDERLINE + "")
					.replaceAll("&o", ChatColor.ITALIC + "")
					.replaceAll("&r", ChatColor.RESET + "");
			
			return message;
		}
		
		/**
		 * A variant of the Colourize function.
		 * @param message Input string to colourize.
		 * @return Returns the input string with colour-formats applied.
		 */
		public String alternativeColourize(String message) {
			return message.replaceAll("&([a-z0-9])", "\u00A7$1");
		}
		
		/**
		 * Gets a string pre-formatted or not and gives back a flat one.
		 * @param message Input string to de-colourize.
		 * @return Decolourized string.
		 */
		public String Decolourize(String message) {
			return ChatColor.RESET + message.replaceAll("&([a-z0-9])", "");
		}
		
		
		// Chat Utilities
		public void Shout(String message) {
			Bukkit.broadcastMessage(Colourize(message));
		}
		
		public void Shout(String message, String permission) {
			Bukkit.broadcast(Colourize(message), permission);
		}
		
		public void Clear() {
			for (int i = 0; i < 20; i++) { Shout(""); }
		}
		
		
		// Formatting Utilities
		public String ApplyFormat(String username, String format) { return ApplyFormat(username, format, ""); }
		public String ApplyFormat(String username, String format, String shout) {
			CPlayer player = plugin.playerManager.get(username);
			
			String message = format
					.replaceAll("<username>", username)
					.replaceAll("<message>", shout);
			
			switch (player.rank){
				case "USER": message = message.replaceAll("<prefix>", plugin.cfgManager.prefixes[0]);
				case "VIP": message = message.replaceAll("<prefix>", plugin.cfgManager.prefixes[1]);
				case "STAFF": message = message.replaceAll("<prefix>", plugin.cfgManager.prefixes[2]);
				default: message = message.replaceAll("<prefix>", "&c&lERROR&r");
			}
			
			return Colourize(message);
		}

}
