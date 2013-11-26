package breakout;

import javax.swing.JFrame;

public class Main extends JFrame {
	public Main() {
		add(new Game());
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(Constants.ROOM_WIDTH, Constants.ROOM_HEIGHT);
		setLocationRelativeTo(null);
		setTitle("Breakout by Hussain Jasim");
		setResizable(false);
		setVisible(true);
	}

	public static void main(String[] args) {
		new Main();
	}
}
