package aimage;

import ij.ImagePlus;
import ij.process.ImageConverter;

import java.io.File;
import java.util.ArrayList;

public class OCREngine {

    private ArrayList<OCRImage> listeImg;

    private static String AlexisPath = "/Users/alexis/ImageORC/ImageORC/images";
    //private static String RaphaelPath = "";

    public OCREngine(){
        createListeImage(AlexisPath);
        //createListeImage(RaphaelPath);
    }

    public void createListeImage(String path) {
        listeImg = new ArrayList<OCRImage>();

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

    public static File[] listFiles(String directoryPath){
        File[] files = null;
        File directoryToScan = new File(directoryPath);
        files = directoryToScan.listFiles();
        return files;
    }

}
