package project.a_la_carte.version2.menuItems;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

import project.a_la_carte.version2.ProgramController;
import project.a_la_carte.version2.classesObjects.*;
import project.a_la_carte.version2.interfaces.MenuItemModelSubscriber;

import java.util.List;

/**
 * Display for creating a new MenuItem
 */
public class MenuItemMakerView extends StackPane implements MenuItemModelSubscriber {
    //TODO should make these private for the sake of good code practice
    MenuItemModel menuItemModel;
    VBox recipeVBOX;
    VBox selectRecipeVBox;
    HBox buttonsHBox;
    TextField menuItemName;
    TextArea menuItemDescription;
    TextField menuItemPrice;
    Label priceOfRecipesLabel;
    Button saveMenuItem;
    Button editMenuItem;
    Button addRecipe;
    Button removeRecipe;
    Button menuItemList;
    Button mainMenu;
    Boolean edit;

    public MenuItemMakerView(){
        this.setMaxSize(5000,2500);
        this.setPrefSize(1000,500);
        this.edit = false;
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
        menuItemDescription.setPrefColumnCount(400);

        descVBox.getChildren().addAll(descLabel,menuItemDescription);
        descVBox.setPadding(new Insets(2,2,2,2));
        descVBox.setPrefSize(400,100);

        this.menuItemPrice = new TextField();
        this.menuItemPrice.setPrefWidth(300);

        Label priceLabel = new Label("Price: ");
        priceOfRecipesLabel = new Label("Cost of Recipes :$00.00" );
        HBox priceBox = new HBox(priceLabel, menuItemPrice);
        priceBox.setPrefWidth(500);
        priceBox.setPadding(new Insets(2));

        addRecipe = new Button("Add Selected Recipe");
        addRecipe.setStyle("-fx-background-color: bisque;\n" + "-fx-border-color: burlywood;\n"
                + "-fx-border-radius: 15;\n"+"-fx-background-radius: 15;\n");
        mainMenu = new Button("Main Menu");
        mainMenu.setStyle("-fx-background-color: bisque;\n" + "-fx-border-color: burlywood;\n"
                + "-fx-border-radius: 15;\n"+"-fx-background-radius: 15;\n");

        createVBox.getChildren().addAll(mainMenu,title, nameHBox,descVBox,priceBox,priceOfRecipesLabel);
        createVBox.setPadding(new Insets(5,5,5,5));
        HBox.setHgrow(createVBox,Priority.ALWAYS);
        VBox.setVgrow(createVBox,Priority.ALWAYS);
        createVBox.setStyle("-fx-border-color: black;\n" + "-fx-background-color: linen;\n");

        //Right side, recipe list ---------------------------------------------
        HBox alignHBoxRemove = new HBox();
        this.removeRecipe = new Button("Remove Selected Recipe");
        removeRecipe.setStyle("-fx-background-color: bisque;\n" + "-fx-border-color: burlywood;\n"
                + "-fx-border-radius: 15;\n"+"-fx-background-radius: 15;\n");

        alignHBoxRemove.getChildren().add(removeRecipe);
        alignHBoxRemove.setAlignment(Pos.BOTTOM_CENTER);
        alignHBoxRemove.setPrefWidth(300);
        alignHBoxRemove.setPadding(new Insets(5,5,5,5));

        recipeVBOX = new VBox();
        recipeVBOX.setPrefSize(300,500);
        recipeVBOX.setPadding(new Insets(2));
        recipeVBOX.setSpacing(2);
        recipeVBOX.setStyle("-fx-background-color: linen;\n");

        ScrollPane recipeScroll = new ScrollPane(recipeVBOX);
        recipeScroll.setPrefSize(300,500);
        recipeScroll.prefHeightProperty().bind(this.heightProperty());
        recipeScroll.setFitToHeight(true);
        recipeScroll.setFitToWidth(true);

        VBox selectBoxRemove = new VBox(recipeScroll,alignHBoxRemove);
        selectBoxRemove.setPrefSize(300,500);
        selectBoxRemove.setStyle("-fx-border-color: black;\n");
        VBox.setVgrow(selectBoxRemove, Priority.ALWAYS);

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
        selectRecipeVBox.setSpacing(2);
        selectRecipeVBox.setPadding(new Insets(2));
        selectRecipeVBox.setStyle("-fx-background-color: linen;\n");

        ScrollPane selectScroll = new ScrollPane(selectRecipeVBox);
        selectScroll.setPrefSize(300,500);
        selectScroll.prefHeightProperty().bind(this.heightProperty());
        selectScroll.setFitToWidth(true);
        selectScroll.setFitToHeight(true);

        Label recipeSelectLabel = new Label("Add New Recipe To Item");

        VBox selectBox = new VBox(selectScroll,alignHBoxAdd);
        selectBox.setPrefSize(300,500);
        selectBox.setStyle("-fx-border-color: black;\n");
        VBox.setVgrow(selectBox,Priority.ALWAYS);

        VBox recipeSelectAlign = new VBox(recipeSelectLabel,selectBox);
        recipeSelectAlign.setPrefSize(300,500);
        recipeSelectAlign.setPadding(new Insets(3));
        VBox.setVgrow(recipeSelectAlign, Priority.ALWAYS);

        HBox alignRecipe = new HBox(recipeAlign,recipeSelectAlign);
        alignRecipe.setPrefSize(600,500);
        alignRecipe.setPadding(new Insets(2));
        VBox.setVgrow(alignRecipe,Priority.ALWAYS);

        buttonsHBox = new HBox();
        menuItemList = new Button("Return to Menu Item List");
        menuItemList.setStyle("-fx-background-color: bisque;\n" + "-fx-border-color: burlywood;\n"
                + "-fx-border-radius: 15;\n"+"-fx-background-radius: 15;\n");
        saveMenuItem = new Button("Save Menu Item");
        saveMenuItem.setStyle("-fx-background-color: bisque;\n" + "-fx-border-color: burlywood;\n"
                + "-fx-border-radius: 15;\n"+"-fx-background-radius: 15;\n");
        editMenuItem = new Button("Save Edits to Item");
        editMenuItem.setStyle("-fx-background-color: bisque;\n" + "-fx-border-color: burlywood;\n"
                + "-fx-border-radius: 15;\n"+"-fx-background-radius: 15;\n");

        buttonsHBox.getChildren().addAll(menuItemList,saveMenuItem);
        buttonsHBox.setPrefWidth(600);
        buttonsHBox.setSpacing(10);
        buttonsHBox.setAlignment(Pos.CENTER_RIGHT);
        buttonsHBox.setPadding(new Insets(2));

        VBox alignRight = new VBox();
        alignRight.getChildren().addAll(alignRecipe,buttonsHBox);
        alignRight.setPadding(new Insets(5,5,5,5));
        alignRight.setAlignment(Pos.BASELINE_RIGHT);

        HBox connectAll = new HBox();
        connectAll.getChildren().addAll(createVBox,alignRight);
        connectAll.setPadding(new Insets(5,5,5,5));
        HBox.setHgrow(connectAll,Priority.ALWAYS);
        VBox.setVgrow(connectAll,Priority.ALWAYS);
        connectAll.setStyle("-fx-border-color: black;\n" + "-fx-background-color: palegoldenrod;\n");

        this.setStyle("-fx-border-color: black;\n");
        this.getChildren().add(connectAll);
    }

    /**
     * Getters and Setters for MenuItem information
     */
    public String getMenuItemName(){
        return this.menuItemName.getText();
    }
    public void setNameText(String name){
        this.menuItemName.setText(name);
    }
    public String getMenuItemDescription(){
        return this.menuItemDescription.getText();
    }
    public void setDescText(String descText){
        this.menuItemDescription.setText(descText);
    }
    public String getMenuPrice(){
        return this.menuItemPrice.getText();
    }
    public void setPriceText(String priceText){
        this.menuItemPrice.setText(priceText);
    }
    public float setMenuPrice(){
        return Float.parseFloat(this.menuItemPrice.getText());
    }

    /**
     * Method for clearing the text fields
     */
    public void clearTextFields(){
        this.menuItemName.clear();
        this.menuItemDescription.clear();
        this.menuItemPrice.clear();
    }
    public void setSave(){
        this.edit = false;
    }
    public void setEdit(){
        this.edit = true;
    }

    /**
     * Set method for MenuItemMakerView's MenuItemModel
     */
    public void setMenuItemModel(MenuItemModel newModel) {
        this.menuItemModel = newModel;
    }

    /**
     * Connecting MenuItemMakerView to the program's controller
     */
    public void setController(ProgramController controller){
        mainMenu.setOnAction(controller::openManagerMainView);
        menuItemList.setOnAction(controller::openMenuListView);
        saveMenuItem.setOnAction(controller::addItemToMenu);
        editMenuItem.setOnAction(controller::saveEditsToItem);
        addRecipe.setOnAction(controller::addRecipeToItem);
        removeRecipe.setOnAction(controller::removeRecipeFromItem);
    }

    @Override
    public void MenuItemModelChanged(List<MenuFoodItem> menuItemList) {
        this.selectRecipeVBox.getChildren().clear();
        if (menuItemModel.getRecipeArrayList() != null){
            menuItemModel.getRecipeArrayList().forEach((recipe -> {
                recipe.getButton().setOnAction((event -> {
                    menuItemModel.selectRecipe(recipe);
                }));
                recipe.getButton().setPrefSize(300,25);
                selectRecipeVBox.getChildren().add(recipe.getButton());
            }));
        }
        float priceOfRecipes = 0;
        this.recipeVBOX.getChildren().clear();
        if (menuItemModel.getAddedRecipes() != null){
            menuItemModel.getAddedRecipes().forEach((recipe -> {
                recipe.getButton().setOnAction((event -> {
                    menuItemModel.selectAddedRecipe(recipe);
                }));
                recipeVBOX.getChildren().add(recipe.getButton());
            }));
            for(Recipe recipe : menuItemModel.getAddedRecipes()){
                priceOfRecipes += recipe.getPrice();
            }
            priceOfRecipesLabel.setText("Cost of Recipes :$" + priceOfRecipes);
        }

        this.buttonsHBox.getChildren().clear();
        if (edit){
            this.buttonsHBox.getChildren().addAll(this.menuItemList,this.editMenuItem);
        }
        else {
            this.buttonsHBox.getChildren().addAll(this.menuItemList,this.saveMenuItem);
        }
    }
}
