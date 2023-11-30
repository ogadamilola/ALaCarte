package project.a_la_carte.version2.serverSide.widgets;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import project.a_la_carte.version2.classesObjects.MenuFoodItem;

import java.util.ArrayList;

/**
 * Used to contain the MenuItems(with or without edits) on the current order
 */
public class OrderListView extends VBox {
    Button deleteButton;
    MenuFoodItem menuItem;
    ArrayList<String> customized;
    public OrderListView(MenuFoodItem item){
        customized = new ArrayList<>();
        this.setPrefSize(250,70);
        menuItem = item;
        double r = 1.5;
        deleteButton = new Button("X");
        deleteButton.setShape(new Circle(r));
        deleteButton.setMinSize(2*r,2*r);
        deleteButton.setStyle("-fx-border-color: black;-fx-background-color: cornflowerblue;\n");

        HBox buttonAlign = new HBox(deleteButton);
        buttonAlign.setPrefWidth(250);
        buttonAlign.setAlignment(Pos.TOP_RIGHT);

        Label name = new Label(menuItem.getName());
        Label change = new Label(menuItem.getCustomize());

        VBox align = new VBox(name,change);
        align.setPadding(new Insets(5));
        align.setSpacing(5);

        this.setStyle("-fx-border-color: black;-fx-background-color: cornflowerblue;\n");
        this.getChildren().addAll(buttonAlign,align);
    }

    /**
     * Get method for the MenuItem
     */
    public MenuFoodItem getMenuItem(){
        return this.menuItem;
    }

    /**
     * Get method for deleting
     */
    public Button getDeleteButton(){
        return this.deleteButton;
    }
}
