package adlj.main.gui;

import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;

import adlj.main.entity.Enemy;
import adlj.main.entity.EnemyProjectile;
import adlj.main.entity.PlayerShip;
import adlj.main.entity.Projectile;
import adlj.main.listeners.MListener;
import adlj.main.listeners.MMListener;
import adlj.main.threads.EnemySpawner;

public class Frame extends JFrame{
	//ints
	public static int WIDTH = 800;
	public static int HEIGHT = 600;
	public static int LIVES = 3;
	static int FPS= 0;
	static int BufferedFPS = 0;
	public static int SCORE = 0;
	static int tehSCORE = 200;
	
	public static Frame GameFrame;
	
	String TITLE = "YoloSwaggerJacker420XxNoMercyxX";
	
	static long startTime = System.currentTimeMillis();
	
	static Image img;
	
	//Import Images
	static Image projectile_Image = ImageLoader.getImageFrom("Projectile.png");
	static Image enemy_Image[] = {ImageLoader.getImageFrom("Enemy1.png"),ImageLoader.getImageFrom("Enemy2.png")};
	static Image enemyProjectile_Image[] = {ImageLoader.getImageFrom("Enemy1Proj.png"),ImageLoader.getImageFrom("Enemy2Proj.png")};
	static Image background_Image = ImageLoader.getImageFrom("background.png");
	static Image ship_Image = ImageLoader.getImageFrom("SpaceShip.png");
	
	public Frame(){
		setSize(WIDTH,HEIGHT);
		setResizable(false);
		setVisible(true);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setTitle(TITLE);
		//Listeners
		addMouseMotionListener(new MMListener());
		addMouseListener(new MListener());
		//addKeyListener(new KListener());
	}
	
	public static void main(String[] args){
		//Initialize Frame
		init();
	}
	
	public static void init(){
		GameFrame = new Frame();
		BufferedImage cursorImg = new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB);
		Cursor blankCursor = Toolkit.getDefaultToolkit().createCustomCursor(
		    cursorImg, new Point(0, 0), "blank cursor");
		GameFrame.getContentPane().setCursor(blankCursor);
		//Paint Thread
		new Thread(){
			public void run(){
				try{
					while(true){
						if(System.currentTimeMillis() - startTime > 15){
							GameFrame.repaint();
							startTime=System.currentTimeMillis();
						}else{
							Thread.sleep(1);
						}
					}
				}catch(Exception e){
					e.printStackTrace();
				}
			}
		}.start();
		new Thread(){
			public void run(){
				try{
					while(true){
						Thread.sleep(1000);
						BufferedFPS = FPS;
						FPS = 0;
					}
				}catch(Exception e){
					e.printStackTrace();
				}
			}
		}.start();
		new EnemySpawner().start();
		new PlayerShip(WIDTH/2-25,HEIGHT*5/6-25,50,50);
	}
	
	public static void addScore(int i){
		//Add to Score
		SCORE+=i;
		//Add to "tehSCORE"
		tehSCORE -= i;
		if(tehSCORE <= 0){
			tehSCORE = 200 + tehSCORE;
			LIVES++;
		}
	}
	public void paint(Graphics g){
		img = createImage(WIDTH,HEIGHT);
		paintComponent(img.getGraphics());
		g.drawImage(img, 0, 0,WIDTH,HEIGHT, this);
		FPS++;
	}
	private void paintComponent(Graphics g){
		//background
		g.drawImage(background_Image, 0,0, WIDTH, HEIGHT,this);
		//FPS
		g.drawString("FPS: " + BufferedFPS + " Score: " + SCORE + " Lives: " + LIVES,5 , 590);
		//Projectiles
		for(Projectile p: Projectile.projectiles){
			g.drawImage(projectile_Image,(int)p.x, (int)p.y, p.width, p.height,this);
		}
		for(EnemyProjectile p: EnemyProjectile.projectiles){
			g.drawImage(enemyProjectile_Image[p.type],(int)p.x, (int)p.y, p.width, p.height,this);
		}
		//Enemies
		for(Enemy p: Enemy.enemies){
			g.drawImage(enemy_Image[p.type],(int)p.x, (int)p.y, p.width, p.height,this);
		}
		//Ship
		g.drawImage(ship_Image,(int)PlayerShip.x, (int)PlayerShip.y, PlayerShip.width, PlayerShip.height,this);
		
	}
}
