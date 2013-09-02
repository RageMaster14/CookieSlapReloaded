package rageteam.cookieslap.runnables;

import org.bukkit.ChatColor;

import rageteam.cookieslap.games.Game;
import rageteam.cookieslap.main.CookieSlap;
import rageteam.cookieslap.main.ScoreboardUtils;

public class GameTime implements Runnable {
	CookieSlap splegg;
	Game game;
	int timeleft = 240;

	public GameTime(CookieSlap splegg, Game game) {
		this.splegg = splegg;
		this.game = game;
	}

	@Override
	public void run() {
		if(game.getCount() != -1){
			if(game.getCount() != 0){
				ScoreboardUtils.get().setScoreAll(game, ChatColor.LIGHT_PURPLE + "Time Left:", game.getCount());
			} 
		} else {

			game.stopGameTimer();
			splegg.chat.bc("&dTime limit reached.", game);
			splegg.game.stopGame(game, 1);

		}

	}
}
