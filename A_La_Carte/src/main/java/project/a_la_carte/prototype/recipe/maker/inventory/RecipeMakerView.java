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

public class RecipeMakerView extends StackPane {
    RecipeMakerModel recipeMakerModel;
    VBox ingredientVBox;
    TextField recipeName;
    TextField recipePrice;
    TextField recipeDescription;
    TextField recipeInstruction;
    TextField recipePrep;
    Button saveRecipe;
    Button addIngredient;
    Button recipeList;
    Button mainMenu;
    public RecipeMakerView(){
        this.setMaxSize(1000,500);

        //Left side of Recipe Creator page
        //----------------------------------------------------
        VBox createVBox = new VBox();
        createVBox.setPrefSize(600,500);
        Label title = new Label("Recipe Creator");
        title.setFont(new Font(30));

        HBox nameHBox = new HBox();
        Label nameLabel = new Label("Enter Name");
        recipeName = new TextField();
        nameHBox.getChildren().addAll(nameLabel,recipeName);
        nameHBox.setPadding(new Insets(2,2,2,2));
        nameHBox.setSpacing(8);
        nameHBox.setPrefWidth(600);

        HBox priceHBox = new HBox();
        Label priceLabel = new Label("Enter Price");
        recipePrice = new TextField();
        priceHBox.getChildren().addAll(priceLabel,recipePrice);
        priceHBox.setPadding(new Insets(2,2,2,2));
        priceHBox.setPrefWidth(600);
        priceHBox.setSpacing(8);

        VBox descVBox = new VBox();
        Label descLabel = new Label("Enter Description");
        recipeDescription = new TextField();
        recipeDescription.setPrefSize(600,100);
        descVBox.getChildren().addAll(descLabel,recipeDescription);
        descVBox.setPadding(new Insets(2,2,2,2));
        descVBox.setPrefSize(600,100);

        VBox prepIVBox = new VBox();
        Label prepILabel = new Label("Preparation Instructions");
        recipeInstruction = new TextField();
        recipeInstruction.setPrefSize(600,100);
        prepIVBox.getChildren().addAll(prepILabel,recipeInstruction);
        prepIVBox.setPadding(new Insets(2,2,2,2));
        prepIVBox.setPrefSize(600,100);

        HBox prepTHBox = new HBox();
        Label prepTLabel = new Label("Estimated preptime");
        recipePrep = new TextField();
        prepTHBox.getChildren().addAll(prepTLabel, recipePrep);
        prepTHBox.setPadding(new Insets(2,2,2,2));
        prepTHBox.setPrefWidth(600);
        prepTHBox.setSpacing(8);

        mainMenu = new Button("Main Menu");

        createVBox.getChildren().addAll(mainMenu,title, nameHBox,priceHBox,descVBox,prepIVBox,prepTHBox);
        createVBox.setPadding(new Insets(5,5,5,5));

        //Right side, ingredient list ---------------------------------------------
        ingredientVBox = new VBox();
        ingredientVBox.setPrefSize(400,500);

        //The ingredient list would probably be filled with a HBox making class which is why
        //ingredientVBox is a variable, so that we can clear it, add ingredients, and delete ingredients

        HBox alignHBoxAdd = new HBox();
        addIngredient = new Button("add ingredient");
        alignHBoxAdd.getChildren().add(addIngredient);
        alignHBoxAdd.setAlignment(Pos.BOTTOM_CENTER);
        alignHBoxAdd.setPrefSize(400,500);
        alignHBoxAdd.setPadding(new Insets(5,5,5,5));

        HBox buttonsHBox = new HBox();
        recipeList = new Button("Return to Recipe List");
        saveRecipe = new Button("Save Recipe to Menu");
        buttonsHBox.getChildren().addAll(recipeList,saveRecipe);
        buttonsHBox.setPrefWidth(400);
        buttonsHBox.setSpacing(10);
        buttonsHBox.setAlignment(Pos.BASELINE_RIGHT);

        ingredientVBox.getChildren().add(alignHBoxAdd);
        ingredientVBox.setStyle("-fx-border-color: black;\n");

        VBox alignRight = new VBox();
        alignRight.getChildren().addAll(ingredientVBox,buttonsHBox);
        alignRight.setPadding(new Insets(5,5,5,5));
        alignRight.setAlignment(Pos.BASELINE_RIGHT);

        HBox connectAll = new HBox();
        connectAll.getChildren().addAll(createVBox,alignRight);
        connectAll.setPadding(new Insets(5,5,5,5));

        this.setStyle("-fx-border-color: black;\n");
        this.getChildren().add(connectAll);
    }

    public void setRecipeMakerModel(RecipeMakerModel newModel){
        this.recipeMakerModel = newModel;
    }
    public void setRecipeMakerController(ProgramController controller){
        mainMenu.setOnAction(controller::openStartUpMVC);
        recipeList.setOnAction(controller::openRecipeList);
    }
}
