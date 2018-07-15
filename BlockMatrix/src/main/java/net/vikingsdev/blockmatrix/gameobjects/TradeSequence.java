package net.vikingsdev.blockmatrix.gameobjects;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class TradeSequence {
	public final String DIR = System.getProperty("user.dir") + "/Communication";

	public TradeSequence() {
	}
	
	//run this from after you find 2 players with the trade tag on,		probs easier to put this in Game
	public void commenceTrade() {
		//boolean continue = ask use if they wanna continue;
		//set up server-client
		//if(continue) {
			//select item, confirm
			//writing(weaponToString(whatever weapon u chose));
			//send ur file, recieve the other person's
			//player.getInventory.add(stringToWeapon);
		//}
		//else{
			//return;
		
		//}
	}
	/*String name
	 * ArrayList<Event> history
	 * HashMap<String,Integer> stats
	 * int kills
	 * int clicks
	 * String prefix
	 * String proper
	 * String suffix
	 * String thisName
	 * ArrayList<ClickTriggerable> availableClickTriggerable
	 * ArrayList<KillTriggerable> availableKillTriggerable
	*/
	public String weaponToString(Weapon weapon) {	//"`" is the character used to separate the small stuff,	"~" is the character used to separate the big stuff
		String str = weapon.getName() + "~";
		for(Event event : weapon.getHistory()) {
			str += event.getName() + "`";
		}
		
		str += "~";
		for(String key : weapon.getStats().keySet()) {
			str += key + "`" + weapon.getStats().get(key) + "`";
		}
		
		str+= "~" + (weapon.getKills()) + "~"+ (weapon.getClicks()) + "~" + weapon.getPrefix() + "~" + weapon.getProper() + "~" + weapon.getSuffix() + "~" + weapon.getName() + "~";
		for(ClickTriggerable event : weapon.getAvailableClickTriggerable()) {
			str += ((Event)(event)).getName() + "`";
		}
		str += "~";
		for(KillTriggerable event : weapon.getAvailableKillTriggerable()) {
			str += ((Event)(event)).getName() + "`";
		}
		return str + "~";
	}
	
	public Weapon stringToWeapon() {
		AvailableEvents eventList = new AvailableEvents();
		String str = readToString();
		
		String name = readUntilL(str);
		str = str.substring(nextBreak(str));
		
		ArrayList<Event> history = new ArrayList<Event>();
		for(int k = 0; k < str.length()/2; k++) {
			if(nextBreak(str) <= 0) {
				str = str.substring(1);
				break;
			}
			else {
				history.add(eventList.getEventByName(readUntilS(str)));
				str = str.substring(nextBreak(str));
			}
		}
		
		HashMap<String,Integer> stats = new HashMap<String,Integer>();
		String key;
		int value;
		for(int k = 0; k < str.length()/2; k++) {
			if(nextBreak(str) <= 0) {
				str = str.substring(1);
				break;
			}
			else {
				key = readUntilS(str);
				str = str.substring(nextBreak(str));
				value = (int)Integer.valueOf(readUntilS(str));
				str = str.substring(nextBreak(str));
				stats.put(key, value);
			}
		}
		
		
		int kills = (int)Integer.valueOf(readUntilL(str));
		str = str.substring(nextBreak(str));
		int clicks = (int)Integer.valueOf(readUntilL(str));
		str = str.substring(nextBreak(str));
		String prefix = readUntilL(str);
		str = str.substring(nextBreak(str));
		String proper = readUntilL(str);
		str = str.substring(nextBreak(str));
		String suffix = readUntilL(str);
		str = str.substring(nextBreak(str));
		String thisName = readUntilL(str);
		str = str.substring(nextBreak(str));
		
		ArrayList<ClickTriggerable> availableClickTriggerable = new ArrayList<ClickTriggerable>();
		for(int k = 0; k < str.length()/2; k++) {
			if(nextBreak(str) <= 0) {
				str = str.substring(1);
				break;
			}
			else {
				availableClickTriggerable.add((ClickTriggerable) eventList.getEventByName(readUntilS(str)));
				str = str.substring(nextBreak(str));
			}
		}
		
		ArrayList<KillTriggerable> availableKillTriggerable = new ArrayList<KillTriggerable>();
		for(int k = 0; k < str.length()/2; k++) {
			if(nextBreak(str) <= 0) {
				str = str.substring(1);
				break;
			}
			else {
				availableKillTriggerable.add((KillTriggerable) eventList.getEventByName(readUntilS(str)));
				str = str.substring(nextBreak(str));
			}
		}
		
		return new Weapon(name, history, stats, kills, clicks, prefix, proper, suffix, thisName, availableClickTriggerable, availableKillTriggerable);
	}
	
	private String readUntilS(String str){
		for(int k = 0; k < str.length(); k++) {
			if(str.substring(k, k+1).equals("`")) {
				return str.substring(0, k);
			}
		}
		return str;
	}
	
	private String readUntilL(String str){
		for(int k = 0; k < str.length(); k++) {
			if(str.substring(k, k+1).equals("~")) {
				return str.substring(0, k);
			}
		}
		return str;
	}
	
	private int nextBreak(String str) {
		for(int k = 0; k < str.length(); k++) {
			if(str.substring(k, k+1).equals("~") || str.substring(k, k+1).equals("`")) {
				return k;
			}
		}
		return -1;
	}
	
    public void writing(String message) {
    	try {
            //Whatever the file path is:
            File fileToSend = new File(DIR + "/ToSend/Message.txt");
            FileOutputStream is = new FileOutputStream(fileToSend);
            OutputStreamWriter osw = new OutputStreamWriter(is);    
            Writer w = new BufferedWriter(osw);
            w.write(message);
            w.close();
    	} catch (IOException e) {
    		System.err.println("Problem writing to the file in ToSend");
    	}
    }
    
    private String readToString(){
    	try {
            //Whatever the file path is:
            File fileToRead = new File(DIR + "/Recieved/Message.txt");
            Scanner scan = new Scanner(fileToRead);
            String str = scan.nextLine();
            scan.close();
            return str;
    	} catch (IOException e) {
    		System.err.println("Problem reading the file in Recieved");
    	}
        return null;
    }
    
    //below is unused
    private boolean readToBool(){
    	try {
            //Whatever the file path is:
            File fileToRead = new File(DIR + "/ToRecieve/Message.txt");
            Scanner scan = new Scanner(fileToRead);
            boolean bool = scan.nextLine().equals("true");
            scan.close();
            if(bool) {
                return true;
            }
            else {
                return false;
            }
		} catch (IOException e) {
			System.err.println("Problem reading the file in ToRecieve");
		}
    	return false;
    }
}
