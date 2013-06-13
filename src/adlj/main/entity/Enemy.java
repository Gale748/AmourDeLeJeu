package adlj.main.entity;

import java.awt.Rectangle;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;

import adlj.main.entity.powerups.Boom;
import adlj.main.entity.powerups.Shield;
import adlj.main.gui.Frame;

public class Enemy {
	/*
	 * Enemy 1 = Green 
	 * Enemy 2 = Purple
	 */
	static int projectile_wait_time[] = {700,1100};
	public double x, y,dx,dy;
	public static double movement_speed[] = {0.2,0.4};
	public int width, height;
	public int type;
	public boolean alive = true;
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
					while(e.y < Frame.HEIGHT){
						
						e.y += e.dy;
						r.setBounds((int)e.x,(int)e.y,e.width,e.height);
						for(Projectile p: Projectile.projectiles){
							pr.setBounds((int)p.x,(int)p.y,p.width,p.height);
							if(r.intersects(pr) && alive){
								enemies.remove(e);
								Projectile.projectiles.remove(p);
								onProjectileCollision();
								e.alive = false;
								this.join();
							}
						}
						if((r.intersects(PlayerShip.x,PlayerShip.y,PlayerShip.width,PlayerShip.height)) && alive){
							enemies.remove(e);
							onPlayerCollision();
							e.alive = false;
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
					while(alive){
						if(alive)
							new EnemyProjectile(e.x + e.width/2 - 5, e.y+10, 10, 20,0, 1, type);
						Thread.sleep(projectile_wait_time[type]);			
					}
				}catch(Exception e){
					e.printStackTrace();
			}
		}
		}.start();
	}
	public void destroy(){
		alive = false;
		enemies.remove(this);
	}
	private void onPlayerCollision(){
		PlayerShip.onCollision();
	}private void onProjectileCollision(){
		int i = new Random().nextInt(100);
		if (i > 70){
			new Shield((int)x+width/2,(int)y+height/2);
		}else if(i > 60){
			new Boom((int)x+width/2,(int)y+height/2);
		}
		Frame.KILLED +=1;
		Frame.addScore(10);
	}
}
