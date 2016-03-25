package aimage;

import ij.process.ImageProcessor;

public class Image {

    byte[] pixels;
    int height, width;

    public Image(ImageProcessor ip){
        pixels = (byte[]) ip.getPixels();
        height = ip.getHeight();
        width = ip.getWidth();
    }

    public double meanImage(){
        double sum = 0.0;

        for (int i = 0; i < this.width; i++) {
            for (int j = 0; j < this.height; j++) {
                sum += (double)getP(i, j);
            }
        }
        return sum/((double)this.height*this.width);
    }

    public int getP(int i, int j){
        return pixels[j*width + i] & 0xff;
    }

    public void setP(int i, int j, int value){
        pixels[j*width + i] = (byte) value;
    }

}
