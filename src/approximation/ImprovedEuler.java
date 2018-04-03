package approximation;

import java.util.ArrayList;

class ImprovedEuler {
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

    private static double func(double x, double y) {
        return Math.pow(y, 2)/Math.pow(x, 2) - 2;
    }
}
