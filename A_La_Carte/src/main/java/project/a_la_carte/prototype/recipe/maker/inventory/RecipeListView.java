package project.a_la_carte.prototype.recipe.maker.inventory;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import project.a_la_carte.prototype.ProgramController;

import java.util.List;

public class RecipeListView extends StackPane implements RecipeModelSubscriber{
    RecipeModel recipeModel;
    VBox recipeListVBox;
    VBox ingredientVBox;

    //We can make these text fields instead if we plan on making possible to change the recipe right here
    //Or we can just stay on making changes to the recipe in the recipe maker page
    //---------------------------------
    Label recipeName;
    Label recipePrice;
    Label recipeDescription;
    Label recipePrepI;
    Label recipePrepT;
    //-------------------------
    Button mainMenu;
    Button createNewButton;
    //Don't know if save changes will still be needed
    Button saveChangesButton;
    Button deleteButton;
    Button editIngredients;
    public RecipeListView(){
        this.setPrefSize(1000,500);

        Label title = new Label("Recipe List");
        title.setFont(new Font(20));

        this.mainMenu = new Button("Main Menu");
        HBox menuHBox = new HBox();
        menuHBox.getChildren().add(mainMenu);


        //TODO show existing recipes
        this.recipeListVBox = new VBox();
        this.recipeListVBox.setStyle("-fx-border-color: black;\n");
        this.recipeListVBox.setPrefSize(300,500);

        VBox leftVBox = new VBox();
        leftVBox.setPrefSize(300,500);
        leftVBox.setPadding(new Insets(5,5,5,5));
        leftVBox.getChildren().addAll(menuHBox, title,recipeListVBox);

        Label selectedTitle = new Label("Recipe Information");
        selectedTitle.setFont(new Font(20));

        this.createNewButton = new Button("Create New Recipe");
        HBox createHBox = new HBox();
        createHBox.setPrefWidth(1000);
        createHBox.setAlignment(Pos.BASELINE_RIGHT);
        createHBox.getChildren().add(createNewButton);

        Label name = new Label("Name: ");
        recipeName = new Label(" ");
        recipeName.setStyle("-fx-border-color: black;\n");
        recipeName.setPrefWidth(150);
        HBox nameHBox = new HBox(name,recipeName);
        nameHBox.setPadding(new Insets(5,5,5,5));

        Label price = new Label("Price: ");
        recipePrice = new Label(" ");
        recipePrice.setStyle("-fx-border-color: black;\n");
        recipePrice.setPrefWidth(150);
        HBox priceHBox = new HBox(price, recipePrice);
        priceHBox.setPadding(new Insets(5,5,5,5));

        Label description = new Label("Recipe Description: ");
        recipeDescription = new Label(" ");
        recipeDescription.setStyle("-fx-border-color: black;\n");
        recipeDescription.setPrefSize(400,100);
        VBox descriptionVBox = new VBox(description,recipeDescription);
        descriptionVBox.setPadding(new Insets(5,5,5,5));

        Label prepInstruction = new Label("Preparation Instruction: ");
        recipePrepI = new Label(" ");
        recipePrepI.setStyle("-fx-border-color: black;\n");
        recipePrepI.setPrefSize(400,100);
        VBox prepInstructionVBox = new VBox(prepInstruction,recipePrepI);
        prepInstructionVBox.setPadding(new Insets(5,5,5,5));

        Label prepTime = new Label("Estimated Preptime: ");
        recipePrepT = new Label(" ");
        recipePrepT.setStyle("-fx-border-color: black;\n");
        recipePrepT.setPrefWidth(150);
        HBox prepTimeHBox = new HBox(prepTime,recipePrepT);
        prepTimeHBox.setPadding(new Insets(5,5,5,5));

        Label selectedLabel = new Label("Selected Recipe");
        selectedLabel.setFont(new Font(20));

        //When clicked, deletes the selected recipe
        this.deleteButton = new Button("Delete Recipe");
        //When clicked, loads recipe maker with the selected Recipe's information
        //For now it will just lead to the RecipeMaker page
        this.editIngredients = new Button("Edit Recipe");

        HBox buttonsHBox = new HBox(deleteButton,editIngredients);
        buttonsHBox.setPrefWidth(400);
        buttonsHBox.setSpacing(7);
        buttonsHBox.setPadding(new Insets(5,3,5,3));

        VBox selectedRecipeVBox = new VBox();
        selectedRecipeVBox.setPrefSize(400,500);
        selectedRecipeVBox.setPadding(new Insets(5,5,5,5));
        selectedRecipeVBox.getChildren().addAll(selectedLabel,nameHBox,priceHBox,descriptionVBox,prepInstructionVBox,prepTimeHBox,buttonsHBox);

        Label ingredientTitle = new Label("List of Ingredients");
        ingredientTitle.setFont(new Font(15));

        this.ingredientVBox = new VBox();
        this.ingredientVBox.setStyle("-fx-border-color: black;\n");
        this.ingredientVBox.setPrefSize(300,500);

        VBox ingredientAlign = new VBox(ingredientTitle,ingredientVBox);
        ingredientAlign.setPrefSize(300,500);
        ingredientAlign.setPadding(new Insets(5,5,5,5));

        HBox alignRight = new HBox(selectedRecipeVBox,ingredientAlign);
        alignRight.setPrefSize(700,500);
        alignRight.setStyle("-fx-border-color: black;\n");

        VBox rightVBox = new VBox();
        rightVBox.setPrefSize(700,500);
        rightVBox.setPadding(new Insets(5,5,5,5));
        rightVBox.getChildren().addAll(createHBox,selectedTitle,alignRight);

        HBox alignRecipeBox = new HBox();
        alignRecipeBox.setPrefSize(1000,500);
        alignRecipeBox.getChildren().addAll(leftVBox,rightVBox);
        alignRecipeBox.setPadding(new Insets(5,5,5,5));

        this.getChildren().add(alignRecipeBox);
    }
    public void setRecipeListModel(RecipeModel newModel){this.recipeModel = newModel;}
    public void setController(ProgramController controller){
        //If a recipe is selected, when createNewButton is clicked, unselects the recipe
        //If edit ingredients is selected, then the selected recipe will show up on recipe maker
        //If no recipe is selected and edit ingredient is clicked, send an alert to user
        //We should have an if statement in making recipes to check if a recipe is selected or not
        this.createNewButton.setOnAction(controller::openRecipeMakerScreen);
        this.editIngredients.setOnAction(controller::openRecipeMakerScreen);
        this.mainMenu.setOnAction(controller::openStartUpMVC);
    }

    @Override
    public void RecipieModelChanged(List<Recipe> recipeList) {
        for(Recipe recipe : recipeList){
            RecipeWidget widget = new RecipeWidget(recipe);
            recipeListVBox.getChildren().add(widget.getWidget());
        }
    }
}
