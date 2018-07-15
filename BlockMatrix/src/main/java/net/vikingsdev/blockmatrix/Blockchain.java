package net.vikingsdev.blockmatrix;

import java.net.*;
import java.io.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;

import java.security.*;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.ECGenParameterSpec;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonIOException;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

import org.bouncycastle.*;
import org.bouncycastle.util.io.pem.PemObjectGenerator;
import org.bouncycastle.util.io.pem.PemWriter;

import net.vikingsdev.blockmatrix.gameobjects.Player;
import net.vikingsdev.blockmatrix.utils.CryptoUtil;
import net.vikingsdev.blockmatrix.utils.OSUtil;
import net.vikingsdev.blockmatrix.utils.StringUtil;

public class Blockchain {
	public static ArrayList<Block> playerchain = new ArrayList<Block>();
	public static int difficulty = 5;
	private static JsonArray localSave;
	private static String localFilename = "save.blkmtx";
	
	private static final String privateKeyString = 
			"MIICWwIBAAKBgFyAgp6Cn4DYD9ujKUt6w/33y1OcS0CR7kVfYvmIZKHeby/1hqvO" + 
			"ML7XTKdARGuVsmyAvAgBwQ3OEFcZupGvx6O4lza05FBtcBpOUmzukke7wrsx7Lb/" + 
			"lcZmZ5+DKicDjbmAvS54Dtcj29IooD1DTZrUpG3tI4uWxFziKGRhxdUpAgMBAAEC" + 
			"gYA5hai0TWJGyxVBbfsV9ue+HMOR4NVi99yFw0VXCxwZFKtr1XDDkdr6MgR21R43" + 
			"EMX9dyh2ijC1FvlOUK+WPQsV4k9Qt8eUiGPCS3rHfcF2R7EsuZ2JurrSEkzSkTrJ" + 
			"QpV0HQnaM+CSoClsGKkVvC6iy5g42mGBme4p8ykcMPalaQJBAKkA78yi3H0RO19I" + 
			"FYcN+LTNFJkCkNJdK2rUBDER6yE0p8W3t1Oi2l91NMr94i/9mnZ8qAhh/hKdtDxS" + 
			"Hae15WcCQQCMHkrISwYpeyI6GUKowF+AubJWxJBvMxKTwuoKkF9hDkOAt0B02LGF" + 
			"jAOBATSPssEl/dWUygU/NKrgL7SA4cbvAkAU+xDKDs3gmOsOARzrD0j46RzNggwI" + 
			"kZh6Qqfij57pGGhbm1se/vCtORe8u5gA7TLn0sHpiIDKPtnJQ0h7MZGxAkEAhn5F" + 
			"FcA9p+9TSSUaANS2Vt7nubVvVe1V9ZLwBzfQ9V41mQVMz1t3+lIMwvUfOJdCIm9l" + 
			"ZS4Mn0C0i//+aH9O7QJAHOH5kBTXO1Flbxb/DhcMba3+24QZC3maO48KNMdUeKpn" + 
			"ymjNfV7BN+vlRLG2NY6B86DPIewmWXO35rgLcpclsA==";
	private static PrivateKey privateKey;
	private static final String publicKeyString =  
			"MIGeMA0GCSqGSIb3DQEBAQUAA4GMADCBiAKBgFyAgp6Cn4DYD9ujKUt6w/33y1Oc" + 
			"S0CR7kVfYvmIZKHeby/1hqvOML7XTKdARGuVsmyAvAgBwQ3OEFcZupGvx6O4lza0" + 
			"5FBtcBpOUmzukke7wrsx7Lb/lcZmZ5+DKicDjbmAvS54Dtcj29IooD1DTZrUpG3t" + 
			"I4uWxFziKGRhxdUpAgMBAAE=";
	private static PublicKey publicKey;
	static {
	    try 
	    {
	    	java.security.Security.addProvider(
	    	         new org.bouncycastle.jce.provider.BouncyCastleProvider()
	    	);
	    	
	        KeyFactory kf = KeyFactory.getInstance("RSA");

	        PKCS8EncodedKeySpec keySpecPKCS8 = new PKCS8EncodedKeySpec(Base64.getDecoder().decode(privateKeyString));
	        PrivateKey privKey = kf.generatePrivate(keySpecPKCS8);

	        X509EncodedKeySpec keySpecX509 = new X509EncodedKeySpec(Base64.getDecoder().decode(publicKeyString));
	        RSAPublicKey pubKey = (RSAPublicKey) kf.generatePublic(keySpecX509);

	        publicKey = pubKey;
	        privateKey = privKey;
	        
	        System.out.println(privKey);
	        System.out.println(pubKey);

	    } catch (Exception e) {
	    	e.printStackTrace();
	    }
	}
	
	public static void sendFile() throws Exception{
		ServerSocket serverSocket = new ServerSocket(15123);
		
		Socket socket = serverSocket.accept();
	    System.out.println("Accepted connection : " + socket);
	    File transferFile = new File ("../Save/save.blkmtx");
	    byte [] bytearray  = new byte [(int)transferFile.length()];
	    FileInputStream fin = new FileInputStream(transferFile);
	    BufferedInputStream bin = new BufferedInputStream(fin);
	    bin.read(bytearray,0,bytearray.length);
	    OutputStream os = socket.getOutputStream();
	    System.out.println("Sending Files...");
	    os.write(bytearray,0,bytearray.length);
	    os.flush();
	    socket.close();
	    System.out.println("File transfer complete");
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
	
	
	public static void read() {
		JsonParser parser = new JsonParser();
		
		String output = "";
		try {
			output = StringUtil.readFile("../Save/" + localFilename, StandardCharsets.UTF_8);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		JsonElement obj = parser.parse(output);
		localSave = obj.getAsJsonArray(); 
		
		Gson gson = new Gson();
		playerchain = gson.fromJson(output, new TypeToken<ArrayList<Block>>(){}.getType());
	}
	
//	public static boolean parseLocalJson() {
//		JsonParser parser = new JsonParser();
//        //Read and parse JSON file
//		try {
//			//System.err.println("READING FILE "+"../Save/" + localFilename+"...");
//			byte[] encrypted = StringUtil.readFileBytes("../Save/" + localFilename);
//			//System.out.println("PRINTING FILE...");
//			System.out.println(encrypted);
//			//System.err.println("\nFILE PRINTED...");
//			//System.err.println("FILE READ.");
//			//System.out.println(encrypted);
//			//System.err.println("DECRYPTING...");
//			byte[][] encryptedBytes = splitBytes(encrypted, 86);
//			String output = "";
//			for(int c = 0; c < encryptedBytes.length; c++) {
//				//System.out.println(encryptedBytes[c].length);
//				//System.err.write(encryptedBytes[c]);
//				byte[] coded = CryptoUtil.decrypt(privateKey, encryptedBytes[c]);
//				String decoded = new String(coded,StandardCharsets.UTF_8);
//				output += decoded;
//			}
//			
//			//System.out.print(output);
//			JsonElement obj = parser.parse(output);
//			localSave = obj.getAsJsonObject();
//			
//			Gson gson = new Gson();
//			playerchain = gson.fromJson(output, new TypeToken<ArrayList<Block>>(){}.getType());
//			
//			return true;
//			
//			
//		} catch (Exception e) {
//			e.printStackTrace();
//			return false;
//		}
//        
//	}
//	
//	public static void save() {
//		String blockchainJson = new GsonBuilder().setPrettyPrinting().create().toJson(playerchain);
//		byte[][] encrypted = null;
//		try {
//			encrypted = splitBytes(blockchainJson.getBytes(),75);
//			System.err.println("ENCRYPTING...");
//			for(int i = 0; i < encrypted.length; i++){
//				encrypted[i] = CryptoUtil.encrypt(publicKey, encrypted[i]);
//			}
//			//encrypted = 
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		File Old = new File("../Save/" + localFilename);
//		Old.delete();
//		File New = new File("../Save/" + localFilename);
//		try {
//			System.err.println("WRITING TO FILE...");
//			PrintWriter out = new PrintWriter(New);
//			for(int c = 0; c < encrypted.length; c++) {
//				for(int r = 0; r < encrypted[c].length;r++) {
//					out.write(encrypted[c][r]);
//				}
//				try {
//					System.err.write(CryptoUtil.decrypt(privateKey, encrypted[c]));
//					//System.out.println("LENGTH: " + encrypted[c].length);
//				} catch (Exception e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//			}
//			
//			out.close();
//		} catch (FileNotFoundException e) {
//			System.err.println("Error occured while writing to " + New.getPath() + ". File not found.");
//		}
//	}
	
	public static void save() {
		String blockchainJson = new GsonBuilder().setPrettyPrinting().create().toJson(playerchain);
		File Old = new File("../Save/" + localFilename);
		Old.delete();
		File New = new File("../Save/" + localFilename);
		
		try {
			PrintWriter out = new PrintWriter(New);
			out.write(blockchainJson);
			out.close();
		}catch(Exception e) {
			
		}
	}
	
	public static byte[][] splitBytes(final byte[] data, final int chunkSize)
	{
	  final int length = data.length;
	  final byte[][] dest = new byte[(length + chunkSize - 1)/chunkSize][];
	  int destIndex = 0;
	  int stopIndex = 0;

	  for (int startIndex = 0; startIndex + chunkSize <= length; startIndex += chunkSize)
	  {
	    stopIndex += chunkSize;
	    dest[destIndex++] = Arrays.copyOfRange(data, startIndex, stopIndex);
	  }

	  if (stopIndex < length)
	    dest[destIndex] = Arrays.copyOfRange(data, stopIndex, length);

	  return dest;
	}
}
