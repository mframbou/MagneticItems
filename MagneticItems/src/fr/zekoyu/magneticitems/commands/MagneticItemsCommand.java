package fr.zekoyu.magneticitems.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import fr.zekoyu.magneticitems.MagneticItems;


public class MagneticItemsCommand implements CommandExecutor {

	private MagneticItems main;
	
	public MagneticItemsCommand(MagneticItems main) {
		this.main = main;
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		
		if(!sender.hasPermission("magneticitems.manage")) return false;
		
		if(args[0] == null) return false;
		switch(args[0].toLowerCase()) {
			case "reload":
				main.updateConfig();
				sender.sendMessage(main.getCommandPrefix() + " Configuration successfully reloaded !");
				break;
				
			case "set":
				if(args.length > 2) {
					String arg = args[2];
					switch(args[1].toLowerCase()) {
						case "strength":
							if(!isValidDouble(arg)) {
								sendInvalidDoubleMessage(args[1], sender);
							} else {
								main.getConfig().set("magnetism.strength", Double.parseDouble(arg));
								main.saveConfig();
								main.updateConfig();
								sender.sendMessage(main.getCommandPrefix() + " Magnetism strength has been set to §e" + arg + "§a (default 1)");
							}
							break;
							
						case "reverse": 
							if(!isValidBoolean(arg)) {
								sendInvalidBoolMessage(args[1], sender);
							} else {
								main.getConfig().set("magnetism.reverse", Boolean.parseBoolean(arg));
								main.saveConfig();
								main.updateConfig();
								if(Boolean.parseBoolean(arg) == true) {
									sender.sendMessage(main.getCommandPrefix() + " Items will now be repulsed from players");
								} else {
									sender.sendMessage(main.getCommandPrefix() + " Items will now be attracted by players");
								}
							}
							break;
							
						case "radius":
							if(!isValidDouble(arg)) {
								sendInvalidDoubleMessage(args[1], sender);
							} else {
								main.getConfig().set("magnetism.radius", Double.parseDouble(arg));
								main.saveConfig();
								main.updateConfig();
								sender.sendMessage(main.getCommandPrefix() + " Magnetism radius has been set to §e" + arg + "§a (default 7)");
							}
							break;
							
						case "acceleration":
							if(!isValidDouble(arg)) {
								sendInvalidDoubleMessage(args[1], sender);
							} else {
								main.getConfig().set("magnetism.acceleration", Double.parseDouble(arg));
								main.saveConfig();
								main.updateConfig();
								sender.sendMessage(main.getCommandPrefix() + " Magnetism acceleration has been set to §e" + arg + "§a (default 25)");
							}
							break;
							
						case "minimum_speed":
							if(!isValidDouble(arg)) {
								sendInvalidDoubleMessage(args[1], sender);
							} else {
								main.getConfig().set("magnetism.minimum_speed", Double.parseDouble(arg));
								main.saveConfig();
								main.updateConfig();
								sender.sendMessage(main.getCommandPrefix() + " Magnetism minimum speed has been set to §e" + arg + "§a (default 2)");
							}
							
						case "prefix_type":
							switch(arg.toLowerCase()) {
								case "long":
									main.getConfig().set("aesthetic.prefix_type", "long");
									main.saveConfig();
									main.updateConfig();
									sender.sendMessage(main.getCommandPrefix() + " < Prefix has been changed to long");
									break;
								case "short":
									main.getConfig().set("aesthetic.prefix_type", "short");
									main.saveConfig();
									main.updateConfig();
									sender.sendMessage(main.getCommandPrefix() + " < Prefix type has been changed to short");
									break;
								default:
									sender.sendMessage(main.getCommandPrefix() + " Invalid syntax: /magneticitems set prefix_type <long | short>");
									break;
							}
							break;
							
						case "smoothness":
							if(args.length > 2) {
								String numArg = "";
								if(args.length > 3) numArg = args[3];
								switch(args[2].toLowerCase()) {
									case "water":
										if(args.length > 3) {
											if(isValidDouble(numArg)) {
												main.getConfig().set("smoothness.water", Double.parseDouble(numArg));
												main.saveConfig();
												main.updateConfig();
												sender.sendMessage(main.getCommandPrefix() + " Water smoothness has been set to §e" + numArg + "§a (default 0.35)" );
												break;
											}
										}
										sender.sendMessage(main.getCommandPrefix() + " Invalid syntax: /magneticitems set smoothness water <number>");
										break;
										
									case"ice":
										if(args.length > 3) {
											if(isValidDouble(numArg)) {
												main.getConfig().set("smoothness.ice", Double.parseDouble(numArg));
												main.saveConfig();
												main.updateConfig();
												sender.sendMessage(main.getCommandPrefix() + " Ice smoothness has been set to §e" + numArg + "§a (default 2)" );
												break;
											}
										}
										sender.sendMessage(main.getCommandPrefix() + " Invalid syntax: /magneticitems set smoothness ice <number>");
										break;
										
									case"other":
										if(args.length > 3) {
											if(isValidDouble(numArg)) {
												main.getConfig().set("smoothness.other", Double.parseDouble(numArg));
												main.saveConfig();
												main.updateConfig();
												sender.sendMessage(main.getCommandPrefix() + " Other blocks smoothness has been set to §e" + numArg + "§a (default 0.2)" );
												break;
											}
										}
										sender.sendMessage(main.getCommandPrefix() + " Invalid syntax: /magneticitems set smoothness other <number>");
										break;
										
									default:
										sender.sendMessage(main.getCommandPrefix() + " Invalid syntax: /magneticitems set smoothness <water | ice | other> <number>");
										break;
								}
							} else {
								sender.sendMessage(main.getCommandPrefix() + " Invalid syntax: /magneticitems set smoothness <water | ice | other> <number>");
							}
							break;
							
						default:
							sender.sendMessage(main.getCommandPrefix() + " Invalid syntax: /magneticitems set <param> <param>");
							break;
					}
				} else {
					if(args[1].equalsIgnoreCase("smoothness")) {
						sender.sendMessage(main.getCommandPrefix() + " Invalid syntax: /magneticitems set smoothness <water | ice | other> <number>");
					} else {
						sender.sendMessage(main.getCommandPrefix() + " Invalid syntax: /magneticitems set <param> <param>");
					}
				}
				
	}
		
		return false;
	}
	
		
	public void sendInvalidBoolMessage(String cmd, CommandSender sender) {
		sender.sendMessage(main.getCommandPrefix() + " Invalid syntax: /magneticitems set " + cmd + " <true | false>");
	}
	
	public boolean isValidBoolean(String string) {
		if(string.equalsIgnoreCase("true") || string.equalsIgnoreCase("false")) {
			return true;
		} else {
			return false;
		}
	}
	
	public void sendInvalidDoubleMessage(String cmd, CommandSender sender) {
		sender.sendMessage(main.getCommandPrefix() + " Invalid syntax: /magneticitems set " + cmd + " <number>");
	}
	
	public boolean isValidDouble(String string) {
		try {
			Double.parseDouble(string);
			return true;
		} catch(NumberFormatException e) {
			return false;
		}
	}

}
