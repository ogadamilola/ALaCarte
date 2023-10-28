package project.a_la_carte.prototype.kitchen.side;

import project.a_la_carte.prototype.server.side.Order;

import java.util.ArrayList;
import java.util.List;

public class KitchenModel {
    KitchenView kitchenView;
    List<KitchenViewsInterface> subscribers;
    ArrayList<KitchenNotes> noteList;
    ArrayList<Order> orders;
    public KitchenModel(){
        this.orders = new ArrayList<>();
        this.noteList = new ArrayList<>();
        this.subscribers = new ArrayList<>();
    }
    public void addOrder(Order order){
        this.orders.add(order);
        notifySubscribers();
    }
    public void deleteOrder(Order order){
        this.orders.remove(order);
        notifySubscribers();
    }
    public ArrayList<Order> getOrders(){
        return this.orders;
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
