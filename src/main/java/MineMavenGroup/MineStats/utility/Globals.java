package MineMavenGroup.MineStats.utility;

import java.util.ArrayList;
import java.util.HashMap;

import org.bukkit.ChatColor;


/*Represents global variables that can be accessed by other classes*/
public class Globals {
		
		public static long startTime = 0;
		
		public static String[] ranks = {"Monni","Wood","Stone","Iron","Gold","Diamond","Netherite","Enchanted"};
		
		public static String[] teams = {"Red","Green","Blue","Yellow","Cyan","Magenta","Black","White"};
		
		public static ChatColor[] rankcolors = {ChatColor.WHITE,ChatColor.YELLOW,ChatColor.DARK_GRAY,ChatColor.GRAY,ChatColor.GOLD,ChatColor.AQUA,ChatColor.BLACK,ChatColor.DARK_PURPLE};
		public static ChatColor[] teamcolors = {ChatColor.RED,ChatColor.GREEN,ChatColor.BLUE,ChatColor.YELLOW,ChatColor.DARK_AQUA,ChatColor.LIGHT_PURPLE,ChatColor.BLACK,ChatColor.WHITE};
		
		
		public static boolean gameHasStarted = false;
		//name that points into a singlegamestat
		public static HashMap<String,SingleGameStats> playerStats = new HashMap<String,SingleGameStats>();
		
		public static ArrayList<Team> teamObjects = new ArrayList<Team>();
		
		public static ArrayList<PlayerStats> players = new ArrayList<PlayerStats>();
		
		public static int nrofplayers = 0;
		public static int nrofteams = 0;
		
		public static int[] advancementPoints = {8,7,20,25,30,15,25,30};
		
		public static int[] extraPoints = {1,2,3,5,7,10, 15, 20};
		
		public static int[] pointLimits = {0,67,102,124,140,151,160};
		
		public static double rankAvg = 0;
		
		public static boolean pollingstarted = false;
		
		public static boolean icarusRun = false;
		
		public static String category = "rsg";
		
		/*How many seconds in the timer*/
		public static int timertime = 20; 
		
		public static int furthestAchievement = -1;
		
		public static long cooldown = 300;
		
	}
