package project.a_la_carte.prototype.server.side;

import project.a_la_carte.prototype.menu.items.MenuFoodItem;
import project.a_la_carte.prototype.recipe.maker.inventory.Recipe;

import java.util.ArrayList;

public class Order {
    ArrayList<MenuFoodItem> menuItems;
    int orderNum;
    int totalItems;
    public Order(ArrayList<MenuFoodItem> items, int i){
        this.menuItems = items;
        this.orderNum = i;
        this.totalItems = 0;
    }

    public void addItem(MenuFoodItem newItem){
        this.menuItems.add(newItem);
    }
    public void deleteItem(MenuFoodItem item){
        if (!menuItems.isEmpty() && this.totalItems != 0){
            this.menuItems.remove(item);
            this.totalItems -= 1;
        }
    }
    public ArrayList<MenuFoodItem> getOrderList(){
        return this.menuItems;
    }
    public void completedSingleOrder(){
        this.totalItems -= 1;
    }
    public int getOrderNum(){
        return this.orderNum;
    }
}
