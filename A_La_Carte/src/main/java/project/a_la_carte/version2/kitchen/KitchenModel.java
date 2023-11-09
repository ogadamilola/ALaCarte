package project.a_la_carte.version2.kitchen;

import project.a_la_carte.version2.kitchen.widgets.KitchenNotes;
import project.a_la_carte.version2.interfaces.KitchenViewsInterface;
import project.a_la_carte.version2.classesObjects.Order;

import java.util.ArrayList;
import java.util.List;

public class KitchenModel {
    KitchenView kitchenView;
    List<KitchenViewsInterface> subscribers;
    ArrayList<KitchenNotes> noteList;
    ArrayList<Order> orders;
    String sentAlert = "";
    public KitchenModel(){
        this.orders = new ArrayList<>();
        this.noteList = new ArrayList<>();
        this.subscribers = new ArrayList<>();
    }
    public void sendServerAlert(String alert){
        this.sentAlert = alert;
    }
    public String getSentAlert(){
        return this.sentAlert;
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
