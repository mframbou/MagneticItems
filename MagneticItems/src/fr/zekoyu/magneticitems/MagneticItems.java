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

import fr.zekoyu.magneticitems.commands.MagneticItemsCommand;
import fr.zekoyu.magneticitems.commands.MagneticItemsTabCompleter;

public class MagneticItems extends JavaPlugin{
	
	private ArrayList<Item> itemList = new ArrayList<>();
	private String consolePrefix = "[MagneticItems]";
	private String commandPrefix;
	private ItemsTickUpdate itemsTickUpdate;
	
	@Override
	public void onEnable() {
		
		String version = Bukkit.getBukkitVersion().split("-")[0];
		
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
		
		if(version.startsWith("1.8") || version.startsWith("1.9") || version.startsWith("1.10") || version.startsWith("1.11") || version.startsWith("1.12")) {
			itemsTickUpdate = new ItemsTickUpdate_1_8(this);
		} else if(version.startsWith("1.13") || version.startsWith("1.14") || version.startsWith("1.15") || version.startsWith("1.16")) {
			itemsTickUpdate = new ItemsTickUpdate_1_13(this);
		} else {
			itemsTickUpdate = null;
			getLogger().severe(this.consolePrefix + " Your server version is not compatible with this plugin ! (1.8-1.16.x)");
		}
				
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
