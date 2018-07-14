package net.vikingsdev.blockmatrix;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Base64;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import java.security.*;
import java.security.spec.ECGenParameterSpec;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import com.google.gson.GsonBuilder;
import com.google.gson.JsonIOException;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;

import org.bouncycastle.*;
import org.bouncycastle.util.io.pem.PemObjectGenerator;
import org.bouncycastle.util.io.pem.PemWriter;

import net.vikingsdev.blockmatrix.gameobjects.Player;
import net.vikingsdev.blockmatrix.utils.CryptoUtil;
import net.vikingsdev.blockmatrix.utils.OSUtil;

public class Blockchain {
	public static ArrayList<Block> playerchain = new ArrayList<Block>();
	public static int difficulty = 5;
	private static JsonObject localSave;
	
	private static final String privateKeyString = "MIICWgIBAAKBgFggG2repM9WfEyYfUnIpyE30p50VzwiR/v5zOqXjsbfm4qTNswO\n" + 
			"XUqqyCYBGyAAm4WFudQwczq1EYhfxjehEyZvsESxWlrJU9lQsHiXZi+TXA2C8U91\n" + 
			"FTcGSd08JCXi45SMj6L5d7nrIcWoh6vzYuZJgVliPCOeOlsqrtxOOxmZAgMBAAEC\n" + 
			"gYAgxVfMxxy8qamxhidZL8/Oz4Z4PEVQbGMedcdZGaETnYWm45XGDjVtFdM2JsUi\n" + 
			"gndTEOaI3LeNBlaNwyff+fs3qIg6xxHrNSWSCOFVrLXyJBycMHCgVAzmwEQ3qlcJ\n" + 
			"F4EMlx8A1TMK2CQXsBno56YnHMS9IBfK+VX4soQw6amb7QJBAKc39yRgQlPuxiBs\n" + 
			"0lSz/Rt+KiQycXo4ClI+iBj54LdzUB9D03yAeSW0JToaZQbtvzaJYoJp3sas1cOk\n" + 
			"SZI2LpMCQQCG6fIQ0cb0gnFq8qUi1y8uBjNRVWKvGsjLla9tJmO7QwOKSB+9IJjx\n" + 
			"OKUVLRiFNds2hEVwCRxw799VyK+PTAajAkB/gHHmf6szemYBxVgfE9qTgwC49umF\n" + 
			"yoM8MMfvW2CYIMnf/QSd8wvszs5v3j5YKSHagKjnTbh6/sBD29npLX0XAkAtHbbR\n" + 
			"YRWDVsvO0+3dRNCGN5SZslAZ9n8HdbsAaUIYsURRzeSvPmNm52ZtOnNkuKniBUGj\n" + 
			"Qai9K7on2cC7ztS5AkAXa3U3l1eOyW425tWFoi3Ayc7Xb4e+9qpx50dL2WHd9aux\n" + 
			"Wz4TT0dFtab+xG0qtTd8W8ekpFkYvyZwWISgfbJ8";
	private static PrivateKey privateKey;
	private static final String publicKeyString = "MIGeMA0GCSqGSIb3DQEBAQUAA4GMADCBiAKBgFggG2repM9WfEyYfUnIpyE30p50\n" + 
			"VzwiR/v5zOqXjsbfm4qTNswOXUqqyCYBGyAAm4WFudQwczq1EYhfxjehEyZvsESx\n" + 
			"WlrJU9lQsHiXZi+TXA2C8U91FTcGSd08JCXi45SMj6L5d7nrIcWoh6vzYuZJgVli\n" + 
			"PCOeOlsqrtxOOxmZAgMBAAE=";
	private static PublicKey publicKey;
	static {
	    KeyFactory kf;
	    try {
	        kf = KeyFactory.getInstance("RSA");
	        byte[] encodedPv = Base64.getDecoder().decode(privateKeyString);
	        PKCS8EncodedKeySpec keySpecPv = new PKCS8EncodedKeySpec(encodedPv);
	        privateKey = kf.generatePrivate(keySpecPv);

	        byte[] encodedPb = Base64.getDecoder().decode(publicKeyString);
	        X509EncodedKeySpec keySpecPb = new X509EncodedKeySpec(encodedPb);
	        publicKey = kf.generatePublic(keySpecPb);

	    } catch (Exception e) {

	    }
	}
	
	public static void register(String name) {
		Player newPlayer = new Player(name);
		playerchain.add(new Block(newPlayer.toJsonString(), playerchain.get(playerchain.size()-1).hash));
		playerchain.get(playerchain.size()-1).mineBlock(difficulty);

	}
	
	public static Boolean isChainValid() {
		Block currentBlock; 
		Block previousBlock;
		
		//loop through playerchain to check hashes:
		for(int i=1; i < playerchain.size(); i++) {
			currentBlock = playerchain.get(i);
			previousBlock = playerchain.get(i-1);
			//compare registered hash and calculated hash:
			if(!currentBlock.hash.equals(currentBlock.calculateHash()) ){
				System.out.println("Current Hashes not equal");			
				return false;
			}
			//compare previous hash and registered previous hash
			if(!previousBlock.hash.equals(currentBlock.previousHash) ) {
				System.out.println("Previous Hashes not equal");
				return false;
			}
		}
		return true;
	}
	
	public static boolean parseLocalJson() {
		return false;
//		JsonParser parser = new JsonParser();
//        //Read and parse JSON file
//		try {
//			if(OSUtil.isUnix()) {
//				Object obj = parser.parse(new FileReader("../Save"));
//			}
//			
//			
//		} catch (Exception e) {
//			return false;
//		}
        
	}

	
	public static void save() {
		String blockchainJson = new GsonBuilder().setPrettyPrinting().create().toJson(playerchain);
		byte[] encrypted;
		try {
			 encrypted = CryptoUtil.encrypt(publicKey, blockchainJson.getBytes());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
//	public static void save() {
//    	Util.exec("rm " + "../" + Ref.foldername + "/" + Ref.filename);
//		File file = new File("../" + Ref.foldername + "/" + Ref.filename);
//		try {
//			PrintWriter out = new PrintWriter(file);
//			out.println(tamagotchis.toString());
//			out.close();
//		} catch (FileNotFoundException e) {
//			System.err.println("Error occured while writing to " + file.getPath() + ". File not found.");
//		}
//    }
}
