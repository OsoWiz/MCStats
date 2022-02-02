package MineMavenGroup.MineStats.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import MineMavenGroup.MineStats.Main;

public class UploadGame implements CommandExecutor {

	
	private Main plugin;
	
	public UploadGame(Main plugin) {
		this.plugin = plugin;
		plugin.getCommand("upload").setExecutor(this);
		
	}
	
	@Override
	public boolean onCommand(CommandSender arg0, Command arg1, String arg2, String[] arg3) {
		
		
		if(arg0.isOp()) {
			this.plugin.StoreAndCalculateGameStats(arg0.getName());
			arg0.sendMessage("Uploading complete!");
		}
		else {
			arg0.sendMessage("you don't have a permission");
		}
		// TODO Auto-generated method stub
		return false;
	}

}
