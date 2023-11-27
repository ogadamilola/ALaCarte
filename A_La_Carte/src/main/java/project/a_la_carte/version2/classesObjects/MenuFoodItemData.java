package project.a_la_carte.version2.classesObjects;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class MenuFoodItemData {

    /*
    This class only stores the name for displaying the menuItem not the menuItem itself
     */

    private String menuItemName;
    private Double quantity;

    public MenuFoodItemData(String name, Double quantity){
        this.menuItemName = name;
        this.quantity = quantity;
    }

    public SimpleStringProperty nameProperty(){
        return new SimpleStringProperty(menuItemName);
    }
    public SimpleDoubleProperty quantityProperty(){
        return new SimpleDoubleProperty(quantity);
    }
}