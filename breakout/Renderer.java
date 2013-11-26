package breakout;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

public class Renderer {
	private List<Sprite> sprites;

	public Boolean addSprite(Sprite sprite) {
		return sprites.add(sprite);
	}

	public Boolean removeSprite(Sprite sprite) {
		return sprites.remove(sprite);
	}

	public void checkCollisions() {
		for(int i = 0; i < sprites.size()-1; i++) {
			for(int j = i+1; j < sprites.size(); j++) {
				if(sprites.get(i).isSolid() && sprites.get(j).isSolid()) {
					if(sprites.get(i).checkCollision(sprites.get(j))) {
						sprites.get(i).collideWith(sprites.get(j));
						sprites.get(j).collideWith(sprites.get(i));
					}
				}
			}
		}
	}

	public void updateState() {
		for(Sprite s : sprites) {
			s.updateState();
		}
		checkCollisions();
	}

	public void render(Graphics ctx) {
		for(Sprite s : sprites) {
			if(s.isVisible()) {
				s.render(ctx);
			}
		}
	}

	public Renderer() {
		sprites = new ArrayList<Sprite>();
	}
}
