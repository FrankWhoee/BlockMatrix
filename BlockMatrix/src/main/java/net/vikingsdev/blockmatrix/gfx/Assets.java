package net.vikingsdev.blockmatrix.gfx;

import java.awt.image.BufferedImage;

public class Assets {

	private static final int UICellWidth = 64, UICellHeight = 64;
	
	public static BufferedImage invButtonRest, invButtonHover, tradeButtonRest, tradeButtonHover;
	
	public static void init() {
		SpriteSheet UISheet = new SpriteSheet(ImageLoader.loadImage("/uisheet.png"));
		
		// UI sprites
		invButtonRest = UISheet.crop(UICellWidth * 4, UICellHeight * 4, UICellWidth * 4, UICellHeight * 2);
		invButtonHover = UISheet.crop(UICellWidth * 4, 0, UICellWidth * 4, UICellHeight * 2);
		tradeButtonRest = UISheet.crop(UICellWidth * 4, UICellHeight * 6, UICellWidth * 4, UICellHeight * 2);
		tradeButtonHover = UISheet.crop(UICellWidth * 4, UICellHeight * 2, UICellWidth * 4, UICellHeight * 2);
	}
	
}
