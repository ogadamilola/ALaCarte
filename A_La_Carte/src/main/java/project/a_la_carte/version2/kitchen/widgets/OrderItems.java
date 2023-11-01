package project.a_la_carte.version2.kitchen.widgets;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import project.a_la_carte.version2.classesObjects.MenuFoodItem;

//Over here would be the changing of order time on kitchen side <-- don't need this yet, not in proto todo
//Also the prep timer
public class OrderItems extends HBox {
    Button complete;
    Button viewRecipe;
    MenuFoodItem menuFoodItem;
    public OrderItems(MenuFoodItem item){
        this.menuFoodItem = item;
        complete = new Button("Complete");
        viewRecipe = new Button("Recipe");

        VBox buttonBox = new VBox(viewRecipe, complete);
        buttonBox.setPrefSize(250,70);
        buttonBox.setPadding(new Insets(5));
        buttonBox.setSpacing(5);
        buttonBox.setAlignment(Pos.BASELINE_RIGHT);

        this.setPrefSize(200,70);
        this.setPadding(new Insets(5));
        this.setStyle("-fx-border-color: black;-fx-background-color: cornflowerblue;\n");

        Label nameLabel = new Label(menuFoodItem.getName());
        VBox stringDisplay = new VBox();
        stringDisplay.setSpacing(3);
        stringDisplay.setPrefSize(200,70);

        if (menuFoodItem.isCustomized()){
            Label customize = new Label("   "+ menuFoodItem.getCustomize());
            stringDisplay.getChildren().addAll(nameLabel,customize);
        }
        else {
            stringDisplay.getChildren().add(nameLabel);
        }

        this.getChildren().addAll(stringDisplay,buttonBox);
    }
    public MenuFoodItem getMenuFoodItem(){
        return this.menuFoodItem;
    }
    public Button getCompleteButton(){
        return this.complete;
    }
    public Button getViewRecipeButton(){
        return this.viewRecipe;
    }
}
