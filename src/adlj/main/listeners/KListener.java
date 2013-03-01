package adlj.main.listeners;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import adlj.main.entity.PlayerShip;

public class KListener implements KeyListener{
	public static boolean keys[] = new boolean[65536];
	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		keys[e.getKeyCode()] = true;
		//A
		if(e.getKeyCode() == KeyEvent.VK_A){
			new Thread(){
				public void run(){
					try{
						while(keys[KeyEvent.VK_A]){
							if(!keys[KeyEvent.VK_D])
								PlayerShip.move(-1.0, 0);
							Thread.sleep(2);
						}
					}catch(Exception e){
						e.printStackTrace();
					}
				}
			}.start();
		}
		//D
				if(e.getKeyCode() == KeyEvent.VK_D){
					new Thread(){
						public void run(){
							try{
								while(keys[KeyEvent.VK_D]){
									if(!keys[KeyEvent.VK_A])
										PlayerShip.move(1.0, 0);
									Thread.sleep(2);
								}
							}catch(Exception e){
								e.printStackTrace();
							}
						}
					}.start();
				}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		keys[e.getKeyCode()] = false;
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

}
