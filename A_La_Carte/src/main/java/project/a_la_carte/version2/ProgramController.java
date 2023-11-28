package project.a_la_carte.version2;

import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.scene.control.Alert;


import project.a_la_carte.version2.classesObjects.*;
import project.a_la_carte.version2.kitchen.*;
import project.a_la_carte.version2.kitchen.widgets.*;
import project.a_la_carte.version2.managerSide.RestaurantInfo.ReportView;
import project.a_la_carte.version2.managerSide.RestaurantInfo.RestaurantDay;
import project.a_la_carte.version2.managerSide.RestaurantInfo.RestaurantInfoView;
import project.a_la_carte.version2.managerSide.inventory.*;
import project.a_la_carte.version2.managerSide.staff.StaffInfoView;
import project.a_la_carte.version2.menuItems.*;
import project.a_la_carte.version2.managerSide.recipe.*;
import project.a_la_carte.version2.serverSide.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ProgramController {
    StartupMVC startupMVC;
    ManagerMainView managerMainView;
    WorkerView workerView;
    InventoryView inventoryView;
    RecipeListView recipeListView;
    RecipeMakerView recipeMakerView;
    MenuItemMakerView menuItemMakerView;
    StaffInfoView staffInfoView;
    RestaurantInfoView restaurantInfoView;
    SignUpView signUpView;
    SignInView signInView;

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
    public void setManagerMainView(ManagerMainView view){
        this.managerMainView = view;
    }
    public void setWorkerView(WorkerView view){
        this.workerView = view;
    }

    public void setRestaurantInfoView(RestaurantInfoView restaurantInfoView) {
        this.restaurantInfoView = restaurantInfoView;
    }

    public void openManagerMainView(ActionEvent event){
        this.managerMainView.selectManagerMenu();
        this.managerMainView.modelChanged();
    }
    public void openWorkerView(WorkerView view){
        view.selectWorkerView();
        view.modelChanged();
    }
    public void startManagerMainView(ActionEvent event){
        ManagerMainView newView = new ManagerMainView(this.startupMVC);

        Stage managerStage = new Stage();
        managerStage.setTitle("Manager");
        managerStage.getIcons().add(new Image(ProgramController.class.getResourceAsStream("/images/icon.png")));
        managerStage.setScene(new Scene(newView));
        managerStage.show();

    }
    public void startWorkerView(ActionEvent event){
        WorkerView newView = new WorkerView(this.startupMVC);

        Stage workerStage = new Stage();
        workerStage.setTitle("Worker");
        workerStage.getIcons().add(new Image(ProgramController.class.getResourceAsStream("/images/icon.png")));
        workerStage.setScene(new Scene(newView));
        newView.setStage(workerStage);
        workerStage.show();
    }
    public void setStartupMVC(StartupMVC newModel){
        this.startupMVC = newModel;
        //This is just for testing
        MenuFoodItem newItem = new MenuFoodItem((ArrayList<Recipe>) startupMVC.getRecipeModel().getRecipeList(), "Test Item", "Test Description");
        startupMVC.getMenuItemModel().addNewMenuItem(newItem);
        this.startupMVC.getMenuItemModel().resetAddedRecipes();
        //----------------------------------------------
    }
    public void openInventoryScreen(ActionEvent event){
        this.managerMainView.selectInventory();
        this.managerMainView.modelChanged();
        this.startupMVC.getInventoryModel().notifySubs();
    }
    public void openStartUpMVC(ActionEvent event){
        this.startupMVC.selectStartup();
        this.startupMVC.modelChanged();
    }
    public void openSignUp(ActionEvent event){
        this.startupMVC.selectSignUp();
        this.startupMVC.modelChanged();
    }
    public void openRecipeList(ActionEvent event){
        this.managerMainView.selectRecipeList();
        this.managerMainView.modelChanged();
        this.startupMVC.getRecipeModel().notifySubscribers();
        this.startupMVC.getRecipeInteractiveModel().clearRecipeIModel();
    }
    public void openMenuView(WorkerView view){
        //So that the new display is shown
        this.startupMVC.getServerModel().notifySubscribers();
        this.startupMVC.getServerModel().setMenuItemList(startupMVC.getMenuItemModel().getMenuItemsList());

        view.selectMenuView();
        view.modelChanged();
    }
    public void openKitchenView(WorkerView view){
        this.startupMVC.getKitchenModel().notifySubscribers();

        view.selectKitchenView();
        view.modelChanged();
    }
    public void openMenuListView(ActionEvent event){
        this.startupMVC.getMenuItemModel().notifySubscribers();
        this.managerMainView.selectMenuItemList();
        this.managerMainView.modelChanged();
    }

    public void openStaffInfoView(ActionEvent event){
        this.startupMVC.getStaffModel().notifySubscribers();
        this.managerMainView.selectStaffInfoView();
        this.managerMainView.modelChanged();
    }

    public void openRestaurantInfoView(ActionEvent event){
        this.startupMVC.getRestaurantModel().notifySubs();
        this.managerMainView.selectRestaurantInfoView();
        this.managerMainView.modelChanged();
    }

    public void setStateLoaded(){
        this.interactionState = INTERACTION_STATE.RECIPE_LOADED;
    }
    public void setStateNotLoaded(ActionEvent event){
        this.interactionState = INTERACTION_STATE.NOT_LOADED;
    }

    public void setIModelCreating(ActionEvent event) {
        startupMVC.getRecipeInteractiveModel().setCreating(true);
    }
    public void setIModelNotCreating(ActionEvent event) {
        startupMVC.getRecipeInteractiveModel().setCreating(false);
    }


    /**
     * Start of log in actions
     */
    public void setSignUpView(SignUpView signUpView) {
        this.signUpView = signUpView;
    }

    public void setSignInView(SignInView signInView) {
        this.signInView = signInView;
    }

    public void handleLogIn(ActionEvent actionEvent){
        String username = signInView.getUsernameText().getText();
        String password = signInView.getPasswordText().getText();

        boolean valid = this.startupMVC.getStaffModel().verifyLogIn(username,password);
        if(valid){
           this.startManagerMainView(actionEvent);
           signInView.clearFields();
        }else {
            showErrorAlert("No Matching Credentials","Double check username and password");
        }
    }

    public void handleServerLogIn(WorkerView workerView) {
        String pin = workerView.getPinText().getText();
        boolean valid = this.startupMVC.getStaffModel().verifyServerLogIn(pin);
        if(valid){
            this.openMenuView(workerView);
        } else {
            showErrorAlert("No Matching Credentials","Double check username and password");
        }
    }

    public void newManager(ActionEvent actionEvent) {
        //try {
            String fName = signUpView.getfNameText().getText();
            String lName = signUpView.getlNameText().getText();
            String id = signUpView.getIdText().getText();
            int sin = Integer.parseInt(signUpView.getSinText().getText());

            String username = signUpView.getUsernameText().getText();
            String password = signUpView.getPasswordText().getText();

            startupMVC.getStaffModel().addManager(fName, lName, id, Staff.position.Manager,sin, username, password);
            signUpView.clearFields();
            showConfirmationAlert("New Manager: ",fName + lName + " successfully added.");
//        } catch (NullPointerException e){
//            System.out.println(e.getStackTrace());
//            //showAlert("ERROR:","Illegal input, please check all fields are filled correctly");
//        } catch (IllegalArgumentException e){
//            System.out.println(e.getStackTrace());
//            //showAlert("ERROR:","Illegal input, please check all fields are filled correctly");
//        }
    }

    /**
     * End of log in actions
     */
    /**
     * Here would be Inventory actions
     */
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
            float pricePerUnit = Float.parseFloat(inventoryView.getPriceText().getText());
            double reorderPoint = 0.0;
            if(!inventoryView.getReorderText().getText().isEmpty()){
                reorderPoint = Double.parseDouble(inventoryView.getReorderText().getText());
            }
            startupMVC.getInventoryModel().addIngredient(ingredientName,quantity,type,mUnit,commonAllergen,pricePerUnit,reorderPoint);
            clearInventoryViewFields(actionEvent);

            if (ingredientName.isEmpty() || type == null || mUnit == null) {
                throw new IllegalArgumentException("Please fill in all the required fields.");
            }

        } catch(NumberFormatException e){
            showErrorAlert("Error", "Quantity must be a valid number");
        } catch(IllegalArgumentException e){
            showErrorAlert("Error", e.getMessage());

        } catch (Exception e){
            showErrorAlert("Error", e.getMessage());
        }

    }
    public void loadIngredient(MouseEvent mouseEvent) {
        IngredientData iData = inventoryView.getInventoryTable().getSelectionModel().getSelectedItem();

        if(iData != null) {
            Ingredient loadedIngredient = iData.getIngredient();
            startupMVC.getInventoryModel().setLoadedIngredient(loadedIngredient);
        }
    }

    public void clearInventoryViewFields(ActionEvent actionEvent) {
        inventoryView.getNameText().clear();
        inventoryView.getNameText().setEditable(true);
        inventoryView.getQuantityText().clear();
        inventoryView.getQuantityText().setEditable(true);
        inventoryView.getMeasurementUnitComboBox().setValue(null);
        inventoryView.getTypeComboBox().setValue(null);
        inventoryView.getCommonAllergenCheck().setSelected(false);
        inventoryView.getReorderText().clear();
        inventoryView.getPriceText().clear();
    }

    public void updateItem(ActionEvent actionEvent){
        try {
            String ingredientName = inventoryView.getNameText().getText();
            Ingredient ingredient = searchIngredientByName(ingredientName);
            Double quantity = Double.valueOf(inventoryView.getQuantityText().getText());
            Ingredient.IngredientType type = inventoryView.getTypeComboBox().getValue();
            Ingredient.MeasurementUnit mUnit = inventoryView.getMeasurementUnitComboBox().getValue();
            Boolean commonAllergen = inventoryView.getCommonAllergenCheck().isSelected();
            float pricePerUnit = Float.parseFloat(inventoryView.getPriceText().getText());
            double reorderPoint = 0.0;
            if(!inventoryView.getReorderText().getText().isEmpty()){
                reorderPoint = Double.parseDouble(inventoryView.getReorderText().getText());
            }
            startupMVC.getInventoryModel().updateItem(ingredient, quantity, type, mUnit, commonAllergen,pricePerUnit,reorderPoint);
            clearInventoryViewFields(actionEvent);
        } catch (Exception e) {
            showErrorAlert("Error", e.getMessage());
        }
    }

    public void deleteItem(ActionEvent actionEvent) {

        String ingredientName = inventoryView.getNameText().getText();
        Ingredient ingredient = searchIngredientByName(ingredientName);
        startupMVC.getInventoryModel().deleteItem(ingredient);
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
            startupMVC.getRecipeInteractiveModel().setLoadedRecipe(null);
            setStateNotLoaded(null);
        } else {

            Recipe recipeToLoad = recipeListView.getRecipeTable().getSelectionModel().getSelectedItem().getRecipe();
            startupMVC.getRecipeInteractiveModel().setLoadedRecipe(recipeToLoad);
            setStateLoaded();
        }
        //cant figure out how to unselect a recipe lol

    }

    public Recipe searchRecipeByName(String name){
        for(Recipe recipe : startupMVC.getRecipeModel().getRecipeList()){
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

    public void setRecipeMakerView(RecipeMakerView recipeMakerView) {
        this.recipeMakerView = recipeMakerView;
    }

    public void openRecipeMakerScreen(ActionEvent event){
        switch (interactionState){
            case RECIPE_LOADED -> {
                this.managerMainView.selectRecipeMaker();
                this.managerMainView.modelChanged();
                this.startupMVC.getInventoryModel().notifySubs();
                this.recipeMakerView.updateMenuHandlers(this);//and updates the handlers for every new menu item
            }
            case NOT_LOADED -> {
                this.startupMVC.getRecipeInteractiveModel().clearRecipeIModel();
                this.managerMainView.selectRecipeMaker();
                this.managerMainView.modelChanged();
                this.startupMVC.getInventoryModel().notifySubs();
                this.recipeMakerView.updateMenuHandlers(this);//and updates the handlers for every new menu item
            }
        }
    }

    public void selectIngredient(MouseEvent mouseEvent) {

        Ingredient selectedIngredientName = recipeMakerView.getIngredientTable().getSelectionModel().getSelectedItem().getIngredient();
        recipeMakerView.getSelectedIngredient().setText(selectedIngredientName.getName());

    }

    public void deleteIngredientFromRecipe(ActionEvent actionEvent) {

        Ingredient ingredientToRemove = searchIngredientByName(recipeMakerView.getSelectedIngredient().getText());
        startupMVC.getRecipeInteractiveModel().removeFromTempMap(ingredientToRemove);
    }


    /**
     * When the addRecipe button is hit, take all the information from the fields and create a new recipe
     * take ingredients iModel and use the map to store them in the recipie
     * @param event
     */
    public void addRecipe(ActionEvent event){

        try {
            if(recipeMakerView.getRecipeName().getText().isBlank() || recipeMakerView.getRecipeDescription().getText().isEmpty() || recipeMakerView.getRecipeInstruction().getText().isEmpty()) {
                throw new IllegalArgumentException("Please fill out all fields");
            }

            String recipeName = recipeMakerView.getRecipeName().getText();
            double recipePrice = Double.parseDouble(recipeMakerView.getRecipePrice().getText());
            String recipeDesc = recipeMakerView.getRecipeDescription().getText();
            String recipeInstruction = recipeMakerView.getRecipeInstruction().getText();
            double recipePrepTime = Double.parseDouble(recipeMakerView.getRecipePrep().getText());

            HashMap<String, Double> ingredientMap = new HashMap<>();

            for (Map.Entry<String, Double> entry : startupMVC.getRecipeInteractiveModel().getTemporaryIngredientMap().entrySet()) {
                Double ingredientQuantity;

                if (searchIngredientByName(entry.getKey()).getMeasurementUnit() == Ingredient.MeasurementUnit.Pounds) {
                    ingredientQuantity = ozToPounds(entry.getValue());//convert the oz number inputted for the recipe to pounds
                } else {
                    ingredientQuantity = entry.getValue();
                }
                ingredientMap.put(entry.getKey(), ingredientQuantity);
            }

            startupMVC.getRecipeModel().addNewOrUpdateRecipe(recipeName, recipePrice, recipeDesc, recipeInstruction, recipePrepTime, ingredientMap);

            ArrayList<Recipe> newRecipeList = new ArrayList<>(startupMVC.getRecipeModel().getRecipeList());
            startupMVC.getMenuItemModel().setRecipeArrayList(newRecipeList);

            //could be deleted if we wanted to leave the recipe on the scree
            //clear all the fields
            recipeMakerView.getRecipeName().clear();
            recipeMakerView.getRecipePrice().clear();
            recipeMakerView.getRecipeDescription().clear();
            recipeMakerView.getRecipeInstruction().clear();
            recipeMakerView.getRecipePrep().clear();
            recipeMakerView.getIngredientTable().setItems(null);
            setStateNotLoaded(event);
            recipieAddedPopUp(recipeName);


        }catch(NumberFormatException e){
            showErrorAlert("Error", "Fill out all the fields");
        } catch (IllegalArgumentException e){
            showErrorAlert("Error", e.getMessage());
        }

    }

    public void deleteRecipe(ActionEvent actionEvent) {
        try{
            String recipeName = recipeListView.getRecipeTable().getSelectionModel().getSelectedItem().getRecipe().getName();
            Recipe recipe = searchRecipeByName(recipeName);
            startupMVC.getRecipeModel().deleteRecipe(recipe);
            setStateNotLoaded(actionEvent);
            startupMVC.getRecipeInteractiveModel().clearRecipeIModel();
        }catch (NullPointerException e){
            showErrorAlert("Error", "No recipe selected to delete");


        }

    }

    public void showRecipeInfo(ActionEvent actionEvent){
        //TODO might need to use separate Models depending on who wants to view
        //for now this is fine
        try {
            Recipe selectedRecipe = startupMVC.getRecipeInteractiveModel().getLoadedRecipe();
            new ShowRecipeInfoView(selectedRecipe);
        } catch (NullPointerException e){
            showErrorAlert("Error","There is no recipeSelected" );
        }
    }



    public void recipieAddedPopUp(String recipieName){//this is just for some user feedback we can remove it tbh

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

        try {
            if(recipeMakerView.getSelectedIngredient().getText().isBlank()){
                throw new IllegalArgumentException("No Ingredient to add");
            }
            if(!recipeMakerView.getRecipeName().getText().isEmpty()){
                startupMVC.getRecipeInteractiveModel().setCreating(true);
            }
            String ingredientName = recipeMakerView.getSelectedIngredient().getText();
            Ingredient ingredient = searchIngredientByName(ingredientName);
            Double recipeQuantity = Double.valueOf(recipeMakerView.getEnterMeasurementField().getText());
            //find the ingredient;
            startupMVC.getRecipeInteractiveModel().addToTempMap(ingredient.getName(), recipeQuantity);
            //add ingredient to temp list of ingredients to be displayed
        }
        catch(NumberFormatException e){
            System.out.println("Error: Enter valid quantity");
        } catch(IllegalArgumentException e){
            showErrorAlert("Error", e.getMessage());
        }
    }

    /**
     * find ingredient by String name in inventoryModel
     * @param name gotten from textfield
     * @return ingredient with matching name
     * null if not found
     */
    public Ingredient searchIngredientByName(String name){

        return this.startupMVC.getInventoryModel().getIngredientMap().get(name);

    }


    /**
     * End of Recipe Actions
     */
    /**
     * Kitchen Actions
     */
    public void showKitchenAlerts(ActionEvent event){
        KitchenAlertView alertView = new KitchenAlertView(this.startupMVC.getKitchenModel());
        this.startupMVC.getKitchenModel().addSubscribers(alertView);
        this.startupMVC.getKitchenModel().notifySubscribers();

        Stage kitchenAlertStage = new Stage();
        kitchenAlertStage.setScene(new Scene(alertView));
        kitchenAlertStage.show();
    }
    public void alertSenderToServer(ActionEvent event){
        ServerNoteMaker serverNoteMaker = new ServerNoteMaker(this, this.startupMVC.getKitchenModel());

        Stage serverNoteStage = new Stage();
        serverNoteStage.setScene(new Scene(serverNoteMaker));
        serverNoteStage.show();
    }
    public void sendKitchenAlertToServer(ActionEvent event){
        startupMVC.getServerModel().addNote(startupMVC.getKitchenModel().getSentAlert());
        startupMVC.getServerModel().notifySubscribers();
    }
    /**
     * End of Kitchen Actions
     */

    /**
     * Menu Action
     */
    public void setMenuItemMakerView(MenuItemMakerView newView){
        this.menuItemMakerView = newView;
    }
    public void openMenuMakerView(ActionEvent event){
        this.menuItemMakerView.setSave();
        ArrayList<Recipe> newList = new ArrayList<>(startupMVC.getRecipeModel().getRecipeList());

        this.startupMVC.getMenuItemModel().setRecipeArrayList(newList);
        this.managerMainView.selectMenuMakerView();
        this.managerMainView.modelChanged();
    }
    public void editMenuMakerView(ActionEvent event){
        this.menuItemMakerView.setEdit();

        menuItemMakerView.setNameText(startupMVC.getMenuItemModel().getSelectedItem().getName());
        menuItemMakerView.setDescText(startupMVC.getMenuItemModel().getSelectedItem().getDescription());
        menuItemMakerView.setPriceText(String.valueOf(startupMVC.getMenuItemModel().getSelectedItem().getPrice()));
        menuItemMakerView.setPrepText(String.valueOf(startupMVC.getMenuItemModel().getSelectedItem().getPrepTime()));

        this.startupMVC.getMenuItemModel().setSelectedAddedRecipe(this.startupMVC.getMenuItemModel().getSelectedItem().getMenuItemRecipes());
        this.startupMVC.getMenuItemModel().notifySubscribers();
        this.managerMainView.selectMenuMakerView();
        this.managerMainView.modelChanged();
    }
    public void deleteMenuItem(ActionEvent event){
        this.startupMVC.getMenuItemModel().deleteMenuItem(startupMVC.getMenuItemModel().getSelectedItem());
        this.startupMVC.getServerModel().setMenuItemList(startupMVC.getMenuItemModel().getMenuItemsList());
    }
    public void addRecipeToItem(ActionEvent event){
        if (this.startupMVC.getMenuItemModel().getSelectedRecipe() != null){
            this.startupMVC.getMenuItemModel().addRecipesToItem(this.startupMVC.getMenuItemModel().getSelectedRecipe());}
    }
    public void removeRecipeFromItem(ActionEvent event){
        if (this.startupMVC.getMenuItemModel().getSelectedAddedRecipe() != null) {
            this.startupMVC.getMenuItemModel().removeRecipeFromItem(this.startupMVC.getMenuItemModel().getSelectedAddedRecipe());
        }
    }
    public void addItemToMenu(ActionEvent event){
        try {
            if (menuItemMakerView.getMenuItemName() != null && menuItemMakerView.getMenuItemDescription() != null) {
                MenuFoodItem newItem = new MenuFoodItem(startupMVC.getMenuItemModel().getAddedRecipes(), menuItemMakerView.getMenuItemName(), menuItemMakerView.getMenuItemDescription());
                if (!menuItemMakerView.getMenuPrice().isBlank()) {
                    newItem.setPrice(menuItemMakerView.setMenuPrice());
                }
                if (!menuItemMakerView.getMenuPrep().isBlank()) {
                    newItem.setPrepTime(menuItemMakerView.setMenuPrep());
                }
                this.startupMVC.getMenuItemModel().addNewMenuItem(newItem);
                menuItemMakerView.clearTextFields();
                this.startupMVC.getMenuItemModel().resetAddedRecipes();

                this.startupMVC.getServerModel().setMenuItemList(startupMVC.getMenuItemModel().getMenuItemsList());
            }
        } catch (Exception e) {
            showErrorAlert("Error", "An error occurred while saving the menu item: " + e.getMessage());

        }

    }

    public void saveEditsToItem(ActionEvent event){
        if (menuItemMakerView.getMenuItemName() != null && menuItemMakerView.getMenuItemDescription() != null) {
            startupMVC.getMenuItemModel().getSelectedItem().setName(menuItemMakerView.getMenuItemName());
            startupMVC.getMenuItemModel().getSelectedItem().setDescription(menuItemMakerView.getMenuItemDescription());
            if (!menuItemMakerView.getMenuPrice().isBlank()) {
                startupMVC.getMenuItemModel().getSelectedItem().setPrice(menuItemMakerView.setMenuPrice());
            }
            if (!menuItemMakerView.getMenuPrep().isBlank()) {
                startupMVC.getMenuItemModel().getSelectedItem().setPrepTime(menuItemMakerView.setMenuPrep());
            }
            menuItemMakerView.clearTextFields();
            this.startupMVC.getMenuItemModel().resetAddedRecipes();

            this.managerMainView.selectMenuItemList();
            this.managerMainView.modelChanged();
        }
    }
    /**
     * Here would be the Server Actions
     */
    public void showStockAlerts(ActionEvent actionEvent) {
        ServerStockAlertView stockView = new ServerStockAlertView(this.startupMVC.getInventoryModel());
        this.startupMVC.getInventoryModel().addSub(stockView);
        this.startupMVC.getInventoryModel().notifySubs();

        Stage inventoryAlertStage = new Stage();
        inventoryAlertStage.setScene(new Scene(stockView));
        inventoryAlertStage.show();
    }
    public void showServerAlerts(ActionEvent event){
        ServerAlertView alertView = new ServerAlertView(this.startupMVC.getServerModel());
        this.startupMVC.getServerModel().addSubscriber(alertView);
        this.startupMVC.getServerModel().notifySubscribers();

        Stage kitchenAlertStage = new Stage();
        kitchenAlertStage.setScene(new Scene(alertView));
        kitchenAlertStage.show();
    }
    public void sendNoteToKitchen(WorkerView view){
        this.startupMVC.getKitchenModel().addNote(view.getNoteView().getNote());
        this.startupMVC.getServerModel().setNoteMessage(view);
    }
    public void openNoteView(WorkerView view){
        this.startupMVC.getServerModel().clearNoteAlert(view.getNoteView());
        this.startupMVC.getServerModel().notifySubscribers();
        view.selectNoteView();
        view.modelChanged();
    }

    public void openTablesView(WorkerView view){
        this.startupMVC.getServerModel().notifySubscribers();
        view.selectTableView();
        view.modelChanged();
    }

    public void openCustomizeView(WorkerView view){
        this.startupMVC.getServerModel().notifySubscribers();
        view.selectCustomize();
        view.modelChanged();
    }
    public void discardSelection(WorkerView view){
        this.startupMVC.getServerModel().unselectAll(view);
    }
    public void saveCustomize(WorkerView view){
        this.startupMVC.getServerModel().setCustomization(view);
        this.startupMVC.getServerModel().unselectAll(view);
    }
    public void sendToKitchen(WorkerView view){
        if (view.getMenuView().getCurrentOrder() != null){
            this.startupMVC.getKitchenModel().addOrder(view.getMenuView().getCurrentOrder());
            this.startupMVC.getServerModel().sendOrderToKitchen();

            //process order in restaurant model
            this.startupMVC.getRestaurantModel().handleOrderPunched(view.getMenuView().getCurrentOrder());
            view.getMenuView().clearOrder();
        }
    }
    public void voidOrder(WorkerView view){
        view.getMenuView().clearOrder();
    }
    public void refundDisplay(ActionEvent event){
        RefundView refundView = new RefundView(startupMVC.getKitchenModel());
        this.startupMVC.getKitchenModel().addSubscribers(refundView);
        this.startupMVC.getKitchenModel().notifySubscribers();

        Stage refundStage = new Stage();
        refundStage.setScene(new Scene(refundView));
        refundStage.show();
    }
    /*Start of staff Actions*/

    public void setStaffInfoView(StaffInfoView staffInfoView) {
        this.staffInfoView = staffInfoView;
    }

    public void handleNewStaff(ActionEvent actionEvent) {
        try {
            String fName = staffInfoView.getfNameText().getText();
            String lName = staffInfoView.getlNameText().getText();
            String id = staffInfoView.getIdText().getText();
            Staff.position position = staffInfoView.getPositionComboBox().getValue();
            int sin = Integer.parseInt(staffInfoView.getSinText().getText());

            startupMVC.getStaffModel().addStaff(fName, lName, id, position, sin);

        } catch (IllegalArgumentException e){
            showErrorAlert("Illegal input, please check all fields are filled correctly", e.getMessage());
        } catch (NullPointerException e){
            showErrorAlert("Illegal input, please check all fields are filled correctly", e.getMessage());
        }
    }

    public void clearStaffInfoFields(ActionEvent event){
        staffInfoView.clearFields();
    }

    public void loadStaff(MouseEvent mouseEvent){
        StaffData sData = staffInfoView.getStaffTable().getSelectionModel().getSelectedItem();

        if(sData != null){
            Staff loadedStaff = sData.getStaff();
            startupMVC.getStaffModel().setLoadedStaff(loadedStaff);
        }
    }

    public void updateStaff(ActionEvent actionEvent){
        try {
            String fName = staffInfoView.getfNameText().getText();
            String lName = staffInfoView.getlNameText().getText();
            String id = staffInfoView.getIdText().getText();
            Staff.position position = staffInfoView.getPositionComboBox().getValue();
            int sin = Integer.parseInt(staffInfoView.getSinText().getText());
            String username;
            String password;


            if(!staffInfoView.getUserText().getText().isEmpty()){
                username = staffInfoView.getUserText().getText();
            } else{
                username = null;
            }
            if(!staffInfoView.getPasswordText().getText().isEmpty()){
                password = staffInfoView.getPasswordText().getText();
            } else{
                password = null;
            }
            startupMVC.getStaffModel().updateStaff(fName, lName, id, position, sin,username,password);
            showConfirmationAlert("Staff Updated","Staff: " + id + " successfully updated");
        } /*catch (IllegalArgumentException e){
            showAlert("ERROR:", "Staff does not exist, can not update");
        }*/ catch (NullPointerException e){
            showErrorAlert("ERROR:","Illegal input, please check all fields are filled correctly");
        }

    }
    public void deleteStaff(ActionEvent actionEvent){
        String id = staffInfoView.getIdText().getText();
        startupMVC.getStaffModel().deleteStaff(id);
    }
    public void saveList(ActionEvent actionEvent) {
        startupMVC.getStaffModel().saveList();
    }


    /*Restaurant information stuff*/

   public void generateReport(ActionEvent event){
       if(this.restaurantInfoView.getDatePicker().getValue()!= null) {
           String reportDate = this.restaurantInfoView.getDatePicker().getValue().toString();
           if(this.startupMVC.getRestaurantModel().getRestaurantDayMap().containsKey(reportDate)) {
               RestaurantDay dayToReport = this.startupMVC.getRestaurantModel().getRestaurantDayMap().get(reportDate);
               ReportView reportView = new ReportView(dayToReport);
               Stage reportStage = new Stage();
               reportStage.setScene(new Scene(reportView));
               reportStage.show();
           } else {
               showErrorAlert("ERROR:","No Report for this day");
           }
       } else{
           showErrorAlert("ERROR:","No date picked");
       }

   }


    /*End of restaurant information*/


    /*ALERTS*/
    private void showErrorAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();

    }
    private void showConfirmationAlert(String title,String message){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
