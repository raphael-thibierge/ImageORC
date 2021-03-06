package aimage;

import ij.ImagePlus;
import ij.process.ImageConverter;
import ij.process.ImageProcessor;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class OCREngine {

    private ArrayList<OCRImage> listeImgReference;
    private ArrayList<OCRImage> listeImg;

    private String path;

    public OCREngine(String path){
        this.path = path;
        setImgReferenceDataBase();
        listeImg = createListeImage(path);
    }

    /**
     * Write results of the recognition
     * @param pathOut path of the result file
     * @throws IOException
     */
    public void logOCR(String pathOut) throws IOException {
        ArrayList<Character> labels = getLabels();
        FileWriter file = new FileWriter(pathOut);

        file.write("Test effectué le " + new Date().toString() + "\r\n\r\n   ");
        for (Character c : labels){
            file.write("   " + c);
        }

        HashMap<Character, HashMap<Character,Integer>> confusion = new HashMap<Character, HashMap<Character, Integer>>();

        // Initialization of confusion matrix
        for(Character character : labels){
            HashMap<Character, Integer> list = new HashMap<Character, Integer>();
            for (Character c : labels){
                list.put(c, 0);
            }
            confusion.put(character, list);
        }
        file.write("\r\n\r\n");

        // we fill the confusion matrix ( compare label and decision )
        int nb_true = 0;
        for(OCRImage ocrImage : listeImg){
            HashMap<Character, Integer> results = confusion.get(ocrImage.getLabel());
            results.put(ocrImage.getDecision(), results.get(ocrImage.getDecision()) + 1);
            if(ocrImage.getLabel() == ocrImage.getDecision()){
                nb_true++;
            }
        }

        // we write the results of the recognition
        for(Character c : labels){
            file.write(c + " :   ");
            HashMap<Character, Integer> values = confusion.get(c);
            for (Character c2 : labels){
                file.write(values.get(c2) + "   ");
            }
            file.write("\r\n");
        }
        file.write("\r\n");

        // we compute recognition 
        double taux = ((double)nb_true / listeImg.size()) * 100;
        file.write("Le taux de reconnaissance est de : " + taux + "%");

        file.close();
    }

    public ArrayList<Character> getLabels(){
        ArrayList<Character> labels = new ArrayList<Character>();
        for(OCRImage ocr : listeImg){
            if(!labels.contains(ocr.getLabel())){
                labels.add(ocr.getLabel());
            }
        }
        return labels;
    }

    /**
     * Load images in directory path
     * @param path directory path where images are stored
     */
    public ArrayList<OCRImage> createListeImage(final String path) {
        ArrayList<OCRImage> imageList = new ArrayList<>();

        File[] files = listFiles(path);
        if (files.length != 0)
        {
            for (File file : files) {
                if(!file.getName().equals(".DS_Store")) {
                    ImagePlus tempImg = new ImagePlus(file.getAbsolutePath());
                    new ImageConverter(tempImg).convertToGray8();
                    resize(tempImg, 20, 20);
                    OCRImage image = new OCRImage(tempImg,
                            file.getName().substring(0, 1).charAt(0),
                            file.getAbsolutePath());
                    imageList.add(image);
                }
            }
        }
        return imageList;
    }

    /**
     * Resize an image
     * @param img image to resize
     * @param larg new width
     * @param haut new heigth
     */
    public static void resize(ImagePlus img, int larg, int haut) {
        ImageProcessor ip2 = img.getProcessor();
        ip2.setInterpolate(true);
        ip2 = ip2.resize(larg, haut);
        img.setProcessor(null, ip2);
    }

    /**
     * Get file list contained in directory path
     * @param directoryPath path where files are stored
     * @return files list
     */
    private static File[] listFiles(final String directoryPath){
        File[] files = null;
        File directoryToScan = new File(directoryPath);
        files = directoryToScan.listFiles();
        return files;
    }

    /**
     * Set feature caracteristic vector for all images from listeImage
     */
    public void setFeatureNdgVect() {
        for (int i = 0; i < listeImg.size(); i++) {
            listeImg.get(i).setFeatureNdg();
        }
    }

    /**
     * Set feature caracteristic vector for all images from listeImage (Profile method)
     */
    public void setFeatureHVVect(){
        for(OCRImage image : listeImg){
            image.setFeatureProfilHV();
        }
    }

    /**
     * Set feature caracteristic vector for all images from listeImage (Isoperimeter method)
     */
    public void setIsoperimeter(){
        for (OCRImage image : listeImg){
            image.rapportIso();
        }
    }

    /**
     * Set feature caracteristic vector for all reference images
     */
    private void setImgReferenceDataBase(){
        listeImgReference = createListeImage(path);
        for (OCRImage image : listeImgReference){
            //image.setFeatureNdg();
            //image.setFeatureProfilHV();
            //image.rapportIso();
            image.zonning();
        }
    }

    private void setZonning(){
        for (OCRImage image : listeImg){
            image.zonning();
        }
    }

    /**
     * Apply a decision to the images
     */
    public void makeDecisionOnImageList(){
        //setFeatureNdgVect();
        //setFeatureHVVect();
        //setIsoperimeter();

        setZonning();


        ArrayList<ArrayList<Double>> ref = new ArrayList<>();
        for (OCRImage referenceImage : listeImgReference) {
            ref.add(referenceImage.getVect());
        }

        for (OCRImage image : listeImg) {
            int except = computeExcept(image);
            int decision = CalculMath.PPV(image.getVect(), ref, except);
            image.setDecision(listeImgReference.get(decision).getLabel());
        }
    }

    /**
     * Find index of the same file in the references
     * @param image image to find
     * @return index of image
     */
    private int computeExcept(OCRImage image){
        for (int i = 0; i < listeImgReference.size(); i++) {
            if (listeImgReference.get(i).getPath().equals(image.getPath())){
                return i;
            }
        }
        return -1;
    }

}
