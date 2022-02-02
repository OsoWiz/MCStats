package MineMavenGroup.MineStats.utility;

import java.util.Date;

import org.bukkit.Bukkit;
import org.bukkit.Material;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;


public class SingleGameStats {

	//Numerical statistics
	private int deaths;
	private int kills;
	private int points;
	private int mainGuestLvl;
	private int currentrank;
	
	//Times
	private double timeinsecs;
	private double irontime;
	private double nethertime;
	
	public long lastCooldown;
	
	//Other stuff
	private int team;
	private String category;
	
	private Date completionDate;
	
	
	//playername for identification(?)
	private String pname;
	
	
	public boolean eyePointsGiven;
	
	/*team runs*/
	public SingleGameStats(String name,int team,int rank, String category) {
	this.pname = name;
	deaths = 0;
	kills = 0;
	points = 0;
	currentrank = rank;
	this.mainGuestLvl = -1;
	
	this.team = team;
	
	this.eyePointsGiven = false;
	
	this.timeinsecs = 999999.0;
	this.irontime = 999999.0;
	this.nethertime = 999999.0;
	
	this.category = category;
	this.completionDate = new Date();
	this.lastCooldown = 0;
	}
	
	/*For solo runs*/
	public SingleGameStats(String name,int rank, String category) {
	this.pname = name;
	deaths = 0;
	kills = 0;
	points = 0;
	currentrank = rank;
	this.mainGuestLvl = -1;
	
	this.team = -1;
	this.eyePointsGiven = false;
	
	this.timeinsecs = 999999.0;
	this.irontime = 999999.0;
	this.nethertime = 999999.0;
	

	this.category = category;
	this.completionDate = new Date();
	this.lastCooldown = 0;
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
	public double GetTime() {
		return this.timeinsecs;
	}
	public int GetLevel() {
		return this.mainGuestLvl;
	}
	public String GetTeamString() {
		if(this.team == -1) return "none";
		return Globals.teams[this.team];
	}
	
	public String GetName() {
		return this.pname;
	}
	
	public int GetTeam() {
		return this.team;
	}
	public int GetRank() {
		return this.currentrank;
	}
	public Date GetDate() {
		return this.completionDate;
	}
	public double GetIronTime() {
		return this.irontime;
	}
	public double GetNetherTime() {
		return this.nethertime;
	}
	public String GetCategory() {
		return this.category;
	}
	
	public void AddKill(){this.kills++;}
	public void AddDeath(){this.deaths++;}
	public void AddPoints(int pts) {this.points+=pts;}
	
	public void GotGuest(int guest) {
		if(guest>this.mainGuestLvl) this.mainGuestLvl = guest;
		if(Globals.furthestAchievement<guest) Globals.furthestAchievement = guest;
	}
	public void SetGuest(int lvl) {this.mainGuestLvl = lvl;}
	
	public void SetCooldown() 
	{
		this.lastCooldown = System.nanoTime()/1000000000; //In seconds
	}
	
	
	public void GotIron(double time) {
		//Iron time in seconds
		this.irontime = time;
		}
	
	public void EnteredNether(double time) {
		//Nether enter time in seconds
		this.nethertime = time;
	}
	public void WonTheGame(double time) {
		//Update the final time 
		this.timeinsecs = time;
		this.mainGuestLvl =7;
	}
	
	public void SetTeam(int team) {
		this.team = team;
	}
	/*Time in seconds*/
	public boolean onCooldown(long time) {
		return System.nanoTime()/1000000000-this.lastCooldown<300;
	}
	
	//This is for a solo game
	public void CalculatePoints() {
		
		
		
		//Add the points for each guest
		int mainsumma = 0;
		for(int i = 0; i<=this.mainGuestLvl;i++) {
			mainsumma+=Globals.advancementPoints[i];
		}
		double difference = 0;
		
		difference = this.currentrank-Globals.rankAvg;
		
		mainsumma*=Math.pow(1.06,-difference);
		
		Bukkit.broadcastMessage("Main guest gave "+this.GetName()+" "+mainsumma+" points. Rankdifference was: "+difference);
		
		this.points+=mainsumma;
		//Extra points for deathless run
		if(this.deaths ==0) this.points+=Globals.extraPoints[5];
		
		//If this player has not been given points for eyes and they are on the right spot
		if(!this.eyePointsGiven &&(this.mainGuestLvl == 5 ||this.mainGuestLvl == 4)) {
			
			Player p = Bukkit.getServer().getPlayer(this.pname);
			
				if(p!=null&&(p.getInventory().contains(Material.ENDER_EYE)||p.getInventory().contains(Material.BLAZE_ROD)||p.getInventory().contains(Material.ENDER_PEARL))) {
					int pisteet = 0;
					int silmat = 0;
					int rodit = 0;
					int pearlit = 0;
					for(ItemStack item:p.getInventory().getContents()){
						//If item is null, skip
						if(item == null) continue;
						
						if(item.getType() == Material.ENDER_EYE) {
							silmat+=item.getAmount();
						}else if(item.getType() == Material.BLAZE_ROD) {
							rodit+=item.getAmount();
						}else if(item.getType() == Material.ENDER_PEARL) {
							pearlit+=item.getAmount();
						}
					}
					
					silmat = Math.min(silmat, 12);
					pearlit = Math.min(pearlit, 12)-silmat;
					rodit = Math.min(rodit,10)-silmat;
					
					if(silmat >=12) { pearlit = 0; rodit = 0;}
					
					
					pisteet = Math.min(24,2*silmat+rodit+pearlit);
					this.points+=pisteet;
					Bukkit.broadcastMessage("Player "+p.getName()+" got "+pisteet+" points for eyes/rods/pearls!");
					this.eyePointsGiven = true;
					
					return; //We can return as we already found ourselves
				}
				
			
			return;
		}
		
		return;
	}
	
	
	
}
