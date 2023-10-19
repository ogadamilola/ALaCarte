package project.a_la_carte.prototype.recipe.maker.inventory;

import java.util.ArrayList;
import java.util.List;

/**
 * Model for Inventory
 */
public class InventoryModel {
    InventoryView inventoryView;

    //added the InventoryList
    List<Ingredient> ingredientInventory;
    public InventoryModel(){

        ingredientInventory = new ArrayList<Ingredient>();
    }
    public void setView(InventoryView newView){
        this.inventoryView = newView;
    }
}
