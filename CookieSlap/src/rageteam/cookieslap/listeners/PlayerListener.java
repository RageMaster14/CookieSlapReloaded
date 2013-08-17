package rageteam.cookieslap.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import rageteam.cookieslap.main.CS;

public class PlayerListener implements Listener{
	
	CS plugin;
	public PlayerListener(CS instance) { this.plugin = instance; }
	
	@EventHandler
	public void onJoin(PlayerJoinEvent e){
		e.getPlayer().setScoreboard(plugin.stats.board);
	}
}
