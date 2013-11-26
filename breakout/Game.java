package breakout;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Color;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;

import javax.swing.JPanel;


public class Game extends JPanel implements Runnable {
	private Thread animator;
	private Renderer renderer;
	private EventHandler eventHandler;
	private Image ball, brick, paddle;
	public static State gameState;

	public void togglePaused() {
		if(gameState == State.PAUSED) {
			gameState = State.PLAYING;
		} else {
			gameState = State.PAUSED;
		}
	}

	public Game() {
		renderer = ResourceManager.getRenderer();
		eventHandler = ResourceManager.getEventHandler();
		setBackground(Color.BLACK);
		setDoubleBuffered(true);
		ResourceManager.preloadImage("paddle", "paddle.png");
		ResourceManager.preloadImage("ball", "ball.png");
		ResourceManager.preloadImage("brick", "brick.png");
		addKeyListener(eventHandler);
		ball = ResourceManager.getImage("ball");
		brick = ResourceManager.getImage("brick");
		paddle = ResourceManager.getImage("paddle");
		for(int i = 0; i < Constants.COLUMNS_OF_BRICKS; i++) {
			for(int j = 0; j < Constants.BRICKS_PER_ROW; j++) {
				renderer.addSprite(new Brick(j*brick.getWidth(null), i*brick.getHeight(null)));
			}
		}
		renderer.addSprite(new Paddle(300, 400)); 
		renderer.addSprite(new Ball(350, 250));
		gameState = State.PLAYING;
	}

	public void addNotify() {
		super.addNotify();
		animator = new Thread(this);
		animator.start();
		eventHandler.registerKeyReleasedEvent(KeyEvent.VK_P, new Runnable() {
			public void run() {
				togglePaused();
			}
		});
		setFocusable(true);
		requestFocusInWindow();
	}

	public void paint(Graphics g) {
		super.paint(g);
		Graphics2D g2d = (Graphics2D)g;
		renderer.render(g2d);
		Toolkit.getDefaultToolkit().sync();
		g.dispose();
	}

	public void cycle() {
		if(gameState.isActive()) {
			renderer.updateState();
		}
	}

	public void run() {
		long prevTime, diffTime, sleep;
		prevTime = System.currentTimeMillis();
		while (gameState != State.OVER) {
			cycle();
			repaint();
			diffTime		 = System.currentTimeMillis() - prevTime;
			sleep = Constants.DELAY - diffTime;
			if (sleep < 0)
				sleep = 2;
			try {
				Thread.sleep(sleep);
			} catch (InterruptedException e) {
				System.out.println("interrupted");
			}
			prevTime = System.currentTimeMillis();
		}
	}
}
