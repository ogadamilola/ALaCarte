package project.a_la_carte.prototype.recipe.maker.inventory;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import project.a_la_carte.prototype.ProgramController;

import java.util.Map;

public class RecipeMakerView extends StackPane implements InventorySubscriber, RecipeInteractiveModelSubsciber {
    RecipeModel recipeModel;
    RecipeInteractiveModel iModel;
    VBox ingredientVBox;
    TextField recipeName;
    TextField recipePrice;
    TextArea recipeDescription;
    TextArea recipeInstruction;
    TextField recipePrep;
    Button saveRecipe;
    Button addIngredient;
    Button deleteIngredient;
    Button recipeList;
    Button mainMenu;
    TextField selectedIngredient;
    MenuBar ingredientMenuBar;

    TextField enterMeasurementField;
    ComboBox<Object> measurementBox;

    TableView<IngredientData> ingredientTable;
    TableColumn<IngredientData,String> nameCol;
    TableColumn<IngredientData,Double> quantityCol;
    TableColumn<IngredientData,String> measurementUnitCol;
    TableColumn<IngredientData, Boolean> allergenCol;
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
        recipeDescription = new TextArea();
        recipeDescription.setPrefSize(600,100);
        recipeDescription.setWrapText(true);
        descVBox.getChildren().addAll(descLabel,recipeDescription);
        descVBox.setPadding(new Insets(2,2,2,2));
        descVBox.setPrefSize(600,100);

        VBox prepIVBox = new VBox();
        Label prepILabel = new Label("Preparation Instructions");
        recipeInstruction = new TextArea();
        recipeInstruction.setPrefSize(600,100);
        recipeInstruction.setWrapText(true);
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

        //menu for selecting ingredients
        VBox selectionVBox = new VBox();
        Label selectLabel = new Label("Select Ingredient");
        ingredientMenuBar = new MenuBar();
        selectedIngredient = new TextField();
        selectedIngredient.setEditable(false);

        addIngredient = new Button("add ingredient");
        deleteIngredient = new Button("Delete ingredient");

        HBox addAndDelBox = new HBox(addIngredient,deleteIngredient);


        for(Ingredient.IngredientType type : Ingredient.IngredientType.values()){
            Menu typeMenu = new Menu(type.getName());
            ingredientMenuBar.getMenus().add(typeMenu);
        }

        HBox amountHBox = new HBox();
        enterMeasurementField = new TextField();

        measurementBox = new ComboBox<>();
        measurementBox.getItems().add("Oz");
        measurementBox.getItems().add(Ingredient.MeasurementUnit.Count);


        amountHBox.getChildren().addAll(enterMeasurementField,measurementBox);

        selectionVBox.getChildren().addAll( selectLabel, ingredientMenuBar,selectedIngredient,amountHBox,addAndDelBox);

        mainMenu = new Button("Main Menu");

        createVBox.getChildren().addAll(mainMenu,title, nameHBox,priceHBox,descVBox,prepIVBox,prepTHBox,selectionVBox);
        createVBox.setPadding(new Insets(5,5,5,5));

        //Right side, ingredient list ---------------------------------------------
        ingredientVBox = new VBox();
        ingredientVBox.setPrefSize(400,500);

        //The ingredient list would probably be filled with a HBox making class which is why
        //ingredientVBox is a variable, so that we can clear it, add ingredients, and delete ingredients

        HBox buttonsHBox = new HBox();
        recipeList = new Button("Return to Recipe List");
        saveRecipe = new Button("Save Recipe to Menu");
        buttonsHBox.getChildren().addAll(recipeList,saveRecipe);
        buttonsHBox.setPrefWidth(400);
        buttonsHBox.setSpacing(10);
        buttonsHBox.setAlignment(Pos.BASELINE_RIGHT);

        ingredientVBox.setStyle("-fx-border-color: black;\n");

        ingredientTable = new TableView<>();
        nameCol = new TableColumn<>("Ingredient Name");
        quantityCol = new TableColumn<>("Quantity");
        quantityCol.setMaxWidth(70);
        quantityCol.setMinWidth(70);
        measurementUnitCol = new TableColumn<>("Unit");
        measurementUnitCol.setMinWidth(50);
        measurementUnitCol.setMaxWidth(50);
        allergenCol = new TableColumn<>("Allergen");
        allergenCol.setMinWidth(60);
        allergenCol.setMaxWidth(60);

        ingredientTable.getColumns().addAll(nameCol,quantityCol,measurementUnitCol,allergenCol);
        ingredientTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        ingredientTable.setPrefSize(700,1500);
        ingredientVBox.getChildren().add(ingredientTable);
        ingredientVBox.setPrefSize(600,700);

        VBox alignRight = new VBox();
        alignRight.getChildren().addAll(ingredientVBox,buttonsHBox);
        alignRight.setPadding(new Insets(5,5,5,5));


        HBox connectAll = new HBox();
        connectAll.getChildren().addAll(createVBox,alignRight);
        connectAll.setPadding(new Insets(5,5,5,5));

        this.setStyle("-fx-border-color: black;\n");
        this.getChildren().add(connectAll);
    }

    /**
     * Update the Ingredient selection menu whenever there is a
     * new ingredient added to InventoryModel
     * @param ingredientInventory
     */
    public void modelChanged(Map<Ingredient, Double> ingredientInventory,Ingredient loadedIngredient) {
        ingredientMenuBar.getMenus().clear();
        for(Ingredient.IngredientType type : Ingredient.IngredientType.values()){

            Menu typeMenu = new Menu(type.getName());
            //for each ingredient with the same type make it a menu item
            for(Map.Entry<Ingredient, Double> entry :ingredientInventory.entrySet() ){
                if(entry.getKey().getIngredientType().getName().equals(type.getName())){
                    MenuItem menuItem = new MenuItem(entry.getKey().getName());
                    typeMenu.getItems().add(menuItem);

                    //System.out.println("Added: " + entry.getKey().getName() + " to " + type.getName() + " menu");
                }
            }
            ingredientMenuBar.getMenus().add(typeMenu);

        }
    }
    /**
     * take updates from Recipie iModel to update recipie list
     * @param tempIngredientList
     */

    public void iModelChanged(Map<Ingredient, Double> tempIngredientList,Recipe loadedRecipe,boolean isCreating) {
        //make an ingredient widget and show it in the list
        ObservableList<IngredientData> data =  FXCollections.observableArrayList();
        ingredientTable.setItems(data);
        for (Map.Entry<Ingredient, Double> entry : tempIngredientList.entrySet()) {
            Ingredient ingredient = entry.getKey();
            Double quantity = entry.getValue();
            IngredientData theData = new IngredientData(ingredient,quantity);
            data.add(theData);
        }
        ingredientTable.setItems(data);
        nameCol.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
        quantityCol.setCellValueFactory(cellData -> cellData.getValue().inventoryQuantityProperty().asObject());
        measurementUnitCol.setCellValueFactory(cellData -> cellData.getValue().recipeMeasurementProperty());
        allergenCol.setCellValueFactory(cellData -> cellData.getValue().allergenProperty());

        if(loadedRecipe == null){
            if(!isCreating){
            getRecipeName().clear();
            getRecipePrice().clear();
            getRecipeDescription().clear();
            getRecipeInstruction().clear();
            getRecipePrep().clear();
            }

        }

        else{
            getRecipeName().setText(loadedRecipe.getName());
            getRecipePrice().setText(String.valueOf(loadedRecipe.getPrice()));
            getRecipeDescription().setText(loadedRecipe.getDescription());
            getRecipeInstruction().setText(loadedRecipe.getPrepInstruction());
            getRecipePrep().setText(String.valueOf(loadedRecipe.getPrepTime()));
        }

        getSelectedIngredient().clear();
        getMeasurementBox().setValue(null);
        getEnterMeasurementField().clear();

    }
    public void setRecipeModel(RecipeModel newModel){
        this.recipeModel = newModel;
    }
    public void setRecipeMakerIModel(RecipeInteractiveModel iModel){
        this.iModel = iModel;
    }
    public void setRecipeMakerController(ProgramController controller){
        mainMenu.setOnAction(controller::openStartUpMVC);
        recipeList.setOnAction(controller::openRecipeList);
        saveRecipe.setOnAction(controller::addRecipe);
        addIngredient.setOnAction(event -> {
            controller.setIModelCreating(event);
            controller.addIngredientToRecipe(event);
        });
        ingredientTable.setOnMouseClicked(controller::selectIngredient);
        deleteIngredient.setOnAction(controller::deleteIngredientFromRecipe);
    }



    /**
     * update event handlers for the menus
     * @param controller
     */
    public void updateMenuHandlers(ProgramController controller) {
        for(Menu types: ingredientMenuBar.getMenus() ){
            for(MenuItem ingredient : types.getItems()){

                ingredient.setOnAction(controller::ingredientSelected);
            }
        }
    }


    //getters for reading fields, will need setters to load recipes in the future
    public TextField getRecipeName() {
        return recipeName;
    }
    public TextField getRecipePrice() {
        return recipePrice;
    }
    public TextArea getRecipeDescription() {
        return recipeDescription;
    }
    public TextArea getRecipeInstruction() {
        return recipeInstruction;
    }
    public TextField getRecipePrep() {
        return recipePrep;
    }
    public TextField getSelectedIngredient() {
        return selectedIngredient;
    }
    public TextField getEnterMeasurementField() {
        return enterMeasurementField;
    }
    public ComboBox<Object> getMeasurementBox() {
        return measurementBox;
    }

    public TableView<IngredientData> getIngredientTable() {
        return ingredientTable;
    }
}
