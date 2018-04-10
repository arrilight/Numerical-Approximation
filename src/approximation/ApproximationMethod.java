package approximation;

import java.util.ArrayList;

/*
This class represents the right part of the initial equation (to use in approximation methods)
 */
abstract class ApproximationMethod {
    static double func(double x, double y) {
        return Math.pow(y, 2)/Math.pow(x, 2) - 2;
    }

    ArrayList<Double> calculateValues(double x0, double X, double y0, double h) {
        ArrayList<Double> result = new ArrayList<>();
        result.add(y0);
        double x = x0 + h;
        while (x <= X) {
            double lastY = result.get(result.size() - 1);
            result.add(calculateValue(lastY, x, h));
            x += h;
        }
        return result;
    }

    /*
    Abstract method to calculate the values. To be implemented in the children classes.
     */
    abstract double calculateValue(double lastY, double x, double h);
}

