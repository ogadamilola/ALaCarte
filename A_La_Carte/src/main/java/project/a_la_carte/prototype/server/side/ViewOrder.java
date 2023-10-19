package project.a_la_carte.prototype.server.side;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import project.a_la_carte.prototype.ProgramController;

public class ViewOrder extends StackPane {
    ServerModel serverModel;
    VBox ordersVBox;
    Button back;
    Button send;
    public ViewOrder(){
        this.setPrefSize(1000,500);

        Label title = new Label("VIEW ORDER");
        title.setFont(new Font(20));

        double r = 2;
        this.back = new Button("<");
        this.back.setShape(new Circle(r));
        this.back.setMinSize(2*r,2*r);
        this.back.setStyle("-fx-border-color: black;-fx-background-color: paleturquoise;\n");

        HBox backBox = new HBox(back);
        backBox.setPrefWidth(200);

        HBox titleHBox = new HBox(title);
        titleHBox.setPrefWidth(600);
        titleHBox.setAlignment(Pos.TOP_CENTER);

        HBox topHBox = new HBox(backBox, titleHBox);
        topHBox.setPrefWidth(1000);
        topHBox.setStyle("-fx-border-color: black;\n");
        topHBox.setPadding(new Insets(5));

        this.send = new Button("SEND");
        this.send.setStyle("-fx-border-color: black;-fx-background-color: lightskyblue;\n");

        HBox sendHBox = new HBox(send);
        sendHBox.setPrefSize(1000,100);
        sendHBox.setAlignment(Pos.BASELINE_RIGHT);
        sendHBox.setPadding(new Insets(5));

        this.ordersVBox = new VBox();
        this.ordersVBox.setPrefSize(1000,500);
        this.ordersVBox.setPadding(new Insets(10));

        VBox align = new VBox(topHBox,ordersVBox,sendHBox);
        align.setPrefSize(1000,500);

        this.getChildren().add(align);
    }
    public void setServerModel(ServerModel newModel){
        this.serverModel = newModel;
    }
    public void setController(ProgramController controller){
        this.back.setOnAction(controller::openMenuView);
    }
}
