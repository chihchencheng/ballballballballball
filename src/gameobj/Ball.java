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
	private String name;

	public Ball(String imgPath,String name, int x, int y) {
		super(imgPath, x, y, 
				(int) (Global.UNIT_X * Global.CHARACTER_SIZE_ADJ),
				(int) (Global.UNIT_Y * Global.CHARACTER_SIZE_ADJ),
				(int) (Global.UNIT_X * Global.CHARACTER_SIZE_ADJ),
				(int) (Global.UNIT_Y * Global.CHARACTER_SIZE_ADJ+2),
				true);
		this.name = name;
		this.speed = 4;
	}

	public boolean isCollision(Ball ball) {
		if (this.rect.left() > ball.rect.right())
			return false;
		if (this.rect.right() < ball.rect.left())
			return false;
		if (this.rect.top() > ball.rect.bottom())
			return false;
		if (this.rect.bottom() < ball.rect.top())
			return false;
		return true;
	}

	public void setSpeed(int speed) {
		this.speed = speed;
	}

	public int getSpeed() {
		return this.speed;
	}
	
	public Ball ball() {
		return this;
	}
	
	public String getName() {
		return this.name;
	}

	@Override
	public boolean move() {
		if (this.rect.top() >= Global.YendPoint) {
			this.setSpeed(0);
		} else if (this.rect.bottom() < Global.YendPoint) {
			this.offset(0, speed);
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
