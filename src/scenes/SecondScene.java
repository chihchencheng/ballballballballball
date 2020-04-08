package scenes;

import java.awt.Graphics;
import java.util.ArrayList;

import controllers.SceneController;
import gameobj.Ball;
import gameobj.Baseball;
import gameobj.Basketball;
import gameobj.Cheerball;
import gameobj.Rect;
import gameobj.Shuttlecock;
import gameobj.Volleyball;
import util.CommandSolver.KeyListener;
import util.CommandSolver.MouseCommandListener;
import util.Delay;
import util.Global;

public class SecondScene extends Scene {

	private Delay delay;

	public SecondScene(SceneController sceneController) {
		super(sceneController);
	}

	@Override
	public void sceneBegin() {
		delay = new Delay(10);
		delay.start();
	}// end of begin

	@Override
	public void sceneUpdate() {
		if(delay.isTrig()) {
			
		}
		

	}

	@Override
	public void sceneEnd() {
		delay.stop();
		delay = null;
	}

	@Override
	public void paint(Graphics g) {
		
	}

	public void update() {
		
	}// end of update

	private void genBalls() {
		
	}

	public void genRect() {
	
	}

	@Override
	public KeyListener getKeyListener() {
		
		return null;
	}

	@Override
	public MouseCommandListener getMouseListener() {
		
		return null;
	}

}// end of class
