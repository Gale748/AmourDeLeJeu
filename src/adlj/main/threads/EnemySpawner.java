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
				new Enemy(rand.nextInt(700) + 50, -50,50,50,0,0.4);
				Thread.sleep(1000);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}
