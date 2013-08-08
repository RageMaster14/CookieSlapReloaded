package rageteam.cookieslap.listeners;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Sign;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import rageteam.cookieslap.game.CustomException;
import rageteam.cookieslap.game.Methods;
import rageteam.cookieslap.main.CS;
import rageteam.cookieslap.objects.Arena;

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
	public void onPlayerQuit(PlayerQuitEvent event) throws CustomException
	{
		Player player = event.getPlayer();

		if (CS.invConfig.contains(player.getName()))
		{
			Methods.quitArena(plugin, player, CS.arenaConfig, CS.invConfig);
			plugin.saveConfig();
		}
	}
	
	@EventHandler
	public void onDamage(EntityDamageEvent event) throws CustomException
	{
		if (event.getEntity() instanceof Player)
		{
			Player player = (Player) event.getEntity();

			if (CS.invConfig.contains("players." + player.getName()))
			{
				Arena arena = new Arena(CS.invConfig.getString("players." + player.getName() + ".arena"), plugin);
				String type = CS.arenaConfig.getString("arenas." + arena.getName() + ".type");
				if (type.equalsIgnoreCase("bowspleef"))
				{

					if (CS.arenaConfig.getBoolean("arenas." + arena.getName() + ".inGame"))
					{
						switch (event.getCause())
						{
							case VOID :
								Methods.quitArena(plugin, player, CS.arenaConfig, CS.invConfig);
								break;
							default :
								break;
						}
						plugin.saveConfig();
						event.setCancelled(true);
					} else
					{
						World world = Bukkit.getServer().getWorld(CS.arenaConfig.getString("arenas." + arena.getName() + ".positions.lobby.world"));
						int x = CS.arenaConfig.getInt("arenas." + arena.getName() + ".positions.lobby.x");
						int y = CS.arenaConfig.getInt("arenas." + arena.getName() + ".positions.lobby.y");
						int z = CS.arenaConfig.getInt("arenas." + arena.getName() + ".positions.lobby.z");
						Location spawn = new Location(world, x, y, z);
						player.teleport(spawn);
						event.setCancelled(true);
					}
				} 
			}
		}
	}
	
	@EventHandler
	public void onDropItem(PlayerDropItemEvent event)
	{
		Player player = event.getPlayer();

		Arena arena = new Arena(CS.invConfig.getString("players." + player.getName() + ".arena"), plugin);
		String type = CS.arenaConfig.getString("arenas." + arena.getName() + ".type");
		if (type.equalsIgnoreCase("bowspleef"))
		{
			if (CS.invConfig.contains("players." + player.getName()))
			{
				event.setCancelled(true);
			}
		}
	}
	
	@EventHandler
	public void onInteract(PlayerInteractEvent e)
	{
		Player player = e.getPlayer();

		if (e.getAction() == Action.RIGHT_CLICK_BLOCK)
		{
			if (e.getClickedBlock().getState() instanceof Sign)
			{
				Sign s = (Sign) e.getClickedBlock().getState();

				if (s.getLine(0).equalsIgnoreCase(ChatColor.DARK_BLUE + "[BowSpleef]") && s.getLine(1).equalsIgnoreCase(ChatColor.GREEN + "Join"))
				{
					String arena = s.getLine(2);

					if (arena != null)
					{

						try
						{
							Methods.joinArena(plugin, arena, player, CS.arenaConfig, CS.invConfig);
							plugin.saveConfig();
						} catch (CustomException e1)
						{
							e1.printStackTrace();
						}

					}
				}

			}
		}
	}
	
	@EventHandler
	public void signCreation(SignChangeEvent event)
	{
		if (event.getLine(0).equalsIgnoreCase("[CookieSlap]") && event.getLine(1).equalsIgnoreCase("Join"))
		{
			if (CS.arenaConfig.contains("arenas." + event.getLine(2)))
			{
				this.pm("Sign creation successful!", event.getPlayer());
				event.setLine(0, ChatColor.LIGHT_PURPLE + "[CookieSlap]");
				event.setLine(1, ChatColor.GREEN + "Join");
				plugin.saveConfig();
				return;
			}

		}
	}
	
	
	
	public static void giveItems(Player player){
		ItemStack cookie = new ItemStack(Material.COOKIE, 1);
		cookie.addUnsafeEnchantment(Enchantment.KNOCKBACK, 25);
		player.getInventory().addItem(cookie);
		ItemMeta meta = cookie.getItemMeta();
		meta.setDisplayName(player.getDisplayName() + "'s Magical Cookie");
		
		List<String> arg0 = new ArrayList<String>();
		arg0.add(ChatColor.BOLD + "" + ChatColor.GOLD + "Left Click And Try To Knock People Off The Map!");
		meta.setLore(arg0);
		cookie.setItemMeta(meta);
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
	
	public void pm(String message, Player player)
	{
		player.sendMessage(ChatColor.LIGHT_PURPLE + "[CookieSlap] " + ChatColor.GRAY + message);
	}
}
