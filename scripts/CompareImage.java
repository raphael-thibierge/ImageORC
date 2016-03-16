import ij.IJ;
import ij.ImagePlus;
import ij.plugin.filter.PlugInFilter;
import ij.process.ImageConverter;
import ij.process.ImageProcessor;

import java.io.File;

public class CompareImage implements PlugInFilter {

    @Override
    public void run(ImageProcessor imageProcessor) {

        String closestImageName = "";
        String path = "/Users/alexis/Documents/S4_Image/semaine1/tp1/img/dataTP1S4";
        File[] files = listFiles(path);

        if (files != null) {
            double gap = Double.MAX_VALUE;
            for (int i = 0; i < files.length; i++) {
                if (!files[i].isHidden()) {
                    // Création d’une image temporaire
                    String filePath = files[i].getAbsolutePath();
                    ImagePlus tempImg = new ImagePlus(filePath);

                    new ImageConverter(tempImg).convertToGray8();

                    ImageProcessor ipTemp = tempImg.getProcessor();

                    // Calcul du niveau de gris moyen de l’image
                    double avgTemp = meanImage(ipTemp);

                    // Différence par rapport à l’image d’origine
                    double dif = Math.abs(meanImage(imageProcessor) - avgTemp);

                    if(dif < gap){
                        gap = dif;
                        closestImageName = filePath;
                    }
                }
            }

            IJ.showMessage("L’image la plus proche est " + closestImageName
                    + " avec une distance de " + gap + ".");
        }else{
            IJ.showMessage("Erreur : file not found");
        }
    }

    public File[] listFiles(String directoryPath){ File[] files = null;
        File directoryToScan = new File(directoryPath);
        files = directoryToScan.listFiles();
        return files;
    }

    public double meanImage(ImageProcessor ip){
        byte[] pixels = (byte[]) ip.getPixels();

        double sum = 0.0;

        for (int i = 0; i < ip.getWidth(); i++) {
            for (int j = 0; j < ip.getHeight(); j++) {
                sum += (double) pixels[j*ip.getWidth() + i];
            }
        }
        return sum/((double)ip.getWidth()*ip.getHeight());
    }

    @Override
    public int setup(String arg, ImagePlus imp){
        if (arg.equals("about")) {
            return DONE;
        }

        new ImageConverter(imp).convertToGray8();
        return DOES_8G + DOES_STACKS + SUPPORTS_MASKING;
    }
}
