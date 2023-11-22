package project.a_la_carte.version2.managerSide.OrderTracking;


import project.a_la_carte.version2.classesObjects.Ingredient;
import project.a_la_carte.version2.menuItems.MenuItemModel;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Class for storing total orders for the day, ingredient usage, orders placed
 */
public class OrderModel {
    HashMap<String, Integer> menuItemMap;
    ArrayList<MenuItemModel> menuItemList;

    HashMap<String, Double> ingredientUsageMap;
    ArrayList<Ingredient> ingredientList;
    int totalOrders;

    OrderModel(){
        menuItemMap = new HashMap<>();
        menuItemList = new ArrayList<>();
        ingredientUsageMap = new HashMap<>();
        ingredientList = new ArrayList<>();
        totalOrders = 0;
    }

}
