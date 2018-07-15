package net.vikingsdev.blockmatrix.gameobjects;

import java.io.*;

public class TradeSequence {
	public final String DIR = System.getProperty("user.dir");

	
	public void writing() {
		try {
	        //Whatever the file path is:
			File fileToSend = new File(DIR + "/sendText.txt");
	        FileOutputStream is = new FileOutputStream(statText);
	        OutputStreamWriter osw = new OutputStreamWriter(is);    
	        Writer w = new BufferedWriter(osw);
	        w.write("POTATO!!!");
	        w.close();
	    } catch (IOException e) {
	        System.err.println("Problem writing to the file statsTest.txt");
	    }
	}
}
