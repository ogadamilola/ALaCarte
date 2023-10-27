package project.a_la_carte.prototype.recipe.maker.inventory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MenuItemModel {
    MenuItemListView menuItemListView;
    MenuItemMakerView menuItemMakerView;
    List<MenuFoodItem> menuItemsList;
    List<MenuItemModelSubscriber> subscriberList;

    public MenuItemModel(){
        menuItemsList = new ArrayList<>();
        subscriberList = new ArrayList<>();
    }

    public void addNewMenuItem(String name, String desc, HashMap<Recipe, Double> recipeHashMap) {
        MenuFoodItem menuFoodItem = new MenuFoodItem(name);
        menuFoodItem.setDescription(desc);

        for(Map.Entry<Recipe,Double> entry : recipeHashMap.entrySet()){
            menuFoodItem.addMenuItemRecipes(entry.getKey(), entry.getValue());
        }
        menuItemsList.add(menuFoodItem);

        System.out.println(menuFoodItem);

    }

    public void addSubscriber(MenuItemModelSubscriber subscriber){
        subscriberList.add(subscriber);
    }

    public void notifySubscribers(){
        for(MenuItemModelSubscriber subscriber : subscriberList){
            subscriber.MenuItemModelChanged(menuItemsList);
        }
    }

    public void setMenuItemListView(MenuItemListView newView) {
        this.menuItemListView = newView;
    }

    public void setMenuItemMakerView(MenuItemMakerView newView) {
        this.menuItemMakerView = newView;
    }
}
