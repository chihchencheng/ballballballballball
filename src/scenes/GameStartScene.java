package scenes;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import controllers.*;
import gameobj.*;
import gameobj.Number;
import sun.awt.SunHints.Value;
import gameobj.Brick;
import util.CommandSolver.*;
import util.*;

public class GameStartScene extends Scene {

	// set up this scene mouseListener
	public class MyMouseCommandListener implements MouseCommandListener {

		@Override
		public void mouseTrig(MouseEvent e, MouseState state, long trigTime) {
			// for reset balls
			if (e.getX() >= Global.XstartPoint - 10 && e.getX() <= Global.XstartPoint + 73
					&& e.getY() >= Global.YendPoint + 10 && e.getY() <= Global.YendPoint + 10 + 56) {
				if (state.toString().equals("CLICKED")) {
					for (int i = 0; i < listOfBalls.size(); i++) {
						for (int j = 0; j < listOfBalls.get(i).size(); j++) {
							listOfBalls.get(i).clear();
						}
					}
				}
			}

//			if (e.getX() >= Global.XstartPoint && e.getX() <= Global.XendPoint) {
//				if (state.toString().equals("CLICKED")) {
//					for (int i = 0; i < listOfBalls.size(); i++) {
//						for (int j = 0; j < listOfBalls.get(i).size(); j++) {
//							if (listOfBalls.get(i).get(j).equals(getBallInArea(e))) {
//								System.out.println("Y");
//								listOfBalls.get(i).remove(j);
//								ballAmount--;
//							}
//						}
//					}
//				}
//			}
			if (e.getX() >= Global.XstartPoint && e.getX() <= Global.XendPoint) {
				if (state.toString().equals("PRESSED")) {
					for (int i = 0; i < listOfBalls.size(); i++) {
						for (int j = 0; j < listOfBalls.get(i).size(); j++) {
							if (listOfBalls.get(i).get(j).equals(getBallInArea(e))) {
								linkBalls.add(getBallInArea(e));
								Global.log("Pressed");
							}
						}
					}
				}
				if (state.toString().equals("DRAGGED")) {
					for (int i = 0; i < listOfBalls.size(); i++) {
						for (int j = 0; j < listOfBalls.get(i).size(); j++) {
							if (linkBalls.size() != 0 && !isExisted(linkBalls, getBallInArea(e))
									&& listOfBalls.get(i).get(j).equals(getBallInArea(e))
									&& isTheSameBall(linkBalls.get(0), getBallInArea(e))) {
								linkBalls.add(getBallInArea(e));
								Global.log("Dragged");
							}
						}
					}
				}
				if (state.toString().equals("RELEASED")) {
					if (linkBalls.size() >= 3) {
						for (int i = 0; i < linkBalls.size(); i++) {
//							System.out.println(linkBalls.get(i));
							isTheSameObject(listOfBalls, linkBalls.get(i));
						}
						getScore();
						getSkillLevel();
						linkBalls.clear();

					}
					if (linkBalls.size() < 3) {
						linkBalls.clear();
					}
				}
			}
		}// end of mouseTrig
	}// end of inner class

	private MyMouseCommandListener mmcl;
	private ArrayList<Brick> bricks;
	private ArrayList<Number> numbers;
	private ArrayList<Ball> linkBalls;
	private List<List<Ball>> listOfBalls;
	private BufferedImage background;

	private int ballAmount;

	private int[] xs;
	private int time;
	private int units;// 秒個位數
	private int tens;// 秒十位數
	private int skillLevel;
	private int score;
	private int totalScore;
	private Delay delay;
	private Delay timeDelay;

	public GameStartScene(SceneController sceneController) {
		super(sceneController);
		background = ImageResourceController.getInstance().tryGetImage(ImgPath.GAMESTART);

		sceneBegin();
		this.skillLevel = 0;
		this.units = 0;// 倒數個位數
		this.tens = 6;// 倒數十位數
		this.time = 60;// 倒數總秒數
		this.ballAmount = 0;// 檯面上總球數
		this.score = 0;
		this.totalScore = 0;
		this.mmcl = new MyMouseCommandListener();
	}

	@Override
	public void sceneBegin() {
		delay = new Delay(0);
		timeDelay = new Delay(120);// 倒數秒數延遲時間
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
		genRect(xs);
//		delay.start();

		timeDelay.start();
	}// end of begin

	@Override
	public void sceneUpdate() {

		for (int i = 0; i < listOfBalls.size(); i++) {
			for (int j = 0; j < listOfBalls.get(i).size(); j++) {
				if (!listOfBalls.get(i).get(j).move()) {
					listOfBalls.get(i).remove(j);
					ballAmount--;
				}
			}
		}
		checkIfLess(listOfBalls);

		skillTrig(2);

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
//		if (ballAmount == Global.LIMIT) {
//				&& 
//			listOfBalls.get(Global.COLUMN).get(Global.ROW).
//			isCollision(listOfBalls.get(Global.COLUMN).get(Global.ROW-1))) {
		if (this.time >= 0 && timeDelay.isTrig()) {
			units = time % 10;
			tens = time / 10;
			this.time -= 1;
		}
	}

	@Override
	public void sceneEnd() {
		delay.stop();
		delay = null;
	}

	@Override
	public void paint(Graphics g) {
		g.drawImage(background, 0, 0, Global.SCREEN_X, Global.SCREEN_Y, null);

		for (int i = 0; i < bricks.size(); i++) {// 最底下的碰撞長方形
			bricks.get(i).paint(g);
		}

		for (int i = 0; i < listOfBalls.size(); i++) {// 畫球COLUMN
			for (int j = 0; j < listOfBalls.get(i).size(); j++) {
				listOfBalls.get(i).get(j).paint(g);
			}
		}

		if (linkBalls.size() >= 2) {
			g.setColor(Color.blue);
			for (int i = 0; i < linkBalls.size() - 1; i++) {
				g.drawLine(linkBalls.get(i).rect().centerX(), linkBalls.get(i).rect().centerY(),
						linkBalls.get(i + 1).rect().centerX(), linkBalls.get(i + 1).rect().centerY());

				g.drawLine(linkBalls.get(i).rect().centerX() + 1, linkBalls.get(i).rect().centerY() + 1,
						linkBalls.get(i + 1).rect().centerX() + 1, linkBalls.get(i + 1).rect().centerY() + 1);

				g.drawLine(linkBalls.get(i).rect().centerX() + 2, linkBalls.get(i).rect().centerY() + 2,
						linkBalls.get(i + 1).rect().centerX() + 2, linkBalls.get(i + 1).rect().centerY() + 2);

			}
			g.setColor(Color.black);
		}

		// 倒數計時60秒
		g.drawImage(numbers.get(units).getImg(), 100, 70, null);// 個位數
		g.drawImage(numbers.get(tens).getImg(), 60, 70, null);// 十位數
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
	
	private boolean checkIfLess(List<List<Ball>> listOfBalls) {
		for (int i = 0; i < Global.COLUMN; i++) {
			while (listOfBalls.get(i).size() < Global.ROW) {
				listOfBalls.get(i).add(getANewBall(xs, i));
				ballAmount++;
			}
		}
		return false;
	}

	private void skillTrig(int key) {

		switch (key) {
		case 1:// Zhang-baseketball
			if (skillLevel == 12) {// basketball skill trig
				for (int i = 2; i < 4; i++) {
					listOfBalls.get(2).remove(i);
					listOfBalls.get(4).remove(i);
				}
				for (int i = 1; i < 4; i++) {
					listOfBalls.get(3).remove(i);
				}
				skillLevel = 0;
			}
			break;
		case 2:// Wang-volleyball
			if (skillLevel >= 17) {// basketball skill trig
				for (int i = 0; i < 15; i++) {
					int c = (int) (Math.random() * listOfBalls.size());
					int r = (int) (Math.random() * listOfBalls.get(0).size());
					listOfBalls.get(c).remove(r);
				}
			}
			skillLevel = 0;
			break;
		case 3:// Tsai-cheerball
			break;
		case 4:// Shu-shuttlecock
			if (skillLevel >= 14) {// basketball skill trig
				int skillStartTime = this.time;
				do {
					System.out.println(this.time);
					this.totalScore += this.score * 1.4;
				} while (this.time - skillStartTime < 7);
			}
			skillLevel = 0;
			break;
		case 5:// Zhou-baseball
			break;
		}
	}

	private void getScore() {
		switch (linkBalls.get(0).getName()) {
		case "Volleyball":
			this.totalScore += linkBalls.size() * 2;
			Global.log(Integer.toString(this.totalScore));
			break;
		case "Basketball":
			this.totalScore += linkBalls.size() * 2;
			Global.log(Integer.toString(this.totalScore));
			break;
		case "Baseball":
			this.totalScore += linkBalls.size() * 2;
			Global.log(Integer.toString(this.totalScore));
			break;
		case "Cheerball":
			this.totalScore += linkBalls.size() * 2;
			Global.log(Integer.toString(this.totalScore));
			break;
		case "Shuttlecock":
			this.totalScore += linkBalls.size() * 2;
			Global.log(Integer.toString(this.totalScore));
			break;
		}
	}

	private void getSkillLevel() {

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

	private void isTheSameObject(List<List<Ball>> listOfBalls, Ball ball) {
		for (int i = 0; i < listOfBalls.size(); i++) {
			for (int j = 0; j < listOfBalls.get(i).size(); j++) {
				if (ball.equals(listOfBalls.get(i).get(j))) {
					listOfBalls.get(i).remove(j);
					ballAmount--;
				}
			}
		}
	}

	private boolean isExisted(ArrayList<Ball> linkBalls, Ball ball) {
		for (int i = 0; i < linkBalls.size(); i++) {
			if (ball == linkBalls.get(i)) {
				return true;
			}
		}
		return false;
	}

	private boolean isTheSameBall(Ball b1, Ball b2) {
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
