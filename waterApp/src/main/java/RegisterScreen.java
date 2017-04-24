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
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.collections.FXCollections;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

import java.io.*;



public class RegisterScreen extends StackPane {

	private ImageView rain = new ImageView(
		new Image("https://rain.today/Pix/rain.jpg"));
	private ImageView mail = new ImageView(
		new Image("https://cdn1.iconfinder.com/data/icons/simple-icons/2048/email-2048-black.png"));
	private ImageView key = new ImageView(
		new Image("http://www.iconsdb.com/icons/preview/black/key-xxl.png"));
    private ImageView hardHat = new ImageView(
        new Image("https://d30y9cdsu7xlg0.cloudfront.net/png/19196-200.png"));

    private TextField emailText = new TextField();
    private TextField passText = new TextField();
    // private TextField accountText = new TextField();

    private Label email = new Label("Email:");
    private Label password = new Label("Password:");
    private Label account = new Label("Account Type:");

    // Buttons
    private Button register = new Button("Register");
    private Button cancel = new Button("Cancel");

    // // create new stage
    // Stage nextStage = new Stage();

    private ListView<AccountType> accounts = new ListView<>(
            FXCollections.observableArrayList(AccountType.USER,
            AccountType.WORKER, AccountType.MANAGER, AccountType.ADMIN));

	public RegisterScreen(Stage primaryStage) {

        final BooleanProperty firstTime = new SimpleBooleanProperty(true); // Variable to store the focus on stage load

        rain.setFitHeight(800);
        rain.setFitWidth(800);
        mail.setFitWidth(40);
        mail.setFitHeight(40);
        key.setFitWidth(40);
        key.setFitHeight(40);
        hardHat.setFitHeight(40);
        hardHat.setFitWidth(40);

        email.setTextFill(Color.BLACK);
        password.setTextFill(Color.BLACK);
        account.setTextFill(Color.BLACK);

        email.setFont(Font.font("Times New Roman", FontWeight.BOLD, 20));
        password.setFont(Font.font("Times New Roman", FontWeight.BOLD, 20));
        account.setFont(Font.font("Times New Roman", FontWeight.BOLD, 20));

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
        gridPane.add(account, 0, 4);
        gridPane.add(hardHat, 0, 5);
        gridPane.add(this.getAccountTypeList(), 1, 5);
        gridPane.add(register, 0, 6);
        gridPane.add(cancel, 1, 6);

        GridPane.setHalignment(mail, HPos.CENTER);
        GridPane.setHalignment(key, HPos.CENTER);
        GridPane.setHalignment(hardHat, HPos.CENTER);
        GridPane.setHalignment(register, HPos.RIGHT);
        GridPane.setHalignment(cancel, HPos.LEFT);

        emailText.focusedProperty().addListener((observable,  oldValue,  newValue) -> {
            if(newValue && firstTime.get()){
                this.requestFocus(); // Delegate the focus to container
                firstTime.setValue(false); // Variable value changed for future references
            }
        });

		this.getChildren().add(rain);
		this.getChildren().add(gridPane);

        cancel.setOnAction(e -> {
            primaryStage.setScene(new Scene(new WelcomeScreen(primaryStage)));
            primaryStage.show();
        });

        register.setOnAction(e -> {
            if (!this.checkFields()) {
                Alert alert = new Alert(AlertType.WARNING);
                alert.setTitle("Warning Dialog");
                alert.setHeaderText("This action cannot be performed!");
                alert.setContentText("You need to fill out all fields. "
                    + " Press OK to continue");
                alert.showAndWait();
            } else {
                User newUser = new User(emailText.getText().toString(),
                passText.getText().toString(), this.getAccountTypeList().getSelectionModel().getSelectedItem());
                boolean added = UserList.addUser(newUser);
                if (added == false) {
                    Alert alert = new Alert(AlertType.WARNING);
                    alert.setTitle("Warning Dialog");
                    alert.setHeaderText("This action cannot be performed!");
                    alert.setContentText("Email is already taken - use another. "
                        + " Press OK to continue");
                    alert.showAndWait();
                } else {

                    // The name of the file to open.
                    String fileName = "users.txt";

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

                        for (User user: UserList.getUserListValues()) {
                            bufferedWriter.write(user.getEmail() + "\n");
                            bufferedWriter.write(user.getPassword() + "\n");
                            bufferedWriter.write(user.getAccountType().toString() + "\n");
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

                    primaryStage.setScene(new Scene(new LoginScreen(primaryStage)));
                    primaryStage.show();
                }
            }

        });

		// Scene scene = new Scene(pane, 800, 800);

		// this.setResizable(false);
  //       this.setScene(scene);
  //       this.show();
	}

    public boolean checkFields() {
        if (emailText.getText().toString().equals("") || passText.getText().toString().equals("") ||
            this.getAccountTypeList().getSelectionModel().getSelectedItem() == null) {
            return false;
        }
        return true;
    }

        /**
    * return a ListView of AccountTypes representing the list of
    * available account types
    * @return listview of AccountType
    */
    public ListView<AccountType> getAccountTypeList() {
        accounts.setMaxHeight(120);
        accounts.setMaxWidth(300);
        accounts.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        return accounts;
    }

}