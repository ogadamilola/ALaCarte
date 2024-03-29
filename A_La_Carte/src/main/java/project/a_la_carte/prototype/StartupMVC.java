package project.a_la_carte.prototype;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import project.a_la_carte.prototype.kitchen.side.KitchenModel;
import project.a_la_carte.prototype.kitchen.side.KitchenView;
import project.a_la_carte.prototype.menu.items.MenuItemListView;
import project.a_la_carte.prototype.menu.items.MenuItemMakerView;
import project.a_la_carte.prototype.menu.items.MenuItemModel;
import project.a_la_carte.prototype.recipe.maker.inventory.*;
import project.a_la_carte.prototype.server.side.*;

/**
 * StartupMVC will be used as a Scene and will merge all MVC classes together
 * Can also work as the main startup screen
 */
public class StartupMVC extends StackPane {
    StackPane startUpView;
    InventoryView inventoryView;
    RecipeMakerView recipeMakerView;
    RecipeListView recipeListView;
    MenuView menuView;
    NoteView noteView;
    CustomizeView customizeView;
    KitchenView kitchenView;
    MenuItemListView menuItemListView;
    MenuItemMakerView menuItemMakerView;
    Button kitchenButton;
    Button inventoryButton;
    Button recipeListButton;
    Button menuViewButton;
    Button menuItemListButton;
    Button menuMakerButton;
    String selectedScreen = "startUp";
    public StartupMVC(){
        this.setMaxSize(1000,500);
        ProgramController programController = new ProgramController();

        //------------------------------------------
        //---------INVENTORY MVC-------------------------------------
        InventoryModel inventoryModel = new InventoryModel();
        inventoryView = new InventoryView();

        inventoryView.setController(programController);
        inventoryView.setModel(inventoryModel);

        //no longer needed
        //inventoryModel.setView(inventoryView);
        inventoryModel.addSub(inventoryView);


        programController.setInventoryModel(inventoryModel);
        programController.setInventoryView(inventoryView);
        //---------------------------------------------------------

        //-----------------------------------------------------------------
        //-----------RECIPE SIDE MVC-------------------------------
        recipeMakerView = new RecipeMakerView();
        RecipeModel recipeModel = new RecipeModel();
        RecipeInteractiveModel recipeInteractiveModel = new RecipeInteractiveModel();



        recipeMakerView.setRecipeModel(recipeModel);
        recipeMakerView.setRecipeMakerController(programController);

        recipeModel.setRecipeMakerView(recipeMakerView);

        recipeListView = new RecipeListView();


        recipeListView.setController(programController);

        inventoryModel.addSub(recipeMakerView);

        recipeInteractiveModel.addSubscriber(recipeMakerView);
        recipeInteractiveModel.addSubscriber(recipeListView);
        recipeModel.setRecipeListView(recipeListView);

        recipeModel.addSubscriber(recipeListView);
        programController.setRecipeInteractiveModel(recipeInteractiveModel);
        programController.setRecipeModel(recipeModel);
        programController.setRecipeMakerView(recipeMakerView);
        programController.setRecipeListView(recipeListView);
        //--------------------------------------------------------------

        //----------------------------------------------------
        //--------------SERVER SIDE---------------------------------
        this.menuView = new MenuView();
        this.noteView = new NoteView();
        this.customizeView = new CustomizeView();
        ServerModel serverModel = new ServerModel();

        serverModel.setCustomizeView(customizeView);
        serverModel.setMenuView(menuView);
        serverModel.setNoteView(noteView);

        this.menuView.setController(programController);
        this.menuView.setServerModel(serverModel);

        this.noteView.setController(programController);
        this.noteView.setServerModel(serverModel);

        this.customizeView.setController(programController);
        this.customizeView.setServerModel(serverModel);

        programController.setServerModel(serverModel);

        //-----------------------------------------------------

        //----------------------------------------------------
        //-----------KITCHEN SIDE----------------------------
        this.kitchenView = new KitchenView();
        KitchenModel kitchenModel = new KitchenModel();

        kitchenModel.setKitchenView(this.kitchenView);

        this.kitchenView.setKitchenModel(kitchenModel);
        this.kitchenView.setController(programController);

        programController.setKitchenModel(kitchenModel);
        //----------------------------------------------------

        //------------------------------------------------------
        //-----------Menu Item------------------------
        MenuItemModel menuItemModel = new MenuItemModel();
        this.menuItemListView = new MenuItemListView();
        this.menuItemMakerView = new MenuItemMakerView();

        this.menuItemListView.setController(programController);
        this.menuItemListView.setMenuItemModel(menuItemModel);

        this.menuItemMakerView.setMenuItemModel(menuItemModel);
        this.menuItemMakerView.setController(programController);

        menuItemModel.addSubscriber(menuItemListView);
        menuItemModel.addSubscriber(menuItemMakerView);

        programController.setMenuItemModel(menuItemModel);
        programController.setMenuItemMakerView(this.menuItemMakerView);
        //--------------------------------------------------------

        //------------------------------------------------
        //----------START UP MVC--------------------------
        startUpView = new StackPane();
        startUpView.setMaxSize(1000,500);
        Label welcomeLabel = new Label("A La Carte Program Main Menu");
        welcomeLabel.setFont(new Font(40));

        inventoryButton = new Button("Inventory");
        inventoryButton.setFont(new Font(30));
        inventoryButton.setPrefSize(300,30);
        recipeListButton = new Button("Recipe List");
        recipeListButton.setFont(new Font(30));
        recipeListButton.setPrefSize(300,30);
        menuViewButton = new Button("Server Menu");
        menuViewButton.setFont(new Font(30));
        menuViewButton.setPrefSize(300,30);
        kitchenButton = new Button("Kitchen View");
        kitchenButton.setFont(new Font(30));
        kitchenButton.setPrefSize(300,30);
        menuItemListButton = new Button("Menu List");
        menuItemListButton.setFont(new Font(30));
        menuItemListButton.setPrefSize(300,30);

        VBox startUpAlign = new VBox(welcomeLabel,inventoryButton,recipeListButton,menuViewButton,kitchenButton,menuItemListButton);
        startUpAlign.setPrefSize(1000,500);
        startUpAlign.setPadding(new Insets(20,20,20,20));
        startUpAlign.setAlignment(Pos.CENTER);
        startUpView.getChildren().add(startUpAlign);

        programController.setStartupMVC(this);
        this.setController(programController);
        this.getChildren().add(startUpView);
    }
    public void setController(ProgramController controller){
        inventoryButton.setOnAction(controller::openInventoryScreen);
        recipeListButton.setOnAction(controller::openRecipeList);
        menuViewButton.setOnAction(controller::openMenuView);
        kitchenButton.setOnAction(controller::openKitchenView);
        menuItemListButton.setOnAction(controller::openMenuListView);
    }
    public void selectStartup(){
        this.selectedScreen ="startUp";
    }
    public void selectInventory(){
        this.selectedScreen = "inventory";
    }
    public void selectRecipeList(){
        this.selectedScreen = "recipeList";
    }
    public void selectRecipeMaker(){
        this.selectedScreen = "recipeMaker";
    }
    public void selectMenuView(){
        this.selectedScreen = "menu";
    }
    public void selectNoteView(){
        this.selectedScreen = "note";
    }
    public void selectCustomize(){
        this.selectedScreen = "customize";
    }
    public void selectKitchenView(){
        this.selectedScreen = "kitchen";
    }
    public void selectMenuItemList(){this.selectedScreen ="menuList";}
    public void selectMenuMakerView(){this.selectedScreen = "menuMaker";}
    //Decided to do this for now since multiple screens is not in our todo for the prototype
    public void modelChanged() {
        this.getChildren().clear();
        switch (this.selectedScreen) {
            case "startUp" -> this.getChildren().add(startUpView);
            case "recipeMaker" -> this.getChildren().add(recipeMakerView);
            case "inventory" -> this.getChildren().add(inventoryView);
            case "recipeList" -> this.getChildren().add(recipeListView);
            case "menu" -> this.getChildren().add(menuView);
            case "note" -> this.getChildren().add(noteView);
            case "customize" -> this.getChildren().add(customizeView);
            case "kitchen" -> this.getChildren().add(kitchenView);
            case "menuList" -> this.getChildren().add(menuItemListView);
            case "menuMaker" -> this.getChildren().add(menuItemMakerView);
        }
    }
}
