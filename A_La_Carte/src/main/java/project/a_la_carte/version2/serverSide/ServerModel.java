package project.a_la_carte.version2.serverSide;

import project.a_la_carte.version2.WorkerView;
import project.a_la_carte.version2.interfaces.ServerViewInterface;
import project.a_la_carte.version2.classesObjects.*;
import project.a_la_carte.version2.serverSide.tableSystem.Table;
import project.a_la_carte.version2.serverSide.widgets.*;

import java.util.ArrayList;
import java.util.List;

public class ServerModel {
    List<ServerViewInterface> subscribers;
    int orderNumber;
    ArrayList<IngredientsCustomize> ingredientList;
    ArrayList<CustomizeSelectionButton> customizeButtons;
    ArrayList<MenuFoodItem> menuItemList;
    ArrayList<ServerNotes> notesArrayList;
    MenuFoodItem selectedMenuItem;
    public ServerModel(){
        //Used when creating order and assigning their number
        this.orderNumber = 1;
        menuItemList = new ArrayList<>();
        this.subscribers = new ArrayList<>();
        this.ingredientList = new ArrayList<>();
        this.customizeButtons = new ArrayList<>();
        this.notesArrayList = new ArrayList<>();

        this.addIngredient("Cheese");
        this.addIngredient("Milk");
        this.addIngredient("Tomato");
        this.addIngredient("Pickles");
        this.addIngredient("Rice");
        this.addIngredient("Beans");
        this.addIngredient("Fries");
        this.addIngredient("Poutine");

        this.addOptionButton("No");
        this.addOptionButton("Extra");
        this.addOptionButton("Just");
    }
    public void addNote(String message){
        ServerNotes newNote = new ServerNotes(message,this);

        notesArrayList.add(newNote);
        notifySubscribers();
    }
    public void deleteNote(ServerNotes note){
        this.getNoteList().remove(note);
        notifySubscribers();
    }
    public ArrayList<ServerNotes> getNoteList(){
        return this.notesArrayList;
    }
    public void clearNotes(){
        this.notesArrayList = new ArrayList<>();
        notifySubscribers();
    }

    public ArrayList<IngredientsCustomize> getIngredientList(){
        return this.ingredientList;
    }
    public ArrayList<CustomizeSelectionButton> getCustomizeButtons(){
        return this.customizeButtons;
    }
    public void addIngredient(String name){
        IngredientsCustomize newI = new IngredientsCustomize(name);
        ingredientList.add(newI);
    }
    public void addOptionButton(String name){
        CustomizeSelectionButton newB = new CustomizeSelectionButton(name);
        customizeButtons.add(newB);
    }

    public void sendOrderToKitchen(){
        this.orderNumber += 1;

        notifySubscribers();
    }
    public Table sendOrderToTable(WorkerView view){
        return new Table(view.getMenuView().getCurrentOrder());
    }
    public void setCustomization(WorkerView view){
        MenuFoodItem copy = new MenuFoodItem(this.getSelectedItem().getMenuItemRecipes()
                , this.getSelectedItem().getName(),this.getSelectedItem().getDescription());
        copy.setPrice(view.getMenuView().getSelectedItem().getPrice());
        copy.setPrepTime(view.getMenuView().getSelectedItem().getPrepTime());
        if (!view.getCustomizeView().getSelectedOption().equals("") && !view.getCustomizeView().getSelectedIngredient().equals("")) {
            copy.setCustomizeOption(view.getCustomizeView().getSelectedOption() + " " + view.getCustomizeView().getSelectedIngredient());
        }

        view.getMenuView().addToOrder(copy);
        notifySubscribers();
    }

    public void unselectAll(WorkerView view){
        view.getCustomizeView().customizeSelectionButtonArrayList.forEach((CustomizeSelectionButton::unselect));
        view.getCustomizeView().ingredientsCustomizeArrayList.forEach((IngredientsCustomize::unselect));
        view.getCustomizeView().selectedIngredient = "";
        view.getCustomizeView().selectedOption = "";
    }
    public void setNoteMessage(WorkerView view){
        view.getNoteView().noteText.clear();
        view.getNoteView().savedAlert.setText("SENT!");
    }
    public void clearNoteAlert(NoteView view){
        view.savedAlert.setText("");
    }
    public void setSelectedMenuItem(MenuFoodItem item, ArrayList<MenuFoodItem> list){
        list.forEach((MenuFoodItem::unselectDisplay));

        selectedMenuItem = item;
        notifySubscribers();
    }

    public MenuFoodItem getSelectedItem(){
        return this.selectedMenuItem;
    }
    public ArrayList<MenuFoodItem> getMenuItemList(){
        return this.menuItemList;
    }
    public void setMenuItemList(ArrayList<MenuFoodItem> newList){
        this.menuItemList = newList;
        notifySubscribers();
    }
    public void setMenuView(MenuView newView){
        this.addSubscriber(newView);
    }
    public void setNoteView(NoteView newView){
        this.addSubscriber(newView);
    }
    public void setTableView(TableView newView){
        this.addSubscriber(newView);
    }
    public void addSubscriber(ServerViewInterface view){
        this.subscribers.add(view);
    }

    public void setCustomizeView(CustomizeView customizeView) {
        this.addSubscriber(customizeView);
    }
    public void notifySubscribers(){
        this.subscribers.forEach((ServerViewInterface::modelChanged));
    }
}
