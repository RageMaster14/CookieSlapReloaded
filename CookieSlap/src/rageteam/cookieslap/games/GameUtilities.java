package rageteam.cookieslap.games;

import java.util.ArrayList;
import java.util.HashMap;

import org.bukkit.entity.Player;

import rageteam.cookieslap.players.CookieSlapPlayer;

public class GameUtilities {
	public HashMap<String, Game> GAMES = new HashMap<String, Game>();
	
	public Game getGame(String map) {
		return (Game) GAMES.get(map);
	}

	public void addGame(String map, Game game) {
		GAMES.put(map, game);
	}

	public CookieSlapPlayer getPlayer(Player player) {

		if (player == null) {
			return null;
		}

		CookieSlapPlayer sp = null;
		for (Game games : this.GAMES.values()) {
			for (CookieSlapPlayer sps : games.players.values()) {
				if (sps.getPlayer().getName()
						.equalsIgnoreCase(player.getName())) {
					sp = sps;
				}
			}
		}
		return sp;
	}

	public Game getMatchedGame(Player player) {
		Game game = null;
		for (Game g : GAMES.values()) {
			if (g.players.containsKey(player.getName())) {
				game = g;
			}
		}
		return game;
	}

	public int howManyOpenGames() {
		ArrayList<Game> all = new ArrayList<Game>();
		for (Game games : this.GAMES.values()) {
			if (games.getStatus() == Status.LOBBY) {
				all.add(games);
			}
		}

		return all.size();
	}

	public void checkWinner(Game game) {
		if (game.players.size() <= 1) {
			if (game.players.size() == 0) {
				game.cookieslap.game.stopGame(game, 0);
			} else {
				String w = "";
				for (CookieSlapPlayer sp : game.players.values()) {
					w = sp.getPlayer().getName();
					game.leaveGame(sp.getUtilPlayer());
				}

				game.cookieslap.chat.bc("&b"
						+ (game.cookieslap.special.contains(w) ? "§4" : "§a") + w
						+ "&6 has won CookieSlap on &c" + game.map.getName()
						+ "&6.");
				game.cookieslap.game.stopGame(game, 5);
			}
		}
	}
}
