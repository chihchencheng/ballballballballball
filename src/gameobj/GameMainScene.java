package gameobj;

import java.awt.Graphics;

import util.Global;
import util.ImgPath;

public class GameMainScene extends GameObject {
	
	public GameMainScene() {
		super(ImgPath.MAINSCENE,0,0,Global.FRAME_X, Global.FRAME_Y);
	}

	@Override
	public void update() {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean move() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	protected void paintComponent(Graphics g) {
		// TODO Auto-generated method stub

	}

}
