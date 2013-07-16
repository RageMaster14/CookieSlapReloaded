package org.cookieslap.main;

import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;

public class CookieMain extends JavaPlugin{
	public final Logger logger = Logger.getLogger("Minecraft");
	
	public static CookieMain plugin;
	
	public static int timeInSeconds;
	
	public static int gameLoop = 0;
	
	public static boolean canStart;
	
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
		timeInSeconds = 120;
		canStart = false;
		gameLoop = getServer().getScheduler().scheduleSyncRepeatingTask(this, new GameLoop(), 20L, 20L);
	}
	
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event){
		if(Bukkit.getOnlinePlayers().length >= 6)
		canStart = true;
	}
	
	@EventHandler
	public void onPlayerLeave(PlayerQuitEvent event){
		if(Bukkit.getOnlinePlayers().length - 1 >= 6)
		canStart = false;
	}
}