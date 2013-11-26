package breakout;

public enum State {
	PLAYING(true),
	PAUSED(false),
	OVER(false);

	Boolean active;

	State(boolean a) {
		active = a;
	}

	Boolean isActive() {
		return active;
	}
}
