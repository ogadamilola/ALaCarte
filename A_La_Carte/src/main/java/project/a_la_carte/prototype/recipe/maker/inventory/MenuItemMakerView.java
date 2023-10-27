package project.a_la_carte.prototype.recipe.maker.inventory;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuBar;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import project.a_la_carte.prototype.ProgramController;

import java.util.List;


public class MenuItemMakerView extends StackPane implements MenuItemModelSubscriber {

    MenuItemModel menuItemModel;
    MenuItemInteractiveModel iModel;
    VBox recipeVBOX;
    TextField menuItemName;
    TextField menuItemDescription;
    Button saveMenuItem;
    Button addRecipe;
    Button menuItemList;
    Button mainMenu;
    TextField selectedRecipe;
    MenuBar recipeMenuBar;

    public MenuItemMakerView(){

        this.setMaxSize(1000,500);

        //Left side of MenuItem Creator page
        //----------------------------------------------------
        VBox createVBox = new VBox();
        createVBox.setPrefSize(600,500);
        Label title = new Label("Recipe Creator");
        title.setFont(new Font(30));

        HBox nameHBox = new HBox();
        Label nameLabel = new Label("Enter Name");
        menuItemName = new TextField();
        nameHBox.getChildren().addAll(nameLabel,menuItemName);
        nameHBox.setPadding(new Insets(2,2,2,2));
        nameHBox.setSpacing(8);
        nameHBox.setPrefWidth(600);

        VBox descVBox = new VBox();
        Label descLabel = new Label("Enter Description");
        menuItemDescription = new TextField();
        menuItemDescription.setPrefSize(600,100);
        descVBox.getChildren().addAll(descLabel,menuItemDescription);
        descVBox.setPadding(new Insets(2,2,2,2));
        descVBox.setPrefSize(600,100);

        //menu for selecting ingredients
        VBox selectionVBox = new VBox();
        Label selectLabel = new Label("Select recipes");
        recipeMenuBar = new MenuBar();
        selectedRecipe = new TextField();
        selectedRecipe.setEditable(false);
        addRecipe = new Button("add recipe");

        mainMenu = new Button("Main Menu");

        createVBox.getChildren().addAll(mainMenu,title, nameHBox,descVBox,selectionVBox);
        createVBox.setPadding(new Insets(5,5,5,5));

        //Right side, recipe list ---------------------------------------------
        recipeVBOX = new VBox();
        recipeVBOX.setPrefSize(400,500);


        //The ingredient list would probably be filled with a HBox making class which is why
        //ingredientVBox is a variable, so that we can clear it, add ingredients, and delete ingredients

        // Same not from Randall in RecipeMakerView

        HBox alignHBoxAdd = new HBox();

        //alignHBoxAdd.getChildren().add(addIngredient);
        alignHBoxAdd.setAlignment(Pos.BOTTOM_CENTER);
        alignHBoxAdd.setPrefSize(400,500);
        alignHBoxAdd.setPadding(new Insets(5,5,5,5));

        HBox buttonsHBox = new HBox();
        menuItemList = new Button("Return to Recipe List");
        saveMenuItem = new Button("Save Recipe to Menu");
        buttonsHBox.getChildren().addAll(menuItemList,saveMenuItem);
        buttonsHBox.setPrefWidth(400);
        buttonsHBox.setSpacing(10);
        buttonsHBox.setAlignment(Pos.BASELINE_RIGHT);

        recipeVBOX.getChildren().add(alignHBoxAdd);
        recipeVBOX.setStyle("-fx-border-color: black;\n");

        VBox alignRight = new VBox();
        alignRight.getChildren().addAll(recipeVBOX,buttonsHBox);
        alignRight.setPadding(new Insets(5,5,5,5));
        alignRight.setAlignment(Pos.BASELINE_RIGHT);

        HBox connectAll = new HBox();
        connectAll.getChildren().addAll(createVBox,alignRight);
        connectAll.setPadding(new Insets(5,5,5,5));

        this.setStyle("-fx-border-color: black;\n");
        this.getChildren().add(connectAll);
    }

    public void setMenuItemModel(MenuItemModel newModel) {
        this.menuItemModel = newModel;
    }

    public void setMenuItemMakerController(ProgramController controller){
        mainMenu.setOnAction(controller::openStartUpMVC);
        menuItemList.setOnAction(controller::openRecipeList);
        //saveRecipe.setOnAction(controller::addRecipie);
        //addIngredient.setOnAction(controller::addIngredientToRecipie);
    }

    @Override
    public void MenuItemModelChanged(List<MenuFoodItem> menuItemList) {

    }
}
