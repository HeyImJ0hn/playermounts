package me.GuitarXpress.PlayerMounts;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

public class Commands implements CommandExecutor {

	int n = 0;
	private Entity passenger;

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

		if (!(sender instanceof Player)) {
			System.out.println("Sorry! Only players can use this command.");
			return true;
		}

		Player player = (Player) sender;

		if (player.hasPermission("plym.use")) {
			if (cmd.getName().equalsIgnoreCase("mounts") || cmd.getName().equalsIgnoreCase("playermounts")
					|| cmd.getName().equalsIgnoreCase("plym")) {
				if (args.length == 0) {
					player.sendMessage(prefix() + "§6Useful Commands: ");
					player.sendMessage("§e/mounts toggle §7- §eToggles mounts On/Off");
					player.sendMessage("§e/mounts eject §7- §eEjects current passenger");
					return true;
				}
				if (args.length == 1) {
					if (args[0].equalsIgnoreCase("eject")) {
						if (player.getPassengers().size() > 0) {
							passenger = player.getPassengers().get(0);
							player.removePassenger(player.getPassengers().get(0));
							Bukkit.getScheduler().scheduleSyncDelayedTask(
									Bukkit.getServer().getPluginManager().getPlugin("PlayerMounts"), () -> {
										passenger.setVelocity(player.getLocation().getDirection().multiply(2).setY(1));
									}, 1);

							Player pPassenger = (Player) passenger;
							pPassenger.sendMessage(prefix() + "§cYou have been ejected!");
							player.sendMessage(prefix() + "§eEjected player!");
							return true;
						} else {
							player.sendMessage(prefix() + "§cNo one is mounting you!");
							return true;
						}

					} else if (args[0].equalsIgnoreCase("toggle")) {
						for (int i = 0; i < Events.UUIDs.size(); i++) {
							if (Events.UUIDs.get(i).equals(player.getUniqueId().toString())) {
								n = i;
								if (Events.perms.get(n)) {
									Events.perms.set(n, false);
									player.sendMessage(prefix() + "§cDisabled.");
									return true;
								} else {
									Events.perms.set(n, true);
									player.sendMessage(prefix() + "§eEnabled.");
									return true;
								}
							}
						}
					} else if (args[0].equalsIgnoreCase("help")) {
						player.sendMessage(prefix() + "§6Useful Commands: ");
						player.sendMessage("§e/mounts toggle §7- §eToggles mounts On/Off");
						player.sendMessage("§e/mounts eject §7- §eEjects current passenger");
						return true;
					} else {
						player.sendMessage(prefix() + "§6Useful Commands: ");
						player.sendMessage("§e/mounts toggle §7- §eToggles mounts On/Off");
						player.sendMessage("§e/mounts eject §7- §eEjects current passenger");
						return true;
					}
				}
				if (args.length >= 2) {
					player.sendMessage(prefix() + "§cToo Many Arguments!");
					return true;
				}
			}
		} else {
			player.sendMessage(prefix() + "§cYou don't have permission for that.");
			return true;
		}
		return false;
	}

	public static String prefix() {
		return "§8[§bPlayerMounts§8]: ";
	}

}
