package project.a_la_carte.version2.managerSide.inventory.widgets;

import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import project.a_la_carte.version2.classesObjects.Ingredient;

public class IngredientWidget {
    private HBox widget;

    /**
     * Widget that displays the ingredient in InventoryView
     * @param ingredient
     */
    public IngredientWidget(Ingredient ingredient, Double quantity, String measurement) {
        //we can make this prettier later on
        widget = new HBox(20);
        Label ingredientName = new Label(ingredient.getName());
        Label ingredientQuantity = new Label(quantity.toString());
        Label ingredientMeasurement = new Label(measurement);
        widget.getChildren().addAll(ingredientName,ingredientQuantity,ingredientMeasurement);

    }

    public HBox getWidget() {
        return widget;
    }
}


