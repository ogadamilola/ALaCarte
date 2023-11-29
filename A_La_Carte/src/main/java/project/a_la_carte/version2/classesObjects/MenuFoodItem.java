package project.a_la_carte.version2.classesObjects;

import project.a_la_carte.version2.menuItems.widgets.*;
import project.a_la_carte.version2.serverSide.widgets.*;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicBoolean;

/** NOTE ON CLASS NAME
 * This class could NOT be called "MenuItem" because there is already a class with this name.
 * in the Maven library
 * Maven: org.openjfx:javafx-controls:win:18.0.1 (javafx-controls-18.0.1-win.jar) javafx.scene.control MenuItem
 */
public class MenuFoodItem {
    private ArrayList<Recipe> menuItemRecipes;
    private String name;
    private String description;
    private float price = 0;
    MenuItemListButton menuItemListButton;
    MenuItemMainDisplay menuItemMainDisplay;
    Boolean selectedStatus;
    Boolean customized = false;
    String customizeOption = "";
    public MenuFoodItem(ArrayList<Recipe> listOfRecipes, String name, String desc) {
        this.name = name;
        this.description = desc;
        this.menuItemRecipes = listOfRecipes;
        this.menuItemListButton = new MenuItemListButton(name);
        this.menuItemMainDisplay = new MenuItemMainDisplay(name);
        selectedStatus = false;
    }
    public void setCustomizeOption(String change){
        this.customizeOption = change;
        this.customized = true;
    }
    public String getCustomize(){
        return this.customizeOption;
    }
    public Boolean isCustomized(){
        return this.customized;
    }
    public MenuItemListButton getButton(){return this.menuItemListButton;}
    public MenuItemMainDisplay getDisplay(){return this.menuItemMainDisplay;}
    public void selectDisplay(){
        this.selectedStatus = true;
        this.getDisplay().select();
    }
    public void unselectDisplay(){
        this.selectedStatus = false;
        this.getDisplay().unselect();
    }
    public Boolean getSelectedStatus(){return this.selectedStatus;}

    //For editing recipes in Menu Items
    public void selectedRecipe(){
        this.selectedStatus = true;
        this.getButton().select();
    }
    public void unselectRecipe(){
        this.selectedStatus = false;
        this.getButton().unselect();
    }

    public ArrayList<Recipe> getMenuItemRecipes() {
        return menuItemRecipes;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public String toString() {
        return "MenuItem{" +
                "menuItemRecipes" + menuItemRecipes +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                '}';
    }
}
