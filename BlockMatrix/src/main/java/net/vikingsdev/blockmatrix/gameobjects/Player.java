package net.vikingsdev.blockmatrix.gameobjects;

import java.lang.reflect.Type;
import java.util.ArrayList;
import com.google.gson.*;
import com.google.gson.reflect.TypeToken;

//only gui (front end) is public
public class Player {

    private String name;
    private Long id;
	private ArrayList<Item> inventory = new ArrayList<Item>();
	
	
    public Player(String name) {
        this.name = name;
        id = (long) (Math.random() * Long.MAX_VALUE);
    }
    
    public Player(String name, Long id) {
        this.name = name;
        this.id = id;
    }
    
    public Player(String name, Long id, ArrayList<Item> inventory) {
        this.name = name;
        this.id = id;
        this.inventory = inventory;
    }

    public String getName() {
        return name;
    }
    
    public Long getId() {
        return id;
    }

    public ArrayList<Item> getInventory() {
        return inventory;
    }
    
    public String toJsonString() {
    	return toJson().toString();
    }
    
    public JsonObject toJson() {
    	JsonObject json = new JsonObject();
    	
    	json.addProperty("name", name);
    	json.addProperty("id", id);
    	
    	Gson gson = new Gson();
    	JsonElement element = gson.toJsonTree(inventory, new TypeToken<ArrayList<Item>>() {}.getType());
    	JsonArray jsonArray = element.getAsJsonArray();
    	json.add("inventory", jsonArray);
    	
    	return json;
    }
    
    public static Player toPlayer(String json) {
    	JsonParser parser = new JsonParser();
    	JsonObject playerInfo = parser.parse(json).getAsJsonObject();

    	Type listType = new TypeToken<ArrayList<Item>>() {}.getType();
    	ArrayList<Item> inventory = new Gson().fromJson(playerInfo.get("inventory"), listType);
        
    	Player p = new Player(playerInfo.get("name").getAsString(),playerInfo.get("id").getAsLong(),inventory);
    	return p;
    }
    
    //get name
	//get inventory
	
}
