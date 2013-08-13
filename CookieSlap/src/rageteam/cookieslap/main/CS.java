package rageteam.cookieslap.main;

import java.io.File;
import java.io.IOException;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;

import rageteam.cookieslap.commands.ToggleCommand;
import rageteam.cookieslap.util.Logger;

import com.sk89q.worldedit.bukkit.WorldEditPlugin;

public class CS extends JavaPlugin{
	
	//Util Classes
	public Logger logger;
	
	//Scoreboard
	public ScoreboardManager manager;
	public Scoreboard board;
	public Objective obj;
	
	//Integers
	public int timeLeft = 240;
	public int players = Bukkit.getServer().getOnlinePlayers().length;
	public int highScore = 0;
	public int arena = 0;
	
	//Commands
	public ToggleCommand toggleCmd;
	
	//Config Stuff
	private static File pluginFolder;
	private static File configFile;
	private static File arenaFile;
	private static File invFile;
	public static FileConfiguration cookieslapConfig;
	public static FileConfiguration arenaConfig;
	public static FileConfiguration invConfig;
	private WorldEditPlugin WorldEdit = (WorldEditPlugin) Bukkit.getServer().getPluginManager().getPlugin("WolrdEdit");
	
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
		
		//Setting Up The Scoreboard
		manager = Bukkit.getScoreboardManager();
		board = manager.getNewScoreboard();
		obj = board.registerNewObjective("CookieSlap", "dummy");
		
		//Scoreboard slot and Scoreboard name
		obj.setDisplaySlot(DisplaySlot.SIDEBAR);
		obj.setDisplayName(ChatColor.GRAY + "/toggleboard" + ChatColor.WHITE + " to hide");
		
		//Scores
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
		
		pluginFolder = getDataFolder();
		configFile = new File(pluginFolder, "config.yml");
		arenaFile = new File(pluginFolder, "arenas.yml");
		invFile = new File(pluginFolder, "inventories.yml");
		cookieslapConfig = new YamlConfiguration();
		arenaConfig = new YamlConfiguration();
		invConfig = new YamlConfiguration();
		
		if(WorldEdit == null){
			this.getLogger().info("WolrdEdit Not Found!");
		} else {
			this.getLogger().info("WorldEdit Found!");
		}
		
		if(!pluginFolder.exists()){
			try
			{
				pluginFolder.mkdir();
			} catch (Exception ex){
			}
		}if(!configFile.exists()){
			try
			{
				configFile.createNewFile();
			} catch (Exception ex){
			}
		}if(!arenaFile.exists()){
			try
			{
				arenaFile.createNewFile();
			} catch (Exception ex){
			}
		}if(!invFile.exists()){
			try
			{
				invFile.createNewFile();
			} catch (Exception ex){
			}
		}
		try
		{
			cookieslapConfig.load(configFile);
			arenaConfig.load(arenaFile);
			invConfig.load(invFile);
		} catch (Exception ex){
		}
		
		saveConfig();
	}
	
	public void onDisable(){
		
	}
	
	public void saveConfig(){
		try
		{
			cookieslapConfig.save(configFile);
			arenaConfig.save(arenaFile);
			invConfig.save(invFile);
		} catch (IOException e)
		{
			e.printStackTrace();
		}
	}
}
