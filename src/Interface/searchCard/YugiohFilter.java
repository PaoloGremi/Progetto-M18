package Interface.searchCard;

import javafx.geometry.Insets;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
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
    static VBox refContainer;
    static TextField referenceText;

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
                "Aqua",
                "Beast",
                "Beast-Warrior",
                "Dinosaur",
                "Dragon",
                "Fairy",
                "Fiend",
                "Fish",
                "Insect",
                "Machine",
                "Plant",
                "Pyro",
                "Reptile",
                "Rock",
                "Sea Serpent",
                "Spellcaster",
                "Thunder",
                "Warrior",
                "Winged Beast",
                "Zombie",
                "Psychic");
        comboType.getItems().addAll("--Type of Cards--",
                "Normal monster",
                "Effect monster",
                "Fusion monster",
                "Ritual monster",
                "Normal spell",
                "Continuous spell",
                "Equip spell",
                "Field spell",
                "Quick-Play spell",
                "Ritual spell",
                "Normal trap",
                "Continuous trap",
                "Counter trap",
                "Synchro monster");
        //Reference
        refContainer=new VBox();
        referenceText=new TextField();

        refContainer.setPadding(new Insets(15));
        refContainer.setSpacing(7);
        refContainer.setStyle("-fx-background-color: orange");
        referenceText.setPrefWidth(55);
        refContainer.getChildren().addAll(new Label("Reference: "),referenceText);

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



        vBoxMain.getChildren().addAll(comboType,comboMonster,refContainer,atkContainer,defContainer);
        mainPane.getChildren().add(vBoxMain);
        return mainPane;
    }

    public static TextField getReferenceText() {
        return referenceText;
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
