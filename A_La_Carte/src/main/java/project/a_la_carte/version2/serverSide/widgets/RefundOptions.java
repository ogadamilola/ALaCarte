package project.a_la_carte.version2.serverSide.widgets;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import project.a_la_carte.version2.classesObjects.Order;

/**
 * Used to contain refund options
 */
public class RefundOptions extends StackPane {
    float total = 0;
    Button refundButton;
    Button viewItemsButton;
    public RefundOptions(Order order){
        this.setPrefSize(290,100);

        Label orderNumber = new Label("Order #" + order.getOrderNum());

        order.getOrderList().forEach((menuFoodItem -> {
            total += menuFoodItem.getPrice();
        }));
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
}
