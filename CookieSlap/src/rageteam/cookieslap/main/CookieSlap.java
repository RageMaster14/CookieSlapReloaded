package rageteam.cookieslap.main;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import rageteam.cookieslap.commands.CookieSlapCommand;
import rageteam.cookieslap.commands.ToggleCommand;
import rageteam.cookieslap.events.CookieSlapEvents;
import rageteam.cookieslap.events.MapListener;
import rageteam.cookieslap.events.PlayerListener;
import rageteam.cookieslap.events.SignListener;
import rageteam.cookieslap.games.Game;
import rageteam.cookieslap.games.GameManager;
import rageteam.cookieslap.games.GameUtilities;
import rageteam.cookieslap.games.Status;
import rageteam.cookieslap.maps.MapUtilities;
import rageteam.cookieslap.misc.Chat;
import rageteam.cookieslap.misc.Config;
import rageteam.cookieslap.misc.UpdateUtils;
import rageteam.cookieslap.misc.Updater;
import rageteam.cookieslap.misc.Updater.UpdateResult;
import rageteam.cookieslap.misc.Updater.UpdateType;
import rageteam.cookieslap.misc.Utilities;
import rageteam.cookieslap.players.PlayerManager;
import rageteam.cookieslap.players.UtilPlayer;

import com.sk89q.worldedit.bukkit.WorldEditPlugin;

public class CookieSlap extends JavaPlugin{
	
	public static CookieSlap getCookieSlap(){
		return (CookieSlap)Bukkit.getPluginManager().getPlugin("CookieSlap");
	}
	
	public Chat chat;
	public MapUtilities maps;
	public GameUtilities games;
	public GameManager game;
	public PlayerManager pm;
	public Utilities utils;
	public Config config;
	
	public ToggleCommand toggleCmd;
	public Updater u;
	public boolean updateOut = false;
	public String newVer = "";
	public File updateFile = this.getFile();
	
	public List<String> special = Arrays.asList(new String[] { "Xquiset", "Rage_Master14"});
	public boolean eco = false;
	public boolean disabling = false;
	
	
	public void onEnable(){
		this.chat = new Chat();
		if(getServer().getPluginManager().getPlugin("WorldEdit") == null){
			chat.log("WorldEdit not found! Please download it from http://dev.bukkit.org/bukkit-plugins/worldedit");
			Bukkit.getPluginManager().disablePlugin(this);
			return;
		}
		
		this.maps = new MapUtilities();
		this.games = new GameUtilities();
		this.game = new GameManager(CookieSlap.getCookieSlap());
		this.pm = new PlayerManager();
		this.utils = new Utilities();
		this.config = new Config(this);
		this.toggleCmd = new ToggleCommand(this);
		
		maps.c.setup();
		config.setup();
		
		getConfig().options().copyDefaults(true);
		saveConfig();
		
		if(getConfig().getBoolean("auto-update")) {
			u = new Updater(this, "cookieslap-game", getFile(), UpdateType.NO_DOWNLOAD, false);
			updateOut = u.getResult() == UpdateResult.UPDATE_AVAILABLE;
			if(updateOut){
				newVer = u.getLatestVersionString();
			}
			getServer().getPluginManager().registerEvents(new UpdateUtils(), this);
		}
		
		getServer().getPluginManager().registerEvents(new MapListener(this), this);
		getServer().getPluginManager().registerEvents(new PlayerListener(), this);
		getServer().getPluginManager().registerEvents(new CookieSlapEvents(), this);
		getServer().getPluginManager().registerEvents(new SignListener(), this);
		
		getCommand("cookieslap").setExecutor(new CookieSlapCommand());
		getCommand("toggleboard").setExecutor(toggleCmd);
		
		for(Player players : Bukkit.getOnlinePlayers()) {
			UtilPlayer u = new UtilPlayer(players);
			pm.PLAYERS.put(players.getName(), u);
		}
		
		super.onEnable();
	}
	
	
	public void onDisable(){
		disabling = true;
		for(Game game : this.games.GAMES.values()){
			if(game.getStatus() == Status.INGAME){
				this.game.stopGame(game, 1);
			}
		}
		super.onDisable();
	}
	
	public WorldEditPlugin getWorldEdit() {
		Plugin worldEdit = getServer().getPluginManager().getPlugin("WorldEdit");
		if ((worldEdit instanceof WorldEditPlugin)) {
			return (WorldEditPlugin) worldEdit;
		}
		return null;
	}
}
