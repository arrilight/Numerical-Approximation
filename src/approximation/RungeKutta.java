package approximation;

import java.util.ArrayList;

public class RungeKutta extends Equation{
    static ArrayList<Double> calculateValues(double x0, double X, double y0, double h) {
        ArrayList<Double> result = new ArrayList<>();
        result.add(y0);
        double x = x0 + h;
        while (x <= X) {
            double lastY = result.get(result.size() - 1);
            double k1 = func(x - h, lastY);
            double k2 = func(x - h/2, lastY + h/2 * k1);
            double k3 = func(x - h/2, lastY + h/2 * k2);
            double k4 = func(x, lastY + h * k3);
            result.add(lastY + h/6 * (k1 + 2 * k2 + 2 * k3 + k4));
            x += h;
        }
        return result;
    }
}
