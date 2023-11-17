package project.a_la_carte.version2;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import project.a_la_carte.version2.managerSide.inventory.InventoryView;
import project.a_la_carte.version2.managerSide.recipe.*;
import project.a_la_carte.version2.managerSide.staff.*;
import project.a_la_carte.version2.menuItems.*;

public class ManagerMainView extends StackPane {
    InventoryView inventoryView;
    RecipeMakerView recipeMakerView;
    RecipeListView recipeListView;
    MenuItemListView menuItemListView;
    MenuItemMakerView menuItemMakerView;
    StaffInfoView staffInfoView;
    StackPane managerMainScreen;
    Button inventoryButton;
    Button recipeButton;
    Button menuItemButton;
    Button staffInfoButton;
    String selectedScreen = "";
    public ManagerMainView(StartupMVC startupMVC){
        this.setPrefSize(1000,500);

        Label welcomeLabel = new Label("A La Carte Manager View");
        welcomeLabel.setFont(new Font(40));

        inventoryView = new InventoryView();
        inventoryView.setController(startupMVC.getController());
        inventoryView.setModel(startupMVC.getInventoryModel());

        startupMVC.getInventoryModel().addSub(inventoryView);
        startupMVC.getController().setInventoryView(inventoryView);

        recipeMakerView = new RecipeMakerView();
        recipeMakerView.setRecipeModel(startupMVC.getRecipeModel());
        recipeMakerView.setRecipeMakerController(startupMVC.getController());

        recipeListView = new RecipeListView();
        recipeListView.setController(startupMVC.getController());
        recipeListView.setRecipeListModel(startupMVC.getRecipeModel());

        staffInfoView = new StaffInfoView();
        staffInfoView.setController(startupMVC.getController());


        startupMVC.getRecipeModel().addSubscriber(recipeListView);
        startupMVC.getRecipeModel().setRecipeListView(recipeListView);
        startupMVC.getRecipeModel().setRecipeMakerView(recipeMakerView);
        startupMVC.getRecipeInteractiveModel().addSubscriber(recipeMakerView);
        startupMVC.getRecipeInteractiveModel().addSubscriber(recipeListView);
        startupMVC.getInventoryModel().addSub(recipeMakerView);
        startupMVC.getStaffModel().addSub(staffInfoView);

        startupMVC.getController().setRecipeMakerView(recipeMakerView);
        startupMVC.getController().setRecipeListView(recipeListView);
        startupMVC.getController().setStaffInfoView(staffInfoView);

        this.menuItemListView = new MenuItemListView();
        this.menuItemMakerView = new MenuItemMakerView();

        this.menuItemListView.setController(startupMVC.getController());
        this.menuItemListView.setMenuItemModel(startupMVC.getMenuItemModel());

        this.menuItemMakerView.setMenuItemModel(startupMVC.getMenuItemModel());
        this.menuItemMakerView.setController(startupMVC.getController());

        startupMVC.getMenuItemModel().addSubscriber(menuItemListView);
        startupMVC.getMenuItemModel().addSubscriber(menuItemMakerView);
        startupMVC.getController().setMenuItemMakerView(this.menuItemMakerView);

        inventoryButton = new Button("Inventory");
        inventoryButton.setFont(new Font(30));
        inventoryButton.setOnMouseEntered((event -> {
            inventoryButton.setStyle("-fx-text-fill: blue;-fx-underline: true;\n");
        }));
        inventoryButton.setOnMouseExited((event -> {
            inventoryButton.setStyle("-fx-text-fill: black;-fx-underline: false;\n");
        }));
        inventoryButton.setPrefSize(300,30);

        recipeButton = new Button("Recipe");
        recipeButton.setFont(new Font(30));
        recipeButton.setOnMouseEntered((event -> {
            recipeButton.setStyle("-fx-text-fill: blue;-fx-underline: true;\n");
        }));
        recipeButton.setOnMouseExited((event -> {
            recipeButton.setStyle("-fx-text-fill: black;-fx-underline: false;\n");
        }));
        recipeButton.setPrefSize(300,30);

        menuItemButton = new Button("Menu Items");
        menuItemButton.setFont(new Font(30));
        menuItemButton.setOnMouseEntered((event -> {
            menuItemButton.setStyle("-fx-text-fill: blue;-fx-underline: true;\n");
        }));
        menuItemButton.setOnMouseExited((event -> {
            menuItemButton.setStyle("-fx-text-fill: black;-fx-underline: false;\n");
        }));
        menuItemButton.setPrefSize(300,30);

        staffInfoButton = new Button("Staff Information");
        staffInfoButton.setFont(new Font(30));
        staffInfoButton.setOnMouseEntered((event -> {
            staffInfoButton.setStyle("-fx-text-fill: blue;-fx-underline: true;\n");
        }));
        staffInfoButton.setOnMouseExited((event -> {
            staffInfoButton.setStyle("-fx-text-fill: black;-fx-underline: false;\n");
        }));
        staffInfoButton.setPrefSize(300,30);

        managerMainScreen = new StackPane();
        managerMainScreen.setMaxSize(1000,500);
        VBox managerViewAlign = new VBox(welcomeLabel,inventoryButton,recipeButton,menuItemButton,staffInfoButton);
        managerViewAlign.setPrefSize(1000,500);
        managerViewAlign.setPadding(new Insets(20,20,20,20));
        managerViewAlign.setAlignment(Pos.CENTER);
        managerMainScreen.getChildren().add(managerViewAlign);

        startupMVC.getController().setManagerMainView(this);
        this.setController(startupMVC.getController());
        this.setStyle("-fx-border-color: black;\n");
        this.getChildren().add(managerMainScreen);
    }
    public void setController(ProgramController controller){
        inventoryButton.setOnAction(controller::openInventoryScreen);
        recipeButton.setOnAction(controller::openRecipeList);
        menuItemButton.setOnAction(controller::openMenuListView);
        staffInfoButton.setOnAction(controller::openStaffInfoView);
    }
    public void selectManagerMenu(){this.selectedScreen = "managerView";}
    public void selectInventory(){
        this.selectedScreen = "inventory";
    }
    public void selectRecipeList(){
        this.selectedScreen = "recipeList";
    }
    public void selectRecipeMaker(){
        this.selectedScreen = "recipeMaker";
    }
    public void selectMenuItemList(){this.selectedScreen ="menuList";}
    public void selectMenuMakerView(){this.selectedScreen = "menuMaker";}
    public void selectStaffInfoView(){this.selectedScreen = "staffInfo";}
    public void modelChanged() {
        this.getChildren().clear();
        switch (this.selectedScreen) {
            case "managerView" -> this.getChildren().add(managerMainScreen);
            case "recipeMaker" -> this.getChildren().add(recipeMakerView);
            case "inventory" -> this.getChildren().add(inventoryView);
            case "recipeList" -> this.getChildren().add(recipeListView);
            case "menuList" -> this.getChildren().add(menuItemListView);
            case "menuMaker" -> this.getChildren().add(menuItemMakerView);
            case "staffInfo" -> this.getChildren().add(staffInfoView);
        }
    }
}
