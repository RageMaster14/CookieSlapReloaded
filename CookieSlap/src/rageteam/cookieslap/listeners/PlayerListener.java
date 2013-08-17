package rageteam.cookieslap.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import rageteam.cookieslap.main.CookieSlap;

public class PlayerListener implements Listener{
	
	CookieSlap plugin;
	public PlayerListener(CookieSlap instance) { this.plugin = instance; }
	
	@EventHandler
	public void onJoin(PlayerJoinEvent e){
		e.getPlayer().setScoreboard(plugin.stats.board);
	}
}
