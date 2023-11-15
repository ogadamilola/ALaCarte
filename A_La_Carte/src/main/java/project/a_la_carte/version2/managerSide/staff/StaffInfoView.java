package project.a_la_carte.version2.managerSide.staff;

import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import project.a_la_carte.version2.ProgramController;
import project.a_la_carte.version2.classesObjects.IngredientData;
import project.a_la_carte.version2.classesObjects.Staff;
import project.a_la_carte.version2.classesObjects.StaffData;
import project.a_la_carte.version2.interfaces.StaffModelSubscriber;

import java.util.ArrayList;

/*This view pretty much mirrors Inventory View*/
public class StaffInfoView extends StackPane implements StaffModelSubscriber {
    TextField fNameText;
    TextField lNameText;
    TextField idText;
    TextField sinText;
    ComboBox<Staff.position> positionComboBox;
    VBox listVBox;
    Button submit;
    Button mainMenu;
    Button clearButton;
    Button updateButton;
    Button deleteButton;

    //FIXME change to staff data
    TableView<StaffData> staffTable;
    TableColumn<StaffData,String> iDCol;
    TableColumn<StaffData,String> fNameCol;
    TableColumn<StaffData,String> lNameCol;
    TableColumn<StaffData,String> positionCol;

    TableColumn<StaffData,Integer> sinCol;

    public StaffInfoView(){
        this.setMaxSize(1000,500);
        VBox addVBox = new VBox();
        listVBox = new VBox();

        addVBox.setPrefSize(300,500);

        addVBox.setStyle("-fx-border-color: black;\n");
        Label addLabel = new Label("Add Staff");
        addLabel.setFont(new Font(20));

        HBox fNameHBox = new HBox();
        Label fNameLabel = new Label("First Name: ");
        fNameText = new TextField();
        fNameHBox.getChildren().addAll(fNameLabel,fNameText);

        HBox lNameHBox = new HBox();
        Label lNameLabel = new Label("Last Name: ");
        lNameText = new TextField();
        lNameHBox.getChildren().addAll(lNameLabel,lNameText);

        //Maybe we can make this randomly generated somehow
        //and maybe code a limit for the ID to 4 digits
        HBox idHBox = new HBox();
        Label idLabel = new Label("Staff ID: ");
        idText = new TextField();
        idHBox.getChildren().addAll(idLabel,idText);

        HBox sinHBox = new HBox();
        Label sinLabel = new Label("SIN: ");
        sinText = new TextField();
        sinHBox.getChildren().addAll(sinLabel,sinText);

        HBox postionHBox = new HBox();
        Label postionLabel = new Label("Staff Position: ");
        positionComboBox = new ComboBox<>();
        positionComboBox.getEditor().setId("Position...");
        for(Staff.position p: Staff.position.values()){
            positionComboBox.getItems().add(p);
        }
        postionHBox.getChildren().addAll(postionLabel,positionComboBox);

        submit = new Button("Submit");
        updateButton = new Button("Update");
        deleteButton = new Button("Delete Item");
        mainMenu = new Button("Main Menu");
        clearButton = new Button("Clear");

        addVBox.getChildren().addAll(mainMenu,addLabel, fNameHBox,lNameHBox,idHBox,sinHBox,postionHBox,updateButton,deleteButton,clearButton, submit);
        addVBox.setPadding(new Insets(5,5,5,5));
        //done addVBox

        staffTable = new TableView<>();

        iDCol = new TableColumn<>("Staff ID");

        lNameCol = new TableColumn<>("Last Name");

        fNameCol = new TableColumn<>("First Name");

        positionCol = new TableColumn<>("Position");

        sinCol = new TableColumn<>("SIN");

        staffTable.getColumns().addAll(iDCol,lNameCol,fNameCol,positionCol,sinCol);
        staffTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        staffTable.setPrefSize(700,500);




        listVBox.setPrefSize(700,500);
        listVBox.setStyle("-fx-border-color: black;\n");
        listVBox.getChildren().add(staffTable);
        listVBox.setPadding(new Insets(5,5,5,5));


        HBox mergeHBox = new HBox();
        mergeHBox.getChildren().addAll(addVBox,listVBox);
        this.getChildren().add(mergeHBox);
    }

    public void setController(ProgramController controller){
        mainMenu.setOnAction(controller::openManagerMainView);
    }

    @Override
    public void modelChanged(ArrayList<Staff> staffList) {

    }
}
