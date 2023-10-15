package project.a_la_carte.prototype.recipe.maker.inventory;

/**
 * Model for Inventory
 */
public class InventoryModel {
    InventoryView inventoryView;
    //We can have a list of inventory/ingredients here
    //Just waiting until we have the class
    public InventoryModel(){

    }
    public void setView(InventoryView newView){
        this.inventoryView = newView;
    }
}
