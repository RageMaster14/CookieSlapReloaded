package rageteam.cookieslap.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import rageteam.cookieslap.games.Game;
import rageteam.cookieslap.games.Status;
import rageteam.cookieslap.main.CookieSlap;
import rageteam.cookieslap.maps.Map;
import rageteam.cookieslap.misc.Permissions;
import rageteam.cookieslap.misc.Updater;
import rageteam.cookieslap.misc.Updater.UpdateType;
import rageteam.cookieslap.players.UtilPlayer;

public class CookieSlapCommand implements CommandExecutor{

	@Override
	public boolean onCommand(CommandSender cs, Command cmd, String tag, String[] args) {
		if(cs instanceof Player){
			Player player = (Player)cs;
			UtilPlayer u = CookieSlap.getCookieSlap().pm.getPlayer(player);
			Permissions perms = new Permissions(player);
			
			if(args.length == 0){
				CookieSlap.getCookieSlap().chat.sendMessage(player, "&b---&dCookieSlap v1.0 &b---");
				CookieSlap.getCookieSlap().chat.sendMessage(player, "&c/" + tag + " help <player|mod|admin>");
			} else if(args.length == 1){
				if(args[0].equalsIgnoreCase("join")){
					if(perms.canjoin()){
						if(u.getGame() != null && u.isAlive()){
							CookieSlap.getCookieSlap().chat.sendMessage(player, "&cYou are already playing.");
						} else {
							if(lobbyset()){
								player.teleport(CookieSlap.getCookieSlap().config.getLobby());
							} else {
								CookieSlap.getCookieSlap().chat.sendMessage(player, "&cCookieSlap is incorrectly setup! Ask and admin to set the lobby.");
							}
						}
					} else {
						CookieSlap.getCookieSlap().chat.sendMessage(player, "&cYou do not have permission");
					}
				} else if (args[0].equalsIgnoreCase("leave")){
					if(u.getGame() != null && u.isAlive()){
						Game game = u.getGame();
						game.leaveGame(u);
						CookieSlap.getCookieSlap().chat.sendMessage(player, "You have left the lobby for map &c" + game.getMap().getName() + "&6.");
					}
				} else if(args[0].equalsIgnoreCase("update")){
					if(perms.isAdmin()){
						if(CookieSlap.getCookieSlap().updateOut){
							try {
								CookieSlap.getCookieSlap().chat.sendMessage(player, "Commencing Update...");
								@SuppressWarnings("unused")
								Updater update = new Updater(CookieSlap.getCookieSlap(), "cookieslap-game", CookieSlap.getCookieSlap().updateFile, UpdateType.NO_VERSION_CHECK, true);
							}catch(Exception e) { CookieSlap.getCookieSlap().chat.sendMessage(player, "An error occured whilst updating! Please download manually from &bhttp://dev.bukkit.org/bukkit-plugins/cookieslapreloaded");}
						} else {
							CookieSlap.getCookieSlap().chat.sendMessage(player, "&bNo updates found.");
						}
					} else {
						CookieSlap.getCookieSlap().chat.sendMessage(player, "&cYou dont have permission");
					}
				} else if(args[0].equalsIgnoreCase("maplist")){
					CookieSlap.getCookieSlap().chat.sendMessage(player, "&bMap list:");
					if(CookieSlap.getCookieSlap().maps.MAPS.size() == 0){
						CookieSlap.getCookieSlap().chat.sendMessage(player, "No maps loaded.");
					}
					for(Map map : CookieSlap.getCookieSlap().maps.MAPS.values()){
						CookieSlap.getCookieSlap().chat.sendMessage(player, "Map: " + map.getName() + " | " + (map.isUsable() ? "Is Setup" : "Incorrectly Setup"));
					}
				} else if(args[0].equalsIgnoreCase("start")){
					if(perms.canStartEnd()){
						if(u.getGame() == null){
							CookieSlap.getCookieSlap().chat.sendMessage(player, "&cYou are not in a game");
						}else if(u.getGame().getStatus() == Status.LOBBY){
							CookieSlap.getCookieSlap().game.startGame(u.getGame());
							CookieSlap.getCookieSlap().chat.sendMessage(player, "&eGame Started!");
						}else if(u.getGame().getStatus() == Status.INGAME){
							CookieSlap.getCookieSlap().chat.sendMessage(player, "§Game has already begun");
						}
					}else if(args[0].equalsIgnoreCase("stop")){
						if(perms.canStartEnd()){
							if(u.getGame() == null){
								CookieSlap.getCookieSlap().chat.sendMessage(player, "&cYou are not in a game");
							} else if(u.getGame().getStatus() == Status.LOBBY){
								CookieSlap.getCookieSlap().chat.sendMessage(player, "&cGame has not begun yet!");
							}else if(u.getGame().getStatus() == Status.INGAME){
								CookieSlap.getCookieSlap().chat.bc("&5" + player.getName() + "&6Has stopped the game");
								CookieSlap.getCookieSlap().game.stopGame(u.getGame(), 1);
								CookieSlap.getCookieSlap().chat.sendMessage(player, "§You have stopped the game");
							}
						} else {
							CookieSlap.getCookieSlap().chat.sendMessage(player, "&cYou do not have permission");
						}
					}else if(args[0].equalsIgnoreCase("setlobby")){
						if(perms.canModifyMaps()){
							CookieSlap.getCookieSlap().config.setLobby(player.getLocation());
							CookieSlap.getCookieSlap().chat.sendMessage(player, "You have set the CookieSlap lobby");
						} else {
							CookieSlap.getCookieSlap().chat.sendMessage(player, "&cYou do not have permission");
						}
					} else if(args[0].equalsIgnoreCase("help")){
						CookieSlap.getCookieSlap().chat.sendMessage(player, "&b--- &dCookieSlap v1.0 &b---");
						CookieSlap.getCookieSlap().chat.sendMessage(player, "&c/" + tag + " help <player|mod|admin>");
					}else {
						CookieSlap.getCookieSlap().chat.sendMessage(player, "&cIncorrect Usage: &6/" + tag + " <join,leave,stop,start,setlobby,help>");
					}
				} else if(args.length == 2){
					if(args[0].equalsIgnoreCase("create")){
						if(perms.canModifyMaps()){
							String name = args[1];
							if(CookieSlap.getCookieSlap().maps.mapExists(name)){
								CookieSlap.getCookieSlap().chat.sendMessage(player, "Map already exists");
							} else {
								CookieSlap.getCookieSlap().maps.c.addMap(name);
								CookieSlap.getCookieSlap().maps.addMap(name);
								Map map = CookieSlap.getCookieSlap().maps.getMap(name);
								Game game = new Game(CookieSlap.getCookieSlap(), map);
								CookieSlap.getCookieSlap().games.addGame(map.getName(), game);
								if(!map.isUsable()){
									game.setStatus(Status.DISABLED);
								}
								CookieSlap.getCookieSlap().chat.sendMessage(player, "Map has been added and created &c" + name + "&6.");
							}
						} else {
							CookieSlap.getCookieSlap().chat.sendMessage(player, "&cYou do not have permission");
						}
					} else if(args[0].equalsIgnoreCase("delete")){
						if(perms.canModifyMaps()){
							String name = args[1];
							if(CookieSlap.getCookieSlap().maps.mapExists(name)){
								CookieSlap.getCookieSlap().maps.deleteMap(name);
								CookieSlap.getCookieSlap().chat.sendMessage(player, "Map has been deleted");
							} else {
								CookieSlap.getCookieSlap().chat.sendMessage(player, "&cMap does not exist");
							}
						} else {
							CookieSlap.getCookieSlap().chat.sendMessage(player, "&cYou do not have permission");
						}
					} else if(args[0].equalsIgnoreCase("start")){
						if(perms.canStartEnd()){
							String name = args[1];
							if(CookieSlap.getCookieSlap().maps.mapExists(name)){
								Game game = CookieSlap.getCookieSlap().games.getGame(name);
								if(game == null) {
									CookieSlap.getCookieSlap().chat.sendMessage(player, "&cYou are not in a game");
								} else if(game.getStatus() == Status.LOBBY){
									CookieSlap.getCookieSlap().game.startGame(game);
									CookieSlap.getCookieSlap().chat.sendMessage(player, "&eStarting " + name + ".");
								} else if(game.getStatus() == Status.INGAME){
									CookieSlap.getCookieSlap().chat.sendMessage(player, "§Game has already begun");
								}
							} else {
								CookieSlap.getCookieSlap().chat.sendMessage(player, "&cMap does not exist");
							}
						} else {
							CookieSlap.getCookieSlap().chat.sendMessage(player, "&cYou do not have permission");
						}
					} else if(args[0].equalsIgnoreCase("stop")){
						if(perms.canStartEnd()){
							String name = args[1];
							if(CookieSlap.getCookieSlap().maps.mapExists(name)){
								Game game = CookieSlap.getCookieSlap().games.getGame(name);
								if(game == null){
									CookieSlap.getCookieSlap().chat.sendMessage(player, "&cYou are not in a game");
								}else if(game.getStatus() == Status.LOBBY){
									CookieSlap.getCookieSlap().chat.sendMessage(player, "&cGame has not yet begun");
								}else if(game.getStatus() == Status.INGAME){
									CookieSlap.getCookieSlap().chat.bc("&5" + player.getName() + "&6has stopped the game", game);
									CookieSlap.getCookieSlap().game.stopGame(game, 1);
									CookieSlap.getCookieSlap().chat.sendMessage(player, "§eYou have stopped the game");
								}
							} else {
								CookieSlap.getCookieSlap().chat.sendMessage(player, "&cMap does not exist");
							}
						} else {
							CookieSlap.getCookieSlap().chat.sendMessage(player, "&cYou do not have permission");
						}
					} else if(args[0].equalsIgnoreCase("join")){
						if(perms.canjoin()){
							if(u.getGame() != null && u.isAlive()){
								CookieSlap.getCookieSlap().chat.sendMessage(player, "&cYou are already playing");
							} else {
								String name = args[1];
								if(CookieSlap.getCookieSlap().maps.mapExists(name)){
									Game game = CookieSlap.getCookieSlap().games.getGame(name);
									if(game != null && CookieSlap.getCookieSlap().maps.getMap(name).isUsable()) {
										game.joinGame(u);
									} else {
										CookieSlap.getCookieSlap().chat.sendMessage(player, "This map is incorrectly setup - See console for detailed output");
									}
								} else {
									CookieSlap.getCookieSlap().chat.sendMessage(player, "&cMap does not exist");
								}
							}
						} else {
							CookieSlap.getCookieSlap().chat.sendMessage(player, "&cYou do not have permission");
						}
					} else if(args[0].equalsIgnoreCase("leave")){
						CookieSlap.getCookieSlap().chat.sendMessage(player, "Please use &e/" + tag + " leave");
					} else if(args[0].equalsIgnoreCase("help")){
						if(args[1].equalsIgnoreCase("player")){
							CookieSlap.getCookieSlap().chat.sendMessage(player, "&b--- &4&lCookieSlap Help &aPlayer &b---");
							sendUsage(player, tag, "join", "Teleport to the lobby");
							sendUsage(player, tag, "join <mapname>", "Join a specified map");
							sendUsage(player, tag, "leave", "Leave your current game");
						} else if(args[1].equalsIgnoreCase("mod")){
							CookieSlap.getCookieSlap().chat.sendMessage(player, "&b--- &4&lCookieSlap Help &aModerator &b---");
							sendUsage(player, tag, "start [mapname]", "Start yours or another game");
							sendUsage(player, tag, "stop [mapname]", "Stop yours or another game");
						} else if(args[1].equalsIgnoreCase("admin")){
							CookieSlap.getCookieSlap().chat.sendMessage(player, "&b--- &4&lCookieSlap Help &aAdmin &b---");
							sendUsage(player, tag, "create <mapname>", "Create a map");
							sendUsage(player, tag, "delete <mapname>", "Delete a map");
							sendUsage(player, tag, "setspawn <map> next", "Set next spawn in a map");
							sendUsage(player, tag, "setspawn <map> <id>", "Reset spawn in a map");
							sendUsage(player, tag, "setlobby", "Set the lobby");
						} else {
							CookieSlap.getCookieSlap().chat.sendMessage(player, "Usage: /" + tag + " help <player|mod|admin>");
						}
					} else {
						CookieSlap.getCookieSlap().chat.sendMessage(player, "Incorrect Usage");
					}
				} else if(args.length == 3){
					if(args[0].equalsIgnoreCase("setspawn")){
						if(perms.canModifyMaps()){
							String name = args[1];
							if(CookieSlap.getCookieSlap().maps.mapExists(name)){
								Map map = CookieSlap.getCookieSlap().maps.getMap(name);
								if(args[2].equalsIgnoreCase("next")){
									map.addSpawn(player.getLocation());
									CookieSlap.getCookieSlap().chat.sendMessage(player, "Spawn &a" + map.getSpawnCount() + "&6 set for map &c" + map.getName() + "&6.");
								} else {
									try{
										int id = Integer.parseInt(args[2]);
										if(spawnset(id, map)){
											map.setSpawn(id, player.getLocation());
											CookieSlap.getCookieSlap().chat.sendMessage(player, "You have reset the spawn " + id + " for map " + name + ".");
										} else {
											CookieSlap.getCookieSlap().chat.sendMessage(player, "Please set the spawn using &e/" + tag + " <mapname> next &6 then try this command again.");
										}
									}catch(NumberFormatException ex){
										CookieSlap.getCookieSlap().chat.sendMessage(player, "&cPlease type a number.");
									}
								}
							} else {
								CookieSlap.getCookieSlap().chat.sendMessage(player, "&cMap does not exist!");
							}
						} else {
							CookieSlap.getCookieSlap().chat.sendMessage(player, "&cYou do not have permission");
						}
					} else {
						CookieSlap.getCookieSlap().chat.sendMessage(player, "Usage: &a/" + tag + " setspawn <mapname> <next|spawnid>");
					}
				} else {
					CookieSlap.getCookieSlap().chat.sendMessage(player, "Incorrect Usage!");
				}
			}
		}
		return false;
	}
	
	boolean lobbyset(){
		try{
			CookieSlap.getCookieSlap().config.getLobby();
		}catch(Exception e) {return false;}
		return true;
	}
	
	boolean spawnset(int i, Map map) {
		if(map.getConfig().isString("Spawns." + i + ".world")){
			return true;
		}
		return false;
	}
	
	public void sendUsage(Player player, String tag, String usage, String def){
		CookieSlap.getCookieSlap().chat.sendMessage(player, "&c/" + tag + " &d" + usage + " &5- &b" + def);
	}
}
