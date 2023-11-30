package project.a_la_carte.version2.kitchen;

import project.a_la_carte.version2.kitchen.widgets.KitchenNotes;
import project.a_la_carte.version2.interfaces.*;
import project.a_la_carte.version2.classesObjects.Order;

import java.util.ArrayList;
import java.util.List;

/**
 * The program's Kitchen Model
 */
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

    /**
     * Methods for sending notes to the Server side
     */
    public void sendServerAlert(String alert){
        this.sentAlert = alert;
    }
    public String getSentAlert(){
        return this.sentAlert;
    }

    /**
     * Method for adding an order to KitchenModel
     */
    public void addOrder(Order order){
        this.activeOrders.add(order);
        this.totalOrdersTracker.add(order);
        notifySubscribers();
    }
    /**
     * Method for deleting an order from the KitchenModel
     */
    public void deleteOrder(Order order){
        this.activeOrders.remove(order);
        notifySubscribers();
    }
    /**
     * Method for refunding an order from the KitchenModel
     */
    public void refundOrder(Order order){
        this.totalOrdersTracker.remove(order);
        if (!order.isFinished()){
            order.orderFinished();
        }

        notifySubscribers();
    }

    /**
     * Get method for all orders made in the KitchenModel
     */
    public ArrayList<Order> getTotalOrders(){return this.totalOrdersTracker;}
    /**
     * Get method for active orders in the KitchenModel
     */
    public ArrayList<Order> getActiveOrders(){
        return this.activeOrders;
    }

    /**
     * Method for adding a note to the KitchenModel
     */
    public void addNote(String message){
        KitchenNotes newNote = new KitchenNotes(message,this);

        noteList.add(newNote);
        notifySubscribers();
    }
    /**
     * Method for deleting a note in the KitchenModel
     */
    public void deleteNote(KitchenNotes note){
        this.getNoteList().remove(note);
        notifySubscribers();
    }

    /**
     * Get method for notes in the KitchenModel
     */
    public ArrayList<KitchenNotes> getNoteList(){
        return this.noteList;
    }

    /**
     * Method for deleting all notes in the KitchenModel
     */
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

    /**
     * Set method for KitchenModel's KitchenView
     */
    public void setKitchenView(KitchenView newView){
        this.kitchenView = newView;
        this.addSubscribers(newView);
    }
}
