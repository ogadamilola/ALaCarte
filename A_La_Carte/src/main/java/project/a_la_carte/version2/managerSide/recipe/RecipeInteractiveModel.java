package project.a_la_carte.version2.managerSide.recipe;

import project.a_la_carte.version2.classesObjects.*;
import project.a_la_carte.version2.classesObjects.Recipe;
import project.a_la_carte.version2.interfaces.RecipeInteractiveModelSubsciber;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RecipeInteractiveModel {
    Map<String, Double> temporaryIngredientMap;
    List<RecipeInteractiveModelSubsciber> subscriberList;

    Recipe loadedRecipe;
    Map<String, Double> loadedRecipeSavedIngredientsMap;

    boolean isCreating;

    public RecipeInteractiveModel(){

        loadedRecipeSavedIngredientsMap = new HashMap<>();
        temporaryIngredientMap = new HashMap<String,Double>();
        subscriberList = new ArrayList<RecipeInteractiveModelSubsciber>();
        loadedRecipe = null;
        isCreating = false;

    }

    public void clearRecipeIModel(){
        temporaryIngredientMap.clear();
        loadedRecipe = null;
        notifySubscribers();
    }

    public void setLoadedRecipe(Recipe loadedRecipe) {
        if(loadedRecipe == null){
            this.loadedRecipe = null;
            setLoadedRecipeSavedIngredientsMap(null);
        } else {
            this.loadedRecipe = loadedRecipe;
            setLoadedRecipeSavedIngredientsMap(this.loadedRecipe.getRecipeIngredients());
            updateTempMap();
        }
        notifySubscribers();
    }

    public void updateTempMap(){
        for(Map.Entry<String,Double> entry : loadedRecipeSavedIngredientsMap.entrySet()){
            temporaryIngredientMap.put(entry.getKey(),entry.getValue());
        }
        notifySubscribers();
    }

    public void setCreating(boolean creating) {
        isCreating = creating;
    }
    public boolean isCreating(){
        return isCreating;
    }

    public Recipe getLoadedRecipe() {
        return loadedRecipe;
    }

    public void setLoadedRecipeSavedIngredientsMap(Map<String, Double> loadedRecipeSavedIngredientsMap) {
        this.loadedRecipeSavedIngredientsMap = loadedRecipeSavedIngredientsMap;
        /*for(Map.Entry<String,Double> entry : loadedRecipeSavedIngredientsMap.entrySet()){
            this.loadedRecipeSavedIngredientsMap.put(entry.getKey(),entry.getValue());
        }*/
        notifySubscribers();
    }

    public Map<String, Double> getLoadedRecipeSavedIngredientsMap() {
        return loadedRecipeSavedIngredientsMap;
    }

    public void addToTempMap(String ingredientName, Double recipeQuantity){
            temporaryIngredientMap.put(ingredientName, recipeQuantity);
            notifySubscribers();

    }

    public void removeFromTempMap(String ingredient){
        if(loadedRecipe != null){
            temporaryIngredientMap.remove(ingredient);
            notifySubscribers();
        }
    }
    public void notifySubscribers(){
        for(RecipeInteractiveModelSubsciber sub: subscriberList){
            sub.iModelChanged(temporaryIngredientMap,loadedRecipe,isCreating);
        }
    }

    public void addSubscriber(RecipeInteractiveModelSubsciber sub){
        subscriberList.add(sub);
    }

    public Map<String, Double> getTemporaryIngredientMap() {
        return temporaryIngredientMap;
    }

    public void clearList(){
        temporaryIngredientMap.clear();
        notifySubscribers();
    }
}
