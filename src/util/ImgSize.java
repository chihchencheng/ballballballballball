/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

/**
 *
 * @author User
 */
public class ImgSize {

    private int weight;
    private int height;
    private float ratio;

    public ImgSize(int weight, int height) {
        this.weight = weight;
        this.height = height;
        this.ratio = 1;
    }

    public ImgSize(int weight, int height, float ratio) {
        this.weight = (int) (ratio * weight);
        this.height = (int) (ratio * height);
    }

    public void setRatio(float ratio) {
        this.ratio = ratio;
        this.weight = (int) (ratio * weight);
        this.height = (int) (ratio * height);
    }

    public int getWeight() {
        return weight;
    }

    public int getHeight() {
        return height;
    }

    public float getRatio() {
        return ratio;
    }

}
