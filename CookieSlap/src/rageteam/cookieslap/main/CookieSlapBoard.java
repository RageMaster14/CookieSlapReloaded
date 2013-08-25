package rageteam.cookieslap.main;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;

import rageteam.cookieslap.games.Game;

public class CookieSlapBoard extends JavaPlugin{
	public ScoreboardManager manager;
	public Scoreboard board;
	public Objective obj;
	public Game game;
	
	//Integers
	public int timeLeft = 240;
	
	public CookieSlapBoard(){
		setup();
	}
	
	public void setup(){
		manager = Bukkit.getScoreboardManager();
		board = manager.getNewScoreboard();
		obj = board.registerNewObjective("CookieSlap", "dummy");
		
		obj.setDisplaySlot(DisplaySlot.SIDEBAR);
		obj.setDisplayName(ChatColor.GRAY + "/toggleboard" + ChatColor.WHITE + " To Hide");
	}
	
	public void enableBoard(){

		final Score time = obj.getScore(Bukkit.getOfflinePlayer(ChatColor.LIGHT_PURPLE + "Time Left: "));
		Bukkit.getScheduler().scheduleSyncRepeatingTask(this, new Runnable(){
			public void run(){
				if(timeLeft != -1){
					if(timeLeft != 0){
						time.setScore(timeLeft);
						timeLeft--;
					} else if(timeLeft == 0){
						time.setScore(0);
						timeLeft--;
					}
				}
			}
		}, 0L, 20L);
		
		Score ingame = obj.getScore(Bukkit.getOfflinePlayer(ChatColor.DARK_AQUA + "Players: "));
		ingame.setScore(game.getPlayers().size());
		
		Score arenaId = obj.getScore(Bukkit.getOfflinePlayer(ChatColor.GREEN + "Area #"));
		arenaId.setScore(game.getMap().getConfig().getInt(""));
	}
	
	public Scoreboard getScoreboard() {
		if (board == null) {
			setup();
		}	
		return board;
	}

	private Objective getSidebar() {
		return	getScoreboard().getObjective("side");
	}

	public void setDisplayName(String string) {
		getSidebar().setDisplayName(string);
	}

	public void setScore(String name, int score) {
		getSidebar().getScore(Bukkit.getOfflinePlayer(name)).setScore(score);
	}

	public void hideScore(String name) {
		getScoreboard().resetScores(Bukkit.getOfflinePlayer(name));
	}
}
