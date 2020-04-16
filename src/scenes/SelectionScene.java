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

        @Override
        public void mouseTrig(MouseEvent e, CommandSolver.MouseState state, long trigTime) {
//            System.out.println("SelectionScene MouseCommand Listener");
            if (state == MouseState.CLICKED && imgs.get(2).isInside(e.getX(), e.getY())) {
//                System.out.println("gameStartButton CLICKED");
                sceneController.changeScene(new GameStartScene(sceneController, 0));
            }

        }
    }// end of inner class

    private MyMouseCommandListener mmcl;
    private ArrayList<Img> imgs;
    private ArrayList<Img> roleImgs;
    private ArrayList<Img> buttonImgs;

    @Override
    public void sceneBegin() {
        this.mmcl=new MyMouseCommandListener();
        
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
        roleImgs.add(new Img(ImgPath.CHOSE_CHEERBALL, (int) (Global.SCREEN_X * 0.2 * Global.ADJ), (int) (Global.SCREEN_Y * 0.14 * Global.ADJ),true));
        roleImgs.add(new Img(ImgPath.CHOSE_BASKETBALL, (int) (Global.SCREEN_X * 0.308 * Global.ADJ), (int) (Global.SCREEN_Y * 0.202 * Global.ADJ), true));
        roleImgs.add(new Img(ImgPath.CHOSE_BADMINTON, (int) (Global.SCREEN_X * 0.419 * Global.ADJ), (int) (Global.SCREEN_Y * 0.139 * Global.ADJ), true));
        roleImgs.add(new Img(ImgPath.CHOSE_BASEBALL, (int) (Global.SCREEN_X * 0.529 * Global.ADJ), (int) (Global.SCREEN_Y * 0.203 * Global.ADJ),  true));
        roleImgs.add(new Img(ImgPath.CHOSE_VOLLEYBALL, (int) (Global.SCREEN_X * 0.641 * Global.ADJ), (int) (Global.SCREEN_Y * 0.138 * Global.ADJ),  true));

        buttonImgs = new ArrayList<>();
        buttonImgs.add(new Img(ImgPath.B_HOME, (int) (Global.SCREEN_X * 0.01 * Global.ADJ), (int) (Global.SCREEN_Y * 0.3 * Global.ADJ),  true));
        buttonImgs.add(new Img(ImgPath.B_GAME, (int) (Global.SCREEN_X * 0.01 * Global.ADJ), (int) (Global.SCREEN_Y * 0.4 * Global.ADJ),  true));
        buttonImgs.add(new Img(ImgPath.B_SHOP, (int) (Global.SCREEN_X * 0.01 * Global.ADJ), (int) (Global.SCREEN_Y * 0.5 * Global.ADJ),  true));
        buttonImgs.add(new Img(ImgPath.B_INFO, (int) (Global.SCREEN_X * 0.01 * Global.ADJ), (int) (Global.SCREEN_Y * 0.6 * Global.ADJ), true));
    }

    @Override
    public void sceneUpdate() {

    }

    @Override
    public void sceneEnd() {

    }

    @Override
    public void paint(Graphics g) {
        //essential part
        for (int i = 0; i < imgs.size() - 4; i++) {
            imgs.get(i).paint(g);
        }

        //role button part
        for (int i = 0; i < roleImgs.size(); i++) {
            roleImgs.get(i).paint(g);
        }

        //switch page part
        for (int i = 0; i < buttonImgs.size(); i++) {
            buttonImgs.get(i).paint(g);
        }

        //locked
        for (int i = imgs.size() - 4 - 1; i < imgs.size(); i++) {
            imgs.get(i).paint(g);
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
