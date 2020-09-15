package me.GuitarXpress.PlayerMounts;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

public class TabComplete implements TabCompleter {
	
	List<String> arguments = new ArrayList<String>();
	
	public List<String> onTabComplete(CommandSender sender, Command cmd, String label, String[] args){
		if (arguments.isEmpty()) {
			Player player = (Player) sender;
			if (player.hasPermission("plym.use")) {
				arguments.add("eject");
				arguments.add("toggle");
				arguments.add("help");
			}
		}
		
		List<String> result = new ArrayList<String>();
		if (args.length == 1) {
			for(String a : arguments) {
				if (a.toLowerCase().startsWith(args[0].toLowerCase())) {
					result.add(a);
				}
			}
			
		}
		return result;
	}
}
