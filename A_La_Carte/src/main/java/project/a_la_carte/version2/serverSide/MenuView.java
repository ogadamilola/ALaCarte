package project.a_la_carte.version2.serverSide;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
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
    Label title;
    Label total;
    ArrayList<MenuFoodItem> menuFoodDisplayList;
    MenuFoodItem selectedItem;
    Order currentOrder;
    float price = 0;
    public MenuView(WorkerView view){
        menuFoodDisplayList = new ArrayList<>();
        this.workerView = view;
        this.setPrefSize(1000,500);
        Label menuTitle = new Label("MENU ITEMS");
        menuTitle.setFont(new Font(20));

        this.mainMenu = new Button("Main Menu");
        HBox menuHBox = new HBox(mainMenu);
        menuHBox.setPrefWidth(100);

        this.stockButton = new AlertButton("Stock");
        HBox stockButtonBox = new HBox(stockButton);
        stockButtonBox.setPrefWidth(100);
        stockButtonBox.setAlignment(Pos.BASELINE_RIGHT);

        this.Tables = new Button("Tables");
        HBox tablesHBox = new HBox(Tables);
        tablesHBox.setPrefWidth(100);

        HBox titleHBox = new HBox(menuTitle);
        titleHBox.setPrefWidth(350);
        titleHBox.setAlignment(Pos.TOP_CENTER);

        this.alertButton = new AlertButton("Alerts!");
        HBox alertBox = new HBox(alertButton);
        alertBox.setPrefWidth(100);
        alertBox.setAlignment(Pos.BASELINE_RIGHT);

        HBox topHBox = new HBox(menuHBox, titleHBox, stockButtonBox,alertBox, tablesHBox);
        topHBox.setPrefWidth(600);
        topHBox.setPadding(new Insets(5,5,5,5));
        topHBox.setStyle("-fx-border-color: black;\n");

        this.addNote = new Button("ADD NOTE");
        this.customize = new Button("CUSTOMIZE");
        this.refund = new Button("REFUND ORDER");

        HBox customizeHBox = new HBox(customize);
        customizeHBox.setPrefWidth(295);
        customizeHBox.setPadding(new Insets(5));

        HBox refundBox = new HBox(refund);
        refundBox.setPrefWidth(300);
        refundBox.setPadding(new Insets(5));
        refundBox.setAlignment(Pos.BASELINE_RIGHT);

        HBox botButtonsBox = new HBox(customizeHBox,refundBox);
        botButtonsBox.setPrefWidth(600);

        HBox addNoteBox = new HBox(addNote);
        addNoteBox.setPrefWidth(600);
        addNoteBox.setPadding(new Insets(5));

        VBox buttons = new VBox(addNoteBox,botButtonsBox);
        buttons.setPrefSize(600,100);
        buttons.setPadding(new Insets(5,5,5,5));
        buttons.setAlignment(Pos.BOTTOM_LEFT);

        menuDisplay = new FlowPane();
        menuDisplay.setPrefSize(584,375);
        menuDisplay.setHgap(4);
        menuDisplay.setVgap(2);
        menuDisplay.setPadding(new Insets(5));

        ScrollPane displayScroll = new ScrollPane(menuDisplay);
        displayScroll.setPrefSize(600,400);

        VBox alignAllLeft = new VBox(topHBox,displayScroll,buttons);
        alignAllLeft.setPrefSize(600,500);


        //View Orders
        title = new Label("VIEW ORDER");
        title.setFont(new Font(20));

        HBox titleViewHBox = new HBox(title);
        titleViewHBox.setPrefWidth(400);
        titleViewHBox.setAlignment(Pos.TOP_CENTER);
        titleViewHBox.setStyle("-fx-border-color: black;\n");

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

        HBox sendTKHBox = new HBox(voidOrderButton,sendToKitchen);
        sendTKHBox.setPrefSize(250,100);
        sendTKHBox.setAlignment(Pos.BASELINE_RIGHT);
        sendTKHBox.setSpacing(5);
        sendTKHBox.setPadding(new Insets(5));

        HBox alignBot = new HBox(totalBox,sendTKHBox);
        alignBot.setPrefSize(400,100);

        this.ordersVBox = new VBox();
        this.ordersVBox.setPrefSize(370,500);
        this.ordersVBox.setPadding(new Insets(5,50,5,50));

        ScrollPane ordersFlow = new ScrollPane(ordersVBox);
        ordersFlow.setPrefSize(400,500);

        VBox alignRight = new VBox(titleViewHBox,ordersFlow,alignBot);
        alignRight.setPrefSize(400,500);
        alignRight.setStyle("-fx-border-color: black;\n");

        HBox alignAll = new HBox(alignAllLeft,alignRight);
        alignAll.setPrefSize(1000,500);

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
