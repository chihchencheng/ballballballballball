/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scenes;

import controllers.SceneController;
import gameobj.Img;
import gameobj.imgArr;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import util.CommandSolver;
import util.CommandSolver.MouseCommandListener;
import util.CommandSolver.MouseState;
import util.Global;
import util.ImgPath;
import static util.ImgPath.*;

/**
 *
 * @author User
 */
public class MainScene extends Scene {

    private MyMouseCommandListener mmcl;
    private imgArr imgs;
    private imgArr imgsRole;
    private int roleNum;

    private class MyMouseCommandListener implements MouseCommandListener {

        private boolean tipsTrig = false;
        private int buttonIndex;
        private int menuIndex;

        @Override
        public void mouseTrig(MouseEvent e, CommandSolver.MouseState state, long trigTime) {

            //點擊立繪後換人
            if (state == MouseState.CLICKED && imgsRole.get(roleNum).isInside(e.getX(), e.getY())) {
                if (MainScene.this.roleNum++ >= imgsRole.size() - 1) {
                    System.out.println("roleNum=" + roleNum);
                    roleNum = 0;
                }
            }

            //按鈕功能
            if (state == MouseState.CLICKED && imgs.get(BB_GAMESTART).isInside(e.getX(), e.getY())) {
                sceneController.changeScene(new SelectionScene(sceneController));
            }   //----gameStart button

            tipsTrig = imgs.get(BB_TIPS).isInside(e.getX(), e.getY()); //----tips button
            if (state == MouseState.CLICKED && imgs.get(BB_SHOP).isInside(e.getX(), e.getY())) {
                sceneController.changeScene(new ShopScene(sceneController));
            }   //----Shop button

            //按鈕換圖            
            for (int i = 0; i < imgs.size() - 1; i++) {
                if (imgs.get(i).isInside(e.getX(), e.getY())) {
                    menuIndex = i;
                }
            }
            if (!imgs.get(BB_TIPS).isInside(e.getX(), e.getY()) && !imgs.get(BB_INFO).isInside(e.getX(), e.getY())
                    && !imgs.get(BB_RECORD).isInside(e.getX(), e.getY()) && !imgs.get(BB_SHOP).isInside(e.getX(), e.getY())
                    && !imgs.get(BB_GAMESTART).isInside(e.getX(), e.getY())) {
                menuIndex = imgs.size() - 1;
            }
        }

    }

    public MainScene(SceneController sceneController) {
        super(sceneController);
        this.roleNum = 0;
        this.mmcl = new MyMouseCommandListener();
    }

    public void sceneBegin() {
        imgs =new imgArr();
        imgs.add(new Img(ImgPath.HOW_TO_PLAY, (int) (Global.SCREEN_X * 0.13 * Global.ADJ), (int) (Global.SCREEN_Y * 0.17 * Global.ADJ), true));
        imgs.add(new Img(ImgPath.BK_TITLE, 0, 0, true)); //背景 i=0
        imgs.add(new Img(ImgPath.BB_TIPS, (int) (Global.SCREEN_X * 0.24 * Global.ADJ), (int) (Global.SCREEN_Y * 0.72 * Global.ADJ), true));
        imgs.add(new Img(ImgPath.BB_INFO, (int) (Global.SCREEN_X * 0.46 * Global.ADJ), (int) (Global.SCREEN_Y * 0.67 * Global.ADJ), true));
        imgs.add(new Img(ImgPath.BB_RECORD, (int) (Global.SCREEN_X * 0.6 * Global.ADJ), (int) (Global.SCREEN_Y * 0.61 * Global.ADJ), true));
        imgs.add(new Img(ImgPath.BB_SHOP, (int) (Global.SCREEN_X * 0.335 * Global.ADJ), (int) (Global.SCREEN_Y * 0.6 * Global.ADJ), true));
        imgs.add(new Img(ImgPath.BB_GAMESTART, (int) (Global.SCREEN_X * 0.051 * Global.ADJ), (int) (Global.SCREEN_Y * 0.53 * Global.ADJ), true));
        imgs.add(new Img(ImgPath.TITLE, (int) (Global.SCREEN_X * 0.038 * Global.ADJ), (int) (Global.SCREEN_Y * 0.085 * Global.ADJ), true));

//        //按鈕換圖
//        imgs.add(new Img(ImgPath.BB_TIPS2, (int) (Global.SCREEN_X * 0.24 * Global.ADJ), (int) (Global.SCREEN_Y * 0.72 * Global.ADJ), true));
//        imgs.add(new Img(ImgPath.BB_INFO2, (int) (Global.SCREEN_X * 0.46 * Global.ADJ), (int) (Global.SCREEN_Y * 0.67 * Global.ADJ), true));
//        imgs.add(new Img(ImgPath.BB_RECORD2, (int) (Global.SCREEN_X * 0.6 * Global.ADJ), (int) (Global.SCREEN_Y * 0.61 * Global.ADJ), true));
//        imgs.add(new Img(ImgPath.BB_SHOP2, (int) (Global.SCREEN_X * 0.335 * Global.ADJ), (int) (Global.SCREEN_Y * 0.6 * Global.ADJ), true));
//        imgs.add(new Img(ImgPath.BB_GAMESTART2, (int) (Global.SCREEN_X * 0.051 * Global.ADJ), (int) (Global.SCREEN_Y * 0.53 * Global.ADJ), true));
//        imgs.add(new Img(ImgPath.NULL, (int) (Global.SCREEN_X * 0.051 * Global.ADJ), (int) (Global.SCREEN_Y * 0.53 * Global.ADJ), true));
        
        //角色立繪
        imgsRole = new imgArr();
        imgsRole.add(new Img(ImgPath.TSAI, (int) (Global.SCREEN_X * 0.53 * Global.ADJ), (int) (Global.SCREEN_Y * 0.1 * Global.ADJ), true));
        imgsRole.add(new Img(ImgPath.ZHANG, (int) (Global.SCREEN_X * 0.6 * Global.ADJ), (int) (Global.SCREEN_Y * 0.1 * Global.ADJ), true));
        imgsRole.add(new Img(ImgPath.SHU, (int) (Global.SCREEN_X * 0.59 * Global.ADJ), (int) (Global.SCREEN_Y * 0.1 * Global.ADJ), true));
        imgsRole.add(new Img(ImgPath.ZHOU, (int) (Global.SCREEN_X * 0.65 * Global.ADJ), (int) (Global.SCREEN_Y * 0.1 * Global.ADJ), true));
        imgsRole.add(new Img(ImgPath.WANG, (int) (Global.SCREEN_X * 0.7 * Global.ADJ), (int) (Global.SCREEN_Y * 0.1 * Global.ADJ), true));
    }

    public void sceneUpdate() {
    }

    public void sceneEnd() {
    }

    public void paint(Graphics g) {
        //元件繪製
        for (int i = 1; i < imgs.size() - 1; i++) {
            imgs.get(i).paint(g);
        }
        
//        //按鈕換圖
//        imgs.get(mmcl.menuIndex).paint(g);

        if (!mmcl.tipsTrig) {
            imgs.get(imgs.size() - 1).paint(g);
            //人物繪製
            imgsRole.get(roleNum).paint(g);
        } else {
            imgs.get(0).paint(g);
        }
        g.setFont(new Font("微軟正黑體",Font.PLAIN,100));
        g.drawString("test", 100, 100);
    }

    @Override
    public CommandSolver.KeyListener getKeyListener() {
        return null;
    }

    @Override
    public CommandSolver.MouseCommandListener getMouseListener() {
        return mmcl;
    }

}
