package project.a_la_carte.version2.managerSide.recipe;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import project.a_la_carte.version2.classesObjects.*;
import project.a_la_carte.version2.managerSide.inventory.InventoryModel;

import java.util.Map;

public class ShowRecipeInfoView {

    public ShowRecipeInfoView(Recipe recipe){
        Stage stage = new Stage();
        stage.setTitle("Recipe Info");

        VBox container = new VBox();

        TableView<IngredientData> ingredientTable = new TableView<>();
        TableColumn<IngredientData,String> ingredientNameCol = new TableColumn<>("Ingredient Name");
        TableColumn<IngredientData,Double> quantityCol = new TableColumn<>("Quantity");
        TableColumn<IngredientData,String> measurementUnitCol = new TableColumn<>("Measurement Unit");
        TableColumn<IngredientData, Boolean> allergenCol = new TableColumn<>("Allergen");

        //same code from IModel changed, could be refactored in the future to clean up
        ObservableList<IngredientData> ingredientData = FXCollections.observableArrayList();

        InventoryModel tempInvModel = new InventoryModel();

        ingredientNameCol.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
        quantityCol.setCellValueFactory(cellData -> cellData.getValue().recipeQuantityProperty().asObject());
        measurementUnitCol.setCellValueFactory(cellData -> cellData.getValue().recipeMeasurementProperty());
        allergenCol.setCellValueFactory(cellData -> cellData.getValue().allergenProperty());
        ingredientTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        ingredientTable.getColumns().addAll(ingredientNameCol,quantityCol,measurementUnitCol,allergenCol);

        System.out.println(recipe.getRecipeIngredients().size());
        for(Map.Entry<String, Double> entry : recipe.getRecipeIngredients().entrySet()){

            Double ingredientQuantity = entry.getValue();
            Ingredient ingredient = tempInvModel.getIngredientFromList(entry.getKey());
            IngredientData iData = new IngredientData(ingredient,ingredientQuantity);
            ingredientData.add(iData);
        }

        ingredientTable.setItems(ingredientData);



        //All these are copy pasted from Recipe List View
        Label prepInstruction = new Label("Preparation \n Instruction: ");
        TextField recipePrepIText = new TextField();
        recipePrepIText.setText(recipe.getPrepInstruction());
        recipePrepIText.setStyle("-fx-border-color: black;\n");
        recipePrepIText.setPrefSize(400,100);
        recipePrepIText.setEditable(false);

        HBox prepInstructionHBox = new HBox(prepInstruction, recipePrepIText);
        prepInstructionHBox.setPadding(new Insets(5,5,5,5));

        Label prepTime = new Label("Estimated Preptime: ");
        TextField recipePrepTimeText = new TextField();
        recipePrepTimeText.setText(String.valueOf(recipe.getPrepTime()));
        recipePrepTimeText.setStyle("-fx-border-color: black;\n");
        recipePrepTimeText.setPrefWidth(150);
        recipePrepTimeText.setEditable(false);
        HBox prepTimeHBox = new HBox(prepTime,recipePrepTimeText);
        prepTimeHBox.setPadding(new Insets(5,5,5,5));


        container.getChildren().addAll(ingredientTable,prepInstructionHBox,prepTimeHBox);
        Scene scene = new Scene(container,500,500);
        stage.setScene(scene);
        stage.setOnCloseRequest(e -> stage.close());

        stage.show();


    }

}
