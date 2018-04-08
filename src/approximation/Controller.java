package approximation;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart.Data;
import javafx.scene.chart.XYChart.Series;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;

import java.util.ArrayList;

/**
 * This class represents the "Controller" in the MVC architectural pattern.
 */

public class Controller {
    @FXML
    // Function graph
    private LineChart<Number, Number> Graph;
    @FXML
    // Graph of relative error
    private LineChart<Number, Number> ErrorGraph;
    @FXML
    // Graph of truncation error
    private LineChart<Integer, Double> TruncationGraph;
    @FXML
    // Initial value of x
    private TextField initial_x;
    @FXML
    // Initial value of y
    private TextField initial_y;
    @FXML
    // Value of X (the end of the interval)
    private TextField initial_X;
    @FXML
    // Value of N (number of steps)
    private TextField initial_N;
    @FXML
    // Value of initial number of steps for Truncation Error graph
    private TextField error_n;
    @FXML
    // Value of N for truncation error graph (the end of the interval)
    private TextField error_N;
    @FXML
    // CheckBox to print the exact solution
    private CheckBox isExact;
    @FXML
    // CheckBox to print the Euler solution and Error graph
    private CheckBox isEuler;
    @FXML
    // CheckBox to print the Improved Euler solution and Error graph
    private CheckBox isImprovedEuler;
    @FXML
    // CheckBox to print the Runge-Kutta solution and Error graph
    private CheckBox isRK;
    @FXML
    // Checkbox to print Euler truncation error
    private CheckBox eulerError;
    @FXML
    // Checkbox to print Improved Euler truncation error
    private CheckBox improvedError;
    @FXML
    // Checkbox to print Runge-Kutta truncation error
    private CheckBox rungeError;

    @FXML
    /*
     * This function handles the "Apply" button for the Solution tab
     */
    private void handleApplyAction(ActionEvent event) {
        // Parsing values of the text boxes
        double x0 = Double.parseDouble(initial_x.getText());
        double X = Double.parseDouble(initial_X.getText());
        double y0 = Double.parseDouble(initial_y.getText());
        double N = Double.parseDouble(initial_N.getText());
        double h = (X - x0)/N; // calculate h
        ArrayList<Double> xAxis = new ArrayList<>();
        double constant = ExactSolution.calculateConstant(x0, y0);
        ArrayList<Double> yExact = ExactSolution.calculateValues(x0, X, h, constant);
        // create the x axis for the solution graphs
        for (double x = x0; x <= X; x+=h) {
            xAxis.add(x);
        }
        // clear the previous graphs
        Graph.getData().clear();
        ErrorGraph.getData().clear();
        if (isExact.isSelected()) {
            Series exact = makeSolutionSeries(xAxis, yExact);
            exact.setName("Exact solution");
            Graph.getData().addAll(exact);
        }
        if (isEuler.isSelected()) {
            ArrayList<Double> yEuler = EulerMethod.calculateValues(x0, X, y0, h);
            Series euler = makeSolutionSeries(xAxis, yEuler);
            euler.setName("Euler");
            Graph.getData().addAll(euler);
            Series error = calculateError(yExact, yEuler, xAxis);
            error.setName("Euler");
            ErrorGraph.getData().addAll(error);
        }
        if (isImprovedEuler.isSelected()) {
            ArrayList<Double> yImproved = ImprovedEuler.calculateValues(x0, X, y0, h);
            Series improved = makeSolutionSeries(xAxis, yImproved);
            improved.setName("Improved Euler");
            Graph.getData().addAll(improved);
            Series error = calculateError(yExact, yImproved, xAxis);
            error.setName("Improved Euler");
            ErrorGraph.getData().addAll(error);
        }
        if (isRK.isSelected()) {
            ArrayList<Double> yRK = RungeKutta.calculateValues(x0, X, y0, h);
            Series RK = makeSolutionSeries(xAxis, yRK);
            RK.setName("Runge-Kutta");
            Graph.getData().addAll(RK);
            Series error = calculateError(yExact, yRK, xAxis);
            error.setName("Runge Kutta");
            ErrorGraph.getData().addAll(error);

        }
    }

    @FXML
    /*
    This function handles the "Apply" button at the Truncation tab.
     */
    private void handleErrorButton(ActionEvent event) {
        double x0 = Double.parseDouble(initial_x.getText());
        double X = Double.parseDouble(initial_X.getText());
        double y0 = Double.parseDouble(initial_y.getText());
        int n0 = Integer.parseInt(error_n.getText());
        int N = Integer.parseInt(error_N.getText());
        ArrayList<Integer> xAxis = new ArrayList<>();
        double c = ExactSolution.calculateConstant(x0, y0);
        TruncationGraph.getData().clear();
        for (int i = n0; i <= N; i++) {
            xAxis.add(i);
        }
        if (eulerError.isSelected()) {
            ArrayList<Double> yAxis = TruncationError.calculateError(n0, N, x0, X, y0, c, "euler");
            Series euler = makeTruncationSeries(xAxis, yAxis);
            euler.setName("Euler");
            TruncationGraph.getData().addAll(euler);
        }
        if (improvedError.isSelected()) {
            ArrayList<Double> yAxis = TruncationError.calculateError(n0, N, x0, X, y0, c, "improved");
            Series improved = makeTruncationSeries(xAxis, yAxis);
            improved.setName("Improved Euler");
            TruncationGraph.getData().addAll(improved);
        }

        if (rungeError.isSelected()) {
            ArrayList<Double> yAxis = TruncationError.calculateError(n0, N, x0, X, y0, c, "runge");
            Series runge = makeTruncationSeries(xAxis, yAxis);
            runge.setName("Runge-Kutta");
            TruncationGraph.getData().addAll(runge);
        }
    }

    /*
    A simple function to create Series for the LineChart
     */
    private Series makeSolutionSeries(ArrayList<Double> xAxis, ArrayList<Double> yAxis) {
        Series rungeKutta = new Series();
        for (int i = 0; i < xAxis.size(); i++) {
            rungeKutta.getData().add(new Data(xAxis.get(i), yAxis.get(i)));
        }
        return rungeKutta;
    }

    /*
    A simple function to create Series for the LineChart (Truncation Error)
    */
    private Series makeTruncationSeries(ArrayList<Integer> xAxis, ArrayList<Double> yAxis) {
        Series series = new Series();
        for (int i = 0; i < xAxis.size(); i++) {
            series.getData().add(new Data(xAxis.get(i), yAxis.get(i)));
        }
        return series;
    }

    /*
    A function to calculate errors with 2 given y-axis lists of values
     */
    private Series calculateError(ArrayList<Double> yExact, ArrayList<Double> yApprox, ArrayList<Double> xAxis) {
        Series error = new Series();
        for (int i = 0; i < xAxis.size(); i++) {
            error.getData().add(new Data(xAxis.get(i), Math.abs(yExact.get(i) - yApprox.get(i))));
        }
        return error;
    }
}
