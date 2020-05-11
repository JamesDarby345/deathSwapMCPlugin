package threePplDeathSwap.commands.deathListener;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerMoveEvent;

import threePplDeathSwap.Main;
import threePplDeathSwap.commands.utils.Utils;

public class deathListener implements Listener{
	
	private static Main plugin;
	
	public deathListener (Main plugin) {
		this.plugin = plugin;
		Bukkit.getPluginManager().registerEvents(this,plugin);
	}
	
	//sets players to spectator on death and checks if game is over
	@EventHandler(priority = EventPriority.LOW)
	public void onPlayerDeath(PlayerDeathEvent e) {
		Player p = e.getEntity();
		
		//if a player killed this player, maybe this is cheating
		if(e.getEntity().getKiller() instanceof Player) {
			Player killer = e.getEntity().getKiller();
			Bukkit.broadcastMessage(Utils.chat(plugin.getConfig().getString("cheat_message")
					.replace("<player>", p.getName()).replace("<player2>", killer.getName())));
		}
		p.setGameMode(GameMode.SPECTATOR);
		Player winner = null;
		int count = 0;
		
		//gets online player count and sets potential winner
		for(Player pl : plugin.getServer().getOnlinePlayers()) {
			if(!pl.getGameMode().equals(GameMode.SURVIVAL)) {
				count++;
			}else {
				winner = pl;
			}
		}
		
		//if there is only 1 player still in survival, they won, end the game
		if(count == plugin.getServer().getOnlinePlayers().size()-1) {
			Bukkit.broadcastMessage(Utils.chat("&a" + winner.getName() + " won! good job"));
			Main.freeze = false;
			Bukkit.getScheduler().cancelTasks(plugin);
		}
		
	}

}
