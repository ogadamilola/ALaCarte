package project.a_la_carte.version2.interfaces;

import javafx.collections.ObservableList;
import project.a_la_carte.version2.classesObjects.MenuFoodItemData;

import java.util.HashMap;

public interface RestaurantModelSubscriber {

    public void restaurantModelChanged(ObservableList<MenuFoodItemData> menuItemData, ObservableList<MenuFoodItemData> ingredientUsagedata, int totalOrders, float incomeToday);
}
