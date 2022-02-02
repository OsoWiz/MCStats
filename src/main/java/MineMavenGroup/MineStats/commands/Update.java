package MineMavenGroup.MineStats.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import MineMavenGroup.MineStats.Main;
import MineMavenGroup.MineStats.utility.Globals;
import MineMavenGroup.MineStats.utility.PlayerStats;

public class Update implements CommandExecutor {

	private Main plugin;
	
	public Update(Main plugin)
	{
		this.plugin = plugin;
		plugin.getCommand("update").setExecutor(this);
	}
	
	@Override
	public boolean onCommand(CommandSender arg0, Command arg1, String arg2, String[] arg3) {
		// TODO Auto-generated method stub
		
		if(!Globals.gameHasStarted) return false;
		
		if(!(arg0 instanceof Player)) return false;
		
		
		for(PlayerStats p:Globals.players) {
			
		if(p.GetName().contains(arg0.getName())) {
			arg0.sendMessage("Updating your stats..");
			this.plugin.UpdatePlayer(p);
			arg0.sendMessage("Updating complete");
			return false;
		}
			
		}
		
		arg0.sendMessage("Updating failed for some reason.");
		
		
		return false;
	}

	
	
}
