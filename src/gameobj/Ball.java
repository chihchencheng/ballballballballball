package gameobj;

import java.awt.Graphics;
import java.awt.Image;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import util.Global;

public class Ball extends GameObject {
	private int d;
	private int a;

	public Ball(String imgPath, int x, int y) {
		super(imgPath, x, y, 
			(int) (105 * Global.CHARACTER_SIZE_ADJ),
			(int) (105 * Global.CHARACTER_SIZE_ADJ));
		this.d = 4;
		this.a=1;
	}

	public boolean isCollision(Ball ball) {
		if (this.getLeft() > ball.getRight())
			return false;
		if (this.getRight() < ball.getLeft())
			return false;
		if (this.getTop() > ball.getBottom())
			return false;
		if (this.getBottom() < ball.getTop())
			return false;
		return true;
	}

	@Override
	public boolean move() {
		if (this.y >= 525) {
			this.y = 525;
		} else if (this.y < 525) {
			this.y += d;
//			d+=a;
		} 
		return true;
	}


	@Override
	protected void paintComponent(Graphics g) {
	
		
	}


	@Override
	public void update() {
	}

}
