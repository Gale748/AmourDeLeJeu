package adlj.main.entity;

import java.awt.Rectangle;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import adlj.main.gui.Frame;

public class EnemyProjectile {
	public double x, y, dx, dy;
	public int width, height;
	public int type;
	public static List<EnemyProjectile> projectiles = new CopyOnWriteArrayList<EnemyProjectile>();
	public EnemyProjectile(final double x,final double y, final int w, final int h, double dx, double dy, int type){
		this.x = x;
		this.y = y;
		this.width = w;
		this.height = h;
		this.dx = dx;
		this.dy = dy;
		this.type = type;
		final EnemyProjectile e = this;
		projectiles.add(e);
		new Thread(){
			public void run(){
				try{	
					Rectangle r = new Rectangle();
					Rectangle pr= new Rectangle();
					while(e.y > 0){
						e.y += e.dy;
						r.setBounds((int)e.x,(int)e.y,w,h);
						if(r.intersects(PlayerShip.x,PlayerShip.y,PlayerShip.width,PlayerShip.height)){
							projectiles.remove(e);
							this.join();
						}
						Thread.sleep(2);
					}
				}catch(Exception e){
					e.printStackTrace();
				}
			}
		}.start();
	}
}
