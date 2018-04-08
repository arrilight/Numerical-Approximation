package approximation;

import java.util.ArrayList;

/*
 * Improved Euler approximation method.
 */
class ImprovedEuler extends Equation {
    static ArrayList<Double> calculateValues(double x0, double X, double y0, double h) {
        ArrayList<Double> result = new ArrayList<>();
        result.add(y0);
        double x = x0 + h;
        while (x <= X) {
            double lastY = result.get(result.size() - 1);
            result.add(lastY + h/2 * (func(x - h, lastY) + func(x, lastY + h * func(x - h, lastY))));
            x += h;
        }
        return result;
    }


}
