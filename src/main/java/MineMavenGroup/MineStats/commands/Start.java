package MineMavenGroup.MineStats.commands;

import MineMavenGroup.MineStats.utility.Globals;
import MineMavenGroup.MineStats.utility.PlayerStats;
import MineMavenGroup.MineStats.utility.SingleGameStats;
import MineMavenGroup.MineStats.utility.Team;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Collections;


import MineMavenGroup.MineStats.Main;

public class Start implements CommandExecutor {

private Main plugin;
	
	public Start(Main plugin) {
		this.plugin = plugin;
	}
	
	@Override
	public boolean onCommand( CommandSender arg0,  Command arg1,  String arg2, String[] arg3) {
		
		if(!arg0.isOp()) return false;
		if(Globals.gameHasStarted) {
			arg0.sendMessage("The game has already begun!");
			return false;
		}
		
		String type = "rsg";
		
		if(Globals.pollingstarted) {
			arg0.sendMessage("Cannot start the game while polling.");
		}
		
		
		Player pl = (Player) arg0;
		pl.getWorld().setTime(0);
		double rankSum = 0;
		
		//Icarus ruleset:
		if(Globals.icarusRun) {
			for(Player gamer:Bukkit.getOnlinePlayers()) {
				this.plugin.giveIcarus(gamer);
			}
			type = "icarus"+type;
		}
		
		
		if(arg3.length > 0 ) {
			
			int teamSize = 2;
			
			if(this.plugin.isInt(arg3[0])) {
				teamSize = Integer.parseInt(arg3[0]);
			Bukkit.broadcastMessage("Size of the teams is "+teamSize);
			}
			
			Collections.shuffle(Globals.players);
			//Rottailut
			if(this.plugin.FindPlayer("MineMaster5000") != null && this.plugin.FindPlayer("FeezyInDaHood") != null) {
				
				//Rottaillaan
				ArrayList<PlayerStats> uudet = new ArrayList<PlayerStats>();
				uudet.add(this.plugin.FindPlayer("MineMaster5000"));
				uudet.add(this.plugin.FindPlayer("FeezyInDaHood"));
				
				for(PlayerStats p: Globals.players) {
					
					if(p.GetName().toLowerCase() != "minemaster5000" && p.GetName().toLowerCase() != "feezyindahood") {
						uudet.add(p);
					}
					
				}
				Globals.players.clear();
				
				for(PlayerStats p: uudet)Globals.players.add(p);
			}//rottailut loppuu
			
			ArrayList<SingleGameStats> ykstiimi = new ArrayList<SingleGameStats>();
			for(int i = 0; i<Globals.nrofplayers;i++) {
				PlayerStats pelaaja = Globals.players.get(i);
				rankSum+=pelaaja.GetRankInt();
				
				String pname = pelaaja.GetName();
				
				//Bukkit.broadcastMessage("Hello monni named "+pname);
				int j = i/teamSize;
				
				SingleGameStats peli = new SingleGameStats(pname,j,Globals.players.get(i).GetRankInt(), type);
				Globals.playerStats.put(pname, peli);
				ykstiimi.add(peli);
				//If team is full or this happpens to be the last  team
				if(i%teamSize>=teamSize-1||i==Globals.nrofplayers-1) {
					//We have a full team
				Team uustiimi = new Team(ykstiimi,j);
				
				//Add team to globals
				Globals.teamObjects.add(uustiimi);
				//Tell who belongs to which team
				Bukkit.broadcastMessage(Globals.teamcolors[j]+"Team "+ Globals.teams[j]+" is: "+uustiimi.GetPlayersString()+ChatColor.RESET);
				ykstiimi.clear();
				}
				
			}
			Bukkit.broadcastMessage("Team game starting in "+Globals.timertime +" seconds...");
			//Set the average rank
			Globals.rankAvg = rankSum/Globals.nrofplayers;
			Bukkit.broadcastMessage("Average rank is "+Globals.rankAvg);
			
			new java.util.Timer().schedule( 
			        new java.util.TimerTask() {
			            @Override
			            public void run() {
			                //your code here
			            	Globals.startTime =System.nanoTime();
			            	Globals.gameHasStarted = true;
			        		Bukkit.broadcastMessage("The game has started!!!");
			        		
			            }
			        }, 
			        Globals.timertime*1000 
			);
			
			return false;
			
		}else {
			
			for(PlayerStats p: Globals.players) {
				rankSum+=p.GetRankInt();
			Globals.playerStats.put(p.GetName(),new SingleGameStats(p.GetName(),p.GetRankInt(), type));
			}
		
		}
		//Set the average rank
		Globals.rankAvg = rankSum/Globals.nrofplayers;
		// Bukkit.broadcastMessage("Average rank is "+Globals.rankAvg);
		//start the game
		Globals.startTime =System.nanoTime();
		Globals.gameHasStarted = true;
		Bukkit.broadcastMessage("A solo game has started!!!");
		
		
		return false;
	}
	
	
}