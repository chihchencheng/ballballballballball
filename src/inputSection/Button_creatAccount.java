/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package inputSection;

import gameobj.Img;
import java.awt.Color;
import java.awt.Graphics;

/**
 *
 * @author user1
 */
public class Button_creatAccount extends Button {

    public Button_creatAccount(int left, int top, int right, int bottom) {
        super(left, top, right, bottom);
    }

    public Button_creatAccount(int left, int top, String imgPath) {
        super(left, top, imgPath);
    }

    @Override
    public void def(Graphics g) {
        imgs.get(0).paint(g);
    }

    @Override
    public void hover(Graphics g) {
        if (imgs.size() >= 2) {
            imgs.get(1).paint(g);
        } else {
            imgs.get(0).paint(g);
        }
    }

    @Override
    public void pressed(Graphics g) {
        if (imgs.size() >= 3) {
            imgs.get(2).paint(g);
        } else if (imgs.size() >= 2) {
            imgs.get(1).paint(g);
        } else {
            imgs.get(0).paint(g);
        }
    }

}
