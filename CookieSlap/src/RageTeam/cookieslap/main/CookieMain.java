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
	
	public static CookieMain plugin;
	
	@Override
	public void onDisable(){
		PluginDescriptionFile pdf = this.getDescription();
		this.logger.info(pdf.getName() + " v" + pdf.getVersion() + " Has Been Disabled!");
		
	}
	
	@Override
	public void onEnable(){
		PluginDescriptionFile pdf = this.getDescription();
		this.logger.info(pdf.getName() + " v" + pdf.getVersion() + " Has Been Enabled!");
	}
	
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event){
	}
	
	@EventHandler
	public void onPlayerLeave(PlayerQuitEvent event){
	}
}