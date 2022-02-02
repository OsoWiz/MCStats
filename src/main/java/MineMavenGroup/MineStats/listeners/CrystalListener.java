package MineMavenGroup.MineStats.listeners;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChangedWorldEvent;

import MineMavenGroup.MineStats.Main;

public class CrystalListener implements Listener {
	
	private Main plugin;
	
	public CrystalListener(Main plugin) {
		this.plugin = plugin;
		Bukkit.getPluginManager().registerEvents(this,plugin);
	}
	
	@EventHandler
	public void onEnd(PlayerChangedWorldEvent event) {
		//prevent ender crystals from spawning
		Player p = event.getPlayer();
		//Bukkit.broadcastMessage("Player has changed world to"+ event.getEventName()+" and their current world is: "+p.getWorld().getName());
		//if it does not contain end we return
		
		if(!p.getWorld().getName().contains("end")) return;
		
		List<Entity> lista = p.getWorld().getEntities();
		for(Entity e:lista) {
			if(e.getType()==EntityType.ENDER_CRYSTAL) e.remove();
		}
		
	}
	

}
