package fr.zekoyu.magneticitems;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Item;
import org.bukkit.plugin.java.JavaPlugin;

import com.tchrisofferson.configupdater.ConfigUpdater;

import fr.zekoyu.magneticsitems.commands.MagneticItemsCommand;
import fr.zekoyu.magneticsitems.commands.MagneticItemsTabCompleter;

public class MagneticItems extends JavaPlugin{
	
	private ArrayList<Item> itemList = new ArrayList<>();
	private String consolePrefix = "[MagneticItems]";
	private String commandPrefix;
	private ItemsTickUpdate itemsTickUpdate;
	
	@Override
	public void onEnable() {
		
		System.out.println(consolePrefix + " Loading config");
		
		this.saveDefaultConfig();
		switch(getConfig().getString("aesthetic.prefix_type").toLowerCase()) {
		case "short":
			this.setShortCommandPrefix();
			break;
		case "long":
			this.setLongCommandPrefix();
			break;
		default:
			throw new IllegalArgumentException("prefix parameter (config.yml) should be either long or short");
		}

		getServer().getPluginManager().registerEvents(new ItemListener(this), this);
		
		getCommand("magneticitems").setExecutor(new MagneticItemsCommand(this));
		getCommand("magneticitems").setTabCompleter(new MagneticItemsTabCompleter());

		System.out.println(consolePrefix + " Loading items");
		
		for (World world : Bukkit.getWorlds()) {
			for(Item item : world.getEntitiesByClass(Item.class)) {
				addItemToList(item);
			}
		}
		
		System.out.println(consolePrefix + " Registering events");
		
		itemsTickUpdate = new ItemsTickUpdate(this);
		
		System.out.println(consolePrefix + " Started succesfully");
		
	}
	
	@Override
	public void onDisable() {
		
	}
	
	public void updateConfig() {
		File configFile = new File(getDataFolder(), "config.yml");
		
		try {
			ConfigUpdater.update(this, "config.yml", configFile, Arrays.asList("none"));
		} catch (IOException e) {
			System.out.println(consolePrefix + " Error while loading config file:");
			e.printStackTrace();
		}
		reloadConfig();
		
		switch(getConfig().getString("aesthetic.prefix_type").toLowerCase()) {
			case "short":
				this.setShortCommandPrefix();
				break;
			case "long":
				this.setLongCommandPrefix();
				break;
			default:
				throw new IllegalArgumentException("prefix parameter (config.yml) should be either long or short");
		}
		
		updateItemsTickUpdate();
	}
	
	public void setShortCommandPrefix() {
		this.commandPrefix = "§6[§eMI§6]§a";
	}
	
	public void setLongCommandPrefix() {
		this.commandPrefix = "§6[§eMagneticItems§6]§a";
	}
	
	public String getCommandPrefix() {
		return this.commandPrefix;
	}
	
	
	public void updateItemsTickUpdate() {
		itemsTickUpdate.initValues();
	}
	
	public String getConsolePrefix() {
		return this.consolePrefix;
	}
	
	
	public void addItemToList(Item item) {
		itemList.add(item);
	}
	
	public void removeItemFromList(Item item) {
		itemList.remove(item);
	}
	
	public ArrayList<Item> getItemList() {
		return itemList;
	}

}
