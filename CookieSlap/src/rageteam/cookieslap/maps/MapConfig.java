package rageteam.cookieslap.maps;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import rageteam.cookieslap.games.Game;
import rageteam.cookieslap.games.Status;
import rageteam.cookieslap.main.CookieSlap;

public class MapConfig {
	
	public FileConfiguration maps;
	public File f;
	
	public void setup(){
		
		f = new File(CookieSlap.getCookieSlap().getDataFolder(), "maps.yml");
		
		try {
			if(!f.exists())
				f.createNewFile();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		loadMaps();
		saveMaps();
		
		CookieSlap.getCookieSlap().maps.MAPS.clear();
		
		for(String maps : getEnabledMaps()){
			CookieSlap.getCookieSlap().maps.addMap(maps);
			Map map = CookieSlap.getCookieSlap().maps.getMap(maps);
			Game game = new Game(CookieSlap.getCookieSlap(), map);
			CookieSlap.getCookieSlap().games.addGame(map.getName(), game);
			if(!map.isUsable()){
				game.setStatus(Status.DISABLED);
			}
		}
	}
	
	public void loadMaps() {
		maps = YamlConfiguration.loadConfiguration(f);
	}
	
	public void saveMaps(){
		try {
			maps.save(f);
		} catch (IOException ex){
		}
	}
	
	public List<String> getEnabledMaps(){
		return maps.getStringList("maps");
	}
	
	public void addSign(String map, String loc) {
		List<String> signs = this.maps.getStringList("Signs." + map + ".lobby");
		signs.add(loc);
		this.maps.set("Signs" + map + ".lobby", signs);
		saveMaps();
	}
	
	public void delSign(String map, String loc) {
		List<String> signs = this.maps.getStringList("Signs." + map + ".lobby");
		signs.remove(loc);
		this.maps.set("Signs." + map + ".lobby", signs);
		saveMaps();
	}
	
	public void addMap(String name) {
		List<String> maps = this.maps.getStringList("maps");
		maps.add(name);
		this.maps.set("maps", maps);
		
		saveMaps();
	}
	
	public void removemap(String name) {
		List<String> maps = this.maps.getStringList("maps");
		maps.remove(name);
		this.maps.set("maps", maps);
		
		saveMaps();
	}
}
