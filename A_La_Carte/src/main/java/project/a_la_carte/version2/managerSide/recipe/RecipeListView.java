package project.a_la_carte.version2.managerSide.recipe;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import project.a_la_carte.version2.ProgramController;
import project.a_la_carte.version2.classesObjects.*;
import project.a_la_carte.version2.interfaces.*;

import java.util.List;
import java.util.Map;

public class RecipeListView extends StackPane implements RecipeModelSubscriber, RecipeInteractiveModelSubsciber {
    RecipeModel recipeModel;

    //Recipe Table
    TableView<RecipeData> recipeTable;
    TableColumn<RecipeData,String> nameCol;
    TableColumn<RecipeData,String> descCol;
    TableColumn<RecipeData,Float> priceCol;
    VBox recipeListVBox;
    //---------------------------
    //Display ingredient table
    TableView<IngredientData> ingredientTable;
    TableColumn<IngredientData,String> ingredientNameCol;
    TableColumn<IngredientData,Double> quantityCol;
    TableColumn<IngredientData,String> measurementUnitCol;
    TableColumn<IngredientData, Boolean> allergenCol;
    VBox ingredientVBox;

    //We can make these text fields instead if we plan on making possible to change the recipe right here
    //Or we can just stay on making changes to the recipe in the recipe maker page
    //---------------------------------
    TextField recipeNameText;
    TextField recipePriceText;
    TextArea recipeDescriptionText;
    TextArea recipePrepIText;
    TextField recipePrepTimeText;
    //-------------------------
    Button mainMenu;
    Button createNewButton;
    //Don't know if save changes will still be needed
    Button saveChangesButton;
    Button deleteButton;
    Button editIngredients;
    Button showRecipe;
    public RecipeListView(){
        this.setPrefSize(1000,500);

        Label title = new Label("Recipe List");
        title.setFont(new Font(20));

        this.mainMenu = new Button("Main Menu");
        HBox menuHBox = new HBox();
        menuHBox.getChildren().add(mainMenu);

        //---------------------------
        //Recipe table

        recipeTable = new TableView<>();
        nameCol = new TableColumn<>("Name");
        descCol = new TableColumn<>("Description");
        priceCol = new TableColumn<>("$$$");
        priceCol.setMinWidth(50);
        priceCol.setMaxWidth(50);


        recipeTable.setPrefSize(300,500);
        recipeTable.getColumns().addAll(nameCol,descCol,priceCol);
        recipeTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);



        //TODO show existing recipes
        this.recipeListVBox = new VBox();
        this.recipeListVBox.setStyle("-fx-border-color: black;\n");
        this.recipeListVBox.setPrefSize(300,500);
        this.recipeListVBox.getChildren().add(recipeTable);

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
        recipeNameText = new TextField();
        recipeNameText.setStyle("-fx-border-color: black;\n");
        recipeNameText.setPrefWidth(150);
        recipeNameText.setEditable(false);
        HBox nameHBox = new HBox(name,recipeNameText);
        nameHBox.setPadding(new Insets(5,5,5,5));

        Label price = new Label("Price: ");
        recipePriceText = new TextField();
        recipePriceText.setStyle("-fx-border-color: black;\n");
        recipePriceText.setPrefWidth(150);
        recipePriceText.setEditable(false);
        HBox priceHBox = new HBox(price, recipePriceText);
        priceHBox.setPadding(new Insets(5,5,5,5));

        Label description = new Label("Recipe Description: ");
        recipeDescriptionText = new TextArea();
        recipeDescriptionText.setStyle("-fx-border-color: black;\n");
        recipeDescriptionText.setPrefSize(400,100);
        recipeDescriptionText.setEditable(false);
        VBox descriptionVBox = new VBox(description,recipeDescriptionText);
        descriptionVBox.setPadding(new Insets(5,5,5,5));

        Label prepInstruction = new Label("Preparation Instruction: ");
        recipePrepIText = new TextArea(" ");
        recipePrepIText.setStyle("-fx-border-color: black;\n");
        recipePrepIText.setPrefSize(400,100);
        recipePrepIText.setEditable(false);
        VBox prepInstructionVBox = new VBox(prepInstruction, recipePrepIText);
        prepInstructionVBox.setPadding(new Insets(5,5,5,5));

        Label prepTime = new Label("Estimated Preptime: ");
        recipePrepTimeText = new TextField();
        recipePrepTimeText.setStyle("-fx-border-color: black;\n");
        recipePrepTimeText.setPrefWidth(150);
        recipePrepTimeText.setEditable(false);
        HBox prepTimeHBox = new HBox(prepTime,recipePrepTimeText);
        prepTimeHBox.setPadding(new Insets(5,5,5,5));

        Label selectedLabel = new Label("Selected Recipe");
        selectedLabel.setFont(new Font(20));

        //When clicked, deletes the selected recipe
        this.deleteButton = new Button("Delete Recipe");
        //When clicked, loads recipe maker with the selected Recipe's information
        //For now it will just lead to the RecipeMaker page
        this.editIngredients = new Button("Edit Recipe");

        this.showRecipe = new Button("Display Recipe");
        HBox buttonsHBox = new HBox(deleteButton,editIngredients,showRecipe);
        buttonsHBox.setPrefWidth(400);
        buttonsHBox.setSpacing(7);
        buttonsHBox.setPadding(new Insets(5,3,5,3));

        VBox selectedRecipeVBox = new VBox();
        selectedRecipeVBox.setPrefSize(400,500);
        selectedRecipeVBox.setPadding(new Insets(5,5,5,5));
        selectedRecipeVBox.getChildren().addAll(selectedLabel,nameHBox,priceHBox,descriptionVBox,prepInstructionVBox,prepTimeHBox,buttonsHBox);

        Label ingredientTitle = new Label("List of Ingredients");
        ingredientTitle.setFont(new Font(15));

        //---------------- straight up copy paste from MakerView
        //Recipe Ingredient Display
        ingredientTable = new TableView<>();
        ingredientNameCol = new TableColumn<>("Ingredient Name");
        ingredientNameCol.setMaxWidth(120);
        quantityCol = new TableColumn<>("Quantity");
        quantityCol.setMaxWidth(60);
        quantityCol.setMinWidth(60);
        measurementUnitCol = new TableColumn<>("Unit");
        measurementUnitCol.setMinWidth(40);
        measurementUnitCol.setMaxWidth(40);
        allergenCol = new TableColumn<>("Allergen");
        allergenCol.setMinWidth(60);
        allergenCol.setMaxWidth(60);

        ingredientTable.getColumns().addAll(ingredientNameCol,quantityCol,measurementUnitCol,allergenCol);
        ingredientTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        ingredientTable.setPrefSize(300,500);


        this.ingredientVBox = new VBox();
        this.ingredientVBox.setStyle("-fx-border-color: black;\n");
        this.ingredientVBox.setPrefSize(300,500);
        ingredientVBox.getChildren().add(ingredientTable);

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

        this.createNewButton.setOnAction(event -> {//need to do it like it
            controller.setIModelNotCreating(event);
            controller.setStateNotLoaded(event);
            controller.openRecipeMakerScreen(event);
        });

        this.editIngredients.setOnAction(controller::openRecipeMakerScreen);

        this.mainMenu.setOnAction(controller::openStartUpMVC);
        this.recipeTable.setOnMouseClicked(controller::loadRecipe);

        this.showRecipe.setOnAction(controller::showRecipeInfo);
        this.deleteButton.setOnAction(event -> {
            controller.deleteRecipe(event);
            controller.setStateNotLoaded(event);
        });

    }

    /**
     * Take updates from the recipeModel and display in the table
     * @param recipeList
     */
    @Override
    public void RecipieModelChanged(List<Recipe> recipeList) {

        ObservableList<RecipeData> data = FXCollections.observableArrayList();

        for(Recipe recipe : recipeList){
            RecipeData myRecipe = new RecipeData(recipe);
            data.add(myRecipe);
        }
        recipeTable.setItems(data);
        nameCol.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
        descCol.setCellValueFactory(cellData -> cellData.getValue().descProperty());
        priceCol.setCellValueFactory(cellData -> cellData.getValue().priceProperty().asObject());
    }

    /**
     * update the recipe imodel and use it to display selected recipe ingredients
     * @param tempIngredientList
     */
    public void iModelChanged(Map<Ingredient, Double> tempIngredientList,Recipe loadedRecipe,boolean isCreating) {


        ObservableList<IngredientData> ingredientData = FXCollections.observableArrayList();

        if(loadedRecipe == null){
            getRecipeNameText().clear();
            getRecipePriceText().clear();
            getRecipeDescriptionText().clear();
            getRecipePrepIText().clear();
            getRecipePrepTimeText().clear();
        }
        else{
            getRecipeNameText().setText(loadedRecipe.getName());
            getRecipePriceText().setText(String.valueOf(loadedRecipe.getPrice()));
            getRecipeDescriptionText().setText(loadedRecipe.getDescription());
            getRecipePrepIText().setText(loadedRecipe.getPrepInstruction());
            getRecipePrepTimeText().setText(String.valueOf(loadedRecipe.getPrepTime()));

            for(Map.Entry<Ingredient, Double> entry : loadedRecipe.getRecipeIngredients().entrySet()){
                Double ingredientQuantity = entry.getValue();
                Ingredient ingredient = entry.getKey();
                IngredientData iData = new IngredientData(ingredient,ingredientQuantity);
                ingredientData.add(iData);
            }
        }

        ingredientTable.setItems(ingredientData);
        ingredientNameCol.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
        //recipe list map takes the recipe map quantity, which has been converted to pounds so this changes it back to ounces, for display only
        quantityCol.setCellValueFactory(cellData -> cellData.getValue().recipeQuantityProperty().asObject());
        measurementUnitCol.setCellValueFactory(cellData -> cellData.getValue().recipeMeasurementProperty());
        allergenCol.setCellValueFactory(cellData -> cellData.getValue().allergenProperty());




    }



    //---------------------------------------
    //Getters

    public TextField getRecipeNameText() {
        return recipeNameText;
    }
    public TextField getRecipePriceText() {
        return recipePriceText;
    }
    public TextArea getRecipeDescriptionText() {
        return recipeDescriptionText;
    }
    public TextArea getRecipePrepIText() {
        return recipePrepIText;
    }
    public TextField getRecipePrepTimeText() {
        return recipePrepTimeText;
    }
    public TableView<RecipeData> getRecipeTable() {
        return recipeTable;
    }


}
