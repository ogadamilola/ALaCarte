package project.a_la_carte.prototype.recipe.maker.inventory;

import javafx.beans.property.ReadOnlyDoubleWrapper;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import project.a_la_carte.prototype.ProgramController;

import java.util.Map;

public class InventoryView extends StackPane implements InventorySubscriber {
    //not sure if needed
    InventoryModel inventoryModel;
    /**
     * HBoxes and VBoxes up here are those that updates when changes are made
     */
    VBox listVBox;
    TextField nameText;
    TextField quantityText;
    Button submit;
    Button mainMenu;
    ComboBox<Ingredient.IngredientType> typeComboBox;
    ComboBox<Ingredient.MeasurementUnit> measurementUnitComboBox;
    CheckBox commonAllergenCheck;
    Button clearButton;
    Button updateButton;
    TableView<IngredientData> inventoryTable;
    TableColumn<IngredientData,String> nameCol;
    TableColumn<IngredientData,Double> quantityCol;
    TableColumn<IngredientData,String> typeCol;
    TableColumn<IngredientData,String> statusCol;
    TableColumn<IngredientData,String> measurementUnitCol;

    public InventoryView(){
        this.setMaxSize(1000,500);
        VBox addVBox = new VBox();
        listVBox = new VBox();

        addVBox.setPrefSize(300,500);


        addVBox.setStyle("-fx-border-color: black;\n");
        Label addLabel = new Label("Add To Inventory");
        addLabel.setFont(new Font(20));

        HBox addNameHBox = new HBox();
        Label nameLabel = new Label("Name:");
        //TextField might have to be added as a public variable,
        //since it's values will have to be used by the Model and Controller
        //Or by the planned ingredient class
        nameText = new TextField();

        addNameHBox.getChildren().addAll(nameLabel, nameText);
        addNameHBox.setPrefWidth(300);
        addNameHBox.setPadding(new Insets(2,2,2,2));

        HBox addQuantityHBox = new HBox();
        //Same here (Variable)
        Label quantityLabel = new Label("Quantity:");
        quantityText = new TextField();



        addQuantityHBox.getChildren().addAll(quantityLabel,quantityText);
        addQuantityHBox.setPrefWidth(300);
        addQuantityHBox.setPadding(new Insets(2,2,2,2));

        //select measurementUnit
        HBox measureHBox = new HBox();
        Label measurementLabel = new Label("Select measurement unit");
        measurementUnitComboBox = new ComboBox<>();
        for(Ingredient.MeasurementUnit mUnit : Ingredient.MeasurementUnit.values()){
            measurementUnitComboBox.getItems().add(mUnit);
        }
        measureHBox.getChildren().addAll(measurementLabel,measurementUnitComboBox);

        //select food type
        HBox typeHBox = new HBox();
        Label typeSelectLabel = new Label("Select type of Ingredient");
        typeComboBox = new ComboBox<>();
        typeComboBox.getEditor().setId("Type...");
        for(Ingredient.IngredientType i : Ingredient.IngredientType.values()){
            typeComboBox.getItems().add(i);
        }
        typeHBox.getChildren().addAll(typeSelectLabel,typeComboBox);


        commonAllergenCheck = new CheckBox("    Common Allergen");


        //Will probably have to be a variable as well to connect with controller class
        submit = new Button("Submit");
        updateButton = new Button("Update");
        mainMenu = new Button("Main Menu");
        clearButton = new Button("Clear");

        addVBox.getChildren().addAll(mainMenu, addLabel,addNameHBox, addQuantityHBox,measureHBox,typeHBox,commonAllergenCheck,updateButton,clearButton, submit);
        addVBox.setPadding(new Insets(5,5,5,5));

        //tables are so weird to work with but looks so much better
        inventoryTable = new TableView<>();
        nameCol = new TableColumn<>("Ingredient Name");
        nameCol.setMinWidth(120);

        quantityCol = new TableColumn<>("Quantity");
        quantityCol.setMaxWidth(70);
        quantityCol.setMinWidth(70);


        measurementUnitCol = new TableColumn<>("Unit");
        measurementUnitCol.setMinWidth(50);
        measurementUnitCol.setMaxWidth(50);

        typeCol = new TableColumn<>("Type");

        statusCol = new TableColumn<>("Status");

        inventoryTable.getColumns().addAll(nameCol,quantityCol,measurementUnitCol,typeCol,statusCol);
        inventoryTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);


        //use tableview here to show
        inventoryTable.setPrefSize(700,700);
        listVBox.setPrefSize(700,500);
        listVBox.setStyle("-fx-border-color: black;\n");
        listVBox.getChildren().add(inventoryTable);

        listVBox.setPadding(new Insets(5,5,5,5));

        HBox mergeHBox = new HBox();
        mergeHBox.getChildren().addAll(addVBox,listVBox);

        this.getChildren().add(mergeHBox);
    }

    //dont think we need this with the Inventory subscriber but i kept it
    public void setModel(InventoryModel newModel){
        this.inventoryModel = newModel;

    }
    public void setController(ProgramController controller){
        mainMenu.setOnAction(controller::openStartUpMVC);
        submit.setOnAction(controller::handleNewIngredient);
        inventoryTable.setOnMouseClicked(controller::loadIngredient);
        clearButton.setOnAction(controller::clearFields);
        updateButton.setOnAction(controller::updateItem);
    }

    public void modelChanged(Map<Ingredient, Double> ingredientInventory){
        //clear text fields
        nameText.clear();
        quantityText.clear();

        listVBox.getChildren().clear();//redraw list
        listVBox.getChildren().add(inventoryTable);

        //for debugging
        /*for (Map.Entry<Ingredient, Double> entry : ingredientInventory.entrySet()) {
            Ingredient ingredient = entry.getKey();
            Double quantity = entry.getValue();
            System.out.println(ingredient.getName() + " - Stock: " + quantity + " - " + ingredient.getMeasurementUnit().getName() + " - type " + ingredient.getIngredientType().getName());
        }*/

        //new way to display inventory
        ObservableList<IngredientData> data = FXCollections.observableArrayList();
        for (Map.Entry<Ingredient, Double> entry : ingredientInventory.entrySet()) {
            Ingredient ingredient = entry.getKey();
            Double quantity = entry.getValue();
            IngredientData theData = new IngredientData(ingredient,quantity);
            data.add(theData);
        }
        inventoryTable.setItems(data);
        nameCol.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
        quantityCol.setCellValueFactory(cellData -> cellData.getValue().quantityProperty().asObject());
        measurementUnitCol.setCellValueFactory(cellData -> cellData.getValue().measurementProperty());
        typeCol.setCellValueFactory(cellData -> cellData.getValue().typeProperty());
        statusCol.setCellValueFactory(cellData -> cellData.getValue().statusProperty());
    }


    public TextField getNameText() {
        return nameText;
    }

    public TextField getQuantityText() {
        return quantityText;
    }

    public ComboBox<Ingredient.IngredientType> getTypeComboBox() {
        return typeComboBox;
    }

    public ComboBox<Ingredient.MeasurementUnit> getMeasurementUnitComboBox() {
        return measurementUnitComboBox;
    }

    public TableView<IngredientData> getInventoryTable(){
        return inventoryTable;
    }

    public CheckBox getCommonAllergenCheck() {
        return commonAllergenCheck;
    }
}



