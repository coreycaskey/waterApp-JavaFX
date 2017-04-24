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

import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.collections.FXCollections;

import java.io.*;



public class ProfileScreen extends StackPane {

	private ImageView rain = new ImageView(
        new Image("https://rain.today/Pix/rain.jpg"));

    private Button editUserType = new Button("Confirm User Type Change");
    private Button finish = new Button("Finish");

    private TextField emailText = new TextField(UserList.getCurrentUser().getEmail());
    private TextField userText = new TextField(
        UserList.getCurrentUser().getAccountType().toString());
    private TextField passText = new TextField(
        UserList.getCurrentUser().getPassword());

    private Label changeEmail = new Label("Your current email:");
    private Label changeUserType = new Label("Your current account type:");
    private Label changePassword = new Label("Your current password:");

    private ListView<AccountType> accounts = new ListView<>(
            FXCollections.observableArrayList(AccountType.USER,
            AccountType.WORKER, AccountType.MANAGER, AccountType.ADMIN));

	public ProfileScreen(Stage primaryStage) {

        final BooleanProperty firstTime = new SimpleBooleanProperty(true); // Variable to store the focus on stage load

        rain.setFitHeight(800);
        rain.setFitWidth(800);

        VBox emailSection = new VBox(5.5);
        emailSection.getChildren().add(changeEmail);
        emailSection.getChildren().add(emailText);
        emailSection.setAlignment(Pos.CENTER);

        VBox userTypeSection = new VBox(5.5);
        userTypeSection.getChildren().add(changeUserType);
        userTypeSection.getChildren().add(userText);
        userTypeSection.getChildren().add(this.getAccountTypeList());
        userTypeSection.getChildren().add(editUserType);
        userTypeSection.setAlignment(Pos.CENTER);

        VBox passwordSection = new VBox(5.5);
        passwordSection.getChildren().add(changePassword);
        passwordSection.getChildren().add(passText);
        passwordSection.setAlignment(Pos.CENTER);

        GridPane editProfile = new GridPane();
        editProfile.setAlignment(Pos.CENTER);
        editProfile.setVgap(20);

        editProfile.add(emailSection, 0, 0);
        editProfile.add(userTypeSection, 0, 1);
        editProfile.add(passwordSection, 0, 2);
        editProfile.add(finish, 0, 3);

        GridPane.setHalignment(emailSection, HPos.CENTER);
        GridPane.setHalignment(userTypeSection, HPos.CENTER);
        GridPane.setHalignment(passwordSection, HPos.CENTER);
        GridPane.setHalignment(finish, HPos.CENTER);

        emailText.focusedProperty().addListener((observable,  oldValue,  newValue) -> {
            if(newValue && firstTime.get()){
                this.requestFocus(); // Delegate the focus to container
                firstTime.setValue(false); // Variable value changed for future references
            }
        });

		this.getChildren().add(rain);
		this.getChildren().add(editProfile);

        editUserType.setOnAction(e -> {
            userText.setText(this.getAccountTypeList().getSelectionModel().getSelectedItem().toString());
        });

        finish.setOnAction(e -> {
            User user = UserList.getCurrentUser(); // gets current email
            User editedUser = null;
            if (this.getAccountTypeList().getSelectionModel().getSelectedItem() == null) {
                editedUser = new User(emailText.getText().toString(), passText.getText().toString(),
                    this.getAccountEnum(userText.getText().toString()));
            } else {
                editedUser = new User(emailText.getText().toString(), passText.getText().toString(),
                    this.getAccountTypeList().getSelectionModel().getSelectedItem());
            }
            Map<String, User> copyUserList = UserList.getUserList();
            copyUserList.remove(user.getEmail());
            UserList.setUserList(copyUserList);

            if (UserList.addUser(editedUser)) {
                UserList.setCurrentUser(editedUser.getEmail());

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

                        for (User aUser: UserList.getUserListValues()) {
                            bufferedWriter.write(aUser.getEmail() + "\n");
                            bufferedWriter.write(aUser.getPassword() + "\n");
                            bufferedWriter.write(aUser.getAccountType().toString() + "\n");
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

                primaryStage.setScene(new Scene(new AppScreen(primaryStage)));
                primaryStage.show();
            } else {
                Alert alert = new Alert(AlertType.WARNING);
                alert.setTitle("Warning Dialog");
                alert.setHeaderText("This action cannot be performed!");
                alert.setContentText("Email already taken - Try another. "
                    + " Press OK to continue");
                alert.showAndWait();
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
    public ListView<AccountType> getAccountTypeList() {
        accounts.setMaxHeight(120);
        accounts.setMaxWidth(300);
        accounts.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        return accounts;
    }

    public AccountType getAccountEnum(String value) {
        if (value.equals("Worker")) {
            return AccountType.WORKER;
        } else if (value.equals("User")) {
            return AccountType.USER;
        } else if (value.equals("Manager")) {
            return AccountType.MANAGER;
        } else {
            return AccountType.ADMIN;
        }
    }

}
