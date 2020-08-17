package fr.zekoyu.magneticitems.commands;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.util.StringUtil;

public class MagneticItemsTabCompleter implements TabCompleter {
	
	private final List<String> COMPLETIONS = Arrays.asList("reload", "set");
	private final List<String> SET_COMPLETIONS = Arrays.asList("strength", "reverse", "radius", "acceleration", "minimum_speed", "smoothness", "prefix_type");
	private final List<String> SMOOTHNESS_COMPLETIONS = Arrays.asList("water", "ice", "other");
	
	@Override
	public List<String> onTabComplete(CommandSender sender, Command cmd, String label, String[] args) {
		
		if(!sender.hasPermission("magneticitems.manage")) return null;
		switch(args.length) {
			case 1:
				return StringUtil.copyPartialMatches(args[0], COMPLETIONS , new ArrayList<>());
			case 2:
				switch(args[0].toLowerCase()) {
					case "reload":
						return null;
					case "set":
						return StringUtil.copyPartialMatches(args[1], SET_COMPLETIONS , new ArrayList<>());
				}
			case 3:
				if(args[1].toLowerCase().equals("smoothness")) {
					return StringUtil.copyPartialMatches(args[2], SMOOTHNESS_COMPLETIONS , new ArrayList<>());
				}
		}

		return null;
	}

}
