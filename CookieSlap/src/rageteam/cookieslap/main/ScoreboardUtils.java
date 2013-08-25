package rageteam.cookieslap.main;

import rageteam.cookieslap.games.Game;
import rageteam.cookieslap.players.CookieSlapPlayer;

public class ScoreboardUtils {
	public static ScoreboardUtils ins = new ScoreboardUtils();
	
	public static ScoreboardUtils get(){
		return ins;
	}
	
	public void setDisplayAll(Game game, String name){
		for(CookieSlapPlayer cp : game.getPlayers().values()){
			cp.getScoreboard().setDisplayName(name);
		}
	}
	
	public void setScoreAll(Game game,String iden, int score){
		for(CookieSlapPlayer cp : game.getPlayers().values()){
			cp.getScoreboard().setScore(iden, score);
		}
	}
	
	public void hideScoreAll(Game game, String iden){
		for(CookieSlapPlayer cp : game.getPlayers().values()){
			cp.getScoreboard().hideScore(iden);
		}
	}
}
