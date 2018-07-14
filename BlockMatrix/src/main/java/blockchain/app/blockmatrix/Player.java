package blockchain.app.blockmatrix;

import java.util.ArrayList;
import com.google.gson.*;
import com.google.gson.reflect.TypeToken;

//only gui (front end) is public
public class Player {

    private String name;
	private ArrayList<Item> inventory = new ArrayList<Item>();
	
	
    public Player(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
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
    	
    	Gson gson = new Gson();
    	JsonElement element = gson.toJsonTree(inventory, new TypeToken<ArrayList<Item>>() {}.getType());
    	JsonArray jsonArray = element.getAsJsonArray();
    	json.add("inventory", jsonArray);
    	
    	return json;
    }

    //get name
	//get inventory
	
}
