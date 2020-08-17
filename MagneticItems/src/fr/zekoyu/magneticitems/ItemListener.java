package fr.zekoyu.magneticitems;

import org.bukkit.entity.Item;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ItemSpawnEvent;

public class ItemListener implements Listener {
	
	private MagneticItems main;
	
	public ItemListener(MagneticItems main) {
		this.main = main;
	}
	
	@EventHandler
	public void onItemSpawn(ItemSpawnEvent e) {
		Item item = e.getEntity();
		main.addItemToList(item);
	}	

}
