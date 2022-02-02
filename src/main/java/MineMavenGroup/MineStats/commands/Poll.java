package MineMavenGroup.MineStats.commands;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import MineMavenGroup.MineStats.Main;
import MineMavenGroup.MineStats.utility.Globals;
import MineMavenGroup.MineStats.utility.PlayerStats;
import MineMavenGroup.MineStats.utility.SingleGameStats;
import MineMavenGroup.MineStats.utility.Team;

public class Poll implements CommandExecutor  {
	
	private Main plugin;
	private int numOfTeams;
	private ArrayList<SingleGameStats>[] tiimit;
	
	public Poll(Main pug) {
		this.plugin = pug;
		this.numOfTeams = 3; //standard
		this.tiimit = (ArrayList<SingleGameStats>[]) new ArrayList[8];
		pug.getCommand("poll").setExecutor(this);
	}
	
	@Override
	public boolean onCommand(CommandSender arg0, Command arg1, String arg2, String[] arg3) {
		
		String type = "";
		
		if(Globals.gameHasStarted) {
			Bukkit.broadcastMessage("Cannot poll when the game has already started");
			return false;
		}
		
		if(Globals.pollingstarted) {
			type = "rsg"; 
			//Icarus ruleset:
			if(Globals.icarusRun) {
				type = "icarus"+type;
				for(Player gamer:Bukkit.getOnlinePlayers()) {
					this.plugin.giveIcarus(gamer);
				}
			}
			
			//So now go through players and do stuff
			
			ArrayList<SingleGameStats> monnit = new ArrayList<SingleGameStats>();
			
			
			
			for(Map.Entry<String, SingleGameStats> playerentry:Globals.playerStats.entrySet()) {//Loop every gamestats
				
			SingleGameStats player =playerentry.getValue();
			
			int proposedteam = player.GetTeam(); // this is now the proposed team..
			
			if(-1<proposedteam&&proposedteam<numOfTeams) {//all good
				this.tiimit[proposedteam].add(player);
				
			}else {//invalid team or no team was set up so add the person to monnit
				monnit.add(player);
			}
			
			
			}//end of for loop
			
			//Testing:
			//for(int i=0;i<8;i++) {
			//	if(tiimit[i]==null)Bukkit.broadcastMessage("The team number "+i+" was null...");
			//	else Bukkit.broadcastMessage("The size of team "+i+" is "+tiimit[i].size());
			//}
			
			if(!monnit.isEmpty()) {
			//There are people with no team
			
			ArrayList<Integer> freeslots = new ArrayList<Integer>();
			
			
			for(int i=0;i<numOfTeams;i++) {
				//Bukkit.broadcastMessage("Value of emptyi is now: "+emptyi); //debug
				
				if(this.tiimit[i].isEmpty()) {//if it is empty
					freeslots.add(i);
				}
			} //checking the slots end
			
			Collections.shuffle(monnit);//shuffle monnit for randomness
			
			int slot =0;
			if(freeslots.isEmpty()) {//no free teams
			for(SingleGameStats monni:monnit) {
				slot = slot%numOfTeams; //if it goes over, it loops back
				this.tiimit[slot].add(monni);
				slot++;
			 }
			}//no free teams atm
			else {//There are free teams
				int freesize = freeslots.size();
				for(SingleGameStats monni:monnit) {//slot still visible
					slot = slot%freesize;
					int team = freeslots.get(slot);
					this.tiimit[team].add(monni);
					slot++;
				 }
			}
			
			}//monnistuff ends here
			
			
			Globals.nrofteams =0; //reset and count the actual teams
			for(int i =0;i<numOfTeams;i++) { //now add every team to the game
				
				if(this.tiimit[i] != null && !this.tiimit[i].isEmpty()) {//If the team exists and is not empty.
					Team uustiimi = new Team(this.tiimit[i],i);
				Globals.teamObjects.add(uustiimi); //Fix so that each player actually has the i as their team.
				Bukkit.broadcastMessage(Globals.teamcolors[i]+"Team "+ Globals.teams[i]+" is: "+uustiimi.GetPlayersString()+ChatColor.RESET);
				Globals.nrofteams++; //add 1
				}
			}
			
				
			
			
			Bukkit.broadcastMessage("Team game starting in "+Globals.timertime +" seconds...");
			
			//TODO: ADD SOME FLARE HERE; TELL THE TEAMS ETC.
			
			new java.util.Timer().schedule( 
			        new java.util.TimerTask() {
			            @Override
			            public void run() {
			                // your code here
			            	Globals.startTime =System.nanoTime();
			            	Globals.gameHasStarted = true;
			        		Bukkit.broadcastMessage("The game has started!!!");
			        		
			            }
			        }, 
			        Globals.timertime*1000
			);
			
			return true;
		}//Polling not started under this
		else {
		
		
		
		if(arg3.length>0&&plugin.isInt(arg3[0])) {
			numOfTeams = Integer.parseInt(arg3[0]); //Set the number of teams properly
			if(numOfTeams>8) this.numOfTeams = 8;
			if(numOfTeams<0) this.numOfTeams = 1;
		}///////////// ACTUAL INITIALIZATIONS STARTING FROM THIS LINE///////////////////////////////
		
		
		
		for(int i=0;i<numOfTeams;i++) { //Create all teams
			this.tiimit[i] = new ArrayList<SingleGameStats>(); // now there is one for each team
		}
		float ranksum = 0.0f;
		
		for(PlayerStats p:Globals.players) {
			ranksum += p.GetRankInt();
			SingleGameStats peli = new SingleGameStats(p.GetName(), p.GetRankInt(), type);
			Globals.playerStats.put(p.GetName(), peli); //Game added
		}
		
		Globals.rankAvg = ranksum/Globals.nrofplayers;
		Globals.nrofteams = this.numOfTeams;
		Bukkit.broadcastMessage("Average rank is "+Globals.rankAvg);
		
		
		
		Globals.pollingstarted = true;
		Bukkit.broadcastMessage("The polling has begun for teams in the range "+0 + " to " + (numOfTeams-1));
		
		return false;
		}
	}
	
	

}
