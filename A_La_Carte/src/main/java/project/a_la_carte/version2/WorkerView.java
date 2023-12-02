package project.a_la_carte.version2;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import project.a_la_carte.version2.serverSide.*;
import project.a_la_carte.version2.kitchen.KitchenView;
import project.a_la_carte.version2.serverSide.NoteView;

/**
 * The view that opens up for Workers
 */
public class WorkerView extends StackPane {
    CustomizeView customizeView;
    MenuView menuView;
    NoteView noteView;
    TableView tableView;
    KitchenView kitchenView;
    StackPane workerView;
    Button kitchenButton;
    TextField pinText;
    Button logInButton;
    String selectedScreen = "";
    public WorkerView(StartupMVC startupMVC){
        this.setPrefSize(1000,500);

        Label welcomeLabel = new Label("A La Carte Worker View");
        welcomeLabel.setFont(new Font(40));

        customizeView = new CustomizeView(this);
        customizeView.setController(startupMVC.getController());
        customizeView.setServerModel(startupMVC.getServerModel());
        customizeView.setInventoryModel(startupMVC.getInventoryModel());

        startupMVC.getServerModel().setCustomizeView(customizeView);

        menuView = new MenuView(this);
        menuView.setServerModel(startupMVC.getServerModel());
        menuView.setController(startupMVC.getController());

        startupMVC.getServerModel().setMenuView(menuView);

        noteView = new NoteView(this);
        noteView.setController(startupMVC.getController());
        noteView.setServerModel(startupMVC.getServerModel());

        tableView = new TableView(this);
        tableView.setController(startupMVC.getController());
        tableView.setServerModel(startupMVC.getServerModel());

        startupMVC.getServerModel().setTableView(tableView);


        this.kitchenView = new KitchenView(this);
        startupMVC.getKitchenModel().setKitchenView(kitchenView);

        this.kitchenView.setKitchenModel(startupMVC.getKitchenModel());
        kitchenView.setController(startupMVC.getController());

        Label pinLabel = new Label("Enter PIN: ");
        pinText = new TextField();
        pinText.textProperty().addListener((observable,oldValue,newValue) -> {
            if(newValue.length() > 4) {
                pinText.setText(oldValue);
            }
        } );

        HBox pinHBox = new HBox(pinLabel,pinText);
        pinHBox.setPrefWidth(1000);
        pinHBox.setAlignment(Pos.CENTER);
        logInButton = new Button("Log In");
        logInButton.setOnMouseEntered((event -> {
            logInButton.setStyle("-fx-text-fill: blue;-fx-underline: true;\n");
        }));
        logInButton.setOnMouseExited((event -> {
            logInButton.setStyle("-fx-text-fill: black;-fx-underline: false;\n");
        }));
        logInButton.setPrefSize(250,30);

        VBox loginVBox = new VBox(pinHBox,logInButton);
        loginVBox.setPrefWidth(1000);
        loginVBox.setAlignment(Pos.CENTER);

        VBox serverLogInVBox = new VBox(loginVBox);
        serverLogInVBox.setPrefWidth(1000);
        serverLogInVBox.setAlignment(Pos.CENTER);

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
        serverLogInVBox.setPadding(new Insets(20,5,20,5));

        VBox workerViewAlign = new VBox(welcomeLabel,serverLogInVBox,kitchenButton);
        workerViewAlign.setPrefSize(1000,500);
        workerViewAlign.setPadding(new Insets(20,20,20,20));
        workerViewAlign.setAlignment(Pos.CENTER);
        workerView.getChildren().add(workerViewAlign);

        this.setStyle("-fx-border-color: black;\n");
        this.setController(startupMVC.getController());
        this.getChildren().add(workerView);
    }

    /**
     * Connecting the WorkerView with the Program's controller
     * @param controller: ProgramController to access methods
     */
    public void setController(ProgramController controller){
        kitchenButton.setOnAction(event -> {
            controller.openKitchenView(this);
        });
        logInButton.setOnAction(event -> {
            controller.handleServerLogIn(this);
        });

    }

    /**
     * Get method for WorkerView's NoteView
     * @return NoteView
     */
    public NoteView getNoteView(){
        return this.noteView;
    }
    public TextField getPinText() {
        return pinText;
    }

    /**
     * Get method for WorkerView's MenuView
     * @return MenuView
     */
    public MenuView getMenuView(){return this.menuView;}

    /**
     * Get method for WorkerView's CustomizeView
     * @return CustomizeView
     */
    public CustomizeView getCustomizeView(){return this.customizeView;}

    public Button getLogInButton() {
        return logInButton;
    }

    /**
     * These methods are used for setting the current display of this WorkerView
     */
    public void selectWorkerView(){this.selectedScreen = "workerView";}
    public void selectKitchenView(){this.selectedScreen = "kitchen";}
    public void selectMenuView(){this.selectedScreen = "menu";}
    public void selectTableView(){this.selectedScreen = "tables";}
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
            case "tables" -> this.getChildren().add(tableView);
        }
    }
}
