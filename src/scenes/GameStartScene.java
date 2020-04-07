package scenes;

import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import controllers.*;
import gameobj.*;
import util.CommandSolver.*;
import util.*;

public class GameStartScene extends Scene {

	// 設定本場景的mouseListener
	public class MyMouseCommandListener implements MouseCommandListener {

		@Override
		public void mouseTrig(MouseEvent e, MouseState state, long trigTime) {

		}

	}// end of inner class

	private MyMouseCommandListener mmcl;
	private ArrayList<Ball> balls;
	private ArrayList<Rect> rects;
	private int[] xs;

	private Delay delay;

	public GameStartScene(SceneController sceneController) {
		super(sceneController);
		sceneBegin();
		this.mmcl = new MyMouseCommandListener();
	}

	@Override
	public void sceneBegin() {
		balls = new ArrayList<Ball>();
		delay = new Delay(10);
		rects = new ArrayList<Rect>();
		delay.start();
	}// end of begin

	@Override
	public void sceneUpdate() {
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

	}

	@Override
	public void sceneEnd() {
		delay.stop();
		delay = null;
	}

	@Override
	public void paint(Graphics g) {
		for (int i = 0; i < rects.size(); i++) {

			rects.get(i).paint(g);
		}

		for (int i = 0; i < balls.size(); i++) {
			Global.log(balls.get(i).toString());
			balls.get(i).paint(g);

		}

	}

	private void genBalls() {
		xs = new int[6];
		for (int i = 0; i < 6; i++) {
			xs[i] = i * (int) (Global.UNIT_X * Global.CHARACTER_SIZE_ADJ) + Global.startPoint;
		}
		if (balls.size() < Global.LIMIT && delay.isTrig()) {//
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

	// 設定讓每個場景都能取得該場景設定的Listener
	@Override
	public CommandSolver.KeyListener getKeyListener() {
		return null;
	}

	@Override
	public CommandSolver.MouseCommandListener getMouseListener() {
		return this.mmcl;
	}

}// end of class
