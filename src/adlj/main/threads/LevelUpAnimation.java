package adlj.main.threads;

public class LevelUpAnimation extends Thread{
	public static boolean animating = false;
	public static int state = 0;
	public void run(){
		try{
			animating = true;
			for(int i = 0; i < 2; i++){
				state = 0;
				Thread.sleep(100);
				state = 1;
				Thread.sleep(100);
			}
			animating = false;
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}
