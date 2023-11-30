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

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicReference;

public class OrderKitchenTab extends StackPane implements OrderClassesInterface {
    Label orderLabel;
    VBox ordersVBox;
    Button cancelButton;
    Order orderItems;
    KitchenModel kModel;
    ArrayList<OrderItems> orderItemList;
    public OrderKitchenTab(KitchenModel model, Order order){
        this.setPrefSize(630,70);
        orderItemList = new ArrayList<>();
        kModel = model;
        this.cancelButton = new Button("Complete");
        HBox cancelBox = new HBox(cancelButton);
        cancelBox.setPrefWidth(230);
        cancelBox.setAlignment(Pos.BOTTOM_CENTER);
        cancelBox.setPadding(new Insets(2));

        this.orderItems = order;
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

        orderItems.getOrderList().forEach(menuItem -> {
            OrderItems orderI = new OrderItems(this, menuItem);
            this.orderItemList.add(orderI);
        });
        this.getChildren().add(align);
    }
    public void removeItem(OrderItems item){
        ordersVBox.getChildren().remove(item);
        modelChanged();
    }
    public Button getCancelButton(){
        return this.cancelButton;
    }
    public boolean isNotIn(OrderItems items){
        AtomicReference<Boolean> check = new AtomicReference<>(false);
        orderItemList.forEach(orderItems1 -> {
            if (orderItems1.totalTimeElapsedStopWatch.getElapsedTime() > 0){
                check.set(true);
            }
        });
        return check.get();
    }
    public Order getOrderItems(){
        return this.orderItems;
    }
    @Override
    public void modelChanged() {
        ordersVBox.getChildren().clear();

        if (!orderItems.isFinished()){
            this.orderItemList.forEach((order ->{
                ordersVBox.getChildren().add(order);
            }));
        }
        else {
            kModel.deleteOrder(orderItems);
        }

        orderLabel.setText("Order #"+ orderItems.getOrderNum());
    }
}
