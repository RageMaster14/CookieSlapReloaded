package rageteam.cookieslap.managers;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import org.bukkit.configuration.file.YamlConfiguration;

import rageteam.cookieslap.main.CS;
import rageteam.cookieslap.main.Info;

public class ConfigManager {
	CS plugin;
	public ConfigManager(CS instance) {this.plugin = instance;}
	
public String chatFormat;	
	
	public String[] prefixes = new String[3];
	public String[] motds = new String[5];
	public String[] messages = new String[5];
	public int[] times = new int[4];
	
	public int minPlayers;
	public List<?> blockBreakWhitelist;
	
	public void load() {
		plugin.logger.log(false, "Loading Configuration...");
		
		YamlConfiguration cfg = YamlConfiguration.loadConfiguration(Info.configFile);
		
		this.chatFormat = cfg.getString("chat.chat-format");
		
		this.prefixes[0] = cfg.getString("chat.prefixes.user");
		this.prefixes[1] = cfg.getString("chat.prefixes.vip");
		this.prefixes[2] = cfg.getString("chat.prefixes.staff");
		
		this.motds[0] = cfg.getString("motds.lobby");
		this.motds[1] = cfg.getString("motds.pregame");
		this.motds[2] = cfg.getString("motds.ingame");
		this.motds[4] = cfg.getString("motds.endgame");
		
		this.messages[0] = cfg.getString("messages.player-join");
		this.messages[1] = cfg.getString("messages.player-quit");
		
		this.times[0] = cfg.getInt("times.lobby");
		this.times[1] = cfg.getInt("times.pregame");
		this.times[2] = cfg.getInt("times.ingame");
		
		this.minPlayers = cfg.getInt("server.min-players");
		
		this.blockBreakWhitelist = cfg.getList("block-break-whitelist");
	}
	
	public void Check() {
		plugin.logger.log(false, "Checking for resource files!");
		
		try {
			if (Info.logFile.exists()) { Info.logFile.delete(); Info.logFile.createNewFile(); }
			if (Info.errorlogFile.exists()) { Info.errorlogFile.delete(); Info.errorlogFile.createNewFile(); }
		} catch (Exception ex) {
			plugin.logger.log(true, "Error while creating logs files!");
		}
		
		
		if (!Info.baseDir.exists()) { Info.baseDir.mkdirs(); }
		if (!Info.configFile.exists()) { Export("config", Info.configFile); }
		if (!Info.playersFile.exists()) { Export("players", Info.playersFile); }
		if (!Info.arenasFile.exists()) { Export("arenas", Info.arenasFile); }
	}
	
	private void Export(String inputFile, File destination) {
		plugin.logger.log(false, "Exporting " + inputFile + "!");
		
		try {
			destination.createNewFile();
			
			InputStream input = plugin.getResource(inputFile + ".yml");
			@SuppressWarnings("resource")
			OutputStream output = new FileOutputStream(destination);
			
			byte[] buffer = new byte[1024];
			int length;
			
			while ((length = input.read(buffer)) > 0) {
				output.write(buffer, 0, length);
			}
		} catch(Exception ex) {
			plugin.logger.logError(ex);
		}
	}
}
