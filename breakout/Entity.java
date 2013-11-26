package breakout;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;

public class Entity {
	Boolean visible, solid;
	int x, y;
	Image image;

	public Boolean isVisible() { return visible; }
	public void setVisible(Boolean visibility) { visible = visibility; }
	public Boolean isSolid() { return solid; }
	public void setSolid(Boolean solidity) { solid = solidity; }
	public int getX() { return x; }
	public void setX(int x) { this.x = x; }
	public int getY() { return y; }
	public void setY() { this.y = y; }
	public Image getImage() { return image; }
	public void setImage(Image image) { this.image = image; }
	public void updateState() { }

	public Boolean checkCollision(Sprite other) {
		if(getRect().intersects(other.getRect())) {
			return true;
		}
		return false;
	}

	public void collideWith(Sprite other) {
//		x = other.getX()+(other.getImage().getWidth(null)+Constants.COLLISION_OFFSET)*((int)Math.signum(x-other.getX()));
//		y = other.getY()+(other.getImage().getHeight(null)+Constants.COLLISION_OFFSET)*((int)Math.signum(y-other.getY()));
	}

	public Rectangle getRect() {
		return new Rectangle(x, y, image.getWidth(null), image.getHeight(null));
	}

	public void render(Graphics ctx) {
		ctx.drawImage(image, x, y, null);
	}

	public Entity(int x, int y) {
		this.x = x;
		this.y = y;
		visible = true;
		solid = true;
	}
}
