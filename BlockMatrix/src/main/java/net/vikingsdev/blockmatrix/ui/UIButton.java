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
	
	public void reset() {
		state = RESTING;
	}
	
	private int clickBuffer = 0;
	
	//ghetto click buffer
	
	public void update() {
		if(clickBuffer < 5) {
			clickBuffer++;
			state = CLICKING;
		}else if(state == CLICKING) state = HOVERING;
	}
	
	public void render(Graphics g) {
		update(); //temporary
		if(texture != null) g.drawImage(texture[state], bounds.x, bounds.y, bounds.width, bounds.height, null);
	}
	
	public void onMouseMove(MouseEvent e) {
		if(bounds.contains(e.getX(), e.getY())) state = HOVERING;
		else reset();
	}
	
	public void onMousePress(MouseEvent e) {
		if(bounds.contains(e.getX(), e.getY())) state = CLICKING;
		else reset();
	}
	
	public void onMouseRelease(MouseEvent e) {
		if(state != RESTING) onClick();
	}
	
	public void onClick() {
		clickBuffer = 0;
		uil.onClick();
	}
}
