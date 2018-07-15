package net.vikingsdev.blockmatrix.networking;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Serializable;

public class FileContents implements Serializable{
	protected static final long serialVersionUID = 1112122200L;
	private String contents;
	FileReader fr;
	BufferedReader br;
	
	public FileContents(File file) {
		try {
			fr = new FileReader(file);
			br = new BufferedReader(fr);
			contents = br.readLine();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
