package rageteam.cookieslap.main;

import java.io.File;
import java.io.IOException;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.permissions.Permission;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;

import rageteam.cookieslap.commands.ToggleCommand;
import rageteam.cookieslap.listeners.PlayerListener;
import rageteam.cookieslap.util.Logger;

import com.sk89q.worldedit.bukkit.WorldEditPlugin;

public class CS extends JavaPlugin{
	
	//Util Classes
	public Logger logger;
	
	//Commands
	public ToggleCommand toggle;
	public CSCommandExecutor executor;
	
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
	
	//Config's
	public static File pluginFolder;
	public static File configFile;
	public static File arenaFile;
	public static File invFile;
	public static FileConfiguration cookieslapConfig;
	public static FileConfiguration arenaConfig;
	public static FileConfiguration invConfig;
	private WorldEditPlugin WorldEdit = (WorldEditPlugin) Bukkit.getServer().getPluginManager().getPlugin("WorldEdit");
	
	private void loadDependicies(){
		
		this.logger = new Logger(this);
		
		this.pListener = new PlayerListener(this);
		
		this.toggle = new ToggleCommand(this);
		this.executor = new CSCommandExecutor(this);
	}
	
	private void loadListeners(){
		getServer().getPluginManager().registerEvents(new PlayerListener(this), this);
	}
	
	private void registerCommands(){
		getCommand("cs").setExecutor(executor);
		
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
		
		obj.setDisplayName(ChatColor.GRAY +  "/toggleboard" + ChatColor.WHITE + " to hide");
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
						timeInSeconds = 240;
						time.setScore(timeInSeconds);
						timeInSeconds--;
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
		this.getServer().getPluginManager().addPermission(new Permission("cs.join"));
		this.getServer().getPluginManager().addPermission(new Permission("cs.quit"));
		this.getServer().getPluginManager().addPermission(new Permission("cs.vote"));
		this.getServer().getPluginManager().addPermission(new Permission("cs.set"));
		this.getServer().getPluginManager().addPermission(new Permission("cs.create"));
		this.getServer().getPluginManager().addPermission(new Permission("cs.reload"));
		if(WorldEdit == null){
			this.getLogger().info("WorldEdit not found!");
		} else {
			this.getLogger().info("WorldEdit found!");
		}
		
		//Config File
		pluginFolder = getDataFolder();
		configFile = new File(pluginFolder, "config.yml");
		arenaFile = new File(pluginFolder, "arenas.yml");
		cookieslapConfig = new YamlConfiguration();
		arenaConfig = new YamlConfiguration();
		
		if(!pluginFolder.exists()){
			try
			{
				pluginFolder.mkdir();
			} catch (Exception ex){
			}
		}
		
		if(!configFile.exists()){
			try
			{
				configFile.createNewFile();
			} catch (Exception ex){
			}
		}
		
		if(!arenaFile.exists())
		{
			try
			{
				arenaFile.createNewFile();
			} catch (Exception ex)
			{
			}
		}
		try
		{
			cookieslapConfig.load(configFile);
			arenaConfig.load(arenaFile);
		} catch (Exception ex){
		}
		saveConfig();
	}
		
		public void note() {
			for(Player player : Bukkit.getOnlinePlayers()){
				player.playSound(player.getLocation(), Sound.CLICK, 10, 1);
			}
	}
	
	@Override
	public void onDisable(){
	}
	
	public void saveConfig(){
		try
		{
			cookieslapConfig.save(configFile);
			arenaConfig.save(arenaFile);
		} catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	public static void setPos1(CS plugin, Player player, String arena,
			FileConfiguration arenaConfig2, Location maximumPoint) {
		// TODO Auto-generated method stub
		
	}

	public static void setPos2(CS plugin, Player player, String arena,
			FileConfiguration arenaConfig2, Location minimumPoint) {
		// TODO Auto-generated method stub
		
	}
}
