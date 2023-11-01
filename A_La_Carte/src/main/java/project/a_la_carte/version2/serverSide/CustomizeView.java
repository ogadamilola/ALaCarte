package project.a_la_carte.version2.serverSide;

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
import project.a_la_carte.version2.ProgramController;
import project.a_la_carte.version2.interfaces.ServerViewInterface;

public class CustomizeView extends StackPane implements ServerViewInterface {
    ServerModel serverModel;
    FlowPane ingredients;
    VBox optionsVBox;
    Label title;
    Button no;
    Button extra;
    Button just;
    Button discard;
    Button send;
    Button back;
    public CustomizeView(){
        this.setPrefSize(1000,500);

        title = new Label("SELECT ITEM TO CUSTOMIZE");
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

        this.no = new Button("NO");
        this.no.setStyle("-fx-border-color: black;-fx-background-color: lightpink;\n");
        this.no.setPrefSize(200,70);

        this.extra = new Button("EXTRA");
        this.extra.setStyle("-fx-border-color: black;-fx-background-color: lightpink;\n");
        this.extra.setPrefSize(200,70);

        this.just = new Button("JUST");
        this.just.setStyle("-fx-border-color: black;-fx-background-color: lightpink;\n");
        this.just.setPrefSize(200,70);

        optionsVBox = new VBox(no,extra,just);
        optionsVBox.setPrefSize(200,500);
        optionsVBox.setPadding(new Insets(5));
        optionsVBox.setSpacing(10);
        optionsVBox.setStyle("-fx-border-color: black;\n");

        this.send = new Button("SEND");
        this.send.setPrefHeight(150);
        this.send.setFont(new Font(15));
        this.discard = new Button("DISCARD");
        this.discard.setPrefHeight(150);
        this.discard.setFont(new Font(15));

        HBox botButtons = new HBox(discard,send);
        botButtons.setPrefSize(800,150);
        botButtons.setPadding(new Insets(5));
        botButtons.setAlignment(Pos.BOTTOM_CENTER);
        botButtons.setSpacing(15);

        this.ingredients = new FlowPane();
        this.ingredients.setPrefSize(800,500);
        this.ingredients.setPadding(new Insets(5));

        HBox ingredientsAlign = new HBox(ingredients);
        ingredientsAlign.setPrefSize(800,500);
        ingredientsAlign.setStyle("-fx-border-color: black;\n");

        VBox alignRight = new VBox(ingredientsAlign,botButtons);
        alignRight.setPrefSize(800,500);

        HBox alignBody = new HBox(optionsVBox, alignRight);

        VBox alignAll = new VBox(topHBox,alignBody);

        this.getChildren().add(alignAll);
    }
    public void setServerModel(ServerModel newModel){
        this.serverModel = newModel;
    }
    public void setController(ProgramController controller){
        this.back.setOnAction(controller::openMenuView);
        this.discard.setOnAction(controller::discardSelection);
        this.send.setOnAction(controller::saveCustomize);
    }

    @Override
    public void modelChanged() {
        ingredients.getChildren().clear();
        optionsVBox.getChildren().clear();

        serverModel.getCustomizeButtons().forEach((button ->{
            button.setOnAction((event -> {
                serverModel.selectOption(button.getOptionName());
            }));
            optionsVBox.getChildren().add(button);
        }));
        if (serverModel.getSelectedItem() != null){
            title.setText("Selected Menu Item: "+ serverModel.getSelectedItem().getName());
            serverModel.getIngredientList().forEach((recipe ->{
                recipe.setOnAction((event -> {
                    serverModel.selectIngredient(recipe.getIngredientName());
                }));
                ingredients.getChildren().add(recipe);
            }));
        }
    }
}
