package scenes;

import java.awt.Graphics;
import java.util.ArrayList;

import controllers.SceneController;
import gameobj.Img;
//import gameobj.Rect;
import java.awt.event.MouseEvent;
import util.CommandSolver;
import util.CommandSolver.KeyListener;
import util.CommandSolver.MouseCommandListener;
import util.CommandSolver.MouseState;
import util.Delay;
import util.Global;
import util.ImgPath;

public class ShopScene extends Scene {

    private Delay delay;
    private MyMouseCommandListener mmcl;
    private ArrayList<Img> imgs;
    private ArrayList<Img> introImgs;
    private ArrayList<Img> goodsImgs;
    private ArrayList<Img> goodsImgs2;
    private ArrayList<Img> buttonImgs;
    private ArrayList<Img> buttonImgs2;

    public class MyMouseCommandListener implements CommandSolver.MouseCommandListener {

        private int selectIndex;//右側說明欄圖片編號
        private int menuIndex;
//        private boolean menuSelect;

        @Override
        public void mouseTrig(MouseEvent e, CommandSolver.MouseState state, long trigTime) {
            //右側說明欄顯示
            for (int i = 0; i < goodsImgs.size(); i++) {
                if (goodsImgs.get(i).isInside(e.getX(), e.getY()) && state == MouseState.CLICKED) {
                    selectIndex = i;
                }
            }

            //左側選單
            //-----按鈕換圖
            for (int i = 0; i < buttonImgs2.size() - 1; i++) {
                if (buttonImgs2.get(i).isInside(e.getX(), e.getY())) {
                    menuIndex = i;
                }
            }            
            if (!buttonImgs2.get(0).isInside(e.getX(), e.getY()) && !buttonImgs2.get(1).isInside(e.getX(), e.getY())
                    && !buttonImgs2.get(2).isInside(e.getX(), e.getY()) && !buttonImgs2.get(3).isInside(e.getX(), e.getY())) {
                menuIndex = buttonImgs2.size() - 1;
            }

            //-----按鈕功能
            if (buttonImgs2.get(0).isInside(e.getX(), e.getY()) && state == MouseState.CLICKED) {
                sceneController.changeScene(new MainScene(sceneController));
            }//首頁
            if (buttonImgs2.get(1).isInside(e.getX(), e.getY()) && state == MouseState.CLICKED) {
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
        delay = new Delay(10);
        delay.start();
        imgs = new ArrayList<>();
        imgs.add(new Img(ImgPath.BK_MAIN, 0, 0, true));
        imgs.add(new Img(ImgPath.ONSALE, (int) (Global.SCREEN_X * 0 * Global.ADJ), (int) (Global.SCREEN_Y * 0 * Global.ADJ), true));
        imgs.add(new Img(ImgPath.PRICE, (int) (Global.SCREEN_X * 0 * Global.ADJ), (int) (Global.SCREEN_Y * 0 * Global.ADJ), true));

        introImgs = new ArrayList<>();
        introImgs.add(new Img(ImgPath.SHOP__CHEERBALL, (int) (Global.SCREEN_X * 0.375 * Global.ADJ), (int) (Global.SCREEN_Y * 0.033 * Global.ADJ), true));
        introImgs.add(new Img(ImgPath.SHOP__BASKETBALL, (int) (Global.SCREEN_X * 0.375 * Global.ADJ), (int) (Global.SCREEN_Y * 0.033 * Global.ADJ), true));
        introImgs.add(new Img(ImgPath.SHOP__BADMINTON, (int) (Global.SCREEN_X * 0.375 * Global.ADJ), (int) (Global.SCREEN_Y * 0.033 * Global.ADJ), true));
        introImgs.add(new Img(ImgPath.SHOP__BASEBALL, (int) (Global.SCREEN_X * 0.375 * Global.ADJ), (int) (Global.SCREEN_Y * 0.033 * Global.ADJ), true));
        introImgs.add(new Img(ImgPath.SHOP__VOLLEYBALL, (int) (Global.SCREEN_X * 0.375 * Global.ADJ), (int) (Global.SCREEN_Y * 0.033 * Global.ADJ), true));

        goodsImgs = new ArrayList<>();
        goodsImgs.add(new Img(ImgPath.SHOPS_CHEERBALL, (int) (Global.SCREEN_X * 0.065 * Global.ADJ), (int) (Global.SCREEN_Y * 0.033 * Global.ADJ), true));
        goodsImgs.add(new Img(ImgPath.SHOPS_BASKETBALL, (int) (Global.SCREEN_X * 0.222 * Global.ADJ), (int) (Global.SCREEN_Y * 0.033 * Global.ADJ), true));
        goodsImgs.add(new Img(ImgPath.SHOPS_BADMINTON, (int) (Global.SCREEN_X * 0.065 * Global.ADJ), (int) (Global.SCREEN_Y * 0.3465 * Global.ADJ), true));
        goodsImgs.add(new Img(ImgPath.SHOPS_BASEBALL, (int) (Global.SCREEN_X * 0.222 * Global.ADJ), (int) (Global.SCREEN_Y * 0.3465 * Global.ADJ), true));
        goodsImgs.add(new Img(ImgPath.SHOPS_VOLLEYBALL, (int) (Global.SCREEN_X * 0.065 * Global.ADJ), (int) (Global.SCREEN_Y * 0.663 * Global.ADJ), true));

        goodsImgs2 = new ArrayList<>();
        goodsImgs2.add(new Img(ImgPath.SHOPS_CHEERBALL2, (int) (Global.SCREEN_X * 0.065 * Global.ADJ), (int) (Global.SCREEN_Y * 0.033 * Global.ADJ), true));
        goodsImgs2.add(new Img(ImgPath.SHOPS_BASKETBALL2, (int) (Global.SCREEN_X * 0.222 * Global.ADJ), (int) (Global.SCREEN_Y * 0.033 * Global.ADJ), true));
        goodsImgs2.add(new Img(ImgPath.SHOPS_BADMINTON2, (int) (Global.SCREEN_X * 0.065 * Global.ADJ), (int) (Global.SCREEN_Y * 0.3465 * Global.ADJ), true));
        goodsImgs2.add(new Img(ImgPath.SHOPS_BASEBALL2, (int) (Global.SCREEN_X * 0.222 * Global.ADJ), (int) (Global.SCREEN_Y * 0.3465 * Global.ADJ), true));
        goodsImgs2.add(new Img(ImgPath.SHOPS_VOLLEYBALL2, (int) (Global.SCREEN_X * 0.065 * Global.ADJ), (int) (Global.SCREEN_Y * 0.663 * Global.ADJ), true));

        buttonImgs = new ArrayList<>();
        buttonImgs.add(new Img(ImgPath.B_HOME, (int) (Global.SCREEN_X * 0.01 * Global.ADJ), (int) (Global.SCREEN_Y * 0.3 * Global.ADJ), true));
        buttonImgs.add(new Img(ImgPath.B_GAME, (int) (Global.SCREEN_X * 0.01 * Global.ADJ), (int) (Global.SCREEN_Y * 0.4 * Global.ADJ), true));
        buttonImgs.add(new Img(ImgPath.B_SHOP, (int) (Global.SCREEN_X * 0.01 * Global.ADJ), (int) (Global.SCREEN_Y * 0.5 * Global.ADJ), true));
        buttonImgs.add(new Img(ImgPath.B_INFO, (int) (Global.SCREEN_X * 0.01 * Global.ADJ), (int) (Global.SCREEN_Y * 0.6 * Global.ADJ), true));

        buttonImgs2 = new ArrayList<>();
        buttonImgs2.add(new Img(ImgPath.B_HOME2, (int) (Global.SCREEN_X * 0.01 * Global.ADJ), (int) (Global.SCREEN_Y * 0.3 * Global.ADJ), true));
        buttonImgs2.add(new Img(ImgPath.B_GAME2, (int) (Global.SCREEN_X * 0.01 * Global.ADJ), (int) (Global.SCREEN_Y * 0.4 * Global.ADJ), true));
        buttonImgs2.add(new Img(ImgPath.NULL, (int) (Global.SCREEN_X * 0.01 * Global.ADJ), (int) (Global.SCREEN_Y * 0.5 * Global.ADJ), true));
        buttonImgs2.add(new Img(ImgPath.B_INFO2, (int) (Global.SCREEN_X * 0.01 * Global.ADJ), (int) (Global.SCREEN_Y * 0.6 * Global.ADJ), true));
        buttonImgs2.add(new Img(ImgPath.NULL, (int) (Global.SCREEN_X * 0 * Global.ADJ), (int) (Global.SCREEN_Y * 0 * Global.ADJ), true));
    }// end of begin

    @Override
    public void sceneUpdate() {
        if (delay.isTrig()) {

        }

    }

    @Override
    public void sceneEnd() {
        delay.stop();
        delay = null;
    }

    @Override
    public void paint(Graphics g) {
        imgs.get(0).paint(g);

        //右側說明欄        
        introImgs.get(mmcl.selectIndex).paint(g);

        //goodsImg part
        for (int i = 0; i < goodsImgs.size(); i++) {
            goodsImgs2.get(i).paint(g);
        }

        goodsImgs.get(mmcl.selectIndex).paint(g);

        //switch page part
        for (int i = 0; i < buttonImgs.size(); i++) {
            buttonImgs.get(i).paint(g);
        }
        buttonImgs2.get(mmcl.menuIndex).paint(g);

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