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
