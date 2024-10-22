package threePplDeathSwap.commands.freezeListener;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityAirChangeEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import threePplDeathSwap.Main;

public class freezeListener implements Listener{
	
	private static Main plugin;
	
	public freezeListener (Main plugin) {
		this.plugin = plugin;
		Bukkit.getPluginManager().registerEvents(this,plugin);
	}
	
	//blocks players moving while swapping
	@EventHandler(priority = EventPriority.HIGHEST)
	public void onPlayerMove(PlayerMoveEvent event) {
		if(Main.freeze) {
			Player player = event.getPlayer();
			Location location = player.getLocation();
			player.teleport(location);				
		}
	}
	
	//blocks damage events to player while swapping
	@EventHandler
	public void onEntityDamageEvent(EntityDamageEvent e) {
		Entity E = e.getEntity();
		if(E instanceof Player && Main.freeze) {
			//Player player = (Player)E;
			e.setCancelled(true);
		}
	}
	
	//blocks air level changes when swapping
	@EventHandler
	public void onEntityAirChangeEvent(EntityAirChangeEvent e) {
		Entity E = e.getEntity();
		if(E instanceof Player && Main.freeze) {
			//Player player = (Player)E;
			e.setCancelled(true);
		}
	}
}
