/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package inputSection;

import gameobj.Img;
import gameobj.ImgArr;
import java.awt.event.MouseEvent;
import util.CommandSolver;

/**
 *
 * @author user1
 */
public abstract class Button extends Component {

    public interface OnClickListener {

        public void onClick(Component c);
    }

    private OnClickListener listener;

    public Button(int left, int top, int right, int bottom) {
        super(left, top, right, bottom);
    }

    public Button(int left, int top, String imgPath) {
        super(left, top, left + 100, top + 100);
        Img temp = new Img(imgPath, left, top, true);
        this.setSize(temp.width(), temp.height());
        imgs.add(temp);

    }

    public void setImgArr(ImgArr imgArr) {
        this.imgs = imgArr;
    }

    public int addImg(String imgPath) {
        this.imgs.add(new Img(imgPath, x, y, true));
        return imgs.size() - 1;
    }

    @Override
    public void update(MouseEvent e, CommandSolver.MouseState state) {
        // 前一幀的狀態 => 按下
        int lastState = currentState;
        super.update(e, state);
        // 當前這一幀的狀態 => Hover

        if (lastState == Component.STATE_PRESSED
                && currentState == Component.STATE_HOVER) {
            if (listener != null) {
                listener.onClick(this);//自動呼叫此interface的方法
            }
        }
    }

    public void setListener(OnClickListener listener) {
        this.listener = listener;
    }
}
