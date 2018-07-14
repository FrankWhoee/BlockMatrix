package net.vikingsdev.blockmatrix.gfx;

import java.awt.image.BufferedImage;

public class Assets {

	private static final int UICellSize = 64;
	
	public static BufferedImage[][] button;
	public static BufferedImage eventBackground, statsBackground, enemyBackground, orc, forestBG;
	
	/*
	 * [0][i] - inventory button
	 * [1][i] - trade button
	 * [2][i] - attack button
	 * [3][i] - equip button
	 * [4][i] - trade button
	 * [5][i] - confirm button
	 * [6][i] - deny button
	 * [7][i] - generic x button
	 * 
	 * [i][0] - button rested
	 * [i][1] - button hovered/active
	 * [i][2] - button clicked
	 */
	
	public static void init() {
		SpriteSheet UISheet = new SpriteSheet(ImageLoader.loadImage("/mainscreenui.png"));
		SpriteSheet MobSheet = new SpriteSheet(ImageLoader.loadImage("/mobandwp.png"));
		SpriteSheet Overlay = new SpriteSheet(ImageLoader.loadImage("/overlayUI.png"));
		button = new BufferedImage[8][3];
		
		// UI sprites
		
		button[0][0] = UISheet.crop(UICellSize * 12, UICellSize * 2, UICellSize * 4, UICellSize * 2);
		button[0][1] = UISheet.crop(UICellSize * 16, UICellSize * 2, UICellSize * 4, UICellSize * 2);
		button[0][2] = UISheet.crop(UICellSize * 16, UICellSize * 2, UICellSize * 4, UICellSize * 2);
		
		button[1][0] = UISheet.crop(UICellSize * 12, UICellSize * 4, UICellSize * 4, UICellSize * 2);
		button[1][1] = UISheet.crop(UICellSize * 16, UICellSize * 4, UICellSize * 4, UICellSize * 2);
		button[1][2] = UISheet.crop(UICellSize * 16, UICellSize * 4, UICellSize * 4, UICellSize * 2);
		
		button[2][0] = UISheet.crop(0, UICellSize * 9, UICellSize * 4, UICellSize * 2);
		button[2][1] = UISheet.crop(UICellSize * 4, UICellSize * 9, UICellSize * 4, UICellSize * 2);
		button[2][2] = UISheet.crop(UICellSize * 8, UICellSize * 9, UICellSize * 4, UICellSize * 2);

		button[3][0] = Overlay.crop(UICellSize * 4, 0, UICellSize * 4, UICellSize * 2);
		button[3][1] = Overlay.crop(UICellSize * 8, 0, UICellSize * 4, UICellSize * 2);
		button[3][2] = Overlay.crop(UICellSize * 8, 0, UICellSize * 4, UICellSize * 2);
		
		button[4][0] = Overlay.crop(UICellSize * 4, UICellSize * 2, UICellSize * 4, UICellSize * 2);
		button[4][1] = Overlay.crop(UICellSize * 8, UICellSize * 2, UICellSize * 4, UICellSize * 2);
		button[4][2] = Overlay.crop(UICellSize * 8, UICellSize * 2, UICellSize * 4, UICellSize * 2);
		
		button[5][0] = Overlay.crop(UICellSize * 12, 0, UICellSize * 4, UICellSize * 2);
		button[5][1] = Overlay.crop(UICellSize * 16, 0, UICellSize * 4, UICellSize * 2);
		button[5][2] = Overlay.crop(UICellSize * 16, 0, UICellSize * 4, UICellSize * 2);
		
		button[6][0] = Overlay.crop(UICellSize * 12, UICellSize * 2, UICellSize * 4, UICellSize * 2);
		button[6][1] = Overlay.crop(UICellSize * 16, UICellSize * 2, UICellSize * 4, UICellSize * 2);
		button[6][2] = Overlay.crop(UICellSize * 16, UICellSize * 2, UICellSize * 4, UICellSize * 2);
		
		button[7][0] = Overlay.crop(0, 0, UICellSize, UICellSize);
		button[7][1] = Overlay.crop(UICellSize, 0, UICellSize, UICellSize);
		button[7][2] = Overlay.crop(UICellSize, 0, UICellSize, UICellSize);
		
		eventBackground = UISheet.crop(UICellSize * 12, 0, UICellSize * 8, UICellSize * 2);
		statsBackground = UISheet.crop(UICellSize * 13, UICellSize * 6, UICellSize * 7, UICellSize * 5);
		enemyBackground = UISheet.crop(0, 0, UICellSize * 12, UICellSize * 9);
		
		forestBG = MobSheet.crop(0, 0, UICellSize * 12, UICellSize * 9);
		orc = MobSheet.crop(UICellSize * 12, 0, UICellSize * 7, UICellSize * 9);
	}
}
