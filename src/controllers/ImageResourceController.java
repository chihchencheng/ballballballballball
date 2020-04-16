package controllers;

import java.awt.Image;
import static java.awt.Image.SCALE_DEFAULT;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import util.Global;

public class ImageResourceController {

    private static class KeyPair {// inner class

        private String path;
        private Image img;

        public KeyPair(String path, Image img) {
            this.path = path;
            this.img = img;
        }
    }// end of inner class

    // 
    private static ImageResourceController irc;

    // 
    private ArrayList<KeyPair> imgPairs;

    private ImageResourceController() {
        imgPairs = new ArrayList<>();
    }// for unable to create a new

    // the only method to get the only object
    public static ImageResourceController getInstance() {
        if (irc == null) {
            irc = new ImageResourceController();
        }
        return irc;
    }

    public Image tryGetImage(String path) {
        KeyPair pair = findKeyPair(path);
        if (pair == null) {
            return addImage(path);
        }
        return pair.img;
    }

    private Image addImage(String path) {
        try {
            if (Global.IS_DEBUG) {
                Global.log("load img from:" + path);
            }
            BufferedImage img = ImageIO.read(getClass().getResource(path));
            Image imgScale=img.getScaledInstance(
                    (int)(img.getWidth()*Global.ADJ),(int)(img.getHeight()*Global.ADJ),SCALE_DEFAULT);
            
            
            imgPairs.add(new KeyPair(path, imgScale));
            return imgScale;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void tryRemoveImg(String path) {
        for (int i = 0; i < imgPairs.size(); i++) {
            if (imgPairs.get(i).path.equals(path)) {
                imgPairs.remove(i);
                i--;
            }
        }
    }

    public String getPath(int index) {
        return imgPairs.get(index).path;
    }

    private KeyPair findKeyPair(String path) {
        for (int i = 0; i < imgPairs.size(); i++) {
            KeyPair pair = imgPairs.get(i);
            if (pair.path.equals(path)) {
                return pair;
            }
        }
        return null;
    }

    public int length() {
        return imgPairs.size();
    }

}
