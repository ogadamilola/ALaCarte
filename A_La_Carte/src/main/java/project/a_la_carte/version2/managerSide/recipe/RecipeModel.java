package project.a_la_carte.version2.managerSide.recipe;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.TypeAdapter;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import project.a_la_carte.version2.classesObjects.*;
import project.a_la_carte.version2.interfaces.*;
import project.a_la_carte.version2.managerSide.inventory.InventoryModel;
import project.a_la_carte.version2.menuItems.widgets.MenuItemRecipeButton;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RecipeModel {
    RecipeListView recipeListView;
    RecipeMakerView recipeMakerView;
    ArrayList<Recipe> recipeList;
    List<RecipeModelSubscriber> subscriberList;
    private static final String FILE_PATH = "recipes.json";

    public RecipeModel(){
        InventoryModel model = new InventoryModel();

        //reading in json file
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(MenuItemRecipeButton.class, new ButtonAdapter())
                .create();
        try {
            FileReader reader = new FileReader(FILE_PATH);
            Type arrayListType = new TypeToken<ArrayList<Recipe>>(){}.getType();
            recipeList = gson.fromJson(reader, arrayListType);
            //catching case where file empty
            if (recipeList == null)
                throw new IOException();
        } catch (IOException e) {
            recipeList = new ArrayList<>();
        }

        subscriberList = new ArrayList<>();
        Map<String,Ingredient> ingredients = model.getIngredientMap();


        //setting buttons for all recipes loaded in
        for (Recipe r : recipeList) {
            r.setButton();
        }

        notifySubscribers();
    }
    public void addNewOrUpdateRecipe(String name, double price, String desc, String instruction, double prepTime, HashMap<String, Double> ingredientMap){

        for(Recipe r: recipeList){
            if(r.getName().equals(name)){
                r.setPrice((float) price);
                r.setDescription(desc);
                r.setPrepInstruction(instruction);
                r.setPrepTime((float) prepTime);
                r.setRecipeIngredients(ingredientMap);//update the map to the new given map
                saveData();
                return;//recipe updated no need for new one
            }
        }
        Recipe newRecipe = new Recipe(name);
        newRecipe.setPrice((float) price);
        newRecipe.setDescription(desc);
        newRecipe.setPrepInstruction(instruction);
        newRecipe.setPrepTime((float) prepTime);
        newRecipe.setRecipeIngredients(ingredientMap);
        recipeList.add(newRecipe);
        System.out.println(newRecipe);
        saveData();
    }



    public void deleteRecipe(Recipe recipe) {
        recipeList.remove(recipe);
        saveData();
        notifySubscribers();
    }

    public void saveData() {
        Gson gson = new GsonBuilder()
                .setPrettyPrinting()
                .registerTypeAdapter(MenuItemRecipeButton.class, new ButtonAdapter())
                .create();
        try {
            FileWriter writer = new FileWriter(new File(FILE_PATH));
            gson.toJson(recipeList, writer);
            writer.flush();
        } catch (IOException e) {
            System.err.println("Error saving menu items list");
            throw new RuntimeException(e);
        }
    }


    public void addSubscriber(RecipeModelSubscriber subscriber){
        subscriberList.add(subscriber);
    }

    public void notifySubscribers(){
        for(RecipeModelSubscriber subscriber : subscriberList){
            subscriber.RecipieModelChanged(recipeList);
        }
    }
    public void setRecipeListView(RecipeListView newView){
        this.recipeListView = newView;
    }
    public void setRecipeMakerView(RecipeMakerView newView){
        this.recipeMakerView = newView;
    }

    public ArrayList<Recipe> getRecipeList(){
        return this.recipeList;
    }

    /**
     * Adapter class for button attribute so that Gson can serialize it
     */
    public static class ButtonAdapter extends TypeAdapter<MenuItemRecipeButton> {
        @Override
        public void write(JsonWriter out, MenuItemRecipeButton button) throws IOException {out.nullValue();}
        @Override
        public MenuItemRecipeButton read(JsonReader in) {return null;}
    }

}
