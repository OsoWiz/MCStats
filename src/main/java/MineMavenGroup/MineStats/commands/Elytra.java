package MineMavenGroup.MineStats.commands;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import MineMavenGroup.MineStats.Main;
import MineMavenGroup.MineStats.utility.Globals;

public class Elytra implements CommandExecutor {
	
	private Main plu;
	
	public Elytra(Main main) {
		this.plu = main;
		main.getCommand("elytra").setExecutor(this);
	}

	@Override
	public boolean onCommand(CommandSender arg0, Command arg1, String arg2, String[] arg3) {
		// TODO Auto-generated method stub
		if(!Globals.gameHasStarted || !(arg0 instanceof Player)||!Globals.icarusRun) {
			return false;
		}
		Player peluri = (Player) arg0;
		ItemStack ely = new ItemStack(Material.ELYTRA);
		peluri.getInventory().addItem(ely);
		
		return true;
	}
	

}
