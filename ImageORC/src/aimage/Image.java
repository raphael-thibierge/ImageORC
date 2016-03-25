package aimage;

import ij.ImagePlus;
import ij.process.ImageProcessor;

public class Image   {

    byte[] pixels;
    int height, width;

    public Image( final ImagePlus image){
        ImageProcessor ip = image.getProcessor();
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

    public int getP(final int i, final int j){
        return pixels[j*width + i] & 0xff;
    }

    public void setP(final int i, final int j, final int value){
        pixels[j*width + i] = (byte) value;
    }

}
