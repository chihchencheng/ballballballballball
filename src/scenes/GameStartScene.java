package scenes;

import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import controllers.*;
import gameobj.*;
import gameobj.Number;
import util.CommandSolver.*;
import util.*;

public class GameStartScene extends Scene {

	// set up this scene mouseListener
	public class MyMouseCommandListener implements MouseCommandListener {

		@Override
		public void mouseTrig(MouseEvent e, MouseState state, long trigTime) {

		}

	}// end of inner class

	private MyMouseCommandListener mmcl;
	private GameMainScene background;
	private ArrayList<Ball> balls;
	private ArrayList<Rect> rects;
	private ArrayList<Number> numbers;

	private int[] xs;
	private int time;
	private int units;
	private int tens;
	private Delay delay;
	private Delay timeDelay;

	public GameStartScene(SceneController sceneController) {
		super(sceneController);
		background = new GameMainScene();
		sceneBegin();
		this.units = 0;
		this.tens = 6;
		this.time = 60;
		this.mmcl = new MyMouseCommandListener();
	}

	@Override
	public void sceneBegin() {
		balls = new ArrayList<Ball>();
		delay = new Delay(10);
		timeDelay = new Delay(60); 
		rects = new ArrayList<Rect>();
		numbers = new ArrayList<Number>();
		genNumbers();
		delay.start();
		timeDelay.start();
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

		for (int i = 0; i < balls.size() - 7; i++) {
			if (balls.get(i + 7).isCollision(balls.get(i))) {
				balls.get(i + 7).setSpeed(0);
				// balls.get(i + 6).offset(0, -4);
			}

		}
		
		if (balls.size() == Global.LIMIT && balls.get(balls.size() - 1).isCollision(balls.get(balls.size() - 8))) {
			if (this.time >= 0 && timeDelay.isTrig()) {
				units = time % 10;
				tens = time / 10;
				this.time -= 1;
				Global.log(String.valueOf(this.time));
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
		background.paint(g);

		for (int i = 0; i < numbers.size(); i++) {
			numbers.get(i).paint(g);
		}

		for (int i = 0; i < rects.size(); i++) {
			rects.get(i).paint(g);
		}

		for (int i = 0; i < balls.size(); i++) {
//			Global.log(balls.get(i).toString());
			balls.get(i).paint(g);
		}
		
		g.drawImage(numbers.get(units).getImg(), 100,70, null);
		g.drawImage(numbers.get(tens).getImg(), 80,70, null);

	}

	private void genBalls() {
		xs = new int[7];
		for (int i = 0; i < 7; i++) {
			xs[i] = i * (int) (Global.UNIT_X * Global.CHARACTER_SIZE_ADJ) + Global.startPoint;
		}
		if (balls.size() < Global.LIMIT && delay.isTrig()) {//
			int r = Global.random(0, 4);

			switch (r) {
			case 0:
				balls.add(new Volleyball(xs[balls.size() % 7], 0));
				break;
			case 1:
				balls.add(new Basketball(xs[balls.size() % 7], 0));
				break;
			case 2:
				balls.add(new Baseball(xs[balls.size() % 7], 0));
				break;
			case 3:
				balls.add(new Cheerball(xs[balls.size() % 7], 0));
				break;
			case 4:
				balls.add(new Shuttlecock(xs[balls.size() % 7], 0));
				break;
			}
		}

	}

	public void genRect() {
		for (int i = 0; i < 7; i++) {
			if (i % 2 == 0) {
				rects.add(new Rect(xs[i] + 5, 540));
			} else {
				rects.add(new Rect(xs[i] + 5, 500));
			}
		}
	}

	public void genNumbers() {
		String[] numImg = ImgPath.numbers();
		for (int i = 0; i < 10; i++) {
			this.numbers.add(new Number(numImg[i]));
		}
	}

	// set up for each scene to get scene Listener
	@Override
	public CommandSolver.KeyListener getKeyListener() {
		return null;
	}

	@Override
	public CommandSolver.MouseCommandListener getMouseListener() {
		return this.mmcl;
	}

}// end of class
