package net.vikingsdev.blockmatrix.utils;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.MessageDigest;

public class StringUtil {
	//Applies Sha256 to a string and returns the result. 
	public static String applySha256(String input){		
		try {
			MessageDigest digest = MessageDigest.getInstance("SHA-256");	        
			//Applies sha256 to our input, 
			byte[] hash = digest.digest(input.getBytes("UTF-8"));	        
			StringBuffer hexString = new StringBuffer(); // This will contain hash as hexidecimal
			for (int i = 0; i < hash.length; i++) {
				String hex = Integer.toHexString(0xff & hash[i]);
				if(hex.length() == 1) hexString.append('0');
				hexString.append(hex);
			}
			return hexString.toString();
		}
		catch(Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	public static void centre(String s, int x, int y, Graphics g) {
		FontMetrics fm = g.getFontMetrics(g.getFont());
		int xOffset = fm.stringWidth(s) / 2;
		int yOffset = fm.getHeight() / 2 - fm.getAscent();
		
		g.drawString(s, x - xOffset, y - yOffset);
	}
	
	public static String readFile(String path, Charset encoding) throws IOException {
			byte[] encoded = Files.readAllBytes(Paths.get(path));
			return new String(encoded, encoding);
	}
}