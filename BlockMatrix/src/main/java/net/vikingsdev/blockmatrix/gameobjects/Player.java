package net.vikingsdev.blockmatrix.gameobjects;

import java.lang.reflect.Type;
import java.util.ArrayList;
import com.google.gson.*;
import com.google.gson.reflect.TypeToken;

//only gui (front end) is public	//@frank ok fine lets just make everything public and easy to get to so anyone can change stuff from a command prompt	//lol ok this is a lot more simple tho
public class Player {
	public boolean trade;
    private String name;
    private Long id;
	private ArrayList<Item> inventory = new ArrayList<Item>();

	private int activeSlot = 0, tradeSlot = -1;		//active slot should start at 0 so we dont get out of bounds stuff
	
    public Player(String name) {
        this.name = name;
        id = (long) (Math.random() * Long.MAX_VALUE);	//wait wait wait whats stopping duplicate ids		//one solution is to count up from MIN_VALUE or whatever its called and add in the total number of players to get the current number and go ++ each time a new player is made
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
    
    public Player(String name, Long id, ArrayList<Item> inventory, int activeSlot) {
        this.name = name;
        this.id = id;
        this.inventory = inventory;
        this.activeSlot = activeSlot;
    }
    
    public Player(String name, Long id, ArrayList<Item> inventory, int activeSlot, boolean trade) {
        this.name = name;
        this.id = id;
        this.inventory = inventory;
        this.activeSlot = activeSlot;
        this.trade = trade;
    }
    
    public String getName() {
        return name;
    }
    
    public Long getId() {
        return id;
    }
    
    public int getActiveSlot() {
        return activeSlot;
    }

    public ArrayList<Item> getInventory() {
        return inventory;
    }
    
    public Weapon getWeapon() {
    	return (Weapon)inventory.get(activeSlot);
    }
    
    //FOR THE STATS DISPLAY
    public int getDamage() {
    	return getWeapon().getStats().get("Damage");
    }
    public String getWeaponName() {
    	return getWeapon().getName();
    }
    
    public String toJsonString() {
    	return toJson().toString();
    }
    
    public JsonObject toJson() {			//idk how this works so just left all the Json stuff alone
    	JsonObject json = new JsonObject();
    	
    	json.addProperty("name", name);
    	json.addProperty("id", id);
    	json.addProperty("activeSlot", activeSlot);
    	json.addProperty("trade", trade);
    	
    	Gson gson = new Gson();
    	JsonElement element = gson.toJsonTree(inventory, new TypeToken<ArrayList<Item>>() {}.getType());
    	JsonArray jsonArray = element.getAsJsonArray();
    	json.add("inventory", jsonArray);
    	
    	return json;
    }
    
    public static Player toPlayer(String json) {		//are we sure the static wont cause problems later when we call this method in a method connected to the front-end stuff
    	JsonParser parser = new JsonParser();
    	JsonObject playerInfo = parser.parse(json).getAsJsonObject();

    	Type listType = new TypeToken<ArrayList<Item>>() {}.getType();
    	ArrayList<Item> inventory = new Gson().fromJson(playerInfo.get("inventory"), listType);
        
    	
    	
    	Player p = new Player(playerInfo.get("name").getAsString(),playerInfo.get("id").getAsLong(),inventory,playerInfo.get("activeSlot").getAsInt(),playerInfo.get("trade").getAsBoolean());
    	return p;
    }

	public void setActiveSlot(int activeSlot) {
		this.activeSlot = activeSlot;
	}
	
	//we gonna use txt files instead of this
	public int getTradeSlot() {
		return tradeSlot;
	}

	public void setTradeSlot(int tradeSlot) {
		this.tradeSlot = tradeSlot;
	}
	
	public boolean checkTradeStatus() {
		return trade;
	}
    
    //get name		//accessor implemented
	//get inventory		//accessor implemented
    
    /*list of stuff to do still (roughly in high to low priority)		not expecting to ever get to the bottom of this list, but its just stuff that we might be able to add
     * 		smt to keep track of which item is currently equipped
     * 		trading (most basic form will be you find someone in the "network" and initiate trade and "barter" until both players press accept or one player leaves, then depending on how we feel about it (will discuess tmrw(or i guess will be today) if we wanna add a marketplace where ppl can put up items for trade (only displays while they're online due to barter system) and others can brows marketplace to find items they want to try to trade for))
     * 		minimum viable product for clicking game (when game runs will probs be window with list of stuff in inventory and one weapon selected as the currently equipped, then they can click on the big thing in center of window, and after each second or smt you'll update and check through the weapon's stats and check for any newly completed events (via the soon-to-be implemented event lists for the currently equipped weapon(should be easy cuz ur only checking as many times as there are interfaces(eg. for loop to run up the list of events and call their conditionMet(int weapon's new stat) function))and then remove any completed events from the weapon's possible events list and stop when it fails one of those checks) 
     * 		game will probs want a max clicks per second mechanic to add another stat to the weapon
     * 		maybe every 10 dmg = dead goblin and earn a unit of currency and u ++ to the goblins killed stat on the weapon, this is for another trackable stat to trigger events (dmg will transfer over to next goblin, just be like ur sweeping through a goblin hoard, cuz this is easier than having a progression system)
     *		a shop to buy new blank swords (cuz diminishing returns for swords that r already insanly high level)
     * 		peer to peer stuff for trading (this should be higher priority cuz its kinda the whole point, but im too lazy to ctrl c, ctrl v)
     * 		if we somehow finish most of the other stuff we can quickly add an intro pop up whenever you open the game that goes smt like "goblin hoards r invading, go strike them down to hone your blade (literally lol) and collect their souls (currency for a shop if we ever implement one))
     *		more items in the shops for you to spend ur souls (the currency) on (we can argue about what to put here when we get there)
     *		if we feel like we have extra time somehow we can copy paste some ascii goblins onto the center canvas for the player to click on to give it some graphics (go google ascii gobiln if u dont know what i mean)
     */
	
}
