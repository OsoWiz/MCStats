package MineMavenGroup.MineStats.listeners;

import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

import MineMavenGroup.MineStats.Main;
import MineMavenGroup.MineStats.utility.Globals;
import MineMavenGroup.MineStats.utility.SingleGameStats;

public class Deathlistener implements Listener {

	private Main plugin;
	
		public Deathlistener(Main plugin) {
			this.plugin = plugin;
			Bukkit.getPluginManager().registerEvents(this,plugin);
		}
	
		@EventHandler
		public void onDeath(PlayerDeathEvent event) {
			Player ded = event.getEntity();
			Entity killer = ded.getKiller();
			SingleGameStats stats =Globals.playerStats.get(ded.getName());
			
			if(stats == null) return;
			
			stats.AddDeath();
			
			if(killer instanceof Player) {
				//you cannot get kills for killing your teammate
				if(stats.GetTeam()>-1&&Globals.teamObjects.get(stats.GetTeam()).GetPlayersString().contains(killer.getName())) return;
				//Not killing teammates so award the killer
				SingleGameStats killstats = Globals.playerStats.get(killer.getName());
				if(killstats == null)return;
				
				killstats.AddKill();
				
			}
			
			
		}
	
}
