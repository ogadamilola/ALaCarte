package project.a_la_carte.version2.kitchen.widgets;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import project.a_la_carte.version2.kitchen.*;
import project.a_la_carte.version2.classesObjects.Order;
import project.a_la_carte.version2.interfaces.*;

public class OrderKitchenTab extends StackPane implements OrderClassesInterface {
    Label orderLabel;
    //We can probably add a ScrollPane for the VBox so that its not cluttered and it scrolls
    VBox ordersVBox;
    Button cancelButton;
    Order singleOrder;
    KitchenModel kModel;
    public OrderKitchenTab(KitchenModel model, Order order){
        this.setPrefSize(630,70);
        kModel = model;
        this.cancelButton = new Button("Cancel");
        HBox cancelBox = new HBox(cancelButton);
        cancelBox.setPrefWidth(230);
        cancelBox.setAlignment(Pos.BOTTOM_CENTER);
        cancelBox.setPadding(new Insets(2));

        this.singleOrder = order;
        orderLabel = new Label("");

        HBox titleBox = new HBox(orderLabel);
        titleBox.setPrefWidth(230);
        titleBox.setAlignment(Pos.CENTER);
        titleBox.setStyle("-fx-border-color: black;\n");

        this.ordersVBox = new VBox();
        this.ordersVBox.setPrefSize(230,70);
        this.ordersVBox.setSpacing(2);
        this.ordersVBox.setStyle("-fx-border-color: black;\n");

        VBox align = new VBox(titleBox,this.ordersVBox, cancelBox);
        align.setPrefSize(230,70);

        this.getChildren().add(align);
    }
    public Button getCancelButton(){
        return this.cancelButton;
    }
    @Override
    public void modelChanged() {
        ordersVBox.getChildren().clear();

        if (!singleOrder.isFinished()){
            if (!singleOrder.isStarted()) {
                singleOrder.startTimers(singleOrder);
            }
            OrderWidget newDisplay = new OrderWidget(this, singleOrder);
            ordersVBox.getChildren().add(newDisplay);
        }
        else {
            kModel.deleteOrder(singleOrder);
        }


        orderLabel.setText("Order #"+ singleOrder.getOrderNum());
    }
}
