package net.vikingsdev.blockmatrix.gfx;

import java.awt.image.BufferedImage;

public class Assets {

	private static final int UICellSize = 64;
	
	public static BufferedImage[][] button;
	
	/*
	 * [0][i] - inventory button
	 * [1][i] - trade button
	 * 
	 * [i][0] - button rested
	 * [i][1] - button active
	 */
	
	public static void init() {
		SpriteSheet UISheet = new SpriteSheet(ImageLoader.loadImage("/uisheet.png"));
		
		// UI sprites
		
		button[0][0] = UISheet.crop(UICellSize * 4, UICellSize * 4, UICellSize * 4, UICellSize * 2);
		button[0][1] = UISheet.crop(UICellSize * 4, 0, UICellSize * 4, UICellSize * 2);
		button[1][0] = UISheet.crop(UICellSize * 4, UICellSize * 6, UICellSize * 4, UICellSize * 2);
		button[1][1] = UISheet.crop(UICellSize * 4, UICellSize * 2, UICellSize * 4, UICellSize * 2);
	}
}
