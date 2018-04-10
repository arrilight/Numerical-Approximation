package approximation;

import java.util.ArrayList;

/*
This class represents the right part of the initial equation (to use in approximation methods)
 */
abstract class ApproximationMethod {
    protected static double func(double x, double y) {
        return Math.pow(y, 2)/Math.pow(x, 2) - 2;
    }

    abstract ArrayList<Double> calculateValues(double x0, double X, double y0, double h);
}
