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
    private Map<String, Double> inventoryMap;
    private Map<String, Ingredient> ingredientMap;
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
            Type ingredientMapType = new TypeToken<Map<String, Ingredient>>(){}.getType();
            Type mapType = new TypeToken<Map<String, Double>>(){}.getType();

            ingredientMap = gson.fromJson(ingReader, ingredientMapType);
            inventoryMap = gson.fromJson(invReader, mapType);
            //catching case where files empty
            if (ingredientMap == null || inventoryMap == null)
                throw new IOException();
        } catch (IOException e) {
            inventoryMap = new HashMap<>();
            ingredientMap = new HashMap<>();
        }

        subscriberList = new ArrayList<>();
        loadedIngredient = null;

        notifySubs();

    }

    public void setLoadedIngredient(Ingredient loadedIngredient) {
        this.loadedIngredient = loadedIngredient;
        notifySubs();
    }
    public Map<String, Double> getInventoryMap(){
        return inventoryMap;
    }

    public void addIngredient(String name, double quantity, Ingredient.IngredientType type, Ingredient.MeasurementUnit unit, boolean allergen,float pricePerUnit,double reorderPoint){

        Ingredient theIngredient =  new Ingredient();
        theIngredient.setName(name);
        theIngredient.setIngredientType(type);
        theIngredient.setMeasurementUnit(unit);
        theIngredient.setCommonAllergen(allergen);
        theIngredient.setPricePerUnit(pricePerUnit);
        theIngredient.setReorderPoint(reorderPoint);

        ingredientMap.put(name,theIngredient);

        //if they key already exists
        if(inventoryMap.containsKey(theIngredient.getName())) {
            throw new IllegalArgumentException("Item already exists, can't add a duplicate");
        }
        else{
            inventoryMap.put(theIngredient.getName(), quantity);
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

        double currentStock = inventoryMap.get(ingredient.getName());
        inventoryMap.put(ingredient.getName(),currentStock - quantity);
        notifySubs();
        saveData();
    }

    public Ingredient getIngredientFromList(String ingredientName){
        try{
            return ingredientMap.get(ingredientName);
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

            inventoryMap.put(ingredient.getName(),quantity);
            ingredientMap.put(ingredient.getName(),ingredient);
            notifySubs();
        }
        catch (IllegalArgumentException e){
            System.out.println("Ingredient does not exist, can not update");
        }
        saveData();
    }

    public void deleteItem(Ingredient ingredient){
        try {
            inventoryMap.remove(ingredient.getName());
            ingredientMap.remove(ingredient);
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
            gson.toJson(ingredientMap, ingWriter);
            gson.toJson(inventoryMap, invWriter);
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
            sub.modelChanged(inventoryMap,loadedIngredient, ingredientMap);
        }
    }

    public Map<String,Ingredient> getIngredientMap() {
        return ingredientMap;
    }
    //setView not needed with subscribers



}
