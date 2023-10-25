package project.a_la_carte.prototype;

import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import project.a_la_carte.prototype.kitchen.side.KitchenModel;
import project.a_la_carte.prototype.recipe.maker.inventory.*;
import project.a_la_carte.prototype.server.side.ServerModel;

import java.util.HashMap;
import java.util.Map;

public class ProgramController {
    StartupMVC startupMVC;
    InventoryModel inventoryModel;
    InventoryView inventoryView;
    RecipeModel recipeModel;
    RecipeInteractiveModel recipeInteractiveModel;
    RecipeMakerView recipeMakerView;


    ServerModel serverModel;
    KitchenModel kitchenModel;


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
        this.startupMVC.selectMenuView();
        this.startupMVC.modelChanged();
    }
    public void openKitchenView(ActionEvent event){
        this.startupMVC.selectKitchenView();
        this.startupMVC.modelChanged();
    }

    /**
     * Here would be Inventory actions
     */
    public void setInventoryModel(InventoryModel newModel){this.inventoryModel = newModel;}
    public void setInventoryView(InventoryView inventoryView){
        this.inventoryView = inventoryView;
    }
    public void handleNewIngredient(ActionEvent actionEvent) {
        String ingredientName = inventoryView.getNameText().getText();
        Double quantity = Double.valueOf(inventoryView.getQuantityText().getText());
        Ingredient.IngredientType type = inventoryView.getTypeComboBox().getValue();
        Ingredient.MeasurementUnit mUnit = inventoryView.getMeasurementUnitComboBox().getValue();
        inventoryModel.addIngredient(ingredientName,quantity,type,mUnit);

    }



    /**
     * End of Inventory Actions
     */

    /**
     * Here would be Recipe actions
     */
    public void setRecipeModel(RecipeModel newModel){this.recipeModel = newModel;}

    public void setRecipeMakerView(RecipeMakerView recipeMakerView) {
        this.recipeMakerView = recipeMakerView;
    }

    public void setRecipeInteractiveModel(RecipeInteractiveModel recipeInteractiveModel) {
        this.recipeInteractiveModel = recipeInteractiveModel;
    }
    public void openRecipeMakerScreen(ActionEvent event){
        this.startupMVC.selectRecipeMaker();
        this.startupMVC.modelChanged();

        //i dont know is this is a good solution but it refreshes the menu to stay updated with inventory
        this.inventoryModel.notifySubs();
        this.recipeMakerView.updateMenuHandlers(this);//and updates the handlers for every new menu item
    }

    /**
     * When the addRecipe button is hit, take all the information from the fields and create a new recipe
     * take ingredients iModel and use the map to store them in the recipie
     * @param event
     */
    public void addRecipie(ActionEvent event){

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

        recipeModel.addNewRecipe(recipeName,recipePrice,recipeDesc,recipeInstruction,recipePrepTime,ingredientMap);

        //clear all the fields
        recipeMakerView.getRecipeName().clear();
        recipeMakerView.getRecipePrice().clear();
        recipeMakerView.getRecipeDescription().clear();
        recipeMakerView.getRecipeInstruction().clear();
        recipeMakerView.getRecipePrep().clear();

        //clear recipe iModel
        recipeInteractiveModel.clearList();
        recipieAddedPopUp(recipeName);
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
        recipeMakerView.getSelectedIngredient().setEditable(true);
        recipeMakerView.getSelectedIngredient().setText(selectedIngredientName);
        recipeMakerView.getSelectedIngredient().setEditable(false);

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
    public void addIngredientToRecipie(ActionEvent actionEvent) {
        String ingredientName = recipeMakerView.getSelectedIngredient().getText();
        Ingredient ingredient = searchIngredientByName(ingredientName);
        Double recipeQuantity = Double.valueOf(recipeMakerView.getEnterMeasurementField().getText());
        //find the ingredient;
        recipeInteractiveModel.addToMap(ingredient,recipeQuantity);
        //add ingredient to temp list of ingredients to be displayed\
        recipeMakerView.getEnterMeasurementField().clear();

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
        this.startupMVC.selectNoteView();
        this.startupMVC.modelChanged();
    }
    public void openCustomizeView(ActionEvent event){
        this.startupMVC.selectCustomize();
        this.startupMVC.modelChanged();
    }
    public void openViewOrder(ActionEvent event){
        this.startupMVC.selectViewOrder();
        this.startupMVC.modelChanged();
    }



}
