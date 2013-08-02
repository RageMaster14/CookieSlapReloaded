package rageteam.cookieslap.objects;

public class CPlayer {
	public String username;
	public String rank;
	public int wins;
	public int loses;
	
	public CPlayer(String username, String rank, int wins, int loses) {
		this.username = username;
		this.rank = rank;
		this.wins = wins;
		this.loses = loses;
	}
}
