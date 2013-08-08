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

import rageteam.cookieslap.commands.ToggleCommand;
import rageteam.cookieslap.listeners.PlayerListener;
import rageteam.cookieslap.util.Logger;

public class CS extends JavaPlugin{
	
	//Util Classes
	public Logger logger;
	
	//Commands
	public ToggleCommand toggle;
	
	//Scoreboard
	public ScoreboardManager manager;
	public Scoreboard board;
	public Objective obj;
	public int timeInSeconds = 240;
	public int onlinePlayers = Bukkit.getServer().getOnlinePlayers().length;
	public int highScore = 0;
	public int arenaID = 0;
	
	//Listeners
	public PlayerListener pListener;
	
	private void loadDependicies(){
		
		this.logger = new Logger(this);
		
		this.pListener = new PlayerListener(this);
		
		this.toggle = new ToggleCommand(this);
	
	}
	
	private void loadListeners(){
		getServer().getPluginManager().registerEvents(pListener, this);
	}
	
	private void registerCommands(){
		getCommand("toggleboard").setExecutor(toggle);
		
	}
	
	@Override
	public void onEnable(){
		loadDependicies();
		loadListeners();
		registerCommands();
		
		logger.log(false, "Loading CS Dependicies");
		
		//ScoreBoard
		manager = Bukkit.getScoreboardManager();
		board = manager.getNewScoreboard();
		obj = board.registerNewObjective("CookieSlap", "dummy");
		
		obj.setDisplayName(ChatColor.GRAY +  "  /toggleboard  " + ChatColor.WHITE + " to hide");
		obj.setDisplaySlot(DisplaySlot.SIDEBAR);
		
		final Score time = obj.getScore(Bukkit.getOfflinePlayer(ChatColor.LIGHT_PURPLE + "Time Left:" + ChatColor.RED));
		getServer().getScheduler().scheduleSyncRepeatingTask(this, new Runnable(){
			public void run(){
				if(timeInSeconds != -1){
					if(timeInSeconds != 0){
						time.setScore(timeInSeconds);
						timeInSeconds--;
					} if(timeInSeconds <= 10 && timeInSeconds > 0){
						note();
					} else if(timeInSeconds == 0){
						time.setScore(0);
					}
				}
			}
		}, 0L, 20L);
		
		Score players = obj.getScore(Bukkit.getOfflinePlayer(ChatColor.DARK_AQUA + "Players:" + ChatColor.RED));
		players.setScore(onlinePlayers);
		
		Score arena = obj.getScore(Bukkit.getOfflinePlayer(ChatColor.GREEN + "Arena ID:" + ChatColor.RED));
		arena.setScore(arenaID);
		
		Score score = obj.getScore(Bukkit.getOfflinePlayer(ChatColor.YELLOW + "HighScore:" + ChatColor.RED));
		score.setScore(highScore);
		
		for(Player player : Bukkit.getOnlinePlayers()){
			player.setScoreboard(board);
		}
	}
		
		public void note() {
			for(Player player : Bukkit.getOnlinePlayers()){
				player.playSound(player.getLocation(), Sound.CLICK, 10, 1);
			}
	}
	
	@Override
	public void onDisable(){
		
	}

}
