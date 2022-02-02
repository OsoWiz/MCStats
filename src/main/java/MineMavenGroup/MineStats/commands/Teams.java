package MineMavenGroup.MineStats.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import MineMavenGroup.MineStats.utility.Team;

import MineMavenGroup.MineStats.Main;
import MineMavenGroup.MineStats.utility.Globals;

public class Teams implements CommandExecutor {

	private Main p;
	
	public Teams(Main p) {
		this.p = p;
		p.getCommand("teams").setExecutor(this);
	}
	
	@Override
	public boolean onCommand(CommandSender arg0, Command arg1, String arg2, String[] arg3) {
		// TODO Auto-generated method stub
		if(!Globals.gameHasStarted) return false;
		
		arg0.sendMessage("The teams are as follows:");
		for(Team t:Globals.teamObjects){
			arg0.sendMessage(t.GetTeamInfo());
		}
		
		return true;
	}

}
