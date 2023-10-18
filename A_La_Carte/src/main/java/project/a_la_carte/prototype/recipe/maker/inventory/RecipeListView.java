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

public class RecipeListView extends StackPane {
    RecipeListModel recipeListModel;
    VBox recipeListVBox;
    VBox selectedRecipeVBox;

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

        this.recipeListVBox = new VBox();
        this.recipeListVBox.setStyle("-fx-border-color: black;\n");
        this.recipeListVBox.setPrefSize(400,500);

        VBox leftVBox = new VBox();
        leftVBox.setPrefSize(400,500);
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
        recipeName.setPrefWidth(300);
        HBox nameHBox = new HBox(name,recipeName);
        nameHBox.setPadding(new Insets(5,5,5,5));

        Label price = new Label("Price: ");
        recipePrice = new Label(" ");
        recipePrice.setStyle("-fx-border-color: black;\n");
        recipePrice.setPrefWidth(300);
        HBox priceHBox = new HBox(price, recipePrice);
        priceHBox.setPadding(new Insets(5,5,5,5));

        Label description = new Label("Recipe Description: ");
        recipeDescription = new Label(" ");
        recipeDescription.setStyle("-fx-border-color: black;\n");
        recipeDescription.setPrefSize(600,100);
        VBox descriptionVBox = new VBox(description,recipeDescription);
        descriptionVBox.setPadding(new Insets(5,5,5,5));

        Label prepInstruction = new Label("Preparation Instruction: ");
        recipePrepI = new Label(" ");
        recipePrepI.setStyle("-fx-border-color: black;\n");
        recipePrepI.setPrefSize(600,100);
        VBox prepInstructionVBox = new VBox(prepInstruction,recipePrepI);
        prepInstructionVBox.setPadding(new Insets(5,5,5,5));

        Label prepTime = new Label("Estimated Preptime: ");
        recipePrepT = new Label(" ");
        recipePrepT.setStyle("-fx-border-color: black;\n");
        recipePrepT.setPrefWidth(300);
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
        buttonsHBox.setSpacing(600);
        buttonsHBox.setSpacing(7);
        buttonsHBox.setPadding(new Insets(5,3,5,3));

        this.selectedRecipeVBox = new VBox();
        this.selectedRecipeVBox.setStyle("-fx-border-color: black;\n");
        this.selectedRecipeVBox.setPrefSize(600,500);
        this.selectedRecipeVBox.setPadding(new Insets(5,5,5,5));
        this.selectedRecipeVBox.getChildren().addAll(selectedLabel,nameHBox,priceHBox,descriptionVBox,prepInstructionVBox,prepTimeHBox,buttonsHBox);

        VBox rightVBox = new VBox();
        rightVBox.setPrefSize(600,500);
        rightVBox.setPadding(new Insets(5,5,5,5));
        rightVBox.getChildren().addAll(createHBox,selectedTitle,selectedRecipeVBox);

        HBox alignRecipeBox = new HBox();
        alignRecipeBox.setPrefSize(1000,500);
        alignRecipeBox.getChildren().addAll(leftVBox,rightVBox);
        alignRecipeBox.setPadding(new Insets(5,5,5,5));

        this.getChildren().add(alignRecipeBox);
    }
    public void setRecipeListModel(RecipeListModel newModel){this.recipeListModel = newModel;}
    public void setController(ProgramController controller){
        this.createNewButton.setOnAction(controller::openRecipeMakerScreen);
        this.editIngredients.setOnAction(controller::openRecipeMakerScreen);
        this.mainMenu.setOnAction(controller::openStartUpMVC);
    }
}
