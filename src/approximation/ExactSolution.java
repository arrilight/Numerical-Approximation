package approximation;

import java.util.ArrayList;

class ExactSolution {
    /**
     * Function to calculate the constant
     * @param x0 initial value of x
     * @param y0 initial value of y
     * @return calculated constant
     */
    static double calculateConstant(double x0, double y0) {
        return (-Math.pow(x0, 4) - y0*Math.pow(x0, 3))/(y0 - 2 * x0);

    }

    /**
     * This function is used to calculate the exact values of the function
     * @param x0 initial value of x
     * @param X the end of the interval of x
     * @param h a step-value
     * @param c constant
     * @return ArrayList of exact values
     */
    static ArrayList<Double> calculateValues(double x0, double X, double h, double c) {
        ArrayList<Double> result = new ArrayList<>();
        double x = x0;
        while (x <= X) {
            result.add(calculateValue(c, x));
            x += h;
        }
        return result;
    }

    /**
     * This function calculates the exact value of the function
     * @param c constant
     * @param x value of x
     * @return exact value of y
     */
    private static double calculateValue(double c, double x) {
        //return x * (-c * Math.pow(x, 3) + 2)/(c * Math.pow(x, 3) + 1);
        return 2*x - 3 * Math.pow(x, 4)/(Math.pow(x, 3) + c);
    }
}
