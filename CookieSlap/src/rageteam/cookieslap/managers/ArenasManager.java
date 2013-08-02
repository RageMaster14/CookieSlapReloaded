package rageteam.cookieslap.managers;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.configuration.file.YamlConfiguration;

import rageteam.cookieslap.main.Info;
import rageteam.cookieslap.objects.Arena;
import rageteam.cookieslap.main.CS;

public class ArenasManager {
	CS plugin;
	public ArenasManager(CS instance) {this.plugin = instance;}
	
YamlConfiguration cfg = YamlConfiguration.loadConfiguration(Info.arenasFile);	
	
	public List<Arena> arenas = new ArrayList<Arena>();
	
	private World loadedArena;
	
	
	/** Loads a list of the avaiable arenas */
	public void load() {
		plugin.logger.log(false, "LOADING ARENA LIST");
		
		Map<String, Object> cfg_arenas = cfg.getConfigurationSection("arenas").getValues(true);
		String[] arenaList = new String[cfg_arenas.values().size()];
		cfg_arenas.values().toArray(arenaList);
		
		for (String arenaName : arenaList) {
			plugin.logger.log(false, "LOADING ARENA: " + arenaName);
			arenas.add(new Arena(arenaName, 
					cfg.getString("arenas." + arenaName + ".label")));
		}
	}
	
	
	/** Loads an arena, its spawnpoints and chests
	 * @param arena Arena to load
	 */
	public void load(Arena arena) {		
		//----------------------------------------------------------------//
		//                             WORLD                              //
		//----------------------------------------------------------------//
		this.loadedArena = plugin.getServer().createWorld(new WorldCreator(Info.worldsPath + arena.name));
				

		//----------------------------------------------------------------//
		//                           SPAWNPOINTS                          //
		//----------------------------------------------------------------//
		int scount = cfg.getInt("arenas." + arena.name + ".spawn-points.spawn-points-count");
		Location[] spawns = new Location[scount];
		
		for (int i = 1; i < scount; i++) {
			List<Double> lloc = cfg.getDoubleList("arenas." + arena.name + ".spawn-points.point" + i + ".loc");
			List<Float> lview = cfg.getFloatList("arenas." + arena.name + ".spawn-points.point" + i + "view");
			
			Location point = new Location(loadedArena, lloc.get(0), lloc.get(1), lloc.get(2), lview.get(0), lview.get(1));
			spawns[i - 1] = point;			
		}
		
		arena.spawnpoint = spawns;
	}
		
		public void unload() {
			plugin.getServer().unloadWorld(loadedArena, false);
		}
}
