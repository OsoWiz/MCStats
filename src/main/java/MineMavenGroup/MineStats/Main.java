package MineMavenGroup.MineStats;


import java.io.File;
import java.io.FileNotFoundException;
import java.util.Date;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;


import org.bson.Document;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;


//Database imports

import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;

import MineMavenGroup.MineStats.commands.Amount;
import MineMavenGroup.MineStats.commands.Average;
import MineMavenGroup.MineStats.commands.Elytra;
import MineMavenGroup.MineStats.commands.Jointeam;
import MineMavenGroup.MineStats.commands.Poll;
import MineMavenGroup.MineStats.commands.Rank;
import MineMavenGroup.MineStats.commands.Record;
//My imports
import MineMavenGroup.MineStats.commands.Start;
import MineMavenGroup.MineStats.commands.Stats;
import MineMavenGroup.MineStats.commands.Teams;
import MineMavenGroup.MineStats.commands.Update;
import MineMavenGroup.MineStats.commands.UploadAll;
import MineMavenGroup.MineStats.commands.UploadGame;
import MineMavenGroup.MineStats.listeners.AdvancementListener;
import MineMavenGroup.MineStats.listeners.CrystalListener;
import MineMavenGroup.MineStats.listeners.Deathlistener;
import MineMavenGroup.MineStats.listeners.JoinListener;
import MineMavenGroup.MineStats.listeners.MovementListener;
import MineMavenGroup.MineStats.listeners.PvpListener;
import MineMavenGroup.MineStats.listeners.RespawnListener;
import MineMavenGroup.MineStats.listeners.bedlistener;
import MineMavenGroup.MineStats.utility.Globals;
import MineMavenGroup.MineStats.utility.PlayerStats;
import MineMavenGroup.MineStats.utility.SingleGameStats;
import MineMavenGroup.MineStats.utility.Team;

import com.mongodb.client.MongoClient;
//Spigot imports 



public class Main extends JavaPlugin {
	
	public MongoCollection<Document> playercollection;
	public MongoCollection<Document> gamecollection;

       
	@Override
    public void onEnable() {
		//Read database info from filesystem
		String dbCred = "";
		try {
			dbCred = new Scanner(new File("databasestring.env")).useDelimiter("\\Z").next();
			
		}
		catch(FileNotFoundException e) {
			System.out.println("Reading of database credentials failed. Shutting down");
			System.exit(3);
		}
		//Database initialization
		 MongoClient mongoClient = MongoClients.create(dbCred);
	     
		 //Get the right database and then the collections from that database
		 //Players for single player stats. And gamecollection for inserting the new games
		 MongoDatabase database = mongoClient.getDatabase("players");
	     this.playercollection = database.getCollection("playercollection");
	     this.gamecollection = database.getCollection("gamecollection");
	     //disable useless logging
	      Logger logger = Logger.getLogger("org.mongodb.driver");
	       	logger.setLevel(Level.SEVERE);
	       	
	        //Commands
			this.getCommand("start").setExecutor(new Start(this));
			new Stats(this);
			new Update(this);
			new UploadGame(this);
			new UploadAll(this);
			new Rank(this);
			new Average(this);
			new Amount(this);
			new Poll(this);
			new Jointeam(this);
			new Record(this);
			new Teams(this);
			new Elytra(this);
			//Listeners
			new JoinListener(this);
			new MovementListener(this);
			new AdvancementListener(this);
			new Deathlistener(this);
			new CrystalListener(this);
			new PvpListener(this);
			new RespawnListener(this);
			new bedlistener(this);

	        	
    }
  
    /*Reads the player data from the database or creates a new one if the player is new */
    public void readPlayer(String name){
    	
    	Document player =playercollection.find(Filters.eq("name",name)).first();
    	
    	
        if(player == null){
        	
        	PlayerStats uus = new PlayerStats(name);
        	Globals.players.add(uus);
        	Bukkit.broadcastMessage("adding a new player!!");
            //User not saved yet. Add him in the DB!
        	Document playerdoc = new Document("name",name)
        			.append("rank", 0)
        			.append("kills", 0)
        			.append("deaths", 0)
        			.append("pb",  999999.0)
        			;
        	
        	this.playercollection.insertOne(playerdoc);
            return;
        }
        //Player is not new
		
        int currentRank = this.calculateRank(name);
        
        PlayerStats peluri = new PlayerStats(name,currentRank, (Integer)player.getInteger("kills"), (Integer)player.getInteger("deaths"),(Double)player.getDouble("pb"));
        Globals.players.add(peluri);
        
    	}
    /*Stores the game data of a certain player to a database*/
    public void StoreAndCalculateGameStats(String name) {
    	
    	SingleGameStats statsit = Globals.playerStats.get(name);
    	
    	if(statsit == null) return;
    	//Calculate the final points
    	statsit.CalculatePoints();
    	Document gamedoc = new Document("name",name)
    						.append("points", statsit.GetPoints())
    						.append("kills", statsit.GetKills())
    						.append("deaths", statsit.GetDeaths())
    						.append("date", statsit.GetDate().getTime()) //Store date in Long format for easier finding
    						.append("irontime", statsit.GetIronTime())
    						.append("nethertime", statsit.GetNetherTime())
    						.append("time", statsit.GetTime())
    						.append("category", statsit.GetCategory())
    						;
    	
    	this.gamecollection.insertOne(gamedoc);
    	//Bukkit.broadcastMessage("Player "+name+"'s gamestats stored into the database!");
    	
    	}
    //Just stores the game, updating must be done separately
    public void StoreGameStats(SingleGameStats statsit) {
    	
    	Document gamedoc = new Document("name",statsit.GetName())
				.append("points", statsit.GetPoints())
				.append("kills", statsit.GetKills())
				.append("deaths", statsit.GetDeaths())
				.append("date", statsit.GetDate().getTime()) //Store date in Long format for easier finding
				.append("irontime", statsit.GetIronTime())
				.append("nethertime", statsit.GetNetherTime())
				.append("time", statsit.GetTime())
				.append("category", statsit.GetCategory())
				;

    	this.gamecollection.insertOne(gamedoc);
    	
    }
    
    
    public void UpdatePlayer(PlayerStats p) {
    	
		
		p.UpdateStats(Globals.playerStats.get(p.GetName()));
		//we update the found one with the updated one, hopefully
		Document pdoc = p.GetUpdatedDocs(gamecollection);
		this.playercollection.replaceOne(Filters.eq("name",p.GetName()),pdoc);
    }
    
    
    public void EndGame() {
    	
    	if(Globals.teamObjects.isEmpty()) {
    	for(PlayerStats p: Globals.players) {
    		this.StoreAndCalculateGameStats(p.GetName());
    		this.UpdatePlayer(p);
    		}
    	}else {
    		//teamgame
    		for(Team t:Globals.teamObjects) {
    			//Calculate points for each team
    			t.CalculatePoints();
    			//And store the game for each teammate after that
    			for(SingleGameStats s: t.GetTeam()) {
    				this.StoreGameStats(s);
    			}
    			
    		}
    		//And then update the players themselves. This is fucking slow
    		for(PlayerStats p: Globals.players) {
    			this.UpdatePlayer(p);
    		}
    	}
    	
    	
    	Bukkit.broadcastMessage("Updating the database has finished");
    	
    }
    
    public String calculateTime(Double seconds) {
    	if(seconds >= 900000) return "too much";
    	int hours = (int) (seconds/3600);
    	double secslft = seconds -hours*3600;
    	int minutes = (int) (secslft/60);
    	secslft = secslft-60*minutes;
    	
    	//3h 24m 15.3s
    	return hours+"h "+minutes+"m "+String.format("%.3f", secslft)+"s";
    }
    
    /*Returns the PlayerStats object by name. Returns null if none is found*/
    public PlayerStats FindPlayer(String name) {
    	
    	for(PlayerStats p: Globals.players) {
    		if(p.GetName().contains(name)) return p;
    	}
    	
    	
    	return null;
    }
    
    public boolean isInt(String numero) {
    	
    	try {
    		Integer.parseInt(numero);
    		return true;
    	}catch(NumberFormatException e) {
    		return false;
    	}
    	
    	
    }
    
    public int calculateRank(String name) {
    	
    	long msday = 1000 * 60 * 60 * 24;
		Long now = new Date().getTime();
		Long ago = now-3*7*msday;
        
		MongoCursor<Document> iterator= gamecollection.find(Filters.and(Filters.eq("name", name),Filters.gte("date", ago))).iterator();
		
		double summa = 0;
		int divider = 0;
		while(iterator.hasNext()) {
			Document doc =iterator.next();
			if(doc ==null) break;
			else summa+=doc.getInteger("points");
			
			divider++;
		}
		
		//Bukkit.broadcastMessage("Went over "+divider+" documents for player "+this.GetName());
		
		int newrank = 0;
		if(divider >2) {
		summa = summa/divider;
		
		double number = (summa/15000)+1;
		double luku = Math.pow(number, summa)+summa/100.0;
		
		 newrank = (int) Math.min(luku,7);
		}
    	
    	return newrank;
    }
    
    public void giveIcarus(Player p) {
    	ItemStack Elytra = new ItemStack(Material.ELYTRA, 1);
		ItemStack raketit = new ItemStack(Material.FIREWORK_ROCKET, 64);
    	
    	p.getInventory().addItem(Elytra);

    	p.getInventory().addItem(raketit);
    }
    
    
}//Class ends here

