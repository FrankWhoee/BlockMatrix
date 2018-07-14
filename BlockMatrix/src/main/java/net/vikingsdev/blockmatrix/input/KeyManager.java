package net.vikingsdev.blockmatrix.input; 
 
import java.awt.event.KeyEvent; 
import java.awt.event.KeyListener; 
 
public class KeyManager implements KeyListener { 
  private boolean[] key, justPressed, cantPress; 
   
  public KeyManager() { 
    key = new boolean[256]; 
    justPressed = new boolean[key.length]; 
    cantPress = new boolean[key.length]; 
  } 
   
  public void tick() { 
    for(int i = 0; i < justPressed.length; i++) { 
      if(justPressed[i]) { 
        justPressed[i] = false; 
        cantPress[i] = true; 
      }else if(cantPress[i] && !key[i]) cantPress[i] = false; 
       
      if(!cantPress[i] && key[i]) justPressed[i] = true; 
    } 
  } 
   
  //detecting keyboard activity 
   
  @Override 
  public void keyPressed(KeyEvent e) { 
    key[e.getKeyCode()] = true; 
  } 
 
  @Override 
  public void keyReleased(KeyEvent e) { 
    key[e.getKeyCode()] = false; 
  } 
 
  //accessors and mutators
   
  public boolean getKey(int value) { 
    if(value < 0 || value >= key.length) return false; 
    return key[value]; 
  } 
   
  public boolean getJustPressed(int value) { 
    if(value < 0 || value >= key.length) return false; 
    else return justPressed[value]; 
  }
  
  //unused methods
   
  public void keyTyped(KeyEvent e) {} 
} 