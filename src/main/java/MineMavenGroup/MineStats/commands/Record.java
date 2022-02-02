package MineMavenGroup.MineStats.commands;


import java.util.Date;

import org.bson.Document;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import com.mongodb.BasicDBObject;
import com.mongodb.client.MongoCursor;
import MineMavenGroup.MineStats.Main;
import MineMavenGroup.MineStats.utility.PlayerStats;



public class Record implements CommandExecutor {
	
	private Main plu;
	
	private double[] fastest = new double[3];
	
	

	public Record(Main plu) {
		this.plu = plu;
		plu.getCommand("record").setExecutor(this);
	}

	@Override
	public boolean onCommand(CommandSender arg0, Command arg1, String arg2, String[] arg3) {
		// TODO Auto-generated method stub
		
		if(! (arg0 instanceof Player)) return false;
		//PlayerStats peluri = this.plu.FindPlayer(arg0.getName());
		
		
		//Uses two filters to find out documents that have this name and are three weeks recent
		MongoCursor<Document> iterator = this.plu.playercollection.find().sort(new BasicDBObject("pb",1)).limit(3).iterator();
		int i = 0;
		while(iterator.hasNext()) {
			Document doc =iterator.next();
			if(doc ==null) break;
			double aika = doc.getDouble("pb");
			if( aika > 0.01) {
				Bukkit.broadcastMessage("aika löytyi: "+ aika);
				this.fastest[i] = aika;
				i++;
				i = i%3;
			}
			
		}
		
		arg0.sendMessage("Fastest times are as follows:");
		arg0.sendMessage(plu.calculateTime(fastest[0]));
		arg0.sendMessage(plu.calculateTime(fastest[1]));
		arg0.sendMessage(plu.calculateTime(fastest[2]));
		
		
		return false;
	}


	


}
