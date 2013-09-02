package rageteam.cookieslap.events;

import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

import rageteam.cookieslap.games.Game;
import rageteam.cookieslap.games.Status;
import rageteam.cookieslap.main.CookieSlap;
import rageteam.cookieslap.maps.Map;
import rageteam.cookieslap.players.CookieSlapPlayer;
import rageteam.cookieslap.players.UtilPlayer;

public class CookieSlapEvents implements Listener {
	Map map;
	Game game;
	@EventHandler
	public void onKnockOut(PlayerMoveEvent e){
		Player player = e.getPlayer();
		UtilPlayer u = CookieSlap.getCookieSlap().pm.getPlayer(player);
		
		if(u.getGame() != null && u.isAlive()){
			if(player.getLocation().getBlockY() < -5.0D || player.getLocation().getBlockY() < u.getGame().getLowestPossible()){
				if(u.getGame().getStatus() == Status.INGAME){
					CookieSlap.getCookieSlap().chat.bc("Player &e" + (CookieSlap.getCookieSlap().special.contains(player.getName()) ? "§4" : "§3") + player.getName() + " &6has been knocked off!", u.getGame());
					player.setFallDistance(0.0F);
					player.setFallDistance(0.0F);
					int c = 1;
					for (CookieSlapPlayer sp : game.players.values()) {

						sp.getPlayer().setLevel(0);

						sp.getUtilPlayer().setAlive(true);

						if (c > map.getSpawnCount()) {
						c = 1;
						}

						sp.getPlayer().teleport(map.getSpawn(c));
						c++;

						sp.getPlayer().setLevel(0);
						sp.getPlayer().setGameMode(GameMode.ADVENTURE);
					}
				}
			}
		}
	}
}
