package gameobj;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import util.Global;

public class Ball extends GameObject {
	private int speed;
	private int a;

	public Ball(String imgPath, int x, int y) {
		super(imgPath, x, y, 
			(int) (105 * Global.CHARACTER_SIZE_ADJ),
			(int) (105 * Global.CHARACTER_SIZE_ADJ));
		this.speed = 4;
		this.a = 1;
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
	
	public void setSpeed(int speed) {
		this.speed = speed;
	}
	
	public int getSpeed() {
		return this.speed;
	}

	@Override
	public boolean move() {
		if (this.y >= 525) {
			this.y = 525;
		} else if (this.y < 525) {
			this.y += speed;
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
