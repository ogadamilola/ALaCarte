package project.a_la_carte.version2.managerSide.RestaurantInfo;


import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;

import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import project.a_la_carte.version2.ProgramController;

import project.a_la_carte.version2.classesObjects.MenuFoodItemData;
import project.a_la_carte.version2.interfaces.RestaurantModelSubscriber;



public class RestaurantInfoView extends BorderPane implements RestaurantModelSubscriber {

    RestaurantModel restaurantModel;
    Button mainMenu;
    Button startDay;
    Button endDay;
    TableView<MenuFoodItemData> menuTable;
    TableColumn<MenuFoodItemData, String> menuItemNameCol;
    TableColumn<MenuFoodItemData, Double> menuItemQuantityCol;
    Label orderNumber;
    Label incomeNumber;
    VBox menuItemVBox;
    Label date;
    DatePicker datePicker;

    Button reportButton;
    TableView<MenuFoodItemData> ingredientTable; //using MenuFoodItem data to display ingredients
    TableColumn<MenuFoodItemData, String> ingredientNameCol;
    TableColumn<MenuFoodItemData, Double> ingredientQuantityCol;
    public RestaurantInfoView(){

        Label title = new Label("Manage Restaurant");
        date = new Label("Day not started!");
        title.setFont(new Font(20));
        HBox titleHBox = new HBox(title,date);
        titleHBox.setSpacing(60);
        titleHBox.setPrefWidth(600);
        titleHBox.setAlignment(Pos.TOP_CENTER);

        this.mainMenu = new Button("Main Menu");
        HBox menuHBox = new HBox();
        menuHBox.setPrefWidth(200);
        menuHBox.getChildren().add(mainMenu);

        HBox topBox = new HBox(menuHBox, titleHBox);
        topBox.setPrefWidth(1000);
        topBox.setStyle("-fx-border-color: black;\n");
        topBox.setPadding(new Insets(5));

        //TODO show total orders for today

        Label orderMessage = new Label("Today's orders");
        orderNumber = new Label("0");
        VBox orderVBox = new VBox(orderMessage,orderNumber);
        orderVBox.setAlignment(Pos.CENTER);  // Center the content vertically
        orderVBox.setStyle("-fx-border-color: black; -fx-border-width: 2px;"); // Add a black border
        orderVBox.setPrefSize(200,50);

        //TODO show menuItems ordered Today

        menuTable = new TableView<>();

        menuItemNameCol = new TableColumn<>("Menu Item");
        menuItemQuantityCol = new TableColumn<>("Times Ordered");
        menuTable.getColumns().addAll(menuItemNameCol,menuItemQuantityCol);
        menuTable.setColumnResizePolicy(TableView.UNCONSTRAINED_RESIZE_POLICY);

        menuItemVBox = new VBox(menuTable);

        //TODO show todays income


        Label incomeMessage = new Label("Today's Income");
        incomeNumber = new Label("0");
        VBox incomeVBox = new VBox(incomeMessage, incomeNumber);
        incomeVBox.setAlignment(Pos.CENTER);  // Center the content vertically
        incomeVBox.setStyle("-fx-border-color: black; -fx-border-width: 2px;"); // Add a black border
        incomeVBox.setPrefSize(200,50);

        //TODO show inventory usage

        ingredientTable = new TableView<>();
        ingredientNameCol = new TableColumn<>("Ingredient");
        ingredientQuantityCol = new TableColumn<>("Quantity Used");
        ingredientTable.getColumns().addAll(ingredientNameCol,ingredientQuantityCol);

        VBox inventoryVBox = new VBox(ingredientTable);

        HBox centerBox = new HBox(orderVBox,menuItemVBox,incomeVBox,inventoryVBox); // display inventory usage

        startDay = new Button("Start new day");
        endDay = new Button("End day");
        HBox bottomBox = new HBox(startDay,endDay);


        Label reportLabel = new Label("Pick a date to view report");
        datePicker = new DatePicker();
        reportButton = new Button("Show Date Report ");


        VBox rightBox = new VBox(reportLabel,datePicker,reportButton);
        rightBox.setAlignment(Pos.CENTER);


        this.setTop(topBox);
        this.setRight(rightBox);
        this.setCenter(centerBox);
        this.setBottom(bottomBox);


    }

    public void setRestaurantModel(RestaurantModel restaurantModel) {
        this.restaurantModel = restaurantModel;
    }

    public void setController(ProgramController controller){

        mainMenu.setOnAction(controller::openManagerMainView);
        reportButton.setOnAction(controller::generateReport);

    }

    @Override
    public void restaurantModelChanged(ObservableList<MenuFoodItemData> menuItemData, ObservableList<MenuFoodItemData> ingredientUsagedata, RestaurantDay today) {

        menuTable.setItems(menuItemData);
        menuItemNameCol.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
        menuItemQuantityCol.setCellValueFactory(cellData -> cellData.getValue().quantityProperty().asObject());

        ingredientTable.setItems(ingredientUsagedata);
        ingredientNameCol.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
        ingredientQuantityCol.setCellValueFactory(cellData -> cellData.getValue().quantityProperty().asObject());

        date.setText(today.getDate());
        orderNumber.setText(String.valueOf(today.getTotalOrders()));
        incomeNumber.setText(String.valueOf(today.getIncomeToday()));
    }

    public DatePicker getDatePicker() {
        return datePicker;
    }
}
