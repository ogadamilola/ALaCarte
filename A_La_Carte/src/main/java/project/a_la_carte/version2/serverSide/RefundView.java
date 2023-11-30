package project.a_la_carte.version2.serverSide;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import project.a_la_carte.version2.interfaces.KitchenViewsInterface;
import project.a_la_carte.version2.kitchen.KitchenModel;
import project.a_la_carte.version2.managerSide.RestaurantInfo.RestaurantModel;
import project.a_la_carte.version2.serverSide.widgets.RefundOptions;

import java.util.ArrayList;

/**
 * Display for refunding orders
 */
public class RefundView extends StackPane implements KitchenViewsInterface {
    KitchenModel kitchenModel;
    RestaurantModel restaurantModel;
    FlowPane flowPane;
    ArrayList<RefundOptions> refundOptions;
    public RefundView(KitchenModel model, RestaurantModel restaurantModel){
        this.setPrefSize(600,400);

        Label title = new Label("REFUND");
        title.setFont(new Font(20));
        HBox titleBox = new HBox(title);
        titleBox.setPrefWidth(600);
        titleBox.setStyle("-fx-border-color: black;\n");
        titleBox.setAlignment(Pos.CENTER);

        kitchenModel = model;
        this.restaurantModel =restaurantModel;
        refundOptions = new ArrayList<>();

        this.flowPane = new FlowPane();
        flowPane.setPrefSize(584,390);
        flowPane.setHgap(3);
        flowPane.setVgap(3);

        ScrollPane scrollPane = new ScrollPane(flowPane);
        scrollPane.setPrefSize(600,400);

        VBox align = new VBox(titleBox,scrollPane);
        align.setPrefSize(600,400);

        this.getChildren().add(align);
    }
    @Override
    public void modelChanged() {
        this.flowPane.getChildren().clear();
        if (!kitchenModel.getTotalOrders().isEmpty()){
            kitchenModel.getTotalOrders().forEach((order -> {
                RefundOptions refund = new RefundOptions(order);
                this.refundOptions.add(refund);
                this.flowPane.getChildren().add(refund);
                refund.getRefundButton().setOnAction((event -> {
                    this.refundOptions.remove(refund);
                    this.kitchenModel.refundOrder(order);
                    this.restaurantModel.handleOrderRefund(order);
                }));
            }));
        }

    }
}
