package rageteam.cookieslap.events;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.player.PlayerInteractEvent;

import rageteam.cookieslap.lobby.LobbySign;
import rageteam.cookieslap.lobby.LobbySignUtils;
import rageteam.cookieslap.main.CookieSlap;
import rageteam.cookieslap.misc.Permissions;

public class SignListener implements Listener{
	
	@EventHandler(priority = EventPriority.HIGHEST)
	public void signPlace(SignChangeEvent e){
		Player player = e.getPlayer();
		Permissions perms = new Permissions(player);
		
		if(e.getLine(0).equalsIgnoreCase("[CS]") && perms.canModifyMaps()){
			String map = e.getLine(1);
			if(CookieSlap.getCookieSlap().maps.mapExists(map)){
				LobbySign ls = new LobbySign(CookieSlap.getCookieSlap().maps.getMap(map), CookieSlap.getCookieSlap());
				ls.create(e.getBlock().getLocation(), CookieSlap.getCookieSlap().maps.getMap(map));
				CookieSlap.getCookieSlap().chat.sendMessage(player, "You have created a lobby sign for §b" + map + "&6.");
			}
		}
	}
	
	@EventHandler
	public void onInteract(PlayerInteractEvent e){
		
		if ((e.hasBlock()) && ((e.getClickedBlock().getType() == Material.WALL_SIGN) || (e.getClickedBlock().getType() == Material.SIGN) || (e.getClickedBlock().getType() == Material.SIGN_POST)) && (e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK)) {
			Sign s = (Sign) e.getClickedBlock().getState();
			Player player = e.getPlayer();
			if(s.getLine(0).equalsIgnoreCase(ChatColor.GOLD + "[CookieSlap]")){
				String map = ChatColor.stripColor(s.getLine(1));
				if(CookieSlap.getCookieSlap().maps.mapExists(map)){
					player.chat("/cs join " + map);
					e.setCancelled(true);
				} else {
					CookieSlap.getCookieSlap().chat.sendMessage(player, "&cMap does not exist!");
					e.setCancelled(true);
				}
			}
		}
	}
	
	@EventHandler
	public void signBreak(BlockBreakEvent e){
		Player player = e.getPlayer();
		if(e.getBlock().getType() == Material.WALL_SIGN || e.getBlock().getType() == Material.SIGN_POST){
			Sign s = (Sign)e.getBlock().getState();
			String[] lines = s.getLines();
			
			String map = ChatColor.stripColor(lines[1]);
			
			if(LobbySignUtils.get().isLobbySign(e.getBlock().getLocation(), map)){
				Permissions perms = new Permissions(player);
				if(perms.canModifyMaps()){
					LobbySign signs = new LobbySign(CookieSlap.getCookieSlap().maps.getMap(map), CookieSlap.getCookieSlap());
					signs.delete(e.getBlock().getLocation());
					CookieSlap.getCookieSlap().chat.sendMessage(player, "You have successfully remove a cookieslap sign for the map &c" + map + "&6.");
				} else {
					e.setCancelled(true);
					CookieSlap.getCookieSlap().chat.sendMessage(player, "You cannot remove that sign");
				}
			}
		}
	}
}
