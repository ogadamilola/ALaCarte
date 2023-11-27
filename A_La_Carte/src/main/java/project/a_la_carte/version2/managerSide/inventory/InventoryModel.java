package project.a_la_carte.version2.managerSide.inventory;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import project.a_la_carte.version2.classesObjects.*;
import project.a_la_carte.version2.interfaces.InventorySubscriber;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
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
    private Map<String, Double> ingredientInventory;
    private ArrayList<Ingredient> ingredientList;
    private static final String ING_FILE_PATH = "ingredients.json";
    private static final String INV_FILE_PATH = "inventory.json";

    private List<InventorySubscriber> subscriberList;
    private Ingredient loadedIngredient;
    public InventoryModel() {
        //reading in json files
        Gson gson = new Gson();
        try {
            FileReader ingReader = new FileReader(ING_FILE_PATH);
            FileReader invReader = new FileReader(INV_FILE_PATH);
            //setting up types for reading in json files
            Type arrayListType = new TypeToken<ArrayList<Ingredient>>(){}.getType();
            Type mapType = new TypeToken<Map<String, Double>>(){}.getType();

            ingredientList = gson.fromJson(ingReader, arrayListType);
            ingredientInventory = gson.fromJson(invReader, mapType);
            //catching case where files empty
            if (ingredientList == null || ingredientInventory == null)
                throw new IOException();
        } catch (IOException e) {
            ingredientInventory = new HashMap<>();
            ingredientList = new ArrayList<>();
        }

        subscriberList = new ArrayList<>();
        loadedIngredient = null;

        notifySubs();

    }

    public void setLoadedIngredient(Ingredient loadedIngredient) {
        this.loadedIngredient = loadedIngredient;
        notifySubs();
    }
    public Map<String, Double> getIngredientMap(){
        return ingredientInventory;
    }

    public void addIngredient(String name, double quantity, Ingredient.IngredientType type, Ingredient.MeasurementUnit unit, boolean allergen,float pricePerUnit,double reorderPoint){

        Ingredient theIngredient =  new Ingredient(name);
        theIngredient.setIngredientType(type);
        theIngredient.setMeasurementUnit(unit);
        theIngredient.setCommonAllergen(allergen);
        theIngredient.setPricePerUnit(pricePerUnit);
        theIngredient.setReorderPoint(reorderPoint);

        ingredientList.add(theIngredient);

        //if they key already exists
        if(ingredientInventory.containsKey(theIngredient.getName())) {
            throw new IllegalArgumentException("Item already exists, can't add a duplicate");
        }
        else{
            ingredientInventory.put(theIngredient.getName(), quantity);
        }
        notifySubs();
        saveData();
    }

    /**
     * subtract the quantity from inventory
     * @param ingredient    the key ingredient
     * @param quantity      the quantity to subtract
     */
    public void removeQuantity(Ingredient ingredient, double quantity){

        double currentStock = ingredientInventory.get(ingredient.getName());
        ingredientInventory.put(ingredient.getName(),currentStock - quantity);
        notifySubs();
        saveData();
    }

    public Ingredient getIngredientFromList(String ingredientName){
        try{
            for (Ingredient i : ingredientList) {
                if(i.getName().equals(ingredientName)){
                    return i;
                }
            }
        } catch (RuntimeException e){
            System.out.println("Ingredient not found");
        }
        return null;
    }

    /**
     * Update the item with the new values put in the fields, if the item doesnt exist throw exception
     * @param quantity
     * @param type
     * @param unit
     * @param allergen
     */
    public void updateItem(Ingredient ingredient, double quantity, Ingredient.IngredientType type, Ingredient.MeasurementUnit unit, boolean allergen,float pricePerUnit,double reorderPoint){
        try{

            ingredient.setIngredientType(type);
            ingredient.setMeasurementUnit(unit);
            ingredient.setCommonAllergen(allergen);
            ingredient.setPricePerUnit(pricePerUnit);
            ingredient.setReorderPoint(reorderPoint);
            ingredientInventory.put(ingredient.getName(),quantity);
            notifySubs();
        }
        catch (IllegalArgumentException e){
            System.out.println("Ingredient does not exist, can not update");
        }
        saveData();
    }

    public void deleteItem(Ingredient ingredient){
        try {
            ingredientInventory.remove(ingredient.getName());
            ingredientList.remove(ingredient);
            notifySubs();
        }
        catch (IllegalArgumentException e){
            System.out.println("Ingredient does not exist, can not delete");
        }
        saveData();
    }


    public void saveData() {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        try {
            FileWriter ingWriter = new FileWriter(ING_FILE_PATH);
            FileWriter invWriter = new FileWriter(INV_FILE_PATH);
            gson.toJson(ingredientList, ingWriter);
            gson.toJson(ingredientInventory, invWriter);
            ingWriter.flush();
            invWriter.flush();
        } catch (IOException e) {
            System.err.println("Error saving menu items list");
            throw new RuntimeException(e);
        }
    }

    public void addSub(InventorySubscriber sub){
        subscriberList.add(sub);
    }

    public void notifySubs(){
        for(InventorySubscriber sub : subscriberList){
            sub.modelChanged(ingredientInventory,loadedIngredient,ingredientList);
        }
    }

    public ArrayList<Ingredient> getIngredientList() {
        return ingredientList;
    }
    //setView not needed with subscribers



}
