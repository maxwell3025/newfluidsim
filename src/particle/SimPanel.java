package particle;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.util.Arrays;

import javax.swing.JPanel;

import vectors.Point2D;

public class SimPanel extends JPanel implements MouseListener, MouseMotionListener, Runnable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	int screenheight;
	int screenwidth;
	int screenarea;
	int threadnum = 0;
	double diffusion = 0.25;
	boolean started = false;
	BufferedImage frame;
	boolean painted;
	int timemilis = 0;
	Cell[] fluid1;
	Cell[] fluid2;
	Cell[] buffer;
	Point2D Mouse = Point2D.Origin();
	boolean[] ispressed = new boolean[4];

	public SimPanel(int width, int height, int particlenum) {
		screenwidth = width;
		screenheight = height;
		screenarea = screenwidth * screenheight;
		fluid1 = new Cell[screenarea];
		fluid2 = new Cell[screenarea];
		setPreferredSize(new Dimension(screenwidth, screenheight));
		addMouseListener(this);
		addMouseMotionListener(this);
		frame = new BufferedImage(screenwidth, screenheight, BufferedImage.TYPE_INT_ARGB);
		for (; threadnum < 3; threadnum++) {
			new Thread(this).start();
			while (!started) {
			}
			started = false;
		}
	}

	protected void contentUpdate() {
		swap();
		inputUpdate();
		for (int i = 0; i < screenarea; i++) {
			// top edge
			if (i < screenwidth) {
				
			} else {
				fluid1[i].vel.y-=fluid2[i].mass-fluid2[i-screenwidth].mass;
			}
			// left edge
			if (i % screenwidth == 0) {
				
			} else {

			}
			// bottom edge
			if (i >= screenarea - screenwidth) {
				
			} else {fluid1[i].vel.y+=fluid2[i].mass-fluid2[i+screenwidth].mass;
				

			}
			// right edge
			if (i % screenwidth == screenwidth - 1) {
				
			} else {

			}
		}
	}

	private void swap() {
		//buffer = Arrays.copyOf(fluid1, screenarea);
		//fluid1 = Arrays.copyOf(fluid2, screenarea);
		//fluid2 = Arrays.copyOf(buffer, screenarea);
		for(int i = 0;i<screenarea;i++){
			fluid2[i]=new Cell(fluid1[i].mass, fluid1[i].vel);
		}
	}

	private void inputUpdate() {
		boolean[] ispressed = this.ispressed;
		for (int i = 0; i < screenarea; i++) {
			int x = i % screenwidth;
			int y = i / screenwidth;
			if ((Mouse.x - x) * (Mouse.x - x) + (Mouse.y - y) * (Mouse.y - y) < 1024 && ispressed[3]) {
				fluid1[i].mass += 0.005;
				fluid2[i].mass += 0.005;
			}
		}
	}

	protected void graphicsUpdate() {
		for (int i = 0; i < screenarea; i++) {
			int color=0;
			color = Color.getHSBColor((float) fluid2[i].mass, 1, 1).getRGB();
			//color=0xff000000|((int)fluid2[i].mass*0x101010);
			frame.setRGB(i % screenwidth, i / screenwidth, color);
		}
	}

	protected void contentInit() {
		for (int i = 0; i < screenarea; i++) {
			fluid1[i] = new Cell(1);
			fluid2[i] = new Cell(1);
		}
	}

	protected void graphicsInit() {

	}

	public void run() {
		if (threadnum == 0) {
			started = true;
			contentInit();
			for (;;) {
				try {
					Thread.sleep(1);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				contentUpdate();
			}
		}
		if (threadnum == 1) {
			started = true;
			graphicsInit();
			for (;;) {
				try {
					Thread.sleep(1);
				} catch (InterruptedException e) {
				}

				graphicsUpdate();
				painted = false;
				repaint();
				while (!painted) {
					try {
						Thread.sleep(1);
					} catch (InterruptedException e) {
					}
				}
			}
		}
		if (threadnum == 2) {
			started = true;
			graphicsInit();
			for (;;) {
				try {
					Thread.sleep(1);
				} catch (InterruptedException e) {
				}
				timemilis++;
			}
		}
	}

	public void paint(Graphics g) {
		g.drawImage(frame, 0, 0, null);
		painted = true;
	}

	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	public void mousePressed(MouseEvent e) {
		ispressed[e.getButton()] = true;

	}

	public void mouseReleased(MouseEvent e) {
		ispressed[e.getButton()] = false;

	}

	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	public void mouseDragged(MouseEvent e) {
		mouseMoved(e);

	}

	public void mouseMoved(MouseEvent e) {
		Mouse = new Point2D(e.getX(), e.getY());

	}

}
