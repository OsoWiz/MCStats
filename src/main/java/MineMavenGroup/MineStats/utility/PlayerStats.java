package MineMavenGroup.MineStats.utility;


import java.util.Date;

import org.bson.Document;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.model.Filters;

import java.lang.Math;

public class PlayerStats {

	private String name;
	private int rank;
	private int kills;
	private int deaths;
	private double pb;

	
	public PlayerStats(String name) {
	//This is for a new player
	this.name = name;	
	this.rank = 0;
	this.kills = 0;
	this.deaths = 0;
	this.pb = 999999.0;
	}
	
	public PlayerStats(String name, int rank, int kills, int deaths, double pb) {
		//This is for an existing player
		this.name = name;	
		this.kills = kills;
		this.deaths = deaths;
		this.rank = rank;
		
		this.pb = pb;
		
		}
	
	public String GetName() {return this.name;}
	
	public String GetRank() {
		return Globals.ranks[this.rank];
	}
	public int GetRankInt() {
		return this.rank;
	}
	
	public int GetKills() { return this.kills;}
	public int GetDeaths() { return this.deaths;}
	public double Getpb() { return this.pb;}
	
	public void UpdateStats(SingleGameStats game) {
		kills+=game.GetKills();
		deaths+=game.GetDeaths();
		
		//Do we have a new personal best? Icarus does not count
		if(!Globals.icarusRun&&this.pb>game.GetTime()) {
			this.pb = game.GetTime();
		}
	}
	
	public Document GetUpdatedDocs(MongoCollection<Document> collection) {
		long msday = 1000 * 60 * 60 * 24;
		Long now = new Date().getTime();
		//changed to three weeks
		Long weeksago = now-21*msday;
		
		//Uses two filters to find out documents that have this name and are two weeks recent
		MongoCursor<Document> iterator = collection.find(Filters.and(Filters.eq("name", this.name),Filters.gte("date", weeksago))).iterator();
		double summa = 0;
		int divider = 0;
		while(iterator.hasNext()) {
			Document doc =iterator.next();
			if(doc ==null) break;
			else summa+=doc.getInteger("points");
			
			divider++;
		}
		
		//Bukkit.broadcastMessage("Went over "+divider+" documents for player "+this.GetName());
		
		int newrank = 0;
		if(divider >2) {
			summa = summa/divider;
		
		
		double number = (summa/15000)+1;
		double luku = Math.pow(number, summa)+summa/100.0;
		
		 newrank = (int) Math.min(luku,7);
		}
		
		Document doc = new Document("name",this.name)
				.append("rank",newrank)
				.append("kills",this.kills )
				.append("deaths",this.deaths)
				.append("pb", this.pb)
				;
		
		return doc;
	}
	
	
}
