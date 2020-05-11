package threePplDeathSwap.commands;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
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
		if (!(sender instanceof Player)) {
			sender.sendMessage("Only players can execute this command!");
			return true;
		}
		if(cmd.getName().equalsIgnoreCase("deathSwap")) { //check which command
			Player p = (Player) sender;
			if(p.hasPermission("deathSwap.use")) {
				Bukkit.broadcastMessage(Utils.chat(plugin.getConfig().getString("start_message")
						.replace("<player>", p.getName())));
				Player p1 = Bukkit.getPlayer(args[0]);
				Player p2 = Bukkit.getPlayer(args[1]);
				Player p3 = Bukkit.getPlayer(args[2]);
				p1.setGameMode(GameMode.SURVIVAL);
				p2.setGameMode(GameMode.SURVIVAL);
				p3.setGameMode(GameMode.SURVIVAL);
				deathListener dL = new deathListener(plugin);
				freezeListener fL = new freezeListener(plugin);
				int intialDelay = 6000;
				int roundDelay = 5400;
				int tpDelay = 300;
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
				Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(plugin, new Runnable() {
					public void run() {
						BukkitRunnable deathSwapTask = new deathSwapRunnable(plugin,p1,p2,p3,Bukkit.getWorld("world"));
        				deathSwapTask.runTask(plugin);
					}
				},intialDelay+600,roundDelay);
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
