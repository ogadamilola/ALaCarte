package project.a_la_carte.version2.managerSide.staff;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.control.TableView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import project.a_la_carte.version2.ProgramController;
import project.a_la_carte.version2.classesObjects.*;
import project.a_la_carte.version2.interfaces.StaffModelSubscriber;

import java.util.ArrayList;
import java.util.List;

/*This view pretty much mirrors Inventory View*/
public class StaffInfoView extends StackPane implements StaffModelSubscriber {
    TextField fNameText;
    TextField lNameText;
    TextField idText;
    TextField sinText;
    TextField userText;
    TextField passwordText;
    ComboBox<Staff.position> positionComboBox;
    VBox listVBox;
    VBox addVBox;
    Button submit;
    Button mainMenu;
    Button clearButton;
    Button updateButton;
    Button deleteButton;

    HBox userHBox;
    HBox passwordHBox;


    javafx.scene.control.TableView<StaffData> staffTable;
    TableColumn<StaffData,String> iDCol;
    TableColumn<StaffData,String> fNameCol;
    TableColumn<StaffData,String> lNameCol;
    TableColumn<StaffData,String> positionCol;
    TableColumn<StaffData,Integer> sinCol;

    public StaffInfoView(){
        this.setMaxSize(1000,500);
        addVBox = new VBox();
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
        //listener to keep id to max of 4 digits
        idText.textProperty().addListener((observable,oldValue,newValue) -> {
            if(newValue.length() > 4) {
                idText.setText(oldValue);
            }
        } );
        idHBox.getChildren().addAll(idLabel,idText);

        HBox sinHBox = new HBox();
        Label sinLabel = new Label("SIN: ");
        sinText = new TextField();
        //listener to keep sin to max of 9 digits
        sinText.textProperty().addListener((observable,oldValue,newValue) -> {
            if(newValue.length() > 9) {
                sinText.setText(oldValue);
            }
        } );
        sinHBox.getChildren().addAll(sinLabel,sinText);

        HBox postionHBox = new HBox();
        Label postionLabel = new Label("Staff Position: ");
        positionComboBox = new ComboBox<>();
        positionComboBox.getEditor().setId("Position...");

        positionComboBox.getItems().add(Staff.position.Cook);
        positionComboBox.getItems().add(Staff.position.Server);

        postionHBox.getChildren().addAll(postionLabel,positionComboBox);

        submit = new Button("Submit");
        updateButton = new Button("Update");
        deleteButton = new Button("Delete Item");
        mainMenu = new Button("Main Menu");
        clearButton = new Button("Clear");

        addVBox.getChildren().addAll(mainMenu,addLabel, fNameHBox,lNameHBox,idHBox,sinHBox,postionHBox,updateButton,deleteButton,clearButton, submit);
        addVBox.setPadding(new Insets(5,5,5,5));

        //done addVBox
        Label userLabel = new Label("Change username: ");
        userText = new TextField();
        userHBox = new HBox(userLabel,userText);

        Label passLabel = new Label("Change password: ");
        passwordText = new TextField();
        passwordHBox = new HBox(passLabel,passwordText);




        staffTable = new javafx.scene.control.TableView<>();

        iDCol = new TableColumn<>("Staff ID");

        lNameCol = new TableColumn<>("Last Name");

        fNameCol = new TableColumn<>("First Name");

        positionCol = new TableColumn<>("Position");

        sinCol = new TableColumn<>("SIN");

        staffTable.getColumns().addAll(iDCol,lNameCol,fNameCol,positionCol,sinCol);
        staffTable.setColumnResizePolicy(javafx.scene.control.TableView.CONSTRAINED_RESIZE_POLICY);
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
        submit.setOnAction(controller::handleNewStaff);
        clearButton.setOnAction(controller::clearStaffInfoFields);
        staffTable.setOnMouseClicked(controller::loadStaff);
        updateButton.setOnAction(controller::updateStaff);
        deleteButton.setOnAction(controller::deleteStaff);

    }

    @Override
    public void modelChanged(ArrayList<Staff> staffList, Staff loadedStaff) {
        clearFields();
        addVBox.getChildren().remove(userHBox);
        addVBox.getChildren().remove(passwordHBox);
        listVBox.getChildren().clear();//redraw list
        listVBox.getChildren().add(staffTable);
        ObservableList<StaffData> data = FXCollections.observableArrayList();
        for(Staff staff : staffList){
            StaffData theData = new StaffData(staff);
            data.add(theData);
        }

        staffTable.setItems(data);
        iDCol.setCellValueFactory(cellData -> cellData.getValue().idProperty());
        fNameCol.setCellValueFactory(cellData -> cellData.getValue().firstNameProperty());
        lNameCol.setCellValueFactory(cellData -> cellData.getValue().lastNameProperty());
        sinCol.setCellValueFactory(cellData -> cellData.getValue().sinProperty().asObject());
        positionCol.setCellValueFactory(cellData -> cellData.getValue().positionProperty());

        if(loadedStaff == null){
            clearFields();
        } else {
            fNameText.setText(loadedStaff.getFirstName());
            lNameText.setText(loadedStaff.getLastName());
            idText.setText(loadedStaff.getStaffID());
            idText.setEditable(false);//don't allow for staff ID to be edited
            positionComboBox.setValue(loadedStaff.getPosition());
            sinText.setText(String.valueOf(loadedStaff.getSin()));
            //clear these incase they were previously filled
            userText.clear();
            passwordText.clear();

            //if the staff is a manager load the log in
            if (loadedStaff.getPosition() == Staff.position.Manager) {
                positionComboBox.setEditable(false);
                addVBox.getChildren().addAll(userHBox, passwordHBox);
                userText.setText(loadedStaff.getUsername());
                passwordText.setText(loadedStaff.getPassword());
            }
        }
    }

    public void clearFields(){
        fNameText.clear();
        lNameText.clear();
        positionComboBox.setValue(null);
        sinText.clear();
        idText.clear();
        idText.setEditable(true);
    }

    public TextField getfNameText() {
        return fNameText;
    }
    public TextField getlNameText() {
        return lNameText;
    }
    public ComboBox<Staff.position> getPositionComboBox() {
        return positionComboBox;
    }
    public TextField getSinText() {
        return sinText;
    }
    public TextField getIdText() {
        return idText;
    }

    public TableView<StaffData> getStaffTable() {
        return staffTable;
    }

    public TextField getPasswordText() {
        return passwordText;
    }

    public TextField getUserText() {
        return userText;
    }
}
