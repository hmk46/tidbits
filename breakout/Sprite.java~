package breakout;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;

public interface Sprite {
	Boolean isVisible();
	void setVisible(Boolean visibility);
	Boolean isSolid();
	void setSolid(Boolean solidity);
	int getX();
	void setX(int x);
	int getY();
	void setY();
	Image getImage();
	void setImage(Image image);
	void updateState();
	Boolean checkCollision(Sprite other);
	Rectangle getRect();
	void collideWith(Sprite other);
	void render(Graphics ctx);
}
