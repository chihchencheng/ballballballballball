package gameobj;

import java.awt.Color;
import java.awt.Graphics;
import java.io.IOException;

import javax.imageio.ImageIO;

import controllers.ImageResourceController;
import graph.Rect;
import java.awt.Image;
import java.util.ArrayList;
import util.Global;

public abstract class GameObject {

    protected KeyPair nowImg;
    protected Rect collider;
    protected Rect rect;
    protected String imgPath;
    protected ArrayList<KeyPair> imgs;

    private class KeyPair {

        private Image img;
        private String imgPath;

        public KeyPair(String imgPath) {
            this.imgPath = imgPath;
            this.img = ImageResourceController.getInstance().tryGetImage(imgPath);
        }

    }

    public GameObject(String imgPath, int x, int y, int width, int height, boolean isBindCollider) {
        this.imgPath = imgPath;
        this.nowImg = new KeyPair(imgPath);
        imgs = new ArrayList<>();
        imgs.add(new KeyPair(imgPath));
        this.rect = Rect.genWithXY(x, y, width, height);
        if (isBindCollider) {
            this.collider = this.rect;
        } else {
            this.collider = Rect.genWithXY(x, y, width, height);
        }
    }// end of constructor

    public GameObject(String imgPath, int x, int y, int width, int height,
            int colliderW, int colliderH, boolean isBindCollider) {
        this.imgPath = imgPath;
        this.nowImg = new KeyPair(imgPath);
        imgs = new ArrayList<>();
        imgs.add(new KeyPair(imgPath));

        if (isBindCollider) {
            this.rect = this.collider = Rect.genWithXY(x, y, colliderW, colliderH);
        } else {
            this.rect = Rect.genWithXY(x, y, width, height);
            this.collider = Rect.genWithXY(x, y, colliderW, colliderH);
        }
    }// end of constructor

    public GameObject(String imgPath, int x, int y, boolean isBindCollider) {
        this.imgPath = imgPath;
        this.nowImg = new KeyPair(imgPath);
        imgs = new ArrayList<>();
        imgs.add(new KeyPair(imgPath));
        this.rect = Rect.genWithXY(x, y, nowImg.img.getWidth(null), nowImg.img.getHeight(null));
        if (isBindCollider) {
            this.collider = this.rect;
        } else {
            this.collider = Rect.genWithXY(x, y, nowImg.img.getWidth(null), nowImg.img.getHeight(null));
        }
    }// end of constructor

    public GameObject(String imgPath) {
        this.imgPath = imgPath;
        this.nowImg = new KeyPair(imgPath);
        imgs = new ArrayList<>();
        imgs.add(new KeyPair(imgPath));
    }

    public GameObject(int x, int y, int width, int height) {
        this.rect = Rect.genWithXY(x, y, width, height);
        this.collider = this.rect;
    }

    public void importVarPic(String[] imgPaths) {
        for (int i = 0; i < imgPaths.length; i++) {
            imgs.add(new KeyPair(imgPaths[i]));
        }
    }

    public void importPic(String imgPath) {
        imgs.add(new KeyPair(imgPath));
        System.out.println(nowImg.imgPath+"追加圖片："+imgs.get(imgs.size()-1).imgPath);
    }

    public void switchNowImage(int i) {
        if (i < 0 || i >= imgs.size()) {
//            System.out.println("switchNowImage " + i + " 超出索引範圍");//test
        } else {
            nowImg = imgs.get(i);
        }
    }

    public void switchNowImage(String imgPath) {
        for (int i = 0; i < imgs.size(); i++) {
            if (imgPath.equals(imgs.get(i).imgPath)) {
                this.nowImg = imgs.get(i);
                break;
            }
        }
    }

    public void nextImg() {
        if ((getNowIndex() + 1) >= (imgs.size())) {
            nowImg = imgs.get(0);
        } else {
            nowImg = imgs.get(getNowIndex() + 1);
        }
        System.out.println("nextImg="+nowImg.imgPath);
    }

    public String getImgPath(int index) {
        if (index < 0 || index >= imgs.size()) {
            return null;
        }
        return imgs.get(index).imgPath;
    }

    public String getNowImgPath() {
        return nowImg.imgPath;
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
        return this.nowImg.img;
    }

    public int getNowIndex() {
        for (int i = 0; i < getImgCount(); i++) {
            if (nowImg.imgPath.equals(imgs.get(i).imgPath)) {
                return i;
            }
        }
        return imgs.size();
    }

    public int getImgCount() {
        return imgs.size();
    }

    public abstract void update();

    public abstract boolean move();

    //判斷標準：碰撞框
    public boolean isInside(int x, int y) {
        return x >= this.collider().left() && x <= this.collider().right()
                && y >= this.collider().top() && y <= this.collider().bottom();
    }

    public void paint(Graphics g) {
        paintComponent(g);
        g.drawImage(nowImg.img, this.rect.left(), this.rect.top(), this.rect.width(), this.rect.height(), null);
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
