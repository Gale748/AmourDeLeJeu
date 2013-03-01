package adlj.main.listeners;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

import adlj.main.entity.PlayerShip;

public class MMListener implements MouseMotionListener{
	public static int x, y;
	@Override
	public void mouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub
		this.x = e.getX();
		this.y = e.getY();
		if(!(KListener.keys[KeyEvent.VK_A] || KListener.keys[KeyEvent.VK_D])){
			PlayerShip.x = x - PlayerShip.width/2;
			PlayerShip.y = y - PlayerShip.height/2;
		}
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub
		this.x = e.getX();
		this.y = e.getY();
		if(!(KListener.keys[KeyEvent.VK_A] || KListener.keys[KeyEvent.VK_D])){
			PlayerShip.x = x - PlayerShip.width/2;
			PlayerShip.y = y - PlayerShip.height/2;
		}
	}
	
}
