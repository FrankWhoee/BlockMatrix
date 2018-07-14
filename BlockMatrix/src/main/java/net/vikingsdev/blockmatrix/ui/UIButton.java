package net.vikingsdev.blockmatrix.ui;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

public class UIButton {
	public static final byte RESTED = 0, HOVERED = 1, CLICKED = 2;
	private boolean hovering;
	
	private Rectangle bounds;
	private BufferedImage[] texture;
	
	public UIButton(int x, int y, int width, int height, BufferedImage[] texture, UIListener uil) {
		this.texture = texture;
		
		bounds = new Rectangle(x, y, width, height);
	}
	
	public void render(Graphics g) {
		g.drawImage(texture[0], bounds.x, bounds.y, bounds.width, bounds.height, null);
	}
}
