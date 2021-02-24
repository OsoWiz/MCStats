package me.LimuKarhu.basic.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import me.LimuKarhu.basic.Main;


public class HelloCommand implements CommandExecutor {
	
	private Main plugin;
	
	public HelloCommand(Main plugin) {
		this.plugin = plugin;
		plugin.getCommand("hello").setExecutor(this);
	}
	
	
	@Override
	public boolean onCommand( CommandSender sender, Command cmd, String label, String[] args) {
		// TODO Auto-generated method stub
		
		sender.sendMessage("Hello legend");
		
		return false;
	}

}
