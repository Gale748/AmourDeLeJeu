package adlj.main.gui;

import java.awt.Color;
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
import adlj.main.entity.powerups.Boom;
import adlj.main.entity.powerups.Shield;
import adlj.main.listeners.KListener;
import adlj.main.listeners.MListener;
import adlj.main.listeners.MMListener;
import adlj.main.threads.BoomAnimation;
import adlj.main.threads.ElapsedTime;
import adlj.main.threads.EnemySpawner;
import adlj.main.threads.LevelUpAnimation;

@SuppressWarnings("serial")
public class Frame extends JFrame{
	//ints
	public static int WIDTH = 800;
	public static int HEIGHT = 600;
	static int FPS= 0;
	static int BufferedFPS = 0;
	public static int SCORE = 0;
	static int tehSCORE = 200;
	public static int KILLED = 0;
	public static int LEVEL = 1;
	private static int LASTLEVEL = 1;
	
	public static Frame GameFrame;
	
	String TITLE = "PsychoticChildren";
	String INFO = "";
	
	static long startTime = System.currentTimeMillis();
	static long previousTime = startTime;
	
	static Color INFO_COLOR = Color.black;
	
	static Image img;
	
	public static boolean showInfo = true;
	public static boolean paused = false;
	
	//Import Images
	static Image levelup_Image[] = {ImageLoader.getImageFrom("levelup.png"), ImageLoader.getImageFrom("levelupinvert.png")};
	static Image projectile_Image = ImageLoader.getImageFrom("Projectile.png");
	static Image enemy_Image[] = {ImageLoader.getImageFrom("Enemy1.png"),ImageLoader.getImageFrom("Enemy2.png")};
	static Image enemyProjectile_Image[] = {ImageLoader.getImageFrom("Enemy1Proj.png"),ImageLoader.getImageFrom("Enemy2Proj.png")};
	static Image background_Image = ImageLoader.getImageFrom("background.png");
	static Image ship_Image = ImageLoader.getImageFrom("SpaceShip.png");
	static Image shield_Image = ImageLoader.getImageFrom("Shield.png");
	static Image bomb_Image = ImageLoader.getImageFrom("BombImage.png");
	static Image bombAniRev_Image[] = {ImageLoader.getImageFrom("FadeoutBomb3.png"),ImageLoader.getImageFrom("FadeoutBomb2.png"),ImageLoader.getImageFrom("FadeoutBomb1.png"),ImageLoader.getImageFrom("FadeoutBomb0.png")};
	static Image bombAni_Image[] = {ImageLoader.getImageFrom("FadeoutBomb0.png"),ImageLoader.getImageFrom("FadeoutBomb1.png"),ImageLoader.getImageFrom("FadeoutBomb2.png"),ImageLoader.getImageFrom("FadeoutBomb3.png")};
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
		addKeyListener(new KListener());
	}
	
	public static void main(String[] args){
		//Initialize Frame
		init();
	}
	private static void setBlankCursor(){
		BufferedImage cursorImg = new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB);
		Cursor blankCursor = Toolkit.getDefaultToolkit().createCustomCursor(
		    cursorImg, new Point(0, 0), "blank cursor");
		GameFrame.getContentPane().setCursor(blankCursor);
	}
	public static void init(){
		GameFrame = new Frame();
		setBlankCursor();
		//Paint Loop Thread
			new Thread(){
				public void run(){
					try{
						while(true){
							if(System.currentTimeMillis() - previousTime > 15){
								GameFrame.repaint();
								previousTime=System.currentTimeMillis();
							}else{
								Thread.sleep(1);
							}
						}
					}catch(Exception e){
						e.printStackTrace();
					}
				}
			}.start();
		//FPS Thread
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
		//Initialize
		new PlayerShip(WIDTH/2-25,HEIGHT*5/6-25,50,50);
		new Thread(){
			public void run(){
				try{
					while(true){
						LEVEL = (int) (Math.round(Math.log(KILLED/2)/Math.log(2))+1);
						if(LEVEL > LASTLEVEL){
							onLevelUp();
							LASTLEVEL = LEVEL;
							new BoomAnimation().start();
						}
						Thread.sleep(100);
					}
				}catch(Exception e){
					e.printStackTrace();
				}
			}
		}.start();
		new ElapsedTime().start();
	}
	public static void onPauseButton(){
		if(paused){
			paused = false;
		}else{
			paused = true;
		}
	}
	public static void onLevelUp(){
		for(Enemy e: Enemy.enemies){
			e.destroy();
			Frame.addScore(10);
		}
		for(EnemyProjectile ep: EnemyProjectile.projectiles){
			ep.destroy();
		}
		for(Boom b: Boom.booms){
			b.destroy();
		}
		new LevelUpAnimation().start();
	}
	public static void addScore(int i){
		//Add to Score
			SCORE+=i;
		//Add to "tehSCORE"
			tehSCORE -= i;
			if(tehSCORE <= 0){
				tehSCORE = 200 + tehSCORE;
				PlayerShip.LIVES++;
			}
	}
	public void paint(Graphics g){
		img = createImage(WIDTH,HEIGHT);
		paintComponent(img.getGraphics());
		g.drawImage(img, 0, 0,WIDTH,HEIGHT, this);
		FPS++;
	}
	private void paintComponent(Graphics g){
		//Draw Background Image
			//g.drawImage(background_Image, 0,0, WIDTH, HEIGHT,this);
			g.setColor(Color.LIGHT_GRAY);
			g.fillRect(0, 0, WIDTH, HEIGHT);
		//Shield Powerups
			for(Shield s: Shield.shields){
				g.drawImage(shield_Image,s.x, s.y, s.width, s.height,this);
			}
		//Boom Powerups
			for(Boom b: Boom.booms){
				g.drawImage(bomb_Image,b.x, b.y, b.width, b.height,this);
			}
		//Draw Info String
			if(showInfo){
				g.setColor(INFO_COLOR);
				INFO = "FPS: " + BufferedFPS + " Score: " + SCORE + " Lives: " + PlayerShip.LIVES + " Enemies Killed: "+ KILLED +" Level: " + LEVEL + " Time Elapsed: " + ElapsedTime.elapsedtime +" seconds";
				g.drawString(INFO,5 , 590);
			}
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
			if(PlayerShip.SHIELDED)
				g.drawImage(shield_Image,(int)PlayerShip.x-4, (int)PlayerShip.y-4, PlayerShip.width+8, PlayerShip.height+8,this);
		//BoomAniRev
			if(BoomAnimation.animating){
				g.drawImage(bombAniRev_Image[BoomAnimation.state], 0, 0, WIDTH, HEIGHT, this);
			}
		//BoomAni
		if(BoomAnimation.animating){
			g.drawImage(bombAni_Image[BoomAnimation.state], 0, 0, WIDTH, HEIGHT, this);
			}
		//LevelUpAni
		if(LevelUpAnimation.animating){
			g.drawImage(levelup_Image[LevelUpAnimation.state], 200, 100, 400, 100, this);
		}
	}
}
