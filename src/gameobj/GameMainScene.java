package gameobj;

import java.awt.Graphics;

import util.Global;
import util.ImgPath;

public class GameMainScene extends GameObject {
	
	public GameMainScene() {
		super(ImgPath.GAMESTART,0,0,
			(int)(Global.FRAME_X*Global.CHARACTER_SIZE_ADJ),
			(int)(Global.FRAME_Y*Global.CHARACTER_SIZE_ADJ),true);//1608, 828
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
		//g.drawImage(img, 0, 0, 1608, 828, null);

	}

}
