package project.a_la_carte.prototype.server.side;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import project.a_la_carte.prototype.ProgramController;

public class MenuView extends StackPane implements ServerViewInterface{
    ServerModel serverModel;
    FlowPane menuDisplay;
    Button addNote;
    Button customize;
    Button send;
    Button mainMenu;
    public MenuView(){
        this.setPrefSize(1000,500);
        Label menuTitle = new Label("MENU ITEMS");
        menuTitle.setFont(new Font(20));

        this.mainMenu = new Button("Main Menu");
        HBox menuHBox = new HBox(mainMenu);
        menuHBox.setPrefWidth(200);

        HBox titleHBox = new HBox(menuTitle);
        titleHBox.setPrefWidth(600);
        titleHBox.setAlignment(Pos.TOP_CENTER);

        HBox topHBox = new HBox(menuHBox, titleHBox);
        topHBox.setPrefWidth(1000);
        topHBox.setPadding(new Insets(5,5,5,5));
        topHBox.setStyle("-fx-border-color: black;\n");

        this.addNote = new Button("ADD NOTE");
        this.customize = new Button("CUSTOMIZE");
        this.send = new Button("->");

        double r = 2;
        this.send.setShape(new Circle(r));
        this.send.setMinSize(2*r,2*r);
        this.send.setStyle("-fx-border-color: black;-fx-background-color: paleturquoise;\n");

        HBox sendHBox = new HBox(send);
        sendHBox.setPrefWidth(500);
        sendHBox.setAlignment(Pos.BASELINE_RIGHT);

        HBox customizeHBox = new HBox(customize);
        customizeHBox.setPrefWidth(500);

        HBox alignBot = new HBox(customizeHBox,sendHBox);
        alignBot.setPrefWidth(1000);
        alignBot.setPadding(new Insets(5,5,5,5));

        HBox addNoteBox = new HBox(addNote);
        addNoteBox.setPrefWidth(1000);
        addNoteBox.setPadding(new Insets(5));

        VBox buttons = new VBox(addNoteBox,alignBot);
        buttons.setPrefSize(1000,100);
        buttons.setPadding(new Insets(5,5,5,5));
        buttons.setAlignment(Pos.BOTTOM_LEFT);

        menuDisplay = new FlowPane();
        menuDisplay.setPrefSize(1000,400);

        VBox alignAll = new VBox(topHBox,menuDisplay,buttons);
        alignAll.setPrefSize(1000,500);

        this.getChildren().addAll(alignAll);
    }
    public void setServerModel(ServerModel newModel){
        this.serverModel = newModel;
    }
    public void setController(ProgramController controller){
        this.mainMenu.setOnAction(controller::openStartUpMVC);
        this.addNote.setOnAction(controller::openNoteView);
        this.customize.setOnAction(controller::openCustomizeView);
        this.send.setOnAction(controller::openViewOrder);
    }

    @Override
    public void modelChanged() {
        menuDisplay.getChildren().clear();
        if (serverModel.getMenuItemList() != null){
            serverModel.getMenuItemList().forEach((item -> {
                item.getDisplay().setOnAction((event -> {
                    serverModel.setSelectedMenuItem(item);
                    item.selectDisplay();
                }));
                menuDisplay.getChildren().add(item.getDisplay());
            }));
        }
    }
}
