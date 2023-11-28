package project.a_la_carte.version2.managerSide.RestaurantInfo;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import project.a_la_carte.version2.classesObjects.*;
import project.a_la_carte.version2.interfaces.RestaurantModelSubscriber;
import project.a_la_carte.version2.menuItems.MenuItemModel;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Class for storing total orders for the day, ingredient usage, orders placed
 */
public class RestaurantModel {

    private static final String FILE_PATH = "restaurantDays.json";
    ArrayList<RestaurantModelSubscriber> subscribers;
    HashMap<String, RestaurantDay> restaurantDayMap;
    String dateToday;

    RestaurantDay loadedDay;

    public RestaurantModel(){
        try(FileReader reader = new FileReader(FILE_PATH)){
            Gson gson = new GsonBuilder().registerTypeAdapter(RestaurantDay.class, new RestaurantDayAdapter()).create();
            Type map = new TypeToken<HashMap<String,RestaurantDay>>(){}.getType();
            restaurantDayMap = gson.fromJson(reader, map);
        } catch (FileNotFoundException e) {
            restaurantDayMap = new HashMap<>();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        subscribers = new ArrayList<>();

        LocalDate currentDate = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        dateToday = currentDate.format(formatter);

        if(restaurantDayMap.containsKey(dateToday)){
            loadedDay = restaurantDayMap.get(dateToday);
        } else {
            startDay();
        }

        System.out.println(restaurantDayMap);
    }

    public void startDay(){

        RestaurantDay newDay = new RestaurantDay();
        newDay.setDate(dateToday);
        restaurantDayMap.put(dateToday,newDay);
        loadedDay = newDay;
        saveDayMap();
        notifySubs();
    }

    public void handleOrderPunched(Order order){
        loadedDay.handleOrderPunched(order);
        notifySubs();
        saveDayMap();
    }


    public void addSubscriber(RestaurantModelSubscriber sub){
        subscribers.add(sub);
    }
    public void notifySubs(){
        for(RestaurantModelSubscriber sub : subscribers){

            sub.restaurantModelChanged(getMenuObservableList(dateToday),getIngredientObservableList(dateToday), restaurantDayMap.get(dateToday));
        }

    }

    public ObservableList<MenuFoodItemData> getMenuObservableList(String date){
        RestaurantDay theDay = restaurantDayMap.get(date);
        ObservableList<MenuFoodItemData> data = FXCollections.observableArrayList();
        for(Map.Entry<String, Double> entry : theDay.getMenuItemMap().entrySet()){
            MenuFoodItemData theData = new MenuFoodItemData(entry.getKey(), entry.getValue());
            data.add(theData);

        }

        return data;
    }

    public ObservableList<MenuFoodItemData> getIngredientObservableList(String date){
        RestaurantDay theDay = restaurantDayMap.get(date);
        ObservableList<MenuFoodItemData> data = FXCollections.observableArrayList();
        for(Map.Entry<String, Double> entry :theDay.getIngredientUsageMap().entrySet()){
            MenuFoodItemData theData = new MenuFoodItemData(entry.getKey(), entry.getValue());
            data.add(theData);

        }

        return data;
    }

    public void setDateToday(String dateToday) {
        this.dateToday = dateToday;
    }

    public String getDateToday() {
        return dateToday;
    }

    public void setRestaurantDayMap(HashMap<String, RestaurantDay> restaurantDayMap) {
        this.restaurantDayMap = restaurantDayMap;
    }

    public HashMap<String, RestaurantDay> getRestaurantDayMap() {
        return restaurantDayMap;
    }

    public void saveDayMap(){
        try(FileWriter writer = new FileWriter(FILE_PATH)){
            Gson gson = new GsonBuilder()
                    .setPrettyPrinting()
                    .registerTypeAdapter(RestaurantDay.class, new RestaurantDayAdapter())
                    .create();
            gson.toJson(restaurantDayMap,writer);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
