package project.a_la_carte.version2.menuItems;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

import project.a_la_carte.version2.ProgramController;
import project.a_la_carte.version2.classesObjects.*;
import project.a_la_carte.version2.interfaces.MenuItemModelSubscriber;

import java.util.List;

public class MenuItemMakerView extends StackPane implements MenuItemModelSubscriber {

    MenuItemModel menuItemModel;
    VBox recipeVBOX;
    VBox selectRecipeVBox;
    HBox buttonsHBox;
    TextField menuItemName;
    TextArea menuItemDescription;
    TextField menuItemPrice;
    TextField menuItemPrep;
    Button saveMenuItem;
    Button editMenuItem;
    Button addRecipe;
    Button removeRecipe;
    Button menuItemList;
    Button mainMenu;
    Boolean edit;

    public MenuItemMakerView() {
        this.setMaxSize(1000, 500);
        this.edit = false;
        // Left side of MenuItem Creator page
        // ----------------------------------------------------
        VBox createVBox = new VBox();
        createVBox.setPrefSize(400, 500);
        Label title = new Label("Menu Item Creator");
        title.setFont(new Font(30));

        HBox nameHBox = new HBox();
        Label nameLabel = new Label("Enter Name");
        menuItemName = new TextField();
        nameHBox.getChildren().addAll(nameLabel, menuItemName);
        nameHBox.setPadding(new Insets(2, 2, 2, 2));
        nameHBox.setSpacing(8);
        nameHBox.setPrefWidth(400);

        VBox descVBox = new VBox();
        Label descLabel = new Label("Enter Description");
        menuItemDescription = new TextArea();
        menuItemDescription.setPrefSize(400, 100);
        menuItemDescription.setWrapText(true);

        descVBox.getChildren().addAll(descLabel, menuItemDescription);
        descVBox.setPadding(new Insets(2, 2, 2, 2));
        descVBox.setPrefSize(400, 100);

        this.menuItemPrice = new TextField();
        this.menuItemPrice.setPrefWidth(300);

        Label priceLabel = new Label("Price ");
        HBox priceBox = new HBox(priceLabel, menuItemPrice);
        priceBox.setPrefWidth(500);
        priceBox.setPadding(new Insets(2));

        this.menuItemPrep = new TextField();
        Label prepLabel = new Label("Prep Time ");
        this.menuItemPrep.setPrefWidth(270);
        HBox prepBox = new HBox(prepLabel, menuItemPrep);
        prepBox.setPrefWidth(500);
        prepBox.setPadding(new Insets(2));

        addRecipe = new Button("Add Selected Recipe");
        mainMenu = new Button("Main Menu");

        createVBox.getChildren().addAll(mainMenu, title, nameHBox, descVBox, priceBox, prepBox);
        createVBox.setPadding(new Insets(5, 5, 5, 5));

        // Right side, recipe list ---------------------------------------------
        HBox alignHBoxRemove = new HBox();
        this.removeRecipe = new Button("Remove Selected Recipe");

        alignHBoxRemove.getChildren().add(removeRecipe);
        alignHBoxRemove.setAlignment(Pos.BOTTOM_CENTER);
        alignHBoxRemove.setPrefWidth(300);
        alignHBoxRemove.setPadding(new Insets(5, 5, 5, 5));

        recipeVBOX = new VBox();
        recipeVBOX.setPrefSize(270, 500);
        recipeVBOX.setPadding(new Insets(2));

        ScrollPane recipeScroll = new ScrollPane(recipeVBOX);
        recipeScroll.setPrefSize(300, 500);

        VBox selectBoxRemove = new VBox(recipeScroll, alignHBoxRemove);
        selectBoxRemove.setPrefSize(300, 500);
        selectBoxRemove.setStyle("-fx-border-color: black;\n");

        Label recipeListLabel = new Label("Recipes in item");
        VBox recipeAlign = new VBox(recipeListLabel, selectBoxRemove);
        recipeAlign.setPrefSize(300, 500);
        recipeAlign.setPadding(new Insets(3));

        HBox alignHBoxAdd = new HBox();

        alignHBoxAdd.getChildren().add(addRecipe);
        alignHBoxAdd.setAlignment(Pos.BOTTOM_CENTER);
        alignHBoxAdd.setPrefWidth(300);
        alignHBoxAdd.setPadding(new Insets(5, 5, 5, 5));

        selectRecipeVBox = new VBox();
        selectRecipeVBox.setPrefSize(270, 500);
        selectRecipeVBox.setPadding(new Insets(2));
        ScrollPane selectScroll = new ScrollPane(selectRecipeVBox);
        selectScroll.setPrefSize(300, 500);

        Label recipeSelectLabel = new Label("Add New Recipe To Item");

        VBox selectBox = new VBox(selectScroll, alignHBoxAdd);
        selectBox.setPrefSize(300, 500);
        selectBox.setStyle("-fx-border-color: black;\n");

        VBox recipeSelectAlign = new VBox(recipeSelectLabel, selectBox);
        recipeSelectAlign.setPrefSize(300, 500);
        recipeSelectAlign.setPadding(new Insets(3));

        HBox alignRecipe = new HBox(recipeAlign, recipeSelectAlign);
        alignRecipe.setPrefSize(600, 500);
        alignRecipe.setPadding(new Insets(2));

        buttonsHBox = new HBox();
        menuItemList = new Button("Return to Menu Item List");
        saveMenuItem = new Button("Save Menu Item");
        editMenuItem = new Button("Save Edits to Item");

        buttonsHBox.getChildren().addAll(menuItemList, saveMenuItem);
        buttonsHBox.setPrefWidth(600);
        buttonsHBox.setSpacing(10);
        buttonsHBox.setAlignment(Pos.BASELINE_RIGHT);

        VBox alignRight = new VBox();
        alignRight.getChildren().addAll(alignRecipe, buttonsHBox);
        alignRight.setPadding(new Insets(5, 5, 5, 5));
        alignRight.setAlignment(Pos.BASELINE_RIGHT);

        HBox connectAll = new HBox();
        connectAll.getChildren().addAll(createVBox, alignRight);
        connectAll.setPadding(new Insets(5, 5, 5, 5));

        this.setStyle("-fx-border-color: black;\n");
        this.getChildren().add(connectAll);
    }

    public String getMenuItemName() {
        return this.menuItemName.getText();
    }

    public void setNameText(String name) {
        this.menuItemName.setText(name);
    }

    public String getMenuItemDescription() {
        return this.menuItemDescription.getText();
    }

    public void setDescText(String descText) {
        this.menuItemDescription.setText(descText);
    }

    public String getMenuPrice() {
        return this.menuItemPrice.getText();
    }

    public void setPriceText(String priceText) {
        this.menuItemPrice.setText(priceText);
    }

    public String getMenuPrep() {
        return this.menuItemPrep.getText();
    }

    public void setPrepText(String prepText) {
        this.menuItemPrep.setText(prepText);
    }

    public float setMenuPrice() {
        return Float.parseFloat(this.menuItemPrice.getText());
    }

    public float setMenuPrep() {
        return Float.parseFloat(this.menuItemPrep.getText());
    }

    public void clearTextFields() {
        this.menuItemName.clear();
        this.menuItemDescription.clear();
        this.menuItemPrice.clear();
        this.menuItemPrep.clear();
    }

    public void setSave() {
        this.edit = false;
    }

    public void setEdit() {
        this.edit = true;
    }

    public void setMenuItemModel(MenuItemModel newModel) {
        this.menuItemModel = newModel;
    }

    public void setController(ProgramController controller) {
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
        if (menuItemModel.getRecipeArrayList() != null) {
            menuItemModel.getRecipeArrayList().forEach((recipe -> {
                recipe.getButton().setOnAction((event -> {
                    menuItemModel.selectRecipe(recipe);
                }));
                recipe.getButton().setPrefSize(300, 25);
                selectRecipeVBox.getChildren().add(recipe.getButton());
            }));
        }

        this.recipeVBOX.getChildren().clear();
        if (menuItemModel.getAddedRecipes() != null) {
            menuItemModel.getAddedRecipes().forEach((recipe -> {
                recipe.getButton().setOnAction((event -> {
                    menuItemModel.selectAddedRecipe(recipe);
                }));
                recipeVBOX.getChildren().add(recipe.getButton());
            }));
        }

        this.buttonsHBox.getChildren().clear();
        if (edit) {
            this.buttonsHBox.getChildren().addAll(this.menuItemList, this.editMenuItem);
        } else {
            this.buttonsHBox.getChildren().addAll(this.menuItemList, this.saveMenuItem);
        }
    }
}
