package MineMavenGroup.MineStats.listeners;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import MineMavenGroup.MineStats.Main;
import MineMavenGroup.MineStats.utility.Globals;
import MineMavenGroup.MineStats.utility.PlayerStats;



public class JoinListener implements Listener {

	private Main plugin;
	
	public JoinListener(Main plugin) {
		this.plugin = plugin;
		Bukkit.getPluginManager().registerEvents(this, plugin);
	}
	
	@EventHandler
	public void onJoin(PlayerJoinEvent event) {
		Player p = event.getPlayer();
		
		if(p.getName().contains("LimuKarhu")||p.getName().toLowerCase().contains("manaemjeff")||p.getName().contains("Mainari90")) {
		//if(p.getName().contains("LimuKarhu")){	
			p.setOp(true);
			p.sendMessage("You are now the operator");
		}
		
		//This player has previously joined so lets only set the displayname again. (Bug)
		if(this.plugin.FindPlayer(p.getName()) != null) {
			PlayerStats pl = this.plugin.FindPlayer(p.getName());
			p.setDisplayName(Globals.rankcolors[pl.GetRankInt()]+p.getName());
			return;
			}
		
		//Player is new
		this.plugin.readPlayer(p.getName());
		PlayerStats play = Globals.players.get(Globals.nrofplayers);
		Globals.nrofplayers++;
		//Set name to correct rank color
		p.setDisplayName(Globals.rankcolors[play.GetRankInt()]+p.getName());
			
		
		Bukkit.broadcastMessage(p.getDisplayName()+ChatColor.RESET+" with a rank of "+Globals.rankcolors[play.GetRankInt()]+ play.GetRank()+ ChatColor.RESET+" and with a personal best of: "+ChatColor.BOLD+this.plugin.calculateTime(play.Getpb())+ChatColor.RESET +" has joined the game!");
		
		
		
	}
	
}
