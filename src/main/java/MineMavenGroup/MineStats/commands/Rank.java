package MineMavenGroup.MineStats.commands;

import java.util.Date;

import org.bson.Document;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.mongodb.client.MongoCursor;
import com.mongodb.client.model.Filters;

import MineMavenGroup.MineStats.Main;
import MineMavenGroup.MineStats.utility.Globals;
import MineMavenGroup.MineStats.utility.PlayerStats;
import net.md_5.bungee.api.ChatColor;

public class Rank implements CommandExecutor {
	
	private Main plugin;
	public Rank(Main plugin) {
		this.plugin = plugin;
		plugin.getCommand("rank").setExecutor(this);
	}
	

	@Override
	public boolean onCommand(CommandSender arg0, Command arg1, String arg2, String[] arg3) {
		
		if(! (arg0 instanceof Player)) return false;
		PlayerStats peluri = this.plugin.FindPlayer(arg0.getName());
		
		long msday = 1000 * 60 * 60 * 24;
		Long now = new Date().getTime();
		Long weeksago = now-21*msday;
		
		//Uses two filters to find out documents that have this name and are three weeks recent
		MongoCursor<Document> iterator = this.plugin.gamecollection.find(Filters.and(Filters.eq("name", arg0.getName()),Filters.gte("date", weeksago))).iterator();
		double summa = 0;
		int divider = 0;
		while(iterator.hasNext()) {
			Document doc =iterator.next();
			if(doc ==null) break;
			else summa+=doc.getInteger("points");
			
			divider++;
		}
		
		if(divider<3) {
			arg0.sendMessage("You need to complete at least three games in order to have a rank.");
			return false;
		}
		
		if(peluri.GetRankInt()>=7) {
			arg0.sendMessage("You currently have a rank of "+Globals.rankcolors[peluri.GetRankInt()]+peluri.GetRank()+ChatColor.RESET+" and that is the highest rank there is.");
			return true;
		}
		//Current rank is now 1 higher than index 
		int pointLimit = Globals.pointLimits[peluri.GetRankInt()];
		
		int required = pointLimit*(divider+1)- (int)summa;
		
		arg0.sendMessage("You currently have a rank of "+Globals.rankcolors[peluri.GetRankInt()]+peluri.GetRank()+ChatColor.RESET+" and you need "+required+" points from this game to reach the next rank.");
		
		return false;
	}

}
