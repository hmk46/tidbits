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

import java.util.HashSet;

// This class contains the main orbit tracing functionality
public class BuddhabrotTracer {
	private double minR, maxR, minI, maxI, scale;
	private int traceMax;
	private int[][] pixelChannels;

	public BuddhabrotTracer(double minR, double maxR, double minI, double maxI, double scale) {
		this.minR = minR;
		this.maxR = maxR;
		this.minI = minI;
		this.maxI = maxI;
		this.scale = scale;
		initialize();
	}

	public void initialize() {
		traceMax = 0;
		pixelChannels = new int[rToX(maxR)][iToY(minI)];
		for(int x[] : pixelChannels) {
			for(int y = 0; y < x.length; y++) {
				x[y] = 0;
			}
		}
	}

	public double xToR(int x) {
		return Math.max(minR + x*scale, minR);
	}

	public int rToX(double r) {
		return (int)Math.round((r - minR) / scale);
	}

	public double yToI(int y) {
		return Math.max(maxI - y * scale, minI);
	}

	public int iToY(double i) {
		return (int)Math.round((maxI - i) / scale);
	}

	public int getRedValue(int x, int y) {
		return (int)(255 * Math.sqrt(pixelChannels[x][y]) / Math.sqrt(traceMax));
	}
	public int getGreenValue(int x, int y) {
		return getRedValue(x, y);
	}
	public int getBlueValue(int x, int y) {
		return getRedValue(x, y);
	}

	public void iteratePixel(int x, int y, int threshold) {
		iteratePoint(xToR(x), yToI(y), threshold);
	}

	public void iteratePoint(double cr, double ci, int threshold) {
		HashSet<int[]> trace = new HashSet<int[]>();
		int[] point;
		boolean escape = false;
		double r, i, nr, ni;
		r = i = 0;
		for(int j = 0; j < threshold; j++) {
			nr = r * r - i * i + cr;
			ni = 2 * r * i + ci;
			if(!escape && nr * nr + ni * ni > 4) {
				escape = true;
				break;
			}
				point = new int[] { rToX(nr), iToY(ni) };
				if(trace.contains(point)) {
					break;
				}
				trace.add(point);
			r = nr;
			i = ni;
		}
		if(!escape) {
			return;
		}
		for(int[] j : trace) {
			if(j[0] >= pixelChannels.length || j[1] >= pixelChannels[0].length || j[0] < 0 || j[1] < 0) {
				continue;
			}
			pixelChannels[j[0]][j[1]]++;
			if(pixelChannels[j[0]][j[1]] > traceMax) {
				traceMax ++;
			}
		}
	}
}