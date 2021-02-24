package me.LimuKarhu.basic.utility;

import java.util.Queue;

public class PlayerStats {

	private String name;
	private int rank;
	private int kills;
	private int deaths;
	private Queue<SingleGameStats> recentGames;
	
	public PlayerStats(String name) {
	//This is for a new player
	this.name = name;	
	this.rank = 0;
	this.kills = 0;
	this.deaths = 0;
	
	}
	
	public PlayerStats(String name, int rank, int kills, int deaths,Queue<SingleGameStats> recent) {
		//This is for an existing player
		this.name = name;	
		this.kills = kills;
		this.deaths = deaths;
		this.rank = rank;
		//Has side effects, but works.
		this.recentGames = recent;
		}
	
	public String GetName() {return this.name;}
	
	public String GetRank() {
		return Globals.ranks[this.rank];
	}
	
	public void UpdateStats(SingleGameStats game) {
		kills+=game.GetKills();
		deaths+=game.GetDeaths();
		
		if(this.recentGames.size()>=5) {
			//Maximum size of the games window has been reached
			recentGames.poll();
			recentGames.add(game);
			
		}else {
			recentGames.add(game);
			
			
		}
		
		
	}
	
	
}
