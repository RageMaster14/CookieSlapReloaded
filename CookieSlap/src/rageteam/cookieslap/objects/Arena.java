package rageteam.cookieslap.objects;

import org.bukkit.Location;

public class Arena {
	public String name;
	public String label;
	public Location[] spawnpoints;
	
	/**
	 * 
	 * @param label Maps' label
	 * @param name Maps' directory name
	 * @param spawnpoints Spawns locationS
	 */
	public Arena(String name, String label) {
		this.name = name;
		this.label = label;
	}	
}
