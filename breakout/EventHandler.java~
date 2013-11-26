package breakout;

import java.util.HashMap;
import java.util.Map;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;

public class EventHandler implements KeyListener {
	private Map<Integer, Runnable> keyPressEvents;
	private Map<Integer, Runnable> keyReleaseEvents;
	private Map<Integer, Runnable> keyTypeEvents;

	public void registerKeyPressedEvent(int code, Runnable dispatch) {
		keyPressEvents.put(code, dispatch);
	}

	public void registerKeyReleasedEvent(int code, Runnable dispatch) {
		keyReleaseEvents.put(code, dispatch);
	}

	public void registerKeyTypedEvent(int code, Runnable dispatch) {
		keyTypeEvents.put(code, dispatch);
	}

	private void dispatch(Integer code, Map register) {
		if(!register.containsKey(code)) {
			return;
		}
		((Runnable)register.get(code)).run();
	}

	public void keyPressed(KeyEvent e) {
		dispatch(e.getKeyCode(), keyPressEvents);
	}

	public void keyReleased(KeyEvent e) {
		dispatch(e.getKeyCode(), keyReleaseEvents);
	}


	public void keyTyped(KeyEvent e) {
		dispatch(e.getKeyCode(), keyTypeEvents);
	}

	public EventHandler() {
		keyPressEvents = new HashMap<Integer, Runnable>();
		keyReleaseEvents = new HashMap<Integer, Runnable>();
		keyTypeEvents = new HashMap<Integer, Runnable>();
	}
}

