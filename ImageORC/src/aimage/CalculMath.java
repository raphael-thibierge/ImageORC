package aimage;

import java.security.InvalidParameterException;
import java.util.ArrayList;

import static java.lang.Math.pow;
import static java.lang.Math.sqrt;

/**
 * Created by raphael on 25/03/2016.
 */
public class CalculMath {

    /**
     * Compute the Euclidian disatance between two vector
     * @param vect1 first vector
     * @param vect2 second vector
     * @return result
     */
    public static double distEucli(final ArrayList<Double> vect1, final ArrayList<Double> vect2){
        // check vectors size
        if (vect1.size() != vect2.size()){
            throw new InvalidParameterException("vect1 and vect2 don't have the same size");
        }

        double result = 0;
        // distance between all dimension of the vector
        for (int i = 0; i < vect1.size(); i++) {
            result += pow((vect1.get(i) - vect2.get(i)), 2);
        }

        return sqrt(result);
    }

    /**
     *
     * @param vect reference vector
     * @param tabVect array of vector to compare with
     * @param except index of vector from tabVect that will not be compare with vect
     * @return
     */
    public static int PPV(final ArrayList<Double> vect, final ArrayList<ArrayList <Double>> tabVect, final int except){
        // index to return
        int index = 0;
        double tmpDistance = Integer.MAX_VALUE;
        // compare all vectors
        for (int i = 0; i < tabVect.size(); i++) {
            // compute distance
            double distance = distEucli(tabVect.get(i), vect);
            // compare
            if (i != except && distance < tmpDistance){
                tmpDistance = distance;
                index = i;
            }
        }

        return index;
    }

}
