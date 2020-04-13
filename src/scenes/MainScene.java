/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scenes;

import controllers.SceneController;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import util.CommandSolver;
import util.CommandSolver.MouseCommandListener;

/**
 *
 * @author User
 */
public class MainScene extends Scene {

    private MyMouseCommandListener mmcl;

    private class MyMouseCommandListener implements MouseCommandListener {
        @Override
        public void mouseTrig(MouseEvent e, CommandSolver.MouseState state, long trigTime) {
           
            
        }

    }

    public MainScene(SceneController sceneController) {
        super(sceneController);
        this.mmcl = new MyMouseCommandListener();
    }

    public void sceneBegin() {
        
        
    }

    public void sceneUpdate() {
    }

    public void sceneEnd() {
    }

    public void paint(Graphics g) {
        
        
    }

    // 
    public CommandSolver.KeyListener getKeyListener() {
        return null;
    }

    public CommandSolver.MouseCommandListener getMouseListener() {
        return mmcl;
    }

}
