package project.a_la_carte.version2;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import project.a_la_carte.version2.serverSide.*;
import project.a_la_carte.version2.kitchen.KitchenView;
import project.a_la_carte.version2.managerSide.inventory.InventoryView;
import project.a_la_carte.version2.managerSide.recipe.RecipeListView;
import project.a_la_carte.version2.managerSide.recipe.RecipeMakerView;
import project.a_la_carte.version2.menuItems.MenuItemListView;
import project.a_la_carte.version2.menuItems.MenuItemMakerView;
import project.a_la_carte.version2.serverSide.NoteView;

public class WorkerView extends StackPane {
    CustomizeView customizeView;
    MenuView menuView;
    NoteView noteView;
    KitchenView kitchenView;
    StackPane workerView;
    Button serverButton;
    Button kitchenButton;
    String selectedScreen = "";
    public WorkerView(StartupMVC startupMVC){
        this.setPrefSize(1000,500);

        Label welcomeLabel = new Label("A La Carte Worker View");
        welcomeLabel.setFont(new Font(40));

        customizeView = new CustomizeView();
        customizeView.setController(startupMVC.getController());
        customizeView.setServerModel(startupMVC.getServerModel());

        startupMVC.getServerModel().setCustomizeView(customizeView);

        menuView = new MenuView();
        menuView.setServerModel(startupMVC.getServerModel());
        menuView.setController(startupMVC.getController());

        startupMVC.getServerModel().setMenuView(menuView);

        noteView = new NoteView();
        noteView.setController(startupMVC.getController());
        noteView.setServerModel(startupMVC.getServerModel());

        startupMVC.getServerModel().setNoteView(noteView);

        this.kitchenView = new KitchenView();
        startupMVC.getKitchenModel().setKitchenView(kitchenView);

        this.kitchenView.setKitchenModel(startupMVC.getKitchenModel());
        kitchenView.setController(startupMVC.getController());

        serverButton = new Button("Server Side");
        serverButton.setFont(new Font(30));
        serverButton.setOnMouseEntered((event -> {
            serverButton.setStyle("-fx-text-fill: blue;-fx-underline: true;\n");
        }));
        serverButton.setOnMouseExited((event -> {
            serverButton.setStyle("-fx-text-fill: black;-fx-underline: false;\n");
        }));
        serverButton.setPrefSize(300,30);

        kitchenButton = new Button("Kitchen Side");
        kitchenButton.setFont(new Font(30));
        kitchenButton.setOnMouseEntered((event -> {
            kitchenButton.setStyle("-fx-text-fill: blue;-fx-underline: true;\n");
        }));
        kitchenButton.setOnMouseExited((event -> {
            kitchenButton.setStyle("-fx-text-fill: black;-fx-underline: false;\n");
        }));
        kitchenButton.setPrefSize(300,30);

        workerView = new StackPane();
        workerView.setMaxSize(1000,500);
        VBox workerViewAlign = new VBox(welcomeLabel,serverButton,kitchenButton);
        workerViewAlign.setPrefSize(1000,500);
        workerViewAlign.setPadding(new Insets(20,20,20,20));
        workerViewAlign.setAlignment(Pos.CENTER);
        workerView.getChildren().add(workerViewAlign);

        startupMVC.getController().setWorkerView(this);
        this.setStyle("-fx-border-color: black;\n");
        this.setController(startupMVC.getController());
        this.getChildren().add(workerView);
    }
    public void setController(ProgramController controller){
        serverButton.setOnAction(controller::openMenuView);
        kitchenButton.setOnAction(controller::openKitchenView);
    }
    public void selectWorkerView(){
        this.selectedScreen = "workerView";
    }
    public void selectKitchenView(){
        this.selectedScreen = "kitchen";
    }
    public void selectMenuView(){
        this.selectedScreen = "menu";
    }
    public void selectCustomize(){this.selectedScreen ="customize";}
    public void selectNoteView(){this.selectedScreen = "note";}
    public void modelChanged() {
        this.getChildren().clear();
        switch (this.selectedScreen) {
            case "workerView" -> this.getChildren().add(workerView);
            case "kitchen" -> this.getChildren().add(kitchenView);
            case "menu" -> this.getChildren().add(menuView);
            case "customize" -> this.getChildren().add(customizeView);
            case "note" -> this.getChildren().add(noteView);
        }
    }
}
