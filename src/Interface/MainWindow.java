package Interface;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class MainWindow extends Application{

    Stage mainWindow;

    @Override
    public void start(Stage primaryStage) throws Exception {
        mainWindow = primaryStage;
        mainWindow.setTitle("MAIN MENU");

        StackPane layout = new StackPane();


        VBox vbox = new VBox();
        vbox.setPadding(new Insets(10));
        vbox.setSpacing(8);
        vbox.getChildren().add(new Text("Robbbe"));

        layout.getChildren().add(vbox);

        Scene scene = new Scene(layout, 1200, 700);
        mainWindow.setScene(scene);
        mainWindow.show();
    }

}
