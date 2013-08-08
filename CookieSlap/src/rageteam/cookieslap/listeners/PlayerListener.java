package rageteam.cookieslap.listeners;




import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;

import rageteam.cookieslap.main.CS;

public class PlayerListener implements Listener{
	CS plugin;
	public PlayerListener(CS instance){this.plugin = instance;}
	
	@EventHandler
	public void onJoin(PlayerJoinEvent event){
		giveItems(event.getPlayer());
		event.getPlayer().setScoreboard(plugin.board);
		for(Player player : Bukkit.getOnlinePlayers()){
			player.setScoreboard(plugin.board);
		}
	}
	@EventHandler
	public void onLeave(PlayerQuitEvent event){
		event.getPlayer().getInventory().clear();
	}
	
	public static void giveItems(Player player){
		ItemStack cookie = new ItemStack(Material.COOKIE);
		cookie.addUnsafeEnchantment(Enchantment.KNOCKBACK, 25);
		player.getInventory().addItem(cookie);
	}
	
	public static void killStreak1(Player player){
		ItemStack cake = new ItemStack(Material.CAKE);
		cake.addUnsafeEnchantment(Enchantment.KNOCKBACK, 25);
		cake.addUnsafeEnchantment(Enchantment.FIRE_ASPECT, 20);
		player.getInventory().addItem(cake);
	}
	
	public static void killStreak2(Player player){
		ItemStack slimeball = new ItemStack(Material.SLIME_BALL);
		slimeball.addUnsafeEnchantment(Enchantment.KNOCKBACK, 30);
		slimeball.addUnsafeEnchantment(Enchantment.FIRE_ASPECT, 20);
		player.getInventory().addItem(slimeball);
	}

}
