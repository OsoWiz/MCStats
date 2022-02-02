package MineMavenGroup.MineStats.listeners;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.ItemStack;

import MineMavenGroup.MineStats.Main;
import MineMavenGroup.MineStats.utility.Globals;
import MineMavenGroup.MineStats.utility.SingleGameStats;

public class RespawnListener implements Listener {

	private Main plugin;
	
	public RespawnListener(Main plugin) {
		this.plugin = plugin;
		Bukkit.getPluginManager().registerEvents(this, plugin);
	}
	
	@EventHandler
	public void onRespawn(PlayerRespawnEvent e) {
		Player p = e.getPlayer();
		String pname = p.getName();
		SingleGameStats s = Globals.playerStats.get(pname);
		if(s == null) {
			p.sendMessage("You have no game stat stored. Unable to compensate..");
			return;
		}
		//Icarus rules:
				if(Globals.icarusRun) {
					this.plugin.giveIcarus(p);
				}
		
		if(s.onCooldown(Globals.cooldown)) {
			p.sendMessage("You are still on cooldown. You still have "+ (Globals.cooldown-System.nanoTime()/1000000000 + s.lastCooldown)+ " seconds of cooldown left.");
			return;
		}
		
		//s is not null, we can continue
		
		if(s.GetLevel()>1 && s.GetLevel()<6) {
			ItemStack cobblet = new ItemStack(Material.COBBLESTONE, 6);
			ItemStack puut = new ItemStack(Material.OAK_LOG, 6);
			ItemStack uuni = new ItemStack(Material.FURNACE, 1);
			ItemStack hiili = new ItemStack(Material.COAL, 1);
			ItemStack safka = new ItemStack(Material.SALMON, 4);
			if(s.GetLevel()>2) {
			ItemStack kilpi = new ItemStack(Material.SHIELD, 1);
			p.getInventory().addItem(kilpi);
			
			if(s.GetLevel()>4) {
			ItemStack irska = new ItemStack(Material.IRON_ORE,3);
			p.getInventory().addItem(irska);
				}
			}
			//Additional help if you are behind
			int erotus = Globals.furthestAchievement-s.GetLevel();
			if(erotus > 0) {
				ItemStack bootsit = new ItemStack(Material.GOLDEN_BOOTS, 1);
				p.getInventory().addItem(bootsit);
				if(erotus>1) {
					ItemStack foodsupply = new ItemStack(Material.WHEAT, 24);
					p.getInventory().addItem(foodsupply);
					if(erotus>2) {
						ItemStack pick = new ItemStack(Material.IRON_PICKAXE, 1);
						p.getInventory().addItem(pick);
					}
					
				}
				
			}//loppuu erotusboostit
				
			
			
			
		p.getInventory().addItem(cobblet,puut,uuni,hiili,safka);
		}
		//p.
		s.SetCooldown();
		
		
		
	}
	
}
