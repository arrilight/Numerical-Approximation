package sample;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart.Data;
import javafx.scene.chart.XYChart.Series;

import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {
    @FXML
    private LineChart<Number, Number> MyChart;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        double x0 = -Math.PI, X = Math.PI;
        int N = 50;
        double x[] = new double[N];
        double y_sin[] = new double[N];
        double y_cos[] = new double[N];

        double h = (X - x0) / N;

        x[0]=x0;
        for (int i = 1; i < N; i++) {
            x[i] = x[i-1] + h;
        }

        for (int i = 0; i < N; i++) {
            y_sin[i] = Math.sin(x[i]);
            y_cos[i] = Math.cos(x[i]);
        }

        Series sin_ser = new Series();
        sin_ser.setName("Sin");
        Series cos_ser = new Series();
        cos_ser.setName("Cos");

        for (int i=0; i<x.length; i++)
        {
            sin_ser.getData().add(new Data(x[i], y_sin[i]));
            cos_ser.getData().add(new Data(x[i], y_cos[i]));
        }

        MyChart.getData().addAll(sin_ser, cos_ser);

    }

}
