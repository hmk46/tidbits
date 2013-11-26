package breakout;

import java.util.HashMap;
import javax.swing.ImageIcon;
import java.awt.Image;
import java.util.HashMap;
import java.util.Map;

// static module for encapsulating resource management
// (such as preloading and caching images)
public class ResourceManager {
	private static Map<String, Image> imageCache;
	private static EventHandler eventHandler;
	private static Renderer renderer;

	public static boolean preloadImage(String key, String path) {
		if(imageCache == null) {
			imageCache = new HashMap<String, Image>();
		}
		if(imageCache.containsKey(key)) {
			return false;
		}
		ImageIcon ii = new ImageIcon(ResourceManager.class.getResource(path));
		imageCache.put(key, ii.getImage());
		return true;
	}

	public static Image getImage(String key) {
		return imageCache.get(key);
	}

	public static Renderer getRenderer() {
		if(renderer == null) {
			renderer = new Renderer();
		}
		return renderer;
	}

	public static EventHandler getEventHandler() {
		if(eventHandler == null) {
			eventHandler = new EventHandler();
		}
		return eventHandler;
	}
}
