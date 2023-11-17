package project.a_la_carte.version2.kitchen;

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
import project.a_la_carte.version2.classesObjects.AlertButton;
import project.a_la_carte.version2.interfaces.*;
import project.a_la_carte.version2.kitchen.widgets.OrderKitchenTab;

public class KitchenView extends StackPane implements KitchenViewsInterface {
    KitchenModel kitchenModel;
    Button mainMenu;
    AlertButton alertButton;
    Button sendNoteButton;
    FlowPane ordersVBox;
    public KitchenView(){
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

        this.alertButton = new AlertButton("!");
        HBox alertBox = new HBox(sendNoteButton,alertButton);
        alertBox.setSpacing(10);
        alertBox.setPrefWidth(200);
        alertBox.setAlignment(Pos.BASELINE_RIGHT);

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

        ScrollPane ordersScroll = new ScrollPane(ordersVBox);
        ordersScroll.setPrefSize(1000,500);

        VBox align = new VBox(topHBox,ordersScroll);
        align.setPrefSize(1000,500);

        this.getChildren().addAll(align);
    }
    public void setKitchenModel(KitchenModel newModel){
        this.kitchenModel = newModel;
    }
    public void setController(ProgramController controller){
        this.mainMenu.setOnAction(controller::openWorkerView);
        this.alertButton.setOnAction(controller::showKitchenAlerts);
        this.sendNoteButton.setOnAction(controller::alertSenderToServer);
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
                    kitchenModel.deleteOrder(order);
                }));
                this.ordersVBox.getChildren().add(newTab);
            }));
        }
    }
}
