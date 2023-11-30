package project.a_la_carte.version2.managerSide.staff;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.TableView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import project.a_la_carte.version2.ProgramController;
import project.a_la_carte.version2.classesObjects.*;
import project.a_la_carte.version2.interfaces.StaffModelSubscriber;

import java.util.ArrayList;

/*This view pretty much mirrors Inventory View*/
public class StaffInfoView extends StackPane implements StaffModelSubscriber {
    TextField fNameText;
    TextField tipsText;
    TextField lNameText;
    TextField idText;
    TextField sinText;
    TextField userText;
    TextField passwordText;
    ComboBox<project.a_la_carte.version2.classesObjects.Staff.position> positionComboBox;
    VBox listVBox;
    VBox addVBox;
    Button submit;
    Button mainMenu;
    Button clearButton;
    Button updateButton;
    Button deleteButton;
    Button dashboardButton;
    HBox userHBox;
    HBox passwordHBox;
    BarChart<String, Number> barChart;
    VBox barChartVBox;

    javafx.scene.control.TableView<project.a_la_carte.version2.classesObjects.StaffData> staffTable;
    TableColumn<project.a_la_carte.version2.classesObjects.StaffData,String> iDCol;
    TableColumn<project.a_la_carte.version2.classesObjects.StaffData,String> fNameCol;
    TableColumn<project.a_la_carte.version2.classesObjects.StaffData,String> lNameCol;
    TableColumn<project.a_la_carte.version2.classesObjects.StaffData,String> positionCol;
    TableColumn<project.a_la_carte.version2.classesObjects.StaffData,Integer> tipsCol;
    TableColumn<project.a_la_carte.version2.classesObjects.StaffData,Integer> sinCol;

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

        HBox tipsHBox = new HBox();
        Label tipsHLabel = new Label("Tips earned: ");
        tipsText = new TextField();
        //listener to keep sin to max of 9 digits
        tipsText.textProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue.length() > 9) {
                tipsText.setText(oldValue);
            }
        } );
        tipsHBox.getChildren().addAll(tipsHLabel, tipsText);

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

        positionComboBox.getItems().add(project.a_la_carte.version2.classesObjects.Staff.position.Cook);
        positionComboBox.getItems().add(project.a_la_carte.version2.classesObjects.Staff.position.Server);

        postionHBox.getChildren().addAll(postionLabel,positionComboBox);

        submit = new Button("Submit");
        updateButton = new Button("Update");
        deleteButton = new Button("Delete");
        mainMenu = new Button("Main Menu");
        clearButton = new Button("Clear");
        dashboardButton = new Button("Dashboard");

        addVBox.getChildren().addAll(mainMenu,addLabel, fNameHBox,lNameHBox,idHBox,sinHBox,tipsHBox,postionHBox,updateButton,deleteButton,clearButton, submit);
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

        tipsCol = new TableColumn<>("Tips received");

        sinCol = new TableColumn<>("SIN");

        staffTable.getColumns().addAll(iDCol,lNameCol,fNameCol,positionCol,tipsCol,sinCol);
        staffTable.setColumnResizePolicy(javafx.scene.control.TableView.CONSTRAINED_RESIZE_POLICY);
        staffTable.setPrefSize(700,500);

        CategoryAxis xAxis = new CategoryAxis();
        xAxis.setLabel("Employee ID");

        NumberAxis yAxis = new NumberAxis();
        yAxis.setLabel("Tips received");

        barChart = new BarChart<>(xAxis, yAxis);
        barChart.setTitle("Tips earned by Employees");

        Button closeButton = new Button("Close");

        barChartVBox = new VBox(barChart, closeButton);
        barChartVBox.setAlignment(Pos.TOP_RIGHT);

        closeButton.setOnAction(e -> {
            listVBox.getChildren().remove(barChartVBox);
            barChart.setDisable(true);
        });

        listVBox.setPrefSize(700,500);
        listVBox.setStyle("-fx-border-color: black;\n");
        listVBox.getChildren().add(dashboardButton);
        listVBox.getChildren().add(staffTable);
        listVBox.setPadding(new Insets(5,5,5,5));
        barChart.setDisable(true);

        HBox mergeHBox = new HBox();
        mergeHBox.getChildren().addAll(addVBox,listVBox);
        this.getChildren().add(mergeHBox);
    }

    public void TipTrackingDashboard(ArrayList<Staff> staffList)
    {

        if(barChart.isDisable())
        {
            XYChart.Series<String, Number> series = new XYChart.Series<>();
            //series.setName("Position Distribution");
            for (Staff staff : staffList) {
                String employeeId = staff.getStaffID();
                Number tipsEarned = staff.getTips();
                series.getData().add(new XYChart.Data<>(employeeId, tipsEarned));
            }

            barChart.getData().clear();
            barChart.getData().add(series);
            listVBox.getChildren().add(barChartVBox);

            barChart.setDisable(false);
        }

    }

    public void setController(ProgramController controller){
        mainMenu.setOnAction(controller::openManagerMainView);
        submit.setOnAction(controller::handleNewStaff);
        clearButton.setOnAction(controller::clearStaffInfoFields);
        staffTable.setOnMouseClicked(controller::loadStaff);
        updateButton.setOnAction(controller::updateStaff);
        deleteButton.setOnAction(controller::deleteStaff);
        dashboardButton.setOnAction(controller::openDashboardView);
    }

    @Override
    public void modelChanged(ArrayList<project.a_la_carte.version2.classesObjects.Staff> staffList, project.a_la_carte.version2.classesObjects.Staff loadedStaff) {
        clearFields();
        addVBox.getChildren().remove(userHBox);
        addVBox.getChildren().remove(passwordHBox);
        listVBox.getChildren().clear();//redraw list
        listVBox.getChildren().add(dashboardButton);
        listVBox.getChildren().add(staffTable);
        ObservableList<project.a_la_carte.version2.classesObjects.StaffData> data = FXCollections.observableArrayList();
        for(project.a_la_carte.version2.classesObjects.Staff staff : staffList){
            project.a_la_carte.version2.classesObjects.StaffData theData = new project.a_la_carte.version2.classesObjects.StaffData(staff);
            data.add(theData);
        }

        staffTable.setItems(data);
        iDCol.setCellValueFactory(cellData -> cellData.getValue().idProperty());
        fNameCol.setCellValueFactory(cellData -> cellData.getValue().firstNameProperty());
        lNameCol.setCellValueFactory(cellData -> cellData.getValue().lastNameProperty());
        sinCol.setCellValueFactory(cellData -> cellData.getValue().sinProperty().asObject());
        positionCol.setCellValueFactory(cellData -> cellData.getValue().positionProperty());
        tipsCol.setCellValueFactory(cellData -> cellData.getValue().tipsProperty().asObject());

        if(loadedStaff == null){
            clearFields();
        } else {
            fNameText.setText(loadedStaff.getFirstName());
            lNameText.setText(loadedStaff.getLastName());
            idText.setText(loadedStaff.getStaffID());
            idText.setEditable(false);//don't allow for staff ID to be edited
            positionComboBox.setValue(loadedStaff.getPosition());
            tipsText.setText(String.valueOf(loadedStaff.getTips()));
            sinText.setText(String.valueOf(loadedStaff.getSin()));
            //clear these incase they were previously filled
            userText.clear();
            passwordText.clear();

            //if the staff is a manager load the log in
            if (loadedStaff.getPosition() == project.a_la_carte.version2.classesObjects.Staff.position.Manager) {
                positionComboBox.setEditable(false);
                addVBox.getChildren().addAll(userHBox, passwordHBox);
                userText.setText(loadedStaff.getUsername());
                passwordText.setText(loadedStaff.getPassword());
            }

            if(!barChart.isDisable() && staffList.size() != 0)
            {
                barChart.setDisable(true);
                TipTrackingDashboard(staffList);
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
    public ComboBox<project.a_la_carte.version2.classesObjects.Staff.position> getPositionComboBox() {
        return positionComboBox;
    }
    public TextField getSinText() {
        return sinText;
    }
    public TextField getTipsText() {
        return tipsText;
    }
    public TextField getIdText() {
        return idText;
    }

    public TableView<project.a_la_carte.version2.classesObjects.StaffData> getStaffTable() {
        return staffTable;
    }

    public TextField getPasswordText() {
        return passwordText;
    }

    public TextField getUserText() {
        return userText;
    }
}
