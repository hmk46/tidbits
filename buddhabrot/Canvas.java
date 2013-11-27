/**
  * Don Mills CI
  * ICS4U H. Strelkovska
  *
  * Hussain Jasim #310372974
  *
  * ISU assignment
  * Buddhabrot and Nebulabrot
  *
  * This work is licensed under the Creative Commons Attribution-NonCommercial-ShareAlike 3.0 Unported License.
  * To view a copy of this license, visit
  * http://creativecommons.org/licenses/by-nc-sa/3.0/
  * or send a letter to
  * Creative Commons, 444 Castro Street, Suite 900, Mountain View, California, 94041, USA.
**/


package buddhabrot;

import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JPanel;
import javax.swing.JFrame;
import java.awt.event.KeyEvent;
import java.awt.event.KeyAdapter;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.Random;

public class Canvas extends JPanel {
	private BuddhabrotTracer mbt;
	private int width, height, iterations, threshold;
	private double minR, minI, maxR, maxI, scale;
	private BufferedImage pixels;

	public Canvas() {
		width = height = 625;
		minR = -0.5;
		minI = -1.3;
		scale = 0.004;
		maxR = minR + scale * width;
		maxI = minI + scale * height;
		mbt = new BuddhabrotTracer(minR, maxR, minI, maxI, scale);
		pixels = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		iterations = 25000;
		threshold = 2000;
		addKeyListener(new KeyHandler());
		renderBuddhabrot();
	}

	public void addNotify() {
		super.addNotify();
		setFocusable(true);
		requestFocusInWindow();
	}

	public double[] generateRandomComplex(double[] seed) {
		if((seed[0] == 0.0 && seed[1] == 0.0) || Math.random() >= 0.8) {
			return new double[] {
				((minR + maxR ) / 2.0) + (Math.random() * (maxR - minR) / 2.0),
				((minI + maxI ) / 2.0) + (Math.random() * (maxI - minI) / 2.0)
			};
		}
		double r1 = (1.0/scale) * 0.0001;
		double r2 = (1.0/scale) * 0.01;
		double fi = Math.random()*Math.PI*2;
		double r = r2 * Math.exp( -Math.log( r2 / r1 ) * Math.random());
		return new double[] { seed[0] + r * Math.cos(fi), seed[1] + r * Math.sin(fi) };
	}

	public void generateFractal(int iterations, int threshold) {
		double[] c = new double[] { 0.0, 0.0 };
		mbt.initialize();
		for(int i = 0; i < iterations; i++) {
			c = generateRandomComplex(c);
			mbt.iteratePoint(c[0], c[1], threshold);
			mbt.iteratePoint(c[0], (minI + maxI) / 2.0 - c[1], threshold);
			mbt.iteratePoint((minR + maxR) / 2.0 - c[0], (minI + maxI) / 2.0 - c[1], threshold);
			mbt.iteratePoint((minR + maxR) / 2.0 - c[0], c[1], threshold);
		}
	}

	public void renderNebulabrot() {
		generateFractal(iterations, threshold);
		for(int x = 0; x < width; x++) {
			for(int y = 0; y < height; y++) {
				pixels.setRGB(x, y, mbt.getRedValue(x, y) <<16);
			}
		}
		generateFractal(iterations, threshold / 10);
		for(int x = 0; x < width; x++) {
			for(int y = 0; y < height; y++) {
				pixels.setRGB(x,y, pixels.getRGB(x, y) | (mbt.getGreenValue(x, y) <<8));
			}
		}
				generateFractal(iterations, threshold / 100);
		for(int x = 0; x < width; x++) {
			for(int y = 0; y < height; y++) {
				pixels.setRGB(x,y, pixels.getRGB(x, y) | mbt.getGreenValue(x, y));
			}
		}
	}

	public void renderBuddhabrot() {
		generateFractal(iterations, threshold);
		for(int x = 0; x < width; x++) {
			for(int y = 0; y < height; y++) {
				pixels.setRGB(x, y, (mbt.getRedValue(x, y) <<16) | (mbt.getGreenValue(x, y) <<8) | mbt.getBlueValue(x, y));
			}
		}
	}

	public void saveImage() {
		String filename = "buddhabrot" + System.currentTimeMillis() + ".png";
		try {
			File out = new File(filename);
			ImageIO.write(pixels, "png", out);
		} catch(IOException e) {
			System.out.println("There was an error opening the target file, " + filename + ", on the hard disk.");
			System.out.println(e.getMessage());
		}
	}

	public void paint(Graphics g) {
	super.paint(g);
		Graphics2D g2d = (Graphics2D)g;
		g2d.drawImage(pixels, 0, 0, null);
		g2d.drawString("iterations:" + iterations, 50, 650);
		g2d.drawString("Threshold: " + threshold, 300, 650);
		g2d.drawString("up/down arrows to change iterations, left/right arrows to change threshold, s to save, b for a Buddhabrot, n for a Nebulabrot", 50, 700);
	}

	public static void main(String[] args) {
		Canvas c = new Canvas();
		JFrame fr = new JFrame();
		fr.add(c);
		fr.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		fr.setSize(625, 750);
		fr.setTitle("buddhabrot");
		fr.setVisible(true);
	}

	class KeyHandler extends KeyAdapter {
		public void keyReleased(KeyEvent e) {
			switch(e.getKeyCode()) {
			case KeyEvent.VK_UP:
				iterations += 5000;
				repaint();
				break;
		case KeyEvent.VK_DOWN:
			iterations -= 5000;
			repaint();
			break;
		case KeyEvent.VK_LEFT:
		threshold /= 10;
		repaint();
			break;
			case KeyEvent.VK_RIGHT:
			threshold*= 10;
			repaint();
				break;
		case KeyEvent.VK_S:
				saveImage();
				break;
			case KeyEvent.VK_B:
				renderBuddhabrot();
				repaint();
				break;
			case KeyEvent.VK_N:
				renderNebulabrot();
				repaint();
				break;
			default:
				break;
			}
		}
	}
}