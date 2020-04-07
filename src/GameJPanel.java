import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.swing.JPanel;

import controllers.ImageResourceController;
import controllers.SceneController;
import gameobj.*;
import scenes.*;
import util.*;
import util.CommandSolver.KeyListener;
import util.CommandSolver.MouseCommandListener;

public class GameJPanel extends JPanel implements KeyListener, MouseCommandListener {
	private SceneController sceneController;
	private Scene scene;
	private Delay delay;

	public GameJPanel() {
		sceneController = new SceneController();
		sceneController.changeScene(new GameStartScene(sceneController));

	}// end of constructor

	public void update() {
//		if(delay.isTrig()) {
//			scene = new SecondScene(sceneController);
//		sceneController.changeScene(new GameStartScene(sceneController));
//			sceneController.changeScene(scene);
//			delay.stop();
//		}
		sceneController.update();
	}

	@Override
	public void paintComponent(Graphics g) {
		sceneController.paint(g);
	}

	@Override // �L�XtrigTime�i�H���D��e�ƹ��ʧ@���@���
	public void mouseTrig(MouseEvent e, CommandSolver.MouseState state, long trigTime) {
		if (state != null && sceneController.getML() != null) {
//          System.out.println("MouseState�G" + state + " " + trigTime);
			sceneController.getML().mouseTrig(e, state, trigTime);
		}
	}

	@Override
	public void keyPressed(int commandCode, long trigTime) {
		if (sceneController.getKL() != null) {
			sceneController.getKL().keyPressed(commandCode, trigTime);
		}
	}

	@Override
	public void keyReleased(int commandCode, long trigTime) {
		if (sceneController.getKL() != null) {
			sceneController.getKL().keyReleased(commandCode, trigTime);
		}

	}

	@Override
	public void keyTyped(char c, long trigTime) {
		if (sceneController.getKL() != null) {
			sceneController.getKL().keyTyped(c, trigTime);
		}

	}
}// end of class
