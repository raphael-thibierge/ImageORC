package aimage;

import java.io.IOException;

public class Main {

    public static void main(String[] args){

        OCREngine ocrEngine = new OCREngine();

        try {
            ocrEngine.logOCR("test.txt");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
