package threePplDeathSwap.commands;

import java.util.ArrayList;

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
	private ArrayList<Player> players;
	private World myWorld;
	private int tpDelay;
	
	public deathSwapRunnable (JavaPlugin plugin, ArrayList<Player> players, World myWorld, int tpDelay) {
		this.plugin = plugin;
		this.players = players;
		this.myWorld = myWorld;
		this.tpDelay = tpDelay;
	}
	
	public void run() {
				ArrayList<Location> locs = new ArrayList<Location>(players.size());
				//locs.add(null);
				String deadPlayers = "";
				
				//if dead dont add location to array
				for(int i = 0;i<players.size();i++) {
					if(!players.get(i).getGameMode().equals(GameMode.SURVIVAL)) {
						deadPlayers += players.get(i).getName() + " ";
					}else {
						locs.add(players.get(i).getLocation());
					}
				}
				
				freezePlayers(players, tpDelay);				
				int j = 1;
				
				//swaps all surviving players to the next location in the array
				for(int i = 0;i<players.size();i++) {
					if(players.get(i).getGameMode().equals(GameMode.SURVIVAL)) {
						if(j == locs.size()) {
							j = 0;
						} 
						players.get(0).sendMessage(players.get(i) + " " + locs.get(j));
						players.get(i).teleport(locs.get(j));
						j++;
						
					}
				}
				
				//if there are dead players, says who they are
				if(!deadPlayers.contentEquals("")) {
					Bukkit.broadcastMessage(deadPlayers + "are dead swapping the others.");
				}
				Bukkit.broadcastMessage(Utils.chat(plugin.getConfig().getString("swaped_message")));//standard message
		}
	
	//makes it so players can't see or move much while swapping
	private void freezePlayers(ArrayList<Player> players, int tpDelay) {
		Main.freeze = true;
		for(int i = 0;i<players.size();i++) {
			if(players.get(i) != null && !players.get(i).getGameMode().equals(GameMode.SPECTATOR)){
				players.get(i).addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, tpDelay-5, 9999));
				players.get(i).addPotionEffect(new PotionEffect(PotionEffectType.SLOW, tpDelay-5, 9999));
				players.get(i).addPotionEffect(new PotionEffect(PotionEffectType.SLOW_FALLING, tpDelay-5, 9999));
				players.get(i).addPotionEffect(new PotionEffect(PotionEffectType.SLOW_DIGGING, tpDelay-5, 9999));
				players.get(i).setNoDamageTicks(tpDelay+5);
			}
		}
	}
}

