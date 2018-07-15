package net.vikingsdev.blockmatrix;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import org.apache.commons.io.*;

import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;
import net.dv8tion.jda.core.OnlineStatus;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.entities.MessageHistory;
import net.dv8tion.jda.core.entities.PrivateChannel;
import net.dv8tion.jda.core.entities.Role;
import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.entities.VoiceChannel;
import net.dv8tion.jda.core.events.Event;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.EventListener;
import net.dv8tion.jda.core.hooks.ListenerAdapter;
import net.dv8tion.jda.core.managers.*;
import net.dv8tion.jda.core.requests.RestAction;
import net.dv8tion.jda.core.EmbedBuilder;

import net.vikingsdev.blockmatrix.gameobjects.Player;
import net.vikingsdev.blockmatrix.gameobjects.Trade;
import net.vikingsdev.blockmatrix.gameobjects.Weapon;
import net.vikingsdev.blockmatrix.networking.Client;

public class App  extends ListenerAdapter {
	static JDA jda;
	static MessageChannel objMsgCh;
	static Game game;
	public static void update() {
		File f = new File("../Save/save.blkmtx");
		objMsgCh.sendMessage("BROADCAST").addFile(f).queue();
		
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
	
	public static void main(String[] args) throws Exception {	
		jda = new JDABuilder(AccountType.BOT).setToken("NDY4MTY2MTE5MDU2ODY3MzM4.Di1NhA.BBKmxdaTTi9qeZLShI2CGRihU-o").buildBlocking();
		objMsgCh = jda.getTextChannelById(468166361068077058L);
		
		
		
/*		// tester
		Client client1 = new Client("localhost", 1500, "BeefyBoi");
		Client client2 = new Client("localhost", 1500, "BlockchainBuster");
		
		client1.start();
		Block bl = new Block("Yeet", "Yeet");
		
		try {
			byte index = 0;
			System.out.println("Attemping to send a file...");
			client1.send(index, bl.getData());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Sent yeet block, " + bl.hash);
		/*try {
			Blockchain.sendFile();
		} catcnew URL(""h (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		
		//add our blocks to the playerChain ArrayList:
		
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
		
		//System.out.println(Blockchain.toJson());
		
	}
}
