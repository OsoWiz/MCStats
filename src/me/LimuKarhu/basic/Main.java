package me.LimuKarhu.basic;

import org.bukkit.plugin.java.JavaPlugin;

import me.LimuKarhu.basic.commands.HelloCommand;
import me.LimuKarhu.basic.commands.MysteryCommand;

public class Main extends JavaPlugin {
	
	@Override
	public void onEnable() {
			new HelloCommand(this);
			new MysteryCommand(this);
	}

}
