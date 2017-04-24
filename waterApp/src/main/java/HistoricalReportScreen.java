import java.util.Map;
import java.util.HashMap;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.scene.layout.StackPane;
import javafx.scene.image.ImageView;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.geometry.Pos;
import javafx.geometry.HPos;
import javafx.scene.control.Label;
import javafx.scene.text.*;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.scene.layout.VBox;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

import javafx.scene.layout.HBox;


import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.collections.FXCollections;

import java.util.ArrayList;

public class HistoricalReportScreen extends StackPane {

	private ImageView rain = new ImageView(
        new Image("https://rain.today/Pix/rain.jpg"));

    private Button displayGraph = new Button("Display Trend Graph");
    private Button cancel = new Button("Cancel");
    private Button updateDates = new Button("Update Dates");

    private Label pickLocation = new Label("Pick a Location");
    private Label pickYear = new Label("Pick a Year");
    private Label pickData = new Label("Pick Graph Data");

    private ListView<String> locations = new ListView<>(
            FXCollections.observableArrayList(QualityReportList.getUniqueLocationList()));

    private ListView<String> dates = new ListView<>(
        FXCollections.observableArrayList("2017"));

    private ListView<String> data = new ListView<>(
        FXCollections.observableArrayList("Virus", "Contaminant"));

	public HistoricalReportScreen(Stage primaryStage) {

        final BooleanProperty firstTime = new SimpleBooleanProperty(true); // Variable to store the focus on stage load

        rain.setFitHeight(800);
        rain.setFitWidth(800);

        HBox buttons = new HBox(5.5);
        buttons.getChildren().add(displayGraph);
        buttons.getChildren().add(cancel);
        buttons.setAlignment(Pos.CENTER);

        GridPane historicalReport = new GridPane();
        historicalReport.setAlignment(Pos.CENTER);
        historicalReport.setVgap(20);

        historicalReport.add(pickLocation, 0, 0);
        historicalReport.add(this.getLocationsList(), 0, 1);
        historicalReport.add(pickData, 0, 2);
        historicalReport.add(this.getDataList(), 0, 3);
        historicalReport.add(pickYear, 0, 4);
        historicalReport.add(this.getDatesList(), 0, 5);
        historicalReport.add(buttons, 0, 6);

        GridPane.setHalignment(this.getLocationsList(), HPos.CENTER);
        GridPane.setHalignment(this.getDatesList(), HPos.CENTER);
        GridPane.setHalignment(buttons, HPos.CENTER);

        locations.focusedProperty().addListener((observable,  oldValue,  newValue) -> {
            if(newValue && firstTime.get()){
                this.requestFocus(); // Delegate the focus to container
                firstTime.setValue(false); // Variable value changed for future references
            }
        });

		this.getChildren().add(rain);
		this.getChildren().add(historicalReport);

        cancel.setOnAction(e -> {
            primaryStage.setScene(new Scene(new AppScreen(primaryStage)));
            primaryStage.show();
        });

        displayGraph.setOnAction(e -> {
            if (this.checkFields()) {
                LineChartScene.setCurrentChart(this.getLocationsList().getSelectionModel().getSelectedItem().toString());
                LineChartScene.setData(this.getDataList().getSelectionModel().getSelectedItem().toString());
                primaryStage.setScene(new Scene(new LineChartScene(primaryStage)));
                primaryStage.show();
            } else {
                Alert alert = new Alert(AlertType.WARNING);
                alert.setTitle("Warning Dialog");
                alert.setHeaderText("This action cannot be performed!");
                alert.setContentText("You must select an option for each field."
                    + " Press OK to continue");
                alert.showAndWait();
            }
        });

        // editQReport.setOnAction(e -> {
        // 	if (!this.checkFields()) {
        // 		Alert alert = new Alert(AlertType.WARNING);
        //         alert.setTitle("Warning Dialog");
        //         alert.setHeaderText("This action cannot be performed!");
        //         alert.setContentText("You must select a quality report to edit. "
        //             + " Press OK to continue");
        //         alert.showAndWait();
        // 	} else {
        // 		QualityReportList.setCurrentQReport(QualityReportList.getQReportFromLocation(
        // 			this.getQualityReportList().getSelectionModel().getSelectedItem()));
        // 		primaryStage.setScene(new Scene(new EditQualityReportScreen(primaryStage)));
        // 		primaryStage.show();
        // 	}
        // });

		// primaryStage.setResizable(false);
  //       primaryStage.setScene(scene);
  //       primaryStage.show();
	}

    /**
    * return a ListView of AccountTypes representing the list of
    * available account types
    * @return listview of AccountType
    */
    public ListView<String> getLocationsList() {
        locations.setMaxHeight(120);
        locations.setMaxWidth(300);
        locations.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        return locations;
    }

    /**
    * return a ListView of AccountTypes representing the list of
    * available account types
    * @return listview of AccountType
    */
    public ListView<String> getDatesList() {
        dates.setMaxHeight(120);
        dates.setMaxWidth(300);
        dates.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        return dates;
    }


    /**
    * return a ListView of AccountTypes representing the list of
    * available account types
    * @return listview of AccountType
    */
    public ListView<String> getDataList() {
        data.setMaxHeight(120);
        data.setMaxWidth(300);
        data.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        return data;
    }

    // public boolean checkFields() {
    //     if (this.getQualityReportList().getSelectionModel().getSelectedItem() == null) {
    //         return false;
    //     }
    //     return true;
    // }

    public boolean checkLocation() {
        if (this.getLocationsList().getSelectionModel().getSelectedItem() == null) {
            return false;
        }
        return true;
    }

    public boolean checkFields() {
        if (this.getLocationsList().getSelectionModel().getSelectedItem() == null
            || this.getDataList().getSelectionModel().getSelectedItem() == null
            || this.getDatesList().getSelectionModel().getSelectedItem() == null) {
            return false;
        }
        return true;
    }

}