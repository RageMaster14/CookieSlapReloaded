package rageteam.cookieslap.main;



import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;

import rageteam.cookieslap.commands.ToggleCommand;
import rageteam.cookieslap.util.Logger;

public class CS extends JavaPlugin{
	

	//Util Classes
	public Logger logger;
	
	//Scoreboard
	public ScoreboardManager manager;
	public Scoreboard ingame;
	public Objective obj;
	public Scoreboard board;
	
	//Integers
	public int timeLeft = 240;
	public int players = 0;
	public int highScore = 0;
	public int arena = 0;

	public int win = 0;
	public int lose = 0;
	
	//Commands
	public ToggleCommand toggleCmd;

	public void loadDepdencies(){
		this.logger = new Logger(this);
		
		this.toggleCmd = new ToggleCommand(this);
	}
	
	public void loadCommands(){
		getCommand("toggleboard").setExecutor(toggleCmd);
	}
	
	@Override
	public void onEnable(){
		//Loads The Classes
		loadDepdencies();
		loadCommands();
		
		//Setting Up The In-Game Scoreboard
		manager = Bukkit.getScoreboardManager();
		ingame = manager.getNewScoreboard();
		obj = ingame.registerNewObjective("CookieSlap", "dummy");
		
		//Scoreboard slot and Scoreboard name
		obj.setDisplaySlot(DisplaySlot.SIDEBAR);
		obj.setDisplayName(ChatColor.GRAY + "/toggleboard" + ChatColor.WHITE + " to hide");
		
		//In-Game Board
		final Score time = obj.getScore(Bukkit.getOfflinePlayer(ChatColor.LIGHT_PURPLE + "Time Left:" + ChatColor.RED));
		getServer().getScheduler().scheduleSyncRepeatingTask(this, new Runnable(){
			public void run(){
				if(timeLeft != -1){
					if(timeLeft != 0){
						timeLeft--;
						time.setScore(timeLeft);
					} else if(timeLeft == 0){
						time.setScore(0);
					}
				}
			}
		}, 0L, 20L);
		
		Score online = obj.getScore(Bukkit.getOfflinePlayer(ChatColor.DARK_AQUA + "Players:" + ChatColor.RED));
		online.setScore(players);
		
		Score hScore = obj.getScore(Bukkit.getOfflinePlayer(ChatColor.YELLOW + "HighScore:" + ChatColor.RED));
		hScore.setScore(highScore);
		
		Score arenas = obj.getScore(Bukkit.getOfflinePlayer(ChatColor.GREEN + "Arena ID:" + ChatColor.RED));
		arenas.setScore(arena);
		
		//Stats Scoreboard
		manager = Bukkit.getScoreboardManager();
		board = manager.getNewScoreboard();
		obj = board.registerNewObjective("Stats", "dummy");
		
		//Scoreboard Slot and Scoreboard name
		obj.setDisplaySlot(DisplaySlot.SIDEBAR);
		obj.setDisplayName(ChatColor.WHITE + "" + ChatColor.BOLD + "Stats");
		
		//Scores
		Score wins = obj.getScore(Bukkit.getOfflinePlayer(ChatColor.BLUE + "Wins:" + ChatColor.WHITE));
		wins.setScore(win);
		
		Score loses = obj.getScore(Bukkit.getOfflinePlayer(ChatColor.RED + "Loses:" + ChatColor.WHITE));
		loses.setScore(lose);
		
		for(Player player : Bukkit.getOnlinePlayers()){
			player.setScoreboard(board);
		}
	}
	
	public void onDisable(){
		
	}
	
	@EventHandler
	public 
}
