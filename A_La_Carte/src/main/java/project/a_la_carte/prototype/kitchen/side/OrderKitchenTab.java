package project.a_la_carte.prototype.kitchen.side;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import project.a_la_carte.prototype.server.side.Order;

public class OrderKitchenTab extends StackPane {
    Order order;
    KitchenView kitchenView;
    VBox ordersVBox;
    public OrderKitchenTab(KitchenView kView, Order x){
        this.setPrefSize(300,115);

        this.order = x;
        this.kitchenView = kView;
        Label orderLabel = new Label("Order #" + this.order.getOrderNum());

        HBox titleBox = new HBox(orderLabel);
        titleBox.setPrefWidth(300);
        titleBox.setAlignment(Pos.CENTER);
        titleBox.setStyle("-fx-border-color: black;\n");

        Button done = new Button("Done");
        done.setOnAction(e ->{
            this.kitchenView.getChildren().remove(this);
        });

        this.ordersVBox.setPrefSize(300,115);
        this.ordersVBox.setSpacing(5);
        this.ordersVBox.setStyle("-fx-border-color: black;\n");
        //Filling in the order with recipes
        //this.order.getRecipeList().forEach((order)->{
        //We can make this a button
         //   Label recipeName = new Label(order)
         //   this.ordersVBox.getChildren().add()
        //});

        VBox align = new VBox(titleBox,this.ordersVBox,done);
        align.setPrefSize(300,115);

        this.getChildren().add(align);
    }
    //Over here would be the changing of order time on kitchen side <-- don't need this yet, not in proto todo
    //Also the prep timer
}
