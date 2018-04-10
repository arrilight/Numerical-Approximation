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
    // Functions graph
    private LineChart<Number, Number> Graph, ErrorGraph;
    @FXML
    // Graph of truncation error
    private LineChart<Integer, Double> TruncationGraph;
    @FXML
    // Initial values
    private TextField initial_x, initial_y, initial_X, initial_N;
    @FXML
    //Values for truncation errors
    private TextField error_n, error_N;
    @FXML
    // CheckBox to print approximate and exact solutions
    private CheckBox isExact, isEuler, isImprovedEuler, isRK;
    @FXML
    // Checkbox to print truncation errors
    private CheckBox eulerError, improvedError, rungeError;
    // Methods of approximation
    private EulerMethod eulerMethod = new EulerMethod();
    private ImprovedEuler improvedEuler = new ImprovedEuler();
    private RungeKutta rungeKutta = new RungeKutta();
    // Truncation error
    private TruncationError truncationError = new TruncationError();
    // Textbox values
    private double x0, X, y0, N, h, constant;
    private int n0, N_truncation;

    @FXML
    /*
     * This function handles the "Apply" button for the Solution tab
     */
    private void handleApplyAction(ActionEvent event) {
        parseSolutionValues();
        ArrayList<Object> xAxis = new ArrayList<>();
        ArrayList<Double> yExact = ExactSolution.calculateValues(x0, X, h, constant);
        // create the x axis for the solution graphs
        for (double x = x0; x <= X; x+=h) {
            xAxis.add(x);
        }
        clearSolutionGraphs(); //clearing previous graphs
        if (isExact.isSelected()) {
            printExactGraph(xAxis, yExact);
        }
        if (isEuler.isSelected()) {
            printApproximateGraph("Euler", xAxis, yExact);
        }
        if (isImprovedEuler.isSelected()) {
            printApproximateGraph("Improved Euler", xAxis, yExact);
        }
        if (isRK.isSelected()) {
            printApproximateGraph("Runge-Kutta", xAxis, yExact);
        }
    }

    @FXML
    /*
    This function handles the "Apply" button at the Truncation tab.
     */
    private void handleErrorButton(ActionEvent event) {
        parseTruncationValues();
        ArrayList<Object> xAxis = new ArrayList<>();
        clearTruncationGraph();
        //create x(N) axis for the graph
        for (int i = n0; i <= N_truncation; i++) {
            xAxis.add(i);
        }
        if (eulerError.isSelected()) {
            printTruncationGraph("Euler", xAxis);
        }
        if (improvedError.isSelected()) {
            printTruncationGraph("Improved Euler", xAxis);
        }

        if (rungeError.isSelected()) {
            printTruncationGraph("Runge-Kutta", xAxis);
        }
    }

    private void parseSolutionValues() {
        // Parsing values of the text boxes
        x0 = Double.parseDouble(initial_x.getText());
        X = Double.parseDouble(initial_X.getText());
        y0 = Double.parseDouble(initial_y.getText());
        N = Double.parseDouble(initial_N.getText());
        h = (X - x0)/N; // calculate h
        constant = ExactSolution.calculateConstant(x0, y0);
    }

    private void parseTruncationValues() {
        parseSolutionValues();
        n0 = Integer.parseInt(error_n.getText());
        N_truncation = Integer.parseInt(error_N.getText());
    }

    private void clearSolutionGraphs() {
        // clear the previous graphs
        Graph.getData().clear();
        ErrorGraph.getData().clear();
    }

    private void clearTruncationGraph() {
        // clear the previous graphs
        TruncationGraph.getData().clear();
    }

    /*
    Function that prints the exact solution
     */
    private void printExactGraph(ArrayList<Object> xAxis, ArrayList<Double> yExact) {
        Series exact = makeSeries(xAxis, yExact);
        exact.setName("Exact solution");
        Graph.getData().addAll(exact);
    }

    /*
    Function that prints approximation graph for a given method
     */
    private void printApproximateGraph(String method, ArrayList<Object> xAxis, ArrayList<Double> yExact) {
        ArrayList<Double> yApprox = new ArrayList<>();
        switch (method) {
            case "Euler":
                yApprox = eulerMethod.calculateValues(x0, X, y0, h);
                break;
            case "Improved Euler":
                yApprox = improvedEuler.calculateValues(x0, X, y0, h);
                break;
            case "Runge-Kutta":
                yApprox = rungeKutta.calculateValues(x0, X, y0, h);
                break;
        }
        Series series = makeSeries(xAxis, yApprox);
        series.setName(method);
        Graph.getData().addAll(series);
        Series error = calculateError(yExact, yApprox, xAxis);
        error.setName(method);
        ErrorGraph.getData().addAll(error);
    }

    /*
    Function that prints Truncation Error graph for a given method
     */
    private void printTruncationGraph(String method, ArrayList<Object> xAxis) {
        ArrayList<Double> yAxis = truncationError.calculateError(n0, N_truncation, x0, X, y0, constant, method);
        Series truncation = makeSeries(xAxis, yAxis);
        truncation.setName(method);
        TruncationGraph.getData().addAll(truncation);
    }
    /*
    A simple function to create Series for the LineChart
     */
    private Series makeSeries(ArrayList<Object> xAxis, ArrayList<Double> yAxis) {
        Series rungeKutta = new Series();
        for (int i = 0; i < xAxis.size(); i++) {
            rungeKutta.getData().add(new Data(xAxis.get(i), yAxis.get(i)));
        }
        return rungeKutta;
    }

    /*
    A function to calculate errors with 2 given y-axis lists of values
     */
    private Series calculateError(ArrayList<Double> yExact, ArrayList<Double> yApprox, ArrayList<Object> xAxis) {
        Series error = new Series();
        for (int i = 0; i < xAxis.size(); i++) {
            error.getData().add(new Data(xAxis.get(i), Math.abs(yExact.get(i) - yApprox.get(i))));
        }
        return error;
    }
}
