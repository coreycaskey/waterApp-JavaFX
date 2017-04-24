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

import java.io.*;



public class EditQualityReportScreen extends StackPane {

	private ImageView rain = new ImageView(
        new Image("https://rain.today/Pix/rain.jpg"));

    private Button save = new Button("Save");
    private Button cancel = new Button("Cancel");
    private Button editPurityCondition = new Button("Confirm Purity Condition Change");

    private Label location = new Label("Edit the Quality Report Location");
    private Label purityCondition = new Label("Edit the Purity Condition");
    private Label virus = new Label("Edit the virus PPM");
    private Label contaminant = new Label("Edit the contaminant PPM");

    private TextField locationText = new TextField(QualityReportList.getCurrentQReport().getLocation());
    private TextField addressText = new TextField(QualityReportList.getCurrentQReport().getAddress());
    private TextField purityConditionText = new TextField(QualityReportList.getCurrentQReport().getPurityCondition().toString());
    private TextField virusText = new TextField(Double.toString(QualityReportList.getCurrentQReport().getVirusCount()));
    private TextField contaminantText = new TextField(Double.toString(QualityReportList.getCurrentQReport().getContaminantCount()));

    private ListView<PurityCondition> purityConditions = new ListView<>(
            FXCollections.observableArrayList(PurityCondition.SAFE, PurityCondition.TREATABLE,
                PurityCondition.UNSAFE));

	public EditQualityReportScreen(Stage primaryStage) {

        final BooleanProperty firstTime = new SimpleBooleanProperty(true); // Variable to store the focus on stage load

        // locationText.setPromptText("Enter the new location here.");

        rain.setFitHeight(800);
        rain.setFitWidth(800);

        HBox buttons = new HBox(5.5);
        buttons.getChildren().add(cancel);
        buttons.getChildren().add(save);
        buttons.setAlignment(Pos.CENTER);

        VBox options = new VBox(5.5);
        options.getChildren().add(location);
        options.getChildren().add(locationText);
        options.getChildren().add(addressText);
        options.getChildren().add(virus);
        options.getChildren().add(virusText);
        options.getChildren().add(contaminant);
        options.getChildren().add(contaminantText);
        options.getChildren().add(purityCondition);
        options.getChildren().add(purityConditionText);
        options.getChildren().add(this.getPurityConditionList());
        options.getChildren().add(editPurityCondition);
        options.setAlignment(Pos.CENTER);

        GridPane reportView = new GridPane();
        reportView.setAlignment(Pos.CENTER);
        reportView.setVgap(20);

        reportView.add(options, 0, 0);
        reportView.add(buttons, 0, 1);

        GridPane.setHalignment(options, HPos.CENTER);
        GridPane.setHalignment(buttons, HPos.CENTER);

        locationText.focusedProperty().addListener((observable,  oldValue,  newValue) -> {
            if(newValue && firstTime.get()){
                this.requestFocus(); // Delegate the focus to container
                firstTime.setValue(false); // Variable value changed for future references
            }
        });

		this.getChildren().add(rain);
		this.getChildren().add(reportView);

        editPurityCondition.setOnAction(e -> {
            purityConditionText.setText(this.getPurityConditionList().getSelectionModel().getSelectedItem().toString());
        });

        cancel.setOnAction(e -> {
            primaryStage.setScene(new Scene(new QualityReportTableScreen(primaryStage)));
            primaryStage.show();
        });

        save.setOnAction(e -> {
            QualityReport qReport = QualityReportList.getCurrentQReport(); // gets current quality report
            QualityReport editedQReport = null;
            if (this.getPurityConditionList().getSelectionModel().getSelectedItem() == null) {
                editedQReport = new QualityReport(qReport.getReportNumber(), locationText.getText().toString(),
                    addressText.getText().toString(), qReport.getDate(), this.getPurityConditionEnum(purityConditionText.getText().toString()),
                    Double.parseDouble(virusText.getText().toString()), Double.parseDouble(contaminantText.getText().toString()));
            } else {
                editedQReport = new QualityReport(qReport.getReportNumber(), locationText.getText().toString(),
                    addressText.getText().toString(), qReport.getDate(), this.getPurityConditionList().getSelectionModel().getSelectedItem(),
                    Double.parseDouble(virusText.getText().toString()), Double.parseDouble(contaminantText.getText().toString()));
            }
            Map<Integer, QualityReport> copyQReportList = QualityReportList.getQualityReportList();
            copyQReportList.remove(qReport.getReportNumber());
            QualityReportList.setQualityReportList(copyQReportList);
            QualityReportList.addQualityReport(editedQReport);

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
        });

		// primaryStage.setResizable(false);
  //       primaryStage.setScene(scene);
  //       primaryStage.show();
	}

        /**
    * return a ListView of WaterConditions representing the list of
    * available account types
    * @return listview of WaterConditions
    */
    public ListView<PurityCondition> getPurityConditionList() {
        purityConditions.setMaxHeight(120);
        purityConditions.setMaxWidth(300);
        purityConditions.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        return purityConditions;
    }

    public PurityCondition getPurityConditionEnum(String value) {
        if (value.equals("Safe")) {
            return PurityCondition.SAFE;
        } else if (value.equals("Treatable")) {
            return PurityCondition.TREATABLE;
        } else {
            return PurityCondition.UNSAFE;
        }
    }



}