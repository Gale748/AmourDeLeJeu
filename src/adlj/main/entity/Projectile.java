package adlj.main.entity;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class Projectile {
	public double x, y;
	public int width, height;
	public double dx, dy;
	public static List<Projectile> projectiles = new CopyOnWriteArrayList<Projectile>();
	public Projectile(double x, double y, int w, int h, double dx, double dy){
		this.x = x;
		this.y = y;
		this.width = w;
		this.height = h;
		this.dx = dx;
		this.dy = dy;
		final Projectile p = this;
		projectiles.add(p);
		new Thread(){
			public void run(){
				try{
					while(p.y + p.height > 0){
						p.y += p.dy;
						Thread.sleep(2);
					}
				}catch(Exception e){
					e.printStackTrace();
				}
				projectiles.remove(p);
			}
		}.start();
	}
}
