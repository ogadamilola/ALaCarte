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
    Button mainMenu;

    //View Order Variables
    VBox ordersVBox;
    Button sendToKitchen;
    Label title;
    public MenuView(){
        this.setPrefSize(1000,500);
        Label menuTitle = new Label("MENU ITEMS");
        menuTitle.setFont(new Font(20));

        this.mainMenu = new Button("Main Menu");
        HBox menuHBox = new HBox(mainMenu);
        menuHBox.setPrefWidth(100);

        HBox titleHBox = new HBox(menuTitle);
        titleHBox.setPrefWidth(400);
        titleHBox.setAlignment(Pos.TOP_CENTER);

        HBox topHBox = new HBox(menuHBox, titleHBox);
        topHBox.setPrefWidth(600);
        topHBox.setPadding(new Insets(5,5,5,5));
        topHBox.setStyle("-fx-border-color: black;\n");

        this.addNote = new Button("ADD NOTE");
        this.customize = new Button("CUSTOMIZE");

        HBox customizeHBox = new HBox(customize);
        customizeHBox.setPrefWidth(600);
        customizeHBox.setPadding(new Insets(5));
        HBox addNoteBox = new HBox(addNote);
        addNoteBox.setPrefWidth(600);
        addNoteBox.setPadding(new Insets(5));

        VBox buttons = new VBox(addNoteBox,customizeHBox);
        buttons.setPrefSize(600,100);
        buttons.setPadding(new Insets(5,5,5,5));
        buttons.setAlignment(Pos.BOTTOM_LEFT);

        menuDisplay = new FlowPane();
        menuDisplay.setPrefSize(600,400);

        VBox alignAllLeft = new VBox(topHBox,menuDisplay,buttons);
        alignAllLeft.setPrefSize(600,500);


        //View Orders
        title = new Label("VIEW ORDER");
        title.setFont(new Font(20));

        HBox titleViewHBox = new HBox(title);
        titleViewHBox.setPrefWidth(400);
        titleViewHBox.setAlignment(Pos.TOP_CENTER);
        titleViewHBox.setStyle("-fx-border-color: black;\n");

        this.sendToKitchen = new Button("SEND ORDER TO KITCHEN");
        this.sendToKitchen.setStyle("-fx-border-color: black;-fx-background-color: lightskyblue;\n");

        HBox sendTKHBox = new HBox(sendToKitchen);
        sendTKHBox.setPrefSize(400,100);
        sendTKHBox.setAlignment(Pos.BASELINE_RIGHT);
        sendTKHBox.setPadding(new Insets(5));

        this.ordersVBox = new VBox();
        this.ordersVBox.setPrefSize(400,500);
        this.ordersVBox.setPadding(new Insets(5,50,5,50));

        VBox alignRight = new VBox(titleViewHBox,ordersVBox,sendTKHBox);
        alignRight.setPrefSize(400,500);
        alignRight.setStyle("-fx-border-color: black;\n");

        HBox alignAll = new HBox(alignAllLeft,alignRight);
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
        this.sendToKitchen.setOnAction(controller::sendToKitchen);
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

        ordersVBox.getChildren().clear();
        if (serverModel.getCurrentOrder() != null){
            title.setText("Order #"+ serverModel.getCurrentOrder().getOrderNum());
            serverModel.getCurrentOrder().getOrderList().forEach((item ->{
                OrderListView newView = new OrderListView(item);
                ordersVBox.getChildren().add(newView);
                newView.getDeleteButton().setOnAction((event -> {
                    serverModel.getCurrentOrder().deleteItem(newView.getMenuItem());
                    ordersVBox.getChildren().remove(newView);
                }));
            }));
        }
    }
}
