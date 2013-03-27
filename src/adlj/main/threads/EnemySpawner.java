package adlj.main.threads;

import java.util.Random;

import adlj.main.entity.Enemy;

public class EnemySpawner extends Thread{
	Random rand = new Random();
	public void run(){
		try{
			Thread.sleep(3000);
			while(true){
				//Create Enemy
				int type = rand.nextInt(2);
				new Enemy(rand.nextInt(700) + 50, -50,50,50,0,Enemy.movement_speed[type],type);
				Thread.sleep(1000);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}
