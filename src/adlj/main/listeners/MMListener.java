package adlj.main.listeners;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

import adlj.main.entity.PlayerShip;
import adlj.main.gui.Frame;

public class MMListener implements MouseMotionListener{
	public static int x, y;
	@Override
	public void mouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub
		MMListener.x = e.getX();
		MMListener.y = e.getY();
		if(!Frame.paused){
		if(!(KListener.keys[KeyEvent.VK_A] || KListener.keys[KeyEvent.VK_D])){
			PlayerShip.x = x - PlayerShip.width/2;
			PlayerShip.y = y - PlayerShip.height/2;
		}}
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub
		MMListener.x = e.getX();
		MMListener.y = e.getY();
		if(!Frame.paused){
		if(!(KListener.keys[KeyEvent.VK_A] || KListener.keys[KeyEvent.VK_D])){
			PlayerShip.x = x - PlayerShip.width/2;
			PlayerShip.y = y - PlayerShip.height/2;
		}}
	}
	
}
