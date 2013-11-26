package breakout;

import java.awt.Image;

public class Ball extends MobileEntity implements Sprite {
	public void collideWith(Sprite other) {
		super.collideWith(other);
		int hOffset = other.getX()-x;
		if(y > other.getY()) {
			dy = 1;
		} else {
			dy = -1;
		}
//		if(Math.abs(hOffset) < image.getWidth(null)/2) {
//			dx = (int)Math.signum(hOffset);
//		}
	}

	public void updateState() {
		super.updateState();
		if(x <= 0 || x+image.getWidth(null) >= Constants.ROOM_WIDTH) {
			dx *= -1;
			dy *= -1;
		}
		if(y >= Constants.ROOM_HEIGHT-image.getHeight(null)) {
			Game.gameState = State.OVER;
		}
	}

	public Ball(int x, int y) {
		super(x, y);
		image = ResourceManager.getImage("ball");
		dy = 1;
		dx = 0;
		speed = 3.0f;
		mobile = true;
	}
}
