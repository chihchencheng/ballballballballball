/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scenes;

import audio.AudioObject;
import controllers.SceneController;
import gameobj.Img;
import gameobj.ImgArr;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import util.AudioPath;
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
    private int roleNum;
    private String[] rolePaths = {TSAI, ZHANG, SHU, ZHOU, WANG};
    private String[] basicPic = {BK_TITLE};
    private String[] bButtonPaths = {BB_TIPS, BB_INFO, BB_RECORD, BB_SHOP, BB_GAMESTART};
    private AudioObject backMusic;

//    private String[] bButton2Paths = {BB_TIPS2, BB_INFO2, BB_RECORD2, BB_SHOP2, BB_GAMESTART2};

    private class MyMouseCommandListener implements MouseCommandListener {

        private boolean tipsTrig = false;
        private int buttonIndex;

        @Override
        public void mouseTrig(MouseEvent e, CommandSolver.MouseState state, long trigTime) {

            //點擊立繪後換人
            if (state == MouseState.CLICKED && imgs.get(rolePaths[roleNum]).isInside(e.getX(), e.getY())) {
                if (MainScene.this.roleNum++ >= rolePaths.length - 1) {
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
            if (state == MouseState.CLICKED && imgs.get(BB_INFO).isInside(e.getX(), e.getY())) {
                sceneController.changeScene(new InfoScene(sceneController));
            }   //----Info button

            //按鈕換圖            
            for (int i = 0; i < bButtonPaths.length; i++) {
                if (imgs.get(bButtonPaths[i]).isInside(e.getX(), e.getY())) {
                    buttonIndex = imgs.searchIndex(bButtonPaths[i]);
                }
            }

            if (!imgs.get(BB_TIPS).isInside(e.getX(), e.getY()) && !imgs.get(BB_INFO).isInside(e.getX(), e.getY())
                    && !imgs.get(BB_RECORD).isInside(e.getX(), e.getY()) && !imgs.get(BB_SHOP).isInside(e.getX(), e.getY())
                    && !imgs.get(BB_GAMESTART).isInside(e.getX(), e.getY())) {
                buttonIndex = imgs.size() - 1;
            }
        }
    }

    public MainScene(SceneController sceneController) {
        super(sceneController);

        this.roleNum = 0;    //預設為 蔡捷如
        this.backMusic = new AudioObject("Main",AudioPath.MAIN);
        this.mmcl = new MyMouseCommandListener();
    }

    public void sceneBegin() {
        this.backMusic.getAudio().loop();
        
        imgs.add(new Img(ImgPath.HOW_TO_PLAY, (int) (Global.SCREEN_X * 0.13 * Global.ADJ), (int) (Global.SCREEN_Y * 0.17 * Global.ADJ), true));
        imgs.add(new Img(ImgPath.BK_TITLE, 0, 0, true)); //背景 i=0

        //按鈕圖片
        imgs.add(new Img(ImgPath.BB_TIPS, (int) (Global.SCREEN_X * 0.24 * Global.ADJ), (int) (Global.SCREEN_Y * 0.72 * Global.ADJ), true));
        imgs.get(BB_TIPS).importPic(BB_TIPS2);
        imgs.add(new Img(ImgPath.BB_INFO, (int) (Global.SCREEN_X * 0.46 * Global.ADJ), (int) (Global.SCREEN_Y * 0.67 * Global.ADJ), true));
        imgs.get(BB_INFO).importPic(BB_INFO2);
        imgs.add(new Img(ImgPath.BB_RECORD, (int) (Global.SCREEN_X * 0.6 * Global.ADJ), (int) (Global.SCREEN_Y * 0.61 * Global.ADJ), true));
        imgs.get(BB_RECORD).importPic(BB_RECORD2);
        imgs.add(new Img(ImgPath.BB_SHOP, (int) (Global.SCREEN_X * 0.335 * Global.ADJ), (int) (Global.SCREEN_Y * 0.6 * Global.ADJ), true));
        imgs.get(BB_SHOP).importPic(BB_SHOP2);
        imgs.add(new Img(ImgPath.BB_GAMESTART, (int) (Global.SCREEN_X * 0.051 * Global.ADJ), (int) (Global.SCREEN_Y * 0.53 * Global.ADJ), true));
        imgs.get(BB_GAMESTART).importPic(BB_GAMESTART2);

        imgs.add(new Img(ImgPath.TITLE, (int) (Global.SCREEN_X * 0.038 * Global.ADJ), (int) (Global.SCREEN_Y * 0.085 * Global.ADJ), true));

        //角色立繪
        for (int i = 0; i < rolePaths.length; i++) {
            imgs.add(new Img(rolePaths[i], (int) (Global.SCREEN_X * 0.53 * Global.ADJ), (int) (Global.SCREEN_Y * 0.1 * Global.ADJ), true));
        }

        //private String[] rolePaths = {TSAI, ZHANG, SHU, ZHOU, WANG};
        imgs.get(ZHANG).offset((int) (Global.SCREEN_X * 0.03 * Global.ADJ), 0);
        imgs.get(SHU).offset((int) (Global.SCREEN_X * 0.025 * Global.ADJ), 0);
        imgs.get(ZHOU).offset((int) (Global.SCREEN_X * 0.065 * Global.ADJ), 0);
        imgs.get(WANG).offset((int) (Global.SCREEN_X * 0.08 * Global.ADJ), 0);

        //最後一張設為null
        imgs.add(new Img(ImgPath.NULL, (int) (Global.SCREEN_X * 0 * Global.ADJ), (int) (Global.SCREEN_Y * 0 * Global.ADJ), true));
    }

    public void sceneUpdate() {
    }

    public void sceneEnd() {
        this.backMusic.getAudio().stop();
    }

    public void paint(Graphics g) {
        //元件繪製
        for (int i = 0; i < basicPic.length; i++) {
            imgs.get(basicPic[i]).paint(g);
        }

        //按鈕繪製
        for (int i = 0; i < bButtonPaths.length; i++) {
            imgs.get(bButtonPaths[i]).paint(g);
        }

        //按鈕圖切換
//        System.out.println("nowButton=" + imgs.get(mmcl.buttonIndex).getNowImgPath());//test
        imgs.get(mmcl.buttonIndex).switchNowImage(1);
        for (int i = 0; i < bButtonPaths.length; i++) {
            if (!(imgs.searchIndex(bButtonPaths[i]) == mmcl.buttonIndex)) {
                imgs.get(bButtonPaths[i]).switchNowImage(0);
            }
        }

        //說明圖顯示
        if (!mmcl.tipsTrig) {
            //繪製標題
            imgs.get(TITLE).paint(g);
            //人物繪製
            imgs.get(rolePaths[roleNum]).paint(g);
        } else {
            //說明文字
            imgs.get(HOW_TO_PLAY).paint(g);
        }

//        //寫字測試
//        g.setFont(new Font("微軟正黑體", Font.PLAIN, 100));
//        g.drawString("test", 100, 100);
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
