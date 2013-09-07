package rageteam.cookieslap.games;

import java.util.Arrays;
import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

import rageteam.cookieslap.main.CookieSlap;
import rageteam.cookieslap.main.ScoreboardUtils;
import rageteam.cookieslap.maps.Map;
import rageteam.cookieslap.players.CookieSlapPlayer;
public class GameManager extends JavaPlugin {
	CookieSlap splegg;

	public GameManager(CookieSlap splegg) {
		this.splegg = splegg;
	}

	public void startGame(Game game) {

		splegg.chat.log("New game commencing..");

		game.startGameTimer();

		Bukkit.getScheduler().cancelTask(game.counter);

		game.status = Status.INGAME;
		game.time = 901;
		game.setLobbyCount(splegg.getConfig().getInt("auto-start.time"));

		int c = 1;

		game.loadFloors();

		splegg.chat.bc("You are playing on &e" + game.getMap().getName()
				+ "&6.", game);

		Map map = game.getMap();

		ScoreboardUtils.get().hideScoreAll(game, "Starting in");
		ScoreboardUtils.get().hideScoreAll(game, "Queue");
		ScoreboardUtils.get().setScoreAll(game, ChatColor.DARK_AQUA + "Players:", game.getPlayers().size());
		ScoreboardUtils.get().setScoreAll(game, ChatColor.YELLOW + "HighScore:", 1);
		ScoreboardUtils.get().setScoreAll(game, ChatColor.GREEN + "Arena ID:", 1);

		for (CookieSlapPlayer sp : game.players.values()) {

			sp.getPlayer().setLevel(0);

			sp.getUtilPlayer().setAlive(true);

			if (c > map.getSpawnCount()) {
				c = 1;
			}

			sp.getPlayer().teleport(map.getSpawn(c));
			c++;

			sp.getPlayer().setLevel(0);
			sp.getPlayer().setGameMode(GameMode.ADVENTURE);

			// give items
			for (int i = 0; i < 9; i++) {
				sp.getPlayer().getInventory().setItem(i, getCookie());
			}

		}

		game.getSign().update(map, false);

		splegg.chat.bc("Use your Cookie to knock other players out.", game);

	}

	public void stopGame(Game game, int r) {

		splegg.chat.log("Commencing shutdown of " + game.getMap().getName()
				+ ".");

		game.status = Status.ENDING;

		game.stopGameTimer();

		game.setLobbyCount(31);
		game.time = 601;
		game.setStatus(Status.LOBBY);

		game.resetArena();
		game.data.clear();
		game.floor.clear();

		game.setStarting(false);

		HashMap<String, CookieSlapPlayer> h = new HashMap<String, CookieSlapPlayer>(
				game.players);
		game.players.clear();

		for (CookieSlapPlayer sp : h.values()) {

			game.leaveGame(sp.getUtilPlayer());

		}

		if (r != 5) {
			splegg.chat.bc("CookieSlap has ended on the map "
					+ game.getMap().getName() + ".");
		}

		if (!splegg.disabling) {
			game.getSign().update(game.map, true);
		}

		splegg.chat.log("Game has reset.");

	}

	public String getDigitTime(int count) {
		int minutes = count / 60;
		int seconds = count % 60;
		String disMinu = (minutes < 10 ? "0" : "") + minutes;
		String disSec = (seconds < 10 ? "0" : "") + seconds;
		String formattedTime = disMinu + ":" + disSec;
		return formattedTime;
	}

	public void ingameTimer(int count, HashMap<String, CookieSlapPlayer> players) {
		for (CookieSlapPlayer sp : players.values()) {
			splegg.chat.sendMessage(sp.getPlayer(), "Splegg is ending in §5§l"
					+ splegg.game.getDigitTime(count));
		}
	}

	public ItemStack getCookie() {
		ItemStack cookie = new ItemStack(Material.COOKIE);
		cookie.addUnsafeEnchantment(Enchantment.KNOCKBACK, 25);
		ItemMeta cookieMeta = cookie.getItemMeta();
		cookieMeta.setDisplayName("Magical Cookie");
		cookieMeta
				.setLore(Arrays
						.asList(new String[] { "§3Left-Click to knock players off the edge" }));
		cookie.setItemMeta(cookieMeta);
		return cookie;
	}
}
