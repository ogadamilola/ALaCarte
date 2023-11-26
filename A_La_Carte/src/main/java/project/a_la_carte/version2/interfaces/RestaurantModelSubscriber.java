package project.a_la_carte.version2.interfaces;

import javafx.collections.ObservableList;
import project.a_la_carte.version2.classesObjects.MenuFoodItemData;

import java.util.HashMap;

public interface RestaurantModelSubscriber {

    public void restaurantModelChanged(HashMap<String,Integer> menuItemMap, HashMap<String, Double> ingredientUsageMap, int totalOrders, float incomeToday);
}
