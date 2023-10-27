package project.a_la_carte.prototype.recipe.maker.inventory;

import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class MenuItemWidget {
    private VBox widget;
    public MenuItemWidget(MenuFoodItem menuItem){
        widget = new VBox();
        //TODO make it look prettier
        Label nameLabel = new Label(menuItem.getName());
        Label descLabel = new Label(menuItem.getDescription());
        Label priceLabel = new Label("$" + menuItem.getPrice());

        widget.getChildren().addAll(nameLabel,descLabel,priceLabel);
    }

    public VBox getWidget() {
        return widget;
    }
}
