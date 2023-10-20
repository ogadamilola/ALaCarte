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
    public List<Ingredient> getIngredientInventory() {
        return ingredientInventory;
    }


    public void addIngredient(String name, double quantity, String measurement){
        Ingredient myIngredient = new Ingredient(name,quantity);
        myIngredient.setMeasurement(myIngredient.stringToMeasurement(measurement));
        ingredientInventory.add(myIngredient);
        notifySubs();
        //TODO save date to be persistent
    }
    public void removeIngredient(Ingredient ingredient){
        ingredientInventory.remove(ingredient);
    }

    //TODO addQuantity() method
    //TODO subtractQuantity() method
    //TODO loadDatabase() method of some sort






    public void notifySubs(){
        //if we have more subs to Inventory model this will have to be a list of subs
        //and a foreach loop
        inventoryView.modelChanged(ingredientInventory);
    }

    public void setView(InventoryView newView){
        this.inventoryView = newView;
    }


}
