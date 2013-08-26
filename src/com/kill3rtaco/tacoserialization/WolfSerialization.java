package com.kill3rtaco.tacoserialization;

import org.bukkit.DyeColor;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Wolf;

import org.json.JSONException;
import org.json.JSONObject;

public class WolfSerialization {

	protected WolfSerialization() {}
	
	public static JSONObject serializeWolf(Wolf wolf){
		try {
			JSONObject root = new JSONObject();
			if(SerializationConfig.getShouldSerialize("wolf.age"))
				root.put("age", wolf.getAge());
			if(SerializationConfig.getShouldSerialize("wolf.health"))
				root.put("health", wolf.getHealth());
			if(SerializationConfig.getShouldSerialize("wolf.name"))
				root.put("name", wolf.getCustomName());
			if(SerializationConfig.getShouldSerialize("wolf.potion-effects"))
				root.put("potion-effects", PotionEffectSerialization.serializeEffects(wolf.getActivePotionEffects()));
			if(SerializationConfig.getShouldSerialize("wolf.collar-color"))
				root.put("collar-color", ColorSerialization.serializeColor(wolf.getCollarColor().getColor()));
			return root;
		} catch (JSONException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static String serializeWolfAsString(Wolf wolf){
		return serializeWolfAsString(wolf, false);
	}
	
	public static String serializeWolfAsString(Wolf wolf, boolean pretty){
		return serializeWolfAsString(wolf, pretty, 5);
	}
	
	public static String serializeWolfAsString(Wolf wolf, boolean pretty, int indentFactor){
		try {
			if(pretty){
				return serializeWolf(wolf).toString(indentFactor);
			}else{
				return serializeWolf(wolf).toString();
			}
		} catch (JSONException e){
			e.printStackTrace();
			return null;
		}
	}
	
	public static void spawnWolf(Location location, String stats){
		try {
			spawnWolf(location, new JSONObject(stats));
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
	
	public static void spawnWolf(Location location, JSONObject stats){
		Wolf wolf = (Wolf) location.getWorld().spawnEntity(location, EntityType.WOLF);
		try{
			if(stats.has("age"))
				wolf.setAge(stats.getInt("age"));
			if(stats.has("collar-color"))
				wolf.setCollarColor(DyeColor.getByColor(ColorSerialization.getColor(stats.getString("collar-color"))));
			if(stats.has("health"))
				wolf.setHealth(stats.getDouble("health"));
			if(stats.has("name"))
				wolf.setCustomName(stats.getString("name"));
			if(stats.has("potion-effects"))
				PotionEffectSerialization.addPotionEffects(stats.getString("potion-effects"), wolf);
		} catch(JSONException e){
			e.printStackTrace();
		}
	}

}
