package project.a_la_carte.prototype.server.side;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import project.a_la_carte.prototype.ProgramController;

public class NoteView extends StackPane implements ServerViewInterface{
    ServerModel serverModel;
    TextArea noteText;
    //We can use this to send an alert if the Note is successfully saved or not
    Label savedAlert;
    Button save;
    Button discard;
    Button back;
    public NoteView(){
        this.setPrefSize(1000,500);

        Label title = new Label("ADD NOTE");
        title.setFont(new Font(20));

        double r = 2;
        this.back = new Button("<");
        this.back.setShape(new Circle(r));
        this.back.setMinSize(2*r,2*r);
        this.back.setStyle("-fx-border-color: black;-fx-background-color: paleturquoise;\n");

        HBox backHBox = new HBox(back);
        backHBox.setPrefWidth(200);

        HBox titleHBox = new HBox(title);
        titleHBox.setPrefWidth(600);
        titleHBox.setAlignment(Pos.TOP_CENTER);

        HBox topHBox = new HBox(backHBox, titleHBox);
        topHBox.setPrefWidth(1000);
        topHBox.setStyle("-fx-border-color: black;\n");
        topHBox.setPadding(new Insets(5));

        Label textLabel = new Label("Type Here: ");
        textLabel.setFont(new Font(20));

        this.noteText = new TextArea();
        this.noteText.setFont(new Font(20));
        this.noteText.setPrefSize(1000,300);
        this.noteText.setStyle("-fx-background-color: lightgrey;\n");
        this.noteText.setWrapText(true);

        VBox textVBox = new VBox(textLabel, noteText);
        textVBox.setPrefSize(1000,300);
        textVBox.setPadding(new Insets(20));
        textVBox.setStyle("-fx-border-color: black;-fx-background-color: lightgrey;\n");

        this.save = new Button("SAVE");
        this.save.setFont(new Font(18));
        this.save.setStyle("-fx-border-color: black;-fx-background-color: lightpink;\n");

        this.discard = new Button("DISCARD");
        this.discard.setFont(new Font(18));
        this.discard.setStyle("-fx-border-color: black;-fx-background-color: lightpink;\n");
        this.discard.setOnAction(e ->{
            this.noteText.clear();
            this.savedAlert.setText("DELETED!");
        });

        this.savedAlert = new Label("");
        this.savedAlert.setFont(new Font(18));

        VBox bottonsVBox = new VBox(save,discard,savedAlert);
        bottonsVBox.setSpacing(10);
        bottonsVBox.setPrefSize(1000,200);
        bottonsVBox.setAlignment(Pos.CENTER);

        VBox alignBody = new VBox(textVBox,bottonsVBox);
        alignBody.setPadding(new Insets(10));
        alignBody.setAlignment(Pos.CENTER);

        VBox align = new VBox(topHBox, alignBody);
        align.setPrefSize(1000,500);

        this.getChildren().add(align);
    }
    public String getNote(){
        return noteText.getText();
    }
    public void setServerModel(ServerModel newModel){
        this.serverModel = newModel;
    }
    public void setController(ProgramController controller){
        this.back.setOnAction(controller::openMenuView);
        this.save.setOnAction(controller::sendNoteToKitchen);
    }

    @Override
    public void modelChanged() {

    }
}
