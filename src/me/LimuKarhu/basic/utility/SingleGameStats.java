package me.LimuKarhu.basic.utility;

public class SingleGameStats {

	private int deaths;
	private int kills;
	private int points;
	//playername for identification(?)
	private String pname;
	
	public SingleGameStats(String name) {
		pname = name;
	deaths = 0;
	kills = 0;
	points = 0;
	}
	
	public int GetDeaths() {
		return this.deaths;
	}
	public int GetKills() {
		return this.kills;
	}
	public int GetPoints() {
		return this.points;
	}
	
	public void AddKill(){this.kills++;}
	public void AddDeath(){this.deaths++;}
	
	public void CalculatePoints() {
		
		
	}
	
}
