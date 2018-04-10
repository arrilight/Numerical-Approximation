package approximation;

/*
 * Improved Euler approximation method.
 */
class ImprovedEuler extends ApproximationMethod {
    @Override
    double calculateValue(double lastY, double x, double h) {
        return lastY + h/2 * (func(x - h, lastY) + func(x, lastY + h * func(x - h, lastY)));
    }
}


