package project.a_la_carte.version2.classesObjects;

import project.a_la_carte.version2.interfaces.*;

import java.util.ArrayList;

public class Order {
    ArrayList<MenuFoodItem> menuItems;
    ArrayList<OrderClassesInterface> subscriber;
    int orderNum;
    int numItems = 0;
    Boolean completed = false;

    public int tableNum;
    public Order(ArrayList<MenuFoodItem> items, int i, int tableNum){
        this.menuItems = items != null ? items : new ArrayList<>();
        this.orderNum = i;
        this.subscriber = new ArrayList<>();
        this.tableNum = tableNum;
    }
    public int getNumberOfItems(){
        numItems = menuItems.size();
        return numItems;
    }
    public void addSubscriber(OrderClassesInterface view){
        this.subscriber.add(view);
        notifySubscribers();
    }
    public void notifySubscribers(){
        this.subscriber.forEach((OrderClassesInterface::modelChanged));
    }
    public void addItem(MenuFoodItem newItem){
        if (newItem != null) {
            this.menuItems.add(newItem);
        };
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
    public int getTableNum(){return this.tableNum;}

    public void setTableNum(int num){
        this.tableNum = num;
    }


    public float getTotalPrice(){
        float totalPrice  = 0;
        for(MenuFoodItem item : menuItems){
            totalPrice += item.getPrice();
        }
        return totalPrice;
    }
    public void startTimers(){
        timerInformation = new OrderTimers(this);
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Order Number: ").append(orderNum)
                .append("\nCompleted: ").append(completed ? "Yes" : "No")
                .append("\nTotal Price: $").append(getTotalPrice())
                .append("\nItems:\n");

        for (MenuFoodItem item : menuItems) {
            stringBuilder.append(" - ").append(item.getName())
                    .append(" (Price: $").append(item.getPrice()).append(")\n");
        }

        return stringBuilder.toString();
    }

    public void setOrderNum(int nextInt) {
        this.orderNum = nextInt;
    }

    public void setFinished(boolean nextBoolean) {
        this.completed = nextBoolean;
    }

    public void setMenuItems(ArrayList<MenuFoodItem> items){
        this.menuItems = items;
    }
}
