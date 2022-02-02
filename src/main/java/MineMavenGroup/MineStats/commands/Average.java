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

public class Average implements CommandExecutor {

	private Main plugin;
	
	public Average(Main plugin) {
		this.plugin = plugin;
		plugin.getCommand("average").setExecutor(this);
	}
	
	
	@Override
	public boolean onCommand(CommandSender arg0, Command arg1, String arg2, String[] arg3) {
		
		if(!(arg0 instanceof Player)) return false;
		
		double summa = 0;
		int divider = 0;
		
		if(arg3.length>0&&this.plugin.isInt(arg3[0])) {
			//Range specified in weeks
			int weeks = Integer.parseInt(arg3[0]);
			
			
			
			long msday = 1000 * 60 * 60 * 24;
			Long now = new Date().getTime();
			Long ago = now-weeks*7*msday;
			
			//Uses two filters to find out documents that have this name and are two weeks recent
			MongoCursor<Document> iterator = this.plugin.gamecollection.find(Filters.and(Filters.eq("name", arg0.getName()),Filters.gte("date", ago))).iterator();
			
			while(iterator.hasNext()) {
				Document doc =iterator.next();
				if(doc ==null) break;
				else summa+=doc.getInteger("points");
				
				divider++;
			}
			
			if(divider>0) {
				double avg = summa/divider;
				arg0.sendMessage("You have an average of "+avg+" points from "+divider+" games played during the last "+weeks+" weeks.");
				return true;
			}
			
		
		
		}else {
		//No amount specified	
			
			MongoCursor<Document> iterator = this.plugin.gamecollection.find(Filters.eq("name", arg0.getName() ) ).iterator();
			
			while(iterator.hasNext()) {
				Document doc =iterator.next();
				if(doc ==null) break;
				else summa+=doc.getInteger("points");
				
				divider++;
			}
			
			if(divider>0) {
				double avg = summa/divider;
				arg0.sendMessage("You have an all time average of "+avg+" points from "+divider+" games.");
				return true;
			}
			
			
		}
		
		arg0.sendMessage("You don't have any recorded games in the database!");
		// TODO Auto-generated method stub
		return false;
	}

}
