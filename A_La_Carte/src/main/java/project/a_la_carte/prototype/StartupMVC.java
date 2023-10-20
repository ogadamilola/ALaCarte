package project.a_la_carte.prototype;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
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
    ViewOrder viewOrder;
    Button inventoryButton;
    Button recipeListButton;
    Button menuViewButton;
    boolean startUp = true;
    boolean inventory = false;
    boolean recipeMaker = false;
    boolean recipeList = false;
    boolean menu = false;
    boolean note = false;
    boolean customize = false;
    boolean order = false;
    public StartupMVC(){
        this.setMaxSize(1000,500);
        ProgramController programController = new ProgramController();

        //------------------------------------------
        //---------INVENTORY MVC-------------------------------------
        InventoryModel inventoryModel = new InventoryModel();
        inventoryView = new InventoryView();

        inventoryView.setController(programController);
        inventoryView.setModel(inventoryModel);

        inventoryModel.setView(inventoryView);

        programController.setInventoryModel(inventoryModel);
        programController.setInventoryView(inventoryView);
        //---------------------------------------------------------

        //-----------------------------------------------------------------
        //-----------RECIPE SIDE MVC-------------------------------
        recipeMakerView = new RecipeMakerView();
        RecipeModel recipeModel = new RecipeModel();

        recipeMakerView.setRecipeModel(recipeModel);
        recipeMakerView.setRecipeMakerController(programController);

        recipeModel.setRecipeMakerView(recipeMakerView);

        recipeListView = new RecipeListView();

        recipeListView.setRecipeListModel(recipeModel);
        recipeListView.setController(programController);

        recipeModel.setRecipeListView(recipeListView);

        programController.setRecipeModel(recipeModel);
        //--------------------------------------------------------------

        //----------------------------------------------------
        //--------------SERVER SIDE---------------------------------
        this.menuView = new MenuView();
        this.noteView = new NoteView();
        this.customizeView = new CustomizeView();
        this.viewOrder = new ViewOrder();
        ServerModel serverModel = new ServerModel();

        serverModel.setCustomizeView(customizeView);
        serverModel.setMenuView(menuView);
        serverModel.setNoteView(noteView);
        serverModel.setViewOrder(viewOrder);

        this.menuView.setController(programController);
        this.menuView.setServerModel(serverModel);

        this.noteView.setController(programController);
        this.noteView.setServerModel(serverModel);

        this.customizeView.setController(programController);
        this.customizeView.setServerModel(serverModel);

        this.viewOrder.setController(programController);
        this.viewOrder.setServerModel(serverModel);

        programController.setServerModel(serverModel);

        //-----------------------------------------------------

        //------------------------------------------------
        //----------START UP MVC--------------------------
        startUpView = new StackPane();
        startUpView.setMaxSize(1000,500);
        Label welcomeLabel = new Label("A La Carte Program Main Menu");
        welcomeLabel.setFont(new Font(40));

        inventoryButton = new Button("Go To Inventory");
        inventoryButton.setFont(new Font(30));
        recipeListButton = new Button("Go To Recipe List");
        recipeListButton.setFont(new Font(30));
        menuViewButton = new Button("Go To Server Menu");
        menuViewButton.setFont(new Font(30));

        VBox startUpAlign = new VBox(welcomeLabel,inventoryButton,recipeListButton,menuViewButton);
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
    }
    public void selectStartup(){
        this.startUp = true;
        this.inventory = false;
        this.recipeMaker = false;
        this.recipeList = false;
        this.menu = false;
        this.note = false;
        this.customize = false;
        this.order = false;
    }
    public void selectInventory(){
        this.startUp = false;
        this.inventory = true;
        this.recipeMaker = false;
        this.recipeList = false;
        this.menu = false;
        this.note = false;
        this.customize = false;
        this.order = false;
    }
    public void selectRecipeList(){
        this.startUp = false;
        this.inventory = false;
        this.recipeMaker = false;
        this.recipeList = true;
        this.menu = false;
        this.note = false;
        this.customize = false;
        this.order = false;
    }
    public void selectRecipeMaker(){
        this.startUp = false;
        this.inventory = false;
        this.recipeMaker = true;
        this.recipeList = false;
        this.menu = false;
        this.note = false;
        this.customize = false;
        this.order = false;
    }
    public void selectMenuView(){
        this.startUp = false;
        this.inventory = false;
        this.recipeMaker = false;
        this.recipeList = false;
        this.menu = true;
        this.note = false;
        this.customize = false;
        this.order = false;
    }
    public void selectNoteView(){
        this.startUp = false;
        this.inventory = false;
        this.recipeMaker = false;
        this.recipeList = false;
        this.menu = false;
        this.note = true;
        this.customize = false;
        this.order = false;
    }
    public void selectCustomize(){
        this.startUp = false;
        this.inventory = false;
        this.recipeMaker = false;
        this.recipeList = false;
        this.menu = false;
        this.note = false;
        this.customize = true;
        this.order = false;
    }
    public void selectViewOrder(){
        this.startUp = false;
        this.inventory = false;
        this.recipeMaker = false;
        this.recipeList = false;
        this.menu = false;
        this.note = false;
        this.customize = false;
        this.order = true;
    }
    //Decided to do this for now since multiple screens is not in our todo for the prototype
    public void modelChanged() {
        this.getChildren().clear();
        if (startUp){
            this.getChildren().add(startUpView);
        }
        else if (recipeMaker) {
            this.getChildren().add(recipeMakerView);
        }
        else if (inventory) {
            this.getChildren().add(inventoryView);
        }
        else if(recipeList){
            this.getChildren().add(recipeListView);
        }
        else if (menu){
            this.getChildren().add(menuView);
        }
        else if (note){
            this.getChildren().add(noteView);
        }
        else if (customize){
            this.getChildren().add(customizeView);
        }
        else if (order){
            this.getChildren().add(viewOrder);
        }
    }
}
