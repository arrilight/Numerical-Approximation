package sample;

import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart.Data;
import javafx.scene.chart.XYChart.Series;
import javafx.scene.control.TextField;

import javafx.event.ActionEvent;
import java.util.ArrayList;

public class Controller {
    @FXML
    private LineChart<Number, Number> Graph;
    @FXML
    private TextField initial_x;
    @FXML
    private TextField initial_y;
    @FXML
    private TextField initial_X;
    @FXML
    private TextField initial_N;


    @FXML
    private void handleApplyAction(ActionEvent event) {
        Series exactSolution = new Series();
        exactSolution.setName("Exact");
        double x0 = Double.parseDouble(initial_x.getText());
        double X = Double.parseDouble(initial_X.getText());
        double y0 = Double.parseDouble(initial_y.getText());
        double N = Double.parseDouble(initial_N.getText());
        double h = (X - x0)/N;
        double constant = ExactSolution.calculateConstant(x0, y0);
        System.out.println(x0 + " " + X + " " + y0 + " " + N + " ");
        ArrayList<Double> exactValues = ExactSolution.calculateValues(x0, X, h, constant);
        double x = x0;
        for (double value:exactValues) {
            exactSolution.getData().add(new Data(x, value));
            x += h;
        }
        Graph.getData().clear();
        Graph.getData().addAll(exactSolution);
    }

}
