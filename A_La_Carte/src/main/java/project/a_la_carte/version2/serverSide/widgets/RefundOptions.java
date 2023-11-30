package project.a_la_carte.version2.serverSide.widgets;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import project.a_la_carte.version2.classesObjects.MenuFoodItem;
import project.a_la_carte.version2.classesObjects.Order;

public class RefundOptions extends StackPane {
    float total = 0;
    Button refundButton;
    Button viewItemsButton;
    public RefundOptions(Order order){
        this.setPrefSize(290,100);

        Label orderNumber = new Label("Order #" + order.getOrderNum());

        total = order.getTotalPrice();
        Label totalPrice = new Label("$" + total);

        this.refundButton = new Button("Refund");
        this.viewItemsButton = new Button("Items");

        VBox labelsBox = new VBox(orderNumber,totalPrice);
        labelsBox.setPrefSize(140, 150);
        labelsBox.setPadding(new Insets(5));
        labelsBox.setAlignment(Pos.CENTER_LEFT);

        VBox buttonsBox = new VBox(refundButton,viewItemsButton);
        buttonsBox.setPrefSize(140,150);
        buttonsBox.setPadding(new Insets(5));
        buttonsBox.setAlignment(Pos.CENTER_RIGHT);

        HBox align = new HBox(labelsBox,buttonsBox);
        align.setPrefSize(290,150);
        this.getChildren().add(align);
        this.setStyle("-fx-border-color: red;\n");
    }
    public Button getRefundButton(){
        return this.refundButton;
    }

    public Button getViewItemsButton() {
        return viewItemsButton;
    }

    public void showItems(Order order){
        Stage stage = new Stage();
        stage.setTitle("Items in order");
        VBox container = new VBox();
        for(MenuFoodItem item: order.getOrderList()){
            Label label = new Label(item.getName() + ":   $" + item.getPrice());
            label.setFont(new Font(15));
            container.getChildren().add(label);
        }
        container.setAlignment(Pos.CENTER);
        container.setPadding(new Insets(10));
        FlowPane flow = new FlowPane(container);
        Scene scene = new Scene(flow);
        stage.setScene(scene);
        stage.setOnCloseRequest(e -> stage.close());
        stage.show();


    }
}
