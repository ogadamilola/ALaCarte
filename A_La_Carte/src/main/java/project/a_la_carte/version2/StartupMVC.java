package project.a_la_carte.version2;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

import project.a_la_carte.version2.kitchen.*;
import project.a_la_carte.version2.managerSide.inventory.*;
import project.a_la_carte.version2.managerSide.recipe.*;
import project.a_la_carte.version2.menuItems.*;
import project.a_la_carte.version2.serverSide.*;

/**
 * StartupMVC will be used as a Scene and will merge all MVC classes together
 * Can also work as the main startup screen
 */
public class StartupMVC extends StackPane {
    StackPane startUpView;
    InventoryView inventoryView;
    InventoryModel inventoryModel;
    RecipeModel recipeModel;
    RecipeInteractiveModel recipeInteractiveModel;
    RecipeMakerView recipeMakerView;
    RecipeListView recipeListView;
    ServerModel serverModel;
    MenuView menuView;
    NoteView noteView;
    CustomizeView customizeView;
    KitchenModel kitchenModel;
    KitchenView kitchenView;
    MenuItemModel menuItemModel;
    MenuItemListView menuItemListView;
    MenuItemMakerView menuItemMakerView;
    Button kitchenButton;
    Button inventoryButton;
    Button recipeListButton;
    Button menuViewButton;
    Button menuItemListButton;
    Button managerButton;
    Button workerButton;
    String selectedScreen = "startUp";
    ProgramController programController;
    public StartupMVC(){
        this.setMaxSize(1000,500);
        programController = new ProgramController();

        //------------------------------------------
        //---------INVENTORY MVC-------------------------------------
        inventoryModel = new InventoryModel();
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
        recipeModel = new RecipeModel();
        recipeInteractiveModel = new RecipeInteractiveModel();



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
        serverModel = new ServerModel();

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
        kitchenModel = new KitchenModel();

        kitchenModel.setKitchenView(this.kitchenView);

        this.kitchenView.setKitchenModel(kitchenModel);
        this.kitchenView.setController(programController);

        programController.setKitchenModel(kitchenModel);
        //----------------------------------------------------

        //------------------------------------------------------
        //-----------Menu Item------------------------
        menuItemModel = new MenuItemModel();
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
        inventoryButton.setStyle("-fx-border-color: transparent;-fx-background-color: transparent;\n");
        inventoryButton.setOnMouseEntered((event -> {
            inventoryButton.setStyle("-fx-text-fill: blue;-fx-underline: true;-fx-border-color: transparent;-fx-background-color: transparent;\n");
        }));
        inventoryButton.setOnMouseExited((event -> {
            inventoryButton.setStyle("-fx-text-fill: black;-fx-underline: false;-fx-border-color: transparent;-fx-background-color: transparent;\n");
        }));
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
        managerButton = new Button("Manager");
        managerButton.setFont(new Font(30));
        managerButton.setPrefSize(300,30);
        workerButton = new Button("Worker");
        workerButton.setFont(new Font(30));
        workerButton.setPrefSize(300,30);

        VBox startUpAlign = new VBox(welcomeLabel,managerButton, workerButton);
        startUpAlign.setPrefSize(1000,500);
        startUpAlign.setPadding(new Insets(20,20,20,20));
        startUpAlign.setAlignment(Pos.CENTER);
        startUpView.getChildren().add(startUpAlign);

        programController.setStartupMVC(this);
        this.setController(programController);
        this.getChildren().add(startUpView);
    }
    public KitchenModel getKitchenModel(){
        return this.kitchenModel;
    }
    public ServerModel getServerModel(){
        return this.serverModel;
    }
    public ProgramController getController(){
        return this.programController;
    }
    public InventoryModel getInventoryModel(){
        return this.inventoryModel;
    }
    public RecipeModel getRecipeModel(){
        return this.recipeModel;
    }
    public RecipeInteractiveModel getRecipeInteractiveModel(){
        return this.recipeInteractiveModel;
    }
    public MenuItemModel getMenuItemModel(){
        return this.menuItemModel;
    }
    public void setController(ProgramController controller){
        inventoryButton.setOnAction(controller::openInventoryScreen);
        recipeListButton.setOnAction(controller::openRecipeList);
        menuViewButton.setOnAction(controller::openMenuView);
        kitchenButton.setOnAction(controller::openKitchenView);
        menuItemListButton.setOnAction(controller::openMenuListView);
        managerButton.setOnAction(controller::startManagerMainView);
        workerButton.setOnAction(controller::startWorkerView);
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
