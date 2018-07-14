package net.vikingsdev.blockmatrix.gameobjects;

import java.util.ArrayList;
import java.util.Arrays;

public class AvailableEvents {
	private ArrayList<ClickTriggerable> availableClickTriggerable = new ArrayList<>();
	private ArrayList<KillTriggerable> availableKillTriggerable = new ArrayList<>(Arrays.asList(new TenKills()));
	
	public AvailableEvents() {
	}
	
	public ArrayList<ClickTriggerable> getAvailableClickTriggerable(){
		return availableClickTriggerable;
	}
	
	public ArrayList<KillTriggerable> getAvailableKillTriggerable(){
		return availableKillTriggerable;
	}
}
