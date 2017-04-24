/*
 Created by Corey Caskey on 4/24/2017
 */

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.*;

/**
 * Created by Corey Caskey Yang on 4/9/2017.
 */
public class WaterApp extends Application {

    /**
     * this method is called upon running/launching the application
     * this method should display a scene on the stage
     */
    public void start(Stage primaryStage) {

        // The name of the file to open.
        String fileName = "users.txt";

        // This will reference one line at a time
        String line = null;

        String email = "";
        String password = "";
        String userType = "";

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
                    email = line;
                } else if (lineNumber == 2) {
                    lineNumber++;
                    password = line;
                } else if (lineNumber == 3) {
                    lineNumber++;
                    userType = line;
                } else {
                    lineNumber = 1;
                    User newUser = new User(email, password, AccountType.valueOf(userType.toUpperCase()));
                    UserList.addUser(newUser);
                    email = "";
                    password = "";
                    userType = "";
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


        WelcomeScreen newWaterApp = new WelcomeScreen(primaryStage);
        Scene scene = new Scene(newWaterApp);
        primaryStage.setResizable(true);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * This is the main method that launches the javafx application
     */
    public static void main(String[] args) {
        Application.launch(args);
    }
}
