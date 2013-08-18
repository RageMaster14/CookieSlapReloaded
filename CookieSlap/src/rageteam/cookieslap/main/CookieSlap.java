package rageteam.cookieslap.main;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;

import rageteam.cookieslap.arena.Game;
import rageteam.cookieslap.arena.GameTime;
import rageteam.cookieslap.commands.ToggleCommand;
import rageteam.cookieslap.economy.Points;
import rageteam.cookieslap.economy.PopupMenu;
import rageteam.cookieslap.economy.PopupMenuAPI;
import rageteam.cookieslap.listeners.PlayerListener;
import rageteam.cookieslap.util.Logger;

public class CookieSlap extends JavaPlugin{
	
	//Util Classes
	public Logger logger;
	
	//Scoreboard
	public ScoreboardManager manager;
	public Objective obj;
	public Scoreboard ingame;
	public StatsBoard stats;
	
	//Listeners
	public PlayerListener pListener;
	
	//Integers
	public int timeLeft = 240;
	public int players = 0;
	public int highScore = 0;
	public int arena = 0;
	
	//Config
	
	//Commands
	public ToggleCommand toggleCmd;
	
	//Arena
	public Game game;
	public GameTime gameTime;
	
	//Economy
	public Points points;
	public PopupMenu popupMenu;
	public PopupMenuAPI popupApi;

	public void loadDepdencies(){
		this.logger = new Logger(this);
		
		this.toggleCmd = new ToggleCommand(this);
		
		this.stats = new StatsBoard(this);
		
		this.pListener = new PlayerListener(this);
		
		this.game = new Game(this);
		this.gameTime = new GameTime(this);
		
		this.points = new Points(this);
		this.popupMenu = new PopupMenu(this);
		this.popupApi = new PopupMenuAPI(this);
	}
	
	public void loadCommands(){
		getCommand("toggleboard").setExecutor(toggleCmd);
	}
	
	public void loadListeners(){
		getServer().getPluginManager().registerEvents(pListener, this);
		getServer().getPluginManager().registerEvents(popupApi, this);
	}
	
	@Override
	public void onEnable(){
		loadDepdencies();
		loadCommands();
		loadListeners();
		
		//In-Game Scoreboard
		manager = Bukkit.getScoreboardManager();
		ingame = manager.getNewScoreboard();
		obj = ingame.registerNewObjective("CookieSlap", "dummy");
					
		obj.setDisplaySlot(DisplaySlot.SIDEBAR);
		obj.setDisplayName(ChatColor.GRAY + "/toggleboard" + ChatColor.WHITE + " to hide");
						
		final Score time = obj.getScore(Bukkit.getOfflinePlayer(ChatColor.LIGHT_PURPLE + "Time Left:" + ChatColor.RED));
		getServer().getScheduler().scheduleSyncRepeatingTask(this, new Runnable(){
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
						
		Score online = obj.getScore(Bukkit.getOfflinePlayer(ChatColor.DARK_AQUA + "Players:" + ChatColor.RED));
		online.setScore(players);
						
		Score hScore = obj.getScore(Bukkit.getOfflinePlayer(ChatColor.YELLOW + "HighScore:" + ChatColor.RED));
		hScore.setScore(highScore);
						
		Score arenas = obj.getScore(Bukkit.getOfflinePlayer(ChatColor.GREEN + "Arena ID:" + ChatColor.RED));
		arenas.setScore(arena);
		
		stats.stats();
	}
	
	public void onDisable(){
	}
}
