package project.a_la_carte.prototype.recipe.maker.inventory;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import project.a_la_carte.prototype.ProgramController;

public class InventoryView extends StackPane {
    InventoryModel inventoryModel;
    /**
     * HBoxes and VBoxes up here are those that updates when changes are made
     */
    VBox listVBox;
    TextField nameText;
    TextField quantityText;
    Button submit;
    Button mainMenu;
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


        //Will probably have to be a variable as well to connect with controller class
        submit = new Button("Submit");
        mainMenu = new Button("Main Menu");

        addVBox.getChildren().addAll(mainMenu, addLabel,addNameHBox, addQuantityHBox, submit);
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
    public void setController(ProgramController controller){
        mainMenu.setOnAction(controller::openStartUpMVC);

    }
}
