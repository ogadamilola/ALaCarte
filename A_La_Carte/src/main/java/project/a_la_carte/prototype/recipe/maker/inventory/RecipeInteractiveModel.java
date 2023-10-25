package project.a_la_carte.prototype.recipe.maker.inventory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RecipeInteractiveModel {
    Map<Ingredient, Double> temporaryIngredientMap;
    List<RecipeInteractiveModelSubsciber> subsciberList;

    public RecipeInteractiveModel(){

        temporaryIngredientMap = new HashMap<Ingredient,Double>();
        subsciberList = new ArrayList<RecipeInteractiveModelSubsciber>();

    }

    public void addToMap(Ingredient ingredient, Double recipeQuantity){
        temporaryIngredientMap.put(ingredient,recipeQuantity);
        notifySubscribers();
    }
    public void notifySubscribers(){
        for(RecipeInteractiveModelSubsciber sub: subsciberList){
            sub.iModelChanged(temporaryIngredientMap);
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
