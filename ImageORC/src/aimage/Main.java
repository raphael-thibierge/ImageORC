package aimage;

import java.io.IOException;

public class Main {

    public static void main(String[] args){

        String alexisPath = "/Users/alexis/ImageORC/ImageORC/images";
        String raphaelPath = "/Users/raphael/Documents/DUT-Info/S4/S4_Image/projet/ImageORC/images";
        String pathDefault = alexisPath;
        if (args.length > 0 && args[0].equals("1")){
            pathDefault = raphaelPath;
        }

        OCREngine ocrEngine = new OCREngine(pathDefault);

        try {
            ocrEngine.makeDecisionOnImageList();
            ocrEngine.logOCR("out/test.txt");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    }
