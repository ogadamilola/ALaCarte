package project.a_la_carte.version2.classesObjects;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class MenuFoodItemData {

    /*
    This class only stores the name for displaying the menuItem not the menuItem itself
     */

    private String menuItemName;
    private Integer quantity;

    public MenuFoodItemData(String name, Integer quantity){
        this.menuItemName = name;
        this.quantity = quantity;
    }

    public SimpleStringProperty nameProperty(){
        return new SimpleStringProperty(menuItemName);
    }
    public SimpleIntegerProperty quantityProperty(){
        return new SimpleIntegerProperty(quantity);
    }
}