package MineMavenGroup.MineStats.listeners;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

import MineMavenGroup.MineStats.Main;
import MineMavenGroup.MineStats.utility.Globals;



public class MovementListener implements Listener {

	private Main plugin;
	
	public MovementListener(Main plugin) {
		this.plugin = plugin;
		Bukkit.getPluginManager().registerEvents(this, plugin);
	}
	
	@EventHandler
	public void onMove(PlayerMoveEvent move) {
		//Players should not be able to move unless the game has been started
		if(!Globals.gameHasStarted) {
			move.setCancelled(true);
		}
	}
	
}