package project.a_la_carte.prototype.kitchen.side;

import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class KitchenNotes extends StackPane {
    Label note;
    Button deleteButton;
    public KitchenNotes(String message, KitchenModel model){
        this.setPrefSize(150,100);

        note = new Label(message);
        note.setWrapText(true);

        deleteButton = new Button("Clear");
        deleteButton.setPrefWidth(150);
        deleteButton.setOnAction(e ->{
            model.deleteNote(this);
        });

        VBox noteBox = new VBox(note);
        noteBox.setPrefSize(150,100);
        noteBox.setAlignment(Pos.CENTER);

        VBox align = new VBox(noteBox,deleteButton);
        align.setPrefSize(150,100);
        align.setPadding(new Insets(5));

        this.setStyle("-fx-border-color: black;\n");
        this.getChildren().add(align);
    }
}
