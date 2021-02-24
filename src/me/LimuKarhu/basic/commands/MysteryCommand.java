package me.LimuKarhu.basic.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import me.LimuKarhu.basic.Main;

public class MysteryCommand implements CommandExecutor {

private Main plugin;
	
	public MysteryCommand(Main plugin) {
		this.plugin = plugin;
		plugin.getCommand("mystery").setExecutor(this);
	}
	
	@Override
	public boolean onCommand( CommandSender arg0,  Command arg1,  String arg2,
			 String[] arg3) {
		arg0.sendMessage("MYSTEERI");
		
		return false;
	}

}
