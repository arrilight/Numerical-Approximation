package approximation;

/*
 * Euler approximation method.
 */
class EulerMethod extends ApproximationMethod {
    @Override
    double calculateValue(double lastY, double x, double h) {
        return lastY + h * func(x - h, lastY);
    }
}


