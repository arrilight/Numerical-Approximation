package approximation;

/*
This class represents the right part of the initial equation (to use in approximation methods)
 */
class Equation {
    protected static double func(double x, double y) {
        return Math.pow(y, 2)/Math.pow(x, 2) - 2;
    }
}
