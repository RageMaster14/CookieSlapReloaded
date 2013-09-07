package rageteam.cookieslap.events;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

import rageteam.cookieslap.games.Status;
import rageteam.cookieslap.main.CookieSlap;
import rageteam.cookieslap.players.UtilPlayer;

public class CookieSlapEvents implements Listener {
	CookieSlap cookieslap;
	@EventHandler
	public void onKnockOut(PlayerMoveEvent e){
		Player player = e.getPlayer();
		UtilPlayer u = CookieSlap.getCookieSlap().pm.getPlayer(player);

		if (u.getGame() != null && u.isAlive()) {

		if (player.getLocation().getBlockY() < -5.0D || player.getLocation().getBlockY() < u.getGame().getLowestPossible()) {

		if (u.getGame().getStatus() == Status.INGAME) {

		cookieslap.chat.bc("Player &e" + (cookieslap.special.contains(player.getName()) ? "§4" : "§e") + player.getName() + " &6has been knocked out!", u.getGame());
		cookieslap.chat.bc("&b&l" + (u.getGame().getPlayers().size() - 1) + " PLAYERS REMAIN", u.getGame());
		player.setFallDistance(0.0F);
		u.getGame().leaveGame(u);
		player.setFallDistance(0.0F);
		cookieslap.chat.sendMessage(player, "You have been knocked out.");
				}
			}
		}
	}
}
