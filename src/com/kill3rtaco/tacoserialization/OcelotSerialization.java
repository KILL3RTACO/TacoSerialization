package com.kill3rtaco.tacoserialization;

import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Ocelot;

import org.json.JSONException;
import org.json.JSONObject;

public class OcelotSerialization {

	protected OcelotSerialization() {}

	public static JSONObject serializeOcelot(Ocelot ocelot){
		try {
			JSONObject root = new JSONObject();
			if(SerializationConfig.getShouldSerialize("ocelot.age"))
				root.put("age", ocelot.getAge());
			if(SerializationConfig.getShouldSerialize("ocelot.health"))
				root.put("health", ocelot.getHealth());
			if(SerializationConfig.getShouldSerialize("ocelot.name"))
				root.put("name", ocelot.getCustomName());
			if(SerializationConfig.getShouldSerialize("ocelot.potion-effects"))
				root.put("potion-effects", PotionEffectSerialization.serializeEffects(ocelot.getActivePotionEffects()));
			if(SerializationConfig.getShouldSerialize("ocelot.type"))
				root.put("type", ocelot.getCatType().name());
			return root;
		} catch (JSONException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static String serializeOcelotAsString(Ocelot ocelot){
		return serializeOcelotAsString(ocelot, false);
	}
	
	public static String serializeOcelotAsString(Ocelot ocelot, boolean pretty){
		return serializeOcelotAsString(ocelot, pretty, 5);
	}
	
	public static String serializeOcelotAsString(Ocelot ocelot, boolean pretty, int indentFactor){
		try {
			if(pretty){
				return serializeOcelot(ocelot).toString(indentFactor);
			}else{
				return serializeOcelot(ocelot).toString();
			}
		} catch (JSONException e){
			e.printStackTrace();
			return null;
		}
	}
	
	public static void spawnOcelot(Location location, String stats){
		try {
			spawnOcelot(location, new JSONObject(stats));
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
	
	public static void spawnOcelot(Location location, JSONObject stats){
		Ocelot ocelot = (Ocelot) location.getWorld().spawnEntity(location, EntityType.OCELOT);
		try{
			if(stats.has("age"))
				ocelot.setAge(stats.getInt("age"));
			if(stats.has("health"))
				ocelot.setHealth(stats.getDouble("health"));
			if(stats.has("name"))
				ocelot.setCustomName(stats.getString("name"));
			if(stats.has("potion-effects"))
				PotionEffectSerialization.addPotionEffects(stats.getString("potion-effects"), ocelot);
			if(stats.has("type"))
				ocelot.setCatType(Ocelot.Type.valueOf(stats.getString("type")));
		} catch(JSONException e){
			e.printStackTrace();
		}
	}
	
}
