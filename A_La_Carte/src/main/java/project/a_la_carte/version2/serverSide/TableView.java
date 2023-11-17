package project.a_la_carte.version2.serverSide;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;



import project.a_la_carte.version2.ProgramController;
import project.a_la_carte.version2.classesObjects.MenuFoodItem;
import project.a_la_carte.version2.classesObjects.Order;
import project.a_la_carte.version2.interfaces.ServerViewInterface;
import project.a_la_carte.version2.serverSide.tableSystem.Bill;
import project.a_la_carte.version2.serverSide.tableSystem.Table;

import java.util.ArrayList;


public class TableView extends StackPane implements ServerViewInterface {

    private ArrayList<Table> tables;
    private GridPane grid;

    Button back;

    private Button addButton;
    private Button removeButton;


    ServerModel serverModel;

    public TableView() {
        this.setPrefSize(1000,500);

        Label title = new Label("TABLES");
        title.setFont(new Font(20));
        tables = new ArrayList<>();

        double r = 2;
        this.back = new Button("<");
        this.back.setShape(new Circle(r));
        this.back.setMinSize(2*r,2*r);
        this.back.setStyle("-fx-border-color: black;-fx-background-color: paleturquoise;\n");

        HBox backHBox = new HBox(back);
        backHBox.setPrefWidth(200);

        HBox titleHBox = new HBox(title);
        titleHBox.setPrefWidth(600);
        titleHBox.setAlignment(Pos.TOP_CENTER);

        HBox topHBox = new HBox(backHBox, titleHBox);
        topHBox.setPrefWidth(1000);
        topHBox.setStyle("-fx-border-color: black;\n");
        topHBox.setPadding(new Insets(5));

        grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        this.getChildren().add(grid);



        addButton = new Button("Add Table");
        addButton.setOnAction(e -> addTable(new Table()));

        removeButton = new Button("Remove Table");
        removeButton.setOnAction(e -> removeLastTable());

        HBox controlButtons = new HBox(addButton, removeButton);
        controlButtons.setAlignment(Pos.CENTER);
        controlButtons.setSpacing(10);

        VBox container = new VBox(topHBox, grid, controlButtons);
        container.setSpacing(10);

        this.getChildren().add(container);


        updateView();
    }

    public void addTable(Table table) {
        tables.add(table);
        updateView();
    }

    public void removeTable(Table table) {
        tables.remove(table);
        updateView();
    }


    private void updateView() {
        grid.getChildren().clear(); // Clear existing views
        int numColumns = 3;
        for (int i = 0; i < tables.size(); i++) {
            Table table = tables.get(i);
            Button tableButton = new Button("Table " + table.getNumber());
            tableButton.setStyle(table.getStatus() ? "-fx-background-color: red;" : "-fx-background-color: green;");
            tableButton.setOnAction(e -> showTableDetails(table));

            int column = i % numColumns;
            int row = i / numColumns;

            grid.add(tableButton, column, row);
        }
    }

    private void removeLastTable() {
        if (!tables.isEmpty()) {
            tables.remove(tables.size() - 1);
            updateView();
        }
    }

    public void setServerModel(ServerModel newModel){
        this.serverModel = newModel;
    }

    private void showTableDetails(Table table) {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Table Details");
        alert.setHeaderText("Details for Table " + table.getNumber());

        // Create UI components
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        TextField occupantsField = new TextField(String.valueOf(table.getOccupants()));
        occupantsField.setPromptText("Occupants");

        CheckBox statusCheckBox = new CheckBox("Occupied");
        statusCheckBox.setSelected(table.getStatus());

        TextArea orderTextArea = new TextArea(table.getOrder().toString());
        orderTextArea.setEditable(false);



        grid.add(new Label("Occupants:"), 0, 0);
        grid.add(occupantsField, 1, 0);
        grid.add(new Label("Status:"), 0, 1);
        grid.add(statusCheckBox, 1, 1);
        grid.add(new Label("Order:"), 0, 2);
        grid.add(orderTextArea, 1, 2);

        // Set the alert's content
        alert.getDialogPane().setContent(grid);

        // Handling the confirmation result
        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                // Update table properties based on user input
                table.setOccupants(Integer.parseInt(occupantsField.getText()));
                table.setStatus(statusCheckBox.isSelected());
                // update the order if needed

                updateView(); // Refresh the table view
            }
        });
    }
    public void setController(ProgramController controller){
        this.back.setOnAction(controller::openMenuView);

    }
    @Override
    public void modelChanged() {
        updateView();
    }

}