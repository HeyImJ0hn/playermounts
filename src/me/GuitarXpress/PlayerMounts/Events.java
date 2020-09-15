package me.GuitarXpress.PlayerMounts;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.entity.IronGolem;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.EquipmentSlot;

public class Events implements Listener {
	Main plugin;

	static List<String> UUIDs = new ArrayList<String>();
	static List<Boolean> perms = new ArrayList<Boolean>();

	public Events(Main plugin) {
		this.plugin = plugin;
	}

	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event) {
		Player player = (Player) event.getPlayer();

		if (!UUIDs.contains(player.getUniqueId().toString())) {
			UUIDs.add(player.getUniqueId().toString());
			perms.add(true);
		}

	}

	@EventHandler
	public void onRightClick(PlayerInteractEntityEvent event) {
		Player player = (Player) event.getPlayer();
		if (event.getHand().equals(EquipmentSlot.HAND)) {
			if (event.getRightClicked() instanceof Player) {
				if (player.hasPermission("plym.use")) {
					for (int i = 0; i < UUIDs.size(); i++) {
						if (UUIDs.get(i).equals(player.getUniqueId().toString())) {
							if (perms.get(i)) {
								for (int j = 0; j < UUIDs.size(); j++) {
									if (UUIDs.get(j).equals(event.getRightClicked().getUniqueId().toString())) {
										if (perms.get(j)) {
											if (event.getRightClicked().getPassengers().size() == 0) {
												if (player.getPassengers().size() > 0) {
													return;
												}
												event.getRightClicked().addPassenger(player);
												player.sendMessage(Commands.prefix() + "§eYou mounted "
														+ event.getRightClicked().getName().toString());
												event.getRightClicked()
														.sendMessage(Commands.prefix() + "§e"
																+ player.getName().toString()
																+ " mounted you. \nEject them with §6/mounts eject.");
												return;
											}
										} else {
											player.sendMessage(
													Commands.prefix() + "§cThat player does not have Mounts enabled.");
											return;
										}
									}
								}
							} else {
								player.sendMessage(Commands.prefix() + "§cYou don't have Mounts enabled.");
								return;
							}
						}
					}
				}
			}
			if (event.getRightClicked() instanceof IronGolem) {
				if (event.getRightClicked().getPassengers().size() == 0) {
					player.sendMessage(Commands.prefix() + "§eYou mounted an iron golem...? Why?");
					event.getRightClicked().addPassenger(player);
					return;
				}
			}
		}
	}

}
