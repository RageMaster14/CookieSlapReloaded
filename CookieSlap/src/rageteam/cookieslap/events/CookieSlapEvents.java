package rageteam.cookieslap.events;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

import rageteam.cookieslap.main.CookieSlap;
import rageteam.cookieslap.players.UtilPlayer;

public class CookieSlapEvents implements Listener {
	
	@EventHandler
	public void onKnockOut(PlayerMoveEvent e){
		Player player = e.getPlayer();
		UtilPlayer u = CookieSlap.getCookieSlap().pm.getPlayer(player);
		
		if(u.getGame() != null && u.isAlive()){
			if(player.getLocation().getBlockY() < -5.0D || player.getLocation().getBlockY() < u.getGame().getLowestPossible()){
				CookieSlap.getCookieSlap().chat.bc("Player &e" + (CookieSlap.getCookieSlap().special.contains(player.getName()) ? "§4" : "§3"));
				CookieSlap.getCookieSlap().chat.bc("&b&l" + (u.getGame().getPlayers().size() - 1) + " PLAYERS REMAIN", u.getGame());
				player.setFallDistance(0.0F);
				u.getGame().leaveGame(u);
				player.setFallDistance(0.0F);
				CookieSlap.getCookieSlap().chat.sendMessage(player, "You have been knoced out.");
			}
		}
	}
}
