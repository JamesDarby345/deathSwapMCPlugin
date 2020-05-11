package threePplDeathSwap.commands;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import threePplDeathSwap.commands.utils.Utils;
import threePplDeathSwap.Main;

public class deathSwapRunnable extends BukkitRunnable{
	private final JavaPlugin plugin;
	private Player p1;
	private Player p2;
	private Player p3;
	private World myWorld;
	
	public deathSwapRunnable (JavaPlugin plugin, Player p1, Player p2, Player p3, World myWorld) {
		this.plugin = plugin;
		this.p1 = p1;
		this.p2 = p2;
		this.p3 = p3;
		this.myWorld = myWorld;
	}
	
	public void run() {
				Location loc1 = null;
				Location loc2 = null;
				Location loc3 = null;
				
				//if dead dont set location
				if(!p1.getGameMode().equals(GameMode.SPECTATOR)) {
					loc1 = p1.getLocation();
				} 
				if(!p2.getGameMode().equals(GameMode.SPECTATOR)) {
					loc2 = p2.getLocation();
				}
				if(!p3.getGameMode().equals(GameMode.SPECTATOR)) {
					loc3 = p3.getLocation();
				} 
				
				//checks if a player is dead and swaps the surviving players
				if(loc1 == null) {
					Bukkit.broadcastMessage(p1.getName() + "p1 is dead, swapping other two");
					freezePlayers(null,p2,p3);
					p2.teleport(loc3);
					p3.teleport(loc2);
				} else if(loc2 == null) {
					Bukkit.broadcastMessage(p2.getName() + "p2 is dead, swapping other two");
					freezePlayers(p1,null,p3);
					p1.teleport(loc3);
					p3.teleport(loc1);
				} else if(loc3 == null) {
					Bukkit.broadcastMessage(p3.getName() + "p3 is dead, swapping other two");
					Bukkit.broadcastMessage(p3.getName() + " loc is " + loc3 + " " + loc2 + " " + loc1);
					freezePlayers(p1,p2,null);
					p1.teleport(loc2);
					p2.teleport(loc1);
				}else if ((loc1 == null && loc2 == null) ||(loc1 == null && loc3 == null) ||(loc2 == null && loc3 == null)) {//2 players are dead, game is over, theoretically should never run
					Main.freeze = false;
					Bukkit.getScheduler().cancelTasks(plugin);
				}else {
					freezePlayers(p1,p2,p3);
					p1.teleport(loc2);
					p2.teleport(loc3);
					p3.teleport(loc1);	
				} 
				Bukkit.broadcastMessage(Utils.chat(plugin.getConfig().getString("swaped_message")));
		}
	
	//makes it so players can't see or move much while swapping
	private void freezePlayers(Player p1,Player p2, Player p3) {
		int tpDelay = 300;
		Main.freeze = true;
		if(p1 != null) {
			p1.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, tpDelay-5, 9999));
			p1.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, tpDelay-5, 9999));
			p1.addPotionEffect(new PotionEffect(PotionEffectType.SLOW_FALLING, tpDelay-5, 9999));
			p1.setNoDamageTicks(tpDelay+5);
		}
		if(p2 != null) {
			p2.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, tpDelay-5, 9999));
			p2.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, tpDelay-5, 9999));
			p2.addPotionEffect(new PotionEffect(PotionEffectType.SLOW_FALLING, tpDelay-5, 9999));
			p2.setNoDamageTicks(tpDelay+5);
		}
		if(p3 != null) {
			p3.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, tpDelay-5, 9999));
			p3.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, tpDelay-5, 9999));
			p3.addPotionEffect(new PotionEffect(PotionEffectType.SLOW_FALLING, tpDelay-5, 9999));
			p3.setNoDamageTicks(tpDelay+5);
		}
	}
}

