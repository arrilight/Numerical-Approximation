package sample;

import java.util.ArrayList;

public class EulerMethod {
    public static ArrayList<Double> calculateValues(double x0, double X, double y0, double h) {
        ArrayList<Double> result = new ArrayList<>();
        result.add(y0);
        double x = x0 + h;
        while (x <= X) {
            double lastY = result.get(result.size() - 1);
            result.add(lastY + h * (Math.pow(lastY, 2)/Math.pow(x - h, 2) - 2));
            x += h;
        }
        return result;
    }
}
