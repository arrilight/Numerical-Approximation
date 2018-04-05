package approximation;

import java.util.ArrayList;

public class ExactSolution {

    public static double calculateConstant(double x0, double y0) {
        return ((2 * x0 - y0)/(Math.pow(x0, 3) * (x0 + y0)));

    }

    public static ArrayList<Double> calculateValues(double x0, double X, double h, double c) {
        ArrayList<Double> result = new ArrayList<>();
        double x = x0;
        while (x <= X) {
            result.add(calculateValue(c, x));
            x += h;
        }
        return result;
    }

    private static double calculateValue(double c, double x) {
        return x * (-c * Math.pow(x, 3) + 2)/(c * Math.pow(x, 3) + 1);
    }
}
