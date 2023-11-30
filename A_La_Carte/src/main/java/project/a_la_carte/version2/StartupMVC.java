package project.a_la_carte.version2;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

import project.a_la_carte.version2.kitchen.*;
import project.a_la_carte.version2.managerSide.RestaurantInfo.RestaurantModel;
import project.a_la_carte.version2.managerSide.inventory.*;
import project.a_la_carte.version2.managerSide.recipe.*;
import project.a_la_carte.version2.managerSide.staff.StaffModel;
import project.a_la_carte.version2.menuItems.*;
import project.a_la_carte.version2.serverSide.*;

/**
 * StartupMVC will be used as a Scene and will merge all MVC classes together
 * Can also work as the main startup screen
 */
public class StartupMVC extends StackPane {
    StackPane startUpView;
    SignUpView signUpView;
    SignInView signInView;
    InventoryModel inventoryModel;
    RecipeModel recipeModel;
    RecipeInteractiveModel recipeInteractiveModel;
    ServerModel serverModel;
    KitchenModel kitchenModel;
    MenuItemModel menuItemModel;
    StaffModel staffModel;
    RestaurantModel restaurantModel;
    Button workerButton;
    String selectedScreen = "startUp";
    ProgramController programController;
    public StartupMVC(){
        this.setMaxSize(5000,2500);
        this.setPrefSize(1000,500);
        programController = new ProgramController();

        //---------------------------------------------------------

        //------------------------------------------
        //---------INVENTORY MVC-------------------------------------
        inventoryModel = new InventoryModel();

        //---------------------------------------------------------

        //-----------------------------------------------------------------
        //-----------RECIPE SIDE MVC-------------------------------
        recipeModel = new RecipeModel();
        recipeInteractiveModel = new RecipeInteractiveModel();

        //--------------------------------------------------------------

        //----------------------------------------------------
        //--------------SERVER SIDE---------------------------------
        serverModel = new ServerModel();
        //-----------------------------------------------------

        //----------------------------------------------------
        //-----------KITCHEN SIDE----------------------------
        kitchenModel = new KitchenModel();
        //----------------------------------------------------

        //------------------------------------------------------
        //-----------Menu Item------------------------
        menuItemModel = new MenuItemModel();
        menuItemModel.setRecipeArrayList(recipeModel.getRecipeList());
        //--------------------------------------------------------

        //-----------Staff Model------------------------
        staffModel = new StaffModel();
        //--------------------------------------------------------

        //-----------Restaurant Model------------------------
        restaurantModel = new RestaurantModel();
        //--------------------------------------------------------

        //------------------------------------------------
        //----------START UP MVC--------------------------

        signUpView = new SignUpView();
        signUpView.setPrefSize(600,300);
        signUpView.setController(programController);

        programController.setSignUpView(signUpView);

        signInView = new SignInView();
        signInView.setPrefSize(600,300);
        signInView.setMaxSize(2000,1500);
        signInView.setController(programController);
        programController.setSignInView(signInView);


        startUpView = new StackPane();
        startUpView.setPrefSize(2000,1500);


        Label welcomeLabel = new Label("A La Carte Program Main Menu");
        welcomeLabel.setFont(new Font(40));
        HBox welcomeBox = new HBox(welcomeLabel);
        welcomeBox.setPrefWidth(1000);
        welcomeBox.setAlignment(Pos.CENTER);
        welcomeBox.setStyle("-fx-border-color: black;\n");

        workerButton = new Button("Worker");
        workerButton.setFont(new Font(30));
        workerButton.setPrefSize(300,30);


        startUpView.getChildren().add(signInView);

        VBox managerSideVBox = new VBox(startUpView);
        //managerSideVBox.setPrefSize(600,500);
        managerSideVBox.setPrefSize(600,500);
        managerSideVBox.setStyle("-fx-border-color: black;\n");

        Label workerLabel = new Label("Worker Sign In");
        workerLabel.setFont(new Font(30));

        VBox workerBox = new VBox(workerLabel,workerButton);
        workerBox.setPrefSize(400,500);
        workerBox.setAlignment(Pos.CENTER);
        workerBox.setStyle("-fx-border-color: black;\n");

        HBox alignBody = new HBox(managerSideVBox,workerBox);
        alignBody.setMaxSize(5000,2500);
        HBox.setHgrow(managerSideVBox, Priority.ALWAYS);
        HBox.setHgrow(workerBox, Priority.ALWAYS);

        VBox startUpAlign = new VBox(welcomeBox,alignBody);
        startUpAlign.setMaxSize(5000,5000);
        VBox.setVgrow(alignBody, Priority.ALWAYS);

        programController.setStartupMVC(this);
        this.setController(programController);
        this.getChildren().add(startUpAlign);
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
    public StaffModel getStaffModel() {
        return this.staffModel;
    }
    public RestaurantModel getRestaurantModel(){return this.restaurantModel;}

    public void setController(ProgramController controller){
        workerButton.setOnAction(controller::startWorkerView);

    }
    public void selectStartup(){
        this.selectedScreen ="startUp";
    }
    public void selectSignUp(){this.selectedScreen ="signUp";}
    //Decided to do this for now since multiple screens is not in our todo for the prototype
    public void modelChanged() {
        this.startUpView.getChildren().clear();
        switch (this.selectedScreen) {
            case "startUp" -> this.startUpView.getChildren().add(signInView);
            case "signUp" -> {
                this.startUpView.getChildren().add(signUpView);
                System.out.println("Pressed");
            }


        }
    }
}
