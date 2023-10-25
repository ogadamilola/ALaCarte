package project.a_la_carte.prototype.recipe.maker.inventory;

import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class RecipeWidget {

    private VBox widget;

    public RecipeWidget(Recipe recipe){
        widget = new VBox();
        //TODO make it look prettier
        Label nameLabel = new Label(recipe.getName());
        Label descLabel = new Label(recipe.getDescription());
        Label priceLabel = new Label("$" + recipe.getPrice());

        widget.getChildren().addAll(nameLabel,descLabel,priceLabel);
    }

    public VBox getWidget() {
        return widget;
    }
}
