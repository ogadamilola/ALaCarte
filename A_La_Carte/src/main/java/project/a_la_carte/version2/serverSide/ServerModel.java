package project.a_la_carte.version2.serverSide;


import project.a_la_carte.version2.interfaces.ServerViewInterface;
import project.a_la_carte.version2.classesObjects.*;
import project.a_la_carte.version2.serverSide.widgets.*;

import java.util.ArrayList;
import java.util.List;

public class ServerModel {
    MenuView menuView;
    NoteView noteView;
    CustomizeView customizeView;
    List<ServerViewInterface> subscribers;
    String noteMessage = "";
    int orderNumber;
    ArrayList<IngredientsCustomize> ingredientList;
    ArrayList<CustomizeSelectionButton> customizeButtons;
    ArrayList<MenuFoodItem> menuItemList;
    MenuFoodItem selectedMenuItem;
    String selectedIngredient = "";
    String selectedOption = "";
    Order currentOrder;
    public ServerModel(){
        //Used when creating order and assigning their number
        this.orderNumber = 1;
        menuItemList = new ArrayList<>();
        this.subscribers = new ArrayList<>();
        this.ingredientList = new ArrayList<>();
        this.customizeButtons = new ArrayList<>();

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
    public void selectIngredient(String name){
        this.selectedIngredient = name;

        ingredientList.forEach((ingredient ->{
            if (ingredient.getIngredientName().equals(this.selectedIngredient)){
                ingredient.select();
            }
            else{
                ingredient.unselect();
            }
        }));
        notifySubscribers();
    }
    public void selectOption(String name){
        this.selectedOption = name;
        customizeButtons.forEach((option -> {
            if (option.getOptionName().equals(this.selectedOption)){
                option.select();
            }
            else{
                option.unselect();
            }
        }));
        notifySubscribers();
    }

    public Order getCurrentOrder() {
        return currentOrder;
    }
    public Order sendOrderToKitchen(){
        Order sendOrder = currentOrder;
        this.orderNumber += 1;
        currentOrder = null;

        notifySubscribers();
        return sendOrder;
    }

    public void setCustomization(){
        MenuFoodItem copy = new MenuFoodItem(this.getSelectedItem().getMenuItemRecipes()
                , this.getSelectedItem().getName(),this.getSelectedItem().getDescription());
        copy.setPrice(this.selectedMenuItem.getPrice());
        copy.setPrepTime(this.selectedMenuItem.getPrepTime());
        if (!selectedOption.equals("") && !selectedIngredient.equals("")) {
            copy.setCustomizeOption(this.selectedOption + " " + this.selectedIngredient);
        }

        this.addToOrder(copy);
        notifySubscribers();
    }
    public void addToOrder(MenuFoodItem item){
        if (this.currentOrder == null){
            currentOrder = new Order(new ArrayList<>(),this.orderNumber);
        }
        currentOrder.addItem(item);
    }
    public void unselectAll(){
        this.customizeButtons.forEach((CustomizeSelectionButton::unselect));
        this.ingredientList.forEach((IngredientsCustomize::unselect));
        this.selectedIngredient = "";
        this.selectedOption = "";
    }
    public void setNoteMessage(){
        this.noteMessage = this.noteView.getNote();
        this.noteView.noteText.clear();
        this.noteView.savedAlert.setText("SENT!");
    }
    public void clearNoteAlert(){
        this.noteView.savedAlert.setText("");
    }
    public void setSelectedMenuItem(MenuFoodItem item){
        this.getMenuItemList().forEach((MenuFoodItem::unselectDisplay));

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
    public void notifySubscribers(){
        this.subscribers.forEach((ServerViewInterface::modelChanged));
    }
}
