package project.a_la_carte.prototype.recipe.maker.inventory;

import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

public class IngredientWidget {
    private HBox widget;
    private Ingredient ingredient;


    public IngredientWidget(Ingredient ingredient) {
        widget = new HBox(20);
        this.ingredient = ingredient;
        Label ingredientName = new Label(ingredient.getName());

        Label quantity = new Label(Double.toString(ingredient.getQuantity()));
        widget.getChildren().addAll(ingredientName, quantity);


    }

    public HBox getWidget() {
        return widget;
    }
}

