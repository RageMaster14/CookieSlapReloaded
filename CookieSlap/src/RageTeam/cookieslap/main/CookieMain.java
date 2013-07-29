package RageTeam.cookieslap.main;

import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scoreboard.Scoreboard;

public class CookieMain extends JavaPlugin{
	public final Logger logger = Logger.getLogger("Minecraft");

	//Objects
	public Object chat;
	public Object playerManager;
	public Object cfgManager;
	
	public static Scoreboard main;
	
	public static int players = 0;
	public static int gameLoop;
	
	public static CookieMain plugin;
	

	public static boolean canStart;

	public static int timeInSeconds;
	
	@Override
	public void onDisable(){
		PluginDescriptionFile pdf = this.getDescription();
		this.logger.info(pdf.getName() + " v" + pdf.getVersion() + " Has Been Disabled!");
		getServer().getScheduler().cancelTask(gameLoop);
		
	}
	
	@Override
	public void onEnable(){
		PluginDescriptionFile pdf = this.getDescription();
		this.logger.info(pdf.getName() + " v" + pdf.getVersion() + " Has Been Enabled!");
		canStart = false;
	}
	
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event){
		players ++;
		Player player = event.getPlayer();
		player.setScoreboard(main);
		if(Bukkit.getOnlinePlayers().length >= 6)
		canStart = true;
	}
	
	@EventHandler
	public void onPlayerLeave(PlayerQuitEvent event){
		players --;
		if(Bukkit.getOnlinePlayers().length - 1 >= 6)
		canStart = false;
	}
}