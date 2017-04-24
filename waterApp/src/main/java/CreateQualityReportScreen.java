/*
 Created by Corey Caskey on 4/24/2017
 */

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

import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import java.io.*;



public class CreateQualityReportScreen extends StackPane {

	private ImageView rain = new ImageView(
        new Image("https://rain.today/Pix/rain.jpg"));

    private Button createQReport = new Button("Create");
    private Button cancel = new Button("Cancel");

    private Label location = new Label("Pick a location and address");
    private Label virusPPM = new Label("Virus PPM");
    private Label contaminantPPM = new Label("Contaminant PPM");
    private Label purityCondition = new Label("Pick a Purity Condition");

    private TextField locationText = new TextField();
    private TextField addressText = new TextField();
    private TextField virusText = new TextField();
    private TextField contaminantText = new TextField();

    private ListView<PurityCondition> purityConditions = new ListView<>(
            FXCollections.observableArrayList(PurityCondition.SAFE, PurityCondition.TREATABLE,
                PurityCondition.UNSAFE));

    private DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
    private Date date = new Date();

	public CreateQualityReportScreen(Stage primaryStage) {

        final BooleanProperty firstTime = new SimpleBooleanProperty(true); // Variable to store the focus on stage load

        locationText.setPromptText("Enter your location here.");
        addressText.setPromptText("Enter the locations' address.");
        virusText.setPromptText("Enter the virus PPM");
        contaminantText.setPromptText("Enter the contaminant PPM");

        rain.setFitHeight(800);
        rain.setFitWidth(800);

        HBox buttons = new HBox(5.5);
        buttons.getChildren().add(cancel);
        buttons.getChildren().add(createQReport);
        buttons.setAlignment(Pos.CENTER);

        VBox options = new VBox(5.5);
        options.getChildren().add(location);
        options.getChildren().add(locationText);
        options.getChildren().add(addressText);
        options.getChildren().add(virusPPM);
        options.getChildren().add(virusText);
        options.getChildren().add(contaminantPPM);
        options.getChildren().add(contaminantText);
        options.getChildren().add(purityCondition);
        options.getChildren().add(this.getPurityConditionList());
        options.setAlignment(Pos.CENTER);

        GridPane qReportView = new GridPane();
        qReportView.setAlignment(Pos.CENTER);
        qReportView.setVgap(20);

        qReportView.add(options, 0, 0);
        qReportView.add(buttons, 0, 1);

        GridPane.setHalignment(options, HPos.CENTER);
        GridPane.setHalignment(buttons, HPos.CENTER);

        locationText.focusedProperty().addListener((observable,  oldValue,  newValue) -> {
            if(newValue && firstTime.get()){
                this.requestFocus(); // Delegate the focus to container
                firstTime.setValue(false); // Variable value changed for future references
            }
        });

		this.getChildren().add(rain);
		this.getChildren().add(qReportView);

        cancel.setOnAction(e -> {
            primaryStage.setScene(new Scene(new QualityReportTableScreen(primaryStage)));
            primaryStage.show();
        });

        createQReport.setOnAction(e -> {
            if (!this.checkFields()) {
                Alert alert = new Alert(AlertType.WARNING);
                alert.setTitle("Warning Dialog");
                alert.setHeaderText("This action cannot be performed!");
                alert.setContentText("You need to fill out all fields. "
                    + " Press OK to continue");
                alert.showAndWait();
            } else {
                QualityReport newQReport = new QualityReport(QualityReportList.getQualityReportList().size() + 1,
                    locationText.getText().toString(), addressText.getText().toString(), dateFormat.format(date),
                    this.getPurityConditionList().getSelectionModel().getSelectedItem(),
                    Double.parseDouble(virusText.getText().toString()), Double.parseDouble(contaminantText.getText().toString()));
                QualityReportList.addQualityReport(newQReport);

                // The name of the file to open.
                String fileName = "qualityReports.txt";

                try {
                    // Assume default encoding.
                    FileWriter fileWriter =
                        new FileWriter(fileName);

                    // Always wrap FileWriter in BufferedWriter.
                    BufferedWriter bufferedWriter =
                        new BufferedWriter(fileWriter);

                    // Note that write() does not automatically
                    // append a newline character.
                    bufferedWriter.flush();

                    for (QualityReport aQReport: QualityReportList.getQualityReportListValues()) {
                        bufferedWriter.write(aQReport.getReportNumber() + "\n");
                        bufferedWriter.write(aQReport.getLocation() + "\n");
                        bufferedWriter.write(aQReport.getAddress() + "\n");
                        bufferedWriter.write(aQReport.getDate() + "\n");
                        bufferedWriter.write(aQReport.getPurityCondition().toString() + "\n");
                        bufferedWriter.write(aQReport.getVirusCount() + "\n");
                        bufferedWriter.write(aQReport.getContaminantCount() + "\n");
                        bufferedWriter.write("-----------------------" + "\n");
                    }

                    // Always close files.
                    bufferedWriter.close();
                }
                catch(IOException ex) {
                    System.out.println(
                        "Error writing to file '"
                        + fileName + "'");
                    // Or we could just do this:
                    // ex.printStackTrace();
                }

                primaryStage.setScene(new Scene(new QualityReportTableScreen(primaryStage)));
                primaryStage.show();
            }
        });

		// primaryStage.setResizable(false);
  //       primaryStage.setScene(scene);
  //       primaryStage.show();
	}

        /**
    * return a ListView of WaterTypes representing the list of
    * available account types
    * @return listview of WaterType
    */
    public ListView<PurityCondition> getPurityConditionList() {
        purityConditions.setMaxHeight(120);
        purityConditions.setMaxWidth(300);
        purityConditions.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        return purityConditions;
    }

    public boolean checkFields() {
        if (locationText.getText().toString().equals("")
            || virusText.getText().toString().equals("") || contaminantText.getText().toString().equals("")
            || this.getPurityConditionList().getSelectionModel().getSelectedItem() == null) {
            return false;
        }
        return true;
    }

}
