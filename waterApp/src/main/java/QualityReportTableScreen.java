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



public class QualityReportTableScreen extends StackPane {

	private ImageView rain = new ImageView(
        new Image("https://rain.today/Pix/rain.jpg"));

    private Button editQReport = new Button("Edit Quality Report");
    private Button createQReport = new Button("Create Quality Report");
    private Button cancel = new Button("Cancel");

    private ListView<String> qReports = new ListView<>(
            FXCollections.observableArrayList(QualityReportList.getLocationList()));

	public QualityReportTableScreen(Stage primaryStage) {

        final BooleanProperty firstTime = new SimpleBooleanProperty(true); // Variable to store the focus on stage load

        rain.setFitHeight(800);
        rain.setFitWidth(800);

        HBox editCreateButtons = new HBox(5.5);
        editCreateButtons.getChildren().add(editQReport);
        editCreateButtons.getChildren().add(createQReport);
        editCreateButtons.setAlignment(Pos.CENTER);

        GridPane qReportView = new GridPane();
        qReportView.setAlignment(Pos.CENTER);
        qReportView.setVgap(20);

        qReportView.add(this.getQualityReportList(), 0, 0);
        qReportView.add(editCreateButtons, 0, 1);
        qReportView.add(cancel, 0, 2);

        GridPane.setHalignment(this.getQualityReportList(), HPos.CENTER);
        GridPane.setHalignment(editCreateButtons, HPos.CENTER);
        GridPane.setHalignment(cancel, HPos.CENTER);

        qReports.focusedProperty().addListener((observable,  oldValue,  newValue) -> {
            if(newValue && firstTime.get()){
                this.requestFocus(); // Delegate the focus to container
                firstTime.setValue(false); // Variable value changed for future references
            }
        });

		this.getChildren().add(rain);
		this.getChildren().add(qReportView);

        cancel.setOnAction(e -> {
            primaryStage.setScene(new Scene(new AppScreen(primaryStage)));
            primaryStage.show();
        });

        createQReport.setOnAction(e -> {
        	primaryStage.setScene(new Scene(new CreateQualityReportScreen(primaryStage)));
        	primaryStage.show();
        });

        editQReport.setOnAction(e -> {
        	if (!this.checkFields()) {
        		Alert alert = new Alert(AlertType.WARNING);
                alert.setTitle("Warning Dialog");
                alert.setHeaderText("This action cannot be performed!");
                alert.setContentText("You must select a quality report to edit. "
                    + " Press OK to continue");
                alert.showAndWait();
        	} else {
        		QualityReportList.setCurrentQReport(QualityReportList.getQReportFromLocation(
        			this.getQualityReportList().getSelectionModel().getSelectedItem()));
        		primaryStage.setScene(new Scene(new EditQualityReportScreen(primaryStage)));
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
    public ListView<String> getQualityReportList() {
        qReports.setMaxHeight(120);
        qReports.setMaxWidth(300);
        qReports.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        return qReports;
    }

    public boolean checkFields() {
        if (this.getQualityReportList().getSelectionModel().getSelectedItem() == null) {
            return false;
        }
        return true;
    }

}