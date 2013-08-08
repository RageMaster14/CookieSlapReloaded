package rageteam.cookieslap.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;

import rageteam.cookieslap.main.CS;

public class ToggleCommand implements CommandExecutor{
	CS plugin;
	public ToggleCommand(CS instance){this.plugin = instance;}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String cL, String[] args) {
		Player player = (Player) sender;
		player.getScoreboard().clearSlot(DisplaySlot.SIDEBAR);
		return false;
	} 
}
