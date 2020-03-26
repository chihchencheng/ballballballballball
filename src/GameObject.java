import java.awt.Graphics;
import java.awt.image.BufferedImage;

public abstract class GameObject {
	protected BufferedImage img;
	protected int x;
	protected int y;
	protected int width;
	protected int height;
	protected int left;
	protected int right;
	protected int top;
	protected int bottom;
	
	public GameObject(String imgPath,int width, int height) {
		this.width = width;
		this.height = height;
		
	}
	
	public void paint(Graphics g) {
		g.drawImage(img,x,y,width,height,null);
	}
	

}
