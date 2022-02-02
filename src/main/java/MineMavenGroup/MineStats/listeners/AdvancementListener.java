package MineMavenGroup.MineStats.listeners;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerAdvancementDoneEvent;

import MineMavenGroup.MineStats.Main;
import MineMavenGroup.MineStats.utility.Globals;
import MineMavenGroup.MineStats.utility.SingleGameStats;
import MineMavenGroup.MineStats.utility.Team;


public class AdvancementListener implements Listener {

private Main plugin;
	
	public AdvancementListener(Main plugin) {
		this.plugin = plugin;
		
		Bukkit.getPluginManager().registerEvents(this, plugin);
	}
	
	@EventHandler
	public void onAdvancement(PlayerAdvancementDoneEvent event) {
		
		
		String adv = event.getAdvancement().getKey().getKey();
		String pname = event.getPlayer().getName();
		
		if(adv.contains("story/")||adv.contains("end")||adv.contains("nether")||adv.contains("adventure")) {
			//Take out the actual advancement name
			adv = adv.split("/")[1];
			//Decide how many points you want to give out for each advancement
		SingleGameStats pstats =Globals.playerStats.get(pname); 
		
		if(pstats == null) {
			Bukkit.broadcastMessage("Could not find player with a name of: "+pname);
			
			return;
		}
		
		
		if(pstats.GetTeam() == -1) {
			//This is a solo game
			
		
		switch(adv) {
		case "smelt_iron":
			pstats.GotGuest(0);
			pstats.GotIron((System.nanoTime()-Globals.startTime)/1000000000.0);
			break;
		case "lava_bucket":
			pstats.GotGuest(1);
			break;
		case "enter_the_nether":
			pstats.GotGuest(2);
			pstats.EnteredNether((System.nanoTime()-Globals.startTime)/1000000000.0);
			break;
		case "find_fortress":
			pstats.GotGuest(3);
			break;
		case "obtain_blaze_rod":
			pstats.GotGuest(4);
			break;
		case "follow_ender_eye":
			pstats.GotGuest(5);
			break;
		case "enter_the_end":
			pstats.GotGuest(6);
			break;
		case "kill_dragon":
			
			pstats.WonTheGame((System.nanoTime()-Globals.startTime)/1000000000.0);
			Bukkit.broadcastMessage(pstats.GetName()+" has won the game with a time of: "+ChatColor.BOLD+this.plugin.calculateTime(pstats.GetTime())+"!"+ChatColor.RESET);
			this.plugin.EndGame();
			break;
		//Additionals
		//Overworld
		case "form_obsidian":
			pstats.AddPoints(Globals.extraPoints[2]);
			break;
		case "obtain_armor":
			pstats.AddPoints(Globals.extraPoints[0]);
			break;
		case "mine_diamond":
			pstats.AddPoints(Globals.extraPoints[2]);
			break;
		case "iron_tools":
			pstats.AddPoints(Globals.extraPoints[0]);
			break;
		case "mine_stone":
			pstats.AddPoints(Globals.extraPoints[0]);
			break;
		case "shiny_gear":
			pstats.AddPoints(Globals.extraPoints[2]);
			break; 
		case "deflect_arrow":
			pstats.AddPoints(Globals.extraPoints[0]);
			break; 
		//Nether
		case "find_bastion":
			pstats.AddPoints(Globals.extraPoints[3]);
			break;
		case "fast_travel":
			pstats.AddPoints(Globals.extraPoints[1]);
			break;
		case "distract_piglin":
			pstats.AddPoints(Globals.extraPoints[2]);
			break;
		case "obtain_crying_obsidian":
			pstats.AddPoints(Globals.extraPoints[2]);
			break;
		case "loot_bastion":
			pstats.AddPoints(Globals.extraPoints[2]);
			break;
		case "charge_respawn_anchor":
			pstats.AddPoints(Globals.extraPoints[0]);
			break;
		case "ride_strider":
			pstats.AddPoints(Globals.extraPoints[0]);
			break;
		//Adventure
		case "ol_betsy":
			pstats.AddPoints(Globals.extraPoints[1]);
			break;
		case "kill_a_mob":
			pstats.AddPoints(Globals.extraPoints[0]);
			break;
		case "sleep_in_bed":
			pstats.AddPoints(Globals.extraPoints[1]);
			break;
		case "trade":
			pstats.AddPoints(Globals.extraPoints[3]);
			break;
		case "shoot_arrow":
			pstats.AddPoints(Globals.extraPoints[2]);
			break;
		//end
		case "dragon_breath":	
			pstats.AddPoints(Globals.extraPoints[1]);
			break;
		case "find_end_city":
			pstats.AddPoints(Globals.extraPoints[6]);
			break;
		case "elytra":
			if(!Globals.icarusRun) pstats.AddPoints(Globals.extraPoints[7]);
			break;
		}
		
		}else {
			Team team = Globals.teamObjects.get(pstats.GetTeam());
			//this is a team game
			
			
			switch(adv) {
			case "smelt_iron":
				team.GotQuest(0);
				team.GotIron();
				break;
			case "lava_bucket":
				team.GotQuest(1);
				break;
			case "enter_the_nether":
				team.GotQuest(2);
				team.EnteredNether();
				break;
			case "find_fortress":
				team.GotQuest(3);
				break;
			case "obtain_blaze_rod":
				team.GotQuest(4);
				break;
			case "follow_ender_eye":
				team.GotQuest(5);
				break;
			case "enter_the_end":
				team.GotQuest(6);
				break;
			case "kill_dragon":
				team.WonTheGame();
				Bukkit.broadcastMessage("Team "+team.GetColor()+team.GetTeamString()+": "+team.GetPlayersString()+ChatColor.RESET+" have won the game with a time of: "+ChatColor.BOLD+this.plugin.calculateTime(pstats.GetTime())+"!"+ChatColor.RESET );
				this.plugin.EndGame();
				break;
				//Additionals
				//Overworld
				case "form_obsidian":
					pstats.AddPoints(Globals.extraPoints[2]);
					break;
				case "obtain_armor":
					pstats.AddPoints(Globals.extraPoints[0]);
					break;
				case "mine_diamond":
					pstats.AddPoints(Globals.extraPoints[3]);
					break;
				case "iron_tools":
					pstats.AddPoints(Globals.extraPoints[1]);
					break;
				case "mine_stone":
					pstats.AddPoints(Globals.extraPoints[1]);
					break;
				case "shiny_gear":
					pstats.AddPoints(Globals.extraPoints[2]);
					break; 
				case "deflect_arrow":
					pstats.AddPoints(Globals.extraPoints[0]);
					break; 
				//Nether
				case "find_bastion":
					pstats.AddPoints(Globals.extraPoints[3]);
					break;
				case "fast_travel":
					pstats.AddPoints(Globals.extraPoints[1]);
					break;
				case "distract_piglin":
					pstats.AddPoints(Globals.extraPoints[2]);
					break;
				case "obtain_crying_obsidian":
					pstats.AddPoints(Globals.extraPoints[2]);
					break;
				case "loot_bastion":
					pstats.AddPoints(Globals.extraPoints[2]);
					break;
				case "charge_respawn_anchor":
					pstats.AddPoints(Globals.extraPoints[0]);
					break;
				case "ride_strider":
					pstats.AddPoints(Globals.extraPoints[2]);
					break;
					//Adventure
				case "ol_betsy":
					pstats.AddPoints(Globals.extraPoints[1]);
					break;
				case "kill_a_mob":
					pstats.AddPoints(Globals.extraPoints[0]);
					break;
				case "sleep_in_bed":
					pstats.AddPoints(Globals.extraPoints[1]);
					break;
				case "trade":
					pstats.AddPoints(Globals.extraPoints[3]);
					break;
				case "shoot_arrow":
					pstats.AddPoints(Globals.extraPoints[2]);
					break;
					//end
				case "dragon_breath":	
					pstats.AddPoints(Globals.extraPoints[1]);
					break;
				case "find_end_city":
					pstats.AddPoints(Globals.extraPoints[6]);
					break;
				case "elytra":
					if(!Globals.icarusRun) pstats.AddPoints(Globals.extraPoints[7]);
					break;
				}
			}
		}
		
		
		
	}
}
