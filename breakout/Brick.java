package breakout;

public class Brick extends Entity implements Sprite {
	private boolean destroyed;
	public void collideWith(Sprite other) {
		//destroyed = true;
	}

	public void updateState() {
		super.updateState();
		if(!destroyed) {
			return;
		}
		// ResourceManager.getRenderer().removeSprite(this);
		setVisible(false);
		setSolid(false);
	}

	public Brick(int x, int y) {
		super(x, y);
		image = ResourceManager.getImage("brick");
		destroyed = false;
	}
}
