package fr.zekoyu.magneticitems;

import org.bukkit.scheduler.BukkitRunnable;

public abstract class ItemsTickUpdate extends BukkitRunnable {
	
	public abstract void run();

	public abstract void initValues();

}
