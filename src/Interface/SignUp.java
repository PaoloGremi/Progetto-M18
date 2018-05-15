package Interface;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import TradeCenter.Customers.Customer;

public class SignUp {
    static boolean correctcredentials;
    public static boolean display(){

        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("SignUp window");
        VBox logInLayout = new VBox(20);
        Label label1 = new Label("insert username and password, and repeat the password");
        TextField username = new TextField("Username");
        username.setPromptText("Username");
       //create password field
        PasswordField password = new PasswordField();
        password.setPromptText("Password");
       //create the check for the password
        PasswordField passwordVerifier = new PasswordField();
        passwordVerifier.setPromptText("Repeat the password");

        Button submit = new Button("Submit");
        // è solo una prova poi implementeremo il metodo di controllo, per adesso è sempre vero che mette la giusta password e id
        submit.setOnAction(event -> {
            correctcredentials = true;
            window.close();
        });
        logInLayout.getChildren().addAll(label1, username, password, passwordVerifier, submit);
        logInLayout.setAlignment(Pos.CENTER);
        Scene scene = new Scene(logInLayout, 500, 200);
        window.setScene(scene);
        window.showAndWait();
        return correctcredentials;


    }
}
