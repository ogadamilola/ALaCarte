package project.a_la_carte.prototype.recipe.maker.inventory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MenuItemInteractiveModel {
    Map<Recipe, Double> temporaryRecipeMap;
    List<MenuItemInteractiveModelSubscriber> subscriberList;

    public MenuItemInteractiveModel(){
        temporaryRecipeMap = new HashMap<Recipe, Double>();
        subscriberList = new ArrayList<MenuItemInteractiveModelSubscriber>();
    }

    public void addToMap(Recipe Recipe, Double menuItemQuantity){
        temporaryRecipeMap.put(Recipe,menuItemQuantity);
        notifySubscribers();
    }
    public void notifySubscribers(){
        for(MenuItemInteractiveModelSubscriber sub: subscriberList){
            sub.iModelChanged(temporaryRecipeMap);
        }
    }

    public void addSubscriber(MenuItemInteractiveModelSubscriber sub){
        subscriberList.add(sub);
    }

    public Map<Recipe, Double> getTemporaryRecipeMap() {
        return temporaryRecipeMap;
    }

    public void clearList(){
        temporaryRecipeMap.clear();
        notifySubscribers();
    }


}
