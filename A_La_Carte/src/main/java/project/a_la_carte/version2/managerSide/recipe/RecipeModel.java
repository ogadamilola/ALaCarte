package project.a_la_carte.version2.managerSide.recipe;

import project.a_la_carte.version2.classesObjects.*;
import project.a_la_carte.version2.interfaces.RecipeModelSubscriber;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class RecipeModel {
    RecipeListView recipeListView;
    RecipeMakerView recipeMakerView;

    List<Recipe> recipeList;

    List<RecipeModelSubscriber> subscriberList;

    public RecipeModel(){

        recipeList = new ArrayList<>();
        subscriberList = new ArrayList<>();

        HashMap<Ingredient,Double> spagMap = new HashMap<>();
        addNewOrUpdateRecipe("Spaghetti Meatball",15.00,"Spaghetti with meatballs and sauce","Put spag on plate",10,spagMap);

        HashMap<Ingredient,Double> burgMap = new HashMap<>();
        addNewOrUpdateRecipe("Cheese burger",24.00,"Burger with cheese and lettuce","Assemble burger",12,burgMap);

        notifySubscribers();
    }
    public void addNewOrUpdateRecipe(String name, double price, String desc, String instruction, double prepTime, HashMap<Ingredient, Double> ingredientMap){

        for(Recipe r: recipeList){
            if(r.getName().equals(name)){
                r.setPrice((float) price);
                r.setDescription(desc);
                r.setPrepInstruction(instruction);
                r.setPrepTime((float) prepTime);
                r.setRecipeIngredients(ingredientMap);//update the map to the new given map
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
    }

    public void deleteRecipe(Recipe recipe) {
        recipeList.remove(recipe);
        notifySubscribers();
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

    public List<Recipe> getRecipeList(){
        return this.recipeList;
    }



}
