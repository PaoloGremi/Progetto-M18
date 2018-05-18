import Interface.MainWindow;
import TradeCenter.Exceptions.UserExceptions.CheckPasswordConditionsException;
import TradeCenter.TradeCenter;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;

public class LogIn extends Application{

    Stage window;
    Button logIn, signUp;
    TextField username;
    PasswordField password;
    Text credentials;
    BorderPane border;
    StackPane stack;


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

        credentials = new Text("Log in your account");
        TextFlow credFlow = new TextFlow();
        credFlow.setPadding(new Insets(5, 5,0,5));
        credFlow.setTextAlignment(TextAlignment.CENTER);
        credFlow.getChildren().add(credentials);
        username = new TextField("Username");
        password = new PasswordField();
        password.setPromptText("Password");
        VBox credentialBox = new VBox();
        stack = new StackPane();
        stack.setPadding(new Insets(5,0,20,0));
        border = new BorderPane();
        credentialBox.getChildren().addAll(credFlow, username, password);
        credentialBox.setSpacing(20);
        credentialBox.setPadding(new Insets(5));

        HBox logIn_Register = new HBox();
        logIn_Register.setPadding(new Insets(0,0,30,0));
        logIn_Register.getChildren().addAll(logIn, signUp);
        logIn_Register.setSpacing(20);
        logIn_Register.setAlignment(Pos.CENTER);

        signUp.setOnAction(event -> {
            credFlow.setPadding(new Insets(5, 5,0,5));
            credentials.setText("Please enter username and password the password must have at least 8 characters an uppercase a lowercase and a number.");
            PasswordField passwordVerified = new PasswordField();
            passwordVerified.setPromptText("Repeat the password");
            credentialBox.getChildren().add(passwordVerified);
            Button confirm = new Button("Confirm");


            confirm.setOnAction(event1 -> {



                if(tc.verifyPassword(password.getText(), passwordVerified.getText())) {
                    {
                        try {
                            tc.addCustomer(username.getText(), password.getText());
                            MainWindow.display(username.getText());
                        } catch (CheckPasswordConditionsException e) {
                            Text errorText = new Text(e.getMessage());

                            TextFlow error = new TextFlow();
                            error.setPadding(new Insets(0,5,5,5));
                            error.setTextAlignment(TextAlignment.CENTER);
                            error.getChildren().add(errorText);
                            stack.getChildren().removeAll(stack.getChildren());
                            stack.getChildren().add(error);

                        }
                    }
                }
                else {
                    Text errorTextMatch = new Text("Sorry, passwords doesn't match.");

                    TextFlow error = new TextFlow();
                    error.setPadding(new Insets(0,5,5,5));

                    error.getChildren().add(errorTextMatch);
                    stack.getChildren().removeAll(stack.getChildren());
                    stack.getChildren().add(error);
                }
            });

            logIn_Register.getChildren().add(confirm);
            logIn_Register.getChildren().removeAll(signUp, logIn);
        });
        logIn.setOnAction(event -> {
            MainWindow.display(username.getText());
        });
        border.setCenter(credentialBox);
        border.setBottom(stack);
        BorderPane layout = new BorderPane();
        layout.setCenter(border);
        layout.setBottom(logIn_Register);
        Scene scene = new Scene(layout, 300, 335);
        window.setScene(scene);
        window.show();
    }
}
