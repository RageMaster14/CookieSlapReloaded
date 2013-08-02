package rageteam.cookieslap.handlers;

import org.bukkit.Bukkit;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import rageteam.cookieslap.objects.GameState;
import rageteam.cookieslap.util.Messages;
import rageteam.cookieslap.main.CS;

public class PlayerHandler implements Listener {
	CS plugin;
	public PlayerHandler(CS instance) {this.plugin = instance;}
	
	@EventHandler
	public void onPlayerChat(AsyncPlayerChatEvent event) {
		event.setCancelled(true);
		
		String username = event.getPlayer().getName();
		String message = event.getMessage();
		String format = plugin.cfgManager.chatFormat;
		
		if (!event.getPlayer().hasPermission("sgr.color")) {
			message = plugin.chat.Decolourize(message);
		}
		
		message = plugin.chat.ApplyFormat(username, format, message);
		plugin.chat.Shout(message);
	}
	
	@EventHandler
	public void onDamage(EntityDamageEvent event) {
		if ((event.getEntityType() == EntityType.PLAYER) && 
			(plugin.gameState != GameState.INGAME)) {
			event.setDamage(0.0);
		}
	}
	
	@EventHandler
	public void onPlayerFoodLevelChanged(FoodLevelChangeEvent event) {
		if ((plugin.gameState != GameState.INGAME)) {
				event.setCancelled(true);
		}
	}
	
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event) {
		if (plugin.gameState != GameState.LOBBY) {
			event.getPlayer().kickPlayer(plugin.chat.Colourize(Messages.CANNOT_JOIN));
			return;
		}
		
		plugin.canStart = (Bukkit.getOnlinePlayers().length >= plugin.cfgManager.minPlayers);
		
		event.getPlayer().setHealth(20.0);
		event.getPlayer().setFoodLevel(20);
		
		plugin.playerManager.load(event.getPlayer().getName());
		
		event.setJoinMessage(plugin.chat.ApplyFormat(event.getPlayer().getName(), plugin.cfgManager.messages[0]));
	}
	
	@EventHandler
	public void onPlayerQuit(PlayerQuitEvent event) {
		plugin.canStart = (Bukkit.getOnlinePlayers().length >= plugin.cfgManager.minPlayers);
		
		event.setQuitMessage(plugin.chat.ApplyFormat(event.getPlayer().getName(), plugin.cfgManager.messages[1]));
	}
	
	@EventHandler
	public void onBlockPlace(BlockPlaceEvent event) {
		if ((plugin.gameState != GameState.INGAME)) {
			event.setCancelled(true);
		}
		
		if (!plugin.cfgManager.blockBreakWhitelist.contains(event.getBlock().getTypeId())) {
			event.setCancelled(true);
		}
	}
	
	@EventHandler
	public void onBlockBreak(BlockBreakEvent event) {
		if ((plugin.gameState != GameState.INGAME)) {
			event.setCancelled(true);
		}
		
		if (!plugin.cfgManager.blockBreakWhitelist.contains(event.getBlock().getTypeId())) {
			event.setCancelled(true);
		}
	}
}
