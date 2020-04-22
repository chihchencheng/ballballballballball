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

        private String roleSelectionPath = ImgPath.CHOSE_CHEERBALL;

        private boolean gameStartSelected = false;
        private String buttonSelectPath=NULL;
        private boolean inButton = false;

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
            if (!imgs.get(button[0]).isInside(e.getX(), e.getY()) && !imgs.get(button[1]).isInside(e.getX(), e.getY())
                    && !imgs.get(button[2]).isInside(e.getX(), e.getY()) && !imgs.get(button[3]).isInside(e.getX(), e.getY())) {
                inButton = false;
                buttonSelectPath =NULL;
            }

            //-----人物點選
            for (int i = 0; i < rolePaths.length; i++) {
                if (imgs.get(rolePaths[i]).isInside(e.getX(), e.getY()) && state == MouseState.CLICKED) {
                    roleSelectionPath = rolePaths[i];
                }
            }

            //-----按鈕功能
            if (buttonImgs2.get(0).isInside(e.getX(), e.getY()) && state == MouseState.CLICKED) {
                sceneController.changeScene(new MainScene(sceneController));
            }   //首頁

            if (imgs.get(B_SHOP).isInside(e.getX(), e.getY()) && state == MouseState.CLICKED) {
                sceneController.changeScene(new ShopScene(sceneController));
            }   //商店

            if (imgs.get(B_INFO).isInside(e.getX(), e.getY()) && state == MouseState.CLICKED) {
//                sceneController.changeScene(new ShopScene(sceneController));
            }   //資訊

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
        
        //null
        imgs.add(new Img(ImgPath.NULL, (int) (Global.SCREEN_X * 0 * Global.ADJ), (int) (Global.SCREEN_Y * 0 * Global.ADJ), true));
        

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
        for(int i=0;i<button.length;i++){
            if(!mmcl.buttonSelectPath.equals(button[i])){
                imgs.get(button[i]).switchNowImage(0);
            }        
        }
        imgs.get(mmcl.buttonSelectPath).switchNowImage(1);

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
