package project.a_la_carte.version2.serverSide;

import javafx.beans.binding.Bindings;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import project.a_la_carte.version2.ProgramController;
import project.a_la_carte.version2.WorkerView;
import project.a_la_carte.version2.classesObjects.AlertButton;
import project.a_la_carte.version2.classesObjects.MenuFoodItem;
import project.a_la_carte.version2.classesObjects.Order;
import project.a_la_carte.version2.serverSide.widgets.OrderListView;
import project.a_la_carte.version2.interfaces.ServerViewInterface;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicReference;

public class MenuView extends StackPane implements ServerViewInterface {
    WorkerView workerView;
    ServerModel serverModel;
    FlowPane menuDisplay;
    Button addNote;
    Button customize;
    Button refund;
    Button mainMenu;
    Button Tables;
    AlertButton alertButton;
    AlertButton stockButton;
    //View Order Variables
    VBox ordersVBox;
    Button sendToKitchen;
    Button voidOrderButton;
    Button autoSend;
    Label title;
    Label total;
    ArrayList<MenuFoodItem> menuFoodDisplayList;
    MenuFoodItem selectedItem;
    Order currentOrder;
    float price = 0;
    public MenuView(WorkerView view){
        menuFoodDisplayList = new ArrayList<>();
        this.workerView = view;
        this.setMaxSize(5000,2500);
        this.setPrefSize(1000,500);
        Label menuTitle = new Label("MENU ITEMS");
        menuTitle.setFont(new Font(20));

        this.mainMenu = new Button("Main Menu");
        HBox menuHBox = new HBox(mainMenu);
        menuHBox.setPrefWidth(100);

        this.stockButton = new AlertButton("Stock");
        this.Tables = new Button("Tables");

        HBox titleHBox = new HBox(menuTitle);
        titleHBox.setPrefWidth(100);
        titleHBox.setAlignment(Pos.BASELINE_RIGHT);
        HBox.setHgrow(titleHBox, Priority.ALWAYS);

        this.alertButton = new AlertButton("Alerts!");
        HBox alertBox = new HBox(stockButton, alertButton, Tables);
        alertBox.setPrefWidth(100);
        alertBox.setAlignment(Pos.BASELINE_RIGHT);
        HBox.setHgrow(alertBox, Priority.ALWAYS);
        alertBox.setPadding(new Insets(2));
        alertBox.setSpacing(3);

        HBox topHBox = new HBox(menuHBox, titleHBox,alertBox);
        topHBox.setPrefWidth(600);
        topHBox.setPadding(new Insets(5,5,5,5));
        topHBox.setStyle("-fx-border-color: black;\n");
        HBox.setHgrow(topHBox, Priority.ALWAYS);

        this.addNote = new Button("ADD NOTE");
        this.customize = new Button("CUSTOMIZE");
        this.refund = new Button("REFUND ORDER");
        this.autoSend = new Button("ADD TO ORDER");

        HBox customizeHBox = new HBox(customize);
        customizeHBox.setPrefWidth(295);
        customizeHBox.setPadding(new Insets(5));

        HBox refundBox = new HBox(refund);
        refundBox.setPrefWidth(300);
        refundBox.setPadding(new Insets(5));
        refundBox.setAlignment(Pos.BASELINE_RIGHT);
        HBox.setHgrow(refundBox, Priority.ALWAYS);

        HBox botButtonsBox = new HBox(customizeHBox,refundBox);
        botButtonsBox.setPrefWidth(600);
        HBox.setHgrow(botButtonsBox, Priority.ALWAYS);

        HBox addNoteBox = new HBox(addNote);
        addNoteBox.setPrefWidth(295);
        addNoteBox.setPadding(new Insets(5));

        HBox autoHBox = new HBox(autoSend);
        autoHBox.setPrefWidth(300);
        autoHBox.setPadding(new Insets(5));
        autoHBox.setAlignment(Pos.BASELINE_RIGHT);
        HBox.setHgrow(autoHBox,Priority.ALWAYS);

        HBox topButtonsBox = new HBox(addNoteBox,autoHBox);
        topButtonsBox.setPrefWidth(600);
        HBox.setHgrow(topButtonsBox, Priority.ALWAYS);

        VBox buttons = new VBox(topButtonsBox,botButtonsBox);
        buttons.setPrefSize(600,100);
        buttons.setPadding(new Insets(5,5,5,5));
        buttons.setAlignment(Pos.BOTTOM_LEFT);


        menuDisplay = new FlowPane();
        menuDisplay.setPrefSize(584,375);
        menuDisplay.setHgap(4);
        menuDisplay.setVgap(2);
        menuDisplay.setPadding(new Insets(5));
        menuDisplay.prefHeightProperty().bind(Bindings.add(-139,this.heightProperty()));
        menuDisplay.prefWidthProperty().bind(Bindings.add(-405,this.widthProperty()));

        ScrollPane displayScroll = new ScrollPane(menuDisplay);
        displayScroll.setPrefSize(600,400);
        displayScroll.prefHeightProperty().bind(Bindings.add(-25,this.heightProperty()));
        displayScroll.prefWidthProperty().bind(Bindings.add(-30,this.widthProperty()));

        VBox alignAllLeft = new VBox(topHBox,displayScroll,buttons);
        alignAllLeft.setPrefSize(600,500);
        VBox.setVgrow(alignAllLeft,Priority.ALWAYS);

        //View Orders
        title = new Label("VIEW ORDER");
        title.setFont(new Font(20));

        HBox titleViewHBox = new HBox(title);
        titleViewHBox.setPrefWidth(400);
        titleViewHBox.setAlignment(Pos.TOP_CENTER);
        titleViewHBox.setStyle("-fx-border-color: black;\n");
        HBox.setHgrow(titleViewHBox, Priority.ALWAYS);

        this.sendToKitchen = new Button("SEND ORDER");
        this.sendToKitchen.setStyle("-fx-border-color: black;-fx-background-color: lightskyblue;\n");

        this.voidOrderButton = new Button("VOID ORDER");
        this.voidOrderButton.setStyle("-fx-border-color: black;-fx-background-color: lightskyblue;\n");

        Label totalLabel = new Label("Total: $");
        totalLabel.setFont(new Font(20));
        this.total = new Label("");
        this.total.setFont(new Font(20));

        HBox totalBox = new HBox(totalLabel,total);
        totalBox.setPrefSize(150,100);
        totalBox.setPadding(new Insets(5));
        totalBox.setAlignment(Pos.BASELINE_LEFT);
        HBox.setHgrow(totalBox, Priority.ALWAYS);

        HBox sendTKHBox = new HBox(voidOrderButton,sendToKitchen);
        sendTKHBox.setPrefSize(250,100);
        sendTKHBox.setAlignment(Pos.BASELINE_RIGHT);
        sendTKHBox.setSpacing(5);
        sendTKHBox.setPadding(new Insets(5));
        HBox.setHgrow(sendTKHBox,Priority.ALWAYS);

        HBox alignBot = new HBox(totalBox,sendTKHBox);
        alignBot.setPrefSize(400,100);
        HBox.setHgrow(alignBot,Priority.ALWAYS);

        this.ordersVBox = new VBox();
        this.ordersVBox.setPrefSize(370,500);
        this.ordersVBox.setPadding(new Insets(5,50,5,50));
        VBox.setVgrow(ordersVBox,Priority.ALWAYS);

        ScrollPane ordersFlow = new ScrollPane(ordersVBox);
        ordersFlow.setPrefSize(400,500);
        ordersFlow.prefWidthProperty().bind(this.widthProperty());
        ordersFlow.prefHeightProperty().bind(this.heightProperty());

        VBox alignRight = new VBox(titleViewHBox,ordersFlow,alignBot);
        alignRight.setPrefSize(400,500);
        alignRight.setStyle("-fx-border-color: black;\n");
        VBox.setVgrow(alignRight,Priority.ALWAYS);

        HBox alignAll = new HBox(alignAllLeft,alignRight);
        alignAll.setMaxSize(5000,2500);
        HBox.setHgrow(alignAllLeft, Priority.ALWAYS);

        this.getChildren().addAll(alignAll);
    }
    public void setServerModel(ServerModel newModel){
        this.serverModel = newModel;
    }
    public void setController(ProgramController controller){
        this.mainMenu.setOnAction((event -> {
            controller.openWorkerView(this.workerView);
        }));
        this.addNote.setOnAction(event -> {
            controller.openNoteView(this.workerView);
        });
        this.customize.setOnAction(event -> {
            controller.openCustomizeView(this.workerView);
        });
        this.sendToKitchen.setOnAction(event -> {
            controller.sendToKitchen(this.workerView);
        });
        this.stockButton.setOnAction(controller::showStockAlerts);
        this.alertButton.setOnAction(controller::showServerAlerts);
        this.voidOrderButton.setOnAction(event -> {
            controller.voidOrder(this.workerView);
        });
        this.refund.setOnAction(controller::refundDisplay);
        this.Tables.setOnAction(event -> {
            controller.openTablesView(this.workerView);
        });
        this.autoSend.setOnAction(event -> {
            controller.saveCustomize(this.workerView);
        });


    }
    public void addMenuDisplay(MenuFoodItem foodItem){
        this.menuFoodDisplayList.add(foodItem);
    }
    public Boolean containsMenuDisplay(String foodName){
        AtomicReference<Boolean> check = new AtomicReference<>(false);
        menuFoodDisplayList.forEach(foodItem -> {
            if (foodItem.getName().equals(foodName)) {
                check.set(true);
            }
        });
        return check.get();
    }
    public void addToOrder(MenuFoodItem item){
        if (this.currentOrder == null){
            currentOrder = new Order(new ArrayList<>(),serverModel.orderNumber);
        }
        currentOrder.addItem(item);
    }
    public MenuFoodItem getSelectedItem(){
        return this.selectedItem;
    }
    public Order getCurrentOrder(){
        return this.currentOrder;
    }
    public void clearOrder(){
        this.currentOrder = null;
        modelChanged();
    }
    @Override
    public void modelChanged() {
        if (!serverModel.getNoteList().isEmpty()){
            alertButton.notificationYes();
        }
        else {
            alertButton.notificationNo();
        }
        if (serverModel.getMenuItemList() != null){
            serverModel.getMenuItemList().forEach((item -> {
                MenuFoodItem newItem = new MenuFoodItem(item.getMenuItemRecipes(), item.getName(), item.getDescription());
                newItem.getDisplay().setText(newItem.getName());
                newItem.getDisplay().setOnAction((event -> {
                    this.serverModel.setSelectedMenuItem(newItem, this.menuFoodDisplayList);
                    newItem.selectDisplay();
                    this.selectedItem = newItem;
                }));
                if (!this.containsMenuDisplay(newItem.getName())) {
                    this.addMenuDisplay(newItem);
                    menuDisplay.getChildren().add(newItem.getDisplay());
                }
            }));
        }
        this.menuFoodDisplayList.forEach((foodItem -> {
            if (foodItem.getSelectedStatus()){
                foodItem.selectDisplay();
            }
        }));

        ordersVBox.getChildren().clear();
        this.price = 0;
        if (this.currentOrder != null){
            title.setText("Order #"+ serverModel.orderNumber);
            currentOrder.getOrderList().forEach((item ->{
                OrderListView newView = new OrderListView(item);
                ordersVBox.getChildren().add(newView);
                price += item.getPrice();
                newView.getDeleteButton().setOnAction((event -> {
                    currentOrder.deleteItem(newView.getMenuItem());
                    serverModel.notifySubscribers();
                    ordersVBox.getChildren().remove(newView);
                }));
            }));
        }
        total.setText(String.valueOf(this.price));
    }
}
