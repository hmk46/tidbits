package breakout;

import java.awt.event.KeyEvent;

public class Paddle extends MobileEntity implements Sprite {
	public Paddle(int x, int y) {
		super(x, y);
		image = ResourceManager.getImage("paddle");
		speed = 12000.0f/Constants.DELAY;
		ResourceManager.getEventHandler().registerKeyPressedEvent(KeyEvent.VK_RIGHT, new Runnable() {
			public void run() {
				setMobile(true);
				setDX(1);
			}
		});
		ResourceManager.getEventHandler().registerKeyReleasedEvent(KeyEvent.VK_RIGHT, new Runnable() {
			public void run() {
				setMobile(false);
				setDX(0);
			}
		});
		ResourceManager.getEventHandler().registerKeyPressedEvent(KeyEvent.VK_LEFT, new Runnable() {
			public void run() {
				setMobile(true);
				setDX(-1);
			}
		});
		ResourceManager.getEventHandler().registerKeyReleasedEvent(KeyEvent.VK_LEFT, new Runnable() {
			public void run() {
				setMobile(false);
				setDX(0);
			}
		});
	}
}
