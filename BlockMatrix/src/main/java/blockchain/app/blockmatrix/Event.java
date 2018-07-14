package blockchain.app.blockmatrix;

public abstract class Event {
	public static final int TITLE_PREFIX = 0, TITLE_PROPER = 1, TITLE_SUFFIX = 2;
	
	protected String modifier; //weapon stat changes
	protected byte region; //where the title change occurs (prefix, proper, suffix)
	protected long date; //a date for when the event occured
	
	public Event(String modifier, byte region, long date) {
		this.modifier = modifier;
		this.date = date;
		this.region = region;
	}
	
	public abstract void checkForEvent();
	
	public abstract void modifyStats();
	
	//Accessors
	
	public String getModifier() {
		return modifier;
	}
	
	public long getDate() {
		return date;
	}
	
	public long getRegion() {
		return region;
	}
	
	/*every event has
	 * modification to stats
	 * function to stat hashmap (changes the modifications of the stats into a hashmap for easy addition into the weapon's previous stats)
	 * 
	 * a modification to the item name (
	 */
}
