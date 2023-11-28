package project.a_la_carte.version2.managerSide.RestaurantInfo;

import project.a_la_carte.version2.classesObjects.Ingredient;
import project.a_la_carte.version2.classesObjects.MenuFoodItem;
import project.a_la_carte.version2.classesObjects.Order;
import project.a_la_carte.version2.classesObjects.Recipe;
import project.a_la_carte.version2.interfaces.RestaurantModelSubscriber;
import project.a_la_carte.version2.menuItems.MenuItemModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class RestaurantDay {

    private String date;
    private HashMap<String, Double> menuItemMap; //track the amount of times a menu item is ordered
    private HashMap<String, Double> ingredientUsageMap;//track ingredient usage
    private int totalOrders; //total orders for the day
    private float incomeToday;


    public RestaurantDay(){
        this.date = "";
        this.ingredientUsageMap = new HashMap<>();
        this.menuItemMap = new HashMap<>();
        totalOrders = 0;
        incomeToday = 0;
    }

    public void handleOrderPunched(Order order){
        totalOrders +=1;
        incomeToday += order.getTotalPrice();

        for(MenuFoodItem item : order.getOrderList()){
            if(menuItemMap.containsKey(item.getName())){ //if it already exists just add the new quantity to existing
                menuItemMap.compute(item.getName(),(k,v) -> v +1);
            } else{
                menuItemMap.put(item.getName(),1.0);//if not add new
            }
            handleIngredientUsage(item);
        }
    }

    public void handleIngredientUsage(MenuFoodItem item){
        if(item.getMenuItemRecipes() !=  null){
            for(Recipe recipe : item.getMenuItemRecipes()){
                //FIXME will have to change to string

                for (Map.Entry<String,Double> entry : recipe.getRecipeIngredients().entrySet()){

                    if(ingredientUsageMap.containsKey(entry.getKey())){
                        ingredientUsageMap.compute(entry.getKey(),(k,v) -> v +entry.getValue());
                    } else{
                        ingredientUsageMap.put(entry.getKey(),entry.getValue());
                    }
                }

            }
        } else {
            System.out.println(item.getName() + " recipe list is empty");
        }
    }

    public float getIncomeToday() {
        return incomeToday;
    }
    public int getTotalOrders() {
        return totalOrders;
    }
    public HashMap<String, Double> getIngredientUsageMap() {
        return ingredientUsageMap;
    }
    public HashMap<String, Double> getMenuItemMap() {
        return menuItemMap;
    }
    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setIncomeToday(float incomeToday) {
        this.incomeToday = incomeToday;
    }

    public void setIngredientUsageMap(HashMap<String, Double> ingredientUsageMap) {
        this.ingredientUsageMap = ingredientUsageMap;
    }

    public void setMenuItemMap(HashMap<String, Double> menuItemMap) {
        this.menuItemMap = menuItemMap;
    }

    public void setTotalOrders(int totalOrders) {
        this.totalOrders = totalOrders;
    }

}
