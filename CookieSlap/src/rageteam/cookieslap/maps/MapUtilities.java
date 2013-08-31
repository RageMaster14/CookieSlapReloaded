package rageteam.cookieslap.maps;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Random;

import rageteam.cookieslap.games.Game;
import rageteam.cookieslap.games.Status;
import rageteam.cookieslap.main.CookieSlap;

public class MapUtilities {

	public MapConfig c;

	public MapUtilities() {
		this.c = new MapConfig();
	}

	public HashMap<String, Map> MAPS = new HashMap<String, Map>();

	public void addMap(String name) {

		Map map = new Map(CookieSlap.getCookieSlap(), name);

		this.MAPS.put(name, map);
		map.load();

	}

	public void deleteMap(String name) {

		Map m = getMap(name);
		Game game = CookieSlap.getCookieSlap().games.getGame(name);
		if (game.getStatus() == Status.INGAME) {
			CookieSlap.getCookieSlap().game.stopGame(game, 0);
		}
		CookieSlap.getCookieSlap().games.GAMES.remove(m.getName());
		this.MAPS.remove(m);
		this.c.removeMap(name);
		m.delete();
	}

	public boolean mapExists(String name) {
		return MAPS.containsKey(name);
	}

	public Collection<Map> getMaps() {
		return MAPS.values();
	}

	public Map getMap(String name) {
		return (Map) MAPS.get(name);
	}

	@SuppressWarnings("unused")
	private Map getRandomMap() {

		ArrayList<Map> all = new ArrayList<Map>();

		for (Map map : MAPS.values()) {
			if (map.isUsable()) {
				all.add(map);
			}
		}

		Object[] mapsa = all.toArray();
		Map randomMap = (Map) mapsa[new Random().nextInt(mapsa.length)];
		return randomMap;
	}
}
