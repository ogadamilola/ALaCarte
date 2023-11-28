package project.a_la_carte.version2.serverSide;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

import project.a_la_carte.version2.classesObjects.Ingredient;
import project.a_la_carte.version2.classesObjects.IngredientData;
import project.a_la_carte.version2.interfaces.InventorySubscriber;
import project.a_la_carte.version2.managerSide.inventory.InventoryModel;

import java.util.ArrayList;
import java.util.Map;

public class ServerStockAlertView extends StackPane implements InventorySubscriber {
    FlowPane alertBox;
    TableView<IngredientData> alertTable;
    InventoryModel model;
    TableColumn<IngredientData,String> ingredientCol;

    public ServerStockAlertView(InventoryModel model){
        this.model = model;
        this.setPrefSize(600,400);
        Label title = new Label("86'd Ingredients");
        title.setFont(new Font(20));
        HBox titleBox = new HBox(title);
        titleBox.setPrefWidth(600);
        titleBox.setStyle("-fx-border-color: black;\n");
        titleBox.setAlignment(Pos.CENTER);


        alertBox = new FlowPane();
        alertBox.setPrefSize(600,400);
        alertBox.setPadding(new Insets(5));
        alertBox.setStyle("-fx-border-color: black;\n");

        alertTable = new TableView<>();
        ingredientCol = new TableColumn<>("Ingredients");
        alertTable.getColumns().add(ingredientCol);

        alertBox.getChildren().add(alertTable);
        alertBox.setAlignment(Pos.CENTER);

        VBox align = new VBox(titleBox,alertBox);
        align.setPrefSize(600,400);

        this.getChildren().add(align);

    }

    @Override
    public void modelChanged(Map<String, Double> ingredientInventory, Ingredient loadedIngredient, Map<String,Ingredient> ingredientMap) {
        ObservableList<IngredientData> data = FXCollections.observableArrayList();

        for (Map.Entry<String, Double> entry : ingredientInventory.entrySet()) {

            Ingredient ingredient = model.getIngredientFromList(entry.getKey());
            Double quantity = entry.getValue();
            if(quantity <=0) {
                IngredientData theData = new IngredientData(ingredient, quantity);
                data.add(theData);
            }
        }

        alertTable.setItems(data);
        ingredientCol.setCellValueFactory(cellData -> cellData.getValue().nameProperty());

    }
}
