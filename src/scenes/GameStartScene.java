package scenes;

import audio.AudioObject;
import java.awt.Color;
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
                //-----按鈕功能
                if (imgs.get(B_HOME).isInside(e.getX(), e.getY()) && state == MouseState.CLICKED) {
                    sceneController.changeScene(new MainScene(sceneController));
                }   //首頁

                if (imgs.get(CONTINUE_BUTTON).isInside(e.getX(), e.getY()) && state == MouseState.CLICKED) {
                    isPause = !isPause;
                }   //繼續按鈕

                if (imgs.get(B_SHOP).isInside(e.getX(), e.getY()) && state == MouseState.CLICKED) {
                    sceneController.changeScene(new ShopScene(sceneController));
                }   //商店

                if (imgs.get(B_INFO).isInside(e.getX(), e.getY()) && state == MouseState.CLICKED) {
                    sceneController.changeScene(new InfoScene(sceneController));
                }   //資訊

                //左側選單
                //-----按鈕換圖
                for (int i = 0; i < button.length; i++) {
                    if (imgs.get(button[i]).isInside(e.getX(), e.getY())) {
                        buttonSelectPath = button[i];
                    }
                }
                if (!imgs.get(button[0]).isInside(e.getX(), e.getY()) && !imgs.get(button[1]).isInside(e.getX(), e.getY())
                        && !imgs.get(button[2]).isInside(e.getX(), e.getY()) && !imgs.get(button[3]).isInside(e.getX(), e.getY())) {
                    buttonSelectPath = NULL;
                }
            } else {
                //if (imgs.get(6).isInside(e.getX(), e.getY()) && state == MouseState.CLICKED) {
//                sceneController.changeScene(new MainScene(sceneController));
//            }
//            if (imgs.get(4).isInside(e.getX(), e.getY()) && state == MouseState.CLICKED) {
//                timeDelay.start();
//            } //continue Button
                if (imgs.get(PAUSE).isInside(e.getX(), e.getY()) && state == MouseState.CLICKED) {
                    isPause = !isPause;
                }
                if (imgs.get(REHEARSE).isInside(e.getX(), e.getY()) && state == MouseState.CLICKED) {
                    for (int i = 0; i < listOfBalls.size(); i++) {
                        listOfBalls.get(i).clear();
                    }
                    ballAmount = 0;
                }
                gameOperation(e, state);
            }
        }// end of mouseTrig
    }// end of inner class

    private MyMouseCommandListener mmcl;
    private ArrayList<Brick> bricks;
    private ArrayList<Number> numbers;
    private ArrayList<Ball> linkBalls;
    private ArrayList<Ball> skillLinkBalls;
    private List<List<Ball>> listOfBalls;
    private int ballAmount;
    private String rolePath;   //小人物圖片
    private Img skillTrigImg;

    private int[] xs;
    private int time;
    private int units;  // 秒個位數
    private int tens;   // 秒十位數
    private int skillLevel;
    private int score;
    private int totalScore;

    private boolean isPause;
    private int countDown;
    private boolean timeUpStart;
    private boolean timeUpOver;
    private int frameAfterTimeUp = 0;
    int rightPanelDeltX = 0;
    int leftPanelDeltX = 0;
    private int skillPaintCount;

    private Delay delay;
    private Delay timeDelay;    //遊戲時間計時器    

    private AudioObject clean;

    private String[] componentPaths = {BK_MAIN, TIME_PANEL, LEFT_PANEL, RIGHT_PANEL, SKILL_BANNER};
    private String[] ballPaths = {CHEERBALL, BASKETBALL, SHUTTLECOCK, BASEBALL, VOLLEYBALL};
    private String[] numPaths = {ZERO, ONE, TWO, THREE, FOUR, FIVE, SIX, SEVEN, EIGHT, NINE};
    private String[] digNumPaths = {ZERO_D, ONE_D, TWO_D, THREE_D, FOUR_D, FIVE_D, SIX_D, SEVEN_D, EIGHT_D, NINE_D};
    private String[] smallRolePaths = {SMALL_TSAI, SMALL_ZHANG, SMALL_SHU, SMALL_ZHOU, SMALL_WANG};
    private String[] rolePaths = {CHOSE_CHEERBALL, CHOSE_BASKETBALL, CHOSE_BADMINTON, CHOSE_BASEBALL, CHOSE_VOLLEYBALL};
    private String[] buttonPaths = {PAUSE, REHEARSE, PLAY_BUTTON};
    private String[] button = {B_HOME, CONTINUE_BUTTON, B_SHOP, B_INFO};
    private String[] isPausePaths = {SHADOW, PAUSE_PANEL, PAUSE_TITLE};

    private String[] leftPanelPaths = {LEFT_PANEL, TIME_PANEL};
    private String[] rightPanelPaths = {RIGHT_PANEL, PAUSE, REHEARSE, SKILL_BANNER};

    public GameStartScene(SceneController sceneController, String rolePath) {
        super(sceneController);
        System.out.println("GameScene constructor rolePath="+rolePath);
        timeUpOver = false;
        timeUpStart = false;

        for (int i = 0; i < rolePaths.length; i++) {
            if (rolePaths[i].equals(rolePath)) {
                this.rolePath = smallRolePaths[i];
            }
        }
        System.out.println("GameScene this.rolePath="+this.rolePath);

        this.skillPaintCount = 1;
        this.skillLevel = 0;
        this.units = 0;         // 倒數個位數
        this.tens = 0;          // 倒數十位數
        this.time = 60;         // 倒數總秒數
        this.ballAmount = 0;    // 檯面上總球數
        this.score = 0;         //分數
        this.totalScore = 0;    //累積分數
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
        linkBalls = new ArrayList<Ball>();
        listOfBalls = new ArrayList<List<Ball>>();
        clean = new AudioObject("Clean", AudioPath.CLEAN);

        //元件
        imgs.add(new Img(ImgPath.BK_MAIN, (int) (0 * Global.ADJ_X), (int) (0 * Global.ADJ_Y), true));
        imgs.add(new Img(ImgPath.TIME_PANEL, (int) (0.022388 * Global.ADJ_X), (int) (0.027 * Global.ADJ_Y), true));
        imgs.add(new Img(ImgPath.LEFT_PANEL, (int) (0.017 * Global.ADJ_X), (int) (0.212 * Global.ADJ_Y), true));
        imgs.add(new Img(ImgPath.RIGHT_PANEL, (int) (0.5 * Global.ADJ_X), (int) (0.022 * Global.ADJ_Y), true));
        imgs.add(new Img(ImgPath.SKILL_BANNER, (int) (0.672 * Global.ADJ_X), (int) (0.865 * Global.ADJ_Y), true));
        imgs.add(new Img(ImgPath.PLAY_BUTTON, (int) (0.903 * Global.ADJ_X), (int) (0.8675 * Global.ADJ_Y), true));
        imgs.add(new Img(ImgPath.PAUSE, (int) (0.515 * Global.ADJ_X), (int) (0.873 * Global.ADJ_Y), true));
        imgs.add(new Img(ImgPath.REHEARSE, (int) (0.58 * Global.ADJ_X), (int) (0.873 * Global.ADJ_Y), true));

        //small role
        imgs.add(new Img(ImgPath.SMALL_TSAI, (int) (-0.012 * Global.ADJ_X), (int) (0.29 * Global.ADJ_Y), true));
        imgs.add(new Img(ImgPath.SMALL_ZHANG, (int) (0.021 * Global.ADJ_X), (int) (0.32 * Global.ADJ_Y), true));
        imgs.add(new Img(ImgPath.SMALL_SHU, (int) (-0.03 * Global.ADJ_X), (int) (0.35 * Global.ADJ_Y), true));
        imgs.add(new Img(ImgPath.SMALL_ZHOU, (int) (-0.01 * Global.ADJ_X), (int) (0.312 * Global.ADJ_Y), true));
        imgs.add(new Img(ImgPath.SMALL_WANG, (int) (0.016 * Global.ADJ_X), (int) (0.316 * Global.ADJ_Y), true));

        //isPause Scene
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

        //Time's up Scene
        imgs.add(new Img(ImgPath.SPOTLIGHT, (int) (0 * Global.ADJ_X), (int) (0 * Global.ADJ_Y), true));
        imgs.add(new Img(ImgPath.TIME_UP, (int) (0.18 * Global.ADJ_X), (int) (-0.35 * Global.ADJ_Y), true));

        skillTrigImg = new Img(ImgPath.TSAI, Global.XendPoint, 0, (int) (960 * Global.ADJ), (int) (1179 * Global.ADJ), true);

        //gray dig8
        //------------score
        float digSize1 = 0.6f;
        for (int i = 0; i < 10; i++) {
            imgs.add(new Img(ImgPath.EIGHT_D_GRAY, (int) (0.19 * Global.ADJ_X + i * 28),
                    (int) (0.43 * Global.ADJ_Y), (int) (46 * digSize1), (int) (70 * digSize1), true));
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
        imgs.add(new Img(ImgPath.NULL, 0, 0, true));

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
        if (!isPause && countDown == 0 && this.time >= 0) {
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
            if (isSkillLevelFull(this.skillLevel)) {
                this.skillPaintCount = 240;
                skillTrig();
            }
            if (this.skillPaintCount > 0) {
                if (this.skillPaintCount > 120) {
                    skillTrigImg.offset(-3, 0);
                } else {
                    skillTrigImg.offset(3, 0);
                }
            }

//            //計時器
//            if (this.time >= -1 && timeDelay.isTrig()) {
//                units = time % 10;
//                tens = time / 10;
//                this.time--;
//                System.out.println("time=" + time);
//            }
        }
        timeUpStart = (this.time == -1);
        if (timeUpStart) {
            frameAfterTimeUp++;
            //rightPanelPaths={RIGHT_PANEL,PAUSE,REHEARSE,SKILL_BANNER};
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
            //leftPanelPaths = {LEFT_PANEL, TIME_PANEL};
            if (leftPanelDeltX < (0.135 * ADJ_X)) {
                imgs.get(TIME_PANEL).offset(0, -1);
                imgs.get(LEFT_PANEL).offset(1, 0);
                imgs.get(rolePath).offset(1, 0);
                leftPanelDeltX++;
            }
            if (leftPanelDeltX < (0.10 * ADJ_X)) {
                imgs.get(TIME_UP).offset(0, 1);
            }
        }
    }       //  update()

    @Override
    public void sceneEnd() {
        delay.stop();
        delay = null;
    }

    @Override
    public void paint(Graphics g) {
        //元件部分         
        for (int i = 0; i < componentPaths.length; i++) {
            imgs.get(componentPaths[i]).paint(g);
        }
        for (int i = 0; i < buttonPaths.length - 1; i++) {
            imgs.get(buttonPaths[i]).paint(g);
        }
//        for (int i = 0; i < bricks.size(); i++) {// 最底下的碰撞長方形
//            bricks.get(i).paint(g);
//        }
        for (int i = 0; i < listOfBalls.size(); i++) {// 畫球COLUMN
            for (int j = 0; j < listOfBalls.get(i).size(); j++) {
                listOfBalls.get(i).get(j).paint(g);
            }
        }
//        if (linkBalls.size() >= 2) {
//            g.setColor(Color.CYAN);
//            for (int i = 0; i < linkBalls.size() - 1; i++) {
//                g.drawLine(linkBalls.get(i).rect().centerX(), linkBalls.get(i).rect().centerY(),
//                        linkBalls.get(i + 1).rect().centerX(), linkBalls.get(i + 1).rect().centerY());
//
//                g.drawLine(linkBalls.get(i).rect().centerX() + 1, linkBalls.get(i).rect().centerY() + 1,
//                        linkBalls.get(i + 1).rect().centerX() + 1, linkBalls.get(i + 1).rect().centerY() + 1);
//
//                g.drawLine(linkBalls.get(i).rect().centerX() + 2, linkBalls.get(i).rect().centerY() + 2,
//                        linkBalls.get(i + 1).rect().centerX() + 2, linkBalls.get(i + 1).rect().centerY() + 2);
//
//            }
//            g.setColor(Color.black);
//        }
        // 倒數計時60秒
        float sizeCD = 0.8f; //數字圖片縮放比例
        g.drawImage(numbers.get(units).getImg(), 112, 38, null);// 個位數
        g.drawImage(numbers.get(tens).getImg(), 78, 38, null);// 十位數
        //sore panel
//        for (int i = 0; i < 30; i++) {
//            imgs.get(13 + i).paint(g);
//        }

//        if (this.skillPaintCount-- > 0) {
//            this.skillTrigImg.paint(g);
//            Graphics2D g2 = (Graphics2D) g;
//            g2.setColor(Color.green);
//            g2.setStroke(new BasicStroke(10));
//            for (int i = 0; i < skillLinkBalls.size() - 1; i++) {
//                g2.drawLine(skillLinkBalls.get(i).rect().centerX(), skillLinkBalls.get(i).rect().centerY(),
//                        skillLinkBalls.get(i + 1).rect().centerX(), skillLinkBalls.get(i + 1).rect().centerY());
//            }
//            g2.setColor(Color.black);
//        }

       

        //左側小人物
        imgs.get(rolePath).paint(g);
        //--------------暫停畫面
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
        
         if (linkBalls.size() >= 2) {
            Graphics2D g2 = (Graphics2D) g;
            g2.setColor(Color.MAGENTA);
            g2.setStroke(new BasicStroke(10));

            for (int i = 0; i < linkBalls.size() - 1; i++) {
                g2.drawLine(linkBalls.get(i).rect().centerX(), linkBalls.get(i).rect().centerY(),
                        linkBalls.get(i + 1).rect().centerX(), linkBalls.get(i + 1).rect().centerY());
            }
            g2.setColor(Color.black);
        }
    }//end of paint();

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

    private void gameOperation(MouseEvent e, MouseState state) {
        //在球盤範圍內
        if (e.getX() >= Global.XstartPoint
                && e.getX() <= Global.XendPoint
                && e.getY() >= Global.YstartPoint
                && e.getY() <= Global.YendPoint) {
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
                                && isrTheSameType(linkBalls.get(0), getBallInArea(e))//是否跟連線球的第一顆為同種球
                                && (Math.abs(linkBalls.get(linkBalls.size() - 1).rect().centerX()
                                        - getBallInArea(e).rect().centerX())) <= Global.UNIT_X - 20
                                && (Math.abs(linkBalls.get(linkBalls.size() - 1).rect().centerY()
                                        - getBallInArea(e).rect().centerY())) <= Global.UNIT_Y - 20) {
                            linkBalls.add(getBallInArea(e));
                        } else if (isExisted(linkBalls, getBallInArea(e))//已在連線球的陣列裡
                                && !((linkBalls.get(linkBalls.size() - 1).equals(getBallInArea(e))))//最後一顆球與滑鼠所在位置的球不同
                                && (Math.abs(linkBalls.get(linkBalls.size() - 1).rect().centerY()
                                        - getBallInArea(e).rect().centerY())) <= Global.UNIT_Y - 20) { //最後一顆球中心點與球所在位置的中心點
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
        } else// 超出球盤範圍，自動消除大於3顆球的連線
        if (e.getX() <= Global.XstartPoint
                || e.getX() >= Global.XendPoint
                || e.getY() <= Global.YstartPoint
                || e.getY() >= Global.YendPoint
                || state.toString().equals("EXITED")) {
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
                if (skillLevel >= 12) {
                    return true;
                }
            case SMALL_ZHANG:
                if (skillLevel >= 17) {
                    return true;
                }
            case CHOSE_BADMINTON:
                if (skillLevel >= 16) {
                    return true;
                }
            case CHOSE_BASEBALL:
                if (skillLevel >= 14) {
                    return true;
                }
            case CHOSE_VOLLEYBALL:
                if (skillLevel >= 5) {
                    return true;
                }
        }
        return false;
    }

    private boolean skillTrig() {//技能條滿時發動

        switch (this.rolePath) {
            case SMALL_TSAI:// Zhang-baseketball
                for (int i = 2; i < 4; i++) {
                    listOfBalls.get(2).remove(i);
                    listOfBalls.get(4).remove(i);
                }
                for (int i = 1; i < 4; i++) {
                    listOfBalls.get(3).remove(i);
                }
                break;
            case SMALL_ZHANG:// Wang-volleyball
                for (int i = 0; i < 10; i++) {
                    int c = (int) (Math.random() * listOfBalls.size());
                    int r = (int) (Math.random() * listOfBalls.get(0).size());
                    listOfBalls.get(c).remove(r);
                }
                break;
            case SMALL_SHU:// Tsai-cheerball
                this.time += 5;
                Global.log("Time:" + this.time);
                break;
            case SMALL_ZHOU:// Shu-shuttlecock
                int skillStartTime = this.time;
                do {
                    System.out.println(this.time);
                    this.totalScore += this.score * 1.4;
                } while (this.time - skillStartTime < 7);
                break;
            case SMALL_WANG:// Zhou-baseball

                for (int i = 0; i < listOfBalls.size(); i++) {
                    skillLinkBalls.add(listOfBalls.get(i).get(4));
                }
                for (int i = 0; i < listOfBalls.get(3).size(); i++) {
                    skillLinkBalls.add(listOfBalls.get(3).get(i));
                }
                skillLevel = 0;
                skillLinkBalls.clear();

                break;
//			this.timeDelay.start();
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

    private boolean isrTheSameType(Ball b1, Ball b2) {
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
