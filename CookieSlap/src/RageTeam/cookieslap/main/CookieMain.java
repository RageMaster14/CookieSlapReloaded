package RageTeam.cookieslap.main;

import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;

public class CookieMain extends JavaPlugin{
	public final Logger logger = Logger.getLogger("Minecraft");

	//Objects
	public Object chat;
	public Object playerManager;
	public Object cfgManager;
	
	public static int players = Bukkit.getOnlinePlayers().length;
	public int timeInSeconds = 240;
	
	public static CookieMain plugin;
	
	ScoreboardManager manager;
	Scoreboard board;
	Objective obj;
	
	
	@Override
	public void onDisable(){
		PluginDescriptionFile pdf = this.getDescription();
		this.logger.info(pdf.getName() + " v" + pdf.getVersion() + " Has Been Disabled!");
		
	}
	
	@Override
	public void onEnable(){
		PluginDescriptionFile pdf = this.getDescription();
		this.logger.info(pdf.getName() + " v" + pdf.getVersion() + " Has Been Enabled!");
		
		manager = Bukkit.getScoreboardManager();
		board = manager.getNewScoreboard();
		obj = board.registerNewObjective("CookieSlap", "dummy");
		
		obj.setDisplaySlot(DisplaySlot.SIDEBAR);
		obj.setDisplayName(ChatColor.GOLD + "" + ChatColor.BOLD + " CookieSlap ");
		
		Score time = obj.getScore(Bukkit.getOfflinePlayer(ChatColor.LIGHT_PURPLE + "Time Left:" + ChatColor.RED));
		if(timeInSeconds >= 240){
			time.setScore(timeInSeconds);
			timeInSeconds--;
		}else if(timeInSeconds <= 240){
			time.setScore(timeInSeconds);
			timeInSeconds--;
		}else if(timeInSeconds == 0){
			time.setScore(0);
		}
		
		Score onlinePlayers = obj.getScore(Bukkit.getOfflinePlayer(ChatColor.DARK_AQUA + "Players:" + ChatColor.RED));
		onlinePlayers.setScore(players);
		
		Score arenaNumber = obj.getScore(Bukkit.getOfflinePlayer(ChatColor.GREEN + "Arena Number:" + ChatColor.RED));
		arenaNumber.setScore(1);
		
		Score highScore = obj.getScore(Bukkit.getOfflinePlayer(ChatColor.YELLOW + "HighScore:" + ChatColor.RED));
		highScore.setScore(0);
		
		for(Player player : Bukkit.getOnlinePlayers()){
			player.setScoreboard(board);
		}
	}
	
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event){
	}
	
	@EventHandler
	public void onPlayerLeave(PlayerQuitEvent event){
	}
}