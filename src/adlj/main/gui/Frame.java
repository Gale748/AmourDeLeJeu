package adlj.main.gui;

import java.awt.Graphics;
import java.awt.Image;

import javax.swing.JFrame;

import adlj.main.entity.PlayerShip;
import adlj.main.entity.Projectile;
import adlj.main.listeners.MListener;
import adlj.main.listeners.MMListener;

public class Frame extends JFrame{
	static int WIDTH = 800;
	static int HEIGHT = 600;
	static Image img;
	public static Frame GameFrame;
	String TITLE = "YoloSwaggerJacker420XxNoMercyxX";
	static long startTime = System.currentTimeMillis();
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
		init();
	}
	public static void init(){
		GameFrame = new Frame();
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
		new PlayerShip(WIDTH/2-25,HEIGHT*5/6-25,50,50);
	}
	public void paint(Graphics g){
		img = createImage(WIDTH,HEIGHT);
		paintComponent(img.getGraphics());
		g.drawImage(img, 0, 0,WIDTH,HEIGHT, this);
	}
	private void paintComponent(Graphics g){
		for(Projectile p: Projectile.projectiles){
			g.fillRect((int)p.x, (int)p.y, p.width, p.height);
		}
		g.fillRect((int)PlayerShip.x, (int)PlayerShip.y, PlayerShip.width, PlayerShip.height);
	}
}
