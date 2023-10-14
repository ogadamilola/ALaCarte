package project.a_la_carte.prototype;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

public class InventoryView extends StackPane {
    InventoryModel inventoryModel;
    InventoryController inventoryController;
    /**
     * HBoxes and VBoxes up here are those that updates when changes are made
     */
    VBox listVBox;
    VBox addVBox;
    public InventoryView(){
        this.setMaxSize(1000,500);

        addVBox = new VBox();
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
        TextField nameText = new TextField();

        addNameHBox.getChildren().addAll(nameLabel, nameText);
        addNameHBox.setPrefWidth(300);
        addNameHBox.setPadding(new Insets(2,2,2,2));

        HBox addQuantityHBox = new HBox();
        //Same here (Variable)
        Label quantityLabel = new Label("Quantity:");
        TextField quantityText = new TextField();

        addQuantityHBox.getChildren().addAll(quantityLabel,quantityText);
        addQuantityHBox.setPrefWidth(300);
        addQuantityHBox.setPadding(new Insets(2,2,2,2));


        //Will probably have to be a variable as well to connect with controller class
        Button submit = new Button("Submit");

        addVBox.getChildren().addAll(addLabel,addNameHBox, addQuantityHBox, submit);
        addVBox.setPadding(new Insets(5,5,5,5));

        listVBox.setPrefSize(700,500);
        listVBox.setStyle("-fx-border-color: black;\n");
        Label listLabel = new Label("Inventory");
        listLabel.setFont(new Font(20));

        listVBox.getChildren().add(listLabel);
        listVBox.setPadding(new Insets(5,5,5,5));

        HBox mergeHBox = new HBox();
        mergeHBox.getChildren().addAll(addVBox,listVBox);

        this.getChildren().add(mergeHBox);
    }
    public void setModel(InventoryModel newModel){
        this.inventoryModel = newModel;

    }
    public void setController(InventoryController newController){
        this.inventoryController = newController;

    }
}
