package rageteam.cookieslap.events;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import rageteam.cookieslap.main.CookieSlap;
import rageteam.cookieslap.players.UtilPlayer;

public class PlayerListener implements Listener{
	
	@EventHandler
	public void onFood(FoodLevelChangeEvent e){
		if(e.getEntity() instanceof Player){
			Player player = (Player) e.getEntity();
			UtilPlayer u = CookieSlap.getCookieSlap().pm.getPlayer(player);
			if(u.getGame() != null && u.isAlive()){
				e.setCancelled(true);
				e.setFoodLevel(20);
			}
		}
	}
	
	@EventHandler
	public void onInvClick(InventoryClickEvent e){
		if(e.getWhoClicked() != null && e.getWhoClicked() instanceof Player){
			Player player = (Player)e.getWhoClicked();
			UtilPlayer u = CookieSlap.getCookieSlap().pm.getPlayer(player);
			
			if(u.getGame() != null && u.isAlive()){
				e.setCancelled(true);
				CookieSlap.getCookieSlap().chat.sendMessage(player, "&eYou cannot edit your inventory while you're in game.");
				
			}
		}
	}
	
	@EventHandler
	public void onJoin(PlayerJoinEvent e){
		Player player = e.getPlayer();
		UtilPlayer u = new UtilPlayer(player);
		CookieSlap.getCookieSlap().pm.PLAYERS.put(player.getName(), u);
	}
	
	@EventHandler
	public void onQuit(PlayerQuitEvent e){
		Player player = e.getPlayer();
		UtilPlayer u = CookieSlap.getCookieSlap().pm.getPlayer(player);
		if(u.getGame() != null && u.isAlive()){
			u.getGame().leaveGame(u);
		}
		
		CookieSlap.getCookieSlap().pm.PLAYERS.remove(player.getName());
	}
	
	@EventHandler
	public void dropItem(PlayerDropItemEvent e){
		Player player = e.getPlayer();
		UtilPlayer u = CookieSlap.getCookieSlap().pm.getPlayer(player);
		
		if(u.getGame() != null && u.isAlive()){
			e.setCancelled(true);
		}
	}
	
	String[] cmds = {""};
	
	@EventHandler
	public void onCommand(PlayerCommandPreprocessEvent e){
		Player player = e.getPlayer();
		UtilPlayer u = CookieSlap.getCookieSlap().pm.getPlayer(player);
		
		if(u.getGame() != null && u.isAlive()){
			if(!e.getMessage().startsWith("/cslap") && !(player.isOp() || player.hasPermission("cookieslap.admin"))){
				e.setCancelled(true);
				CookieSlap.getCookieSlap().chat.sendMessage(player, "&eYou cannot use commands in &bCookieSlap");
			}
		}
	}
}
