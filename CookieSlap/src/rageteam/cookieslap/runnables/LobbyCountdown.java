package rageteam.cookieslap.runnables;

import org.bukkit.Bukkit;

import rageteam.cookieslap.games.Game;
import rageteam.cookieslap.main.CookieSlap;
import rageteam.cookieslap.main.ScoreboardUtils;
import rageteam.cookieslap.players.CookieSlapPlayer;

public class LobbyCountdown implements Runnable{
	
	int lobbycount;
	Game game;
	
	public LobbyCountdown(Game game, int lobbycount){
		this.game = game;
		this.lobbycount = lobbycount;
	}
	
	public void run(){
		if(lobbycount >= 1){
			lobbycount--;
			game.setLobbyCount(lobbycount);
			
			for(CookieSlapPlayer cp : game.getPlayers().values()){
				cp.getPlayer().setLevel(lobbycount);
				cp.getScoreboard().setScore("Starting in", game.getLobbyCount());
			}
			
			ScoreboardUtils.get().setScoreAll(game, "Queue", game.getPlayers().size());
			
			game.getSign().update(game.getMap(), false);
			
			if(lobbycount % 10 == 0){
				CookieSlap.getCookieSlap().chat.bc("CookieSlap starting in &b" + lobbycount + "&6.", game);
			}
			if((lobbycount <= 5) && (lobbycount >= 1) && (lobbycount != 0)){
				CookieSlap.getCookieSlap().chat.bc("CookieSlap starting in &b" + lobbycount + "&6.", game);
			}
		} else {
			if(game.getPlayers().size() > 1){
				Bukkit.getScheduler().cancelTask(game.getCounterID());
				CookieSlap.getCookieSlap().game.startGame(game);
			} else {
				CookieSlap.getCookieSlap().chat.bc("&cNot Enough Players", game);
				Bukkit.getScheduler().cancelTask(game.getCounterID());
				game.setLobbyCount(31);
				game.setStarting(false);
				game.getSign().update(game.getMap(), false);
			}
		}
	}
}
