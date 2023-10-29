package project.a_la_carte.prototype.recipe.maker.inventory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RecipeInteractiveModel {
    Map<Ingredient, Double> temporaryIngredientMap;
    List<RecipeInteractiveModelSubsciber> subsciberList;

    Recipe loadedRecipe;
    Map<Ingredient, Double> loadedRecipeSavedIngredientsMap;

    public RecipeInteractiveModel(){

        temporaryIngredientMap = new HashMap<Ingredient,Double>();
        subsciberList = new ArrayList<RecipeInteractiveModelSubsciber>();
        loadedRecipe = null;

    }

    public void setLoadedRecipe(Recipe loadedRecipe) {
        this.loadedRecipe = loadedRecipe;
        notifySubscribers();
        System.out.println("them subs notified");
    }

    public Recipe getLoadedRecipe() {
        return loadedRecipe;
    }

    public void setLoadedRecipeSavedIngredientsMap(Map<Ingredient, Double> loadedRecipeSavedIngredientsMap) {
        this.loadedRecipeSavedIngredientsMap = loadedRecipeSavedIngredientsMap;
    }
    public Map<Ingredient, Double> getLoadedRecipeSavedIngredientsMap() {
        return loadedRecipeSavedIngredientsMap;
    }

    public void addToMap(Ingredient ingredient, Double recipeQuantity){
        temporaryIngredientMap.put(ingredient,recipeQuantity);
        notifySubscribers();
    }
    public void notifySubscribers(){
        for(RecipeInteractiveModelSubsciber sub: subsciberList){
            sub.iModelChanged(temporaryIngredientMap,loadedRecipe);
        }
    }

    public void addSubscriber(RecipeInteractiveModelSubsciber sub){
        subsciberList.add(sub);
    }

    public Map<Ingredient, Double> getTemporaryIngredientMap() {
        return temporaryIngredientMap;
    }

    public void clearList(){
        temporaryIngredientMap.clear();
        notifySubscribers();
    }
}
