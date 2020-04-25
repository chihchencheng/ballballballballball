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
	private String name;

	private boolean isPress;

	public Ball(String imgPath, String name, int x, int y) {
		super(imgPath, x, y, (int) (Global.UNIT_X * Global.ADJ),
				(int) (Global.UNIT_Y * Global.ADJ), (int) (Global.UNIT_X * Global.ADJ),
				(int) (Global.UNIT_Y * Global.ADJ ), true);
		this.name = name;
		this.speed = 4;
	}

	public Ball(String imgPath, String name) {
		super(imgPath);
		this.name = name;
		this.speed = 4;
	}

	public boolean isCollision(Ball ball) {
//		if (this.rect.left() > ball.rect.right())
//			return false;
//		if (this.rect.right() < ball.rect.left())
//			return false;
		if (this.collider().top() > ball.collider().bottom())
			return false;
		if (this.collider().bottom() < ball.collider().top())
			return false;
		return true;
	}

	public void setSpeed(int speed) {
		this.offset(0, speed);
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
		if (this.collider().bottom() >= Global.YendPoint) {
			this.setSpeed(0);
			return false;
		} else if (this.collider().bottom() < Global.YendPoint) {
			this.setSpeed(speed);
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
