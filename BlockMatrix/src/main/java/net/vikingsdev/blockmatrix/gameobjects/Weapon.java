package net.vikingsdev.blockmatrix.gameobjects;

import java.util.ArrayList;
import java.util.HashMap;

public class Weapon extends Item{
	//every weapon has		// <---- should delete this comment

    private String name;
    private HashMap stats = new HashMap();
    private ArrayList<Event> history = new ArrayList<Event>();

    public Weapon(String name) {
        this.name = name;
        stats.put("Damage", 0);		//i think weapon dmg needs to start at 1 lol, cuz for this mvp the point is all ur stats r tied up in ur weapon
    }

    public String getName() {
        return name;
    }

    public HashMap<String, Integer> getStats() {p
        return stats;
    }

    public ArrayList<Event> getHistory() {
        return history;
    }

    public void addToHistory(Event event){		//this will run everytime the event's trigger condition is met, and should include deleting that completed event off the array list of possible events, and updating the name
        history.add(event);
        stats.put("Damage", (int) (stats.get("Damage")) + (int) (event.getModStats().get("Damage")));
    }
	
	/*every item has:
	*string name
	*hashmap stats
	*arraylist<Event> history
	*
	*name and stats depend on history
	*
	*function for turning history into stats
	*/

    /*List of stuff to add (roughly in high to low priority)
     * 		add int goblinsKilled or smt (another trackable)
     * 		function to update the name whenever you get a new event (inside the addToHistory) (probs a for loop that goes through each completed event and adds each name into one of 3 parts (prefix, middle(proper), suffix), then adds the 3 parts together to create a full name (or you can just store the prefix, middle part (proper), suffix in separate strings and the getName method just combines them)(oh btw dont forget to add spaces and en dashes and stuff (i think easy way would be add an en dash between each prefix, proper, and suffix, and a space between the prefix proper and suffix (not technically correct but ez to implement)))
     * 		arraylist of all the events (grouped by events' interface) (will act as a checklist for this weapon to make sure we don't count one event (lol tbh calling them achivements make a lot more sense when u think about it but whatever) thingy twice)
     *		another potential stat to add would be maximum click speed, but these extra features r low priority
     */
}