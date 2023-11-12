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
    InventoryModel inventoryModel;
    RecipeModel recipeModel;
    RecipeInteractiveModel recipeInteractiveModel;
    ServerModel serverModel;
    KitchenModel kitchenModel;
    MenuItemModel menuItemModel;
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

        programController.setInventoryModel(inventoryModel);
        //---------------------------------------------------------

        //-----------------------------------------------------------------
        //-----------RECIPE SIDE MVC-------------------------------
        recipeModel = new RecipeModel();
        recipeInteractiveModel = new RecipeInteractiveModel();

        programController.setRecipeInteractiveModel(recipeInteractiveModel);
        programController.setRecipeModel(recipeModel);
        //--------------------------------------------------------------

        //----------------------------------------------------
        //--------------SERVER SIDE---------------------------------
        serverModel = new ServerModel();

        programController.setServerModel(serverModel);

        //-----------------------------------------------------

        //----------------------------------------------------
        //-----------KITCHEN SIDE----------------------------
        kitchenModel = new KitchenModel();

        programController.setKitchenModel(kitchenModel);
        //----------------------------------------------------

        //------------------------------------------------------
        //-----------Menu Item------------------------
        menuItemModel = new MenuItemModel();

        programController.setMenuItemModel(menuItemModel);
        //--------------------------------------------------------

        //------------------------------------------------
        //----------START UP MVC--------------------------
        startUpView = new StackPane();
        startUpView.setMaxSize(1000,500);
        Label welcomeLabel = new Label("A La Carte Program Main Menu");
        welcomeLabel.setFont(new Font(40));

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
        managerButton.setOnAction(controller::startManagerMainView);
        workerButton.setOnAction(controller::startWorkerView);
    }
    public void selectStartup(){
        this.selectedScreen ="startUp";
    }
    //Decided to do this for now since multiple screens is not in our todo for the prototype
    public void modelChanged() {
        this.getChildren().clear();
        switch (this.selectedScreen) {
            case "startUp" -> this.getChildren().add(startUpView);
        }
    }
}
