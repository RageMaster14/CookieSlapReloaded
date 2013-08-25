package rageteam.cookieslap.players;

import org.bukkit.entity.Player;

import rageteam.cookieslap.main.CookieSlapBoard;

public class CookieSlapPlayer {
	
	Player player;
	UtilPlayer u;
	int points;
	int broken;
	private CookieSlapBoard csBoard;
	
	public CookieSlapPlayer(Player player, UtilPlayer u) {
		this.player = player;
		this.u = u;
		this.points = 0;
		this.setScoreboard(new CookieSlapBoard());
		player.setScoreboard(this.getScoreboard().getScoreboard());
	}
	
	public UtilPlayer getUtilPlayer(){
		return this.u;
	}
	
	public Player getPlayer(){
		return this.player;
	}
	
	public int getKills(){
		return this.points;
	}
	
	public void setPoints(int i){
		this.points = i;
	}
	
	public CookieSlapBoard getScoreboard(){
		return csBoard;
	}
	
	public void setScoreboard(CookieSlapBoard csBoard){
		this.csBoard = csBoard;
	}
}
