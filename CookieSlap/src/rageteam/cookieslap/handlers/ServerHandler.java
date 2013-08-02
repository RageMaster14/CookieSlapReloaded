package rageteam.cookieslap.handlers;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.ServerListPingEvent;

import rageteam.cookieslap.objects.GameState;
import rageteam.cookieslap.main.CS;

public class ServerHandler implements Listener {
	CS plugin;
	public ServerHandler(CS instance) {this.plugin = instance;}
	
	@EventHandler
	public void onServerListPing(ServerListPingEvent event) {
		String motd = "";
		if (plugin.gameState == GameState.LOBBY) {
			motd = plugin.cfgManager.motds[0];
		} else if (plugin.gameState == GameState.INGAME) {
			motd = plugin.cfgManager.motds[1];
		} else if (plugin.gameState == GameState.ENDGAME) {
			motd = plugin.cfgManager.motds[2];
		}		
		event.setMotd(plugin.chat.Colourize(motd));
	}
}
