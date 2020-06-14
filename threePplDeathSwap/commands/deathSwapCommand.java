package threePplDeathSwap.commands;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import threePplDeathSwap.commands.deathListener.deathListener;
import threePplDeathSwap.commands.freezeListener.freezeListener;
import threePplDeathSwap.commands.utils.Utils;
import threePplDeathSwap.Main;

public class deathSwapCommand implements CommandExecutor{
	private Main plugin;
	public deathSwapCommand(Main plugin) {
		this.plugin = plugin;
		plugin.getCommand("deathSwap").setExecutor(this);
	}
	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		
		//ensures a player is the sender
		if (!(sender instanceof Player)) {
			sender.sendMessage("Only players can execute this command!");
			return true;
		}
		
		//checks that it is the deathswap command
		if(cmd.getName().equalsIgnoreCase("deathSwap")) {
			Player p = (Player) sender;
			
			//checks senders permissions
			if(p.hasPermission("deathSwap.use")) {
				Bukkit.broadcastMessage(Utils.chat(plugin.getConfig().getString("start_message")
						.replace("<player>", p.getName())));
				
				//collects all the players from arguments, sets gamemode and starts the death and freeze listeners
				ArrayList<Player> players = new ArrayList<Player>();
				for(int i = 0;i<args.length;i++) {
					
					players.add(Bukkit.getPlayer(args[i]));
					//p.sendMessage(args[i]+ " "+players.get(i));
					players.get(i).setGameMode(GameMode.SURVIVAL);
				}

				deathListener dL = new deathListener(plugin, players);
				freezeListener fL = new freezeListener(plugin);
				
				int intialDelay = 6000; //delay in ticks, 20 ticks = 1sec if server is running properly
				int roundDelay = 5400; //delay between rounds
				int tpDelay = 300; //how long the players are frozen and invunerable
				
				//countdown tasks
				Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(plugin, new Runnable() {
					public void run() {
						Bukkit.broadcastMessage(Utils.chat(plugin.getConfig().getString("swapping_message")
        						.replace("<time>", "30")));
					}
				},intialDelay,roundDelay);
				Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(plugin, new Runnable() {
					public void run() {
						Bukkit.broadcastMessage(Utils.chat(plugin.getConfig().getString("swapping_message")
        						.replace("<time>", "10")));
					}
				},intialDelay+400,roundDelay);
				Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(plugin, new Runnable() {
					public void run() {
						Bukkit.broadcastMessage(Utils.chat(plugin.getConfig().getString("swapping_message")
        						.replace("<time>", "5")));
					}
				},intialDelay+500,roundDelay);
				Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(plugin, new Runnable() {
					public void run() {
						Bukkit.broadcastMessage(Utils.chat(plugin.getConfig().getString("swapping_message")
        						.replace("<time>", "4")));
					}
				},intialDelay+520,roundDelay);
				Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(plugin, new Runnable() {
					public void run() {
						Bukkit.broadcastMessage(Utils.chat(plugin.getConfig().getString("swapping_message")
        						.replace("<time>", "3")));
					}
				},intialDelay+540,roundDelay);
				Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(plugin, new Runnable() {
					public void run() {
						Bukkit.broadcastMessage(Utils.chat(plugin.getConfig().getString("swapping_message")
        						.replace("<time>", "2")));
					}
				},intialDelay+560,roundDelay);
				Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(plugin, new Runnable() {
					public void run() {
						Bukkit.broadcastMessage(Utils.chat(plugin.getConfig().getString("swapping_message")
        						.replace("<time>", "1")));
					}
				},intialDelay+580,roundDelay);
				
				//swap task
				Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(plugin, new Runnable() {
					public void run() {
						BukkitRunnable deathSwapTask = new deathSwapRunnable(plugin,players,Bukkit.getWorld("world"), tpDelay);
        				deathSwapTask.runTask(plugin);
					}
				},intialDelay+600,roundDelay);
				
				//switches off freeze after tpDelay ticks from swap
				Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(plugin, new Runnable() {
					public void run() {
						Main.freeze = false;
					}
				},intialDelay+tpDelay+600,roundDelay);
			} else {
				p.sendMessage("You dont have permission to use this command, the deathSwap is too cool for you");
			}
		}	
		return false;
	}

}
