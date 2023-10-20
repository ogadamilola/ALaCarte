package project.a_la_carte.prototype.recipe.maker.inventory;

import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

public class IngredientWidget {
    private HBox widget;

    /**
     * Widget that displays the ingredient in InventoryView
     * @param ingredient
     */
    public IngredientWidget(Ingredient ingredient) {
        //we can make this prettier later on
        widget = new HBox(20);
        Label ingredientName = new Label(ingredient.getName());
        widget.getChildren().add(ingredientName);

    }

    public HBox getWidget() {
        return widget;
    }
}


