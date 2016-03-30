package aimage;

import ij.ImagePlus;

import java.util.ArrayList;

public class OCRImage {

    /**
     * Image content
     */
    private ImagePlus img;

    /**
     * Image label, given by file name.-> +, -, 0, 1 ... 0
     */
    private char label;

    /**
     * File path
     */
    private String path;

    /**
     * new label after analyse
     */
    private char decision;

    /**
     * features vector
     */
    private ArrayList<Double> vect;

    /**
     * pixels of the image
     */
    private byte[] pixels;

    /**
     * Constructor
     * @param img image's content
     * @param label image's label
     * @param path image's path
     */
    public OCRImage(final ImagePlus img, final char label, final String path) {
        this.img = img;
        this.label = label;
        this.path = path;
    }

    /**
     * Compute average of each horizontal line
     * @return a vector with all averages
     */
    public ArrayList<Double> getProfilH(){
        ArrayList<Double> profilH = new ArrayList<>();
        byte[] pixels = (byte[]) img.getProcessor().getPixels();

        for(int i=0; i < img.getHeight(); i++){
            double average = 0.0;
            for(int j = 0; j < img.getWidth(); j++){
                average += pixels[i * img.getWidth() + j] & 0xff;
            }
            profilH.add(average);
        }

        return profilH;
    }

    /**
     * Compute average of each vertical line
     * @return a vector with all averages
     */
    public ArrayList<Double> getProfilV(){
        ArrayList<Double> profilV = new ArrayList<>();
        byte[] pixels = (byte[]) img.getProcessor().getPixels();

        for(int i=0; i < img.getWidth(); i++){
            double average = 0.0;
            for(int j = 0; j < img.getHeight(); j++){
                average += pixels[i + j * img.getWidth()] & 0xff;
            }
            profilV.add(average);
        }

        return profilV;
    }

    /**
     * Set the vector of features with vertical profile and horizontal profile
     */
    public void setFeatureProfilHV(){
        vect = new ArrayList<>();

        vect.addAll(getProfilV());
        vect.addAll(getProfilH());
    }

    /**
     * Set the vector of features using isoperimeter report
     */
    public void rapportIso(){
        double perimeter = 0.0, surface = 0.0;
        refreshPixelsArray();
        binarize(220);

        // compute surface
        for (byte pixel : pixels) {
            if((pixel & 0xff) == 0){
                surface += 1.0;
            }
        }

        // compute perimeter
        for(int i=0; i<img.getWidth(); i++){
            for(int j=0; j<img.getHeight(); j++){
                // check if pixel is black
                if( getP(i,j) == 0){
                    // we look around it to find white pixel
                    boolean find = false;
                    for(int x=-1; x<=1 && !find; x++){
                        for(int y=-1; y<=1 && !find; y++){
                            if( i+x >= 0 && i+x < img.getWidth() && j+y >= 0 && j+y < img.getHeight()
                                    && getP(i+x, j+y) == 255){
                                find = true;
                                perimeter += 1.0;
                            }
                        }
                    }
                }
            }
        }

        double r = perimeter / (4.0 * Math.PI * surface);

        vect = new ArrayList<>();
        vect.add(r);
    }

    /**
     * binarize array of pixels
     * @param threshold
     */
    public void binarize(int threshold){
        for (int i=0; i < img.getWidth(); i++){
            for (int j = 0; j < img.getHeight(); j++) {
                int pix = getP(i,j);

                if(pix < threshold){
                    setP(i,j, 0);
                } else {
                    setP(i,j,255);
                }

            }
        }
    }

    /**
     * load the array of image's pixels
     */
    public void refreshPixelsArray(){
        pixels = (byte[]) img.getProcessor().getPixels();
    }

    /**
     * @param i column
     * @param j line
     * @return value of the pixel
     */
    public int getP(int i, int j){
        return pixels[j*img.getWidth() + i] & 0xff;
    }

    /**
     * @param i colum
     * @param j line
     * @param value new value of the pixel
     */
    public void setP(int i, int j, int value){
        pixels[j*img.getWidth() + i] = (byte) value;
    }

    /**
     * Compute average from img binarized
     * @return average
     */
    public double averageNdg(){
        // get pixels
        byte[] pixels = (byte[]) img.getProcessor().getPixels();

        double sum = 0;
        // sum of all pixels
        for (int i = 0; i < pixels.length; i++) {
            sum += pixels[i] & 0xff;
        }

        return sum/(double)pixels.length;
    }

    /**
     * Initialize or update the vector of features
     */
    public void setFeatureNdg(){
        vect = new ArrayList<>();
        vect.add(averageNdg());
    }

    /**
     * Anemic getter
     * @return features vector
     */
    public ArrayList<Double> getVect() {
        return vect;
    }

    /**
     * Anemic getter
     * @return label
     */
    public Character getLabel(){return label;}

    /**
     * Anemic getter
     * @return decision
     */
    public Character getDecision(){return decision;}

    /**
     * Anemic getter
     * @return getter
     */
    public String getPath() {
        return path;
    }

    /**
     * Anemic setter
     * @param decision char found decision
     */
    public void setDecision(char decision) {
        this.decision = decision;
    }
}
