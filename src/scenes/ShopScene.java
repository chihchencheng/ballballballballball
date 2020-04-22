package scenes;

import java.awt.Graphics;
import java.util.ArrayList;

import controllers.SceneController;
import gameobj.Img;
import gameobj.ImgArr;
//import gameobj.Rect;
import java.awt.event.MouseEvent;
import util.CommandSolver;
import util.CommandSolver.KeyListener;
import util.CommandSolver.MouseCommandListener;
import util.CommandSolver.MouseState;
import util.Delay;
import util.Global;
import util.ImgPath;
import static util.ImgPath.*;

public class ShopScene extends Scene {

    private Delay delay;
    private MyMouseCommandListener mmcl;
    private ImgArr imgs;
    private String[] rolePic = {SHOPS_CHEERBALL, SHOPS_BASKETBALL, SHOPS_BADMINTON, SHOPS_BASEBALL, SHOPS_VOLLEYBALL};
    private String[] roleIntro = {SHOP__CHEERBALL, SHOP__BASKETBALL, SHOP__BADMINTON, SHOP__BASEBALL, SHOP__VOLLEYBALL};
    private String[] button = {B_HOME, B_GAME, B_SHOP, B_INFO};

    public class MyMouseCommandListener implements CommandSolver.MouseCommandListener {

        private String selectIntroPath = roleIntro[0];    //右側說明欄圖片編號
        private int menuIndex;
//        private boolean menuSelect;

        @Override
        public void mouseTrig(MouseEvent e, CommandSolver.MouseState state, long trigTime) {
            //右側說明欄顯示
            for (int i = 0; i < rolePic.length; i++) {
                if (imgs.get(rolePic[i]).isInside(e.getX(), e.getY()) && state == MouseState.CLICKED) {
                    selectIntroPath = roleIntro[i];
                }
            }

            //左側選單
            //-----按鈕換圖
            for (int i = 0; i < button.length; i++) {
                if (imgs.get(button[i]).isInside(e.getX(), e.getY())) {
                    menuIndex = imgs.searchIndex(button[i]);
                }
            }
            if (!imgs.get(button[0]).isInside(e.getX(), e.getY()) && !imgs.get(button[1]).isInside(e.getX(), e.getY())
                    && !imgs.get(button[2]).isInside(e.getX(), e.getY()) && !imgs.get(button[3]).isInside(e.getX(), e.getY())) {
                menuIndex = imgs.size() - 1;
            }

            //-----按鈕功能
            if (imgs.get(B_HOME).isInside(e.getX(), e.getY()) && state == MouseState.CLICKED) {
                sceneController.changeScene(new MainScene(sceneController));
            }//首頁
            if (imgs.get(B_GAME).isInside(e.getX(), e.getY()) && state == MouseState.CLICKED) {
                sceneController.changeScene(new SelectionScene(sceneController));
            }//遊戲開始
        }
    }

    public ShopScene(SceneController sceneController) {
        super(sceneController);
    }

    @Override
    public void sceneBegin() {
        this.mmcl = new MyMouseCommandListener();
//        delay = new Delay(10);
//        delay.start();
        imgs = new ImgArr();
        imgs.add(new Img(ImgPath.BK_MAIN, 0, 0, true));
        imgs.add(new Img(ImgPath.ONSALE, (int) (Global.SCREEN_X * 0 * Global.ADJ), (int) (Global.SCREEN_Y * 0 * Global.ADJ), true));
        imgs.add(new Img(ImgPath.PRICE, (int) (Global.SCREEN_X * 0 * Global.ADJ), (int) (Global.SCREEN_Y * 0 * Global.ADJ), true));

        imgs.add(new Img(ImgPath.SHOP__CHEERBALL, (int) (Global.SCREEN_X * 0.375 * Global.ADJ), (int) (Global.SCREEN_Y * 0.033 * Global.ADJ), true));
        imgs.add(new Img(ImgPath.SHOP__BASKETBALL, (int) (Global.SCREEN_X * 0.375 * Global.ADJ), (int) (Global.SCREEN_Y * 0.033 * Global.ADJ), true));
        imgs.add(new Img(ImgPath.SHOP__BADMINTON, (int) (Global.SCREEN_X * 0.375 * Global.ADJ), (int) (Global.SCREEN_Y * 0.033 * Global.ADJ), true));
        imgs.add(new Img(ImgPath.SHOP__BASEBALL, (int) (Global.SCREEN_X * 0.375 * Global.ADJ), (int) (Global.SCREEN_Y * 0.033 * Global.ADJ), true));
        imgs.add(new Img(ImgPath.SHOP__VOLLEYBALL, (int) (Global.SCREEN_X * 0.375 * Global.ADJ), (int) (Global.SCREEN_Y * 0.033 * Global.ADJ), true));

        imgs.add(new Img(ImgPath.SHOPS_CHEERBALL, (int) (Global.SCREEN_X * 0.065 * Global.ADJ), (int) (Global.SCREEN_Y * 0.033 * Global.ADJ), true));
        imgs.get(SHOPS_CHEERBALL).importPic(SHOPS_CHEERBALL2);
        imgs.add(new Img(ImgPath.SHOPS_BASKETBALL, (int) (Global.SCREEN_X * 0.222 * Global.ADJ), (int) (Global.SCREEN_Y * 0.033 * Global.ADJ), true));
        imgs.get(SHOPS_BASKETBALL).importPic(SHOPS_BASKETBALL2);
        imgs.add(new Img(ImgPath.SHOPS_BADMINTON, (int) (Global.SCREEN_X * 0.065 * Global.ADJ), (int) (Global.SCREEN_Y * 0.3465 * Global.ADJ), true));
        imgs.get(SHOPS_BADMINTON).importPic(SHOPS_BADMINTON2);
        imgs.add(new Img(ImgPath.SHOPS_BASEBALL, (int) (Global.SCREEN_X * 0.222 * Global.ADJ), (int) (Global.SCREEN_Y * 0.3465 * Global.ADJ), true));
        imgs.get(SHOPS_BASEBALL).importPic(SHOPS_BASEBALL2);
        imgs.add(new Img(ImgPath.SHOPS_VOLLEYBALL, (int) (Global.SCREEN_X * 0.065 * Global.ADJ), (int) (Global.SCREEN_Y * 0.663 * Global.ADJ), true));
        imgs.get(SHOPS_VOLLEYBALL).importPic(SHOPS_VOLLEYBALL2);

        imgs.add(new Img(ImgPath.B_HOME, (int) (Global.SCREEN_X * 0.01 * Global.ADJ), (int) (Global.SCREEN_Y * 0.3 * Global.ADJ), true));
        imgs.get(B_HOME).importPic(B_HOME2);
        imgs.add(new Img(ImgPath.B_GAME, (int) (Global.SCREEN_X * 0.01 * Global.ADJ), (int) (Global.SCREEN_Y * 0.4 * Global.ADJ), true));
        imgs.get(B_GAME).importPic(B_GAME2);
        imgs.add(new Img(ImgPath.B_SHOP, (int) (Global.SCREEN_X * 0.01 * Global.ADJ), (int) (Global.SCREEN_Y * 0.5 * Global.ADJ), true));
        imgs.get(B_SHOP).importPic(B_SHOP2);
        imgs.add(new Img(ImgPath.B_INFO, (int) (Global.SCREEN_X * 0.01 * Global.ADJ), (int) (Global.SCREEN_Y * 0.6 * Global.ADJ), true));
        imgs.get(B_INFO).importPic(B_INFO2);

        imgs.add(new Img(ImgPath.NULL, (int) (Global.SCREEN_X * 0 * Global.ADJ), (int) (Global.SCREEN_Y * 0 * Global.ADJ), true));
    }// end of begin

    @Override
    public void sceneUpdate() {
//        if (delay.isTrig()) {
//        }
    }

    @Override
    public void sceneEnd() {
//        delay.stop();
//        delay = null;
    }

    @Override
    public void paint(Graphics g) {
        imgs.get(BK_MAIN).paint(g);

        //右側說明欄        
        imgs.get(mmcl.selectIntroPath).paint(g);

        //rolePic part
        for (int i = 0; i < rolePic.length; i++) {
            if (!roleIntro[i].equals(mmcl.selectIntroPath)) {
                imgs.get(rolePic[i]).switchNowImage(1);
            } else {
                imgs.get(rolePic[i]).switchNowImage(0);
            }
            imgs.get(rolePic[i]).paint(g);
        }

        //switch page part
        for (int i = 0; i < button.length; i++) {
            imgs.get(button[i]).paint(g);
        }
        imgs.get(mmcl.menuIndex).paint(g);

    }

    public void update() {

    }// end of update

    private void genBalls() {

    }

    public void genRect() {

    }

    @Override
    public KeyListener getKeyListener() {

        return null;
    }

    @Override
    public MouseCommandListener getMouseListener() {

        return this.mmcl;
    }

}// end of class
