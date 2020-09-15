package me.GuitarXpress.PlayerMounts;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {

	public void loadConfig() {
		getConfig().options().copyDefaults(true);
		this.saveDefaultConfig();
		saveConfig();

		if (getConfig().get("Player.UUID") != null) {
			for (String s : (ArrayList<String>) Bukkit.getServer().getPluginManager().getPlugin("PlayerMounts")
					.getConfig().getStringList("Player.UUID")) {
				Events.UUIDs.add(s);
			}

			for (boolean b : Bukkit.getServer().getPluginManager().getPlugin("PlayerMounts").getConfig()
					.getBooleanList("Player.Perms")) {
				Events.perms.add(b);
			}
		}

	}

	@Override
	public void onEnable() {
		loadConfig();
		getServer().getPluginManager().registerEvents(new Events(this), this);
		getCommand("mounts").setExecutor(new Commands());
		getCommand("mounts").setTabCompleter(new TabComplete());
	}

	@Override
	public void onDisable() {
		getConfig().set("Player.UUID", Events.UUIDs);
		getConfig().set("Player.Perms", Events.perms);
		saveConfig();
	}

}
