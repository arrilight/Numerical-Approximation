package approximation;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart.Data;
import javafx.scene.chart.XYChart.Series;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;

import java.util.ArrayList;

public class Controller {
    @FXML
    private LineChart<Number, Number> Graph;
    @FXML
    private LineChart<Number, Number> ErrorGraph;
    @FXML
    private TextField initial_x;
    @FXML
    private TextField initial_y;
    @FXML
    private TextField initial_X;
    @FXML
    private TextField initial_N;
    @FXML
    private CheckBox isExact;
    @FXML
    private CheckBox isEuler;
    @FXML
    private CheckBox isImprovedEuler;
    @FXML
    private CheckBox isRK;



    @FXML
    private void handleApplyAction(ActionEvent event) {
        double x0 = Double.parseDouble(initial_x.getText());
        double X = Double.parseDouble(initial_X.getText());
        double y0 = Double.parseDouble(initial_y.getText());
        double N = Double.parseDouble(initial_N.getText());
        double h = (X - x0)/N;
        ArrayList<Double> xAxis = new ArrayList<>();
        double constant = ExactSolution.calculateConstant(x0, y0);
        ArrayList<Double> yExact = ExactSolution.calculateValues(x0, X, h, 1);
        for (double x = x0; x <= X; x+=h) {
            xAxis.add(x);
        }
        Graph.getData().clear();
        ErrorGraph.getData().clear();
        if (isExact.isSelected()) {
            Graph.getData().addAll(exactSeries(xAxis, yExact));
        }
        if (isEuler.isSelected()) {
            ArrayList<Double> yEuler = EulerMethod.calculateValues(x0, X, y0, h);
            Graph.getData().addAll(eulerSeries(xAxis, yEuler));
            Series error = calculateError(yExact, yEuler, xAxis);
            error.setName("Euler");
            ErrorGraph.getData().addAll(error);
        }
        if (isImprovedEuler.isSelected()) {
            ArrayList<Double> yImproved = ImprovedEuler.calculateValues(x0, X, y0, h);
            Graph.getData().addAll(eulerSeries(xAxis, yImproved));
            Series error = calculateError(yExact, yImproved, xAxis);
            error.setName("Improved Euler");
            ErrorGraph.getData().addAll(error);
        }

    }

    private Series exactSeries(ArrayList<Double> xAxis, ArrayList<Double> yAxis) {
        Series exactSolution = new Series();
        exactSolution.setName("Exact");
        for (int i = 0; i < xAxis.size(); i++) {
            exactSolution.getData().add(new Data(xAxis.get(i), yAxis.get(i)));
            System.out.println(xAxis.get(i)+ " " +yAxis.get(i));
        }
        return exactSolution;
    }

    private Series eulerSeries(ArrayList<Double> xAxis, ArrayList<Double> yAxis) {
        Series eulerSolution = new Series();
        eulerSolution.setName("Euler");
        for (int i = 0; i < xAxis.size(); i++) {
            eulerSolution.getData().add(new Data(xAxis.get(i), yAxis.get(i)));
            System.out.println(xAxis.get(i)+ " " +yAxis.get(i));
        }
        return eulerSolution;
    }

    private Series improvedEulerSeries(ArrayList<Double> xAxis, ArrayList<Double> yAxis) {
        Series improvedEuler = new Series();
        improvedEuler.setName("Improved Euler");
        for (int i = 0; i < xAxis.size(); i++) {
            improvedEuler.getData().add(new Data(xAxis.get(i), yAxis.get(i)));
            System.out.println(xAxis.get(i)+ " " +yAxis.get(i));
        }
        return improvedEuler;
    }

    private Series calculateError(ArrayList<Double> yExact, ArrayList<Double> yApprox, ArrayList<Double> xAxis) {
        Series error = new Series();
        for (int i = 0; i < xAxis.size(); i++) {
            error.getData().add(new Data(xAxis.get(i), Math.abs(yExact.get(i) - yApprox.get(i))));
        }
        return error;
    }
}
