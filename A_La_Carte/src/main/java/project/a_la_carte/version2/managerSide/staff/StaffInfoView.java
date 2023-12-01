package project.a_la_carte.version2.managerSide.staff;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.control.TableView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import project.a_la_carte.version2.ProgramController;
import project.a_la_carte.version2.classesObjects.*;
import project.a_la_carte.version2.interfaces.StaffModelSubscriber;

import java.util.ArrayList;
import java.util.HexFormat;
import java.util.List;

/*This view pretty much mirrors Inventory View*/
public class StaffInfoView extends StackPane implements StaffModelSubscriber {
    TextField tipsText;
    TextField fNameText;
    TextField lNameText;
    TextField idText;
    TextField sinText;
    TextField userText;
    TextField passwordText;
    ComboBox<Staff.position> positionComboBox;
    BarChart<String, Number> barChart;
    VBox barChartVBox;
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


    TableView<StaffData> staffTable;
    TableColumn<StaffData,String> iDCol;
    TableColumn<StaffData,String> fNameCol;
    TableColumn<StaffData,String> lNameCol;
    TableColumn<StaffData,String> positionCol;
    TableColumn<StaffData,Integer> sinCol;
    TableColumn<StaffData,Integer> tipsCol;

    public StaffInfoView(){
        this.setMaxSize(5000,2500);
        this.setPrefSize(1000,500);
        addVBox = new VBox();
        listVBox = new VBox();

        addVBox.setPrefSize(300,500);

        addVBox.setStyle("-fx-border-color: black;\n");
        Label addLabel = new Label("Add Staff");
        addLabel.setFont(new Font(20));
        HBox addBox = new HBox(addLabel);
        addBox.setPrefWidth(300);
        addBox.setAlignment(Pos.TOP_CENTER);
        HBox.setHgrow(addBox,Priority.ALWAYS);

        HBox fNameHBox = new HBox();
        Label fNameLabel = new Label("First Name: ");
        fNameText = new TextField();
        fNameHBox.getChildren().addAll(fNameLabel,fNameText);
        fNameHBox.setPrefWidth(300);
        fNameHBox.setAlignment(Pos.CENTER_RIGHT);
        HBox.setHgrow(fNameHBox,Priority.ALWAYS);

        HBox lNameHBox = new HBox();
        Label lNameLabel = new Label("Last Name: ");
        lNameText = new TextField();
        lNameHBox.getChildren().addAll(lNameLabel,lNameText);
        lNameHBox.setPrefWidth(300);
        lNameHBox.setAlignment(Pos.CENTER_RIGHT);
        HBox.setHgrow(lNameHBox,Priority.ALWAYS);

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
        idHBox.setPrefWidth(300);
        idHBox.setAlignment(Pos.CENTER_RIGHT);
        HBox.setHgrow(idHBox,Priority.ALWAYS);

        HBox tipsHBox = new HBox();
        Label tipsHLabel = new Label("Tips earned: ");
        tipsText = new TextField();
        //listener to keep id to max of 4 digits
        tipsText.textProperty().addListener((observable,oldValue,newValue) -> {
            if(newValue.length() > 4) {
                tipsText.setText(oldValue);
            }
        } );
        tipsHBox.getChildren().addAll(tipsHLabel,tipsText);
        tipsHBox.setPrefWidth(300);
        tipsHBox.setAlignment(Pos.CENTER_RIGHT);
        HBox.setHgrow(tipsHBox,Priority.ALWAYS);

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
        sinHBox.setPrefWidth(300);
        sinHBox.setAlignment(Pos.CENTER_RIGHT);
        HBox.setHgrow(sinHBox,Priority.ALWAYS);

        HBox postionHBox = new HBox();
        Label postionLabel = new Label("Position: ");
        positionComboBox = new ComboBox<>();
        positionComboBox.setPrefWidth(150);
        positionComboBox.getEditor().setId("Position...");

        positionComboBox.getItems().add(Staff.position.Cook);
        positionComboBox.getItems().add(Staff.position.Server);

        postionHBox.getChildren().addAll(postionLabel,positionComboBox);
        postionHBox.setPadding(new Insets(0,0,0,0));
        postionHBox.setAlignment(Pos.CENTER);
        HBox.setHgrow(postionHBox,Priority.ALWAYS);

        submit = new Button("Submit");
        submit.setPrefWidth(100);
        updateButton = new Button("Update");
        updateButton.setPrefWidth(100);
        deleteButton = new Button("Delete");
        deleteButton.setPrefWidth(100);
        mainMenu = new Button("Main Menu");
        mainMenu.setPrefWidth(100);
        clearButton = new Button("Clear");
        clearButton.setPrefWidth(100);
        dashboardButton = new Button("Dashboard");
        dashboardButton.setPrefWidth(100);

        VBox buttonsVBox = new VBox(updateButton,deleteButton,clearButton,submit);
        buttonsVBox.setSpacing(3);
        buttonsVBox.setAlignment(Pos.TOP_CENTER);

        VBox padding = new VBox();
        padding.setPrefWidth(50);
        HBox.setHgrow(padding,Priority.ALWAYS);

        VBox textBox = new VBox(fNameHBox,lNameHBox,idHBox,tipsHBox,sinHBox);
        textBox.setPrefWidth(250);
        textBox.setSpacing(3);
        HBox.setHgrow(textBox,Priority.ALWAYS);

        HBox alignH = new HBox(textBox,padding);
        alignH.setPrefWidth(250);
        addVBox.getChildren().addAll(mainMenu,addBox, alignH,postionHBox,buttonsVBox);
        addVBox.setPadding(new Insets(5,5,5,5));
        addVBox.setSpacing(3);
        HBox.setHgrow(addVBox,Priority.ALWAYS);
        VBox.setVgrow(addVBox,Priority.ALWAYS);

        //done addVBox
        Label userLabel = new Label("Change username: ");
        userText = new TextField();
        userHBox = new HBox(userLabel,userText);
        userHBox.setPrefWidth(300);
        userHBox.setAlignment(Pos.CENTER);

        Label passLabel = new Label("Change password: ");
        passwordText = new TextField();
        passwordHBox = new HBox(passLabel,passwordText);
        passwordHBox.setPrefWidth(300);
        passwordHBox.setAlignment(Pos.CENTER);


        staffTable = new TableView<>();
        HBox.setHgrow(staffTable,Priority.ALWAYS);
        VBox.setVgrow(staffTable,Priority.ALWAYS);

        iDCol = new TableColumn<>("Staff ID");

        lNameCol = new TableColumn<>("Last Name");

        fNameCol = new TableColumn<>("First Name");

        positionCol = new TableColumn<>("Position");

        sinCol = new TableColumn<>("SIN");

        tipsCol = new TableColumn<>("Tips earned");

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
        HBox.setHgrow(listVBox,Priority.ALWAYS);
        VBox.setVgrow(listVBox,Priority.ALWAYS);

        HBox mergeHBox = new HBox();
        mergeHBox.getChildren().addAll(addVBox,listVBox);
        VBox.setVgrow(mergeHBox, Priority.ALWAYS);
        HBox.setHgrow(mergeHBox,Priority.ALWAYS);

        this.getChildren().add(mergeHBox);
    }

    /*
     * This method is used to display the bar chart of the tips earned by each employee
     */
    public void TipTrackingDashboard(ArrayList<Staff> staffList)
    {
        //if the bar chart is disabled, enable it and display the bar chart
        if(barChart.isDisable())
        {
            //create a new series
            XYChart.Series<String, Number> series = new XYChart.Series<>();
            series.setName("Tip Tracking Dashboard");
            //add the data to the series
            for (Staff staff : staffList)
            {
                String employeeId = staff.getStaffID();
                Number tipsEarned = staff.getTips();
                series.getData().add(new XYChart.Data<>(employeeId, tipsEarned));
            }
            //clear the data from the bar chart and add the new series
            barChart.getData().clear();
            barChart.getData().add(series);
            listVBox.getChildren().add(barChartVBox);
            barChart.setDisable(false);
            //change the color of the bars and the legend
            for (XYChart.Series<String, Number> s : barChart.getData()) {
                for (XYChart.Data<String, Number> d : s.getData()) {
                    d.getNode().setStyle("-fx-bar-fill: grey;");
                }
            }
            Platform.runLater(() -> {
                Node legend = barChart.lookup(".chart-legend-item-symbol.default-color0.chart-bar");
                if (legend != null) {
                    legend.setStyle("-fx-background-color: grey;");
                }
            });
        }
        //if the bar chart is enabled, disable it and remove it from the list
        else
        {
            listVBox.getChildren().remove(barChartVBox);
            barChart.setDisable(true);
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
    public void modelChanged(ArrayList<Staff> staffList, Staff loadedStaff) {
        clearFields();
        addVBox.getChildren().remove(userHBox);
        addVBox.getChildren().remove(passwordHBox);
        listVBox.getChildren().clear();//redraw list
        listVBox.getChildren().add(dashboardButton);
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
            if (loadedStaff.getPosition() == Staff.position.Manager) {
                positionComboBox.setEditable(false);
                addVBox.getChildren().addAll(userHBox, passwordHBox);
                userText.setText(loadedStaff.getUsername());
                passwordText.setText(loadedStaff.getPassword());
            }

            // if the bar chart is enabled and the staff list is empty, disable the bar chart
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
    public ComboBox<Staff.position> getPositionComboBox() {
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
