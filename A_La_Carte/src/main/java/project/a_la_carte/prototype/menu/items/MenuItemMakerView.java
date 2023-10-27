package project.a_la_carte.prototype.menu.items;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import project.a_la_carte.prototype.ProgramController;

import java.util.List;


public class MenuItemMakerView extends StackPane implements MenuItemModelSubscriber {

    MenuItemModel menuItemModel;
    VBox recipeVBOX;
    VBox selectRecipeVBox;
    TextField menuItemName;
    TextArea menuItemDescription;
    TextField menuItemPrice;
    TextField menuItemPrep;
    Button saveMenuItem;
    Button addRecipe;
    Button removeRecipe;
    Button menuItemList;
    Button mainMenu;
    TextField selectedRecipe;
    MenuBar recipeMenuBar;

    public MenuItemMakerView(){

        this.setMaxSize(1000,500);

        //Left side of MenuItem Creator page
        //----------------------------------------------------
        VBox createVBox = new VBox();
        createVBox.setPrefSize(400,500);
        Label title = new Label("Menu Item Creator");
        title.setFont(new Font(30));

        HBox nameHBox = new HBox();
        Label nameLabel = new Label("Enter Name");
        menuItemName = new TextField();
        nameHBox.getChildren().addAll(nameLabel,menuItemName);
        nameHBox.setPadding(new Insets(2,2,2,2));
        nameHBox.setSpacing(8);
        nameHBox.setPrefWidth(400);

        VBox descVBox = new VBox();
        Label descLabel = new Label("Enter Description");
        menuItemDescription = new TextArea();
        menuItemDescription.setPrefSize(400,100);
        menuItemDescription.setWrapText(true);

        descVBox.getChildren().addAll(descLabel,menuItemDescription);
        descVBox.setPadding(new Insets(2,2,2,2));
        descVBox.setPrefSize(400,100);

        this.menuItemPrice = new TextField();
        this.menuItemPrice.setPrefWidth(300);

        Label priceLabel = new Label("Price: ");
        HBox priceBox = new HBox(priceLabel, menuItemPrice);
        priceBox.setPrefWidth(500);
        priceBox.setPadding(new Insets(2));

        this.menuItemPrep = new TextField();
        Label prepLabel = new Label("Prep: ");
        this.menuItemPrep.setPrefWidth(300);
        HBox prepBox = new HBox(prepLabel, menuItemPrep);
        prepBox.setPrefWidth(500);
        prepBox.setPadding(new Insets(2));

        //menu for selecting ingredients
        VBox selectionVBox = new VBox();
        Label selectLabel = new Label("Select recipes");
        recipeMenuBar = new MenuBar();
        selectedRecipe = new TextField();
        selectedRecipe.setEditable(false);

        addRecipe = new Button("Add Selected Recipe");

        mainMenu = new Button("Main Menu");

        createVBox.getChildren().addAll(mainMenu,title, nameHBox,descVBox,priceBox,prepBox);
        createVBox.setPadding(new Insets(5,5,5,5));

        //Right side, recipe list ---------------------------------------------
        HBox alignHBoxRemove = new HBox();
        this.removeRecipe = new Button("Remove Selected Recipe");

        alignHBoxRemove.getChildren().add(removeRecipe);
        alignHBoxRemove.setAlignment(Pos.BOTTOM_CENTER);
        alignHBoxRemove.setPrefWidth(300);
        alignHBoxRemove.setPadding(new Insets(5,5,5,5));

        recipeVBOX = new VBox();
        recipeVBOX.setPrefSize(300,500);
        recipeVBOX.setPadding(new Insets(2));
        VBox selectBoxRemove = new VBox(recipeVBOX,alignHBoxRemove);
        selectBoxRemove.setPrefSize(300,500);
        selectBoxRemove.setStyle("-fx-border-color: black;\n");

        Label recipeListLabel = new Label("Recipes in item");
        VBox recipeAlign = new VBox(recipeListLabel,selectBoxRemove);
        recipeAlign.setPrefSize(300,500);
        recipeAlign.setPadding(new Insets(3));

        HBox alignHBoxAdd = new HBox();

        alignHBoxAdd.getChildren().add(addRecipe);
        alignHBoxAdd.setAlignment(Pos.BOTTOM_CENTER);
        alignHBoxAdd.setPrefWidth(300);
        alignHBoxAdd.setPadding(new Insets(5,5,5,5));

        selectRecipeVBox = new VBox();
        selectRecipeVBox.setPrefSize(300,500);
        selectRecipeVBox.setPadding(new Insets(2));
        Label recipeSelectLabel = new Label("Add New Recipe To Item");

        VBox selectBox = new VBox(selectRecipeVBox,alignHBoxAdd);
        selectBox.setPrefSize(300,500);
        selectBox.setStyle("-fx-border-color: black;\n");

        VBox recipeSelectAlign = new VBox(recipeSelectLabel,selectBox);
        recipeSelectAlign.setPrefSize(300,500);
        recipeSelectAlign.setPadding(new Insets(3));

        HBox alignRecipe = new HBox(recipeAlign,recipeSelectAlign);
        alignRecipe.setPrefSize(600,500);
        alignRecipe.setPadding(new Insets(2));

        HBox buttonsHBox = new HBox();
        menuItemList = new Button("Return to Menu Item List");
        saveMenuItem = new Button("Save Menu Item");
        buttonsHBox.getChildren().addAll(menuItemList,saveMenuItem);
        buttonsHBox.setPrefWidth(600);
        buttonsHBox.setSpacing(10);
        buttonsHBox.setAlignment(Pos.BASELINE_RIGHT);

        VBox alignRight = new VBox();
        alignRight.getChildren().addAll(alignRecipe,buttonsHBox);
        alignRight.setPadding(new Insets(5,5,5,5));
        alignRight.setAlignment(Pos.BASELINE_RIGHT);

        HBox connectAll = new HBox();
        connectAll.getChildren().addAll(createVBox,alignRight);
        connectAll.setPadding(new Insets(5,5,5,5));

        this.setStyle("-fx-border-color: black;\n");
        this.getChildren().add(connectAll);
    }
    public String getMenuItemName(){
        return this.menuItemName.getText();
    }
    public String getMenuItemDescription(){
        return this.menuItemDescription.getText();
    }
    public String getMenuPrice(){
        return this.menuItemPrice.getText();
    }
    public String getMenuPrep(){
        return this.menuItemPrep.getText();
    }
    public float setMenuPrice(){
        return Float.parseFloat(this.menuItemPrice.getText());
    }
    public float setMenuPrep(){
        return Float.parseFloat(this.menuItemPrep.getText());
    }
    public void clearTextFields(){
        this.menuItemName.clear();
        this.menuItemDescription.clear();
        this.menuItemPrice.clear();
        this.menuItemPrep.clear();
    }
    public void setMenuItemModel(MenuItemModel newModel) {
        this.menuItemModel = newModel;
    }

    public void setController(ProgramController controller){
        mainMenu.setOnAction(controller::openStartUpMVC);
        menuItemList.setOnAction(controller::openMenuListView);
        //saveRecipe.setOnAction(controller::addRecipie);
        addRecipe.setOnAction(controller::addRecipeToItem);
    }

    @Override
    public void MenuItemModelChanged(List<MenuFoodItem> menuItemList) {
        this.selectRecipeVBox.getChildren().clear();
        if (!menuItemModel.getRecipeArrayList().isEmpty()){
            menuItemModel.getRecipeArrayList().forEach((recipe -> {
                recipe.getButton().setOnAction((event -> {
                    menuItemModel.selectRecipe(recipe);
                }));
                selectRecipeVBox.getChildren().add(recipe.getButton());
            }));
        }

        this.recipeVBOX.getChildren().clear();
        if (!menuItemModel.getAddedRecipes().isEmpty()){
            menuItemModel.getAddedRecipes().forEach((recipe -> {
                recipe.getButton().setOnAction((event -> {
                    menuItemModel.selectAddedRecipe(recipe);
                }));
                recipeVBOX.getChildren().add(recipe.getButton());
            }));
        }
    }
}
