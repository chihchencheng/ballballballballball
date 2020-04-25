package scenes;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
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

			if (imgs.get(5).isInside(e.getX(), e.getY()) && state == MouseState.CLICKED) {
				timeDelay.pause();
			}
			if (imgs.get(6).isInside(e.getX(), e.getY()) && state == MouseState.CLICKED) {
				for (int i = 0; i < listOfBalls.size(); i++) {
					listOfBalls.get(i).clear();
				}
				ballAmount = 0;
			}
			
			//球連線及消除操作
			gameOperation(e, state);
		}// end of mouseTrig
	}// end of inner class

	private MyMouseCommandListener mmcl;
    private ArrayList<Brick> bricks;
    private ArrayList<Number> numbers;
    private ArrayList<Number> digNumbers;
    private ArrayList<Ball> linkBalls;
    private ArrayList<Ball> skillLinkBalls;
    private List<List<Ball>> listOfBalls;
    private ArrayList<Img> imgs;
    private Img bk;
    private Img skillTrigImg;
    private Img star;
    private int ballAmount;
    private int roleNum;

	private int[] xs;
	private int time;
	private int units;// 秒個位數
	private int tens;// 秒十位數
	private int skillLevel;
	private int score;
	private int totalScore;
	private int countDown;
	private int skillPaintCount;
	private int removeSkilLinkBalls;
	private Delay delay;
	private Delay timeDelay;
	
	private AudioObject clean;
	private AudioObject houwo;
	
    public GameStartScene(SceneController sceneController, int roleNum) {
        super(sceneController);
        bk = new Img(ImgPath.BK_MAIN, 0, 0,
                (int) (Global.SCREEN_X * Global.ADJ), (int) (Global.SCREEN_Y * Global.ADJ), true);//載入背景圖片
        this.roleNum = roleNum + 7;

		imgs = new ArrayList<>();

		// 角色
		sceneBegin();
		
		this.skillPaintCount = 1;
		this.countDown = 0;
		this.skillLevel = 0;
		this.units = 0;// 倒數個位數
		this.tens = 6;// 倒數十位數
		this.time = 60;// 倒數總秒數
		this.ballAmount = 0;// 檯面上總球數
		this.score = 0;
		this.totalScore = 0;
		this.removeSkilLinkBalls = 0;
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
		skillLinkBalls =new ArrayList<Ball>();
		clean = new AudioObject("Clean", AudioPath.CLEAN);
		houwo = new AudioObject("HouWo",AudioPath.HOWO);

		 //元件
		 imgs.add(new Img(ImgPath.TIME_PANEL, (int) (Global.SCREEN_X * 0.022388 * Global.ADJ), (int) (Global.SCREEN_Y * 0.027 * Global.ADJ), true));
		 imgs.add(new Img(ImgPath.LEFT_PANEL, (int) (Global.SCREEN_X * 0.017 * Global.ADJ), (int) (Global.SCREEN_Y * 0.212 * Global.ADJ), true));
		 imgs.add(new Img(ImgPath.RIGHT_PANEL, (int) (Global.SCREEN_X * 0.5 * Global.ADJ), (int) (Global.SCREEN_Y * 0.022 * Global.ADJ), true));
		 imgs.add(new Img(ImgPath.SKILL_BANNER, (int) (Global.SCREEN_X * 0.672 * Global.ADJ), (int) (Global.SCREEN_Y * 0.865 * Global.ADJ), true));
		 imgs.add(new Img(ImgPath.PLAY_BUTTON, (int) (Global.SCREEN_X * 0.903 * Global.ADJ), (int) (Global.SCREEN_Y * 0.8675 * Global.ADJ), true));
		 imgs.add(new Img(ImgPath.PAUSE, (int) (Global.SCREEN_X * 0.515 * Global.ADJ), (int) (Global.SCREEN_Y * 0.873 * Global.ADJ), true));
		 imgs.add(new Img(ImgPath.REHEARSE, (int) (Global.SCREEN_X * 0.58 * Global.ADJ), (int) (Global.SCREEN_Y * 0.873 * Global.ADJ), true));
		 //small role
		 imgs.add(new Img(ImgPath.SMALL_TSAI, (int) (Global.SCREEN_X * -0.012 * Global.ADJ), (int) (Global.SCREEN_Y * 0.29 * Global.ADJ), true));
		 imgs.add(new Img(ImgPath.SMALL_ZHANG, (int) (Global.SCREEN_X * 0.021 * Global.ADJ), (int) (Global.SCREEN_Y * 0.32 * Global.ADJ), true));
		 imgs.add(new Img(ImgPath.SMALL_SHU, (int) (Global.SCREEN_X * -0.03 * Global.ADJ), (int) (Global.SCREEN_Y * 0.35 * Global.ADJ), true));
		 imgs.add(new Img(ImgPath.SMALL_ZHOU, (int) (Global.SCREEN_X * -0.01 * Global.ADJ), (int) (Global.SCREEN_Y * 0.312 * Global.ADJ), true));
		 imgs.add(new Img(ImgPath.SMALL_WANG, (int) (Global.SCREEN_X * 0.016 * Global.ADJ), (int) (Global.SCREEN_Y * 0.316 * Global.ADJ), true));
		 
		 skillTrigImg = new Img(ImgPath.TSAI,Global.XendPoint, 0,(int)(960*Global.ADJ),(int)(1179*Global.ADJ),true);//
		 star = new Img(ImgPath.STAR);
		 //gray dig8
        //------------score
        float digSize1 = 0.6f;
        for (int i = 0; i < 10; i++) {
            imgs.add(new Img(ImgPath.EIGHT_D_GRAY, (int) (Global.SCREEN_X * 0.19 * Global.ADJ + i * 28),
                    (int) (Global.SCREEN_Y * 0.43 * Global.ADJ), (int) (46 * digSize1), (int) (70 * digSize1), true));
        }
        //------------coin
        float digSize2 = 0.45f;
        for (int i = 0; i < 10; i++) {
            imgs.add(new Img(ImgPath.EIGHT_D_GRAY, (int) (Global.SCREEN_X * 0.255 * Global.ADJ + i * 20),
                    (int) (Global.SCREEN_Y * 0.577 * Global.ADJ), (int) (46 * digSize2), (int) (70 * digSize2), true));
        }
        //------------exp
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
			if (this.time >= 0 && timeDelay.isTrig()) {
				units = time % 10;
				tens = time / 10;
				this.time -= 1;
			}
		}
		//技能發動特效
		if(isSkillLevelFull(this.skillLevel, 5)){
			this.skillPaintCount = 240;
			
		}
		if(this.skillPaintCount > 0) {
			skillTrig(5);
//			this.removeSkilLinkBalls = 240;
//			if(this.skillPaintCount > 120) {
//				skillTrigImg.offset(-3, 0);
//			}else{
//				skillTrigImg.offset(3, 0);
//			}
		}
		
//		if(this.removeSkilLinkBalls == 120) {
//			restSkillLevel(5);
//			this.removeSkilLinkBalls = 0;
//		}
		
	}//end of update

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
		
		if(this.skillPaintCount-- > 0){
			this.skillTrigImg.paint(g);
			for (int i = 0; i < skillLinkBalls.size() ; i++) {
				g.drawImage(star.getImg(), skillLinkBalls.get(i).getX(),
						skillLinkBalls.get(i).getY(), null);
			}
			if(this.skillPaintCount == 0) {
				this.removeSkilLinkBalls = 120;
			}
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
	
	private boolean isSkillLevelFull(int skillLevel, int roleNum) {
		switch(roleNum) {
			case 1:
				if(skillLevel >= 12) return true;
			case 2:
				if(skillLevel >= 17) return true;
			case 3:
				if(skillLevel >= 16) return true;
			case 4:
				if(skillLevel >= 14) return true;
			case 5:
				if(skillLevel >= 5) {
					
					return true;
				}
		}
		return false;
	}

	private boolean skillTrig(int roleNum) {//技能條滿時發動
		
		switch (roleNum) {
		case 1:// Zhang-baseketball
			for (int i = 2; i < 4; i++) {
				listOfBalls.get(2).remove(i);
				listOfBalls.get(4).remove(i);
			}
			for (int i = 1; i < 4; i++) {
				listOfBalls.get(3).remove(i);
			}
			break;
		case 2:// Wang-volleyball
			for (int i = 0; i < 10; i++) {
				int c = (int) (Math.random() * listOfBalls.size());
				int r = (int) (Math.random() * listOfBalls.get(0).size());
				listOfBalls.get(c).remove(r);
			}
			break;
		case 3:// Tsai-cheerball
			this.time += 5;
			Global.log("Time:" + this.time);
			break;
		case 4:// Shu-shuttlecock
			int skillStartTime = this.time;
			do {
				System.out.println(this.time);
				this.totalScore += this.score * 1.4;
			} while (this.time - skillStartTime < 7);
			break;
		case 5:// Zhou-baseball
			
			for (int i = 0; i < listOfBalls.size(); i++) {
				skillLinkBalls.add(listOfBalls.get(i).get(4));
			}
			for (int i = 0; i < listOfBalls.get(3).size(); i++) {
				skillLinkBalls.add(listOfBalls.get(3).get(i));
			}
//			skillLevel = 0;
//			skillLinkBalls.clear();	
			
			break;
//			this.timeDelay.start();
		}
		Global.log("Skill Trig");
		restSkillLevel(5);
//		Global.log("Skill Level reset to 0");
//		Global.log("skillBalls(trig): "+ skillLinkBalls.size());
//		Global.log("skillBalls(trig2): "+ skillLinkBalls.size());
		return true;
	}
	
	private void restSkillLevel(int roleNum) {
		for (int i = 0; i < skillLinkBalls.size(); i++) {
			isTheSameObject(listOfBalls, skillLinkBalls.get(i));
		}
		skillLinkBalls.clear();
		this.skillLevel = 0;
	}
	
	private void gameOperation(MouseEvent e, MouseState state) {
		//在球盤範圍內
		if (e.getX() >= Global.XstartPoint &&
			e.getX() <= Global.XendPoint &&
			e.getY() >= Global.YstartPoint &&
			e.getY() <= Global.YendPoint) {
			//當滑鼠是Pressed 且連線的球數量為0的時候，將第一顆球加進連線的陣列
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
			//當滑鼠是Dragged 且連線的球數量不為0的時候，
			if (state.toString().equals("DRAGGED") && (linkBalls.size() != 0)) {
				for (int i = 0; i < listOfBalls.size(); i++) {
					for (int j = 0; j < listOfBalls.get(i).size(); j++) {
						if (!isExisted(linkBalls, getBallInArea(e))//如果滑鼠所在位置不在連線的球的陣列裡
							&& listOfBalls.get(i).get(j).equals(getBallInArea(e))
							&& isTheSameType(linkBalls.get(0), getBallInArea(e))//是否跟連線球的第一顆為同種球
							&& (Math.abs(linkBalls.get(linkBalls.size() - 1).rect().centerX()
								- getBallInArea(e).rect().centerX())) <= Global.UNIT_X - 20
							&& (Math.abs(linkBalls.get(linkBalls.size() - 1).rect().centerY()
								- getBallInArea(e).rect().centerY())) <= Global.UNIT_Y - 20) {
							linkBalls.add(getBallInArea(e));
						} else 
						if (isExisted(linkBalls, getBallInArea(e))//已在連線球的陣列裡
							&& !((linkBalls.get(linkBalls.size() - 1).equals(getBallInArea(e))))//最後一顆球與滑鼠所在位置的球不同
							&& (Math.abs(linkBalls.get(linkBalls.size() - 1).rect().centerY()
								- getBallInArea(e).rect().centerY())) <= Global.UNIT_Y - 20)  { //最後一顆球中心點與球所在位置的中心點
							for (int k = 0; k < linkBalls.size(); k++) {
								if (linkBalls.get(k).equals(getBallInArea(e))) {
									for (int l = k + 1; l < linkBalls.size(); l++) {
										linkBalls.remove(l);//移除掉最後一顆
									}
								}
							}
						}
					}
				}
			} 
			removeLinkBalls(e, state);
		}else// 超出球盤範圍，自動消除大於3顆球的連線
			if(e.getX() <= Global.XstartPoint ||
					e.getX() >= Global.XendPoint ||
					e.getY() <= Global.YstartPoint ||
					e.getY() >= Global.YendPoint ||
					state.toString().equals("EXITED")) {
				if(linkBalls.size() >=3) {
					for (int i = 0; i < linkBalls.size(); i++) {
						isTheSameObject(listOfBalls, linkBalls.get(i));
					}
					getScore();
					getSkillLevel();
					this.houwo.getAudio().play();
					linkBalls.clear();
				}
			}
	}

	private void removeLinkBalls(MouseEvent e, MouseState state) {
		if (state.toString().equals("RELEASED")) {
			if (linkBalls.size() >= 3)  {
				for (int i = 0; i < linkBalls.size(); i++) {
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
