package rageteam.cookieslap.listeners;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import rageteam.cookieslap.main.CookieSlap;

public class PlayerListener implements Listener{
	
	CookieSlap plugin;
	public PlayerListener(CookieSlap instance) { this.plugin = instance; }
	
	@EventHandler
	public void onJoin(PlayerJoinEvent e){
		Player player = e.getPlayer();
		player.setScoreboard(plugin.stats.board);
		
		//Emerald Shop
		ItemStack emerald = new ItemStack(Material.EMERALD);
		ItemMeta emeraldMeta = emerald.getItemMeta();
		emeraldMeta.setDisplayName(ChatColor.GREEN + "Emerald Shop");
		emerald.setItemMeta(emeraldMeta);
		player.getInventory().addItem(emerald);
		
		//Tele Compass
		ItemStack compass = new ItemStack(Material.COMPASS);
		ItemMeta compassMeta = compass.getItemMeta();
		compassMeta.setDisplayName(ChatColor.GOLD + "Quick Travel");
		compass.setItemMeta(compassMeta);
		player.getInventory().addItem(compass);
	}
	
	@EventHandler
	public void onQuit(PlayerQuitEvent e){
		Player player = e.getPlayer();
		player.getInventory().remove(Material.COMPASS);
		player.getInventory().remove(Material.EMERALD);
	}
}
