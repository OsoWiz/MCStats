package MineMavenGroup.MineStats.utility;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class Team {

	private ArrayList<SingleGameStats> players;
	private int nrofplayers;
	private int teamInt;
	private int questlvl;
	
	public Team(ArrayList<SingleGameStats> pelurit, int teamnbr) {
		this.teamInt = teamnbr;
		this.questlvl = -1;
		nrofplayers = 0;
		this.players = new ArrayList<SingleGameStats>();
		for(SingleGameStats p: pelurit) {
			this.players.add(p);
			this.nrofplayers++;
		}
		this.SetTeam(); //Force the players to have correct teams
	}
	
	
	public int GetnrofPlayers() {
		return this.nrofplayers;
	}
	
	public int GetTeamInt() {
		return this.teamInt;
	}
	
	public String GetTeamString() {
		return Globals.teams[this.teamInt];
	}
	
	public ChatColor GetColor() {
		return Globals.teamcolors[this.teamInt];
	}
	
	public String GetPlayersString() {
		String team = "";
		for(SingleGameStats p: this.players) {
			team+=p.GetName()+", ";
			
		}
		team = team.substring(0, team.length()-2);
		return team;
	}
	
	public String GetTeamInfo() {
		return this.GetColor()+"Team "+ this.GetTeamString()+": "+this.GetPlayersString()+ChatColor.RESET;
	}
	
	
	public ArrayList<SingleGameStats> GetTeam() {
		return  this.players;
	}
	
	
	
	public double GetAvgRank() {
		double summa = 0;
		for(SingleGameStats p: this.players) {
			summa+=p.GetRank();
		}
		return summa/this.nrofplayers;
	}
	
	public void GotQuest(int quest) {
	if(this.questlvl<quest) this.questlvl = quest;
	if(Globals.furthestAchievement<quest) Globals.furthestAchievement = quest; //Update the global goal also
	
	for(SingleGameStats p: this.players) {
		p.SetGuest(this.questlvl);
		}
	}
	
	public void GotIron() {
		double time =(System.nanoTime()-Globals.startTime)/1000000000.0;
		for(SingleGameStats p: this.players) {
			p.GotIron(time);
		}
	}
	
	public void EnteredNether() {
		double time =(System.nanoTime()-Globals.startTime)/1000000000.0;
		for(SingleGameStats p: this.players) {
			p.EnteredNether(time);
		}
	}
	
	
	public void WonTheGame() {
		this.questlvl = 7;
		//Calculate one time so no discrepencies
		double time =(System.nanoTime()-Globals.startTime)/1000000000.0;
		for(SingleGameStats p: this.players) {
			p.WonTheGame(time);
		}
	}
	
	public void SetTeam() { //Makes each player have the correct teaminteger
		for(SingleGameStats pl:this.players) {
			pl.SetTeam(this.teamInt);
		}
	}
	
	public void CalculatePoints() {
		
		int mainsumma = 0;
		for(int i = 0; i<=this.questlvl;i++) {
			mainsumma+=Globals.advancementPoints[i];
		}
		
		double difference = this.GetAvgRank()-Globals.rankAvg;
		
		mainsumma*=Math.pow(1.06,-difference);
		
		//Bukkit.broadcastMessage("Main guest gave team "+this.GetTeamString()+" "+mainsumma+" points. Rankdifference was: "+difference);
		//Eyepoints?
		int eyePoints = 0;
		if(this.questlvl== 4 || this.questlvl == 5) {
			eyePoints =this.calculateEyePoints();
		//	Bukkit.broadcastMessage("Team "+this.GetTeamString()+" got "+eyePoints+" points from endereyes/rods/pearls.");
		}
		
		//Add the points from the mainquest to each player
		for(SingleGameStats p: this.players) {
			p.AddPoints(mainsumma);
			if(p.GetDeaths() == 0) p.AddPoints(Globals.extraPoints[5]);
			else p.AddPoints(Globals.extraPoints[5]/p.GetDeaths());
			
			p.AddPoints(eyePoints);
		}
		
		
	}
	
	private int calculateEyePoints() {
		
		int silmat = 0;
		int rodit = 0;
		int pearlit = 0;
		for(SingleGameStats p: this.players){
			Player pl = Bukkit.getServer().getPlayer(p.GetName());
			if(pl != null&&(pl.getInventory().contains(Material.ENDER_EYE)||pl.getInventory().contains(Material.BLAZE_ROD)||pl.getInventory().contains(Material.BLAZE_POWDER)||pl.getInventory().contains(Material.ENDER_PEARL))) {
				//contains the ender eye so loop until we find it
				for(ItemStack i:pl.getInventory().getContents()) {
					if(i==null) continue;
					
					if(i.getType() == Material.ENDER_EYE) {
						silmat+=i.getAmount();
					}else if(i.getType() == Material.BLAZE_ROD||i.getType() == Material.BLAZE_POWDER) {
						rodit+=i.getAmount();
					}else if(i.getType() == Material.ENDER_PEARL) {
						pearlit+=i.getAmount();
					}
				}
				
			}else if(pl == null) {Bukkit.broadcastMessage("Bukkit claims there is no player called "+p.GetName()+" on the server..");}//does not, onto the next one
		}
		
		silmat = Math.min(silmat, 12);
		pearlit = Math.min(pearlit, 12);
		rodit = Math.min(rodit,10);
		
		if(silmat >=12) { pearlit = 0; rodit = 0;}
		
		
		int pisteet = Math.min(24, 2*silmat +pearlit+rodit );
		return pisteet;
	}
	
}
