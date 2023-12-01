package project.a_la_carte.version2.kitchen;

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
import project.a_la_carte.version2.interfaces.*;
import project.a_la_carte.version2.kitchen.widgets.OrderKitchenTab;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicReference;

/**
 * The main display for Kitchen workers
 */
public class KitchenView extends StackPane implements KitchenViewsInterface {
    WorkerView workerView;
    KitchenModel kitchenModel;
    Button mainMenu;
    AlertButton alertButton;
    Button sendNoteButton;
    FlowPane ordersVBox;
    ArrayList<OrderKitchenTab> orderKitchenTabs;
    public KitchenView(WorkerView view){
        orderKitchenTabs = new ArrayList<>();
        this.workerView = view;
        this.setMaxSize(5000,2500);
        this.setPrefSize(1000,500);

        Label menuTitle = new Label("ORDERS");
        menuTitle.setFont(new Font(20));

        this.mainMenu = new Button("Main Menu");
        this.sendNoteButton = new Button("Send Note");

        HBox menuHBox = new HBox(mainMenu);
        menuHBox.setPrefWidth(200);

        HBox titleHBox = new HBox(menuTitle);
        titleHBox.setPrefWidth(600);
        titleHBox.setAlignment(Pos.TOP_CENTER);
        HBox.setHgrow(titleHBox, Priority.ALWAYS);

        this.alertButton = new AlertButton("!");
        HBox alertBox = new HBox(sendNoteButton,alertButton);
        alertBox.setSpacing(10);
        alertBox.setPrefWidth(200);
        alertBox.setAlignment(Pos.BASELINE_RIGHT);
        HBox.setHgrow(alertBox,Priority.ALWAYS);

        HBox topHBox = new HBox(menuHBox, titleHBox, alertBox);
        topHBox.setPrefWidth(1000);
        topHBox.setPadding(new Insets(5,5,5,5));
        topHBox.setStyle("-fx-border-color: black;\n");

        this.ordersVBox = new FlowPane();
        this.ordersVBox.setPrefSize(998,455);
        this.ordersVBox.setPadding(new Insets(2,2,2,2));
        ordersVBox.setHgap(3);
        ordersVBox.setVgap(3);
        //Bordering it red just to show the area the orders take up
        this.ordersVBox.setStyle("-fx-border-color: red;\n");
        ordersVBox.prefWidthProperty().bind(Bindings.add(-2,this.widthProperty()));
        ordersVBox.prefHeightProperty().bind(Bindings.add(-47,this.heightProperty()));

        ScrollPane ordersScroll = new ScrollPane(ordersVBox);
        ordersScroll.setPrefSize(1000,500);

        VBox align = new VBox(topHBox,ordersScroll);
        align.setPrefSize(1000,500);
        HBox.setHgrow(topHBox,Priority.ALWAYS);
        VBox.setVgrow(ordersScroll, Priority.ALWAYS);

        this.getChildren().addAll(align);
    }

    /**
     * Set method for KitchenView's KitchenModel
     */
    public void setKitchenModel(KitchenModel newModel){
        this.kitchenModel = newModel;
    }

    /**
     * Connecting KitchenView to the program's controller
     */
    public void setController(ProgramController controller){
        this.mainMenu.setOnAction((event -> {
            controller.openWorkerView(this.workerView);
        }));
        this.alertButton.setOnAction(controller::showKitchenAlerts);
        this.sendNoteButton.setOnAction(controller::alertSenderToServer);
    }

    /**
     * Method for checking if OrderKitchenTab is already in the KitchenView
     */
    public boolean isNotDisplayed(int val){
        AtomicReference<Boolean> check = new AtomicReference<>(false);
        orderKitchenTabs.forEach(tabs ->{
            if (tabs.getSingleOrder().getOrderNum() == val){
                check.set(true);
            }
        });
        return check.get();
    }


    public void modelChanged(){
        this.ordersVBox.getChildren().clear();

        if (!this.kitchenModel.getNoteList().isEmpty()){
            this.alertButton.notificationYes();
        }
        else {
            this.alertButton.notificationNo();
        }
        if (kitchenModel.getActiveOrders() != null){
            kitchenModel.getActiveOrders().forEach((order -> {
                OrderKitchenTab newTab = new OrderKitchenTab(kitchenModel,order);
                order.addSubscriber(newTab);
                newTab.getCancelButton().setOnAction((event -> {
                    newTab.setFinished();
                    kitchenModel.deleteOrder(order);
                }));
                if (!isNotDisplayed(newTab.getSingleOrder().getOrderNum())) {
                    this.orderKitchenTabs.add(newTab);
                }
            }));
        }
        this.orderKitchenTabs.forEach(tabs -> {
            if (!tabs.isFinished()) {
                this.ordersVBox.getChildren().add(tabs);
            }
        });
    }
}
