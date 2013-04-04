package adlj.main.threads;

import java.util.Random;

import adlj.main.entity.Enemy;
import adlj.main.gui.Frame;

public class EnemySpawner extends Thread{
	Random rand = new Random();
	public static boolean alive = true;
	public void run(){
		try{
			Thread.sleep(3000);
			while(alive){
				//Create Enemy
				if(alive){
				int type = rand.nextInt(2);
				new Enemy(rand.nextInt(700-10*Frame.LEVEL) + 50, -50,50,50,0,Enemy.movement_speed[type],type);
				}
				Thread.sleep(1000);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}
