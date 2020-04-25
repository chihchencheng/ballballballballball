/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scenes;

import controllers.SceneController;
import gameobj.Img;
import inputSection.Button_creatAccount;
import inputSection.AEditText;
import inputSection.Button.OnClickListener;
import inputSection.Component;
import inputSection.EditText;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import util.CommandSolver;
import util.Delay;
import static util.Global.*;
import static util.ImgPath.*;

/**
 *
 * @author User
 */
public class CreatAccountScene extends Scene {

    EditText etA;
    Button_creatAccount btnEnter;

    public CreatAccountScene(SceneController sceneController) {
        super(sceneController);
        btnEnter = new Button_creatAccount((int) (0.35 * ADJ_X), (int) (0.55 * ADJ_Y), B_LOGIN);
        System.out.println("btnEnter[i]=" + btnEnter.addImg(B_LOGIN2));
        System.out.println("btnEnter[i]=" + btnEnter.addImg(B_LOGIN3));
        btnEnter.printImgArr();
        btnEnter.setListener(new ButtonClickListener());
    }

    public void sceneBegin() {
        etA = new AEditText((int) (ADJ_X * 0.3), (int) (ADJ_Y * 0.4), 300, 150);
        etA.setHint("enter your name");
        imgs.add(new Img(BK_LOGIN, 0, 0, true));
        imgs.add(new Img(ENTER_NAME, (int) (ADJ_X * 0.2), (int) (ADJ_Y * 0.4), true));
    }

    public void sceneUpdate() {
    }

    public void sceneEnd() {
    }

    public void paint(Graphics g) {
        imgs.get(BK_LOGIN).paint(g);
        imgs.get(ENTER_NAME).paint(g);
        btnEnter.paint(g);
        etA.paint(g);

    }

    // -----------------------------------------------------Listener Part
    public class ButtonClickListener implements OnClickListener {

        @Override
        public void onClick(Component c) {
            System.out.println(c);  //印出元件c的記憶體位置
            sceneController.changeScene(new MainScene(sceneController));
            
        }
    }

    @Override
    public CommandSolver.KeyListener getKeyListener() {
        return new MyKeyListener();
    }

    @Override
    public CommandSolver.MouseCommandListener getMouseListener() {
        return new MyMouseListener();
    }

    public class MyMouseListener implements CommandSolver.MouseCommandListener {

        @Override
        public void mouseTrig(MouseEvent e, CommandSolver.MouseState state, long trigTime) {
            btnEnter.update(e, state);
            if (state == CommandSolver.MouseState.RELEASED
                    && etA.withinRange(e.getX(), e.getY())) {
                etA.active();
            }
        }
    }

    public class MyKeyListener implements CommandSolver.KeyListener {

        @Override
        public void keyPressed(int commandCode, long trigTime) {
        }

        @Override
        public void keyReleased(int commandCode, long trigTime) {
        }

        @Override
        public void keyTyped(char c, long trigTime) {
            etA.update(c, trigTime);
        }

    }

}
