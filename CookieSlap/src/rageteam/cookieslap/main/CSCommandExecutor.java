package rageteam.cookieslap.main;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;

import rageteam.cookieslap.game.CustomException;
import rageteam.cookieslap.game.Methods;

import com.sk89q.worldedit.bukkit.WorldEditPlugin;
import com.sk89q.worldedit.bukkit.selections.Selection;

public class CSCommandExecutor  implements CommandExecutor
{
	public static WorldEditPlugin WorldEdit = (WorldEditPlugin) Bukkit.getServer().getPluginManager().getPlugin("WorldEdit");

	CS plugin;
	public static List<String> arenaNames = new ArrayList<String>();

	public CSCommandExecutor(CS plugin)
	{
		this.plugin = plugin;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)
	{
		if (sender instanceof Player)
		{
			if (args.length == 0)
			{
				return false;
			}

			Player player = (Player) sender;

			if (args[0].equalsIgnoreCase("create"))
			{
				if (args.length == 2)
				{
					String arena = args[1];
					Methods.createArena(plugin, arena, player, CS.arenaConfig);
					CS.arenaConfig.set("arenas." + arena + ".type", "cookieslap");
				}

				plugin.saveConfig();
				return true;
			}

			if (args[0].equalsIgnoreCase("delete"))
			{
				if (args.length == 2)
				{
					String arena = args[1];
					try
					{
						Methods.deleteArena(plugin, arena, player, CS.arenaConfig, CS.invConfig);
						plugin.saveConfig();
						return true;
					} catch (CustomException e)
					{
					}
				}
			}
			if (args[0].equalsIgnoreCase("list"))
			{
				Methods.listArenas(plugin, player, CS.arenaConfig);
				return true;
			}

			if (args[0].equalsIgnoreCase("set"))
			{
				if (args.length == 3 || args.length == 4)
				{
					String arena = args[1];

					if (args[2].equalsIgnoreCase("spawn"))
					{
						Methods.setSpawn(plugin, player, arena, CS.arenaConfig);
						plugin.saveConfig();
						return true;
					}

					if (args[2].equalsIgnoreCase("lobby"))
					{
						Methods.setLobby(plugin, player, arena, CS.arenaConfig);
						plugin.saveConfig();
						return true;
					}

					if (args[2].equalsIgnoreCase("floor"))
					{
						if (WorldEdit != null)
						{
							Selection floor = WorldEdit.getSelection(player);
							if (floor != null)
							{

								CS.setPos1(plugin, player, arena, CS.arenaConfig, floor.getMaximumPoint());
								CS.setPos2(plugin, player, arena, CS.arenaConfig, floor.getMinimumPoint());
							} else
							{
								pm("Select your points!", player);
							}

						} else
						{
							pm("World Edit isn't installed. Use pos1/pos2 instead!", player);
						}
						return true;
					}

					if (args[2].equalsIgnoreCase("pos1"))
					{
						Location loc = player.getLocation();
						loc.setY(loc.getY() - 1);
						CSmethods.setPos1(plugin, player, arena, CS.arenaConfig, loc);
						plugin.saveConfig();
						return true;
					}

					if (args[2].equalsIgnoreCase("pos2"))
					{
						Location loc = player.getLocation();
						loc.setY(loc.getY() - 1);
						CSmethods.setPos2(plugin, player, arena, CS.arenaConfig, loc);
						plugin.saveConfig();
						return true;
					}

					if (args[2].equalsIgnoreCase("enabled"))
					{
						CSmethods.setEnabled(plugin, player, arena, CS.arenaConfig);
						plugin.saveConfig();
						return true;
					}
					if (args[2].equalsIgnoreCase("enable"))
					{
						CSmethods.setEnabled(plugin, player, arena, CS.arenaConfig);
						plugin.saveConfig();
						return true;
					}
					if (args[2].equalsIgnoreCase("disable"))
					{
						CS.arenaConfig.set("arenas." + arena + ".enabled", false);
						this.plugin.saveConfig();
						this.pm("The Arena, " + arena + ", has been disabled!", player);
						return true;
					}
					if (args[2].equalsIgnoreCase("disabled"))
					{
						CS.arenaConfig.set("arenas." + arena + ".enabled", false);
						this.plugin.saveConfig();
						this.pm("The Arena, " + arena + ", has been disabled!", player);
						return true;
					}

					if (args.length == 4)
					{
						if (args[2].equalsIgnoreCase("min"))
						{
							// Saving Position 1
							CS.arenaConfig.set("arenas." + arena + ".min-players", Integer.parseInt(args[3]));
							this.pm("Minimum Players set!", player);
							plugin.saveConfig();
							return true;
						}
						if (args[2].equalsIgnoreCase("max"))
						{
							// Saving Position 1
							CS.arenaConfig.set("arenas." + arena + ".max-players", Integer.parseInt(args[3]));
							this.pm("Maximum Players set!", player);
							plugin.saveConfig();
							return true;
						}
					}

					return false;

				}

				return false;
			}

			if (args[0].equalsIgnoreCase("join"))
			{
				if (args.length == 2)
				{
					try
					{
						Methods.joinArena(plugin, args[1], player, CS.arenaConfig, CS.invConfig);
						return true;
					} catch (CustomException e)
					{
						e.printStackTrace();
					}

					plugin.saveConfig();

				}

			}
			if(args[0].equals("toggleboard")){
				if(args.length == 1){
					Player player1 = (Player) sender;
					
					player1.getScoreboard().clearSlot(DisplaySlot.SIDEBAR);
					player1.sendMessage(ChatColor.RED + "Scoreboard Disabled!");
				}
			}
			
			if (args[0].equals("vote"))
			{
				if (args.length == 1)
				{
					try
					{
						Methods.addVote(plugin, CS.invConfig.getString("players." + player.getName() + ".arena"), player, CS.arenaConfig, CS.invConfig);
						return true;
					} catch (Exception e)
					{
						e.printStackTrace();
					}
				}
				return false;
			}
			if (args[0].equalsIgnoreCase("quit"))
			{
				if (args.length == 1)
				{
					try
					{
						Methods.quitArena(plugin, player, CS.arenaConfig, CS.invConfig);
						return true;
					} catch (CustomException e)
					{
						e.printStackTrace();
					}
				}

				plugin.saveConfig();

			}

			return false;

		}

		sender.sendMessage("[CookieSlap] This command can only be run by a Player!");

		return true;

	}
	public void pm(String message, Player player)
	{
		player.sendMessage(ChatColor.LIGHT_PURPLE + "[CookieSlap] " + ChatColor.GRAY + message);
	}

}