package fr.zekoyu.magneticitems.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import fr.zekoyu.magneticitems.MagneticItems;
import fr.zekoyu.magneticitems.constants.CommandMessages;
import fr.zekoyu.magneticitems.constants.ConfigPaths;
import fr.zekoyu.magneticitems.constants.Permissions;


public class MagneticItemsCommand implements CommandExecutor {

	private MagneticItems main;
	
	public MagneticItemsCommand(MagneticItems main) {
		this.main = main;
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		
		int argsLength = args.length;
		
		if(!sender.hasPermission(Permissions.MANAGE_PERMISSION)) return true;
		
		if(argsLength == 0) {
			sendWrongSyntaxMsg(sender, CommandMessages.DEFAULT_SYNTAX);
			return true;
		}
		
		switch(args[0].toLowerCase()) {
		
			case "reload":
				main.updateConfig();
				sender.sendMessage(main.getCommandPrefix() + " Configuration succesfully reloaded !");
				break;
				
			case "set":
				if(argsLength < 2) sendWrongSyntaxMsg(sender, CommandMessages.SET_SYNTAX);		
				
				switch(args[1]) {
					case "strength":
						if(argsLength < 3) {
							sendWrongSyntaxMsg(sender, CommandMessages.STRENGTH_SYNTAX);
						} else {
							String arg = args[2];
							if(!isValidDouble(arg)) {
								sendWrongSyntaxMsg(sender, CommandMessages.STRENGTH_SYNTAX);
								return true;
							}
							setConfigValue(ConfigPaths.STRENGTH_PATH, Double.parseDouble(arg));
							sender.sendMessage(main.getCommandPrefix() + " Magnetism strength has been changed to §e" + arg + "§a (default 1)");
						}
						break;
						
					case "reverse":
						if(argsLength < 3) {
							sendWrongSyntaxMsg(sender, CommandMessages.REVERSE_SYNTAX);
						} else {
							String arg = args[2];
							if(!isValidBoolean(arg)) {
								sendWrongSyntaxMsg(sender, CommandMessages.REVERSE_SYNTAX);
								return true;
							}
							setConfigValue(ConfigPaths.REVERSE_PATH, Boolean.parseBoolean(arg));
							if(Boolean.parseBoolean(arg) == true) {
								sender.sendMessage(main.getCommandPrefix() + " Items will now be repulsed by players");
							} else {
								sender.sendMessage(main.getCommandPrefix() + " Items will now be attracted by players");
							}
						}
						break;
						
					case "radius":
						if(argsLength < 3) {
							sendWrongSyntaxMsg(sender, CommandMessages.RADIUS_SYNTAX);
						} else {
							String arg = args[2];
							if(!isValidDouble(arg)) {
								sendWrongSyntaxMsg(sender, CommandMessages.RADIUS_SYNTAX);
								return true;
							}
							setConfigValue(ConfigPaths.RADIUS_PATH, Double.parseDouble(arg));
							sender.sendMessage(main.getCommandPrefix() + " Magnetism radius has been changed to §e" + arg + "§a (default 7)");
						}
						break;
						
					case "acceleration":
						if(argsLength < 3) {
							sendWrongSyntaxMsg(sender, CommandMessages.ACCELERATION_SYNTAX);
						} else {
							String arg = args[2];
							if(!isValidDouble(arg)) {
								sendWrongSyntaxMsg(sender, CommandMessages.ACCELERATION_SYNTAX);
								return true;
							}
							setConfigValue(ConfigPaths.ACCELERATION_PATH, Double.parseDouble(arg));
							sender.sendMessage(main.getCommandPrefix() + " Magnetism acceleration has been changed to §e" + arg + "§a (default 25)");
						}
						break;
						
					case "minimum_speed":
						if(argsLength < 3) {
							sendWrongSyntaxMsg(sender, CommandMessages.MINIMUM_SPEED_SYNTAX);
						} else {
							String arg = args[2];
							if(!isValidDouble(arg)) {
								sendWrongSyntaxMsg(sender, CommandMessages.MINIMUM_SPEED_SYNTAX);
								return true;
							}
							setConfigValue(ConfigPaths.MINIMUM_SPEED_PATH, Double.parseDouble(arg));
							sender.sendMessage(main.getCommandPrefix() + " Magnetism minimum speed has been changed to §e" + arg + "§a (default 2)");
						}
						break;
						
					case "prefix_type":
						if(argsLength < 3) {
							sendWrongSyntaxMsg(sender, CommandMessages.PREFIX_TYPE_SYNTAX);
						} else {
							String arg = args[2];
							switch(arg.toLowerCase()) {
								case "long":
									setConfigValue(ConfigPaths.PREFIX_TYPE_PATH, "long");
									sender.sendMessage(main.getCommandPrefix() + " < Prefix type has been changed to long");
									break;
								case "short":
									setConfigValue(ConfigPaths.PREFIX_TYPE_PATH, "short");
									sender.sendMessage(main.getCommandPrefix() + " < Prefix type has been changed to short");
									break;
								default:
									sendWrongSyntaxMsg(sender, CommandMessages.PREFIX_TYPE_SYNTAX);
									break;
							}
						}
						break;
						
					case "smoothness":
						if(argsLength < 4) {
							sendWrongSyntaxMsg(sender, CommandMessages.SMOOTHNESS_SYNTAX);
						} else {
							String number = args[3];
							switch(args[2].toLowerCase()) {
							
								case "ice":
									if(!isValidDouble(number)) {
										sendWrongSyntaxMsg(sender, CommandMessages.SMOOTHNESS_SYNTAX);
										return true;
									} 
									setConfigValue(ConfigPaths.ICE_SMOOTHNESS_PATH, Double.parseDouble(number));
									sender.sendMessage(main.getCommandPrefix() + " Ice smoothness has been changed to §e" + number + "§a (default 2)");
									break;
									
								case "water":
									if(!isValidDouble(number)) {
										sendWrongSyntaxMsg(sender, CommandMessages.SMOOTHNESS_SYNTAX);
										return true;
									}
									setConfigValue(ConfigPaths.WATER_SMOOTHNESS_PATH, Double.parseDouble(number));
									sender.sendMessage(main.getCommandPrefix() + " Water smoothness has been changed to §e" + number + "§a (default 0.35)");
									break;
									
								case "other":
									if(!isValidDouble(number)) {
										sendWrongSyntaxMsg(sender, CommandMessages.SMOOTHNESS_SYNTAX);
										return true;
									} 
									setConfigValue(ConfigPaths.OTHER_SMOOTHNESS_PATH, Double.parseDouble(number));
									sender.sendMessage(main.getCommandPrefix() + " Other blocks smoothness has been changed to §e" + number + "§a (default 0.2)");
									break;
									
								default:
									sendWrongSyntaxMsg(sender, CommandMessages.SMOOTHNESS_SYNTAX);
									break;
							
							}
						}
						break;
						
					default:
						sendWrongSyntaxMsg(sender, CommandMessages.SET_SYNTAX);
						break;
				}	
				break;	// case "set"
				
			default:
				sendWrongSyntaxMsg(sender, CommandMessages.DEFAULT_SYNTAX);
				break;
		}
		
		return true;
	}
	
	private void setConfigValue(String path, Object value) {
		main.getConfig().set(path, value);
		main.saveConfig();
		main.updateConfig();
	}
	
	private void sendWrongSyntaxMsg(CommandSender sender, String message) {
		sender.sendMessage(main.getCommandPrefix() + " " + message);
	}

	
	private boolean isValidBoolean(String string) {
		if(string.equalsIgnoreCase("true") || string.equalsIgnoreCase("false")) {
			return true;
		} else {
			return false;
		}
	}
	
	
	private boolean isValidDouble(String string) {
		try {
			Double.parseDouble(string);
			return true;
		} catch(NumberFormatException e) {
			return false;
		}
	}

}
