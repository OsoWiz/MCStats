package MineMavenGroup.MineStats.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import MineMavenGroup.MineStats.Main;
import MineMavenGroup.MineStats.utility.Globals;
import MineMavenGroup.MineStats.utility.SingleGameStats;
import net.md_5.bungee.api.ChatColor;

import org.bukkit.entity.Player;

public class Stats implements CommandExecutor {
	
	private Main plugin;
	
	public Stats(Main plugin) {
		this.plugin = plugin;
		plugin.getCommand("stats").setExecutor(this);
	}

	@Override
	public boolean onCommand(CommandSender arg0, Command arg1, String arg2, String[] arg3) {
		
		if(!Globals.gameHasStarted) return false;
		
		if(arg0 instanceof Player || arg0.isOp()) {
			Player p = ((Player) arg0).getPlayer();
			
			if(Globals.playerStats.isEmpty())return false;
			
			SingleGameStats s = Globals.playerStats.get(p.getName());
			
			if(s==null)return false;
			
			p.sendMessage("Your stats are as follows: ");
			p.sendMessage("Points: "+s.GetPoints());
			p.sendMessage("Kills: "+s.GetKills());
			p.sendMessage("Deaths: "+s.GetDeaths());
			p.sendMessage("Main quest level:"+s.GetLevel());
			if(s.GetTeam()>-1)p.sendMessage("Team "+Globals.teamcolors[s.GetTeam()]+s.GetTeamString()+ChatColor.RESET+" : "+Globals.teamObjects.get(s.GetTeam()).GetPlayersString());
		}else {
			arg0.sendMessage("This command may only be sent by players");
		}
		
		// TODO Auto-generated method stub
		return false;
	}
	
	

}
