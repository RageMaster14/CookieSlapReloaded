package RageTeam.cookieslap.main;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;

public class InfoSystem{	
	public static Scoreboard main;
	
	//Scores
	public static int timeInSeconds = 0;
	public static int players;
	public static int topScore = 0;
	public static int arenaID = 0;
	
	public void info(){
		ScoreboardManager manager = Bukkit.getScoreboardManager();
		main = manager.getNewScoreboard();
		Objective obj = main.registerNewObjective("CookieSlap", "dummie");
		obj.setDisplayName(ChatColor.GOLD + "" + ChatColor.BOLD + "CookieSlap");
		obj.setDisplaySlot(DisplaySlot.SIDEBAR);
		Score score1 = obj.getScore(Bukkit.getOfflinePlayer(ChatColor.LIGHT_PURPLE + "Time Left:" + ChatColor.RED));
		score1.setScore(timeInSeconds);
		
		Score score2= obj.getScore(Bukkit.getOfflinePlayer(ChatColor.BLUE + "Players:" + ChatColor.RED));
		score2.setScore(players);
		
		Score score3 = obj.getScore(Bukkit.getOfflinePlayer(ChatColor.YELLOW + "High Score:" + ChatColor.RED));
		score3.setScore(topScore);
		
		Score score4 = obj.getScore(Bukkit.getOfflinePlayer(ChatColor.GREEN + "Arena Number:" + ChatColor.RED));
		score4.setScore(arenaID);
	}
}