package com.kill3rtaco.tacoserialization;

import org.bukkit.Color;

import org.json.JSONException;
import org.json.JSONObject;

public class ColorSerialization {

	protected ColorSerialization() {}
	
	public static JSONObject serializeColor(Color color){
		try {
			JSONObject root = new JSONObject();
			root.put("red", color.getRed());
			root.put("green", color.getGreen());
			root.put("blue", color.getBlue());
			return root;
		} catch (JSONException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static Color getColor(String color){
		try {
			return getColor(new JSONObject(color));
		} catch (JSONException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static Color getColor(JSONObject color){
			try {
				int r = 0, g = 0, b = 0;
				if(color.has("red"))
					r = color.getInt("red");
				if(color.has("green"))
					g = color.getInt("green");
				if(color.has("blue"))
					b = color.getInt("blue");
				return Color.fromRGB(r, g, b);
			} catch (JSONException e) {
				e.printStackTrace();
				return null;
			}
	}
	
	public static String serializeColorAsString(Color color){
		return serializeColorAsString(color, false);
	}
	
	public static String serializeColorAsString(Color color, boolean pretty){
		return serializeColorAsString(color, pretty, 5);
	}
	
	public static String serializeColorAsString(Color color, boolean pretty, int indentFactor){
		try {
			if(pretty){
				return serializeColor(color).toString(indentFactor);
			}else{
				return serializeColor(color).toString();
			}
		} catch (JSONException e) {
			e.printStackTrace();
			return null;
		}
	}

}
