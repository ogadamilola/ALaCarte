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
 * StartupMVC will be used as a Scene, merge the MVCs together and will also work as the programs main menu
 */
public class StartupMVC extends StackPane {
    StackPane startUpView;
    SignUpView signUpView;
    SignInView signInView;
    //--------------------------------------------------
    //Starting the program's models
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
    ProgramController programController; //The program's controller
    public StartupMVC(){
        this.setMaxSize(5000,2500);
        this.setPrefSize(1000,500);
        programController = new ProgramController();

        //---------INVENTORY-------------------------------------
        inventoryModel = new InventoryModel();

        //-----------RECIPE-------------------------------
        recipeModel = new RecipeModel();
        recipeInteractiveModel = new RecipeInteractiveModel();

        //--------------SERVER SIDE---------------------------------
        serverModel = new ServerModel();

        //-----------KITCHEN SIDE----------------------------
        kitchenModel = new KitchenModel();

        //-----------MENU ITEM------------------------
        menuItemModel = new MenuItemModel();
        menuItemModel.setRecipeArrayList(recipeModel.getRecipeList());

        //-----------Staff Model------------------------
        staffModel = new StaffModel();

        //-----------Restaurant Model------------------------
        restaurantModel = new RestaurantModel();

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

    /**
     * The get method for the program's KitchenModel
     * @return KitchenModel
     */
    public KitchenModel getKitchenModel(){
        return this.kitchenModel;
    }

    /**
     * The get method for the program's ServerModel
     * @return ServerModel
     */
    public ServerModel getServerModel(){
        return this.serverModel;
    }

    /**
     * The get method for the program's Controller
     * @return ProgramController
     */
    public ProgramController getController(){
        return this.programController;
    }

    /**
     * The get method for the program's InventoryModel
     * @return InventoryModel
     */
    public InventoryModel getInventoryModel(){
        return this.inventoryModel;
    }

    /**
     * The get method for the program's RecipeModel
     * @return RecipeModel
     */
    public RecipeModel getRecipeModel(){
        return this.recipeModel;
    }

    /**
     * The get method for the program's RecipeInteractiveModel
     * @return RecipeInteractiveModel
     */
    public RecipeInteractiveModel getRecipeInteractiveModel(){
        return this.recipeInteractiveModel;
    }

    /**
     * The get method for the program's MenuItemModel
     * @return MenuItemModel
     */
    public MenuItemModel getMenuItemModel(){
        return this.menuItemModel;
    }

    /**
     * The get method for the program's StaffModel
     * @return StaffModel
     */
    public StaffModel getStaffModel() {
        return this.staffModel;
    }

    /**
     * The get method for the program's RestaurantModel
     * @return RestaurantModel
     */
    public RestaurantModel getRestaurantModel(){return this.restaurantModel;}

    /**
     * Sets the controller that will be used in the Program
     */
    public void setController(ProgramController controller){
        workerButton.setOnAction(controller::startWorkerView);

    }

    /**
     * Used for changing this view to StartUpMVC
     */
    public void selectStartup(){
        this.selectedScreen ="startUp";
    }

    /**
     * Used for changing this view for signing up
     */
    public void selectSignUp(){this.selectedScreen ="signUp";}

    /**
     * Changing the view
     */
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
