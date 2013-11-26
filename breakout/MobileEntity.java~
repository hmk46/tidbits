package breakout;

public class MobileEntity extends Entity {
	protected int dx, dy;
	protected boolean mobile;
	protected float moveMetre;
	protected float speed;

	public int getDX() { return dx; }
	public void setDX(int dx) { this.dx = dx; }
	public int getDY() { return dy; }
	public void setDY(int dy) { this.dy = dy; }
	public Boolean isMobile() { return mobile; }
	public void setMobile(Boolean mobility) { mobile = mobility; }
	public float getSpeed() { return speed; }
	public void setSpeed(float speed) { this.speed = speed; }
	public float getMoveMetre() { return moveMetre; }

	public void updateState() {
		if(!mobile) {
			return;
		}
		moveMetre += speed;
		if(moveMetre >= 1) {
			x += dx;
			y += dy;
			moveMetre %= 1;
		}
		//x = Math.max(x, 0);
		//x = Math.min(x, Constants.ROOM_WIDTH-image.getWidth(null));
		//y = Math.max(y, 0);
		//x = Math.min(y, Constants.ROOM_HEIGHT-image.getHeight(null));
	}

	public MobileEntity(int x, int y) {
		super(x, y);
		speed = moveMetre = 0;
		dx = dy = 0;
		mobile = false;
	}

	public MobileEntity(int x, int y, int dx, int dy, float speed) {
		super(x, y);
		mobile = true;
		this.dx = dx;
		this.dy = dy;
		this.speed = speed;
	}
}
