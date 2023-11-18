package project.a_la_carte.version2;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

import project.a_la_carte.version2.kitchen.*;
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
    VBox signUpView;
    VBox signInView;
    InventoryModel inventoryModel;
    RecipeModel recipeModel;
    RecipeInteractiveModel recipeInteractiveModel;
    ServerModel serverModel;
    KitchenModel kitchenModel;
    MenuItemModel menuItemModel;
    StaffModel staffModel;
    Button managerButton;
    Button createAccButton;
    Button signUpButton;
    Button workerButton;
    Button backButton;
    TextField usernameText;
    TextField passwordText;
    String selectedScreen = "startUp";
    ProgramController programController;
    public StartupMVC(){
        this.setMaxSize(1000,500);
        programController = new ProgramController();

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
        //--------------------------------------------------------

        //-----------Staff Model------------------------
        staffModel = new StaffModel();
        //--------------------------------------------------------

        //------------------------------------------------
        //----------START UP MVC--------------------------
        startUpView = new StackPane();
        startUpView.setMaxSize(600,300);

        signUpView = new VBox();
        signUpView.setPrefSize(600,300);

        signInView = new VBox();
        signInView.setPrefSize(600,300);

        Label welcomeLabel = new Label("A La Carte Program Main Menu");
        welcomeLabel.setFont(new Font(40));
        HBox welcomeBox = new HBox(welcomeLabel);
        welcomeBox.setPrefWidth(1000);
        welcomeBox.setAlignment(Pos.CENTER);
        welcomeBox.setStyle("-fx-border-color: black;\n");

        Label managerLabel = new Label("Manager Login");
        managerLabel.setFont(new Font(20));
        Label userLabel = new Label("Username: ");
        usernameText = new TextField();
        usernameText.setPrefWidth(400);

        Label passLabel = new Label("Password: ");
        passwordText = new TextField();
        passwordText.setPrefWidth(400);

        VBox textBox = new VBox(managerLabel, userLabel,usernameText,passLabel,passwordText);
        textBox.setPadding(new Insets(5));
        textBox.setPrefSize(600,200);

        managerButton = new Button("LogIn");
        managerButton.setFont(new Font(20));
        managerButton.setPrefSize(200,20);
        createAccButton = new Button("Sign-up");
        createAccButton.setFont(new Font(20));
        createAccButton.setPrefSize(200,20);
        backButton = new Button("Back");
        backButton.setFont(new Font(20));
        backButton.setPrefSize(200,20);
        workerButton = new Button("Worker");
        workerButton.setFont(new Font(30));
        workerButton.setPrefSize(300,30);

        signUpButton = new Button("Sign-up");
        signUpButton.setStyle("-fx-underline: true;-fx-border-color: transparent;-fx-background-color: transparent;-fx-text-fill: black;\n");
        signUpButton.setOnMouseEntered((event -> {
            signUpButton.setStyle("-fx-underline: true;-fx-border-color: transparent;-fx-background-color: transparent;-fx-text-fill: blue;\n");
        }));
        signUpButton.setOnMouseExited((event -> {
            signUpButton.setStyle("-fx-underline: true;-fx-border-color: transparent;-fx-background-color: transparent;-fx-text-fill: black;\n");
        }));
        signUpView.getChildren().addAll(createAccButton,backButton);
        signUpView.setAlignment(Pos.TOP_CENTER);
        signUpView.setSpacing(5);
        signInView.getChildren().addAll(managerButton,signUpButton);
        signInView.setAlignment(Pos.TOP_CENTER);

        startUpView.getChildren().add(signInView);

        VBox managerSideVBox = new VBox(textBox,startUpView);
        managerSideVBox.setPrefSize(600,500);
        managerSideVBox.setStyle("-fx-border-color: black;\n");

        Label workerLabel = new Label("Worker Sign In");
        workerLabel.setFont(new Font(30));

        VBox workerBox = new VBox(workerLabel,workerButton);
        workerBox.setPrefSize(400,500);
        workerBox.setAlignment(Pos.CENTER);
        workerBox.setStyle("-fx-border-color: black;\n");

        HBox alignBody = new HBox(managerSideVBox,workerBox);
        alignBody.setPrefSize(1000,500);

        VBox startUpAlign = new VBox(welcomeBox,alignBody);
        startUpAlign.setPrefSize(1000,500);

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

    public void setController(ProgramController controller){
        managerButton.setOnAction(controller::startManagerMainView);
        workerButton.setOnAction(controller::startWorkerView);
        backButton.setOnAction(controller::openStartUpMVC);
        signUpButton.setOnAction(controller::openSignUp);
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
            case "signUp" -> this.startUpView.getChildren().add(signUpView);
        }
    }
}
