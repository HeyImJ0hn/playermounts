package me.GuitarXpress.PlayerMounts;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {

	public Map<String, Boolean> isOn = new HashMap<>();

	public void loadConfig() {
		getConfig().options().copyDefaults(true);
		saveDefaultConfig();

		ConfigurationSection cfg = this.getConfig().getConfigurationSection("Player");
		if (cfg != null)
			for (String key : cfg.getKeys(false)) {
				isOn.put(key, getConfig().getBoolean("Player." + key));
			}
	}

	@Override
	public void onEnable() {
		loadConfig();
		getServer().getPluginManager().registerEvents(new Events(this), this);
		getCommand("mounts").setExecutor(new Commands(this));
		getCommand("mounts").setTabCompleter(new TabComplete());
		getServer().getConsoleSender().sendMessage(Commands.prefix() + "§aEnabled.");
	}

	@Override
	public void onDisable() {
		saveData();
		getServer().getConsoleSender().sendMessage(Commands.prefix() + "§cDisabled.");
	}

	public void saveData() {
		for (Map.Entry<String, Boolean> entry : isOn.entrySet())
			getConfig().set("Player." + entry.getKey(), entry.getValue());
		saveConfig();
	}

}
