package MineMavenGroup.MineStats.commands;



import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import MineMavenGroup.MineStats.Main;
import MineMavenGroup.MineStats.utility.Globals;
import MineMavenGroup.MineStats.utility.SingleGameStats;

public class Jointeam implements CommandExecutor {
	
	private Main plugin;
	
	public Jointeam(Main pug) {
		this.plugin = pug;
		pug.getCommand("join").setExecutor(this);
	}

	@Override
	public boolean onCommand(CommandSender arg0, Command arg1, String arg2, String[] arg3) {
		
		if(!Globals.pollingstarted) {
			arg0.sendMessage("Polling is not available at this moment.");
		}else if(arg3.length ==0) {
			arg0.sendMessage("You cannot join an arbitrary team...");
		}else if(!this.plugin.isInt(arg3[0])) {//eg not int
			arg0.sendMessage("Please specify the team using a number. Using team name will appear at a later date.");
		}
		else {
			String pname =arg0.getName();
			SingleGameStats s = Globals.playerStats.get(pname);
			if(s==null) {
				arg0.sendMessage("It seems your name does not match any player on the server currently.");
				return false;
			}
			//Player is not null
			int team = Integer.parseInt(arg3[0]);
			if(team>Globals.nrofteams||team<0) {
				arg0.sendMessage("That number is not in the range of teamnumbers. Please try again.");
				return false;
			}
			
			s.SetTeam(team);
			arg0.sendMessage("You are now part of team "+team+"!");
			return true;
		}
		
		
		
		// TODO Auto-generated method stub
		return false;
	}
	
	

}
