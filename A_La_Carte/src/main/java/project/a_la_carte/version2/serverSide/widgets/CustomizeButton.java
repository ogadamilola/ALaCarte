package project.a_la_carte.version2.serverSide.widgets;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import project.a_la_carte.version2.interfaces.AlertButtonInterface;
import project.a_la_carte.version2.interfaces.MenuItemWidget;

public class CustomizeButton extends StackPane implements MenuItemWidget {
    Background selectedBG;
    Background unselectedBG;
    VBox edits;
    Button select;
    public CustomizeButton(String name){
        this.setPrefSize(140,50);
        //If this menu item is selected, it will be highlighted
        selectedBG = new Background(new BackgroundFill(Color.GRAY,new CornerRadii(3),null));
        //If the button is unselected, leave it as it
        unselectedBG = new Background(new BackgroundFill(Color.WHITE,new CornerRadii(3),null));
        this.setBackground(unselectedBG);
        Label nameL = new Label(name);

        select = new Button();
        select.setPrefSize(160,50);
        select.setStyle("-fx-background-color: transparent;\n" + "-fx-border-color: black;");
        select.prefWidthProperty().bind(this.widthProperty());
        select.prefHeightProperty().bind(this.heightProperty());

        edits = new VBox(nameL);
        edits.setPrefSize(140,50);
        edits.setSpacing(2);
        edits.setAlignment(Pos.TOP_CENTER);

        this.setStyle("-fx-border-color: transparent;\n" +
                "fx-border-width: 1;\n" + "-fx-background-color: transparent");
        this.getChildren().addAll(edits, select);
    }
    public Button getSelect(){
        return this.select;
    }
    public void addEdit(String edit){
        Label newL = new Label(edit);
        this.edits.getChildren().add(newL);
    }

    @Override
    public void select() {
        this.setBackground(selectedBG);
    }

    @Override
    public void unselect() {
        this.setBackground(unselectedBG);
    }
}
