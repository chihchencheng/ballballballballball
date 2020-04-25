package scenes;

import controllers.ImageResourceController;
import java.awt.Graphics;

import controllers.SceneController;
import gameobj.ImgArr;
import util.CommandSolver.*;

public abstract class Scene {

    protected SceneController sceneController;
    protected ImgArr imgs = new ImgArr();

    public Scene(SceneController sceneController) {
        this.sceneController = sceneController;
    }

    public void releaseImg() {
        for (int i = 0; i < imgs.size(); i++) {
            for (int j=0;j<imgs.get(i).getImgCount();j++) {
                ImageResourceController.getInstance().tryRemoveImg(imgs.get(i).getImgPath(j));
            }
        }
    }

    public abstract void sceneBegin();

    public abstract void sceneUpdate();

    public abstract void sceneEnd();

    public abstract void paint(Graphics g);

    // 
    public abstract KeyListener getKeyListener();

    public abstract MouseCommandListener getMouseListener();
}
