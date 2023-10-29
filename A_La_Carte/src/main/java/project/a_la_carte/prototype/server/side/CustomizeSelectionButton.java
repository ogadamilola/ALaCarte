package project.a_la_carte.prototype.server.side;

import javafx.scene.control.Button;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import project.a_la_carte.prototype.menu.items.MenuItemWidget;

public class CustomizeSelectionButton extends Button implements MenuItemWidget {
    Background selectedBG;
    Background unselectedBG;
    Boolean selected = false;
    String optionName;
    public CustomizeSelectionButton(String name){
        this.optionName = name;
        this.setPrefSize(200,70);
        //If this menu item is selected, it will be highlighted
        selectedBG = new Background(new BackgroundFill(Color.DARKRED,new CornerRadii(3),null));
        //If the button is unselected, leave it as it
        unselectedBG = new Background(new BackgroundFill(Color.RED,new CornerRadii(3),null));
        this.setBackground(unselectedBG);

        this.setStyle("-fx-border-color: black;\n"+
                "fx-border-width: 1;\n");
        this.setText(name);
    }
    public String getOptionName(){
        return this.optionName;
    }
    @Override
    public void select() {
        selected = true;
        setBackground(selectedBG);
    }

    @Override
    public void unselect() {
        selected = false;
        setBackground(unselectedBG);
    }
}
