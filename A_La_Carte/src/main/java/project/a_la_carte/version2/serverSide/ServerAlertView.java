package project.a_la_carte.version2.serverSide;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import project.a_la_carte.version2.interfaces.ServerViewInterface;

public class ServerAlertView extends StackPane implements ServerViewInterface {
    ServerModel serverModel;
    FlowPane alertBox;
    Button clear;
    public ServerAlertView(ServerModel model){
        this.setPrefSize(600,400);
        this.serverModel = model;

        Label title = new Label("KitchenAlerts");
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
            serverModel.clearNotes();
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
        if (!serverModel.getNoteList().isEmpty()){
            serverModel.getNoteList().forEach((notes -> {
                alertBox.getChildren().add(notes);
            }));
        }
    }
}
