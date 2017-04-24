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



public class ReportTableScreen extends StackPane {

	private ImageView rain = new ImageView(
        new Image("https://rain.today/Pix/rain.jpg"));

    private Button editReport = new Button("Edit Report");
    private Button createReport = new Button("Create Report");
    private Button cancel = new Button("Cancel");

    private ListView<String> reports = new ListView<>(
            FXCollections.observableArrayList(ReportList.getLocationList()));

	public ReportTableScreen(Stage primaryStage) {

        final BooleanProperty firstTime = new SimpleBooleanProperty(true); // Variable to store the focus on stage load

        rain.setFitHeight(800);
        rain.setFitWidth(800);

        HBox editCreateButtons = new HBox(5.5);
        editCreateButtons.getChildren().add(editReport);
        editCreateButtons.getChildren().add(createReport);
        editCreateButtons.setAlignment(Pos.CENTER);

        GridPane reportView = new GridPane();
        reportView.setAlignment(Pos.CENTER);
        reportView.setVgap(20);

        reportView.add(this.getReportList(), 0, 0);
        reportView.add(editCreateButtons, 0, 1);
        reportView.add(cancel, 0, 2);

        GridPane.setHalignment(this.getReportList(), HPos.CENTER);
        GridPane.setHalignment(editCreateButtons, HPos.CENTER);
        GridPane.setHalignment(cancel, HPos.CENTER);

        reports.focusedProperty().addListener((observable,  oldValue,  newValue) -> {
            if(newValue && firstTime.get()){
                this.requestFocus(); // Delegate the focus to container
                firstTime.setValue(false); // Variable value changed for future references
            }
        });

		this.getChildren().add(rain);
		this.getChildren().add(reportView);

        cancel.setOnAction(e -> {
            primaryStage.setScene(new Scene(new AppScreen(primaryStage)));
            primaryStage.show();
        });

        createReport.setOnAction(e -> {
        	primaryStage.setScene(new Scene(new CreateReportScreen(primaryStage)));
        	primaryStage.show();
        });

        editReport.setOnAction(e -> {
        	if (!this.checkFields()) {
        		Alert alert = new Alert(AlertType.WARNING);
                alert.setTitle("Warning Dialog");
                alert.setHeaderText("This action cannot be performed!");
                alert.setContentText("You must select a report to edit. "
                    + " Press OK to continue");
                alert.showAndWait();
        	} else {
        		ReportList.setCurrentReport(ReportList.getReportFromLocation(
        			this.getReportList().getSelectionModel().getSelectedItem()));
        		primaryStage.setScene(new Scene(new EditReportScreen(primaryStage)));
        		primaryStage.show();
        	}
        });

		// primaryStage.setResizable(false);
  //       primaryStage.setScene(scene);
  //       primaryStage.show();
	}

        /**
    * return a ListView of AccountTypes representing the list of
    * available account types
    * @return listview of AccountType
    */
    public ListView<String> getReportList() {
        reports.setMaxHeight(120);
        reports.setMaxWidth(300);
        reports.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        return reports;
    }

    public boolean checkFields() {
        if (this.getReportList().getSelectionModel().getSelectedItem() == null) {
            return false;
        }
        return true;
    }

}