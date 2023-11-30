package project.a_la_carte.version2.classesObjects;

import project.a_la_carte.version2.interfaces.*;

import java.util.ArrayList;

public class Order {
    ArrayList<MenuFoodItem> menuItems;
    ArrayList<OrderClassesInterface> subscriber;
    int orderNum;
    Boolean completed = false;
    public Order(ArrayList<MenuFoodItem> items, int i){
        this.menuItems = items;
        this.orderNum = i;
        this.subscriber = new ArrayList<>();
    }
    public void addSubscriber(OrderClassesInterface view){
        this.subscriber.add(view);
        notifySubscribers();
    }
    public void notifySubscribers(){
        this.subscriber.forEach((OrderClassesInterface::modelChanged));
    }
    public void addItem(MenuFoodItem newItem){
        this.menuItems.add(newItem);
    }
    public void deleteItem(MenuFoodItem item){
        if (!menuItems.isEmpty()){
            this.menuItems.remove(item);
        }
    }
    public ArrayList<MenuFoodItem> getOrderList(){
        return this.menuItems;
    }
    public void orderFinished(){
        this.completed = true;
        notifySubscribers();
    }
    public Boolean isFinished(){
        return completed;
    }
    public int getOrderNum(){
        return this.orderNum;
    }

    public float getTotalPrice(){
        float totalPrice  = 0;
        for(MenuFoodItem item : menuItems){
            totalPrice += item.getPrice();
        }
        return totalPrice;
    }


    public void updateOrderFromString(String text) {
    }
}
