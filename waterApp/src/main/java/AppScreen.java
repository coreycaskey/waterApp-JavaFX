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

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

import java.io.*;


public class AppScreen extends StackPane {

	ImageView rain = new ImageView(
        new Image("https://rain.today/Pix/rain.jpg"));

    Button edit = new Button("Edit Profile");
    Button reportList = new Button("See Report List");
    Button qReportList = new Button("See Quality Report List");
    Button historyReport = new Button("View History Report");
    Button logout = new Button("Logout");

	public AppScreen(Stage primaryStage) {
        rain.setFitHeight(800);
        rain.setFitWidth(800);

        VBox appButtons = new VBox(15);
        appButtons.getChildren().add(edit);
        appButtons.getChildren().add(reportList);
        appButtons.getChildren().add(qReportList);
        appButtons.getChildren().add(historyReport);
        appButtons.getChildren().add(logout);

        appButtons.setAlignment(Pos.CENTER);

		this.getChildren().add(rain);
		this.getChildren().add(appButtons);

        logout.setOnAction(e -> {
            UserList.setCurrentUser(null);
            primaryStage.setScene(new Scene(new WelcomeScreen(primaryStage)));
            primaryStage.show();
        });

        edit.setOnAction(e -> {
            primaryStage.setScene(new Scene(new ProfileScreen(primaryStage)));
            primaryStage.show();
        });

        reportList.setOnAction(e -> {

            // The name of the file to open.
            String fileName = "reports.txt";

            // This will reference one line at a time
            String line = null;

            String reportNum = "";
            String location = "";
            String address = "";
            String waterCondition = "";
            String waterType = "";

            int lineNumber = 1;

            try {
                // FileReader reads text files in the default encoding.
                FileReader fileReader =
                    new FileReader(fileName);

                // Always wrap FileReader in BufferedReader.
                BufferedReader bufferedReader =
                    new BufferedReader(fileReader);

                while((line = bufferedReader.readLine()) != null) {
                    if (lineNumber == 1) {
                        lineNumber++;
                        reportNum = line;
                    } else if (lineNumber == 2) {
                        lineNumber++;
                        location = line;
                    } else if (lineNumber == 3) {
                        lineNumber++;
                        address = line;
                    } else if (lineNumber == 4) {
                        lineNumber++;
                        waterType = line;
                    } else if (lineNumber == 5) {
                        lineNumber++;
                        waterCondition = line;
                    } else {
                        lineNumber = 1;
                        Report newReport = new Report(Integer.valueOf(reportNum), location, address, WaterType.valueOf(waterType.toUpperCase()), WaterCondition.valueOf(waterCondition.toUpperCase()));
                        ReportList.addReport(newReport);
                        reportNum = "";
                        location = "";
                        address = "";
                        waterCondition = "";
                        waterType = "";
                    }
                }

                // Always close files.
                bufferedReader.close();
            }
            catch(FileNotFoundException ex) {
                System.out.println(
                    "Unable to open file '" +
                    fileName + "'");
            }
            catch(IOException ex) {
                System.out.println(
                    "Error reading file '"
                    + fileName + "'");
                // Or we could just do this:
                // ex.printStackTrace();
            }
            primaryStage.setScene(new Scene(new ReportTableScreen(primaryStage)));
            primaryStage.show();
        });

        qReportList.setOnAction(e -> {

            if (UserList.getCurrentUser().getAccountType().toString().equals("Worker")
                || UserList.getCurrentUser().getAccountType().toString().equals("Manager")) {

                String fileName = "qualityReports.txt";

                // This will reference one line at a time
                String line = null;

                String reportNum = "";
                String location = "";
                String address = "";
                String date = "";
                String purityCondition = "";
                String virus = "";
                String contaminant = "";

                int lineNumber = 1;

                try {
                    // FileReader reads text files in the default encoding.
                    FileReader fileReader =
                        new FileReader(fileName);

                    // Always wrap FileReader in BufferedReader.
                    BufferedReader bufferedReader =
                        new BufferedReader(fileReader);

                    while((line = bufferedReader.readLine()) != null) {
                        if (lineNumber == 1) {
                            lineNumber++;
                            reportNum = line;
                        } else if (lineNumber == 2) {
                            lineNumber++;
                            location = line;
                        } else if (lineNumber == 3) {
                            lineNumber++;
                            address = line;
                        } else if (lineNumber == 4) {
                            lineNumber++;
                            date = line;
                        } else if (lineNumber == 5) {
                            lineNumber++;
                            purityCondition = line;
                        } else if (lineNumber == 6) {
                            lineNumber++;
                            virus = line;
                        } else if (lineNumber == 7) {
                            lineNumber++;
                            contaminant = line;
                        } else {
                            lineNumber = 1;
                            QualityReport newQualityReport = new QualityReport(Integer.valueOf(reportNum), location, address, date,
                                PurityCondition.valueOf(purityCondition.toUpperCase()), Double.valueOf(virus), Double.valueOf(contaminant));
                            QualityReportList.addQualityReport(newQualityReport);
                            reportNum = "";
                            location = "";
                            address = "";
                            date = "";
                            purityCondition = "";
                            virus = "";
                            contaminant = "";
                        }
                    }

                    // Always close files.
                    bufferedReader.close();
                }
                catch(FileNotFoundException ex) {
                    System.out.println(
                        "Unable to open file '" +
                        fileName + "'");
                }
                catch(IOException ex) {
                    System.out.println(
                        "Error reading file '"
                        + fileName + "'");
                    // Or we could just do this:
                    // ex.printStackTrace();
                }

                primaryStage.setScene(new Scene(new QualityReportTableScreen(primaryStage)));
                primaryStage.show();
            } else {
                Alert alert = new Alert(AlertType.WARNING);
                alert.setTitle("Warning Dialog");
                alert.setHeaderText("This action cannot be performed!");
                alert.setContentText("Must be a Manager or Worker."
                    + " Press OK to continue");
                alert.showAndWait();
            }




        });

        historyReport.setOnAction(e -> {

            if (UserList.getCurrentUser().getAccountType().toString().equals("Manager")) {

                String fileName = "qualityReports.txt";

                // This will reference one line at a time
                String line = null;

                String reportNum = "";
                String location = "";
                String address = "";
                String date = "";
                String purityCondition = "";
                String virus = "";
                String contaminant = "";

                int lineNumber = 1;

                try {
                    // FileReader reads text files in the default encoding.
                    FileReader fileReader =
                        new FileReader(fileName);

                    // Always wrap FileReader in BufferedReader.
                    BufferedReader bufferedReader =
                        new BufferedReader(fileReader);

                    while((line = bufferedReader.readLine()) != null) {
                        if (lineNumber == 1) {
                            lineNumber++;
                            reportNum = line;
                        } else if (lineNumber == 2) {
                            lineNumber++;
                            location = line;
                        } else if (lineNumber == 3) {
                            lineNumber++;
                            address = line;
                        } else if (lineNumber == 4) {
                            lineNumber++;
                            date = line;
                        } else if (lineNumber == 5) {
                            lineNumber++;
                            purityCondition = line;
                        } else if (lineNumber == 6) {
                            lineNumber++;
                            virus = line;
                        } else if (lineNumber == 7) {
                            lineNumber++;
                            contaminant = line;
                        } else {
                            lineNumber = 1;
                            QualityReport newQualityReport = new QualityReport(Integer.valueOf(reportNum), location, address, date,
                                PurityCondition.valueOf(purityCondition.toUpperCase()), Double.valueOf(virus), Double.valueOf(contaminant));
                            QualityReportList.addQualityReport(newQualityReport);
                            reportNum = "";
                            location = "";
                            address = "";
                            date = "";
                            purityCondition = "";
                            virus = "";
                            contaminant = "";
                        }
                    }

                    // Always close files.
                    bufferedReader.close();
                }
                catch(FileNotFoundException ex) {
                    System.out.println(
                        "Unable to open file '" +
                        fileName + "'");
                }
                catch(IOException ex) {
                    System.out.println(
                        "Error reading file '"
                        + fileName + "'");
                    // Or we could just do this:
                    // ex.printStackTrace();
                }

                primaryStage.setScene(new Scene(new HistoricalReportScreen(primaryStage)));
                primaryStage.show();
            } else {
                Alert alert = new Alert(AlertType.WARNING);
                alert.setTitle("Warning Dialog");
                alert.setHeaderText("This action cannot be performed!");
                alert.setContentText("Must be a Manager."
                    + " Press OK to continue");
                alert.showAndWait();
            }

        });

		// primaryStage.setResizable(false);
  //       primaryStage.setScene(scene);
  //       primaryStage.show();
	}

}