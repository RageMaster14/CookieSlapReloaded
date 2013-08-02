package rageteam.cookieslap.managers;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.configuration.file.YamlConfiguration;

import rageteam.cookieslap.main.Info;
import rageteam.cookieslap.objects.CPlayer;
import rageteam.cookieslap.main.CS;

public class PlayerManager {
	CS plugin;
	public PlayerManager(CS instance) {this.plugin = instance;}
	
	YamlConfiguration cfg;
	
	private Map<String, CPlayer> players = new HashMap<String, CPlayer>();
	
	public CPlayer get(String username) {
		return players.get(username);
	}
	
	public void load(String username) {
		plugin.logger.log(false, "Loading user " + username + "!");
		
		if (isNew(username)) { add(username); }
		
		String rank = null;
		int wins = 0, loses = 0;
		
		cfg = YamlConfiguration.loadConfiguration(Info.playersFile);
		
		rank = cfg.getString("players." + username + ".rank");
		wins = cfg.getInt("players." + username + ".wins");
		loses = cfg.getInt("players." + username + ".loses");
		
		players.put(username, new CPlayer(username, rank, wins, loses));
	}
	
	public void update(String username, int wins, int loses) {
		plugin.logger.log(false, "Updating " + username + "'s statistics!");
		
		cfg = YamlConfiguration.loadConfiguration(Info.playersFile);
		
		cfg.set("players." + username + ".wins", cfg.getInt("players." + username + ".wins") + wins);
		cfg.set("players." + username + ".loses", cfg.getInt("players." + username + ".loses") + loses);
		
		try {
			cfg.save(Info.playersFile);
		} catch (Exception ex) {
			plugin.logger.logError(ex);
		}
	}
	
	private boolean isNew(String username) {
		String rank = null;
		
		cfg = YamlConfiguration.loadConfiguration(Info.playersFile);
		
		rank = cfg.getString("players." + username + ".rank");
		
		if (rank == null) {
			return true;
		} else {
			return false;
		}
	}
	
	public void add(String username) { add(username, "USER"); }
	public void add(String username, String rank) {
		plugin.logger.log(false, "Creating data for user " + username);
		
		cfg = YamlConfiguration.loadConfiguration(Info.playersFile);
		
		cfg.set("players." + username + ".rank", rank);
		cfg.set("players." + username + ".wins", 0);
		cfg.set("players." + username + ".loses", 0);
		
		try {
			cfg.save(Info.playersFile);
		} catch (Exception ex) {
			plugin.logger.logError(ex);
		}
	}
}
