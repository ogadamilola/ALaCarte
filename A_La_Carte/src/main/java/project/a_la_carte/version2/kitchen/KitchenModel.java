package project.a_la_carte.version2.kitchen;

import project.a_la_carte.version2.kitchen.widgets.KitchenNotes;
import project.a_la_carte.version2.interfaces.*;
import project.a_la_carte.version2.classesObjects.Order;

import java.util.ArrayList;
import java.util.List;

public class KitchenModel {
    KitchenView kitchenView;
    List<KitchenViewsInterface> subscribers;
    ArrayList<KitchenNotes> noteList;
    ArrayList<Order> activeOrders;
    ArrayList<Order> totalOrdersTracker;
    String sentAlert = "";
    public KitchenModel(){
        this.activeOrders = new ArrayList<>();
        this.noteList = new ArrayList<>();
        this.subscribers = new ArrayList<>();
        this.totalOrdersTracker = new ArrayList<>();
    }
    public void sendServerAlert(String alert){
        this.sentAlert = alert;
    }
    public String getSentAlert(){
        return this.sentAlert;
    }
    public void addOrder(Order order){
        this.activeOrders.add(order);
        this.totalOrdersTracker.add(order);
        notifySubscribers();
    }
    public void deleteOrder(Order order){
        this.activeOrders.remove(order);
        notifySubscribers();
    }
    public void refundOrder(Order order){
        this.totalOrdersTracker.remove(order);
        if (!order.isFinished()){
            order.orderFinished();
        }

        notifySubscribers();
    }

    public ArrayList<Order> getTotalOrders(){return this.totalOrdersTracker;}
    public ArrayList<Order> getActiveOrders(){
        return this.activeOrders;
    }
    public void addNote(String message){
        KitchenNotes newNote = new KitchenNotes(message,this);

        noteList.add(newNote);
        notifySubscribers();
    }
    public void deleteNote(KitchenNotes note){
        this.getNoteList().remove(note);
        notifySubscribers();
    }
    public ArrayList<KitchenNotes> getNoteList(){
        return this.noteList;
    }
    public void clearNotes(){
        this.noteList = new ArrayList<>();
        notifySubscribers();
    }
    public void addSubscribers(KitchenViewsInterface view){
        this.subscribers.add(view);
    }
    public void notifySubscribers(){
        this.subscribers.forEach((KitchenViewsInterface::modelChanged));
    }
    public void setKitchenView(KitchenView newView){
        this.kitchenView = newView;
        this.addSubscribers(newView);
    }
}
