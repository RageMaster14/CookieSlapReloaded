package rageteam.cookieslap.games;

import java.util.ArrayList;
import java.util.HashMap;

import org.bukkit.entity.Player;

import rageteam.cookieslap.players.CookieSlapPlayer;

public class GameUtilities {
	
	public HashMap<String, Game> GAMES = new HashMap<String, Game>();
	
	public Game getGame(String map){
		return (Game)GAMES.get(map);
	}
	
	public void addGame(String map, Game game){
		GAMES.put(map, game);
	}
	
	public CookieSlapPlayer getPlayer(Player player){
		
		if(player == null){
			return null;
		}
		
		CookieSlapPlayer cp = null;
		for(Game games : this.GAMES.values()){
			for(CookieSlapPlayer csp : games.players.values()){
				if(csp.getPlayer().getName().equalsIgnoreCase(player.getName())){
					cp = csp;
				}
			}
		}
		return cp;
	}
	
	public Game getMatchedGame(Player player){
		Game game = null;
		for(Game g : GAMES.values()){
			if(g.players.containsKey(player.getName())){
				game = g;
			}
		}
		return game;
	}
	
	public int howManyOpenGames(){
		ArrayList<Game> all = new ArrayList<Game>();
		for(Game games : this.GAMES.values()){
			
			all.add(games);
		}
		return all.size();
	}
	
	public void checkWinner(Game game){
		if(game.players.size() <= 1){
			game.cookieslap.game.stopGame(game, 0);
		} else {
			String w = "";
			for(CookieSlapPlayer cp : game.players.values()){
				w = cp.getPlayer().getName();
				game.leaveGame(cp.getUtilPlayer());
			}
			
			game.cookieslap.chat.bc("&b" + (game.cookieslap.special.contains(w) ? "§4" : "§a") + w + "&6 has won cookieslap on &c" + game.map.getName() + "&6.");
			game.cookieslap.game.stopGame(game, 5);
		}
	}
}
