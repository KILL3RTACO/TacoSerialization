package com.kill3rtaco.tacoserialization;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;

public class SerializationConfig {

	private static YamlConfiguration config;
	
	protected SerializationConfig() {}
	
	public static File getDataFolder(){
		//this serialization system is meant to be embedded into another plugin
		//therefore at least one plugin should always exist
		File pluginFolder = Bukkit.getServer().getPluginManager().getPlugins()[0].getDataFolder();
		return new File(pluginFolder.getParentFile() + "/TacoSerialization/");
	}
	
	public static File getConfigFile(){
		return new File(getDataFolder() + "/config.yml");
	}
	
	public static void reload(){
		config = YamlConfiguration.loadConfiguration(getConfigFile());
		setDefaults();
		save();
		Logger.getLogger("Minecraft").log(Level.INFO, "[TacoSerialization] Config reloaded");
	}
	
	public static void save(){
		try {
			config.save(getConfigFile());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private static void setDefaults(){
		addDefault("horse.age", true);
		addDefault("horse.color", true);
		addDefault("horse.health", true);
		addDefault("horse.health", true);
		addDefault("horse.inventory", true);
		addDefault("horse.jump-stength", true);
		addDefault("horse.name", true);
		addDefault("horse.potion-effects", true);
		addDefault("horse.style", true);
		addDefault("horse.variant", true);
		addDefault("ocelot.age", true);
		addDefault("ocelot.health", true);
		addDefault("ocelot.name", true);
		addDefault("ocelot.potion-effects", true);
		addDefault("ocelot.type", true);
		addDefault("player.ender-chest", true);
		addDefault("player.inventory", true);
		addDefault("player.stats", true);
		addDefault("player-stats.can-fly", true);
		addDefault("player-stats.display-name", true);
		addDefault("player-stats.exhaustion", true);
		addDefault("player-stats.exp", true);
		addDefault("player-stats.food", true);
		addDefault("player-stats.flying", true);
		addDefault("player-stats.health", true);
		addDefault("player-stats.level", true);
		addDefault("player-stats.potion-effects", true);
		addDefault("player-stats.saturation", true);
		addDefault("wolf.age", true);
		addDefault("wolf.collar-color", true);
		addDefault("wolf.health", true);
		addDefault("wolf.name", true);
		addDefault("wolf.potion-effects", true);
	}
	
	public static void addDefault(String path, Object value){
		if(!getConfig().contains(path))
			getConfig().set(path, value);
	}
	
	private static YamlConfiguration getConfig(){
		if(config == null){
			reload();
		}
		return config;
	}
	
	public static boolean getShouldSerialize(String path){
		return getConfig().getBoolean(path);
	}

}
