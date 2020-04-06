import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.swing.JPanel;

import controllers.ImageResourceController;
import gameobj.*;
import util.*;

public class GameJPanel extends JPanel {
	public static final int LIMIT = 42;
	public static final int startPoint = 603;

	class MyListener extends MouseAdapter {
		@Override
		public void mouseMoved(MouseEvent e) {

		}
	}// end of inner class

	private ArrayList<Ball> balls;
	private Delay delay;
	private ArrayList<Rect> rects;
	private int[] xs;

	public GameJPanel() {
		balls = new ArrayList<Ball>();
		delay = new Delay(10);
		rects = new ArrayList<Rect>();
		xs = new int[6];
		for (int i = 0; i < 6; i++) {
			xs[i] = i * (int) (Global.UNIT_X * Global.CHARACTER_SIZE_ADJ) + startPoint;
		}

//		delay.start();
		this.addMouseMotionListener(new MyListener());
	}// end of constructor

	public void update() {
		genBalls();
		genRect();
		for (int i = 0; i < balls.size(); i++) {
			if (!balls.get(i).move()) {
				balls.remove(i);
				i--;
			}
		}

		for (int i = 0; i < balls.size(); i++) {
			for (int j = 0; j < balls.size(); j++) {
				if (rects.get(j).isCollision(balls.get(i))) {
					balls.get(i).setSpeed(0);
				}
			}
		}

		for (int i = 0; i < balls.size() - 6; i++) {
			if (balls.get(i + 6).isCollision(balls.get(i))) {
				balls.get(i + 6).setSpeed(0);
				// balls.get(i + 6).offset(0, -4);
			}

		}

	}// end of update

	private void genBalls() {
		if (balls.size() < LIMIT && delay.isTrig()) {//
			int r = Global.random(0, 4);

			switch (r) {
			case 0:
				balls.add(new Volleyball(xs[balls.size() % 6], 0));
				break;
			case 1:
				balls.add(new Basketball(xs[balls.size() % 6], 0));
				break;
			case 2:
				balls.add(new Baseball(xs[balls.size() % 6], 0));
				break;
			case 3:
				balls.add(new Cheerball(xs[balls.size() % 6], 0));
				break;
			case 4:
				balls.add(new Shuttlecock(xs[balls.size() % 6], 0));
				break;
			}
		}

	}

	public void genRect() {
		rects.add(new Rect(xs[1] + 5, 559));
		rects.add(new Rect(xs[3] + 5, 559));
		rects.add(new Rect(xs[5] + 5, 559));

	}

	@Override
	public void paint(Graphics g) {
		for (int i = 0; i < rects.size(); i++) {
			rects.get(i).paint(g);
		}

		for (int i = 0; i < balls.size(); i++) {
			balls.get(i).paint(g);

		}
	}
}// end of class
