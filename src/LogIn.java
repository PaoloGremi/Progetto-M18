import Interface.MainWindow;
import Interface.SignUp;
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
        window = primaryStage;
        window.setTitle("LogIn interface");
        signUp = new Button("SignUp");
        signUp.setOnAction(event -> {
            boolean Logged = SignUp.display();
        });
        logIn = new Button("LogIn");

        credentials = new Label("Please enter username and password");
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
