package rageteam.cookieslap.main;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;

public class StatsBoard {
	CS plugin;
	public StatsBoard(CS instance) { this.plugin = instance; }
	
	public ScoreboardManager manager;
	public Scoreboard board;
	public Objective obj;
	
	//Integers
	public int wins = 0;
	public int scores = 0;
	public int ranks = 0;
	
	public void stats(){
		
		manager = Bukkit.getScoreboardManager();
		board = manager.getNewScoreboard();
		obj = board.registerNewObjective("Stats", "dummy");
		
		obj.setDisplaySlot(DisplaySlot.SIDEBAR);
		obj.setDisplayName(ChatColor.WHITE + "" + ChatColor.BOLD + "Stats");
		
		Score rank = obj.getScore(Bukkit.getOfflinePlayer(ChatColor.GOLD + "Rank:"));
		rank.setScore(ranks);
		
		Score win = obj.getScore(Bukkit.getOfflinePlayer(ChatColor.GREEN + "Wins:"));
		win.setScore(wins);
		
		Score score = obj.getScore(Bukkit.getOfflinePlayer(ChatColor.AQUA + "Score:"));
		score.setScore(scores);
		
		for(Player player : Bukkit.getOnlinePlayers()){
			player.setScoreboard(board);
		}
	}
}
