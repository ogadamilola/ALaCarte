package project.a_la_carte.prototype.recipe.maker.inventory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Model for Inventory
 */
public class InventoryModel {

    private InventoryView inventoryView;

    //ingredientInventory is now a hashmap,
    Map<Ingredient, Double> ingredientInventory;
    public InventoryModel(){

            ingredientInventory = new HashMap<>();
    }
    public Map<Ingredient, Double> getIngredientMap(){
        return ingredientInventory;
    }


    public void addIngredient(String name, double quantity, Ingredient.IngredientType type){
        //addIngredient has been changed for the map
        Ingredient theIngredient =  new Ingredient(name);
        theIngredient.setIngredientType(type);

        //if they key already exists
        if(ingredientInventory.containsKey(theIngredient)) {
            //update the value to new amount
            Double currentAmount = ingredientInventory.get(theIngredient);
            ingredientInventory.put(theIngredient,currentAmount + quantity);
        }
        else{
            ingredientInventory.put(theIngredient, quantity);
        }
        notifySubs();
    }

    public void removeQuantity(Ingredient ingredient, double quantity){

        double currentStock = ingredientInventory.get(ingredient);
        ingredientInventory.put(ingredient,currentStock -quantity);
        notifySubs();
    }

    //TODO addQuantity() method

    //TODO loadDatabase() method of some sort


    //TODO work on this
    public void notifySubs(){
        //will need to change if we add more inventory views
        inventoryView.modelChanged(ingredientInventory);
    }

    public void setView(InventoryView newView){
        this.inventoryView = newView;
    }


}
