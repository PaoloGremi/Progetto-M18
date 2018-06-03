package Interface.searchCard;

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


        comboMonster.getItems().addAll("--Type of Monster--",
                "1,Aqua",
                "2,Beast",
                "3,Beast-Warrior",
                "4,Dinosaur",
                "5,Dragon",
                "6,Fairy",
                "7,Fiend",
                "8,Fish",
                "9,Insect",
                "10,Machine",
                "11,Plant",
                "12,Pyro",
                "13,Reptile",
                "14,Rock",
                "15,Sea Serpent",
                "16,Spellcaster",
                "17,Thunder",
                "18,Warrior",
                "19,Winged Beast",
                "20,Zombie",
                "21,Psychic");
        comboType.getItems().addAll("--Type of Cards--",
                "1,Normal monster",
                "2,Effect monster",
                "3,Fusion monster",
                "4,Ritual monster",
                "5,Normal spell",
                "6,Continuous spell",
                "7,Equip spell",
                "8,Field spell",
                "9,Quick-Play spell",
                "10,Ritual spell",
                "11,Normal trap",
                "12,Continuous trap",
                "13,Counter trap",
                "14,Synchro monster");
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

        Label atkLabel=new Label("--");
        Label defLabel=new Label("--");
        atkContainer.getChildren().addAll(new Label("ATK:"),atkSlider,atkLabel);
        defContainer.getChildren().addAll(new Label("DEF:") ,defSlider,defLabel);




        atkSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
            double valueAtk = 0;
            atkLabel.setText(Integer.toString(newValue.intValue()   ));
            valueAtk=newValue.doubleValue();

        });
        /*
        if(atkSlider.is)
        Label valueLabel=new Label("value atk current?");
        valueLabel.setText(Integer.toString((int) valueAtk));*/

        defSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
            defLabel.setText(Integer.toString(newValue.intValue()   ));

        });



        vBoxMain.getChildren().addAll(comboType,comboMonster,atkContainer,defContainer/*,valueLabel*/);
        mainPane.getChildren().add(vBoxMain);
        return mainPane;
    }
    public static ComboBox getComboMonster() {
        return comboMonster;
    }

    public static ComboBox getComboType() {
        return comboType;
    }

    public static Slider getAtkSlider() {
        return atkSlider;
    }

    public static Slider getDefSlider() {
        return defSlider;
    }
}
