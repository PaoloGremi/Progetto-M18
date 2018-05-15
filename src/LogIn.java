import Interface.MainWindow;
import TradeCenter.Exceptions.UserExceptions.CheckPasswordConditionsException;
import TradeCenter.TradeCenter;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class LogIn extends Application{

    Stage window;
    Button logIn, signUp;
    TextField username;
    PasswordField password;
    Label credentials;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        TradeCenter tc = new TradeCenter();
        window = primaryStage;
        window.setTitle("LogIn interface");
        signUp = new Button("SignUp");

        logIn = new Button("LogIn");

        credentials = new Label("Please enter username and password\n the password must have at least 8 characters\n an Uppercase a lowercase and a number.");
        username = new TextField("Username");
        password = new PasswordField();
        password.setPromptText("Password");
        VBox credentialBox = new VBox();
        credentialBox.getChildren().addAll(credentials, username, password);
        credentialBox.setSpacing(20);

        HBox logIn_Register = new HBox();
        logIn_Register.getChildren().addAll(logIn, signUp);
        logIn_Register.setSpacing(30);
        logIn_Register.setAlignment(Pos.CENTER);

        signUp.setOnAction(event -> {
            PasswordField passwordVerified = new PasswordField();
            passwordVerified.setPromptText("Repeat the password");
            credentialBox.getChildren().add(passwordVerified);
            Button confirm = new Button("Confirm");

            confirm.setOnAction(event1 -> {
                if(tc.verifyPassword(password.getText(), passwordVerified.getText())) {
                    {
                        try {
                            tc.addCustomer(username.getText(), password.getText());
                        } catch (CheckPasswordConditionsException e) {
                            Label error = new Label("Incorrect password");
                            credentialBox.getChildren().add(error);
                        }
                    }
                }
            });

            logIn_Register.getChildren().add(confirm);
            logIn_Register.getChildren().removeAll(signUp, logIn);
        });
        logIn.setOnAction(event -> {
            MainWindow.display(username.getText());
        });
        BorderPane layout = new BorderPane();
        layout.setCenter(credentialBox);
        layout.setBottom(logIn_Register);
        Scene scene = new Scene(layout, 300, 250);
        window.setScene(scene);
        window.show();
    }
}
