import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

import javax.swing.JPanel;

import gameobj.*;
import util.*;

public class GameJPanel extends JPanel {
	public static final int LIMIT = 8;

	class MyListener extends MouseAdapter {
		@Override
		public void mouseMoved(MouseEvent e) {

		}
	}// end of inner class

	private ArrayList<Ball> balls;
	private Delay delay;

	public GameJPanel() {
		balls = new ArrayList<Ball>();
		delay = new Delay(10);
		delay.start();
		this.addMouseMotionListener(new MyListener());
	}// end of constructor

	public void update() {

		for (int i = 0; i < balls.size(); i++) {
			if (!balls.get(i).move()) {
				balls.remove(i);
				i--;
			}

		}

		if (balls.size() < LIMIT) {
			int r = Global.random(0, 4);
			int[] x = { 603, 689, 775, 861, 947, 1033 };
			switch (r) {
			case 0:
				balls.add(new Volleyball(x[balls.size() % 6], 0));
				break;
			case 1:
				balls.add(new Basketball(x[balls.size() % 6], 0));
				break;
			case 2:
				balls.add(new Baseball(x[balls.size() % 6], 0));
				break;
			case 3:
				balls.add(new Cheerball(x[balls.size() % 6], 0));
				break;
			case 4:
				balls.add(new Shuttlecock(x[balls.size() % 6], 0));
				break;
			}
		}
	}// end of update

	public void paint(Graphics g) {
		for (int i = 0; i < balls.size(); i++) {
			balls.get(i).paint(g);

		}
	}
}// end of class
