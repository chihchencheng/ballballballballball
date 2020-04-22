package scenes;

import java.applet.Applet;
import java.applet.AudioClip;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import audio.AudioObject;
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
//			if (imgs.get(6).isInside(e.getX(), e.getY()) && state == MouseState.CLICKED) {
//                sceneController.changeScene(new MainScene(sceneController));
//            }


			if (imgs.get(5).isInside(e.getX(), e.getY()) && state == MouseState.CLICKED) {
				timeDelay.pause();
			}

			if (imgs.get(6).isInside(e.getX(), e.getY()) && state == MouseState.CLICKED) {
				for (int i = 0; i < listOfBalls.size(); i++) {
					listOfBalls.get(i).clear();
				}
				ballAmount = 0;
			}
			

			if (e.getX() >= Global.XstartPoint && e.getX() <= Global.XendPoint) {
				
				if (state.toString().equals("PRESSED") && linkBalls.size() == 0) {
					for (int i = 0; i < listOfBalls.size(); i++) {
						for (int j = 0; j < listOfBalls.get(i).size(); j++) {
							if (listOfBalls.get(i).get(j).equals(getBallInArea(e))) {
								linkBalls.add(getBallInArea(e));
								Global.log("Pressed");
							}
						}
					}

				}
				
//				Global.log(state.toString());
				if (state.toString().equals("DRAGGED")) {
					for (int i = 0; i < listOfBalls.size(); i++) {
						for (int j = 0; j < listOfBalls.get(i).size(); j++) {
							if (linkBalls.size() != 0 && !isExisted(linkBalls, getBallInArea(e))
									&& listOfBalls.get(i).get(j).equals(getBallInArea(e))
									&& isTheSameBall(linkBalls.get(0), getBallInArea(e))
									&& (Math.abs(linkBalls.get(linkBalls.size() - 1).rect().centerX()
											- getBallInArea(e).rect().centerX())) <= Global.UNIT_X - 1
									&& (Math.abs(linkBalls.get(linkBalls.size() - 1).rect().centerY()
											- getBallInArea(e).rect().centerY())) <= Global.UNIT_Y - 1) {
								linkBalls.add(getBallInArea(e));
//								getBallInArea(e).setPress(true);
//								Global.log("Dragged");
							} else if (isExisted(linkBalls, getBallInArea(e))
									&& !((linkBalls.get(linkBalls.size() - 1).equals(getBallInArea(e))))
									&& (Math.abs(linkBalls.get(linkBalls.size() - 1).rect().centerY()
											- getBallInArea(e).rect().centerY())) <= Global.UNIT_Y - 20)  {
								for (int k = 0; k < linkBalls.size(); k++) {
									if (linkBalls.get(k).equals(getBallInArea(e))) {
										for (int l = k + 1; l < linkBalls.size(); l++) {
											linkBalls.remove(l);
										}
									}
								}
							}
						}
					}
				} //
				
				if (state.toString().equals("RELEASED") || state.toString().equals("EXITED") ||
						!(e.getX() >= Global.XstartPoint && e.getX() <= Global.XendPoint
						&& (e.getY() >= Global.YstartPoint && e.getY() <= Global.YendPoint))) {
					if (linkBalls.size() >= 3) {
						for (int i = 0; i < linkBalls.size(); i++) {
							isTheSameObject(listOfBalls, linkBalls.get(i));
						}
						getScore();
						getSkillLevel();
						
						clean.getAudio().play();
						linkBalls.clear();

					}
					if (linkBalls.size() >= 3 
							&& ((e.getX() >= Global.XstartPoint && e.getX() <= Global.XendPoint
									&& (e.getY() >= Global.YstartPoint && e.getY() <= Global.YendPoint)
									&& !isTheSameBall(linkBalls.get(linkBalls.size() - 1), getBallInArea(e))
									))) {
						for (int i = 0; i < linkBalls.size(); i++) {
//								System.out.println(linkBalls.get(i));
							isTheSameObject(listOfBalls, linkBalls.get(i));
						}
						getScore();
						getSkillLevel();
						clean.getAudio().play();
						linkBalls.clear();
					}
					if(linkBalls.size() >= 3) {
						for (int i = 0; i < linkBalls.size(); i++) {
//							System.out.println(linkBalls.get(i));
						isTheSameObject(listOfBalls, linkBalls.get(i));
					}
					getScore();
					getSkillLevel();
					clean.getAudio().play();
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
	private ArrayList<Number> digNumbers;
	private ArrayList<Ball> linkBalls;
	private List<List<Ball>> listOfBalls;
	private ArrayList<Img> imgs;
	private int ballAmount;
	private Img bk;
	private int roleNum;// 小人物圖片

	private int[] xs;
	private int time;
	private int units;// 秒個位數
	private int tens;// 秒十位數
	private int skillLevel;
	private int score;
	private int totalScore;
	private int countDown;
	private Delay delay;
	private Delay timeDelay;
	
	private AudioObject clean;
	private AudioObject houwo;
	

	public GameStartScene(SceneController sceneController, int roleNum) {
		super(sceneController);
		bk = new Img(ImgPath.BK_MAIN, 0, 0, (int) (Global.SCREEN_X * Global.ADJ), (int) (Global.SCREEN_Y * Global.ADJ),
				true);// 載入背景圖片
		this.roleNum = roleNum + 7;

		imgs = new ArrayList<>();

		// 角色
		sceneBegin();
		
		this.clean = new AudioObject("Clean", AudioPath.CLEAN2);
		this.countDown = 0;
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

		delay = new Delay(Global.UPDATE_TIMES_PER_SEC);
		timeDelay = new Delay(Global.UPDATE_TIMES_PER_SEC);// 倒數秒數延遲時間
		bricks = new ArrayList<Brick>();// 最底下的碰撞框
		numbers = new ArrayList<Number>();
		digNumbers = new ArrayList<Number>();
		linkBalls = new ArrayList<Ball>();
		listOfBalls = new ArrayList<List<Ball>>();
		houwo = new AudioObject("HouWo",AudioPath.HOWO);
		

		// 元件
		imgs.add(new Img(ImgPath.TIME_PANEL, (int) (Global.SCREEN_X * 0.022388 * Global.ADJ),
				(int) (Global.SCREEN_Y * 0.027 * Global.ADJ), true));
		imgs.add(new Img(ImgPath.LEFT_PANEL, (int) (Global.SCREEN_X * 0.017 * Global.ADJ),
				(int) (Global.SCREEN_Y * 0.212 * Global.ADJ), true));
		imgs.add(new Img(ImgPath.RIGHT_PANEL, (int) (Global.SCREEN_X * 0.5 * Global.ADJ),
				(int) (Global.SCREEN_Y * 0.022 * Global.ADJ), true));
		imgs.add(new Img(ImgPath.SKILL_BANNER, (int) (Global.SCREEN_X * 0.672 * Global.ADJ),
				(int) (Global.SCREEN_Y * 0.865 * Global.ADJ), true));
		imgs.add(new Img(ImgPath.PLAY_BUTTON, (int) (Global.SCREEN_X * 0.903 * Global.ADJ),
				(int) (Global.SCREEN_Y * 0.8675 * Global.ADJ), true));
		imgs.add(new Img(ImgPath.PAUSE, (int) (Global.SCREEN_X * 0.515 * Global.ADJ),
				(int) (Global.SCREEN_Y * 0.873 * Global.ADJ), true));
		imgs.add(new Img(ImgPath.REHEARSE, (int) (Global.SCREEN_X * 0.58 * Global.ADJ),
				(int) (Global.SCREEN_Y * 0.873 * Global.ADJ), true));
		// small role
		imgs.add(new Img(ImgPath.SMALL_TSAI, (int) (Global.SCREEN_X * -0.012 * Global.ADJ),
				(int) (Global.SCREEN_Y * 0.29 * Global.ADJ), true));
		imgs.add(new Img(ImgPath.SMALL_ZHANG, (int) (Global.SCREEN_X * -0.03 * Global.ADJ),
				(int) (Global.SCREEN_Y * 0.35 * Global.ADJ), true));
		imgs.add(new Img(ImgPath.SMALL_SHU, (int) (Global.SCREEN_X * -0.03 * Global.ADJ),
				(int) (Global.SCREEN_Y * 0.35 * Global.ADJ), true));
		imgs.add(new Img(ImgPath.SMALL_ZHOU, (int) (Global.SCREEN_X * -0.03 * Global.ADJ),
				(int) (Global.SCREEN_Y * 0.35 * Global.ADJ), true));
		imgs.add(new Img(ImgPath.SMALL_WANG, (int) (Global.SCREEN_X * -0.03 * Global.ADJ),
				(int) (Global.SCREEN_Y * 0.35 * Global.ADJ), true));

		// gray dig8
		// ------------score
		float digSize1 = 0.6f;
		for (int i = 0; i < 10; i++) {
			imgs.add(new Img(ImgPath.EIGHT_D_GRAY, (int) (Global.SCREEN_X * 0.19 * Global.ADJ + i * 28),
					(int) (Global.SCREEN_Y * 0.43 * Global.ADJ), (int) (46 * digSize1), (int) (70 * digSize1), true));
		}
		// ------------coin
		float digSize2 = 0.45f;
		for (int i = 0; i < 10; i++) {
			imgs.add(new Img(ImgPath.EIGHT_D_GRAY, (int) (Global.SCREEN_X * 0.255 * Global.ADJ + i * 20),
					(int) (Global.SCREEN_Y * 0.577 * Global.ADJ), (int) (46 * digSize2), (int) (70 * digSize2), true));
		}
		// ------------exp
		for (int i = 0; i < 10; i++) {
			imgs.add(new Img(ImgPath.EIGHT_D_GRAY, (int) (Global.SCREEN_X * 0.255 * Global.ADJ + i * 20),
					(int) (Global.SCREEN_Y * 0.679 * Global.ADJ), (int) (46 * digSize2), (int) (70 * digSize2), true));
		}

		xs = genXPoint();
		for (int i = 0; i < Global.COLUMN; i++) {
			List<Ball> columnOfBalls = new ArrayList<>();
			listOfBalls.add(columnOfBalls);
		}
		genNumbers(ImgPath.numbers());
		genNumbers(ImgPath.digNumbers());
		genRect(xs);
		delay.start();
		timeDelay.start();
	}// end of begin

	@Override
	public void sceneUpdate() {
		checkIfLess(listOfBalls);
		
//		if(countDown != 0) {
			for (int i = 0; i < listOfBalls.size(); i++) {
				for (int j = 0; j < listOfBalls.get(i).size(); j++) {
					if (!listOfBalls.get(i).get(j).move()) {
						listOfBalls.get(i).remove(j);
						ballAmount--;
					}
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
//					&& 
//				listOfBalls.get(Global.COLUMN).get(Global.ROW).
//				isCollision(listOfBalls.get(Global.COLUMN).get(Global.ROW-1))) {
				if (this.time >= 0 && timeDelay.isTrig()) {
					units = time % 10;
					tens = time / 10;
					this.time -= 1;
				}
			}

//		}
	
//	else {
//			countDown--;
//		}
		
		
	}

	@Override
	public void sceneEnd() {
		delay.stop();
		delay = null;
	}

	@Override
	public void paint(Graphics g) {
		bk.paint(g);
		// 元件部分
		for (int i = 0; i < 7; i++) {
			imgs.get(i).paint(g);
		}

		// 小人物部分
		imgs.get(this.roleNum).paint(g);

		for (int i = 0; i < bricks.size(); i++) {// 最底下的碰撞長方形
			bricks.get(i).paint(g);
		}

		for (int i = 0; i < listOfBalls.size(); i++) {// 畫球COLUMN
			for (int j = 0; j < listOfBalls.get(i).size(); j++) {
				listOfBalls.get(i).get(j).paint(g);
			}
		}
		

		


		// 倒數計時60秒
		float sizeCD = 0.8f; // 數字圖片縮放比例
		g.drawImage(numbers.get(units).getImg(), 112, 38, null);// 個位數
		g.drawImage(numbers.get(tens).getImg(), 78, 38, null);// 十位數

		// sore panel
		for (int i = 0; i < 30; i++) {
			imgs.get(12 + i).paint(g);
		}
		
		if (linkBalls.size() >= 2) {
			
			Graphics2D g2 = (Graphics2D) g;
			g2.setColor(Color.MAGENTA);
			g2.setStroke(new BasicStroke(10));
			
			for (int i = 0; i < linkBalls.size() - 1; i++) {
				g2.drawLine(linkBalls.get(i).rect().centerX(), linkBalls.get(i).rect().centerY() ,
						linkBalls.get(i + 1).rect().centerX() , linkBalls.get(i + 1).rect().centerY());
			}
			g2.setColor(Color.black);
		}
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
			xs[i] = i * (int) (Global.UNIT_X * Global.ADJ) + Global.XstartPoint;
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

	public void genNumbers(String[] path) {
		String[] numImg = path;
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

	private boolean skillTrig(int key) {
		
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
				Global.log("Skill Trig");
				skillLevel = 0;
				Global.log("Skill Level reset to 0");
			}
			return true;
		case 2:// Wang-volleyball
			if (skillLevel >= 17) {
				for (int i = 0; i < 10; i++) {
					int c = (int) (Math.random() * listOfBalls.size());
					int r = (int) (Math.random() * listOfBalls.get(0).size());
					listOfBalls.get(c).remove(r);
				}
				Global.log("Skill Trig");
				skillLevel = 0;
				Global.log("Skill Level reset to 0");
			}
			return true;
		case 3:// Tsai-cheerball
			if (skillLevel >= 16) {
				this.time += 5;
				System.out.println("Time:" + this.time);
				Global.log("Skill Trig");
				skillLevel = 0;
				Global.log("Skill Level reset to 0");
			}
			return true;
		case 4:// Shu-shuttlecock
			if (skillLevel >= 14) {// basketball skill trig
				int skillStartTime = this.time;
				do {
					System.out.println(this.time);
					this.totalScore += this.score * 1.4;
				} while (this.time - skillStartTime < 7);
			}
			skillLevel = 0;
			return true;
		case 5:// Zhou-baseball
//			Global.log("case 5");
			if (skillLevel >= 3) {
				for (int i = 0; i < listOfBalls.size(); i++) {
					listOfBalls.get(i).remove(4);
//					Global.log("skill level");
				}
				for (int i = 0; i < listOfBalls.get(3).size(); i++) {
					if (i == 4) {
//						Global.log("listOfBalls");
						continue;
					}
					
					listOfBalls.get(3).remove(i);
				}
				this.countDown = 240;
				Global.log("Skill Trig");
				skillLevel = 0;
				Global.log("Skill Level reset to 0");
				return true;
			}
		}
		return false;
	}


	private void getScore() {
		switch (linkBalls.get(0).getName()) {
		case "Volleyball":
			this.totalScore += linkBalls.size() * 2;
			Global.log("TotalScore: " + this.totalScore);
			break;
		case "Basketball":
			this.totalScore += linkBalls.size() * 2;
			Global.log("TotalScore: " + this.totalScore);
			break;
		case "Baseball":
			this.totalScore += linkBalls.size() * 2;
			Global.log("TotalScore: " + this.totalScore);
			break;
		case "Cheerball":
			this.totalScore += linkBalls.size() * 2;
			Global.log("TotalScore: " + this.totalScore);
			break;
		case "Shuttlecock":
			this.totalScore += linkBalls.size() * 2;
			Global.log("TotalScore: " + this.totalScore);
			break;
		}
	}

	private void getSkillLevel() {
		this.skillLevel++;
		Global.log("SkillLevel + 1, SkillLevel:" + this.skillLevel);
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
