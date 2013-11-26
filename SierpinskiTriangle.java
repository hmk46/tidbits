import java.awt.Graphics;
import javax.swing.JPanel;
import javax.swing.JFrame;
import java.awt.Color;
import java.awt.Graphics2D;

public class SierpinskiTriangle extends JPanel {
    int sx, sy, sl;
    
    public SierpinskiTriangle(int sx, int sy, int sl) {
	this.sx = sx;
	this.sy = sy;
	this.sl = sl;
	setBackground(Color.BLACK);
    }


    public void drawIteration(Graphics2D g2d, double ax, double ay, double bx, double by, double cx, double cy) {
	if(cx - ax < 6 || ay - by < 6 ) {
	    return;
	}
	double hax = (ax + bx) / 2 ;
	double hay = (ay + by) / 2;
	double hbx = (ax + cx) / 2;
	double hby = ay;
	double hcx = (bx + cx) / 2;
	double hcy = hay;
	g2d.fillPolygon(new int[] { (int)hax, (int)hbx, (int)hcx }, new int[] { (int)hay, (int)hby, (int)hcy }, 3);
	drawIteration(g2d, ax, ay, hax, hay, hbx, hby);
	drawIteration(g2d, hax, hay, bx, by, hcx, hcy);
	drawIteration(g2d, hbx, hby, hcx, hcy, cx, cy);
    }
    public void paint(Graphics g) {
	super.paint(g);
	Graphics2D g2d = (Graphics2D)g;
	double bx = (sx + sl) / 2;
	double by = sy - Math.sin(Math.toRadians(60)) * sl;
	g2d.setColor(Color.WHITE);
	g2d.fillPolygon(new int[] { (int)sx, (int)bx, (int)(sx + sl) }, new int[] { (int)sy, (int)by, (int)sy }, 3);
	g2d.setColor(Color.BLACK);
	drawIteration(g2d, sx, sy, bx, by, sx + sl, sy);
    }

    public static void main(String[] args) {
	JFrame f = new JFrame();
	f.add(new SierpinskiTriangle(0, 800, 800));
	f.setTitle("Sierpinski Triangle");
	f.setSize(800, 800);
	f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	f.setVisible(true);
    }
}