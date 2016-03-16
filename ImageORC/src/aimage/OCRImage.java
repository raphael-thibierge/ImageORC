package aimage;

import ij.ImagePlus;

import java.util.ArrayList;

public class OCRImage {

    private ImagePlus img; //contenu de l’image
    private char label; //correspond au label de l’image (donne par le nom du fichier --> 0,1,... ou 9)
    private String path; //path du fichier image
    private char decision; //affectation du label apres classification
    private ArrayList<Double> vect; //Vecteur de caracteristiques de l’image

    public OCRImage(ImagePlus img, char label, String path) {
        this.img = img;
        this.label = label;
        this.path = path;
    }

    public void setImg(ImagePlus img){
        this.img = img;
    }

    public ImagePlus getImg(){
        return img;
    }

    public void setVect(int i, double val){
        this.vect.set(i,val);
    }

    public Double getVect(int i){
        return vect.get(i);
    }

}
