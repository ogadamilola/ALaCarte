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

    List<InventorySubscriber> subscriberList;
    public InventoryModel(){

            ingredientInventory = new HashMap<>();
            subscriberList = new ArrayList<>();
    }
    public Map<Ingredient, Double> getIngredientMap(){
        return ingredientInventory;
    }


    public void addIngredient(String name, double quantity, Ingredient.IngredientType type, Ingredient.MeasurementUnit unit){
        //addIngredient has been changed for the map
        Ingredient theIngredient =  new Ingredient(name);
        theIngredient.setIngredientType(type);
        theIngredient.setMeasurementUnit(unit);

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

    //TODO loadDatabase() or save() method of some sort


    //TODO work on this

    public void addSub(InventorySubscriber sub){
        subscriberList.add(sub);
    }

    public void notifySubs(){
        for(InventorySubscriber sub : subscriberList){
            sub.modelChanged(ingredientInventory);
        }

    }

    public void setView(InventoryView newView){
        this.inventoryView = newView;
    }


}
