package project.a_la_carte.prototype.kitchen.side;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import project.a_la_carte.prototype.ProgramController;

public class KitchenView extends StackPane implements KitchenViewsInterface{
    KitchenModel kitchenModel;
    Button mainMenu;
    FlowPane ordersVBox;
    HBox notesBox;
    public KitchenView(){
        this.setPrefSize(1000,500);

        Label menuTitle = new Label("ORDERS");
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

        this.ordersVBox = new FlowPane();
        this.ordersVBox.setPrefSize(1000,350);
        this.ordersVBox.setPadding(new Insets(10,10,10,10));
        //Bordering it red just to show the area the orders take up
        this.ordersVBox.setStyle("-fx-border-color: red;\n");

        this.notesBox = new HBox();
        this.notesBox.setPrefSize(1000,150);
        this.notesBox.setSpacing(5);
        this.notesBox.setStyle("-fx-border-color: black;\n");

        if (kitchenModel!=null && !kitchenModel.getNoteList().isEmpty()) {
            kitchenModel.getNoteList().forEach((notes) -> {
                this.notesBox.getChildren().add(notes);
            });
        }

        VBox alignOrderNote = new VBox(ordersVBox,notesBox);
        alignOrderNote.setPrefSize(1000,500);

        VBox align = new VBox(topHBox,alignOrderNote);
        align.setPrefSize(1000,500);

        this.getChildren().addAll(align);
    }
    public void setKitchenModel(KitchenModel newModel){
        this.kitchenModel = newModel;
    }
    public void setController(ProgramController controller){
        this.mainMenu.setOnAction(controller::openStartUpMVC);
    }
    public void modelChanged(){
        this.notesBox.getChildren().clear();
        this.ordersVBox.getChildren().clear();

        if (!this.kitchenModel.getNoteList().isEmpty()){
            kitchenModel.getNoteList().forEach((notes) ->{
                this.notesBox.getChildren().add(notes);
            });
        }
        if (kitchenModel.getOrders() != null){
            kitchenModel.getOrders().forEach((order -> {
                OrderKitchenTab newTab = new OrderKitchenTab(kitchenModel,order);
                order.addSubscriber(newTab);
                newTab.getCancelButton().setOnAction((event -> {
                    kitchenModel.deleteOrder(order);
                }));
                this.ordersVBox.getChildren().add(newTab);
            }));
        }
    }
}
