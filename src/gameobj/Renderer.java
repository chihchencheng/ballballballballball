package gameobj;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import controllers.ImageResourceController;
import util.Delay;
import util.ImgPath;

public class Renderer {

//    private BufferedImage img;// ball icon
//    private int characterIndex;// 
//    
//    // 
//    private int currentStep;
//    private int stepIndex;
//    private int[] steps;
//    // 
//   
//    // 
//    private Delay delay;
//    // 
//    
//    public Renderer(int characterIndex, int[] steps, int delay){
//        img = ImageResourceController.getInstance().tryGetImage(ImgPath.BASEBALL);
//      
//        this.delay = new Delay(delay);
//        this.delay.start();
//    }
//    
//    public void update() {
//        if(delay.isTrig()){
//            stepIndex = (stepIndex + 1) % steps.length;
//            currentStep = steps[stepIndex];
//        }
//    }
//    
//    public void pause(){
//        this.delay.pause();
//    }
//    
//    public void start(){
//        this.delay.start();
//    }
//    
//    public void paint(Graphics g, int x, int y, int w, int h) {
//        g.drawImage(img, x, y, x + w, y + h,
//                (Global.UNIT_X * currentStep) + (characterIndex % 4) * (Global.UNIT_X * 3),
//                (Global.UNIT_Y * dir) + (characterIndex / 4) * (Global.UNIT_Y * 4),
//                (Global.UNIT_X + Global.UNIT_X * currentStep) + (characterIndex % 4) * (Global.UNIT_X * 3),
//                (Global.UNIT_Y + Global.UNIT_Y * dir) + (characterIndex / 4) * (Global.UNIT_Y * 4), null);
//    }
}
