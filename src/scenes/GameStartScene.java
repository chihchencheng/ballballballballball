package scenes;

import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import controllers.*;
import gameobj.*;
import gameobj.Number;
import graph.Rect;
import util.CommandSolver.*;
import util.*;

public class GameStartScene extends Scene {

	// set up this scene mouseListener
	public class MyMouseCommandListener implements MouseCommandListener {

		@Override
		public void mouseTrig(MouseEvent e, MouseState state, long trigTime) {
			if (e.getX() >= Global.XstartPoint && e.getX() <= Global.XendPoint) {
				if(state.toString().equals("CLICKED")) {
					for(int i=0;i<balls.size();i++) {
						if(balls.get(i).equals(getBallInArea(e))) {
							balls.remove(i);
						}
					}
		
				}
//				if (state.toString().equals("PRESSED")) {
//					Ball b1 = getBallInArea(e);
//					linkBalls.add(b1);
//					Global.log(b1.toString());
////					Global.log(String.valueOf(linkBalls.size()));
//					do {
//						Ball ball = getBallInArea(e);
//						if(!ball.getName().equals(b1.getName())) {
//							linkBalls.removeAll(linkBalls);
//							Global.log("1");
//							break;
//						}else {
//							linkBalls.add(ball);
//							Global.log("2");
//						}
//					} while (state.toString().equals("DRAGGED"));
//				}
			}
//			if(state.toString().equals("RELEASED") && linkBalls.size()>=3) {
//				linkBalls.removeAll(linkBalls);
//			}

			/*
			 * PRESSED DRAGGED DRAGGED DRAGGED RELEASED
			 */

		}// end of mouseTrig

	}// end of inner class

	private MyMouseCommandListener mmcl;
	private GameMainScene background;
	private ArrayList<Ball> balls;
	private ArrayList<Rect> rects;
	private ArrayList<Number> numbers;
	private ArrayList<Ball> linkBalls;

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
		linkBalls = new ArrayList<Ball>();
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

//		for (int i = 0; i < balls.size(); i++) {
//			for (int j = 0; j < balls.size(); j++) {
//				if (balls.get(j).isCollision(balls.get(i))) {
//					balls.get(i).setSpeed(0);
//				}
//			}
//		}

		for (int i = 0; i < balls.size(); i++) {
			if (balls.get(i).collider().intersects(rects.get(i).left(), rects.get(i).top(), rects.get(i).right(),
					rects.get(i).bottom())) {
				balls.get(i).setSpeed(0);
				// balls.get(i + 6).offset(0, -4);
			}
		}

		for (int i = 0; i < balls.size() - 7; i++) {
			if (balls.get(i + 7).isCollision(balls.get(i))) {
				balls.get(i + 7).setSpeed(0);
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

	}// end of update

	@Override
	public void sceneEnd() {
		delay.stop();
		delay = null;
	}

	@Override
	public void paint(Graphics g) {
		background.paint(g);

		for (int i = 0; i < rects.size(); i++) {
			rects.get(i).paint(g);
		}

		for (int i = 0; i < balls.size(); i++) {
			balls.get(i).paint(g);
		}

		g.drawImage(numbers.get(units).getImg(), 100, 70, null);
		g.drawImage(numbers.get(tens).getImg(), 80, 70, null);

	}

	private void genBalls() {
		newBall();
		

	}
	
	private void newBall() {
		xs = new int[7];
		for (int i = 0; i < 7; i++) {
			xs[i] = i * (int) (Global.UNIT_X * Global.CHARACTER_SIZE_ADJ) + Global.XstartPoint;
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
		int sizeX = (int) (Global.UNIT_X * Global.CHARACTER_SIZE_ADJ);
		int sizeY = (int) (Global.UNIT_Y * Global.CHARACTER_SIZE_ADJ);
		for (int i = 0; i < 7; i++) {
			if (i % 2 == 0) {
				rects.add(new Rect(Global.XstartPoint + i * sizeX, 540, Global.XstartPoint + i * sizeX + sizeX, 550));
			} else {
				rects.add(new Rect(Global.XstartPoint + i * sizeX, 500, Global.XstartPoint + i * sizeX + sizeX, 510));
			}
		}
	}
	


	public void genNumbers() {
		String[] numImg = ImgPath.numbers();
		for (int i = 0; i < 10; i++) {
			this.numbers.add(new Number(numImg[i]));
		}
	}
	
	private Ball getBallInArea(MouseEvent e) {
		for(int i=0;i<this.balls.size();i++) {
			if( e.getX() >= this.balls.get(i).rect().left() && 
				e.getX() <= this.balls.get(i).rect().right()&&
				e.getY() >= this.balls.get(i).rect().top()&&
				e.getY() <= this.balls.get(i).rect().bottom()) {
				return this.balls.get(i);
			}
		}
		return null;
	}
	
	private boolean isTheSame(Ball b1, Ball b2) {
		return b1.getName().equals(b2.getName());
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
