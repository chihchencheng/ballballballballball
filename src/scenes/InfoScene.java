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
import util.CommandSolver;
import util.CommandSolver.MouseCommandListener;
import util.Global;
import util.ImgPath;
import static util.ImgPath.*;

/**
 *
 * @author User
 */
public class InfoScene extends Scene {

    private MyMouseCommandListener mmcl;
    private String[] button = {B_HOME, B_GAME, B_SHOP, B_INFO};

    private class MyMouseCommandListener implements MouseCommandListener {
        private String buttonSelectPath = NULL;
        
        @Override
        public void mouseTrig(MouseEvent e, CommandSolver.MouseState state, long trigTime) {
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

            //-----按鈕功能
            if (imgs.get(B_HOME).isInside(e.getX(), e.getY()) && state == CommandSolver.MouseState.CLICKED) {
                sceneController.changeScene(new MainScene(sceneController));
            }   //首頁
            if (imgs.get(B_GAME).isInside(e.getX(), e.getY()) && state == CommandSolver.MouseState.CLICKED) {
                sceneController.changeScene(new SelectionScene(sceneController));
            }   //遊戲開始
            if (imgs.get(B_SHOP).isInside(e.getX(), e.getY()) && state == CommandSolver.MouseState.CLICKED) {
                sceneController.changeScene(new ShopScene(sceneController));
            }   //商店
        }
    }

    public InfoScene(SceneController sceneController) {
        super(sceneController);
    }

    @Override
    public void sceneBegin() {
        mmcl=new MyMouseCommandListener();
        
        imgs.add(new Img(BK_INFO, 0, 0, true));
        imgs.add(new Img(ACCOUNT_DATA, (int) (Global.SCREEN_X * 0.08 * Global.ADJ), (int) (Global.SCREEN_Y * 0.03 * Global.ADJ), true));
        imgs.add(new Img(RANK_SCORE, (int) (Global.SCREEN_X * 0.45 * Global.ADJ), (int) (Global.SCREEN_Y * 0.03 * Global.ADJ), true));

        imgs.add(new Img(ImgPath.B_HOME, (int) (Global.SCREEN_X * 0.01 * Global.ADJ), (int) (Global.SCREEN_Y * 0.3 * Global.ADJ), true));
        imgs.get(B_HOME).importPic(B_HOME2);
        imgs.add(new Img(ImgPath.B_GAME, (int) (Global.SCREEN_X * 0.01 * Global.ADJ), (int) (Global.SCREEN_Y * 0.4 * Global.ADJ), true));
        imgs.get(B_GAME).importPic(B_GAME2);
        imgs.add(new Img(ImgPath.B_SHOP, (int) (Global.SCREEN_X * 0.01 * Global.ADJ), (int) (Global.SCREEN_Y * 0.5 * Global.ADJ), true));
        imgs.get(B_SHOP).importPic(B_SHOP2);
        imgs.add(new Img(ImgPath.B_INFO, (int) (Global.SCREEN_X * 0.01 * Global.ADJ), (int) (Global.SCREEN_Y * 0.6 * Global.ADJ), true));
        imgs.get(B_INFO).importPic(B_INFO2);

        imgs.add(new Img(NULL, 0, 0, true));
    }

    @Override
    public void sceneUpdate() {
    }

    @Override
    public void sceneEnd() {
    }

    @Override
    public void paint(Graphics g) {
        imgs.get(BK_INFO).paint(g);
        imgs.get(ACCOUNT_DATA).paint(g);
        imgs.get(RANK_SCORE).paint(g);

        for (int i = 0; i < button.length; i++) {
            imgs.get(button[i]).paint(g);
        }
        for (int i = 0; i < button.length; i++) {
            if (!mmcl.buttonSelectPath.equals(button[i])) {
                imgs.get(button[i]).switchNowImage(0);
            } else {
                imgs.get(mmcl.buttonSelectPath).switchNowImage(1);
            }
        }

    }

    // 
    @Override
    public CommandSolver.KeyListener getKeyListener() {
        return null;
    }

    @Override
    public CommandSolver.MouseCommandListener getMouseListener() {
        return mmcl;
    }

}
