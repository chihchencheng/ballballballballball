/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gameobj;

import inputSection.Button;
import java.util.ArrayList;

/**
 *
 * @author User
 */
public class ButtonArr extends ArrayList<Button> {

    public Button getButton(String imgPath) {
        for (int i = 0; i < this.size(); i++) {
            if (this.get(i).getImg(0).imgPath.equals(imgPath)) {
                return this.get(i);
            }
        }
        System.out.println("imgArrNotFound:" + imgPath);
        return null;
    }

    public int searchIndex(String imgPath) {
        for (int i = 0; i < this.size(); i++) {
            if (this.get(i).getImg(0).imgPath.equals(imgPath)) {
                return i;
            }
        }
        System.out.println("ButtonArrNotFound:" + imgPath);
        return this.size();
    }
}
