package Interface.searchCard;

import com.jfoenix.controls.JFXSlider;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public class YugiohFilter {
    static Pane mainPane;
    static ComboBox comboMonster;
    static ComboBox comboType;
    static VBox vBoxMain;
    static JFXSlider atkSlider;
    static JFXSlider defSlider;
    static JFXSlider levSlider;
    static VBox atkContainer;
    static VBox defContainer;
    static VBox refContainer;
    static TextField referenceText;
    static VBox levContainer;
    static HBox atkLabelContainer;
    static HBox defLabelContainer;
    static HBox levLabelContainer;

    static Pane display(){
        mainPane=new Pane();
        comboMonster=new ComboBox();
        comboType=new ComboBox();
        vBoxMain=new VBox();
        vBoxMain.setPadding(new Insets(15,0,5,0));
        vBoxMain.setSpacing(10);
        comboMonster.setPromptText("Type of Monster");
        comboType.setPromptText("Type of Card");


        comboMonster.getItems().addAll("--Nothing--",
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
        comboType.getItems().addAll("--Nothing--",
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

        //ATK,DEF,LEV
        atkContainer=new VBox();
        defContainer=new VBox();
        levContainer=new VBox();
        atkLabelContainer=new HBox();
        defLabelContainer=new HBox();
        levLabelContainer=new HBox();
        atkSlider= new JFXSlider();
        defSlider= new JFXSlider();
        levSlider= new JFXSlider();

        atkContainer.setPadding(new Insets(15));
        atkContainer.setSpacing(7);
        atkContainer.setStyle("-fx-background-color: orange");
        defContainer.setPadding(new Insets(15));
        defContainer.setSpacing(7);
        defContainer.setStyle("-fx-background-color: orange");
        levContainer.setPadding(new Insets(15));
        levContainer.setSpacing(7);
        levContainer.setStyle("-fx-background-color: orange");

        atkSlider.setValue(0);
        atkSlider.setMin(0);
        atkSlider.setMax(5000);
        atkSlider.setShowTickLabels(true);
        atkSlider.setShowTickMarks(true);
        atkSlider.setMajorTickUnit(2500);
        atkSlider.setMinorTickCount(250);
        atkSlider.setBlockIncrement(500);

        defSlider.setValue(0);
        defSlider.setMin(0);
        defSlider.setMax(5000);
        defSlider.setShowTickLabels(true);
        defSlider.setShowTickMarks(true);
        defSlider.setMajorTickUnit(2500);
        defSlider.setMinorTickCount(250);
        defSlider.setBlockIncrement(500);

        levSlider.setValue(0);
        levSlider.setMin(0);
        levSlider.setMax(7);
        levSlider.setShowTickLabels(true);
        levSlider.setShowTickMarks(true);
        levSlider.setMajorTickUnit(1);
        levSlider.setMinorTickCount(1);
        levSlider.setBlockIncrement(1);

        Label atkLabel=new Label("--");
        Label defLabel=new Label("--");
        Label levLabel=new Label("--");


        atkLabelContainer.getChildren().addAll(new Label("ATK: "),atkLabel);
        defLabelContainer.getChildren().addAll(new Label("DEF: "),defLabel);
        levLabelContainer.getChildren().addAll(new Label("LEVEL: "),levLabel);


        atkContainer.getChildren().addAll(atkLabelContainer,atkSlider);
        defContainer.getChildren().addAll(defLabelContainer,defSlider);
        levContainer.getChildren().addAll(levLabelContainer,levSlider);


        atkSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
            //double valueAtk = 0;
            atkLabel.setText(Integer.toString(newValue.intValue()   ));
//            valueAtk=newValue.doubleValue();
        });

        defSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
            defLabel.setText(Integer.toString(newValue.intValue()   ));
        });

        levSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
            levLabel.setText(Integer.toString(newValue.intValue()   ));
        });
        //CheckBox
        HBox checkContainer=new HBox();
        CheckBox cbAtk=new CheckBox("ATK ");
        CheckBox cbLev=new CheckBox("Level ");
        CheckBox cbDef=new CheckBox("DEF ");
        CheckBox cbReF=new CheckBox("Ref");
        checkContainer.getChildren().addAll(cbAtk,cbDef,cbLev,cbReF);

        //Action check
        CheckBox cbList[]={cbAtk,cbDef,cbLev,cbReF};
        Pane container[]={atkContainer,defContainer,levContainer,refContainer};
        for (int i=0;i<cbList.length;i++) {
            Pane containerCurr=container[i];
            cbList[i].selectedProperty().addListener(new ChangeListener<Boolean>() {
                @Override
                public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                    if(newValue){
                        vBoxMain.getChildren().add(containerCurr);
                    }
                    else if (vBoxMain.getChildren().contains(containerCurr)){
                        vBoxMain.getChildren().remove(containerCurr);
                    }
                }
            });
        }



        vBoxMain.getChildren().addAll(comboType,comboMonster,checkContainer);
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

    public static Slider getLevSlider() {
        return levSlider;
    }
}
