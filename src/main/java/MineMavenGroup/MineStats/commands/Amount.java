package MineMavenGroup.MineStats.commands;


import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import MineMavenGroup.MineStats.Main;

public class Amount implements CommandExecutor {

	private Main plugin;

	public Amount(Main plugin) {
		this.plugin = plugin;
		plugin.getCommand("amount").setExecutor(this);
	}
	
	@Override
	public boolean onCommand(CommandSender arg0, Command arg1, String arg2, String[] arg3) {
		// TODO Auto-generated method stub
		
		
		
		if(!(arg0 instanceof Player))return false;
		
		
		
		Player p = (Player) arg0;
		
		if(arg3.length<1) {
		
			
			int maara = 0;
			int rodit = 0;
			int pearls = 0;
			for(ItemStack item:p.getInventory().getContents()){
				if(item!=null&&item.getType() == Material.ENDER_EYE) {
					maara+=item.getAmount();
				}else if(item!=null&&item.getType() == Material.ENDER_PEARL) {
					pearls+=item.getAmount();
				}else if(item!=null&&item.getType() == Material.BLAZE_ROD) {
					rodit+=item.getAmount();
				}
				
			}
			arg0.sendMessage("You currently have "+maara+" ender eyes, "+rodit+" blaze rods and "+pearls+" ender pearls");
			
			
			
			
			
		}else {
			//Has arguments
			
			
		}
		
		return false;
	}

	
}
