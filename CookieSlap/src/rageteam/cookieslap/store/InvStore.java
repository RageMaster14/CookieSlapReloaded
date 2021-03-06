package rageteam.cookieslap.store;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class InvStore {
	
	public Player player;
	ItemStack[] inv;
	ItemStack[] armour;
	double health;
	int fire;
	int food;
	int level;
	float exp;
	GameMode gamemode;
	
	public InvStore(Player player){
		this.player = player;
		this.exp = 0.0F;
		this.level = 0;
		this.health = 20;
		this.food = 0;
		this.armour = null;
		this.inv = null;
	}

	public void load(){
		this.player.getInventory().setContents(this.inv);
		this.player.getInventory().setArmorContents(this.armour);
		this.player.setExp(this.exp);
		this.player.setLevel(this.level);
		this.player.setHealth(this.health);
		this.player.setFoodLevel(this.food);
		this.player.setFireTicks(this.fire);
		this.player.setGameMode(this.gamemode);
		this.player.setScoreboard(Bukkit.getScoreboardManager().getNewScoreboard());
	}
	
	public void save(){
		this.inv = this.player.getInventory().getContents();
		this.armour = this.player.getInventory().getArmorContents();
		this.exp = this.player.getExp();
		this.level = this.player.getLevel();
		this.food = this.player.getFoodLevel();
		this.fire = this.player.getFireTicks();
		this.gamemode = this.player.getGameMode();
	}
	
	public void reset(){
		this.exp = 0.0F;
		this.level = 0;
		this.health = 20;
		this.food = 0;
		this.fire = 0;
		this.gamemode = null;
		this.armour = null;
		this.inv = null;	
	}
}
