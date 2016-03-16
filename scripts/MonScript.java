import ij.*;
import ij.process.*;
import ij.plugin.filter.PlugInFilter;

public class MonScript implements PlugInFilter{

    byte[] pixels;
    int height, width;

    public void run(ImageProcessor ip){

        pixels = (byte[]) ip.getPixels();
        height = ip.getHeight();
        width = ip.getWidth();

        /*IJ.showMessage(Integer.toString(pixels.length)); // -> 379392
        IJ.showMessage(Integer.toString(getP(0,0)));
        IJ.showMessage(Integer.toString(getP(0,10)));
        IJ.showMessage(Integer.toString(getP(10,0)));
        IJ.showMessage(Integer.toString(getP(234,132)));*/

//        binarize(127);
        IJ.showMessage(Double.toString(meanImage()));
    }

    public int setup(String arg, ImagePlus imp){
        if (arg.equals("about")) {
            return DONE;
        }

        new ImageConverter(imp).convertToGray8();
        return DOES_8G + DOES_STACKS + SUPPORTS_MASKING;
    }

    public void binarize(int threshold){
        for (int i=0; i < width; i++){
            for (int j = 0; j < height; j++) {
                int pix = getP(i,j);

                if(pix < threshold){
                    setP(i,j, 0);
                } else {
                    setP(i,j,255);
                }

            }
        }
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