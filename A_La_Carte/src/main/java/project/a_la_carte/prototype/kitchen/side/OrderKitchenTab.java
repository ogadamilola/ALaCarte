package project.a_la_carte.prototype.kitchen.side;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import project.a_la_carte.prototype.server.side.Order;
import project.a_la_carte.prototype.server.side.OrderClassesInterface;

import java.util.ArrayList;

public class OrderKitchenTab extends StackPane implements OrderClassesInterface {
    Label orderLabel;
    Label itemsRemain;
    VBox ordersVBox;
    Button cancelButton;
    Order orderItems;
    KitchenModel kModel;
    public OrderKitchenTab(KitchenModel model,Order order){
        this.setPrefSize(230,230);
        kModel = model;
        this.cancelButton = new Button("Cancel Order");
        HBox cancelBox = new HBox(cancelButton);
        cancelBox.setPrefWidth(230);
        cancelBox.setAlignment(Pos.BOTTOM_CENTER);
        cancelBox.setPadding(new Insets(2));

        this.orderItems = order;
        orderLabel = new Label("");
        itemsRemain = new Label("");

        HBox titleBox = new HBox(orderLabel, itemsRemain);
        titleBox.setPrefWidth(230);
        titleBox.setAlignment(Pos.CENTER);
        titleBox.setStyle("-fx-border-color: black;\n");

        this.ordersVBox = new VBox();
        this.ordersVBox.setPrefSize(230,230);
        this.ordersVBox.setSpacing(2);
        this.ordersVBox.setStyle("-fx-border-color: black;\n");

        VBox align = new VBox(titleBox,this.ordersVBox, cancelBox);
        align.setPrefSize(230,230);

        this.getChildren().add(align);
    }
    public Button getCancelButton(){
        return this.cancelButton;
    }
    @Override
    public void modelChanged() {
        ordersVBox.getChildren().clear();

        if (!orderItems.isFinished()){
            orderItems.getOrderList().forEach((order ->{
                OrderItems newDisplay = new OrderItems(order);
                newDisplay.getCompleteButton().setOnAction((event -> {
                    orderItems.completedSingleItem();
                }));
                ordersVBox.getChildren().add(newDisplay);
            }));
        }
        else {
            kModel.deleteOrder(orderItems);
        }


        orderLabel.setText("Order #"+ orderItems.getOrderNum());
        itemsRemain.setText("Remaining Items: "+ orderItems.getTotalItems());
    }
}
