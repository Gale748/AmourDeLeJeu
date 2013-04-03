package adlj.main.entity;

import java.awt.Rectangle;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;

import adlj.main.entity.powerups.Shield;
import adlj.main.gui.Frame;

public class Enemy {
	/*
	 * Enemy 1 = Green 
	 * Enemy 2 = Purple
	 */
	static int projectile_wait_time[] = {800,1200};
	public double x, y,dx,dy;
	public static double movement_speed[] = {0.1,0.3};
	public int width, height;
	private boolean exists = true;
	public int type;
	public static List<Enemy> enemies = new CopyOnWriteArrayList<Enemy>();
	public Enemy(double x, double y, int w, int h, double dx, double dy, final int type){
		this.x= x ;
		this.y = y;
		this.width = w;
		this.height = h;
		this.dx = dx;
		this.dy = dy;
		this.type = type;
		final Enemy e = this;
		enemies.add(e);
		
		new Thread(){
			public void run(){
				try{	
					Rectangle r = new Rectangle();
					Rectangle pr= new Rectangle();
					Rectangle sr = new Rectangle();
					while(e.y < Frame.GameFrame.HEIGHT){
						
						e.y += e.dy;
						r.setBounds((int)e.x,(int)e.y,e.width,e.height);
						for(Projectile p: Projectile.projectiles){
							pr.setBounds((int)p.x,(int)p.y,p.width,p.height);
							if(r.intersects(pr)){
								enemies.remove(e);
								Projectile.projectiles.remove(p);
								onProjectileCollision();
								e.exists = false;
								this.join();
							}
						}
						if(r.intersects(PlayerShip.x,PlayerShip.y,PlayerShip.width,PlayerShip.height)){
							enemies.remove(e);
							onPlayerCollision();
							e.exists = false;
							this.join();
						}
						Thread.sleep(2);
					}
				}catch(Exception e){
					e.printStackTrace();
				}
			}
		}.start();
		new Thread(){
			public void run(){
				try{
					while(exists){
						Thread.sleep(projectile_wait_time[type]);
						if(exists)
						new EnemyProjectile(e.x + e.width/2 - 5, e.y+10, 10, 20,0, 1, type);
						
					}
				}catch(Exception e){
					e.printStackTrace();
			}
		}
		}.start();
	}
	private void onPlayerCollision(){
		PlayerShip.onCollision();
	}private void onProjectileCollision(){
		if ((new Random().nextInt(100))> 70){
			new Shield((int)x+width/2,(int)y+height/2);
		}
		Frame.KILLED +=1;
		Frame.addScore(10);
	}
}
