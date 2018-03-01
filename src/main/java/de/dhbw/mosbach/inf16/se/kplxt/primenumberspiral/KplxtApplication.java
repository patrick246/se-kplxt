package de.dhbw.mosbach.inf16.se.kplxt.primenumberspiral;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.ScatterChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.stream.Collectors;

public class KplxtApplication extends Application {

    private PrimeNumberSpiralGenerator generator = new PrimeNumberSpiralGenerator();
    private Slider slider = createSlider();
    private ObservableList<XYChart.Series<Number, Number>> seriesList = FXCollections.observableArrayList();
    private ScatterChart<Number, Number> scatterChart = new ScatterChart<>(new NumberAxis(), new NumberAxis());
    private LineChart<Number, Number> lineChart = new LineChart<>(new NumberAxis(), new NumberAxis());

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        XYChart.Series<Number, Number> series = new XYChart.Series<>();
        series.getData().add(new XYChart.Data<>(0, 0));
        series.getData().add(new XYChart.Data<>(0, 5));
        series.getData().add(new XYChart.Data<>(5, 5));
        series.getData().add(new XYChart.Data<>(5, 0));

        lineChart.getData().add(series);
        lineChart.setPrefHeight(99999);
        lineChart.setPrefWidth(99999);
        lineChart.setAxisSortingPolicy(LineChart.SortingPolicy.NONE);
        lineChart.setCreateSymbols(false);

        stage.setTitle("Prime Number Spiral");
        scatterChart.setPrefHeight(99999);
        VBox vBox = new VBox(8, new HBox(slider, createGenerateButton()), lineChart);
        Scene scene = new Scene(vBox, 500, 500);
        scene.getStylesheets().add(this.getClass().getResource("Chart.css").toExternalForm());
        stage.setScene(scene);
        stage.show();

        Platform.runLater(() -> generate(10_000));
    }

    private Slider createSlider() {
        Slider slider = new Slider();
        slider.setMin(1);
        slider.setMax(7);
        slider.setValue(4);
        slider.setMinorTickCount(1);
        slider.setMajorTickUnit(1);
        slider.setBlockIncrement(1);
        slider.setShowTickLabels(true);
        slider.setShowTickMarks(true);
        slider.valueProperty().addListener((observableValue, oldVal, newVal) -> slider.setValue(newVal.intValue()));

        return slider;
    }

    private Button createGenerateButton() {
        Button b = new Button();
        b.setText("Generate");
        b.setOnAction((e) -> {
            int limit = ((Double)Math.pow(10, slider.getValue())).intValue();
            Platform.runLater(() -> generate(limit));
        });
        return b;
    }

    private void generate(int limit) {
        XYChart.Series<Number, Number> series = new XYChart.Series<>(
                FXCollections.observableList(
                        generator.generate(limit)
                                .stream()
                                .map(point -> new XYChart.Data<Number, Number>(point.getX(), point.getY()))
                                .collect(Collectors.toList())
                )
        );
        lineChart.setData(FXCollections.singletonObservableList(series));
    }
}
