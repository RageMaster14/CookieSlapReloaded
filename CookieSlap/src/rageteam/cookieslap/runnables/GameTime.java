package rageteam.cookieslap.runnables;

import rageteam.cookieslap.games.Game;
import rageteam.cookieslap.main.CookieSlap;
import rageteam.cookieslap.main.ScoreboardUtils;

public class GameTime implements Runnable{
	CookieSlap cookieslap;
	Game game;
	
	public GameTime(CookieSlap cookieslap, Game game){
		this.cookieslap = cookieslap;
		this.game = game;
	}
	
	@Override
	public void run(){
		if(game.getCount() > 0){
			cookieslap.games.checkWinner(game);
			ScoreboardUtils.get().setDisplayAll(game, "CookieSlap | " + cookieslap.game.getDigitTIme(game.getLobbyCount()));
			
			if(game.getCount() % 300 == 0) {
				cookieslap.game.ingameTimer(game.getCount(), game.getPlayers());
			}
			
			if(game.getCount() % 30 == 0 && game.getCount() < 60) {
				cookieslap.game.ingameTimer(game.getCount(), game.getPlayers());
			}
			
			if(game.getCount() <= 5 && game.getCount() >= 1){
				cookieslap.chat.bc("CookieSlap is ending in &b&1" + game.getCount() + "&6.", game);
			}
		} else {
			game.stopGameTimer();
			cookieslap.chat.bc("&dTime limit reached.", game);
			cookieslap.game.stopGame(game, 1);
		}
	}
}
