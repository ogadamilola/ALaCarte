package project.a_la_carte.version2.kitchen;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import project.a_la_carte.version2.interfaces.*;

public class KitchenAlertView extends StackPane implements KitchenViewsInterface {
    KitchenModel kitchenModel;
    FlowPane alertBox;
    Button clear;
    public KitchenAlertView(KitchenModel model){
        this.setPrefSize(600,400);
        this.kitchenModel = model;

        Label title = new Label("ServerAlerts");
        title.setFont(new Font(20));
        HBox titleBox = new HBox(title);
        titleBox.setPrefWidth(600);
        titleBox.setStyle("-fx-border-color: black;\n");
        titleBox.setAlignment(Pos.CENTER);

        alertBox = new FlowPane();
        alertBox.setPrefSize(600,400);
        alertBox.setPadding(new Insets(5));
        alertBox.setStyle("-fx-border-color: black;\n");

        clear = new Button("Clear");
        clear.setOnAction((event -> {
            kitchenModel.clearNotes();
        }));
        HBox clearBox = new HBox(clear);
        clearBox.setPrefWidth(600);
        clearBox.setAlignment(Pos.CENTER);

        VBox align = new VBox(titleBox,alertBox,clearBox);
        align.setPrefSize(600,400);

        this.getChildren().add(align);
    }
    @Override
    public void modelChanged() {
        alertBox.getChildren().clear();
        if (!kitchenModel.getNoteList().isEmpty()){
            kitchenModel.getNoteList().forEach((alerts -> {
                alertBox.getChildren().add(alerts);
            }));
        }
    }
}
