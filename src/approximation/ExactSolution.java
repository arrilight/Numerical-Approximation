package approximation;

import java.util.ArrayList;

public class ExactSolution {

    public static double calculateConstant(double x0, double y0) {
        return (Math.log((Math.abs(y0 - 2*x0))/(Math.pow(x0, 3) * (y0 + x0))))/3;

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
        return -(x * Math.exp(3 * c) * Math.pow(x, 3) + 2)/(Math.exp(3 * c) * Math.pow(x, 3) - 1);
    }
}
