package rageteam.cookieslap.events;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.hanging.HangingBreakByEntityEvent;

import rageteam.cookieslap.main.CookieSlap;
import rageteam.cookieslap.players.UtilPlayer;

public class MapListener implements Listener{
	public CookieSlap cookieslap;
	
	public MapListener(CookieSlap c){
		this.cookieslap = c;
	}
	
	@EventHandler
	public void blockBreak(BlockBreakEvent e) {
		Player player = e.getPlayer();
		UtilPlayer u = cookieslap.pm.getPlayer(player);
		
		if(u.getGame() != null){
			if(u.isAlive()){
				e.setCancelled(true);
			}
		}
	}
	
	@EventHandler
	public void blockPlace(BlockPlaceEvent e){
		Player player = e.getPlayer();
		UtilPlayer u = cookieslap.pm.getPlayer(player);
		
		if(u.getGame() != null){
			if(u.isAlive()){
				e.setCancelled(true);
			}
		}
	}
	
	@EventHandler
	public void hangingEntityBreak(HangingBreakByEntityEvent e){
		Player player = (Player)e.getRemover();
		UtilPlayer u = cookieslap.pm.getPlayer(player);
		
		if(u.getGame() != null){
			if(u.isAlive()){
				e.setCancelled(true);
			}
		}
	}
}
