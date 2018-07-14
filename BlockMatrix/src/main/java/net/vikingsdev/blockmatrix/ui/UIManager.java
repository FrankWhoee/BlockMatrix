package net.vikingsdev.blockmatrix.ui;

import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class UIManager {
	private ArrayList<UIButton> objects; //we only have one type of UI object so I removed the intermediate parent class
	
	public UIManager() {
		objects = new ArrayList<UIButton>();
	}
	
	public void onMouseMove(MouseEvent e) {
		for(UIButton o : objects) o.onMouseMove(e);
	}
	
	public void onMousePress(MouseEvent e) {
		for(UIButton o : objects) o.onMousePress(e);
	}
	
	public void onMouseRelease(MouseEvent e) {
		for(UIButton o : objects) o.onMouseRelease(e);
	}
	
	public void tick() {
		for(UIButton o : objects) o.tick();
	}
	
	public void render(Graphics g) {
		for(UIButton o : objects) o.render(g);
	}
	
	//getters and setters
	
	public void addObject(UIButton o) {
		objects.add(o);
	}
	
	public void removeObjects(UIButton o) {
		objects.remove(o);
	}

	public ArrayList<UIButton> getObjects() {
		return objects;
	}
}
