package net.vikingsdev.blockmatrix.gameobjects;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.InstanceCreator;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import flexjson.JSONDeserializer;
import net.sf.json.JSONSerializer;

public class Weapon extends Item{
	private ArrayList<Event> history;
	private Map<String, Integer> stats;
	
	private int kills;
	private int clicks;

	private String prefix = "", proper = "", suffix = "", fullName = "";

	AvailableEvents availableEvents = new AvailableEvents();
	
	private ArrayList<ClickTriggerable> availableClickTriggerable;
	private ArrayList<KillTriggerable> availableKillTriggerable;
	
    public Weapon(String name) {
    	super(name);
    	proper = super.name;
    	this.availableClickTriggerable = availableEvents.getAvailableClickTriggerable();
    	this.availableKillTriggerable = availableEvents.getAvailableKillTriggerable();
    	kills = 0;
        this.fullName = name;
		history = new ArrayList<Event>();
		stats = new HashMap<String, Integer>();
        stats.put("Damage",1);		//i think weapon dmg needs to start at 1 lol, cuz for this mvp the point is all ur stats r tied up in ur weapon
        stats.put("Speed",1);
    }
    
    public Weapon(String name, ArrayList<Event> history, HashMap<String,Integer> stats, int kills, int clicks, String prefix, String proper, String suffix, String fullName, ArrayList<ClickTriggerable> availableClickTriggerable, ArrayList<KillTriggerable> availableKillTriggerable) {
    	super(name);
    	this.history = history;
    	this.stats = stats;
    	this.kills = kills;
    	this.clicks = clicks;
    	this.prefix = prefix;
    	this.proper = proper;
    	this.suffix = suffix;
    	this.fullName = fullName;
    	this.availableClickTriggerable = availableClickTriggerable;
    	this.availableKillTriggerable =availableKillTriggerable;
    }
    
    public void addKills(int addKill) {
    	this.kills += addKill;
    }
    
    public ArrayList<ClickTriggerable> getAvailableClickTriggerable(){
    	return availableClickTriggerable;
    }
    
    public ArrayList<KillTriggerable> getAvailableKillTriggerable(){
    	return availableKillTriggerable;
    }
    
    public void addClick() {
    	this.clicks ++;
    }

    public String getName() {
        return fullName;
    }
    
	public int getKills() {
		return kills;
	}
	
	public int getClicks() {
		return clicks;
	}
	
	public String getPrefix() {
		return prefix;
	}
	
	public String getProper() {
		return proper;
	}
	
	public String getSuffix() {
		return suffix;
	}
	
    public Map<String, Integer> getStats() {
        return stats;
    }

    public ArrayList<Event> getHistory() {
        return history;
    }
    
    private void addToHistory(Event event){		//this will run every time the event's trigger condition is met, and should include deleting that completed event off the array list of possible events, and updating the name
        history.add(event);
        if(event.getModStats().containsKey("Damage"))
        	stats.put("Damage", (int) (stats.get("Damage")) + (int) (event.getModStats().get("Damage")));
    }
    
    private void updateName(Event event) {
    	if(event.getRegion() == Event.TITLE_PREFIX) {
    		if(prefix.equals("")) {
    			prefix = event.getModifier();
    		}
    		else
    			prefix = prefix + "-" + event.getModifier();
    	}
    	
    	else if(event.getRegion() == Event.TITLE_PROPER) {
    		if(proper.equals("")) {
    			proper = event.getModifier();
    		}
    		else
    			proper = proper + "-" + event.getModifier();
    	}
    	
    	else{
    		if(suffix.equals("")) {
    			suffix = event.getModifier();
    		}
    		else
    			suffix = suffix + "-" + event.getModifier();
    	}
    	super.name = prefix + " " + proper + " " + suffix;
    	this.fullName = super.name;
    }
	
    public void update(){
    	for(ClickTriggerable event : availableClickTriggerable) {
    		if(event.conditionMet(clicks)) {
    			updateName((Event)event);
    			addToHistory((Event)event);
    			availableClickTriggerable.remove(0);
    		}
    		else
    			break;
    	}
    	
    	for(KillTriggerable event : availableKillTriggerable) {
    		if(event.conditionMet(kills)) {
    			updateName((Event)event);
    			addToHistory((Event)event);
    			availableKillTriggerable.remove(0);
    		}
    		else
    			break;
    	}
    }
    
    public String toJson() {
    	Gson gson = new Gson();
    	return gson.toJson(this);
    }

    
    
    public static Weapon fromJson(String json) {
    	Gson gson = new Gson();

    	JsonParser jp = new JsonParser();
    	JsonObject jo = jp.parse(json).getAsJsonObject();
    	
    	String name = jo.get("name").getAsString();
    	
    	Type eventType = new TypeToken<ArrayList<Event>>(){}.getType();
    	ArrayList<Event> history = gson.fromJson(jo.get("history"), eventType);
    	
    	Type statsMapType = new TypeToken<HashMap<String,Integer>>(){}.getType();
    	HashMap<String,Integer> stats = gson.fromJson(jo.get("stats"), statsMapType);
    	
    	int kills = jo.get("kills").getAsInt();
    	int clicks = jo.get("clicks").getAsInt();
    	
    	/*
    	 * {
    	 * "history":[],
    	 * "stats":{"Speed":1,"Damage":1},
    	 * "kills":0,
    	 * "clicks":0,
    	 * "name2":"peanus cake",
    	 * "availableEvents":{"availableClickTriggerable":[],
    	 * "availableKillTriggerable":[
    	 * 	{"killsReq":10,"region":0,"modifier":"Sharp","modStats":{"Damage":1},"name":"TenKills"}
    	 * 	],
    	 * 
    	 * "availableByName":{"TenKills":{"killsReq":10,"region":0,"modifier":"Sharp","modStats":{"Damage":1},"name":"TenKills"}}},"availableClickTriggerable":[],"availableKillTriggerable":[{"killsReq":10,"region":0,"modifier":"Sharp","modStats":{"Damage":1},"name":"TenKills"}],"name":"peanus cake"}

    	 */
    	
    	
    	String prefix = jo.get("prefix").getAsString();
    	String proper = jo.get("proper").getAsString();
    	String suffix = jo.get("suffix").getAsString();
    	String name2 = jo.get("name2").getAsString();
    	
    	Type clickTriggerableType = new TypeToken<ArrayList<ClickTriggerable>>(){}.getType();
    	ArrayList<ClickTriggerable> availableClickTriggerable = gson.fromJson(jo.get("availableClickTriggerable"), clickTriggerableType);
    	
    	Type killTriggerableType = new TypeToken<ArrayList<TenKills>>(){}.getType();
    	ArrayList<KillTriggerable> availableKillTriggerable = gson.fromJson(jo.get("availableKillTriggerable"), killTriggerableType);
    	
    	Weapon output = new Weapon(name, history, stats, kills, clicks, prefix, proper, suffix, name2, availableClickTriggerable, availableKillTriggerable);
    	
    	return output;
    }
    
    /*List of stuff to add (roughly in high to low priority)
     * 		//done 		add int goblinsKilled or smt (another trackable)
     * 		//done		function to update the name whenever you get a new event (inside the addToHistory) (probs a for loop that goes through each completed event and adds each name into one of 3 parts (prefix, middle(proper), suffix), then adds the 3 parts together to create a full name (or you can just store the prefix, middle part (proper), suffix in separate strings and the getName method just combines them)(oh btw dont forget to add spaces and en dashes and stuff (i think easy way would be add an en dash between each prefix, proper, and suffix, and a space between the prefix proper and suffix (not technically correct but ez to implement)))
     * 		//done 		arraylist of all the events (grouped by events' interface) (will act as a checklist for this weapon to make sure we don't count one event (lol tbh calling them achivements make a lot more sense when u think about it but whatever) thingy twice)
     *		another potential stat to add would be maximum click speed, but these extra features r low priority
     *					peanus cake
     */
}