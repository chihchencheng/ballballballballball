package gameobj;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import controllers.ImageResourceController;
import graph.Rect;
import util.Global;

public abstract class GameObject {
	protected BufferedImage img;
	protected int x;
	protected int y;
	protected int width;
	protected int height;
	protected int left;
	protected int top;
	protected int right;
	protected int bottom;

	public GameObject(String imgPath, int x, int y, int width, int height) {
		this.img = ImageResourceController.getInstance().tryGetImage(imgPath);
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;

	}// end of constructor

	public GameObject(String imgPath) {
		this.img = ImageResourceController.getInstance().tryGetImage(imgPath);
	}

	public GameObject() {

	}

	public void setX(int x) {
		this.x = x;
	}

	public int getX() {
		return this.x;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getY() {
		return this.y;
	}

	public int getLeft() {
		return this.x;
	}

	public int getTop() {
		return this.y;
	}

	public int getRight() {
		return this.x + this.width;
	}

	public int getBottom() {
		return this.y + this.height;
	}

	public int getWidth() {
		return this.width;
	}

	public int getHeight() {
		return this.height;
	}

	public void offset(int dx, int dy) {
		this.x += dx;
		this.y += dy;
		this.left += dx;
		this.right += dx;
		this.top += dy;
		this.bottom += dy;
	}

	public boolean isCollision(GameObject obj) {
		if (this.getLeft() > obj.getRight())
			return false;
		if (this.getRight() < obj.getLeft())
			return false;
		if (this.getTop() > obj.getBottom())
			return false;
		if (this.getBottom() < obj.getTop())
			return false;
		return true;
	}
	
	public BufferedImage getImg() {
		return this.img;
	}

	public abstract void update();

	public abstract boolean move();

	public void paint(Graphics g) {
		paintComponent(g);
		if (Global.IS_DEBUG) {
			g.drawImage(img, this.x, this.y, this.width, this.height, null);
			g.setColor(Color.RED);
			g.drawRect(this.x, this.y, this.width, this.height);
			g.setColor(Color.black);

		} else {
			g.drawImage(img, this.x, this.y, this.width, this.height, null);
		}

	}

	protected abstract void paintComponent(Graphics g);

}// end of class
