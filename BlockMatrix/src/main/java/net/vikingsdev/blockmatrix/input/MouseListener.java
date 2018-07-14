package net.vikingsdev.blockmatrix.input; 
 
import java.awt.Point; 
import java.awt.event.MouseEvent; 
import java.awt.event.MouseListener; 
import java.awt.event.MouseMotionListener; 
import java.awt.event.MouseWheelEvent; 
import java.awt.event.MouseWheelListener; 
 
import net.vikingsdev.blockmatrix.ui.UIManager; 
 
public class MouseManager implements MouseListener, MouseMotionListener, MouseWheelListener { 
  private UIManager uim; 
   
  private boolean left, right; 
  private Point location; 
   
  public MouseManager() { 
    location = new Point(); 
  } 
   
  //tracking motion 
   
  @Override 
  public void mouseDragged(MouseEvent e) { 
    location = new Point(e.getX(), e.getY()); 
     
    if(uim != null) uim.onMouseMove(e); 
  } 
 
  @Override 
  public void mouseMoved(MouseEvent e) { 
    location = new Point(e.getX(), e.getY()); 
     
    if(uim != null) uim.onMouseMove(e); 
  } 
   
  //detecting mouse interactions 
   
  @Override 
  public void mousePressed(MouseEvent e) { 
    if(e.getButton() == MouseEvent.BUTTON1) left = true; 
    if(e.getButton() == MouseEvent.BUTTON3) right = true; 
     
    if(uim != null) uim.onMousePress(e); 
  } 
 
  @Override 
  public void mouseReleased(MouseEvent e) { 
    if(e.getButton() == MouseEvent.BUTTON1) left = false; 
    if(e.getButton() == MouseEvent.BUTTON3) right = false; 
     
    if(uim != null) uim.onMouseRelease(e); 
  } 
   
  @Override 
  public void mouseWheelMoved(MouseWheelEvent e) { 
    //unused for now - will be useful later 
  } 
   
  //accessors and mutators 
   
  public void setUIM(UIManager uim) { 
    this.uim = uim; 
  } 
   
  public boolean getLeft() { 
    return left; 
  } 
 
  public boolean getRight() { 
    return right; 
  } 
 
  public Point getLocation() { 
    return location; 
  } 
   
  //unused methods 
   
  public void mouseClicked(MouseEvent e) {} 
  public void mouseEntered(MouseEvent e) {} 
  public void mouseExited(MouseEvent e) {} 
} 