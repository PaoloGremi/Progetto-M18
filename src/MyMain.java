import Interface.LogIn;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import Interface.LogIn;

public class MyMain extends Application{

    Stage window;
    Button logIn;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        window = primaryStage;
        window.setTitle("LogIn interface");
        logIn = new Button("LogIn");
        logIn.setOnAction(event -> {
            boolean Logged = LogIn.display();
        });
        StackPane layout = new StackPane();
        layout.getChildren().add(logIn);
        Scene scene = new Scene(layout, 300, 250);
        window.setScene(scene);
        window.show();
    }
}
