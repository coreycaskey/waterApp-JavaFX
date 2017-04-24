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
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.scene.text.*;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;


public class LoginScreen extends StackPane {

	// Establish text field icons
	ImageView rain = new ImageView(
        new Image("https://rain.today/Pix/rain.jpg"));
    ImageView mail = new ImageView(
        new Image("https://cdn1.iconfinder.com/data/icons/simple-icons/2048/email-2048-black.png"));
    ImageView key = new ImageView(
        new Image("http://www.iconsdb.com/icons/preview/black/key-xxl.png"));

	// Establish login text fields
	TextField emailText = new TextField();
    TextField passText = new TextField();

    // Establish labels
    Label email = new Label("Email:");
    Label password = new Label("Password:");

    // Buttons
    Button login = new Button("Login");
    Button cancel = new Button("Cancel");

    // // create new stage

	public LoginScreen(Stage primaryStage) {
    	//int invalidCount = 2;

        final BooleanProperty firstTime = new SimpleBooleanProperty(true); // Variable to store the focus on stage load

        rain.setFitHeight(800);
        rain.setFitWidth(800);
        mail.setFitWidth(40);
        mail.setFitHeight(40);
        key.setFitWidth(40);
        key.setFitHeight(40);

        email.setTextFill(Color.BLACK);
        password.setTextFill(Color.BLACK);

        email.setFont(Font.font("Times New Roman", FontWeight.BOLD, 20));
        password.setFont(Font.font("Times New Roman", FontWeight.BOLD, 20));

        emailText.setPromptText("Enter your email.");
        passText.setPromptText("Enter your password.");

        GridPane gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setHgap(5.5);
        gridPane.setVgap(5.5);

        gridPane.add(email, 0, 0);
        gridPane.add(mail, 0, 1);
        gridPane.add(emailText, 1, 1);
        gridPane.add(password, 0, 2);
        gridPane.add(key, 0, 3);
        gridPane.add(passText, 1, 3);
        gridPane.add(login, 0, 4);
        gridPane.add(cancel, 1, 4);

        GridPane.setHalignment(mail, HPos.CENTER);
        GridPane.setHalignment(key, HPos.CENTER);
        GridPane.setHalignment(login, HPos.RIGHT);
        GridPane.setHalignment(cancel, HPos.LEFT);

		this.getChildren().add(rain);
		this.getChildren().add(gridPane);

        emailText.focusedProperty().addListener((observable,  oldValue,  newValue) -> {
            if(newValue && firstTime.get()){
                this.requestFocus(); // Delegate the focus to container
                firstTime.setValue(false); // Variable value changed for future references
            }
        });

        cancel.setOnAction(e -> {
            primaryStage.setScene(new Scene(new WelcomeScreen(primaryStage)));
            primaryStage.show();
        });

        login.setOnAction(e -> {
            if (!this.checkFields()) {
                Alert alert = new Alert(AlertType.WARNING);
                alert.setTitle("Warning Dialog");
                alert.setHeaderText("This action cannot be performed!");
                alert.setContentText("You need to fill out all fields. "
                    + " Press OK to continue");
                alert.showAndWait();
            }
            if (UserList.getUserListKeys().contains(emailText.getText().toString())
                && UserList.getPasswordList().contains(passText.getText().toString())) {

                UserList.setCurrentUser(emailText.getText().toString());
                primaryStage.setScene(new Scene(new AppScreen(primaryStage)));
                primaryStage.show();
            }
            // else {
            //     if (invalidCount <= 0) {
            //         Alert alert = new Alert(AlertType.WARNING);
            //         alert.setTitle("Warning Dialog");
            //         alert.setHeaderText("This action cannot be performed!");
            //         alert.setContentText("You used up all your login attempts. "
            //             + " Press OK to continue");
            //         alert.showAndWait();
            //     } else {
            //         invalidCount--;
            //         Alert alert = new Alert(AlertType.WARNING);
            //         alert.setTitle("Warning Dialog");
            //         alert.setHeaderText("This action cannot be performed!");
            //         alert.setContentText("Invalid login credentials. "
            //             + (invalidCount + 1) + " attempts remaining."
            //             + " Press OK to continue");
            //         alert.showAndWait();
            //     }
            // }
        });

		// Scene scene = new Scene(pane, 800, 800);

		// this.setResizable(false);
  //       this.setScene(scene);
  //       this.show();
	}

    public boolean checkFields() {
        if (emailText.getText().toString().equals("") || passText.getText().toString().equals("")) {
            return false;
        }
        return true;
    }

}