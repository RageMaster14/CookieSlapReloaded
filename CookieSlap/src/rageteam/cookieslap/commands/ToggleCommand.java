package rageteam.cookieslap.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Scoreboard;

import rageteam.cookieslap.main.CookieSlap;
import rageteam.cookieslap.main.CookieSlapBoard;

public class ToggleCommand implements CommandExecutor{
	CookieSlapBoard board;
	CookieSlap plugin;
	public ToggleCommand(CookieSlap instance) { this.plugin = instance; }
	
	Scoreboard scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String cL, String[] args) {
		Player player = (Player) sender;
		if(player.getScoreboard() == board.board){
			player.setScoreboard(scoreboard);
			player.sendMessage(ChatColor.RED + "Scoreboard Disabled!");
		}else if(player.getScoreboard() == scoreboard){
			player.setScoreboard(board.board);
			player.sendMessage(ChatColor.GREEN + "Scoreboard Enabled!");
		}
		return false;
	}
}
