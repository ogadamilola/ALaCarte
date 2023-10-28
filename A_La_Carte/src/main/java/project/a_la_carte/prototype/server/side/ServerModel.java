package project.a_la_carte.prototype.server.side;


import project.a_la_carte.prototype.menu.items.MenuFoodItem;

import java.util.ArrayList;
import java.util.List;

public class ServerModel {
    MenuView menuView;
    NoteView noteView;
    List<ServerViewInterface> subscribers;
    CustomizeView customizeView;
    ViewOrder viewOrder;
    String noteMessage = "";
    int orderNumber;
    ArrayList<MenuFoodItem> menuItemList;
    MenuFoodItem selectedMenuItem;
    public ServerModel(){
        //Used when creating order and assigning their number
        this.orderNumber = 1;
        menuItemList = new ArrayList<>();
        this.subscribers = new ArrayList<>();
    }
    public void setNoteMessage(){
        this.noteMessage = this.noteView.getNote();
        this.noteView.noteText.clear();
        this.noteView.savedAlert.setText("SENT!");
    }
    public void setSelectedMenuItem(MenuFoodItem item){
        this.getMenuItemList().forEach((MenuFoodItem::unselectDisplay));

        selectedMenuItem = item;
        notifySubscribers();
    }
    public ArrayList<MenuFoodItem> getMenuItemList(){
        return this.menuItemList;
    }
    public void setMenuItemList(ArrayList<MenuFoodItem> newList){
        this.menuItemList = newList;
        notifySubscribers();
    }
    public String getNoteMessage(){
        return this.noteMessage;
    }
    public void setMenuView(MenuView newView){
        this.menuView = newView;
        this.addSubscriber(this.menuView);
    }
    public void setNoteView(NoteView newView){
        this.noteView = newView;
        this.addSubscriber(this.noteView);
    }
    public void addSubscriber(ServerViewInterface view){
        this.subscribers.add(view);
    }

    public void setCustomizeView(CustomizeView customizeView) {
        this.customizeView = customizeView;
        this.addSubscriber(this.customizeView);
    }

    public void setViewOrder(ViewOrder viewOrder) {
        this.viewOrder = viewOrder;
        this.addSubscriber(this.viewOrder);
    }
    public void notifySubscribers(){
        this.subscribers.forEach((ServerViewInterface::modelChanged));
    }
}
