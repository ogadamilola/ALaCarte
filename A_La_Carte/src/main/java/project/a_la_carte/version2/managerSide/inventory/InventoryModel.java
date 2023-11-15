package project.a_la_carte.version2.managerSide.inventory;

import project.a_la_carte.version2.classesObjects.Ingredient;
import project.a_la_carte.version2.interfaces.InventorySubscriber;

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
    private Map<Ingredient, Double> ingredientInventory;

    private List<InventorySubscriber> subscriberList;
    private Ingredient loadedIngredient;
    public InventoryModel(){

        ingredientInventory = new HashMap<>();
        subscriberList = new ArrayList<>();
        loadedIngredient = null;

        //this is temporary
        addIngredient("Burger Patty", 30, Ingredient.IngredientType.Proteins, Ingredient.MeasurementUnit.Count,false);
        addIngredient("Burger Bun", 50, Ingredient.IngredientType.Grains, Ingredient.MeasurementUnit.Count,false);
        addIngredient("Cheese", 60, Ingredient.IngredientType.Dairy, Ingredient.MeasurementUnit.Count,false);
        addIngredient("Lettuce", 10, Ingredient.IngredientType.Vegetable, Ingredient.MeasurementUnit.Pounds,false);
        addIngredient("Mayo", 10, Ingredient.IngredientType.Sauce, Ingredient.MeasurementUnit.Pounds,false);
        addIngredient("Pepsi", 10, Ingredient.IngredientType.Other, Ingredient.MeasurementUnit.Pounds,false);
        addIngredient("Shrimp", 50, Ingredient.IngredientType.Proteins, Ingredient.MeasurementUnit.Pounds, true);
        notifySubs();

    }

    public void setLoadedIngredient(Ingredient loadedIngredient) {
        this.loadedIngredient = loadedIngredient;
        notifySubs();
    }
    public Map<Ingredient, Double> getIngredientMap(){
        return ingredientInventory;
    }

    public void addIngredient(String name, double quantity, Ingredient.IngredientType type, Ingredient.MeasurementUnit unit, boolean allergen){

        Ingredient theIngredient =  new Ingredient(name);
        theIngredient.setIngredientType(type);
        theIngredient.setMeasurementUnit(unit);
        theIngredient.setCommonAllergen(allergen);

        //TODO need a more obvious way to add quantity, maybe a update ingredient button
        //if they key already exists
        if(ingredientInventory.containsKey(theIngredient)) {
            throw new IllegalArgumentException("Item already exists, can't add a duplicate");
        }
        else{
            ingredientInventory.put(theIngredient, quantity);
        }
        notifySubs();

    }

    /**
     * subtract the quantity from inventory
     * @param ingredient    the key ingredient
     * @param quantity      the quantity to subtract
     */
    public void removeQuantity(Ingredient ingredient, double quantity){

        double currentStock = ingredientInventory.get(ingredient);
        ingredientInventory.put(ingredient,currentStock -quantity);
        notifySubs();
    }


    /**
     * Update the item with the new values put in the fields, if the item doesnt exist throw exception
     * @param quantity
     * @param type
     * @param unit
     * @param allergen
     */
    public void updateItem(Ingredient ingredient, double quantity, Ingredient.IngredientType type, Ingredient.MeasurementUnit unit, boolean allergen){
        try{

            ingredient.setIngredientType(type);
            ingredient.setMeasurementUnit(unit);
            ingredient.setCommonAllergen(allergen);
            ingredientInventory.put(ingredient,quantity);
            notifySubs();
        }
        catch (IllegalArgumentException e){
            System.out.println("Ingredient does not exist, can not update");
        }


    }

    public void deleteItem(Ingredient ingredient){
        try {
            ingredientInventory.remove(ingredient);
            notifySubs();
        }
        catch (IllegalArgumentException e){
            System.out.println("Ingredient does not exist, can not delete");
        }
    }


    //TODO loadDatabase() or save() method of some sort

    public void addSub(InventorySubscriber sub){
        subscriberList.add(sub);
    }

    public void notifySubs(){
        for(InventorySubscriber sub : subscriberList){
            sub.modelChanged(ingredientInventory,loadedIngredient);
        }
    }

    //setView not needed with subscribers



}
