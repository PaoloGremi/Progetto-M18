package Interface;

import javafx.geometry.Insets;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public class PokemonFilter {
    static Pane mainPane;
    static VBox vBoxMain;
    static ComboBox comboType;
    static Slider hpSlider;
    static Slider levSlider;
    static Slider weightSlider;
    static VBox hpContainer;
    static VBox levContainer;
    static VBox weigthContainer;
    static VBox lengContainer;
    static HBox textContainer;
    static TextField textLen1;
    static TextField textLen2;

    static Pane display(){
        mainPane=new Pane();
        vBoxMain=new VBox();
        comboType=new ComboBox();
        hpSlider=new Slider();
        levSlider=new Slider();
        weightSlider=new Slider();
        hpContainer=new VBox();
        levContainer=new VBox();
        weigthContainer=new VBox();


        vBoxMain.setPadding(new Insets(15,0,5,0));
        vBoxMain.setSpacing(10);

        comboType.setPromptText("Type of Pokèmon");
        comboType.getItems().addAll("1","2","3");

        //Slider:
        hpContainer.setPadding(new Insets(15));
        hpContainer.setSpacing(7);
        hpContainer.setStyle("-fx-background-color: orange");

        levContainer.setPadding(new Insets(15));
        levContainer.setSpacing(7);
        levContainer.setStyle("-fx-background-color: orange");

        weigthContainer.setPadding(new Insets(15));
        weigthContainer.setSpacing(7);
        weigthContainer.setStyle("-fx-background-color: orange");

        hpSlider.setMin(0);
        hpSlider.setMax(200);
        hpSlider.setShowTickLabels(true);
        hpSlider.setShowTickMarks(true);
        hpSlider.setMajorTickUnit(100);
        hpSlider.setMinorTickCount(5);
        hpSlider.setBlockIncrement(10);

        levSlider.setMin(0);
        levSlider.setMax(76);
        levSlider.setShowTickLabels(true);
        levSlider.setShowTickMarks(true);
        levSlider.setMajorTickUnit(38);
        levSlider.setMinorTickCount(2);
        levSlider.setBlockIncrement(5);

        weightSlider.setMin(0);
        weightSlider.setMax(518);
        weightSlider.setShowTickLabels(true);
        weightSlider.setShowTickMarks(true);
        weightSlider.setMajorTickUnit(259);
        weightSlider.setMinorTickCount(12);
        weightSlider.setBlockIncrement(24);

        hpContainer.getChildren().addAll(new Label("HP: "), hpSlider);
        levContainer.getChildren().addAll(new Label("Level: "), levSlider);
        weigthContainer.getChildren().addAll(new Label("Weigth: "), weightSlider);

        //Length:

        lengContainer=new VBox();
        textContainer=new HBox();
        textLen1=new TextField();
        textLen2=new TextField();
        textLen1.setPrefSize(0.8,0.8); //capire come settarli giusti
        textLen2.setPrefSize(5,3);
        /*.setMinSize(7,7);
        textLen2.setMinSize(7,7);
        textLen1.setMaxSize(10,10);
        textLen2.setMaxSize(10,10);*/
        lengContainer.setPadding(new Insets(15));
        lengContainer.setSpacing(7);
        lengContainer.setStyle("-fx-background-color: orange");
        textContainer.setPadding(new Insets(5));
        textContainer.setSpacing(5);


        textContainer.getChildren().addAll(textLen1,new Label(" ' "),textLen2,new Label(" '' "));
        lengContainer.getChildren().addAll(new Label("Length:"), textContainer);






        vBoxMain.getChildren().addAll(comboType,hpContainer,levContainer,weigthContainer,lengContainer);
        mainPane.getChildren().add(vBoxMain);
        return mainPane;
    }
}
