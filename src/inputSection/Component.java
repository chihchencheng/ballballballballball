/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package inputSection;

import gameobj.Img;
import gameobj.ImgArr;
import util.CommandSolver.MouseState;
import java.awt.Graphics;
import java.awt.event.MouseEvent;

/**
 *
 * @author user1
 */
public abstract class Component {       //一個可以偵測滑鼠互動狀況的元件(本例寫死成偵測滑鼠狀態)

    public static final int STATE_DEFAULT = 0;
    public static final int STATE_HOVER = 1;
    public static final int STATE_PRESSED = 2;

    protected int x;
    protected int y;
    protected int width;
    protected int height;
    protected ImgArr imgs = new ImgArr();

    protected int currentState;

    public Component(int left, int top, int right, int bottom) {
        this.x = left;
        this.y = top;
        this.width = right - left;
        this.height = bottom - top;
    }

    public void setSize(int width, int height) {
        this.width = width;
        this.height = height;
    }
    
    public Img getImg(int index){
        return imgs.get(index);
    }

    public void update(MouseEvent e, MouseState state) {
        currentState = STATE_DEFAULT;
        if (!withinRange(e.getX(), e.getY())) {
            return;
        }       //滑鼠未位於此物件時，state=default;

        if (state == MouseState.PRESSED || state == MouseState.DRAGGED) {
            currentState = STATE_PRESSED;
            return;
        }       //滑鼠位於此物件，且屬於pressed和dragged的狀態，state=點擊;

        currentState = STATE_HOVER;     //其他狀況代表滑鼠在此元件在範圍內游移
    }

    public boolean withinRange(int x, int y) {
        if (x < this.x || x > this.x + width) {
            return false;
        }
        if (y < this.y || y > this.y + height) {
            return false;
        }
        return true;
    }

    public void paint(Graphics g) {
        switch (currentState) {
            case STATE_PRESSED:
                pressed(g);
                break;
            case STATE_HOVER:
                hover(g);
                break;
            default:
                def(g);
                break;
        }
    }

    public void printImgArr() {
        System.out.println("[imgArrPaths]");
        for (int i = 0; i < imgs.size(); i++) {
            System.out.println("["+i+"]"+imgs.get(i).toString());
        }
    }

    public abstract void def(Graphics g);       //滑鼠屬於預設狀況 所需使用的方法

    public abstract void hover(Graphics g);     //

    public abstract void pressed(Graphics g);   //
}
