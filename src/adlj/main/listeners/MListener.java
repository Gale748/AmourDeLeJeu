package adlj.main.listeners;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import adlj.main.entity.Projectile;

public class MListener implements MouseListener{
	public static boolean pressed = false;
	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		pressed = true;
		new Thread(){
			public void run(){
				try{
					while(pressed){
						new Projectile(MMListener.x-5, MMListener.y-15, 10, 30, 0, -2);
						for(int i = 0; i < 20; i++){
							Thread.sleep(10);
							if(!pressed)
								this.join();
						}
					}
				}catch(Exception e){
					e.printStackTrace();
				}
			}
		}.start();
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		pressed = false;
	}

}
