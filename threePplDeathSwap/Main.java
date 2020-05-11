package threePplDeathSwap;

import org.bukkit.plugin.java.JavaPlugin;

import threePplDeathSwap.commands.deathSwapCommand;
import threePplDeathSwap.commands.fastSwap;
import threePplDeathSwap.commands.deathListener.deathListener;

public class Main extends JavaPlugin {
	public static boolean freeze;
	public void onEnable() {
		freeze = false;
		saveDefaultConfig();
		new deathSwapCommand(this);
		new fastSwap(this);
	}
}