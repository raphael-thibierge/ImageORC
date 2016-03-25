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
    public static double distEucli(ArrayList<Double> vect1, ArrayList<Double> vect2){
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

}
