package threePplDeathSwap.commands;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import threePplDeathSwap.Main;
import threePplDeathSwap.commands.deathListener.deathListener;
import threePplDeathSwap.commands.freezeListener.freezeListener;

public class fastSwap implements CommandExecutor{
	private Main plugin;
	public fastSwap(Main plugin) {
		this.plugin = plugin;
		plugin.getCommand("swap").setExecutor(this);
	}
	
	//testing method that instantly swaps players
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(cmd.getName().equalsIgnoreCase("swap")) {
			Player p = (Player) sender;
			if(p.hasPermission("deathSwap.use")) {
				ArrayList<Player> players = new ArrayList<Player>();
				for(int i = 0;i<args.length;i++) {
					players.add(Bukkit.getPlayer(args[i]));
					//p.sendMessage(args[i]+ " "+players.get(i));
					players.get(i).setGameMode(GameMode.SURVIVAL);
				}
				deathListener dL = new deathListener(plugin, players);
				freezeListener fL = new freezeListener(plugin);
				Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
					public void run() {
						BukkitRunnable deathSwapTask = new deathSwapRunnable(plugin,players,Bukkit.getWorld("world"), 200);
	    				deathSwapTask.runTask(plugin);
					}
				},0);
				Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
					public void run() {
						Main.freeze = false;
					}
				},200);
			}
		}
		return false;
	}
}
