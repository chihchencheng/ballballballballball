package scenes;

import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import controllers.*;
import gameobj.*;
import gameobj.Number;
import gameobj.Brick;
import util.CommandSolver.*;
import util.*;

public class GameStartScene extends Scene {

	// set up this scene mouseListener
	public class MyMouseCommandListener implements MouseCommandListener {

		@Override
		public void mouseTrig(MouseEvent e, MouseState state, long trigTime) {
			if (e.getX() >= Global.XstartPoint && e.getX() <= Global.XendPoint) {
				if (state.toString().equals("CLICKED")) {
					for (int i = 0; i < listOfBalls.size(); i++) {
						for (int j = 0; j < listOfBalls.get(i).size(); j++) {
							if (listOfBalls.get(i).get(j).equals(getBallInArea(e))) {
								listOfBalls.get(i).remove(j);
								ballAmount--;
							}
						}
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
//		}
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
	private ArrayList<Brick> bricks;
	private ArrayList<Number> numbers;
	private ArrayList<Ball> linkBalls;
	private List<List<Ball>> listOfBalls;

	private int ballAmount;

	private int[] xs;
	private int time;
	private int units;// 秒個位數
	private int tens;// 秒十位數
	private Delay delay;
	private Delay timeDelay;

	public GameStartScene(SceneController sceneController) {
		super(sceneController);
		background = new GameMainScene();
		sceneBegin();

		this.units = 0;// 倒數個位數
		this.tens = 6;// 倒數十位數
		this.time = 60;// 倒數總秒數
		this.ballAmount = 0;// 檯面上總球數
		this.mmcl = new MyMouseCommandListener();
	}

	@Override
	public void sceneBegin() {
		delay = new Delay(10);
		timeDelay = new Delay(60);// 倒數秒數延遲時間
		bricks = new ArrayList<Brick>();// 最底下的碰撞框
		numbers = new ArrayList<Number>();
		linkBalls = new ArrayList<Ball>();
		listOfBalls = new ArrayList<List<Ball>>();
		xs = genXPoint();
		for (int i = 0; i < Global.COLUMN; i++) {
			List<Ball> columnOfBalls = new ArrayList<>();
			listOfBalls.add(columnOfBalls);
		}
		genNumbers();
		delay.start();
		timeDelay.start();
	}// end of begin

	@Override
	public void sceneUpdate() {
		genBalls(listOfBalls, xs);
		genRect(xs);

//		if (listOfBalls.size() < Global.COLUMN) {
		for (int i = 0; i < listOfBalls.size(); i++) {
//				if (listOfBalls.get(i).size() < Global.ROW) {
			for (int j = 0; j < listOfBalls.get(i).size(); j++) {
				listOfBalls.get(i).get(j).move();
//				if (!listOfBalls.get(i).get(j).move()) {
////					listOfBalls.get(i).remove(j);
//					j--;
//					ballAmount--;
//				}
//			}
//				}
		}
		}

		// 判斷球是否碰撞至最底下磚塊
		for (int i = 0; i < listOfBalls.size(); i++) {
			for (int j = 0; j < listOfBalls.get(i).size(); j++) {
				if (bricks.get(i).isCollision(listOfBalls.get(i).get(j))) {
					listOfBalls.get(i).get(j).offset(0, -4);
				}
			}
		}

		// 判斷球是否互相碰撞
		for (int i = 0; i < listOfBalls.size(); i++) {
			for (int j = 1; j < listOfBalls.get(i).size(); j++) {
				if (listOfBalls.get(i).get(j).isCollision(listOfBalls.get(i).get(j - 1))) {
					listOfBalls.get(i).get(j).offset(0, -4);
				}
			}
		}

		// 倒數計時器，當球滿了，且最後一顆已經落下停止時
		if (ballAmount == Global.LIMIT) {
//				&& 
//			listOfBalls.get(Global.COLUMN).get(Global.ROW).
//			isCollision(listOfBalls.get(Global.COLUMN).get(Global.ROW-1))) {
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

		for (int i = 0; i < bricks.size(); i++) {// 最底下的碰撞長方形
			bricks.get(i).paint(g);
		}
//listOfBalls

		for (int i = 0; i < listOfBalls.size(); i++) {// 畫球COLUMN
			for (int j = 0; j < listOfBalls.get(i).size(); j++) {
				listOfBalls.get(i).get(j).paint(g);
			}
		}

		// 倒數計時60秒
		g.drawImage(numbers.get(units).getImg(), 100, 70, null);// 個位數
		g.drawImage(numbers.get(tens).getImg(), 80, 70, null);// 十位數

	}

	private void genBalls(List<List<Ball>> listOfBall, int[] xs) {
		int count =0;
		if (ballAmount < Global.LIMIT && delay.isTrig()) {
//			for (int i = 0; i < Global.COLUMN; i++) {
			if (listOfBall.get(count).size() < Global.ROW) {

				listOfBall.get(count).add(listOfBall.get(count).size(),
						getANewBall(xs, count));
				count++;
				ballAmount++;
			}
		}
//		}
	}
	
	private boolean checkIfLess(List<List<Ball>> listOfBall) {
		return false;
	}

	private Ball getANewBall(int[] xs, int index) {
		Ball ball = null;

		int r = Global.random(0, 4);
		switch (r) {
		case 0:
			ball = new Volleyball(xs[index], 0);
			break;
		case 1:
			ball = new Basketball(xs[index], 0);
			break;
		case 2:
			ball = new Baseball(xs[index], 0);
			break;
		case 3:
			ball = new Cheerball(xs[index], 0);
			break;
		case 4:
			ball = new Shuttlecock(xs[index], 0);
			break;
		}

		return ball;
	}

	private int[] genXPoint() {// 球畫製的地方，在開始就要生成
		xs = new int[7];
		for (int i = 0; i < 7; i++) {
			xs[i] = i * (int) (Global.UNIT_X * Global.CHARACTER_SIZE_ADJ) + Global.XstartPoint;
		}
		return xs;
	}

	public void genRect(int[] xs) {// 最底下的碰撞框
		for (int i = 0; i < Global.COLUMN; i++) {
			if (i % 2 == 0) {
				bricks.add(new Brick(xs[i] + 2, 540, 50, 10));
			} else {
				bricks.add(new Brick(xs[i] + 2, 500, 50, 10));
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
		for (int i = 0; i < this.listOfBalls.size(); i++) {
			for (int j = 0; j < this.listOfBalls.get(i).size(); j++) {
				if (e.getX() >= this.listOfBalls.get(i).get(j).rect().left()
						&& e.getX() <= this.listOfBalls.get(i).get(j).rect().right()
						&& e.getY() >= this.listOfBalls.get(i).get(j).rect().top()
						&& e.getY() <= this.listOfBalls.get(i).get(j).rect().bottom()) {
					return this.listOfBalls.get(i).get(j);
				}
			}
		}
		return null;
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
