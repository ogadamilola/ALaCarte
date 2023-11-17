package project.a_la_carte.version2.serverSide.tableSystem;


import project.a_la_carte.version2.classesObjects.Order;

public class Table {
    boolean status;
    int occupants;
    Order order;
    Bill bill;

    private static int lastTableNumber = 0;
    int number;

    public Table(){
        this.status = false; // true indicates occupancy, false indicates vacancy
        this.occupants = 0;
        this.number = ++lastTableNumber;
        this.order = new Order();
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
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public int getNumber() {
        return this.number;
    }
}
