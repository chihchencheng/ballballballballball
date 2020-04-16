package gameobj;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import controllers.ImageResourceController;
import graph.Rect;
import java.awt.Image;
import util.Global;

public abstract class GameObject {

    protected Image img;
    private Rect collider;
    protected Rect rect;

    public GameObject(String imgPath, int x, int y, int width, int height, boolean isBindCollider) {
        this.img = ImageResourceController.getInstance().tryGetImage(imgPath);
        this.rect = Rect.genWithXY(x, y, width, height);
        if (isBindCollider) {
            this.img = ImageResourceController.getInstance().tryGetImage(imgPath);
            this.collider = this.rect;
        } else {
            this.collider = Rect.genWithXY(x, y, width, height);
        }
    }// end of constructor

    public GameObject(String imgPath, int x, int y, int width, int height,
            int colliderW, int colliderH, boolean isBindCollider) {
        this.img = ImageResourceController.getInstance().tryGetImage(imgPath);
        if (isBindCollider) {
            this.rect = this.collider = Rect.genWithXY(x, y, colliderW, colliderH);
        } else {
            this.rect = Rect.genWithXY(x, y, width, height);
            this.collider = Rect.genWithXY(x, y, colliderW, colliderH);
        }
    }// end of constructor
    
        public GameObject(String imgPath, int x, int y,boolean isBindCollider) {
        this.img = ImageResourceController.getInstance().tryGetImage(imgPath);        
        this.rect = Rect.genWithXY(x, y, img.getWidth(null), img.getHeight(null));
        if (isBindCollider) {
            this.img = ImageResourceController.getInstance().tryGetImage(imgPath);
            this.collider = this.rect;
        } else {
            this.collider = Rect.genWithXY(x, y, img.getWidth(null), img.getHeight(null));
        }
    }// end of constructor
    

    public GameObject(String imgPath) {
        this.img = ImageResourceController.getInstance().tryGetImage(imgPath);
    }

    public GameObject(int x, int y, int width, int height) {
        this.rect = Rect.genWithXY(x, y, width, height);
        this.collider = this.rect;
    }

    public int getX() {
        return this.rect.centerX();
    }

    public int getY() {
        return this.rect.centerY();
    }

    public int width() {
        return this.rect.width();
    }

    public int height() {
        return this.rect.height();
    }

    public void offset(int dx, int dy) {
        this.rect.offset(dx, dy);
        this.collider.offset(dx, dy);
    }

    public void setX(int x) {
        this.rect.offset(x - this.rect.centerX(), 0);
        this.collider.offset(x - this.collider.centerX(), 0);
    }

    public void setY(int y) {
        this.rect.offset(0, y - this.rect.centerY());
        this.collider.offset(0, y - this.collider.centerY());
    }

    public boolean isCollision(GameObject obj) {
        if (this.collider == null || obj.collider == null) {
            return false;
        }
        return Rect.intersects(this.collider, obj.collider);
    }

    public Rect collider() {
        return this.collider;
    }

    public Rect rect() {
        return this.rect;
    }

    public Image getImg() {
        return this.img;
    }

    public abstract void update();

    public abstract boolean move();

    //判斷標準：碰撞框
    public boolean isInside(int x, int y) {
        return x >= this.collider.left() && x <= this.collider().right() && 
                y >= this.collider().top() && y <= this.collider().bottom();
    }

    public void paint(Graphics g) {
        paintComponent(g);
        g.drawImage(img, this.rect.left(), this.rect.top(), this.rect.width(), this.rect.height(), null);
        if (Global.IS_DEBUG) {
            g.setColor(Color.RED);
            g.drawRect(this.rect.left(), this.rect.top(), this.rect.width(), this.rect.height());
            g.setColor(Color.BLUE);
            g.drawRect(this.collider.left(), this.collider.top(), this.collider.width(), this.collider.height());
            g.setColor(Color.BLACK);//回復畫筆顏色
        }
    }

    protected abstract void paintComponent(Graphics g);

}// end of class
