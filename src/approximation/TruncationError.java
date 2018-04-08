package approximation;

import java.util.ArrayList;

/*
This class is used to calculate truncation error for the given method
 */
class TruncationError {
    static ArrayList<Double> calculateError(int n0, int N, double x0, double X, double y0, double c, String method) {
        ArrayList<Double> maxError = new ArrayList<>();
        for (int i = n0; i <= N; i++) {
            double h = (X - x0)/i;
            ArrayList<Double> exact = ExactSolution.calculateValues(x0, X, h, c);
            ArrayList<Double> error = new ArrayList<>();
            if (method.equals("euler"))
                error = EulerMethod.calculateValues(x0, X, y0, h);
            else if (method.contains("improved"))
                error = ImprovedEuler.calculateValues(x0, X, y0, h);
            else if (method.contains("runge"))
                error = RungeKutta.calculateValues(x0, X, y0, h);
            maxError.add(findMax(exact, error));
        }
        return maxError;
    }

    private static double findMax(ArrayList<Double> exact, ArrayList<Double> approx) {
        double max = -1;
        for (int i = 0; i < exact.size(); i++) {
            if (Math.abs(exact.get(i) - approx.get(i)) > max)
                max = Math.abs(exact.get(i) - approx.get(i));
        }
        return max;
    }
}
