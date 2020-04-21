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
import static util.ImgPath.*;

/**
 *
 * @author User
 */
public class SelectionScene extends Scene {

    String[] rolePaths = {CHOSE_CHEERBALL, CHOSE_BASKETBALL, CHOSE_BADMINTON, CHOSE_BASEBALL, CHOSE_VOLLEYBALL};
    String[] button = {B_HOME, B_GAME, B_SHOP, B_INFO};
    int[][] selectSignSites = {
        {(int) (Global.SCREEN_X * 0.1995 * Global.ADJ), (int) (Global.SCREEN_Y * 0.137 * Global.ADJ)},
        {(int) (Global.SCREEN_X * 0.3078 * Global.ADJ), (int) (Global.SCREEN_Y * 0.19965 * Global.ADJ)},
        {(int) (Global.SCREEN_X * 0.4188 * Global.ADJ), (int) (Global.SCREEN_Y * 0.137 * Global.ADJ)},
        {(int) (Global.SCREEN_X * 0.5288 * Global.ADJ), (int) (Global.SCREEN_Y * 0.2029 * Global.ADJ)},
        {(int) (Global.SCREEN_X * 0.6405 * Global.ADJ), (int) (Global.SCREEN_Y * 0.137 * Global.ADJ)}
    };

    private MyMouseCommandListener mmcl;

    public SelectionScene(SceneController sceneController) {
        super(sceneController);
    }

    public class MyMouseCommandListener implements CommandSolver.MouseCommandListener {

        private String roleSelectionPath = ImgPath.CHOSE_CHEERBALL;
        private boolean gameStartSelected = false;
        private String buttonSelectPath;
        private boolean inButton = false;

        @Override
        public void mouseTrig(MouseEvent e, CommandSolver.MouseState state, long trigTime) {
            //切換至 遊戲開始畫面
            if (state == MouseState.CLICKED && imgs.get(GAMESTART).isInside(e.getX(), e.getY())) {
//                sceneController.changeScene(new GameStartScene(sceneController, roleSelectionPath));
            }
            gameStartSelected = (imgs.get(GAMESTART).isInside(e.getX(), e.getY()));

            //選單圖示轉換
            for (int i = 0; i < button.length; i++) {
                if (imgs.get(button[i]).isInside(e.getX(), e.getY())) {
                    buttonSelectPath = button[i];
                    inButton = true;
                }
            }
            if (!imgs.get(button[0]).isInside(e.getX(), e.getY())&&!imgs.get(button[1]).isInside(e.getX(),e.getY())&&
                    !imgs.get(button[2]).isInside(e.getX(), e.getY())&&!imgs.get(button[3]).isInside(e.getX(), e.getY())) {
                inButton = false;
            }

            //-----按鈕功能
            if (imgs.get(B_HOME).isInside(e.getX(), e.getY()) && state == MouseState.CLICKED) {
                sceneController.changeScene(new MainScene(sceneController));
            }   //首頁

            if (imgs.get(B_SHOP).isInside(e.getX(), e.getY()) && state == MouseState.CLICKED) {
//                sceneController.changeScene(new ShopScene(sceneController));
            }   //商店

            if (imgs.get(B_INFO).isInside(e.getX(), e.getY()) && state == MouseState.CLICKED) {
//                sceneController.changeScene(new ShopScene(sceneController));
            }   //資訊

        }
    }// end of inner class

//    private ArrayList<Img> roleImgs;
    @Override
    public void sceneBegin() {

        this.mmcl = new MyMouseCommandListener();

        imgs.add(new Img(ImgPath.BK_MAIN, (int) (Global.SCREEN_X * 0 * Global.ADJ), (int) (Global.SCREEN_Y * 0 * Global.ADJ), true));
        imgs.add(new Img(ImgPath.SELECTION_PANEL, (int) (Global.SCREEN_X * 0.16 * Global.ADJ), (int) (Global.SCREEN_Y * 0.08 * Global.ADJ), true));
        imgs.add(new Img(ImgPath.GAMESTART, (int) (Global.SCREEN_X * 0.315 * Global.ADJ), (int) (Global.SCREEN_Y * 0.785 * Global.ADJ), true));
        imgs.get(GAMESTART).importPic(GAMESTART2);

        //locked Part,locked int =4
        imgs.add(new Img(ImgPath.LOCKED, (int) (Global.SCREEN_X * 0.308 * Global.ADJ), (int) (Global.SCREEN_Y * 0.52 * Global.ADJ), true));
        imgs.add(new Img(ImgPath.LOCKED, (int) (Global.SCREEN_X * 0.419 * Global.ADJ), (int) (Global.SCREEN_Y * 0.46 * Global.ADJ), true));
        imgs.add(new Img(ImgPath.LOCKED, (int) (Global.SCREEN_X * 0.529 * Global.ADJ), (int) (Global.SCREEN_Y * 0.52 * Global.ADJ), true));
        imgs.add(new Img(ImgPath.LOCKED, (int) (Global.SCREEN_X * 0.641 * Global.ADJ), (int) (Global.SCREEN_Y * 0.46 * Global.ADJ), true));

        imgs.add(new Img(ImgPath.CHOSE_CHEERBALL, (int) (Global.SCREEN_X * 0.1995 * Global.ADJ), (int) (Global.SCREEN_Y * 0.137 * Global.ADJ), true));
        imgs.get(CHOSE_CHEERBALL).importPic(CHOSE_CHEERBALL2);
        imgs.add(new Img(ImgPath.CHOSE_BASKETBALL, (int) (Global.SCREEN_X * 0.3078 * Global.ADJ), (int) (Global.SCREEN_Y * 0.19965 * Global.ADJ), true));
        imgs.get(CHOSE_BASKETBALL).importPic(CHOSE_BASKETBALL2);
        imgs.add(new Img(ImgPath.CHOSE_BADMINTON, (int) (Global.SCREEN_X * 0.4188 * Global.ADJ), (int) (Global.SCREEN_Y * 0.137 * Global.ADJ), true));
        imgs.get(CHOSE_BADMINTON).importPic(CHOSE_BADMINTON2);
        imgs.add(new Img(ImgPath.CHOSE_BASEBALL, (int) (Global.SCREEN_X * 0.5288 * Global.ADJ), (int) (Global.SCREEN_Y * 0.2029 * Global.ADJ), true));
        imgs.get(CHOSE_BASEBALL).importPic(CHOSE_BASEBALL2);
        imgs.add(new Img(ImgPath.CHOSE_VOLLEYBALL, (int) (Global.SCREEN_X * 0.6405 * Global.ADJ), (int) (Global.SCREEN_Y * 0.137 * Global.ADJ), true));
        imgs.get(CHOSE_VOLLEYBALL).importPic(CHOSE_VOLLEYBALL2);

        imgs.add(new Img(ImgPath.B_HOME, (int) (Global.SCREEN_X * 0.01 * Global.ADJ), (int) (Global.SCREEN_Y * 0.3 * Global.ADJ), true));
        imgs.get(B_HOME).importPic(B_HOME2);
        imgs.add(new Img(ImgPath.B_GAME, (int) (Global.SCREEN_X * 0.01 * Global.ADJ), (int) (Global.SCREEN_Y * 0.4 * Global.ADJ), true));
        imgs.get(B_GAME).importPic(B_GAME2);
        imgs.add(new Img(ImgPath.B_SHOP, (int) (Global.SCREEN_X * 0.01 * Global.ADJ), (int) (Global.SCREEN_Y * 0.5 * Global.ADJ), true));
        imgs.get(B_SHOP).importPic(B_SHOP2);
        imgs.add(new Img(ImgPath.B_INFO, (int) (Global.SCREEN_X * 0.01 * Global.ADJ), (int) (Global.SCREEN_Y * 0.6 * Global.ADJ), true));
        imgs.get(B_INFO).importPic(B_INFO2);

        //select sign
        imgs.add(new Img(ImgPath.SELECT, (int) (Global.SCREEN_X * 0.1995 * Global.ADJ), (int) (Global.SCREEN_Y * 0.137 * Global.ADJ), true));

    }

    @Override
    public void sceneUpdate() {

    }

    @Override
    public void sceneEnd() {

    }

    @Override
    public void paint(Graphics g) {
        imgs.get(BK_MAIN).paint(g);
        imgs.get(SELECTION_PANEL).paint(g);
        imgs.get(GAMESTART).paint(g);

        //role part
        for (int i = 0; i < rolePaths.length; i++) {
            imgs.get(rolePaths[i]).paint(g);
            if (!mmcl.roleSelectionPath.equals(rolePaths[i])) {
                imgs.get(rolePaths[i]).switchNowImage(0);
            }
        }
        imgs.get(mmcl.roleSelectionPath).switchNowImage(1);

        //遊戲開始按鈕
        if (mmcl.gameStartSelected) {
            imgs.get(ImgPath.GAMESTART).switchNowImage(1);
        } else {
            imgs.get(ImgPath.GAMESTART).switchNowImage(0);
        }

        //左側選單
        for (int i = 0; i < button.length; i++) {
            imgs.get(button[i]).paint(g);
        }
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
