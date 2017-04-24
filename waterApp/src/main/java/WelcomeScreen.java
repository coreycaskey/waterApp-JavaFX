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
import javafx.scene.layout.HBox;
import javafx.event.EventHandler;
import javafx.event.ActionEvent;



public class WelcomeScreen extends StackPane {

	private ImageView rain = new ImageView(
		new Image("https://rain.today/Pix/rain.jpg"));

    private Button register = new Button("Register");
    private Button login = new Button("Login");

    private Label welcomeText = new Label("Register or Login!");

    // // create new stage
    // Stage nextStage = new Stage();

	public WelcomeScreen(Stage primaryStage) {
        RegisterScreen regScreen = new RegisterScreen(primaryStage);
        Scene rScene = new Scene(regScreen);
        LoginScreen logScreen = new LoginScreen(primaryStage);
        Scene lScene = new Scene(logScreen);
        rain.setFitHeight(800);
        rain.setFitWidth(800);

        welcomeText.setTextFill(Color.BLACK);

        HBox buttons = new HBox(10.0);
        buttons.getChildren().add(register);
        buttons.getChildren().add(login);

        GridPane welcome = new GridPane();
        welcome.setAlignment(Pos.CENTER);
        welcome.setHgap(5.5);
        welcome.setVgap(5.5);

        welcome.add(welcomeText, 0, 0);
        welcome.add(buttons, 0, 1);

        GridPane.setHalignment(welcomeText, HPos.CENTER);
        GridPane.setHalignment(buttons, HPos.CENTER);

		this.getChildren().add(rain);
		this.getChildren().add(welcome);

        // transition based on button click
        register.setOnAction(e -> {
            primaryStage.setScene(rScene);
            primaryStage.show();
        });

        login.setOnAction(e -> {
            primaryStage.setScene(lScene);
            primaryStage.show();
        });

		// primaryStage.setResizable(false);
  //       primaryStage.setScene(scene);
  //       primaryStage.show();
	}

}