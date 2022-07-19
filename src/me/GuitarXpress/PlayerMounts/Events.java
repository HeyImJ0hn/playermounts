package me.GuitarXpress.PlayerMounts;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.EquipmentSlot;

public class Events implements Listener {
	private Main plugin;

	public Events(Main plugin) {
		this.plugin = plugin;
	}

	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event) {
		Player player = (Player) event.getPlayer();

		if (!plugin.isOn.containsKey(player.getUniqueId().toString()))
			plugin.isOn.put(player.getUniqueId().toString(), true);
	}

	@EventHandler
	public void onRightClick(PlayerInteractEntityEvent event) {
		Player player = (Player) event.getPlayer();

		if (!event.getHand().equals(EquipmentSlot.HAND))
			return;

		if (!(event.getRightClicked() instanceof Player))
			return;

		Player clicked = (Player) event.getRightClicked();

		if (player.hasPermission("plym.use")) {
			if (plugin.isOn.containsKey(player.getUniqueId().toString())
					&& plugin.isOn.get(player.getUniqueId().toString())) {
				if (plugin.isOn.containsKey(clicked.getUniqueId().toString())
						&& plugin.isOn.get(player.getUniqueId().toString())) {
					if (clicked.getPassengers().size() == 0) {
						if (player.getPassengers().size() > 0)
							return;
						clicked.addPassenger(player);
						player.sendMessage(Commands.prefix() + "§eYou mounted " + clicked.getName() + ".");
						clicked.sendMessage(Commands.prefix() + "§e" + player.getName()
								+ " mounted you. \nEject them with §6/mounts eject§e.");
						return;
					}
				} else {
					player.sendMessage(Commands.prefix() + "§cThat player does not have Mounts enabled.");
				}
			} else {
				player.sendMessage(Commands.prefix() + "§cYou don't have Mounts enabled.");
			}
		}
	}

}
