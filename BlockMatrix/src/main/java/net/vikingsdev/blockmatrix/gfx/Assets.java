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
	 * 
	 * [i][0] - button rested
	 * [i][1] - button hovered/active
	 * [i][2] - button clicked
	 */
	
	public static void init() {
		SpriteSheet UISheet = new SpriteSheet(ImageLoader.loadImage("/mainscreenui.png"));
		SpriteSheet MobSheet = new SpriteSheet(ImageLoader.loadImage("/mobandwp.png"));
		button = new BufferedImage[3][3];
		
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

		eventBackground = UISheet.crop(UICellSize * 12, 0, UICellSize * 8, UICellSize * 2);
		statsBackground = UISheet.crop(UICellSize * 13, UICellSize * 6, UICellSize * 7, UICellSize * 5);
		enemyBackground = UISheet.crop(0, 0, UICellSize * 12, UICellSize * 9);
		
		forestBG = MobSheet.crop(0, 0, UICellSize * 12, UICellSize * 9);
		orc = MobSheet.crop(UICellSize * 12, 0, UICellSize * 7, UICellSize * 9);
	}
}
