package MineMavenGroup.MineStats.listeners;

import org.bukkit.Bukkit;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.Event.Result;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockExplodeEvent;
import org.bukkit.event.player.PlayerBedEnterEvent;
import org.bukkit.event.player.PlayerBedEnterEvent.BedEnterResult;

import MineMavenGroup.MineStats.Main;

public class bedlistener implements Listener{
private Main plugin;
	
	public bedlistener(Main plugin) {
		this.plugin = plugin;
		Bukkit.getPluginManager().registerEvents(this,plugin);
	}
	
	@EventHandler
	public void onBed(PlayerBedEnterEvent event) {
		Player p = event.getPlayer();
		
		if(p.getWorld().getName().contains("end")) {
			event.setCancelled(true);
			p.kickPlayer("Using bed on end is strictly prohibited");
		}else if(p.getWorld().getName().contains("nether")) {
			//event.setCancelled(true);
			
		}	
	}
	
	@EventHandler
	public void explode(BlockExplodeEvent event) {
		//Bukkit.broadcastMessage(event.getEventName());
		}
	

}
