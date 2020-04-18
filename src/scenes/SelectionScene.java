/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scenes;

import controllers.SceneController;
import gameobj.Img;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import util.CommandSolver;
import util.CommandSolver.MouseState;
import util.Global;
import util.ImgPath;

/**
 *
 * @author User
 */
public class SelectionScene extends Scene {


    public SelectionScene(SceneController sceneController) {
        super(sceneController);
    }

    public class MyMouseCommandListener implements CommandSolver.MouseCommandListener {

        private int menuIndex;
        private int roleSelection = 0;

        @Override
        public void mouseTrig(MouseEvent e, CommandSolver.MouseState state, long trigTime) {
            if (state == MouseState.CLICKED && imgs.get(2).isInside(e.getX(), e.getY())) {
                sceneController.changeScene(new GameStartScene(sceneController, roleSelection));
            }

            //角色選擇
            for (int i = 0; i < roleImgs.size(); i++) {
                if (roleImgs.get(i).isInside(e.getX(), e.getY()) && state == MouseState.CLICKED) {
                    roleSelection = i;
                }
            }

            //左側選單
            for (int i = 0; i < buttonImgs2.size() - 1; i++) {
                if (buttonImgs2.get(i).isInside(e.getX(), e.getY())) {
                    menuIndex = i;
                }
            }
            //-----按鈕換圖
            if (!buttonImgs2.get(0).isInside(e.getX(), e.getY()) && !buttonImgs2.get(1).isInside(e.getX(), e.getY())
                    && !buttonImgs2.get(2).isInside(e.getX(), e.getY()) && !buttonImgs2.get(3).isInside(e.getX(), e.getY())) {
                menuIndex = buttonImgs2.size() - 1;
            }

            //-----按鈕功能
            if (buttonImgs2.get(0).isInside(e.getX(), e.getY()) && state == MouseState.CLICKED) {
                sceneController.changeScene(new MainScene(sceneController));
            }//首頁
            if (buttonImgs2.get(2).isInside(e.getX(), e.getY()) && state == MouseState.CLICKED) {
                sceneController.changeScene(new ShopScene(sceneController));
            }//商店

        }
    }// end of inner class

    private MyMouseCommandListener mmcl;
    private ArrayList<Img> imgs;
    private ArrayList<Img> roleImgs;
    private ArrayList<Img> buttonImgs;
    private ArrayList<Img> buttonImgs2;

    @Override
    public void sceneBegin() {
        
        this.mmcl = new MyMouseCommandListener();
        
        imgs = new ArrayList<>();
        imgs.add(new Img(ImgPath.BK_MAIN, (int) (Global.SCREEN_X * 0 * Global.ADJ), (int) (Global.SCREEN_Y * 0 * Global.ADJ), true));
        imgs.add(new Img(ImgPath.ESSIENTIAL, (int) (Global.SCREEN_X * 0.16 * Global.ADJ), (int) (Global.SCREEN_Y * 0.08 * Global.ADJ), true));
        imgs.add(new Img(ImgPath.GAMESTART, (int) (Global.SCREEN_X * 0.315 * Global.ADJ), (int) (Global.SCREEN_Y * 0.785 * Global.ADJ), true));
        //locked Part,locked int =4
        imgs.add(new Img(ImgPath.LOCKED, (int) (Global.SCREEN_X * 0.308 * Global.ADJ), (int) (Global.SCREEN_Y * 0.52 * Global.ADJ), true));
        imgs.add(new Img(ImgPath.LOCKED, (int) (Global.SCREEN_X * 0.419 * Global.ADJ), (int) (Global.SCREEN_Y * 0.46 * Global.ADJ), true));
        imgs.add(new Img(ImgPath.LOCKED, (int) (Global.SCREEN_X * 0.529 * Global.ADJ), (int) (Global.SCREEN_Y * 0.52 * Global.ADJ), true));
        imgs.add(new Img(ImgPath.LOCKED, (int) (Global.SCREEN_X * 0.641 * Global.ADJ), (int) (Global.SCREEN_Y * 0.46 * Global.ADJ), true));

        roleImgs = new ArrayList<>();
        roleImgs.add(new Img(ImgPath.CHOSE_CHEERBALL, (int) (Global.SCREEN_X * 0.1995 * Global.ADJ), (int) (Global.SCREEN_Y * 0.137 * Global.ADJ), true));
        roleImgs.add(new Img(ImgPath.CHOSE_BASKETBALL, (int) (Global.SCREEN_X * 0.3078 * Global.ADJ), (int) (Global.SCREEN_Y * 0.19965 * Global.ADJ), true));
        roleImgs.add(new Img(ImgPath.CHOSE_BADMINTON, (int) (Global.SCREEN_X * 0.4188 * Global.ADJ), (int) (Global.SCREEN_Y * 0.137 * Global.ADJ), true));
        roleImgs.add(new Img(ImgPath.CHOSE_BASEBALL, (int) (Global.SCREEN_X * 0.5288 * Global.ADJ), (int) (Global.SCREEN_Y * 0.2029 * Global.ADJ), true));
        roleImgs.add(new Img(ImgPath.CHOSE_VOLLEYBALL, (int) (Global.SCREEN_X * 0.6405 * Global.ADJ), (int) (Global.SCREEN_Y * 0.137 * Global.ADJ), true));

        buttonImgs = new ArrayList<>();
        buttonImgs.add(new Img(ImgPath.B_HOME, (int) (Global.SCREEN_X * 0.01 * Global.ADJ), (int) (Global.SCREEN_Y * 0.3 * Global.ADJ), true));
        buttonImgs.add(new Img(ImgPath.B_GAME, (int) (Global.SCREEN_X * 0.01 * Global.ADJ), (int) (Global.SCREEN_Y * 0.4 * Global.ADJ), true));
        buttonImgs.add(new Img(ImgPath.B_SHOP, (int) (Global.SCREEN_X * 0.01 * Global.ADJ), (int) (Global.SCREEN_Y * 0.5 * Global.ADJ), true));
        buttonImgs.add(new Img(ImgPath.B_INFO, (int) (Global.SCREEN_X * 0.01 * Global.ADJ), (int) (Global.SCREEN_Y * 0.6 * Global.ADJ), true));

        buttonImgs2 = new ArrayList<>();
        buttonImgs2.add(new Img(ImgPath.B_HOME2, (int) (Global.SCREEN_X * 0.01 * Global.ADJ), (int) (Global.SCREEN_Y * 0.3 * Global.ADJ), true));
        buttonImgs2.add(new Img(ImgPath.NULL, (int) (Global.SCREEN_X * 0.01 * Global.ADJ), (int) (Global.SCREEN_Y * 0.4 * Global.ADJ), true));
        buttonImgs2.add(new Img(ImgPath.B_SHOP2, (int) (Global.SCREEN_X * 0.01 * Global.ADJ), (int) (Global.SCREEN_Y * 0.5 * Global.ADJ), true));
        buttonImgs2.add(new Img(ImgPath.B_INFO2, (int) (Global.SCREEN_X * 0.01 * Global.ADJ), (int) (Global.SCREEN_Y * 0.6 * Global.ADJ), true));
        buttonImgs2.add(new Img(ImgPath.NULL, (int) (Global.SCREEN_X * 0 * Global.ADJ), (int) (Global.SCREEN_Y * 0 * Global.ADJ), true));
    }

    @Override
    public void sceneUpdate() {

    }

    @Override
    public void sceneEnd() {

    }

    public int getRoleNum() {
        return mmcl.roleSelection; 
    }

    @Override
    public void paint(Graphics g) {
        //essential part
        for (int i = 0; i < imgs.size() - 4; i++) {
            imgs.get(i).paint(g);
        }

        //role button part
        roleImgs.get(mmcl.roleSelection).paint(g);

        //switch page part
        for (int i = 0; i < buttonImgs.size(); i++) {
            buttonImgs.get(i).paint(g);
        }
        buttonImgs2.get(mmcl.menuIndex).paint(g);

//        //locked
//        for (int i = imgs.size() - 4 - 1; i < imgs.size(); i++) {
//            imgs.get(i).paint(g);
//        }
    }

    @Override
    public CommandSolver.KeyListener getKeyListener() {
        return null;
    }

    @Override
    public CommandSolver.MouseCommandListener getMouseListener() {
        return this.mmcl;
    }
}
