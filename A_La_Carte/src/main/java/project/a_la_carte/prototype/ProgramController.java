package project.a_la_carte.prototype;

import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import project.a_la_carte.prototype.kitchen.side.KitchenModel;
import project.a_la_carte.prototype.menu.items.MenuFoodItem;
import project.a_la_carte.prototype.menu.items.MenuItemMakerView;
import project.a_la_carte.prototype.menu.items.MenuItemModel;
import project.a_la_carte.prototype.recipe.maker.inventory.*;
import project.a_la_carte.prototype.server.side.ServerModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ProgramController {
    StartupMVC startupMVC;
    InventoryModel inventoryModel;
    InventoryView inventoryView;
    RecipeModel recipeModel;
    RecipeInteractiveModel recipeInteractiveModel;
    RecipeListView recipeListView;
    RecipeMakerView recipeMakerView;
    MenuItemModel menuItemModel;
    MenuItemMakerView menuItemMakerView;
    ServerModel serverModel;
    KitchenModel kitchenModel;

    private enum INTERACTION_STATE{
        RECIPE_LOADED,
        NOT_LOADED
    }
    private INTERACTION_STATE interactionState = INTERACTION_STATE.NOT_LOADED;


    public ProgramController(){

    }

    /**
     * These would be the action in StartupMVC
     */
    public void setStartupMVC(StartupMVC newModel){
        this.startupMVC = newModel;
    }
    public void openInventoryScreen(ActionEvent event){
        this.startupMVC.selectInventory();
        this.startupMVC.modelChanged();
        this.inventoryModel.notifySubs();
    }
    public void openStartUpMVC(ActionEvent event){
        this.startupMVC.selectStartup();
        this.startupMVC.modelChanged();
    }
    public void openRecipeList(ActionEvent event){
        this.startupMVC.selectRecipeList();
        this.startupMVC.modelChanged();
        this.recipeModel.notifySubscribers();
    }
    public void openMenuView(ActionEvent event){
        //So that the new display is shown
        this.serverModel.setMenuItemList(menuItemModel.getMenuItemsList());
        this.startupMVC.selectMenuView();
        this.startupMVC.modelChanged();
    }
    public void openKitchenView(ActionEvent event){
        this.startupMVC.selectKitchenView();
        this.startupMVC.modelChanged();
    }
    public void openMenuListView(ActionEvent event){
        this.startupMVC.selectMenuItemList();
        this.startupMVC.modelChanged();
    }

    public void setStateLoaded(){
        this.interactionState = INTERACTION_STATE.RECIPE_LOADED;
        System.out.println("State loaded");
    }
    public void setStateNotLoaded(ActionEvent event){
        this.interactionState = INTERACTION_STATE.NOT_LOADED;
        System.out.println("State not loaded");
    }

    /**
     * Here would be Inventory actions
     */
    public void setInventoryModel(InventoryModel newModel){this.inventoryModel = newModel;}
    public void setInventoryView(InventoryView inventoryView){
        this.inventoryView = inventoryView;
    }
    public void handleNewIngredient(ActionEvent actionEvent) {
        try{
        String ingredientName = inventoryView.getNameText().getText();
        Double quantity = Double.valueOf(inventoryView.getQuantityText().getText());
        Ingredient.IngredientType type = inventoryView.getTypeComboBox().getValue();
        Ingredient.MeasurementUnit mUnit = inventoryView.getMeasurementUnitComboBox().getValue();
        Boolean commonAllergen = inventoryView.getCommonAllergenCheck().isSelected();
        inventoryModel.addIngredient(ingredientName,quantity,type,mUnit,commonAllergen);
        clearInventoryViewFields(actionEvent);

        if (ingredientName.isEmpty() || type == null || mUnit == null) {
            throw new IllegalArgumentException("Please fill in all the required fields.");
        }

        } catch(NumberFormatException e){
            System.out.println("ERROR: Quantity must be a valid number");
        } catch(IllegalArgumentException e){
            System.out.println("ERROR:" + e.getMessage());
        } catch (Exception e){
            System.out.println("An unexpected error occurred: " + e.getMessage());
        }

    }
    public void loadIngredient(MouseEvent mouseEvent) {
        String ingredientName = inventoryView.getInventoryTable().getSelectionModel().getSelectedItem().getIngredient().getName();
        Ingredient loadedIngredient = searchIngredientByName(ingredientName);
        inventoryModel.setLoadedIngredient(loadedIngredient);
    }

    public void clearInventoryViewFields(ActionEvent actionEvent) {
        inventoryView.getNameText().clear();
        inventoryView.getNameText().setEditable(true);
        inventoryView.getQuantityText().clear();
        inventoryView.getQuantityText().setEditable(true);
        inventoryView.getMeasurementUnitComboBox().setValue(null);
        inventoryView.getTypeComboBox().setValue(null);
        inventoryView.getCommonAllergenCheck().setSelected(false);
    }

    public void updateItem(ActionEvent actionEvent){

        String ingredientName = inventoryView.getNameText().getText();
        Ingredient ingredient = searchIngredientByName(ingredientName);
        Double quantity = Double.valueOf(inventoryView.getQuantityText().getText());
        Ingredient.IngredientType type = inventoryView.getTypeComboBox().getValue();
        Ingredient.MeasurementUnit mUnit = inventoryView.getMeasurementUnitComboBox().getValue();
        Boolean commonAllergen = inventoryView.getCommonAllergenCheck().isSelected();
        inventoryModel.updateItem(ingredient,quantity,type,mUnit,commonAllergen);


        clearInventoryViewFields(actionEvent);
    }

    public void deleteItem(ActionEvent actionEvent) {

        String ingredientName = inventoryView.getNameText().getText();
        Ingredient ingredient = searchIngredientByName(ingredientName);
        inventoryModel.deleteItem(ingredient);
    }

    /**
     * End of Inventory Actions
     */


    /**
     * Here would be Recipe List Actions
     */
    public void setRecipeListView(RecipeListView recipeListView) {
        this.recipeListView = recipeListView;
    }

    public void loadRecipe(MouseEvent mouseEvent) {

        if( recipeListView.getRecipeTable().getSelectionModel().getSelectedItem() ==null){
            recipeInteractiveModel.setLoadedRecipe(null);
            setStateNotLoaded(null);
        } else {

            Recipe recipeToLoad = recipeListView.getRecipeTable().getSelectionModel().getSelectedItem().getRecipe();
            System.out.println(recipeToLoad.getRecipeIngredients().size());
            recipeInteractiveModel.setLoadedRecipe(recipeToLoad);
            setStateLoaded();
        }
        //cant figure out how to unselect a recipe lol

    }

    public Recipe searchRecipeByName(String name){
        for(Recipe recipe : recipeModel.getRecipeList()){
            if(recipe.getName().equals(name)){
                return recipe;
            }
        }
        //recipe not found
        return null;
    }
    /**
     * End of Recipe List Actions
     */

    /**
     * Here would be Recipe Maker actions
     */
    public void setRecipeModel(RecipeModel newModel){this.recipeModel = newModel;}

    public void setRecipeMakerView(RecipeMakerView recipeMakerView) {
        this.recipeMakerView = recipeMakerView;
    }

    public void setRecipeInteractiveModel(RecipeInteractiveModel recipeInteractiveModel) {
        this.recipeInteractiveModel = recipeInteractiveModel;
    }
    public void openRecipeMakerScreen(ActionEvent event){

        switch (interactionState){
            case RECIPE_LOADED -> {

                this.startupMVC.selectRecipeMaker();
                this.startupMVC.modelChanged();
                this.inventoryModel.notifySubs();
                this.recipeMakerView.updateMenuHandlers(this);//and updates the handlers for every new menu item



            }
            case NOT_LOADED -> {
                this.recipeInteractiveModel.clearRecipeIModel();
                this.startupMVC.selectRecipeMaker();
                this.startupMVC.modelChanged();
                this.inventoryModel.notifySubs();
                this.recipeMakerView.updateMenuHandlers(this);//and updates the handlers for every new menu item
            }
        }

    }

    /**
     * When the addRecipe button is hit, take all the information from the fields and create a new recipe
     * take ingredients iModel and use the map to store them in the recipie
     * @param event
     */
    public void addRecipe(ActionEvent event){

        String recipeName = recipeMakerView.getRecipeName().getText();
        double recipePrice = Double.parseDouble(recipeMakerView.getRecipePrice().getText());
        String recipeDesc = recipeMakerView.getRecipeDescription().getText();
        String recipeInstruction = recipeMakerView.getRecipeInstruction().getText();
        double recipePrepTime = Double.parseDouble(recipeMakerView.getRecipePrep().getText());

        HashMap<Ingredient,Double> ingredientMap = new HashMap<>();

        for (Map.Entry<Ingredient, Double> entry : recipeInteractiveModel.getTemporaryIngredientMap().entrySet()) {
            Double ingredientQuantity;
            if(entry.getKey().getMeasurementUnit() == Ingredient.MeasurementUnit.Pounds){
                ingredientQuantity = ozToPounds(entry.getValue());//convert the oz number inputted for the recipe to pounds
            }
            else{
                ingredientQuantity = entry.getValue();
            }
            ingredientMap.put(entry.getKey(), ingredientQuantity);
        }

        recipeModel.addNewOrUpdateRecipe(recipeName,recipePrice,recipeDesc,recipeInstruction,recipePrepTime,ingredientMap);

        ArrayList<Recipe> newRecipeList = new ArrayList<>(recipeModel.getRecipeList());
        menuItemModel.setRecipeArrayList(newRecipeList);

        //clear all the fields
        recipeMakerView.getRecipeName().clear();
        recipeMakerView.getRecipePrice().clear();
        recipeMakerView.getRecipeDescription().clear();
        recipeMakerView.getRecipeInstruction().clear();
        recipeMakerView.getRecipePrep().clear();

        recipieAddedPopUp(recipeName);
        setStateNotLoaded(event);
        recipeInteractiveModel.clearRecipeIModel();

    }

    /**
     * popup window that we can honestly remove
     * @param recipieName
     */
    public void recipieAddedPopUp(String recipieName){

        Stage secondStage = new Stage();
        secondStage.setScene(new Scene(new HBox(4, new Label(recipieName + " added to Recipie List!"))));
        secondStage.show();
    }

    /**
     * When the user clicks from the menu, show the selected Ingredient in the textfield
     * @param actionEvent
     */
    public void ingredientSelected(ActionEvent actionEvent) {
        MenuItem selectedIngredient = (MenuItem) actionEvent.getSource();
        String selectedIngredientName = selectedIngredient.getText();

        //System.out.println(selectedIngredientName);
        recipeMakerView.getSelectedIngredient().setEditable(false);
        recipeMakerView.getSelectedIngredient().setText(selectedIngredientName);

        //set comboBox to match measurement;
        Ingredient ingredient = searchIngredientByName(selectedIngredientName);
        if(ingredient.getMeasurementUnit() == Ingredient.MeasurementUnit.Count){
            recipeMakerView.getMeasurementBox().setValue("Count");
        }
        else{
            //Ounces makes sense for small recipes, will have to convert ounce to pound
            recipeMakerView.getMeasurementBox().setValue("Oz");
        }
    }

    /**
     * convert inputted ounce to pounds for subtracting from inventory
     * @param ounces
     * @return
     */
    public double ozToPounds(double ounces){
        double pounds = ounces / 16.0;
        pounds = Math.round(pounds * 10.)/10.0;
        return pounds;
    }

    /**
     * add ingredient in textbox to temp ingredient list in Recipe I model
     * @param actionEvent
     */
    public void addIngredientToRecipe(ActionEvent actionEvent) {
        String ingredientName = recipeMakerView.getSelectedIngredient().getText();
        Ingredient ingredient = searchIngredientByName(ingredientName);
        Double recipeQuantity = Double.valueOf(recipeMakerView.getEnterMeasurementField().getText());
        //find the ingredient;
        recipeInteractiveModel.addToTempMap(ingredient,recipeQuantity);
        //add ingredient to temp list of ingredients to be displayed
    }

    /**
     * find ingredient by String name in inventoryModel
     * @param name gotten from textfield
     * @return ingredient with matching name
     * null if not found
     */
    public Ingredient searchIngredientByName(String name){
        for(Ingredient key: inventoryModel.getIngredientMap().keySet()){
            if(key.getName().equals(name)){
                return key;
            }
        }
        //handle error better
        return null;
    }


    /**
     * End of Recipe Actions
     */
    /**
     * Kitchen Actions
     */
    public void setKitchenModel(KitchenModel newModel){
        this.kitchenModel = newModel;
    }

    /**
     * End of Kitchen Actions
     */

    /**
     * Menu Action
     */
    public void setMenuItemModel(MenuItemModel newModel){
        this.menuItemModel = newModel;
    }
    public void setMenuItemMakerView(MenuItemMakerView newView){
        this.menuItemMakerView = newView;
    }
    public void openMenuMakerView(ActionEvent event){
        this.menuItemMakerView.setSave();
        this.startupMVC.selectMenuMakerView();
        this.startupMVC.modelChanged();
    }
    public void editMenuMakerView(ActionEvent event){
        this.menuItemMakerView.setEdit();

        menuItemMakerView.setNameText(menuItemModel.getSelectedItem().getName());
        menuItemMakerView.setDescText(menuItemModel.getSelectedItem().getDescription());
        menuItemMakerView.setPriceText(String.valueOf(menuItemModel.getSelectedItem().getPrice()));
        menuItemMakerView.setPrepText(String.valueOf(menuItemModel.getSelectedItem().getPrepTime()));

        this.menuItemModel.setSelectedAddedRecipe(this.menuItemModel.getSelectedItem().getMenuItemRecipes());
        this.menuItemModel.notifySubscribers();
        this.startupMVC.selectMenuMakerView();
        this.startupMVC.modelChanged();
    }
    public void deleteMenuItem(ActionEvent event){
        this.menuItemModel.deleteMenuItem(menuItemModel.getSelectedItem());
        this.serverModel.setMenuItemList(menuItemModel.getMenuItemsList());
    }
    public void addRecipeToItem(ActionEvent event){
        if (this.menuItemModel.getSelectedRecipe() != null){
            this.menuItemModel.addRecipesToItem(this.menuItemModel.getSelectedRecipe());}
    }
    public void removeRecipeFromItem(ActionEvent event){
        if (this.menuItemModel.getSelectedAddedRecipe() != null) {
            this.menuItemModel.removeRecipeFromItem(this.menuItemModel.getSelectedAddedRecipe());
        }
    }
    public void addItemToMenu(ActionEvent event){
        if (menuItemMakerView.getMenuItemName() != null && menuItemMakerView.getMenuItemDescription() != null) {
            MenuFoodItem newItem = new MenuFoodItem(menuItemModel.getAddedRecipes(), menuItemMakerView.getMenuItemName(), menuItemMakerView.getMenuItemDescription());
            if (!menuItemMakerView.getMenuPrice().isBlank()) {
                newItem.setPrice(menuItemMakerView.setMenuPrice());
            }
            if (!menuItemMakerView.getMenuPrep().isBlank()) {
                newItem.setPrepTime(menuItemMakerView.setMenuPrep());
            }
            this.menuItemModel.addNewMenuItem(newItem);
            menuItemMakerView.clearTextFields();
            this.menuItemModel.resetAddedRecipes();

            this.serverModel.setMenuItemList(menuItemModel.getMenuItemsList());
        }
    }
    public void saveEditsToItem(ActionEvent event){
        if (menuItemMakerView.getMenuItemName() != null && menuItemMakerView.getMenuItemDescription() != null) {
            menuItemModel.getSelectedItem().setName(menuItemMakerView.getMenuItemName());
            menuItemModel.getSelectedItem().setDescription(menuItemMakerView.getMenuItemDescription());
            if (!menuItemMakerView.getMenuPrice().isBlank()) {
                menuItemModel.getSelectedItem().setPrice(menuItemMakerView.setMenuPrice());
            }
            if (!menuItemMakerView.getMenuPrep().isBlank()) {
                menuItemModel.getSelectedItem().setPrepTime(menuItemMakerView.setMenuPrep());
            }
            menuItemMakerView.clearTextFields();
            this.menuItemModel.resetAddedRecipes();

            this.startupMVC.selectMenuItemList();
            this.startupMVC.modelChanged();
        }
    }
    /**
     * Here would be the Server Actions
     */
    public void sendNoteToKitchen(ActionEvent event){
        this.serverModel.setNoteMessage();
        this.kitchenModel.addNote(this.serverModel.getNoteMessage());
    }
    public void setServerModel(ServerModel newModel){
        this.serverModel = newModel;
    }
    public void openNoteView(ActionEvent event){
        this.serverModel.clearNoteAlert();
        this.serverModel.notifySubscribers();
        this.startupMVC.selectNoteView();
        this.startupMVC.modelChanged();
    }
    public void openCustomizeView(ActionEvent event){
        this.serverModel.notifySubscribers();
        this.startupMVC.selectCustomize();
        this.startupMVC.modelChanged();
    }
    public void openViewOrder(ActionEvent event){
        this.serverModel.notifySubscribers();
        this.startupMVC.selectViewOrder();
        this.startupMVC.modelChanged();
    }
    public void discardSelection(ActionEvent event){
        this.serverModel.unselectAll();
    }
    public void saveCustomize(ActionEvent event){
        this.serverModel.setCustomization();
        this.serverModel.unselectAll();
    }
    public void sendToKitchen(ActionEvent event){
        this.kitchenModel.addOrder(this.serverModel.sendOrderToKitchen());
    }


}
