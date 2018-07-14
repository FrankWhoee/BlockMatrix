package net.vikingsdev.blockmatrix.gfx;

import java.awt.image.BufferedImage;

public class Assets {

	private int UICellWidth = 64, UICellHeight = 64;
	
	public static BufferedImage weapon;
	
	public static void init() {
		SpriteSheet UISheet = new SpriteSheet(ImageLoader.loadImage("/uisheet.png"));
	}
	
}
