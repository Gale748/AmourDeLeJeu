package adlj.main.threads;

import adlj.main.gui.Frame;

public class ElapsedTime extends Thread{
	public static int elapsedtime = 0;
	public void run(){
		try{
			while(true){
				Thread.sleep(1000);
				if(!Frame.paused)
					elapsedtime++;
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}
