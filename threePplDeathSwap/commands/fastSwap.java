package threePplDeathSwap.commands;

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
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		//Bukkit.broadcastMessage("oncommand");
		if(cmd.getName().equalsIgnoreCase("swap")) {
			Player p = (Player) sender;
			if(p.hasPermission("deathSwap.use")) {
				//Bukkit.broadcastMessage("oncommand is swap and has perms");
				Player p1 = Bukkit.getPlayer(args[0]);
				Player p2 = Bukkit.getPlayer(args[1]);
				Player p3 = Bukkit.getPlayer(args[2]);
				p1.setGameMode(GameMode.SURVIVAL);
				p2.setGameMode(GameMode.SURVIVAL);
				p3.setGameMode(GameMode.SURVIVAL);
				//Bukkit.broadcastMessage(p1.getName()+ " " + p2.getName() + " " + p3.getName());
				deathListener dL = new deathListener(plugin);
				freezeListener fL = new freezeListener(plugin);
				Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
					public void run() {
						//Bukkit.broadcastMessage("ass");
						BukkitRunnable deathSwapTask = new deathSwapRunnable(plugin,p1,p2,p3,Bukkit.getWorld("world"));
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
