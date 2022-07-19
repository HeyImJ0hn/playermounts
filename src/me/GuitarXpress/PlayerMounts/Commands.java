package me.GuitarXpress.PlayerMounts;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

public class Commands implements CommandExecutor {

	private Main plugin;

	public Commands(Main plugin) {
		this.plugin = plugin;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

		if (!(sender instanceof Player))
			return true;

		Player player = (Player) sender;

		if (player.hasPermission("plym.use")) {
			if (cmd.getName().equalsIgnoreCase("mounts") || cmd.getName().equalsIgnoreCase("playermounts")
					|| cmd.getName().equalsIgnoreCase("plym")) {
				if (args.length == 0) {
					player.sendMessage(prefix() + "§6Useful Commands: ");
					player.sendMessage("§e/mounts toggle §7- §eToggles mounts On/Off");
					player.sendMessage("§e/mounts eject §7- §eEjects current passenger");
				} else if (args.length == 1) {
					if (args[0].equalsIgnoreCase("eject")) {
						if (player.getPassengers().size() > 0) {
							Entity passenger = player.getPassengers().get(0);
							player.removePassenger(player.getPassengers().get(0));
							Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, () -> {
								passenger.setVelocity(player.getLocation().getDirection().multiply(2).setY(1));
							}, 1);
							Player pPassenger = (Player) passenger;
							pPassenger.sendMessage(prefix() + "§cYou have been ejected!");
							player.sendMessage(prefix() + "§eEjected player!");
						} else {
							player.sendMessage(prefix() + "§cNo one is mounting you!");
						}

					} else if (args[0].equalsIgnoreCase("toggle")) {
						if (plugin.isOn.containsKey(player.getUniqueId().toString())) {
							boolean isOn = plugin.isOn.get(player.getUniqueId().toString());
							if (isOn) {
								plugin.isOn.put(player.getUniqueId().toString(), false);
								player.sendMessage(prefix() + "§cDisabled.");
							} else {
								plugin.isOn.put(player.getUniqueId().toString(), true);
								player.sendMessage(prefix() + "§aEnabled.");
							}
						}
					} else if (args[0].equalsIgnoreCase("help")) {
						player.sendMessage(prefix() + "§6Useful Commands: ");
						player.sendMessage("§e/mounts toggle §7- §eToggles mounts On/Off");
						player.sendMessage("§e/mounts eject §7- §eEjects current passenger");
					} else {
						player.sendMessage(prefix() + "§6Useful Commands: ");
						player.sendMessage("§e/mounts toggle §7- §eToggles mounts On/Off");
						player.sendMessage("§e/mounts eject §7- §eEjects current passenger");
					}
				} else if (args.length >= 2) {
					player.sendMessage(prefix() + "§cToo Many Arguments!");
				}
			}
		} else {
			player.sendMessage(prefix() + "§cYou don't have permission for that.");
		}
		return true;
	}

	public static String prefix() {
		return "§8[§bPlayerMounts§8]: ";
	}

}
