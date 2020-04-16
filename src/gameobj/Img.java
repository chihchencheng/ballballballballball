/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gameobj;

import java.awt.Graphics;

/**
 *
 * @author User
 */
public class Img extends GameObject {

    public Img(String imgPath, int x, int y, int width, int height, boolean isBindCollider) {
        super(imgPath, x, y, width, height, isBindCollider);
    }

    public Img(String imgPath, int x, int y, int width, int height,
            int colliderW, int colliderH, boolean isBindCollider) {
        super(imgPath, x, y, width, height, colliderW, colliderH, isBindCollider);
    }

    public Img(String imgPath) {
        super(imgPath);
    }

    public Img(int x, int y, int width, int height) {
        super(x, y, width, height);
    }

    public Img(String imgPath, int x, int y, boolean isBindCollider) {
        super(imgPath, x, y, isBindCollider);
    }

    @Override
    public void update() {
    }

    @Override
    public boolean move() {
        return false;
    }

    @Override
    public void paintComponent(Graphics g) {

    }

}
