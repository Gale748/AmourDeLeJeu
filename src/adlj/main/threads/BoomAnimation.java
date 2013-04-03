package adlj.main.threads;

public class BoomAnimation extends Thread{
	public static boolean animating = false;
	public static int state = 0;
	public void run(){
		try{
			animating = true;
			while(state <= 2){
				Thread.sleep(100);
				state++;
			}
			Thread.sleep(100);
			animating = false;
			state = 0;
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}
