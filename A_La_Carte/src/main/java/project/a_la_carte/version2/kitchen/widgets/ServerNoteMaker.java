package project.a_la_carte.version2.kitchen.widgets;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import project.a_la_carte.version2.ProgramController;
import project.a_la_carte.version2.kitchen.*;

/**
 * Display for sending notes from Kitchen side to Server side
 */
public class ServerNoteMaker extends StackPane{
    public ServerNoteMaker(ProgramController controller, KitchenModel model){
        this.setPrefSize(600,400);

        Label title = new Label("Send Alert To Server");
        title.setFont(new Font(20));
        HBox titleBox = new HBox(title);
        titleBox.setPrefWidth(600);
        titleBox.setStyle("-fx-border-color: black;\n" + "-fx-background-color: paleturquoise;\n");
        titleBox.setAlignment(Pos.CENTER);

        TextArea noteText = new TextArea();
        noteText.setFont(new Font(20));
        noteText.setPrefSize(500,300);
        noteText.setStyle("-fx-background-color: lightgrey;\n");
        noteText.setWrapText(true);

        HBox noteBox = new HBox(noteText);
        noteBox.setPrefSize(600, 310);
        noteBox.setAlignment(Pos.CENTER);

        Label alertLabel = new Label(" ");
        alertLabel.setFont(new Font(20));

        Button sendButton = new Button("SEND");
        sendButton.setPrefHeight(20);
        sendButton.setPrefWidth(150);
        sendButton.setStyle("-fx-background-color: darkturquoise;\n" + "-fx-border-color: darkcyan;\n"
                + "-fx-border-radius: 15;\n"+"-fx-background-radius: 15;\n");
        sendButton.setOnAction((event -> {
            alertLabel.setText("SENT!");
            sendButton.setStyle("-fx-background-color: paleturquoise;\n" + "-fx-border-color: darkcyan;\n"
                    + "-fx-border-radius: 15;\n"+"-fx-background-radius: 15;\n");
            model.sendServerAlert(noteText.getText());
            noteText.clear();
            controller.sendKitchenAlertToServer(event);
        }));

        Button discardButton = new Button("DISCARD");
        discardButton.setPrefHeight(20);
        discardButton.setPrefWidth(150);
        discardButton.setStyle("-fx-background-color: darkturquoise;\n" + "-fx-border-color: darkcyan;\n"
                + "-fx-border-radius: 15;\n"+"-fx-background-radius: 15;\n");

        sendButton.setOnAction((event -> {
            alertLabel.setText("SENT!");
            sendButton.setStyle("-fx-background-color: paleturquoise;\n" + "-fx-border-color: darkcyan;\n"
                    + "-fx-border-radius: 15;\n"+"-fx-background-radius: 15;\n");
            discardButton.setStyle("-fx-background-color: darkturquoise;\n" + "-fx-border-color: darkcyan;\n"
                    + "-fx-border-radius: 15;\n"+"-fx-background-radius: 15;\n");
            model.sendServerAlert(noteText.getText());
            noteText.clear();
            controller.sendKitchenAlertToServer(event);
        }));
        discardButton.setOnAction((event -> {
            discardButton.setStyle("-fx-background-color: paleturquoise;\n" + "-fx-border-color: darkcyan;\n"
                    + "-fx-border-radius: 15;\n"+"-fx-background-radius: 15;\n");
            sendButton.setStyle("-fx-background-color: darkturquoise;\n" + "-fx-border-color: darkcyan;\n"
                    + "-fx-border-radius: 15;\n"+"-fx-background-radius: 15;\n");
            noteText.clear();
            alertLabel.setText("DISCARDED!");
        }));

        VBox alignBody = new VBox(noteBox, sendButton, discardButton, alertLabel);
        alignBody.setPadding(new Insets(5));
        alignBody.setSpacing(2);
        alignBody.setAlignment(Pos.CENTER);
        alignBody.setStyle("-fx-background-color: linen;\n");

        VBox alignAll = new VBox(titleBox,alignBody);

        this.getChildren().add(alignAll);
    }
}
