/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package inputSection;

import java.awt.Color;
import java.awt.Graphics;

/**
 *
 * @author user1
 */
public class AEditText extends EditText {

    public AEditText(int left, int top, int right, int bottom) {
        super(left, top, right, bottom);
    }

    @Override
    public void focus(Graphics g) {
        g.setColor(Color.green);
        g.drawRect(x, y, width, height);
    }

    @Override
    public void unfocus(Graphics g) {
        g.setColor(Color.yellow);
        g.drawRect(x, y, width, height);
    }
}
