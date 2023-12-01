package project.a_la_carte.version2.serverSide;

import project.a_la_carte.version2.WorkerView;
import project.a_la_carte.version2.interfaces.ServerViewInterface;
import project.a_la_carte.version2.classesObjects.*;
import project.a_la_carte.version2.serverSide.tableSystem.Table;
import project.a_la_carte.version2.serverSide.widgets.*;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

/**
 * Class for the program's Server Model
 */
public class ServerModel {
    List<ServerViewInterface> subscribers;
    int orderNumber;
    ArrayList<IngredientsCustomize> ingredientList;
    ArrayList<CustomizeSelectionButton> customizeButtons;
    ArrayList<MenuFoodItem> menuItemList;
    ArrayList<ServerNotes> notesArrayList;
    MenuFoodItem selectedMenuItem;
    MenuFoodItem selectedCustomizeItem;
    public ServerModel(){
        //Used when creating order and assigning their number
        this.orderNumber = 1;
        menuItemList = new ArrayList<>();
        this.subscribers = new ArrayList<>();
        this.ingredientList = new ArrayList<>();
        this.customizeButtons = new ArrayList<>();
        this.notesArrayList = new ArrayList<>();

        this.addOptionButton("No");
        this.addOptionButton("Extra");
        this.addOptionButton("Just");
    }

    /**
     * Method for adding a note sent from Kitchen side to the list of notes on ServerSide
     */
    public void addNote(String message){
        ServerNotes newNote = new ServerNotes(message,this);

        notesArrayList.add(newNote);
        notifySubscribers();
    }

    /**
     * Method for deleting a note in the ServerSide
     */
    public void deleteNote(ServerNotes note){
        this.getNoteList().remove(note);
        notifySubscribers();
    }

    /**
     * Get method for ServerModel's notes
     */
    public ArrayList<ServerNotes> getNoteList(){
        return this.notesArrayList;
    }

    /**
     * Method for deleting all of notes
     */
    public void clearNotes(){
        this.notesArrayList = new ArrayList<>();
        notifySubscribers();
    }

    /**
     * Get method for IngredientsCustomize ArrayList
     */
    public ArrayList<IngredientsCustomize> getIngredientList(){
        return this.ingredientList;
    }

    /**
     * Get method for CustomizeSelectionButton ArrayList
     */
    public ArrayList<CustomizeSelectionButton> getCustomizeButtons(){
        return this.customizeButtons;
    }

    /**
     * Method for adding an ingredient in the CustomizeView
     */
    public void addIngredient(String name){
        IngredientsCustomize newI = new IngredientsCustomize(name);
        ingredientList.add(newI);
    }

    /**
     * Method for adding an option in the CustomizeView
     */
    public void addOptionButton(String name){
        CustomizeSelectionButton newB = new CustomizeSelectionButton(name);
        customizeButtons.add(newB);
    }

    /**
     * Method for sending an order to the Kitchen
     */
    public void sendOrderToKitchen(){
        this.orderNumber += 1;

        notifySubscribers();
    }
    public Table sendOrderToTable(WorkerView view){
        return new Table(view.getMenuView().getCurrentOrder());
    }

    /**
     * Method for adding a temporary edit on the MenuItem
     */
    public void setCustomization(WorkerView view){
        if (this.selectedCustomizeItem != null){
            view.getMenuView().addToOrder(selectedCustomizeItem);
            selectedCustomizeItem = null;
        }
        else if(this.selectedMenuItem != null) {
            MenuFoodItem copy = new MenuFoodItem(this.getSelectedItem().getMenuItemRecipes()
                    , this.getSelectedItem().getName(),this.getSelectedItem().getDescription());
            copy.setPrice(view.getMenuView().getSelectedItem().getPrice());
            view.getMenuView().addToOrder(copy);
        }
        notifySubscribers();
    }

    /**
     * Adding edits to an Item selected to be customized
     */
    public void addCustomize(WorkerView view){
        if (this.selectedCustomizeItem == null && this.selectedMenuItem != null) {
            this.selectedCustomizeItem = new MenuFoodItem(this.getSelectedItem().getMenuItemRecipes()
                    , this.getSelectedItem().getName(), this.getSelectedItem().getDescription());
            selectedCustomizeItem.setPrice(view.getMenuView().getSelectedItem().getPrice());
        }
        if (!view.getCustomizeView().getSelectedOption().equals("") && !view.getCustomizeView().getSelectedIngredient().equals("")) {
            selectedCustomizeItem.setCustomizeOption(view.getCustomizeView().getSelectedOption() + " " + view.getCustomizeView().getSelectedIngredient());
        }
        notifySubscribers();
    }

    /**
     * Setting the Item selected to be customized
     */
    public void setSelectedCustomizeItem(MenuFoodItem newI){
        this.selectedCustomizeItem = newI;
        notifySubscribers();
    }

    public void unselectCustomizeItem(WorkerView view){
        this.selectedCustomizeItem = null;
    }

    public void unselectMenuItem(WorkerView view){
        if (view.getMenuView().getSelectedItem() != null) {
            view.getMenuView().menuFoodDisplayList.forEach(foodItem -> {
                foodItem.getDisplay().unselect();
            });
            view.getMenuView().unselectItem();
        } else if (this.selectedMenuItem != null) {
            this.selectedMenuItem = null;
        }
    }

    /**
     * Get method for Item selected to be customized
     */
    public MenuFoodItem getSelectedCustomizeItem(){
        return this.selectedCustomizeItem;
    }

    /**
     * Discard the edits put on the Item selected to be customized
     */
    public void discardChanges(){
        this.selectedCustomizeItem.resetCustomize();
        notifySubscribers();
    }

    /**
     * Method for updating button displays
     */
    public void unselectAll(WorkerView view){
        view.getCustomizeView().customizeSelectionButtonArrayList.forEach((CustomizeSelectionButton::unselect));
        view.getCustomizeView().ingredientsCustomizeArrayList.forEach((IngredientsCustomize::unselect));
        view.getCustomizeView().selectedIngredient = "";
        view.getCustomizeView().selectedOption = "";
    }

    /**
     * Method used in sending Notes
     */
    public void setNoteMessage(WorkerView view){
        view.getNoteView().noteText.clear();
        view.getNoteView().savedAlert.setText("SENT!");
    }

    /**
     * Used for updating NoteView
     */
    public void clearNoteAlert(NoteView view){
        view.savedAlert.setText("");
    }

    /**
     * Used for updating display of MenuItems
     */
    public void setSelectedMenuItem(MenuFoodItem item, ArrayList<MenuFoodItem> list){
        list.forEach((MenuFoodItem::unselectDisplay));

        selectedMenuItem = item;
        notifySubscribers();
    }

    /**
     * Used to check if a MenuItem is already in the ServerModel
     */
    public boolean containsMenuItem(MenuFoodItem item){
        AtomicReference<Boolean> check = new AtomicReference<>(false);
        this.menuItemList.forEach(foodItem -> {
            if (foodItem.getName().equals(item.getName()) && foodItem.getDescription().equals(item.getDescription())
            && foodItem.getPrice() == item.getPrice()){
                check.set(true);
            }
        });
        return check.get();
    }

    /**
     * Get method for ServerModel's selected MenuItem
     */
    public MenuFoodItem getSelectedItem(){
        return this.selectedMenuItem;
    }

    /**
     * Get method for ServerModel's ArrayList of MenuItems
     */
    public ArrayList<MenuFoodItem> getMenuItemList(){
        return this.menuItemList;
    }

    /**
     * Set method for ServerModel's ArrayList of MenuItems
     */
    public void setMenuItemList(ArrayList<MenuFoodItem> newList){
        this.menuItemList = newList;
        notifySubscribers();
    }

    /**
     * Set method for ServerModel's MenuView
     */
    public void setMenuView(MenuView newView){
        this.addSubscriber(newView);
    }

    /**
     * Set method for ServerModel's TableView
     */
    public void setTableView(TableView newView){
        this.addSubscriber(newView);
    }

    public void addSubscriber(ServerViewInterface view){
        this.subscribers.add(view);
    }

    /**
     * Set method for ServerModel's CustomizeView
     */
    public void setCustomizeView(CustomizeView customizeView) {
        this.addSubscriber(customizeView);
    }
    public void notifySubscribers(){
        this.subscribers.forEach((ServerViewInterface::modelChanged));
    }
}
