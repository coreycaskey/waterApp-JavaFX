/*
 Created by Corey Caskey on 4/24/2017
 */

import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.stage.Stage;
import javafx.scene.layout.StackPane;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.geometry.Pos;
import javafx.scene.chart.Axis;



public class LineChartScene extends StackPane {

    private Button cancel = new Button("Cancel");
    private static String currentChart = "";
    private static String chartData = "";

    public LineChartScene(Stage primaryStage) {
        //defining the axes
        final NumberAxis xAxis = new NumberAxis();
        final NumberAxis yAxis = new NumberAxis();
        xAxis.setLabel("Number of Month");

        //creating the chart
        final LineChart<Number,Number> lineChart =
                new LineChart<Number,Number>(xAxis,yAxis);

        lineChart.setTitle(this.getCurrentChart() + " " + this.getData() + " Purity Trends");
        //defining a series
        XYChart.Series series = new XYChart.Series();
        series.setName(this.getData() + " Trends");

        if (this.getData().equals("Virus")) {
            for (QualityReport qr: QualityReportList.getQualityReportListValues()) {
                if (qr.getLocation().equals(this.getCurrentChart())) {
                    String month = qr.getDate().substring(5,7);
                    series.getData().add(new XYChart.Data(Integer.parseInt(month), qr.getVirusCount()));
                }
            }
        } else if (this.getData().equals("Contaminant")) {
            for (QualityReport qr: QualityReportList.getQualityReportListValues()) {
                if (qr.getLocation().equals(this.getCurrentChart())) {
                    String month = qr.getDate().substring(5,7);
                    series.getData().add(new XYChart.Data(Integer.parseInt(month), qr.getVirusCount()));
                }
            }
        }

        lineChart.getData().add(series);

        VBox chartBox = new VBox(15);
        chartBox.getChildren().add(lineChart);
        chartBox.getChildren().add(cancel);
        chartBox.setAlignment(Pos.CENTER);

        this.getChildren().add(chartBox);

        cancel.setOnAction(e -> {
            primaryStage.setScene(new Scene(new HistoricalReportScreen(primaryStage)));
            primaryStage.show();
        });

    }

    public static void setCurrentChart(String chart) {
        currentChart = chart;
    }

    public static String getCurrentChart() {
        return currentChart;
    }

    public static void setData(String data) {
        chartData = data;
    }

    public static String getData() {
        return chartData;
    }

}
