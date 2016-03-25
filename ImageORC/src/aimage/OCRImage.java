package aimage;

import ij.ImagePlus;

import java.util.ArrayList;

public class OCRImage {

    /*
    contenu de l'image
     */
    private ImagePlus img;

    /*
    label de l'image (donné par le nom du fichier -> 0,1,.. ou 9)
     */
    private char label;

    /*
    path du fichier image
     */
    private String path;

    /*
    affectation du label après affectation
     */
    private char decision;

    /*
    vecteur de caractéristiques de l'image
     */
    private ArrayList<Double> vect;

    public OCRImage(ImagePlus img, char label, String path) {
        this.img = img;
        this.label = label;
        this.path = path;

        int val=(int)(Math.random()*10.0);
        String i = Integer.toString(val);
        char a = i.charAt(0);
        this.decision=(char)a;
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

    public Character getLabel(){return label;}

    public Character getDecision(){return decision;}

}
