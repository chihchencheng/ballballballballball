package scenes;

import audio.AudioObject;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import controllers.*;
import gameobj.*;
import gameobj.Number;
import gameobj.Brick;
import java.awt.BasicStroke;
import java.awt.Graphics2D;
import util.CommandSolver.*;
import util.*;
import static util.Global.*;
import static util.ImgPath.*;

public class GameStartScene extends Scene {

	public class MyMouseCommandListener implements MouseCommandListener {

		private String buttonSelectPath;

		@Override
		public void mouseTrig(MouseEvent e, MouseState state, long trigTime) {

			if (isPause || GameStartScene.this.time <= 0) {
				// -----按鈕功能
				if (imgs.get(B_HOME).isInside(e.getX(), e.getY()) && state == MouseState.CLICKED) {
					sceneController.changeScene(new MainScene(sceneController));
				} // 首頁

				if (imgs.get(CONTINUE_BUTTON).isInside(e.getX(), e.getY()) && state == MouseState.CLICKED) {
					isPause = !isPause;
				} // 繼續按鈕

				if (imgs.get(B_SHOP).isInside(e.getX(), e.getY()) && state == MouseState.CLICKED) {
					sceneController.changeScene(new ShopScene(sceneController));
				} // 商店

				if (imgs.get(B_INFO).isInside(e.getX(), e.getY()) && state == MouseState.CLICKED) {
					sceneController.changeScene(new InfoScene(sceneController));
				} // 資訊

				// -----按鈕換圖
				for (int i = 0; i < button.length; i++) {
					if (imgs.get(button[i]).isInside(e.getX(), e.getY())) {
						buttonSelectPath = button[i];
					}
				}
				if (!imgs.get(button[0]).isInside(e.getX(), e.getY())
						&& !imgs.get(button[1]).isInside(e.getX(), e.getY())
						&& !imgs.get(button[2]).isInside(e.getX(), e.getY())
						&& !imgs.get(button[3]).isInside(e.getX(), e.getY())) {
					buttonSelectPath = NULL;
				}
			} else {
				if (imgs.get(PAUSE).isInside(e.getX(), e.getY()) && state == MouseState.CLICKED) {
					isPause = !isPause;
				}
				if (imgs.get(REHEARSE).isInside(e.getX(), e.getY()) && state == MouseState.CLICKED) {
					for (int i = 0; i < listOfBalls.size(); i++) {
						listOfBalls.get(i).clear();
					}
					ballAmount = 0;
				}
				if (!isPause && countDown == 0) {
					gameOperation(e, state);
				}
			}
		}// end of mouseTrig
	}// end of inner class

	private MyMouseCommandListener mmcl;
	private ArrayList<Brick> bricks;
	private ArrayList<Number> numbers;
	private ArrayList<Number> digNumbers;
	private ArrayList<Ball> linkBalls;
	private ArrayList<Ball> skillLinkBalls;
	private List<List<Ball>> listOfBalls;
	private int ballAmount;
	private String rolePath; // 小人物圖片

	private int[] xs;
	private int time;
	private int units; // 秒個位數
	private int tens; // 秒十位數
	private int skillLevel;
	private int score;
	private int totalScore;
	private int exp;
	private int coin;
	private int[] scoreUnits;
	private int[] expUnits;
	private int[] coinUnits;
	private int digsMove;

	private boolean isPause;
	private int preCountDown;
	private int countDown;
	private int countDownActive;
	private boolean timeUpStart;
	private boolean timeUpOver;
	private int frameAfterTimeUp = 0;
	int rightPanelDeltX = 0;
	int leftPanelDeltX = 0;
	private int skillPaintCount;

	private Delay delay;
	private Delay timeDelay; // 遊戲時間計時器

	private AudioObject clean;

	private int skill;
	private int[] skillCDtime = { 0, 120, 300, 600, 180, 200 };

	private String[] componentPaths = { BK_MAIN, TIME_PANEL, LEFT_PANEL, RIGHT_PANEL, SKILL_BANNER };
	private String[] ballPaths = { CHEERBALL, BASKETBALL, SHUTTLECOCK, BASEBALL, VOLLEYBALL };
	private String[] numPaths = { ZERO, ONE, TWO, THREE, FOUR, FIVE, SIX, SEVEN, EIGHT, NINE };
	private String[] digNumPaths = { ZERO_D, ONE_D, TWO_D, THREE_D, FOUR_D, FIVE_D, SIX_D, SEVEN_D, EIGHT_D, NINE_D };
	private String[] smallRolePaths = { SMALL_TSAI, SMALL_ZHANG, SMALL_SHU, SMALL_ZHOU, SMALL_WANG };
	private String[] rolePaths = { CHOSE_CHEERBALL, CHOSE_BASKETBALL, CHOSE_BADMINTON, CHOSE_BASEBALL,
			CHOSE_VOLLEYBALL };
	private String[] buttonPaths = { PAUSE, REHEARSE, PLAY_BUTTON };
	private String[] button = { B_HOME, CONTINUE_BUTTON, B_SHOP, B_INFO };
	private String[] isPausePaths = { SHADOW, PAUSE_PANEL, PAUSE_TITLE };

	private String[] leftPanelPaths = { LEFT_PANEL, TIME_PANEL };
	private String[] rightPanelPaths = { RIGHT_PANEL, PAUSE, REHEARSE, SKILL_BANNER };

	public GameStartScene(SceneController sceneController, String rolePath) {
		super(sceneController);
		skill = 0;
		preCountDown = 0;
		System.out.println("GameScene constructor rolePath=" + rolePath);
		timeUpOver = false;
		timeUpStart = false;

		for (int i = 0; i < rolePaths.length; i++) {
			if (rolePaths[i].equals(rolePath)) {
				this.rolePath = smallRolePaths[i];
			}
		}
		System.out.println("GameScene this.rolePath=" + this.rolePath);

		this.skillPaintCount = 1;
		this.skillLevel = 0;
		this.units = 0; // 倒數個位數
		this.tens = 0; // 倒數十位數
		this.time = 60; // 倒數總秒數
		this.ballAmount = 0; // 檯面上總球數
		this.score = 0; // 分數
		this.totalScore = 0; // 累積分數
		this.scoreUnits = new int[10];
		this.coinUnits = new int[10];
		this.expUnits = new int[10];
		this.digsMove = 0;
		this.countDownActive = 0;
		this.exp = 0;
		this.coin = 0;
		this.mmcl = new MyMouseCommandListener();
		isPause = false;
	}

	@Override
	public void sceneBegin() {
		countDown = 0;
		delay = new Delay(0);
		timeDelay = new Delay(Global.UPDATE_TIMES_PER_SEC);// 倒數秒數延遲時間
		bricks = new ArrayList<Brick>();// 最底下的碰撞框
		numbers = new ArrayList<Number>();
		digNumbers = new ArrayList<Number>();
		linkBalls = new ArrayList<Ball>();
		listOfBalls = new ArrayList<List<Ball>>();
		clean = new AudioObject("Clean", AudioPath.CLEAN);

		// 元件 8
		imgs.add(new Img(ImgPath.BK_MAIN, (int) (0 * Global.ADJ_X), (int) (0 * Global.ADJ_Y), true));
		imgs.add(new Img(ImgPath.TIME_PANEL, (int) (0.022388 * Global.ADJ_X), (int) (0.027 * Global.ADJ_Y), true));
		imgs.add(new Img(ImgPath.LEFT_PANEL, (int) (0.017 * Global.ADJ_X), (int) (0.212 * Global.ADJ_Y), true));
		imgs.add(new Img(ImgPath.RIGHT_PANEL, (int) (0.5 * Global.ADJ_X), (int) (0.022 * Global.ADJ_Y), true));
		imgs.add(new Img(ImgPath.SKILL_BANNER, (int) (0.672 * Global.ADJ_X), (int) (0.865 * Global.ADJ_Y), true));
		imgs.add(new Img(ImgPath.PLAY_BUTTON, (int) (0.903 * Global.ADJ_X), (int) (0.8675 * Global.ADJ_Y), true));
		imgs.add(new Img(ImgPath.PAUSE, (int) (0.515 * Global.ADJ_X), (int) (0.873 * Global.ADJ_Y), true));
		imgs.add(new Img(ImgPath.REHEARSE, (int) (0.58 * Global.ADJ_X), (int) (0.873 * Global.ADJ_Y), true));

		// small role 5
		imgs.add(new Img(ImgPath.SMALL_TSAI, (int) (-0.012 * Global.ADJ_X), (int) (0.29 * Global.ADJ_Y), true));
		imgs.add(new Img(ImgPath.SMALL_ZHANG, (int) (0.021 * Global.ADJ_X), (int) (0.32 * Global.ADJ_Y), true));
		imgs.add(new Img(ImgPath.SMALL_SHU, (int) (-0.03 * Global.ADJ_X), (int) (0.35 * Global.ADJ_Y), true));
		imgs.add(new Img(ImgPath.SMALL_ZHOU, (int) (-0.01 * Global.ADJ_X), (int) (0.312 * Global.ADJ_Y), true));
		imgs.add(new Img(ImgPath.SMALL_WANG, (int) (0.016 * Global.ADJ_X), (int) (0.316 * Global.ADJ_Y), true));

		// isPause Scene 4 + 3
		imgs.add(new Img(ImgPath.B_HOME, (int) (0.373 * Global.ADJ_X), (int) (0.51 * Global.ADJ_Y), true));
		imgs.get(B_HOME).importPic(B_HOME2);
		imgs.add(new Img(ImgPath.CONTINUE_BUTTON, (int) (0.431 * Global.ADJ_X), (int) (0.51 * Global.ADJ_Y), true));
		imgs.get(CONTINUE_BUTTON).importPic(CONTINUE_BUTTON2);
		imgs.add(new Img(ImgPath.B_SHOP, (int) (0.49 * Global.ADJ_X), (int) (0.512 * Global.ADJ_Y), true));
		imgs.get(B_SHOP).importPic(B_SHOP2);
		imgs.add(new Img(ImgPath.B_INFO, (int) (0.547 * Global.ADJ_X), (int) (0.51 * Global.ADJ_Y), true));
		imgs.get(B_INFO).importPic(B_INFO2);

		imgs.add(new Img(ImgPath.PAUSE_PANEL, (int) (0.35 * Global.ADJ_X), (int) (0.36 * Global.ADJ_Y), true));
		imgs.add(new Img(ImgPath.PAUSE_TITLE, (int) (0.4 * Global.ADJ_X), (int) (0.408 * Global.ADJ_Y), true));
		imgs.add(new Img(ImgPath.SHADOW, 0, 0, true));

		// Time's up Scene 2
		imgs.add(new Img(ImgPath.SPOTLIGHT, (int) (0 * Global.ADJ_X), (int) (0 * Global.ADJ_Y), true));
		imgs.add(new Img(ImgPath.TIME_UP, (int) (0.18 * Global.ADJ_X), (int) (-0.35 * Global.ADJ_Y), true));
		imgs.add(new Img(ImgPath.STAR));

		// gray dig8
		// ------------score
		float digSize1 = 0.6f;
//        for (int i = 0; i < 10; i++) {
		imgs.add(new Img(ImgPath.EIGHT_D_GRAY));
//            		, (int) (0.19 * Global.ADJ_X + i * 28),
//                    (int) (0.43 * Global.ADJ_Y), (int) (46 * digSize1), (int) (70 * digSize1), true));
//        }
		// ------------coin
		float digSize2 = 0.45f;
//        for (int i = 0; i < 10; i++) {
//            imgs.add(new Img(ImgPath.EIGHT_D_GRAY));
//            		, (int) (Global.SCREEN_X * 0.255 * Global.ADJ + i * 20),
//                    (int) (Global.SCREEN_Y * 0.577 * Global.ADJ), (int) (46 * digSize2), (int) (70 * digSize2), true));
//        }
		// ------------exp
//        for (int i = 0; i < 10; i++) {
//            imgs.add(new Img(ImgPath.EIGHT_D_GRAY, (int) (Global.SCREEN_X * 0.255 * Global.ADJ + i * 20),
//                    (int) (Global.SCREEN_Y * 0.679 * Global.ADJ), (int) (46 * digSize2), (int) (70 * digSize2), true));
//        }

		xs = genXPoint();
		for (int i = 0; i < Global.COLUMN; i++) {
			List<Ball> columnOfBalls = new ArrayList<>();
			listOfBalls.add(columnOfBalls);
		}
		genNumbers(numbers, ImgPath.numbers());
		genNumbers(digNumbers, ImgPath.digNumbers());
		genRect(xs);
		delay.start();
		timeDelay.start();
	}// end of begin

	@Override
	public void sceneUpdate() {
		if (countDown > 0 && preCountDown == 0) {
			Global.log("countDown=" + countDown);
			countDown--;

		}
		if (countDownActive > 0) {
			Global.log("countDownActive=" + countDownActive);
			countDownActive--;
			Global.log("skill(CDA)=" + skill);
		}
		if (preCountDown > 0) {
			Global.log("preCountDown=" + preCountDown);
			preCountDown--;
		}
		if ((!isPause && countDown == 0 && this.time >= 0) || (preCountDown > 0 && countDown > 0)) {
			checkIfLess(listOfBalls);
			for (int i = 0; i < listOfBalls.size(); i++) {
				for (int j = 0; j < listOfBalls.get(i).size(); j++) {
					if (!listOfBalls.get(i).get(j).move()) {
						listOfBalls.get(i).remove(j);
						ballAmount--;
					}
				}
			}

			if (isSkillLevelFull(this.skillLevel)) {
				Global.log("----------------------------[skillTrig()]");
				skillTrig();
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
			// for generating score, exp and coin dig numbers
			scoreUnitsGen(scoreUnits, totalScore);
			expUnitsGen(expUnits);
			coinUnitsGen(coinUnits);
			// 倒數計時器
			if (this.time >= 0 && timeDelay.isTrig()) {
				units = time % 10;
				tens = time / 10;
				this.time -= 1;
			}

		}

		timeUpStart = (this.time == -1);
		if (timeUpStart) {
			frameAfterTimeUp++;
			// rightPanelPaths={RIGHT_PANEL,PAUSE,REHEARSE,SKILL_BANNER};
			// (int) (0.5 * ADJ_Y);
			for (int i = 0; i < rightPanelPaths.length; i++) {
				if (rightPanelDeltX < (1.1 * ADJ_X)) {
					imgs.get(rightPanelPaths[i]).offset(1, 0);
					rightPanelDeltX++;
				}
			}
			for (int i = 0; i < listOfBalls.size(); i++) {
				for (int j = 0; j < listOfBalls.get(i).size(); j++) {
					listOfBalls.get(i).remove(j);
				}
			}
			// leftPanelPaths = {LEFT_PANEL, TIME_PANEL};
			if (leftPanelDeltX < (0.135 * ADJ_X)) {
				imgs.get(TIME_PANEL).offset(0, -1);
				imgs.get(LEFT_PANEL).offset(1, 0);
				imgs.get(rolePath).offset(1, 0);
				leftPanelDeltX++;
				this.digsMove += 2;
			}
			if (leftPanelDeltX < (0.10 * ADJ_X)) {
				imgs.get(TIME_UP).offset(0, 1);
			}
		}

		if (countDown == 0 && countDownActive == 0) {
			this.skill = 0;
		}
//		if (countDownActive == 0) {
//			this.skill = 0;
//		}

		switch (skill) {
		case 1:// CHOSE_CHEERBALL
				// System.out.println("update()_skill "+skill);
			if (countDownActive == skillCDtime[skill]) {
				this.time += 5;
				Global.log("Time:" + this.time);
			}

			break;
		case 2:// CHOSE_BASKETBALL
			if (countDown == 1) {
				for (int i = 2; i < 4; i++) {
					listOfBalls.get(2).remove(i);
					listOfBalls.get(4).remove(i);
				}
				for (int i = 1; i < 3; i++) {
					listOfBalls.get(3).remove(i);
					// System.out.println(i+" "+listOfBalls.get(3).get(i).getY());
				}
			}
			// System.out.println("update()_skill " + skill);
			break;
		case 3:// CHOSE_BADMINTON
			if (countDownActive == skillCDtime[skill]) {
					Global.log("time: "+this.time);
					this.totalScore += this.score * 1.4;
			}
			// System.out.println("update()_skill "+skill);
			break;
		case 4:// CHOSE_BASEBALL
				// System.out.println("update()_skill "+skill);
			if (countDown == 1) {
				for (int i = 0; i < listOfBalls.size(); i++) {
					listOfBalls.get(i).remove(4);
				}
				for (int i = 0; i < listOfBalls.get(3).size(); i++) {
					if (i == 4) {
						continue;
					}
					listOfBalls.get(3).remove(i);
				}
			}

			break;
		case 5:// CHOSE_VOLLEYBALL
			if (countDown == 1) {
				for (int i = 0; i < 10; i++) {
					int c = (int) (Math.random() * listOfBalls.size());
					int r = (int) (Math.random() * listOfBalls.get(0).size());
					listOfBalls.get(c).remove(r);
				}
			}
			// System.out.println("update()_skill "+skill);
			break;
		default:
			break;
		}

	} // update()

	@Override
	public void sceneEnd() {
		delay.stop();
		delay = null;
	}

	@Override
	public void paint(Graphics g) {
		// 元件部分
		for (int i = 0; i < componentPaths.length; i++) {
			imgs.get(componentPaths[i]).paint(g);
		}
		for (int i = 0; i < buttonPaths.length - 1; i++) {
			imgs.get(buttonPaths[i]).paint(g);
		}
		for (int i = 0; i < listOfBalls.size(); i++) {// 畫球COLUMN
			for (int j = 0; j < listOfBalls.get(i).size(); j++) {
				listOfBalls.get(i).get(j).paint(g);
			}
		}

		// 時間、分數、金錢、經驗
		drawPanel(g);

		// 左側小人物
		imgs.get(rolePath).paint(g);
		// --------------暫停畫面
		if (isPause) {
			for (int i = 0; i < isPausePaths.length; i++) {
				imgs.get(isPausePaths[i]).paint(g);
			}
//            //button Part
//            for (int i = 0; i < button.length; i++) {
//                if (!mmcl.buttonSelectPath.equals(button[i])) {
//                    imgs.get(button[i]).switchNowImage(0);
//                }
//            }
//            imgs.get(mmcl.buttonSelectPath).switchNowImage(1);
			for (int i = 0; i < button.length; i++) {
				imgs.get(button[i]).paint(g);
			}
		}
		if (this.time <= -1) {

		}
		if (leftPanelDeltX >= (0.05 * ADJ_X) - 1) {
			imgs.get(TIME_UP).paint(g);
		}
		drawScore(g);
		drawExp(g);
		drawCoin(g);

		if (timeUpStart) {

		} else {
			if (linkBalls.size() >= 2) {
				Graphics2D g2 = (Graphics2D) g;
				g2.setColor(Color.CYAN);
				g2.setStroke(new BasicStroke(10));

				for (int i = 0; i < linkBalls.size() - 1; i++) {
					g2.drawLine(linkBalls.get(i).rect().centerX(), linkBalls.get(i).rect().centerY(),
							linkBalls.get(i + 1).rect().centerX(), linkBalls.get(i + 1).rect().centerY());
				}
				g2.setColor(Color.black);
			}
		}
		// -----------------------------------------Skill part
		switch (skill) {
		case 1:
			g.setColor(Color.RED);
			g.setFont(new Font("微軟正黑體", Font.BOLD, 100));
			g.drawString("Time +5", Global.XstartPoint - 2 * Global.UNIT_X, 360);
			Global.log("paint_skill=" + rolePath);
			break;
		case 2:// basketball skill
			if (countDown <= (skillCDtime[skill] - skillCDtime[skill] / 3 * 1)) {
				// paint center star
				g.drawImage(imgs.get(STAR).getImg(), listOfBalls.get(3).get(2).rect().left(),
						listOfBalls.get(3).get(2).rect().top(), null);
			}
			if (countDown <= (skillCDtime[skill] - skillCDtime[skill] / 3 * 2)) {
				// paint external star
				g.drawImage(imgs.get(STAR).getImg(), listOfBalls.get(2).get(2).rect().left(),
						listOfBalls.get(2).get(2).rect().top(), null);
				g.drawImage(imgs.get(STAR).getImg(), listOfBalls.get(2).get(3).rect().left(),
						listOfBalls.get(2).get(3).rect().top(), null);
				g.drawImage(imgs.get(STAR).getImg(), listOfBalls.get(3).get(1).rect().left(),
						listOfBalls.get(3).get(1).rect().top(), null);
				g.drawImage(imgs.get(STAR).getImg(), listOfBalls.get(3).get(3).rect().left(),
						listOfBalls.get(3).get(3).rect().top(), null);
				g.drawImage(imgs.get(STAR).getImg(), listOfBalls.get(4).get(2).rect().left(),
						listOfBalls.get(4).get(2).rect().top(), null);
				g.drawImage(imgs.get(STAR).getImg(), listOfBalls.get(4).get(3).rect().left(),
						listOfBalls.get(4).get(3).rect().top(), null);
			}
			break;
		case 3:// CHOSE_BADMINTON
				g.setColor(Color.RED);
				g.setFont(new Font("微軟正黑體", Font.BOLD, 100));
				g.drawString("Score X 2", Global.XstartPoint - 2 * Global.UNIT_X, 360);
				Global.log("paint_skill=" + rolePath);
			break;
		case 4:// baseball skill

			if (countDown <= (skillCDtime[skill] - skillCDtime[skill] / 3 * 1)) {
				// paint center row star
				for (int i = 0; i < listOfBalls.size(); i++) {
					g.drawImage(imgs.get(STAR).getImg(), listOfBalls.get(i).get(4).rect().left(),
							listOfBalls.get(i).get(4).rect().top(), null);
				}
			}
			if (countDown <= (skillCDtime[skill] - skillCDtime[skill] / 3 * 2)) {
				// paint center column star
				for (int i = 0; i < listOfBalls.get(3).size(); i++) {
					if (i == 4) {
						continue;
					}
					g.drawImage(imgs.get(STAR).getImg(), listOfBalls.get(3).get(i).rect().left(),
							listOfBalls.get(3).get(i).rect().top(), null);
				}
			}
//			Global.log("paint_skill=" + rolePath);
			break;
		case 5:// volleyball skill
			if (countDown <= (skillCDtime[skill] - skillCDtime[skill] / 4 * 1)) {
				// paint random star1
			}
			if (countDown <= (skillCDtime[skill] - skillCDtime[skill] / 4 * 2)) {
				// paint random star2
			}
			if (countDown <= (skillCDtime[skill] - skillCDtime[skill] / 4 * 3)) {
				// paint random star3
			}
//			Global.log("paint_skill=" + rolePath);
			break;
		default:
			break;
		}

	}// end of paint();

	private void drawPanel(Graphics g) {
		// 時間、分數、金幣、經驗值
		if (timeUpStart) {// 時間到時
			for (int i = 0; i < 10; i++) {
				// score panel
				g.drawImage(imgs.get(EIGHT_D_GRAY).getImg(), (int) (0.19 * Global.ADJ_X + i * 28 + this.digsMove),
						(int) (0.43 * Global.ADJ_Y), (int) (46 * 0.6f), (int) (70 * 0.6f), null);

				// coin panel
				g.drawImage(imgs.get(EIGHT_D_GRAY).getImg(),
						(int) (Global.SCREEN_X * 0.255 * Global.ADJ + i * 20 + this.digsMove),
						(int) (Global.SCREEN_Y * 0.577 * Global.ADJ), (int) (46 * 0.45f), (int) (70 * 0.45f), null);

				// exp panel
				g.drawImage(imgs.get(EIGHT_D_GRAY).getImg(),
						(int) (Global.SCREEN_X * 0.255 * Global.ADJ + i * 20 + this.digsMove),
						(int) (Global.SCREEN_Y * 0.679 * Global.ADJ), (int) (46 * 0.45f), (int) (70 * 0.45f), null);

			}
		} else {
			for (int i = 0; i < 10; i++) {
				float sizeCD = 0.8f; // 數字圖片縮放比例
				g.drawImage(numbers.get(units).getImg(), 112, 38, null);// 個位數
				g.drawImage(numbers.get(tens).getImg(), 78, 38, null);// 十位數

				// score panel
				g.drawImage(imgs.get(EIGHT_D_GRAY).getImg(), (int) (0.19 * Global.ADJ_X + i * 28),
						(int) (0.43 * Global.ADJ_Y), (int) (46 * 0.6f), (int) (70 * 0.6f), null);

				// coin panel
				g.drawImage(imgs.get(EIGHT_D_GRAY).getImg(), (int) (Global.SCREEN_X * 0.255 * Global.ADJ + i * 20),
						(int) (Global.SCREEN_Y * 0.577 * Global.ADJ), (int) (46 * 0.45f), (int) (70 * 0.45f), null);

				// exp panel
				g.drawImage(imgs.get(EIGHT_D_GRAY).getImg(), (int) (Global.SCREEN_X * 0.255 * Global.ADJ + i * 20),
						(int) (Global.SCREEN_Y * 0.679 * Global.ADJ), (int) (46 * 0.45f), (int) (70 * 0.45f), null);

			}
		}
	}

	private void drawScore(Graphics g) {
//		if (timeUpStart) {// 時間到時
		for (int i = 0; i < 10; i++) {
			if (timeUpStart) {// 時間到時
				if (totalScore > 0) { // 時間結束成績顯示移動
					if (this.totalScore / (int) Math.pow(10, i) > 0) {
						g.drawImage(digNumbers.get(scoreUnits[i]).getImg(),
								(int) (0.19 * Global.ADJ_X + (252 - i * 28) + this.digsMove),
								(int) (0.43 * Global.ADJ_Y), (int) (46 * 0.6f), (int) (70 * 0.6f), null);
					}
				}
			} else {
				if (totalScore == 0) {
					g.drawImage(digNumbers.get(scoreUnits[0]).getImg(), (int) (0.19 * Global.ADJ_X + (252)),
							(int) (0.43 * Global.ADJ_Y), (int) (46 * 0.6f), (int) (70 * 0.6f), null);
				} else if (totalScore > 0) {

					if (this.totalScore / (int) Math.pow(10, i) > 0) {
						g.drawImage(digNumbers.get(scoreUnits[i]).getImg(),
								(int) (0.19 * Global.ADJ_X + (252 - i * 28)), (int) (0.43 * Global.ADJ_Y),
								(int) (46 * 0.6f), (int) (70 * 0.6f), null);
					}
				}
			}
		}
	}

	private void drawExp(Graphics g) {
//		if (timeUpStart) {// 時間到時
		for (int i = 0; i < 10; i++) {
			if (timeUpStart) {// 時間到時
				if (exp == 0) {
					g.drawImage(digNumbers.get(expUnits[0]).getImg(),
							((int) (Global.SCREEN_X * 0.255 * Global.ADJ + 180) + this.digsMove),
							(int) (Global.SCREEN_Y * 0.679 * Global.ADJ), (int) (46 * 0.45f), (int) (int) (70 * 0.45f),
							null);
				} else if (exp > 0) { // 時間結束成績顯示移動
					if (this.exp / (int) Math.pow(10, i) > 0) {
						g.drawImage(digNumbers.get(expUnits[i]).getImg(),
								((int) (Global.SCREEN_X * 0.255 * Global.ADJ + 180 - 20 * i) + this.digsMove),
								(int) (Global.SCREEN_Y * 0.679 * Global.ADJ), (int) (46 * 0.45f),
								(int) (int) (70 * 0.45f), null);
					}
				}
			} else {
				if (exp == 0) {
					g.drawImage(digNumbers.get(expUnits[0]).getImg(),
							((int) (Global.SCREEN_X * 0.255 * Global.ADJ + 180)),
							(int) (Global.SCREEN_Y * 0.679 * Global.ADJ), (int) (46 * 0.45f), (int) (int) (70 * 0.45f),
							null);
				} else if (exp > 0) {
					if (this.exp / (int) Math.pow(10, i) > 0) {
						g.drawImage(digNumbers.get(expUnits[i]).getImg(),
								((int) (Global.SCREEN_X * 0.255 * Global.ADJ + 180 - 20 * i)),
								(int) (Global.SCREEN_Y * 0.679 * Global.ADJ), (int) (46 * 0.45f),
								(int) (int) (70 * 0.45f), null);
					}
				}
			}
		}
	}

	private void drawCoin(Graphics g) {
//		if (timeUpStart) {// 時間到時
		for (int i = 0; i < 10; i++) {
			if (timeUpStart) {// 時間到時
				if (coin > 0) { // 時間結束成績顯示移動
					if (this.coin / (int) Math.pow(10, i) > 0) {
						g.drawImage(digNumbers.get(coinUnits[i]).getImg(),
								(int) (Global.SCREEN_X * 0.255 * Global.ADJ + (180 - i * 20) + this.digsMove),
								(int) (Global.SCREEN_Y * 0.577 * Global.ADJ), (int) (46 * 0.45f), (int) (70 * 0.45f),
								null);
					}
				} else if (this.coin == 0) {
					g.drawImage(digNumbers.get(coinUnits[0]).getImg(),
							(int) (Global.SCREEN_X * 0.255 * Global.ADJ + 180 + this.digsMove),
							(int) (Global.SCREEN_Y * 0.577 * Global.ADJ), (int) (46 * 0.45f), (int) (70 * 0.45f), null);
				}
			} else {
				if (coin == 0) {
					g.drawImage(digNumbers.get(coinUnits[0]).getImg(),
							(int) (Global.SCREEN_X * 0.255 * Global.ADJ + 180),
							(int) (Global.SCREEN_Y * 0.577 * Global.ADJ), (int) (46 * 0.45f), (int) (70 * 0.45f), null);
				} else if (coin > 0) {
					if (this.coin / (int) Math.pow(10, i) > 0) {
						g.drawImage(digNumbers.get(coinUnits[i]).getImg(),
								(int) (Global.SCREEN_X * 0.255 * Global.ADJ + (180 - i * 20)),
								(int) (Global.SCREEN_Y * 0.577 * Global.ADJ), (int) (46 * 0.45f), (int) (70 * 0.45f),
								null);
					}
				}
			}
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

	public void genNumbers(ArrayList<Number> numbers, String[] path) {
		String[] numImg = path;
		for (int i = 0; i < 10; i++) {
			numbers.add(new Number(numImg[i]));
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

	private void gameOperation(MouseEvent e, MouseState state) {
		// 在球盤範圍內
		if (e.getX() >= Global.XstartPoint && e.getX() <= Global.XendPoint && e.getY() >= Global.YstartPoint
				&& e.getY() <= Global.YendPoint) {
			if (preCountDown == 0) {
				// 當滑鼠是Pressed 且連線的球數量為0的時候，將第一顆球加進連線的陣列
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
				// 當滑鼠是Dragged 且連線的球數量不為0的時候，
				if (state.toString().equals("DRAGGED") && (linkBalls.size() != 0)) {
					for (int i = 0; i < listOfBalls.size(); i++) {
						for (int j = 0; j < listOfBalls.get(i).size(); j++) {
							if (!isExisted(linkBalls, getBallInArea(e))// 如果滑鼠所在位置不在連線的球的陣列裡
									&& listOfBalls.get(i).get(j).equals(getBallInArea(e))
									&& isTheSameType(linkBalls.get(0), getBallInArea(e))// 是否跟連線球的第一顆為同種球
									&& (Math.abs(linkBalls.get(linkBalls.size() - 1).rect().centerX()
											- getBallInArea(e).rect().centerX())) <= Global.UNIT_X - 20
									&& (Math.abs(linkBalls.get(linkBalls.size() - 1).rect().centerY()
											- getBallInArea(e).rect().centerY())) <= Global.UNIT_Y - 20) {
								linkBalls.add(getBallInArea(e));
							} else if (isExisted(linkBalls, getBallInArea(e))// 已在連線球的陣列裡
									&& !((linkBalls.get(linkBalls.size() - 1).equals(getBallInArea(e))))// 最後一顆球與滑鼠所在位置的球不同
									&& (Math.abs(linkBalls.get(linkBalls.size() - 1).rect().centerY()
											- getBallInArea(e).rect().centerY())) <= Global.UNIT_Y - 20) { // 最後一顆球中心點與球所在位置的中心點
								for (int k = 0; k < linkBalls.size(); k++) {
									if (linkBalls.get(k).equals(getBallInArea(e))) {
										for (int l = k + 1; l < linkBalls.size(); l++) {
											linkBalls.remove(l);// 移除掉最後一顆
										}
									}
								}
							}
						}
					}
				}
			}
			removeLinkBalls(e, state);
		} else// 超出球盤範圍，自動消除大於3顆球的連線
		if (e.getX() <= Global.XstartPoint || e.getX() >= Global.XendPoint || e.getY() <= Global.YstartPoint
				|| e.getY() >= Global.YendPoint || state.toString().equals("EXITED")) {
			if (linkBalls.size() >= 3) {
				for (int i = 0; i < linkBalls.size(); i++) {
					isTheSameObject(listOfBalls, linkBalls.get(i));
				}
				getScore();
				getSkillLevel();
				clean.getAudio().play();
				linkBalls.clear();
			}
		}
	}

	private void removeLinkBalls(MouseEvent e, MouseState state) {
		if (state.toString().equals("RELEASED")) {
			if (linkBalls.size() >= 3) {
				for (int i = 0; i < linkBalls.size(); i++) {
					isTheSameObject(listOfBalls, linkBalls.get(i));
				}
				getScore();
				getSkillLevel();
				if (linkBalls.size() >= 5) {
					experience(linkBalls);
				}
				if (linkBalls.size() >= 4) {
					coin(linkBalls);
				}

				clean.getAudio().play();
				linkBalls.clear();
			}
			if (linkBalls.size() < 3) {
				linkBalls.clear();
			}
		}
	}

	private boolean isSkillLevelFull(int skillLevel) {
		switch (this.rolePath) {
		case SMALL_TSAI:
			if (skillLevel >= 5) {
				return true;
			}
		case SMALL_ZHANG:
			if (skillLevel >= 5) {
				return true;
			}
		case CHOSE_BADMINTON:
			if (skillLevel >= 5) {
				return true;
			}
		case CHOSE_BASEBALL:
			if (skillLevel >= 5) {
				return true;
			}
		case CHOSE_VOLLEYBALL:
			if (skillLevel >= 5) {
				return true;
			}
		}
		return false;
	}

	private boolean skillTrig() {// 技能條滿時發動
		preCountDown = 30;
		switch (this.rolePath) {
		case SMALL_TSAI:// Tsai-cheerball
			this.skill = 1;
			this.countDownActive = skillCDtime[skill];
			skillLevel = 0;
//			Global.log("skillTrig()_tsai");
			break;
		case SMALL_ZHANG:// Zhang-baseketball
			skillLevel = 0;
			this.skill = 2;
			this.countDown = skillCDtime[skill];
			break;
		case SMALL_SHU:// Shu-shuttlecock
			skillLevel = 0;
			this.skill = 3;
			this.countDownActive = skillCDtime[skill];
			break;
		case SMALL_ZHOU:// Zhou-baseball
			skillLevel = 0;
			this.skill = 4;
			this.countDown = skillCDtime[skill];
			break;

		case SMALL_WANG:// Wang-volleyball
			skillLevel = 0;
			this.skill = 5;
			this.countDown = skillCDtime[skill];
			break;
		}
		Global.log("Skill Trig");
//		Global.log("Skill Level reset to 0");
//		Global.log("skillBalls(trig): "+ skillLinkBalls.size());
//		
//		
//		Global.log("skillBalls(trig2): "+ skillLinkBalls.size());
		return true;
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

	private void experience(ArrayList<Ball> linkBalls) {
		if (this.rolePath.equals(SMALL_TSAI) && linkBalls.get(0).getName().equals("Cheerball")) {
			this.exp += linkBalls.size() * 2;
		} else if (this.rolePath.equals(SMALL_ZHANG) && linkBalls.get(0).getName().equals("Basketball")) {
			this.exp += linkBalls.size() * 2;
		} else if (this.rolePath.equals(SMALL_ZHOU) && linkBalls.get(0).getName().equals("Baseball")) {
			this.exp += linkBalls.size() * 2;
			Global.log("experience: " + this.exp);
		} else if (this.rolePath.equals(SMALL_WANG) && linkBalls.get(0).getName().equals("Volleyball")) {
			this.exp += linkBalls.size() * 2;
		} else if (this.rolePath.equals(SMALL_SHU) && linkBalls.get(0).getName().equals("Shuttlecock")) {
			this.exp += linkBalls.size() * 2;
		} else {
			this.exp += linkBalls.size();
		}
	}

	private void coin(ArrayList<Ball> linkBalls) {
		if (this.rolePath.equals(SMALL_TSAI) && linkBalls.get(0).getName().equals("Cheerball")) {
			this.coin += linkBalls.size() * 2;
		} else if (this.rolePath.equals(SMALL_ZHANG) && linkBalls.get(0).getName().equals("Basketball")) {
			this.coin += linkBalls.size() * 2;
		} else if (this.rolePath.equals(SMALL_ZHOU) && linkBalls.get(0).getName().equals("Baseball")) {
			this.coin += linkBalls.size() * 2;
			Global.log("coin: " + this.exp);
		} else if (this.rolePath.equals(SMALL_WANG) && linkBalls.get(0).getName().equals("Volleyball")) {
			this.coin += linkBalls.size() * 2;
		} else if (this.rolePath.equals(SMALL_SHU) && linkBalls.get(0).getName().equals("Shuttlecock")) {
			this.coin += linkBalls.size() * 2;
		} else {
			this.coin += linkBalls.size();
		}
	}

	private void getSkillLevel() {
		this.skillLevel++;
		Global.log("SkillLevel + 1, SkillLevel:" + this.skillLevel);
	}

	private void scoreUnitsGen(int[] scoreUnits, int totalScore) {
		for (int i = 0; i < scoreUnits.length; i++) {
			int devid = (int) (Math.pow(10, i));
			scoreUnits[i] = (totalScore / devid) % 10;
		}
	}

	private void expUnitsGen(int[] expUnits) {
		for (int i = 0; i < expUnits.length; i++) {
			int devid = (int) (Math.pow(10, i));
			expUnits[i] = (this.exp / devid) % 10;
		}
	}

	private void coinUnitsGen(int[] coinUnits) {
		for (int i = 0; i < coinUnits.length; i++) {
			int devid = (int) (Math.pow(10, i));
			coinUnits[i] = (this.coin / devid) % 10;
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

	private void isTheSameObject(List<List<Ball>> listOfBalls, Ball ball) {// remove balls
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

	private boolean isTheSameType(Ball b1, Ball b2) {
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
