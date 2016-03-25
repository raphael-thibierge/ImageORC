package aimage;

import ij.ImagePlus;
import ij.process.ImageConverter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class OCREngine {

    private ArrayList<OCRImage> listeImg;

    private String path;

    public OCREngine(String path){
        this.path = path;
        createListeImage(path);
    }

    public void logOCR(String pathOut) throws IOException {
        FileWriter file = new FileWriter(pathOut);
        file.write("Test effectué le " + new Date().toString() + "\r\n\r\n   ");

        HashMap<Character, HashMap<Character,Integer>> confusion = new HashMap<Character, HashMap<Character, Integer>>();
        ArrayList<Character> labels = getLabels();

        for(Character character : labels){
            HashMap<Character, Integer> list = new HashMap<Character, Integer>();
            for (Character c : labels){
                list.put(c, 0);
            }
            confusion.put(character, list);

            file.write("   " + character);
        }
        file.write("\r\n\r\n");

        int nb_true = 0;
        for(OCRImage ocrImage : listeImg){
            HashMap<Character, Integer> results = confusion.get(ocrImage.getLabel());
            results.put(ocrImage.getDecision(), results.get(ocrImage.getDecision()) + 1);
            if(ocrImage.getLabel() == ocrImage.getDecision()){
                nb_true++;
            }
        }

        for(Character c : labels){
            file.write(c + " :   ");
            HashMap<Character, Integer> values = confusion.get(c);
            for (Character c2 : labels){
                file.write(values.get(c2) + "   ");
            }
            file.write("\r\n");
        }
        file.write("\r\n");

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
    public void createListeImage(final String path) {
        listeImg = new ArrayList<>();

        File[] files = listFiles(path);
        if (files.length != 0)
        {
            for (File file : files) {
                ImagePlus tempImg = new ImagePlus(file.getAbsolutePath());
                new ImageConverter(tempImg).convertToGray8();
                listeImg.add(new OCRImage(tempImg,
                        file.getName().substring(0, 1).charAt(0),
                        file.getAbsolutePath()));
            }
        }
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
    public void setFeatureNdgVect()
    {
        for (int i = 0; i < listeImg.size(); i++) {
            listeImg.get(i).setFeatureNdg();
        }
    }

}
