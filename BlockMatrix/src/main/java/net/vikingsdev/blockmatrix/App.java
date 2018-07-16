package net.vikingsdev.blockmatrix;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import org.apache.commons.io.*;

import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

import net.vikingsdev.blockmatrix.gameobjects.Player;
import net.vikingsdev.blockmatrix.gameobjects.Trade;
import net.vikingsdev.blockmatrix.gameobjects.Weapon;

public class App  extends ListenerAdapter {
	static JDA jda;
	static MessageChannel objMsgCh;
	static Game game;
	public static void main(String[] args) throws Exception {	
		//NDIwNDYxNDc5OTA5NjU0NTI5.DYJhgg.E45CSzhMDMk48CuQ9a1UFXMl4jQ
		//REAL:  NDY4MTY2MTE5MDU2ODY3MzM4.Di1NhA.BBKmxdaTTi9qeZLShI2CGRihU-o
		jda = new JDABuilder(AccountType.BOT).setToken("NDY4MTY2MTE5MDU2ODY3MzM4.Di1NhA.BBKmxdaTTi9qeZLShI2CGRihU-o").buildBlocking();
		jda.addEventListener(new App());
		objMsgCh = jda.getTextChannelById(468166361068077058L);
		
		game = new Game(1280, 720, "BeefyBoi's BlockchainBasher");
		game.start();
		
		// tester code
		Player sender = new Player("Sender");
		Player receiver = new Player("Receiver");
		
		Weapon sendItem = new Weapon("Brutal waraxe of Supreme Jeremius");
		Weapon receiveItem = new Weapon("Master Devito's mighty magnum dong");
		
		sender.getInventory().add(sendItem);
		receiver.getInventory().add(receiveItem);
		
		System.out.println("Sender item: " + sender.getInventory().get(0));
		System.out.println("Receiver item: " + receiver.getInventory().get(0));
		
		Trade trade = new Trade(sender, receiver, 0, 0);
		trade.completeTrade();
		
		System.out.println("Sender item: " + sender.getInventory().get(0));
		System.out.println("Receiver item: " + receiver.getInventory().get(0));
		
		System.out.print("Exiting main...");
		
	}
	
	 @Override
	 public void onMessageReceived(MessageReceivedEvent evt) {
		 Message objMsg = evt.getMessage();
		 if(objMsg.getContentRaw().equals("BROADCAST")) {
			 System.out.println("Downloading...");
			 File old = new File("../Save/save.blkmtx");
			 old.delete();
			 File New = new File("../Save/save.blkmtx");
			 URL url = null;
			try {
				url = new URL(objMsg.getAttachments().get(0).getUrl());
			} catch (MalformedURLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			 try {
				FileUtils.copyURLToFile(url, New);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		 }else {
			 Long id = Long.parseLong(objMsg.getContentRaw());
			 if(id == game.getPlayer().getId()) {
				 File f = new File(System.getProperty("user.dir") + "/Communication/Recieved/Message.txt");
				 URL url = null;
					try {
						url = new URL(objMsg.getAttachments().get(0).getUrl());
					} catch (MalformedURLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					 try {
						FileUtils.copyURLToFile(url, f);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
			 }
			 
		 }
	 }
	
	 public static void update() {
			File f = new File("../Save/save.blkmtx");
			objMsgCh.sendMessage("BROADCAST").addFile(f).queue();
			
		}
	 
	
}
