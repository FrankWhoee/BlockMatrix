package blockchain.app.blockmatrix;

public class Event {
	public static final int TITLE_PREFIX = 0, TITLE_PROPER = 1, TITLE_SUFFIX = 2;
	
	private String modifier; //weapon stat changes
	private byte region; //where the title change occurs (prefix, proper, suffix)
	private long date; //a date for when the event occured
	private Item item; //accessor for the item that is being changed
	
	public Event(String modifier, byte region, long date, Item item) {
		this.modifier = modifier;
		this.date = date;
		this.item = item;
	}
	
	public void checkForEvent() {
		//Unused
	}
	
	public void modifyStats() {
		//Unused
	}
	
	//Accessors
	
	public String getModifier() {
		return modifier;
	}
	public long getDate() {
		return date;
	}
	
	/*every event has
	 * modification to stats
	 * function to stat hashmap (changes the modifications of the stats into a hashmap for easy addition into the weapon's previous stats)
	 * 
	 * a modification to the item name (
	 */
}
