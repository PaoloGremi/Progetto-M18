package Interface;

import javafx.geometry.Insets;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public class YugiohFilter {
    static Pane mainPane;
    static ComboBox comboMonster;
    static ComboBox comboType;
    static VBox vBoxMain;
    static Slider atkSlider;
    static Slider defSlider;
    static VBox atkContainer;
    static VBox defContainer;

    static Pane display(){
        mainPane=new Pane();
        comboMonster=new ComboBox();
        comboType=new ComboBox();
        vBoxMain=new VBox();
        vBoxMain.setPadding(new Insets(15,0,5,0));
        vBoxMain.setSpacing(10);
        comboMonster.setPromptText("Type of Monster");
        comboType.setPromptText("Type of Card");


        comboMonster.getItems().addAll("1","2","3","4");
        comboType.getItems().addAll("1","2","3","4");
        //ATK e DEF
        atkContainer=new VBox();
        defContainer=new VBox();
        atkSlider=new Slider();
        defSlider=new Slider();

        atkContainer.setPadding(new Insets(15));
        atkContainer.setSpacing(7);
        atkContainer.setStyle("-fx-background-color: orange");
        defContainer.setPadding(new Insets(15));
        defContainer.setSpacing(7);
        defContainer.setStyle("-fx-background-color: orange");

        atkSlider.setMin(0);
        atkSlider.setMax(5000);
        atkSlider.setShowTickLabels(true);
        atkSlider.setShowTickMarks(true);
        atkSlider.setMajorTickUnit(2500);
        atkSlider.setMinorTickCount(250);
        atkSlider.setBlockIncrement(500);



        defSlider.setMin(0);
        defSlider.setMax(5000);
        defSlider.setShowTickLabels(true);
        defSlider.setShowTickMarks(true);
        defSlider.setMajorTickUnit(2500);
        defSlider.setMinorTickCount(250);
        defSlider.setBlockIncrement(500);

        Label atkLabel=new Label("Non modificato");
        atkContainer.getChildren().addAll(new Label("ATK:"),atkSlider,atkLabel);
        defContainer.getChildren().addAll(new Label("DEF:") ,defSlider);

        atkSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
            atkLabel.setText(Integer.toString(newValue.intValue()   ));

        });
        /*double valatk;
        String valAtk=new String();
        if(atkSlider.valueCh){
            atkContainer.getChildren().remove(atkPrint);
            valatk=atkSlider.getValue();
            valAtk=Double.toString(valatk);
            atkPrint.setText(valAtk);
            atkContainer.getChildren().add(atkPrint);
        }*/


        vBoxMain.getChildren().addAll(comboMonster,comboType,atkContainer,defContainer);
        mainPane.getChildren().add(vBoxMain);
        return mainPane;
    }
}
