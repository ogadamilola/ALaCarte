package project.a_la_carte.version2.serverSide.tableSystem;


import project.a_la_carte.version2.classesObjects.MenuFoodItem;
import project.a_la_carte.version2.classesObjects.Order;

import java.util.ArrayList;

public class Table {
    boolean status;
    int occupants;
    Order order;
    ArrayList<Order> orderList;
    Bill bill;

    int number;

    private String notes = "";
    private String orders = "";

    public Table(){
        this.number = 1;
        this.order = null;
        this.orderList = new ArrayList<>();
        this.status = false;
        this.occupants = 0;
    }

    public boolean getStatus(){
        return this.status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

     public Bill getBill() {
        return bill;
    }

     public void setBill(Bill bill){
        this.bill = bill;
    }

    public boolean isStatus() {
        return status;
    }

    public int getOccupants() {
        return occupants;
    }

    public void setOccupants(int occupants) {
        this.occupants = occupants;
    }

    public Order getOrder() {
        if (this.order == null) {
            this.order = new Order(new ArrayList<MenuFoodItem>(), 0, 1);
        }
        return this.order;
    }
    public void addToOrderList(Order order){
        if (this.orderList == null){
            this.orderList = new ArrayList<Order>();
        }
        this.orderList.add(order);
    }
    public ArrayList<Order> getOrderList(){
        return this.orderList;
    }

    public void removeFromOrderList(){
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public int getNumber() {
        return this.number;
    }

    public void setNumber(int num){
        this.number = num;
    }

    public String toString() {
        StringBuilder ordersString = new StringBuilder();
        for (Order order : orderList) {
            ordersString.append(order.toString()).append("\n");
        }
        return "Orders:\n" + ordersString;
    }

    public void setOrderList(ArrayList<Order> orderList) {
        this.orderList = orderList;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getOrders() {
        return orders;
    }

    public void setOrders(String orders) {
        this.orders = orders;
    }
}
