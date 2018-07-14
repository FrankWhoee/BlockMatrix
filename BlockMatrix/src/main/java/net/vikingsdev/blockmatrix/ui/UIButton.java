package net.vikingsdev.blockmatrix.ui;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

public class UIButton {
	public static final byte RESTING = 0, HOVERING = 1, CLICKING = 2;
	private byte state;
	
	private UIListener uil;
	
	private Rectangle bounds;
	private BufferedImage[] texture;
	
	public UIButton(int x, int y, int width, int height, BufferedImage[] texture, UIListener uil) {
		this.texture = texture;
		this.uil = uil;
		state = 0;
		
		bounds = new Rectangle(x, y, width, height);
	}
	
	public void tick() {
		
	}
	
	public void render(Graphics g) {
		g.drawImage(texture[state], bounds.x, bounds.y, bounds.width, bounds.height, null);
	}
	
	public void onMouseMove(MouseEvent e) {
		if(bounds.contains(e.getX(), e.getY())) state = HOVERING;
		else state = RESTING;
	}
	
	public void onMousePress(MouseEvent e) {
		if(bounds.contains(e.getX(), e.getY())) state = CLICKING;
		else state = RESTING;
	}
	
	public void onMouseRelease(MouseEvent e) {
		if(state == HOVERING) onClick();
	}
	
	public void onClick() {
		uil.onClick();
	}
}
