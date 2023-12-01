package project.a_la_carte.version2.serverSide.widgets;

import javafx.scene.control.Button;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import project.a_la_carte.version2.interfaces.MenuItemWidget;

/**
 * Button container for displaying MenuItem's ingredients
 */
public class IngredientsCustomize extends Button implements MenuItemWidget {
    Background selectedBG;
    Background unselectedBG;
    Boolean selected;
    String ingredientName;
    public IngredientsCustomize(String name){
        this.setPrefSize(200,120);
        ingredientName = name;
        selected = false;

        //If this menu item is selected, it will be highlighted
        selectedBG = new Background(new BackgroundFill(Color.GRAY,new CornerRadii(3),null));
        //If the button is unselected, leave it as it
        unselectedBG = new Background(new BackgroundFill(Color.WHITE,new CornerRadii(3),null));
        this.setBackground(unselectedBG);

        this.setStyle("-fx-border-color: black;\n"+
                "fx-border-width: 1;\n");
        this.setText(name);
    }
    public String getIngredientName(){
        return this.ingredientName;
    }
    @Override
    public void select() {
        this.selected = true;
        setBackground(selectedBG);
    }

    @Override
    public void unselect() {
        this.selected = false;
        setBackground(unselectedBG);
    }
}
