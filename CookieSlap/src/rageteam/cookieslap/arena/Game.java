package rageteam.cookieslap.arena;

import rageteam.cookieslap.main.CookieSlap;

public class Game {
	CookieSlap plugin;
	public Game(CookieSlap instance) { this.plugin = instance; }

	public static enum GameMode {
		DISABLED,LOBBY,INGAME,ENDING,NEW_ARENA,ERROR;
	}
}

//test