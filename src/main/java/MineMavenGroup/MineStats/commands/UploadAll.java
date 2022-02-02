package MineMavenGroup.MineStats.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import MineMavenGroup.MineStats.Main;
import MineMavenGroup.MineStats.utility.Globals;
import MineMavenGroup.MineStats.utility.PlayerStats;

public class UploadAll implements CommandExecutor {

	private Main plugin;
	
	public UploadAll(Main plugin) {
		this.plugin = plugin;
		plugin.getCommand("uploadall").setExecutor(this);
	}
	
	@Override
	public boolean onCommand(CommandSender arg0, Command arg1, String arg2, String[] arg3) {
		
		
		if(arg0.isOp()) {
			for(PlayerStats p: Globals.players) {
				this.plugin.StoreAndCalculateGameStats(p.GetName());
				
			}
			
			
			arg0.sendMessage("Uploading complete!");
		}
		else {
			arg0.sendMessage("you don't have a permission");
		}
		// TODO Auto-generated method stub
		return false;
	}
}
