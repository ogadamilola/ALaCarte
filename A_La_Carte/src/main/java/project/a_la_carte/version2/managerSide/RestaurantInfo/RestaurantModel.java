package project.a_la_carte.version2.managerSide.RestaurantInfo;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import project.a_la_carte.version2.classesObjects.*;
import project.a_la_carte.version2.interfaces.RestaurantModelSubscriber;
import project.a_la_carte.version2.menuItems.MenuItemModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Class for storing total orders for the day, ingredient usage, orders placed
 */
public class RestaurantModel {
    HashMap<String, Integer> menuItemMap; //track the amount of times a menu item is ordered
    ArrayList<MenuItemModel> menuItemList;//store the whole menu to view
    HashMap<String, Double> ingredientUsageMap;//track ingredient usage
    ArrayList<Ingredient> ingredientList;//list of ingredients to track usage
    ArrayList<RestaurantModelSubscriber> subscribers;
    int totalOrders; //total orders for the day
    float incomeToday;

    public RestaurantModel(){
        menuItemMap = new HashMap<>();
        menuItemList = new ArrayList<>();
        ingredientUsageMap = new HashMap<>();
        ingredientList = new ArrayList<>();
        subscribers = new ArrayList<>();
        totalOrders = 0;
        incomeToday = 0;
    }

    public int getTotalOrders() {
        return totalOrders;
    }
    public float getIncomeToday() {
        return incomeToday;
    }
    public ArrayList<MenuItemModel> getMenuItemList() {
        return menuItemList;
    }
    public HashMap<String, Integer> getMenuItemMap() {
        return menuItemMap;
    }
    public HashMap<String, Double> getIngredientUsageMap() {
        return ingredientUsageMap;
    }
    public ArrayList<Ingredient> getIngredientList() {
        return ingredientList;
    }
    public void handleOrderPunched(Order order){
        totalOrders += 1;
        if(order.getOrderList() != null){
            incomeToday += order.getTotalPrice();

            for (MenuFoodItem item : order.getOrderList()){
                if(menuItemMap.containsKey(item.getName())){
                    menuItemMap.compute(item.getName(),(k,v) -> v +1);
                    System.out.println("1");
                } else{
                    menuItemMap.put(item.getName(),1);
                    System.out.println("2");
                }
                handleIngredientUsage(item);
            }
        } //TODO handle case where order list is null;
        System.out.println(menuItemMap);
        notifySubs();
    }

    public void handleIngredientUsage(MenuFoodItem item){
        if(item.getMenuItemRecipes() !=  null){
            for(Recipe recipe : item.getMenuItemRecipes()){
                if(recipe.getRecipeIngredients() != null){

                } else {
                    System.out.println(recipe.getName() + " Ingredient map is empty");
                }
            }
        } else {
            System.out.println(item.getName() + " recipe list is empty");
        }
    }

    public void addSubscriber(RestaurantModelSubscriber sub){
        subscribers.add(sub);
    }
    public void notifySubs(){
        for(RestaurantModelSubscriber sub : subscribers){
            sub.restaurantModelChanged(menuItemMap,ingredientUsageMap,totalOrders,incomeToday);
        }

    }

    public ObservableList<MenuFoodItemData> getMenuObservableList(){
        ObservableList<MenuFoodItemData> data = FXCollections.observableArrayList();
        for(Map.Entry<String, Integer> entry : menuItemMap.entrySet()){
            MenuFoodItemData theData = new MenuFoodItemData(entry.getKey(), entry.getValue());
            data.add(theData);

        }

        return data;
    }
}
