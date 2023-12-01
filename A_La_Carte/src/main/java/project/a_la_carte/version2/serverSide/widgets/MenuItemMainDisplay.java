package project.a_la_carte.version2.serverSide.widgets;

import javafx.scene.control.Button;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import project.a_la_carte.version2.interfaces.MenuItemWidget;

/**
 * Button container for MenuItems in MenuView
 */
public class MenuItemMainDisplay extends Button implements MenuItemWidget {
    Background selectedBG;
    Background unselectedBG;
    public MenuItemMainDisplay(String name){
        this.setPrefSize(140,120);
        //If this menu item is selected, it will be highlighted
        selectedBG = new Background(new BackgroundFill(Color.LIGHTGRAY,new CornerRadii(20),null));
        //If the button is unselected, leave it as it
        unselectedBG = new Background(new BackgroundFill(Color.LIGHTSALMON,new CornerRadii(20),null));
        this.setBackground(unselectedBG);

        this.setStyle("-fx-border-color: black;\n"+
                "fx-border-width: 1;\n"+"-fx-border-radius: 20;\n");
        this.setText(name);
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
