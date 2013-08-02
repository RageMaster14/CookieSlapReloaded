package rageteam.cookieslap.objects;

import org.bukkit.Location;

public class Arena {
	public String name;
	public String label;
	public Location[] spawnpoint;
	
	/**
	 * 
	 * @param label Maps' label
	 * @param name Maps' directory name
	 * @param spawnpoints Spawns location
	 */
	public Arena(String name, String label) {
		this.name = name;
		this.label = label;
	}	
}
