package rageteam.cookieslap.main;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;

public class CookieSlapBoard extends JavaPlugin{
	
	CS plugin;
	public CookieSlapBoard(CS instance) {this.plugin = instance; }
	
	public int timeInSeconds = 240;
	public int onlinePlayers = getServer().getOnlinePlayers().length;
	public int highScore = 0;
	public int arenaID = 0;
	
	ScoreboardManager manager;
	Scoreboard board;
	Objective obj;
	
	public void gameBoard(){
		manager = Bukkit.getScoreboardManager();
		board = manager.getNewScoreboard();
		obj = board.registerNewObjective("CookieSlap", "dummy");
		
		obj.setDisplayName(ChatColor.GRAY +  "  /toggleboard  " + ChatColor.WHITE + " to hide");
		obj.setDisplaySlot(DisplaySlot.SIDEBAR);
		
		final Score time = obj.getScore(getServer().getOfflinePlayer(ChatColor.LIGHT_PURPLE + "Time Left:" + ChatColor.RED));
		getServer().getScheduler().scheduleSyncRepeatingTask(plugin, new Runnable(){
			public void run(){
				if(timeInSeconds != -1){
					if(timeInSeconds != 0){
						time.setScore(timeInSeconds);
					} else if(timeInSeconds <= 10 && timeInSeconds > 0){
						note();
					}
				}
			}
		}, 0L, 20L);
		
		Score online = obj.getScore(getServer().getOfflinePlayer(ChatColor.DARK_AQUA + "Players:" + ChatColor.RED));
		online.setScore(onlinePlayers);
		
		Score highscore = obj.getScore(getServer().getOfflinePlayer(ChatColor.YELLOW + "HighScore:" + ChatColor.RED));
		highscore.setScore(highScore);
		
		Score arenaNumber = obj.getScore(getServer().getOfflinePlayer(ChatColor.GREEN + "Arena Number:" + ChatColor.RED));
		arenaNumber.setScore(arenaID);
	}

	private void note() {
		for(Player player : getServer().getOnlinePlayers()){
			player.playSound(player.getLocation(), Sound.NOTE_PIANO, 10, 1);
		}
	}
	
	public void enable(){
		for(Player player : getServer().getOnlinePlayers()){
			player.setScoreboard(board);
		}
	}
}
