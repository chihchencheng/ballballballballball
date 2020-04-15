package scenes;

import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import controllers.*;
import gameobj.*;
import gameobj.Number;
import gameobj.Brick;
import java.awt.image.BufferedImage;
import util.CommandSolver.*;
import util.*;

public class GameStartScene extends Scene {

    // set up this scene mouseListener
    public class MyMouseCommandListener implements MouseCommandListener {

        @Override
        public void mouseTrig(MouseEvent e, MouseState state, long trigTime) {
//            System.out.println(state);
//            if (e.getX() >= Global.XstartPoint && e.getX() <= Global.XendPoint) {
//                if (state.toString().equals("CLICKED")) {
//                    for (int i = 0; i < listOfBalls.sizeCD(); i++) {
//                        for (int j = 0; j < listOfBalls.get(i).sizeCD(); j++) {
//                            if (listOfBalls.get(i).get(j).equals(getBallInArea(e))) {
//                                System.out.println("Y");
//                                listOfBalls.get(i).remove(j);
//                                ballAmount--;
//                            }
//                        }
//                    }
//                }
//            }
//
            if (e.getX() >= Global.XstartPoint && e.getX() <= Global.XendPoint) {
                if (state.toString().equals("PRESSED")) {
                    for (int i = 0; i < listOfBalls.size(); i++) {
                        for (int j = 0; j < listOfBalls.get(i).size(); j++) {
                            if (listOfBalls.get(i).get(j).equals(getBallInArea(e))) {
                                linkBalls.add(getBallInArea(e));
                            }
                        }
                    }
                }
                if (state.toString().equals("DRAGGED")) {
                    for (int i = 0; i < listOfBalls.size(); i++) {
                        for (int j = 0; j < listOfBalls.get(i).size(); j++) {
                            if (!isExisted(linkBalls, getBallInArea(e))
                                    && listOfBalls.get(i).get(j).equals(getBallInArea(e))
                                    && isTheSameBall(linkBalls.get(0), getBallInArea(e))) {
                                linkBalls.add(getBallInArea(e));
                            }
                        }
                    }
                }
                if (state.toString().equals("RELEASED")) {
                    if (linkBalls.size() >= 3) {
                        for (int i = 0; i < linkBalls.size(); i++) {
                            //       System.out.println(linkBalls.get(i));
                            isTheSameObject(listOfBalls, linkBalls.get(i));
                        }
                        linkBalls.clear();
                    }
                    if (linkBalls.size() < 3) {
                        linkBalls.clear();
                    }
                } // end of pressed and dragged
//   System.out.println(linkBalls.sizeCD());
            }// end of mouseTrig
        }// end of mouseTrig

    }// end of inner class

    private MyMouseCommandListener mmcl;

    private ArrayList<Brick> bricks;
    private ArrayList<Number> numbers;
    private ArrayList<Number> digNumbers;
    private ArrayList<Ball> linkBalls;
    private List<List<Ball>> listOfBalls;

    private GameMainScene background;
    private ArrayList<Img> imgs;

    private int ballAmount;

    private int[] xs;
    private int time;
    private int units;// 秒個位數
    private int tens;// 秒十位數
    private int roleNum;//小人物圖片
    private Delay delay;
    private Delay timeDelay;

    public GameStartScene(SceneController sceneController, int roleNum) {
        super(sceneController);
        background = new GameMainScene();//載入背景圖片
        this.roleNum = roleNum + 6;

        imgs = new ArrayList<>();
        //元件
        imgs.add(new Img(ImgPath.TIME_PANEL, (int) (Global.SCREEN_X * 0.022388 * Global.CHARACTER_SIZE_ADJ), (int) (Global.SCREEN_Y * 0.027 * Global.CHARACTER_SIZE_ADJ), (int) (729 * Global.CHARACTER_SIZE_ADJ), (int) (138 * Global.CHARACTER_SIZE_ADJ), true));
        imgs.add(new Img(ImgPath.LEFT_PANEL, (int) (Global.SCREEN_X * 0.017 * Global.CHARACTER_SIZE_ADJ), (int) (Global.SCREEN_Y * 0.212 * Global.CHARACTER_SIZE_ADJ), (int) (739 * Global.CHARACTER_SIZE_ADJ), (int) (631 * Global.CHARACTER_SIZE_ADJ), true));
        imgs.add(new Img(ImgPath.RIGHT_PANEL, (int) (Global.SCREEN_X * 0.5 * Global.CHARACTER_SIZE_ADJ), (int) (Global.SCREEN_Y * 0.022 * Global.CHARACTER_SIZE_ADJ), (int) (778 * Global.CHARACTER_SIZE_ADJ), (int) (782 * Global.CHARACTER_SIZE_ADJ), true));
        imgs.add(new Img(ImgPath.SKILL_BANNER, (int) (Global.SCREEN_X * 0.672 * Global.CHARACTER_SIZE_ADJ), (int) (Global.SCREEN_Y * 0.865 * Global.CHARACTER_SIZE_ADJ), (int) (476 * Global.CHARACTER_SIZE_ADJ), (int) (70 * Global.CHARACTER_SIZE_ADJ), true));
        imgs.add(new Img(ImgPath.PLAY_BUTTON, (int) (Global.SCREEN_X * 0.903 * Global.CHARACTER_SIZE_ADJ), (int) (Global.SCREEN_Y * 0.8675 * Global.CHARACTER_SIZE_ADJ), (int) (99 * Global.CHARACTER_SIZE_ADJ), (int) (66 * Global.CHARACTER_SIZE_ADJ), true));
        imgs.add(new Img(ImgPath.PAUSE, (int) (Global.SCREEN_X * 0.515 * Global.CHARACTER_SIZE_ADJ), (int) (Global.SCREEN_Y * 0.873 * Global.CHARACTER_SIZE_ADJ), (int) (88 * Global.CHARACTER_SIZE_ADJ), (int) (67 * Global.CHARACTER_SIZE_ADJ), true));
        imgs.add(new Img(ImgPath.REHEARSE, (int) (Global.SCREEN_X * 0.58 * Global.CHARACTER_SIZE_ADJ), (int) (Global.SCREEN_Y * 0.873 * Global.CHARACTER_SIZE_ADJ), (int) (83 * Global.CHARACTER_SIZE_ADJ), (int) (68 * Global.CHARACTER_SIZE_ADJ), true));
        //small rolej
        imgs.add(new Img(ImgPath.SMALL_TSAI, (int) (Global.SCREEN_X * -0.012 * Global.CHARACTER_SIZE_ADJ), (int) (Global.SCREEN_Y * 0.29 * Global.CHARACTER_SIZE_ADJ), (int) (287 * Global.CHARACTER_SIZE_ADJ), (int) (545 * Global.CHARACTER_SIZE_ADJ), true));
        imgs.add(new Img(ImgPath.SMALL_ZHANG, (int) (Global.SCREEN_X * -0.03 * Global.CHARACTER_SIZE_ADJ), (int) (Global.SCREEN_Y * 0.35 * Global.CHARACTER_SIZE_ADJ), (int) (202 * Global.CHARACTER_SIZE_ADJ), (int) (571 * Global.CHARACTER_SIZE_ADJ), true));
        imgs.add(new Img(ImgPath.SMALL_SHU, (int) (Global.SCREEN_X * -0.03 * Global.CHARACTER_SIZE_ADJ), (int) (Global.SCREEN_Y * 0.35 * Global.CHARACTER_SIZE_ADJ), (int) (296 * Global.CHARACTER_SIZE_ADJ), (int) (493 * Global.CHARACTER_SIZE_ADJ), true));
        imgs.add(new Img(ImgPath.SMALL_ZHOU, (int) (Global.SCREEN_X * -0.03 * Global.CHARACTER_SIZE_ADJ), (int) (Global.SCREEN_Y * 0.35 * Global.CHARACTER_SIZE_ADJ), (int) (261 * Global.CHARACTER_SIZE_ADJ), (int) (534 * Global.CHARACTER_SIZE_ADJ), true));
        imgs.add(new Img(ImgPath.SMALL_WANG, (int) (Global.SCREEN_X * -0.03 * Global.CHARACTER_SIZE_ADJ), (int) (Global.SCREEN_Y * 0.35 * Global.CHARACTER_SIZE_ADJ), (int) (273 * Global.CHARACTER_SIZE_ADJ), (int) (516 * Global.CHARACTER_SIZE_ADJ), true));

        //gray dig8
        //------------score
        float digSize1 = 0.6f;
        for (int i = 0; i < 10; i++) {
            imgs.add(new Img(ImgPath.EIGHT_D_GRAY, (int) (Global.SCREEN_X * 0.19 * Global.CHARACTER_SIZE_ADJ + i * 28),
                    (int) (Global.SCREEN_Y * 0.42 * Global.CHARACTER_SIZE_ADJ), (int) (46 * digSize1), (int) (70 * digSize1), true));
        }
        //------------coin
        float digSize2 = 0.45f;
        for (int i = 0; i < 10; i++) {
            imgs.add(new Img(ImgPath.EIGHT_D_GRAY, (int) (Global.SCREEN_X * 0.255 * Global.CHARACTER_SIZE_ADJ + i * 20),
                    (int) (Global.SCREEN_Y * 0.575 * Global.CHARACTER_SIZE_ADJ), (int) (46 * digSize2), (int) (70 * digSize2), true));
        }
        //------------exp
        for (int i = 0; i < 10; i++) {
            imgs.add(new Img(ImgPath.EIGHT_D_GRAY, (int) (Global.SCREEN_X * 0.255 * Global.CHARACTER_SIZE_ADJ + i * 20),
                    (int) (Global.SCREEN_Y * 0.677 * Global.CHARACTER_SIZE_ADJ), (int) (46 * digSize2), (int) (70 * digSize2), true));
        }

        //角色
        sceneBegin();

        this.units = 0;// 倒數個位數
        this.tens = 6;// 倒數十位數
        this.time = 60;// 倒數總秒數
        this.ballAmount = 0;// 檯面上總球數
        this.mmcl = new MyMouseCommandListener();
    }

    @Override
    public void sceneBegin() {
        delay = new Delay(0);
        timeDelay = new Delay(60);// 倒數秒數延遲時間
        bricks = new ArrayList<Brick>();// 最底下的碰撞框
        numbers = new ArrayList<Number>();
        digNumbers = new ArrayList<Number>();
        linkBalls = new ArrayList<Ball>();
        listOfBalls = new ArrayList<List<Ball>>();
        xs = genXPoint();
        for (int i = 0; i < Global.COLUMN; i++) {
            List<Ball> columnOfBalls = new ArrayList<>();
            listOfBalls.add(columnOfBalls);
        }
        genNumbers(ImgPath.numbers());
        genNumbers(ImgPath.digNumbers());
//		genBalls(listOfBalls, xs);
        genRect(xs);
        delay.start();
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
        //元件部分        
        for (int i = 0; i < 7; i++) {
            imgs.get(i).paint(g);
        }

        //小人物部分
        imgs.get(this.roleNum).paint(g);

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
        float sizeCD = 0.8f; //數字圖片縮放比例
        g.drawImage(numbers.get(units).getImg(), 112, 38, (int) (42 * sizeCD), (int) (72 * sizeCD), null);// 個位數
        g.drawImage(numbers.get(tens).getImg(), 78, 38, (int) (42 * sizeCD), (int) (72 * sizeCD), null);// 十位數

        //sore panel
        for (int i = 0; i < 30; i++) {
            imgs.get(12 + i).paint(g);
        }

    }

    private void genBalls(List<List<Ball>> listOfBall, int[] xs) {
        int count = 0;
        if (ballAmount < Global.LIMIT && delay.isTrig()) {
            for (int i = 0; i < Global.COLUMN; i++) {
                if (listOfBall.get(count).size() < Global.ROW) {
                    listOfBall.get(count).add(listOfBall.get(count).size(),
                            getANewBall(xs, count));
                    count++;
                    ballAmount++;
                }
            }
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

    public void genNumbers(String[] path) {
        String[] numImg = path;
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

    private void isTheSameObject(List<List<Ball>> listOfBalls, Ball ball) {
        for (int i = 0; i < listOfBalls.size(); i++) {
            for (int j = 0; j < listOfBalls.get(i).size(); j++) {
                if (ball.equals(listOfBalls.get(i).get(j))) {
                    listOfBalls.get(i).remove(j);
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
