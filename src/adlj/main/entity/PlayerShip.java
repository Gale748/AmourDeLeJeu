package adlj.main.entity;

import javax.swing.JOptionPane;

import adlj.main.gui.Frame;
import adlj.main.threads.EnemySpawner;

public class PlayerShip {
	public static double x, y;
	public static int width, height;
	public static int LIVES = 3;
	public static boolean SHIELDED = false;
	public PlayerShip(double x, double y, int w, int h){
		this.x = x;
		this.y = y;
		this.width = w;
		this.height = h;
	}
	public static void move(double dx, double dy){
		x += dx;
		y += dy;
	}
	public static void removeLife(){
		LIVES--;
		if(LIVES < 0){
			Frame.GameFrame.dispose();
			EnemySpawner.alive = false;
			for(Enemy e: Enemy.enemies){
				e.destroy();
			}
			for(EnemyProjectile ep: EnemyProjectile.projectiles){
				ep.destroy();
			}
			JOptionPane.showMessageDialog(null,"Score: " +Frame.SCORE);
			System.exit(0);
		}
	}
	public static void onCollision(){
		if(SHIELDED){
			SHIELDED = false;
		}else{
			removeLife();
		}
	}
}
