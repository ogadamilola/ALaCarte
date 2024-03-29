package project.a_la_carte.version2.menuItems;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.TypeAdapter;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import project.a_la_carte.version2.classesObjects.*;
import project.a_la_carte.version2.interfaces.MenuItemModelSubscriber;
import project.a_la_carte.version2.managerSide.recipe.RecipeModel;
import project.a_la_carte.version2.menuItems.widgets.MenuItemListButton;
import project.a_la_carte.version2.menuItems.widgets.MenuItemRecipeButton;
import project.a_la_carte.version2.serverSide.widgets.CustomizeButton;
import project.a_la_carte.version2.serverSide.widgets.MenuItemMainDisplay;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * The program's MenuItem model
 */
public class MenuItemModel {
    ArrayList<MenuFoodItem> menuItemsList;
    List<MenuItemModelSubscriber> subscriberList;
    ArrayList<Recipe> recipeArrayList;
    ArrayList<Recipe> addedRecipes;
    MenuFoodItem selectedItem;
    Recipe selectedRecipe;
    Recipe selectedAddedRecipe;
    private final static String FILE_PATH = "menuItems.json";
    public MenuItemModel(){

        //reading in json file
        Gson gson = new GsonBuilder()
                .setPrettyPrinting()
                .registerTypeAdapter(MenuItemListButton.class, new ListButtonAdapter())
                .registerTypeAdapter(MenuItemMainDisplay.class, new MainDisplayAdapter())
                .registerTypeAdapter(MenuItemRecipeButton.class, new RecipeModel.ButtonAdapter())
                .registerTypeAdapter(CustomizeButton.class, new CustomizeAdapter())
                .create();
        try {
            FileReader reader = new FileReader(FILE_PATH);

            Type arrayListType = new TypeToken<ArrayList<MenuFoodItem>>(){}.getType();

            menuItemsList = gson.fromJson(reader, arrayListType);
            //catching case where file empty
            if (menuItemsList == null)
                throw new IOException();
        } catch (IOException e) {
            menuItemsList = new ArrayList<>();
        }

        //setting buttons & displays for all menu items loaded in
        for (MenuFoodItem i : menuItemsList) {
            i.setButton();
            i.setDisplay();
            //setting recipe buttons inside array to avoid exceptions
            for (Recipe r : i.getMenuItemRecipes()) {
                r.setButton();
            }
        }

        subscriberList = new ArrayList<>();
        addedRecipes = new ArrayList<>();
    }

    /**
     * Method for adding Recipes to the MenuItem
     */
    public void addRecipesToItem(Recipe recipe){
        Recipe newRecipe = new Recipe(recipe.getName());
        newRecipe.setDescription(recipe.getDescription());
        newRecipe.setPrice(recipe.getPrice());
        newRecipe.setPrepTime(recipe.getPrepTime());
        newRecipe.setPrepInstruction(recipe.getPrepInstruction());
        newRecipe.setRecipeIngredients(recipe.getRecipeIngredients());

        this.addedRecipes.add(newRecipe);
        notifySubscribers();
    }

    /**
     * Method for removing Recipes in the MenuItem
     */
    public void removeRecipeFromItem(Recipe recipe){
        this.addedRecipes.remove(recipe);
        notifySubscribers();
    }

    /**
     * Method for clearing all Recipes in the MenuItem
     */
    public void resetAddedRecipes(){
        this.addedRecipes = new ArrayList<>();
        notifySubscribers();
    }

    /**
     * Get method for the added Recipes in the MenuItem
     */
    public ArrayList<Recipe> getAddedRecipes(){
        return this.addedRecipes;
    }

    /**
     * Get methods for selected Recipes
     */
    public Recipe getSelectedAddedRecipe(){return this.selectedAddedRecipe;}
    public Recipe getSelectedRecipe(){
        return this.selectedRecipe;
    }

    /**
     * Method for selecting an Added recipe in the MenuItem
     */
    public void selectAddedRecipe(Recipe newRecipe){
        this.getRecipeArrayList().forEach((Recipe::unselectRecipe));
        this.getAddedRecipes().forEach((Recipe::unselectRecipe));

        this.selectedRecipe = null;
        this.selectedAddedRecipe = newRecipe;
        newRecipe.selectedRecipe();
    }

    /**
     * Method for setting the Recipes that is already added to the MenuItem
     */
    public void setSelectedAddedRecipe(ArrayList<Recipe> newList){
        this.addedRecipes = newList;
        notifySubscribers();
    }

    /**
     * Method for setting the Recipes that is in the database
     */
    public void setRecipeArrayList(ArrayList<Recipe> newList){
        this.recipeArrayList = newList;
        notifySubscribers();
    }

    /**
     * Get method for Recipe ArrayList in the Database
     */
    public ArrayList<Recipe> getRecipeArrayList(){
        return this.recipeArrayList;
    }

    /**
     * Method used for updating displays
     */
    public void selectRecipe(Recipe newRecipe){
        this.getRecipeArrayList().forEach((Recipe::unselectRecipe));
        this.getAddedRecipes().forEach((Recipe::unselectRecipe));

        this.selectedRecipe = newRecipe;
        this.selectedAddedRecipe = null;
        newRecipe.selectedRecipe();
    }

    /**
     * Method for adding a new MenuItem to the database
     */
    public void addNewMenuItem(MenuFoodItem menuFoodItem) {
        this.menuItemsList.add(menuFoodItem);
        notifySubscribers();
        saveData();
    }

    /**
     * Delete a MenuItem in the database
     */
    public void deleteMenuItem(MenuFoodItem item){
        this.menuItemsList.remove(item);
        menuItemsList.forEach((MenuFoodItem::unselectRecipe));

        this.selectedItem = null;
        notifySubscribers();
        saveData();
    }

    /**
     * Select MenuItem in the list
     */
    public void selectMenuItem(MenuFoodItem newItem){
        menuItemsList.forEach((MenuFoodItem::unselectRecipe));

        this.selectedItem = newItem;
        this.selectedItem.getButton().select();
        this.setSelectedAddedRecipe(selectedItem.getMenuItemRecipes());
        notifySubscribers();
    }

    /**
     * Get method for the selected MenuItem
     */
    public MenuFoodItem getSelectedItem(){return this.selectedItem;}
    public void addSubscriber(MenuItemModelSubscriber subscriber){
        subscriberList.add(subscriber);
    }

    public void notifySubscribers(){
        for(MenuItemModelSubscriber subscriber : subscriberList){
            subscriber.MenuItemModelChanged(menuItemsList);
        }
    }

    public void saveData() {
        Gson gson = new GsonBuilder()
                .setPrettyPrinting()
                .registerTypeAdapter(MenuItemListButton.class, new ListButtonAdapter())
                .registerTypeAdapter(MenuItemMainDisplay.class, new MainDisplayAdapter())
                .registerTypeAdapter(MenuItemRecipeButton.class, new RecipeModel.ButtonAdapter())
                .registerTypeAdapter(CustomizeButton.class, new CustomizeAdapter())
                .create();
        try {
            FileWriter writer = new FileWriter(new File(FILE_PATH));
            gson.toJson(menuItemsList, writer);
            writer.flush();
        } catch (IOException e) {
            System.err.println("Error saving menu items list");
            throw new RuntimeException(e);
        }
    }

    public ArrayList<MenuFoodItem> getMenuItemsList(){return this.menuItemsList;}

    /**
     * Adapter classes for button attributes so that Gson can serialize them
     */
    public static class ListButtonAdapter extends TypeAdapter<MenuItemListButton> {
        @Override
        public void write(JsonWriter out, MenuItemListButton button) throws IOException {out.nullValue();}
        @Override
        public MenuItemListButton read(JsonReader in) {return null;}
    }
    public static class MainDisplayAdapter extends TypeAdapter<MenuItemMainDisplay> {
        @Override
        public void write(JsonWriter out, MenuItemMainDisplay display) throws IOException {out.nullValue();}
        @Override
        public MenuItemMainDisplay read(JsonReader in) {return null;}
    }

    /**
     * Added for customize button
     */
    public static class CustomizeAdapter extends TypeAdapter<CustomizeButton> {
        @Override
        public void write(JsonWriter out, CustomizeButton customize) throws IOException {out.nullValue();}
        @Override
        public CustomizeButton read(JsonReader in) {return null;}
    }
}
