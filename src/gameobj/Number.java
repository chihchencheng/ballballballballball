package gameobj;

import java.awt.Graphics;

import util.Global;

public class Number extends GameObject {
	
	public Number(String imgPath, int x, int y, int width, int height) {
		super(imgPath,x,y,width,height,true);
	}
	public Number(String imgPath) {
		super(imgPath);
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
