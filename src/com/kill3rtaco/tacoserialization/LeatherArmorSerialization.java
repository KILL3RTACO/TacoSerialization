package com.kill3rtaco.tacoserialization;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.LeatherArmorMeta;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * A class to help with the serialization of dyed leather armor. The Red, Green, and Blue values are saved
 * appropriately.
 * @author KILL3RTACO
 *
 */
public class LeatherArmorSerialization {

	protected LeatherArmorSerialization() {}
	
	public static JSONObject serializeArmor(LeatherArmorMeta meta){
		try {
			JSONObject root = new JSONObject();
			root.put("color", ColorSerialization.serializeColor(meta.getColor()));
			return root;
		} catch (JSONException e) {
			e.printStackTrace();
			return null;
		}
	}

	public static String serializeArmorAsString(LeatherArmorMeta meta){
		return serializeArmorAsString(meta, false);
	}

	public static String serializeArmorAsString(LeatherArmorMeta meta, boolean pretty){
		return serializeArmorAsString(meta, pretty, 5);
	}

	public static String serializeArmorAsString(LeatherArmorMeta meta, boolean pretty, int indentFactor){
		try {
			if(pretty){
				return serializeArmor(meta).toString(indentFactor);
			}else{
				return serializeArmor(meta).toString();
			}
		} catch (JSONException e){
			e.printStackTrace();
			return null;
		}
	}
	
	public static LeatherArmorMeta getLeatherArmorMeta(String json){
		try {
			return getLeatherArmorMeta(new JSONObject(json));
		} catch (JSONException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static LeatherArmorMeta getLeatherArmorMeta(JSONObject json){
		try {
			ItemStack dummyItems = new ItemStack(Material.LEATHER_HELMET, 1);
			LeatherArmorMeta meta = (LeatherArmorMeta) dummyItems.getItemMeta();
			if(json.has("color")){
				meta.setColor(ColorSerialization.getColor(json.getJSONObject("color")));
			}
			return meta;
		} catch(JSONException e){
			e.printStackTrace();
			return null;
		}
	}

}
