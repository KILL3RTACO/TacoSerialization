package com.kill3rtaco.tacoserialization;

import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Horse;

import org.json.JSONException;
import org.json.JSONObject;

public class HorseSerialization {

	protected HorseSerialization(){
	}
	
	public static JSONObject serializeHorse(Horse horse){
		try {
			JSONObject root = new JSONObject();
			if(SerializationConfig.getShouldSerialize("horse.age"))
				root.put("age", horse.getAge());
			if(SerializationConfig.getShouldSerialize("horse.color"))
				root.put("color", horse.getColor().name());
			if(SerializationConfig.getShouldSerialize("horse.health"))
				root.put("health", horse.getHealth());
			if(SerializationConfig.getShouldSerialize("horse.inventory"))
				root.put("inventory", InventorySerialization.serializeInventory(horse.getInventory()));
			if(SerializationConfig.getShouldSerialize("horse.jump-strength"))
				root.put("jump-strength", horse.getJumpStrength());
			if(SerializationConfig.getShouldSerialize("horse.name"))
				root.put("name", horse.getCustomName());
			if(SerializationConfig.getShouldSerialize("horse.potion-effects"))
				root.put("potion-effects", PotionEffectSerialization.serializeEffects(horse.getActivePotionEffects()));
			if(SerializationConfig.getShouldSerialize("horse.style"))
				root.put("style", horse.getStyle());
			if(SerializationConfig.getShouldSerialize("horse.variant"))
				root.put("variant", horse.getVariant());
			return root;
		} catch (JSONException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static String serializeHorseAsString(Horse horse){
		return serializeHorseAsString(horse, false);
	}
	
	public static String serializeHorseAsString(Horse horse, boolean pretty){
		return serializeHorseAsString(horse, pretty, 5);
	}
	
	public static String serializeHorseAsString(Horse horse, boolean pretty, int indentFactor){
		try {
			if(pretty){
				return serializeHorse(horse).toString(indentFactor);
			}else{
				return serializeHorse(horse).toString();
			}
		} catch (JSONException e){
			e.printStackTrace();
			return null;
		}
	}
	
	public static void spawnHorse(Location location, String stats){
		try {
			spawnHorse(location, new JSONObject(stats));
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
	
	public static void spawnHorse(Location location, JSONObject stats){
		Horse horse = (Horse) location.getWorld().spawnEntity(location, EntityType.HORSE);
		try{
			if(stats.has("age"))
				horse.setAge(stats.getInt("age"));
			if(stats.has("color"))
				horse.setColor(Horse.Color.valueOf(stats.getString("color")));
			if(stats.has("health"))
				horse.setHealth(stats.getDouble("health"));
			if(stats.has("jump-strength"))
				horse.setJumpStrength(stats.getDouble("jump-strength"));
			if(stats.has("name"))
				horse.setCustomName(stats.getString("name"));
			if(stats.has("style"))
				horse.setStyle(Horse.Style.valueOf(stats.getString("style")));
			if(stats.has("inventory"))
				InventorySerialization.setInventory(horse, stats.getJSONArray("inventory"));
			if(stats.has("potion-effects"))
				PotionEffectSerialization.addPotionEffects(stats.getString("potion-effects"), horse);
			if(stats.has("variant"))
				horse.setVariant(Horse.Variant.valueOf(stats.getString("variant")));
		} catch(JSONException e){
			e.printStackTrace();
		}
	}

}
