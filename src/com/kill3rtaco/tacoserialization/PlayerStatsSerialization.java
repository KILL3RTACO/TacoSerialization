package com.kill3rtaco.tacoserialization;

import org.bukkit.entity.Player;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * A class to help with the serialization of player stats, like exp level and health.
 * @author KILL3RTACO
 *
 */
public class PlayerStatsSerialization {
	
	protected PlayerStatsSerialization() {}
	
	public static JSONObject serializePlayerStats(Player player){
		try {
			JSONObject root = new JSONObject();
			if(SerializationConfig.getShouldSerialize("player-stats.can-fly"))
				root.put("can-fly", player.getAllowFlight());
			if(SerializationConfig.getShouldSerialize("player-stats.display-name"))
				root.put("display-name", player.getDisplayName());
			if(SerializationConfig.getShouldSerialize("player-stats.exhaustion"))
				root.put("exhaustion", player.getExhaustion());
			if(SerializationConfig.getShouldSerialize("player-stats.exp"))
				root.put("exp", player.getExp());
			if(SerializationConfig.getShouldSerialize("player-stats.flying"))
				root.put("flying", player.isFlying());
			if(SerializationConfig.getShouldSerialize("player-stats.food"))
				root.put("food", player.getFoodLevel());
			if(SerializationConfig.getShouldSerialize("player-stats.health"))
				root.put("health", player.getHealthScale());
			if(SerializationConfig.getShouldSerialize("player-stats.level"))
				root.put("level", player.getLevel());
			if(SerializationConfig.getShouldSerialize("player-stats.potion-effects"))
				root.put("potion-effects", PotionEffectSerialization.serializeEffects(player.getActivePotionEffects()));
			if(SerializationConfig.getShouldSerialize("player-stats.saturation"))
				root.put("saturation", player.getSaturation());
			return root;
		} catch (JSONException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static String serializePlayerStatsAsString(Player player){
		return serializePlayerStatsAsString(player, false);
	}
	
	public static String serializePlayerStatsAsString(Player player, boolean pretty){
		return serializePlayerStatsAsString(player, pretty, 5);
	}
	
	public static String serializePlayerStatsAsString(Player player, boolean pretty, int indentFactor){
		try{
			if(pretty){
				return serializePlayerStats(player).toString(indentFactor);
			}else{
				return serializePlayerStats(player).toString();
			}
		} catch(JSONException e){
			e.printStackTrace();
			return null;
		}
	}
	
	public static void applyPlayerStats(Player player, String stats){
		try {
			applyPlayerStats(player, new JSONObject(stats));
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
	
	public static void applyPlayerStats(Player player, JSONObject stats){
		try {
			if(stats.has("can-fly"))
				player.setAllowFlight(stats.getBoolean("can-fly"));
			if(stats.has("display-name"))
				player.setDisplayName(stats.getString("display-name"));
			if(stats.has("exhaustion"))
				player.setExhaustion((float) stats.getDouble("exhaustion"));
			if(stats.has("exp"))
				player.setExp((float) stats.getDouble("exp"));
			if(stats.has("flying"))
				player.setFlying(stats.getBoolean("flying"));
			if(stats.has("food"))
				player.setFoodLevel(stats.getInt("food"));
			if(stats.has("health"))
				player.setHealth(stats.getDouble("health"));
			if(stats.has("level"))
				player.setLevel(stats.getInt("level"));
			if(stats.has("potion-effects"))
				PotionEffectSerialization.setPotionEffects(stats.getString("potion-effects"), player);
			if(stats.has("saturation"))
				player.setSaturation((float) stats.getDouble("saturation"));
		} catch (JSONException e){
			e.printStackTrace();
		}
	}

}
