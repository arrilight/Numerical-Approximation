package approximation;

/*
 * Runge-Kutta approximation method.
 */
class RungeKutta extends ApproximationMethod {
    @Override
    double calculateValue(double lastY, double x, double h) {
        double k1 = func(x - h, lastY);
        double k2 = func(x - h/2, lastY + h/2 * k1);
        double k3 = func(x - h/2, lastY + h/2 * k2);
        double k4 = func(x, lastY + h * k3);
        return (lastY + h/6 * (k1 + 2 * k2 + 2 * k3 + k4));
    }
}
