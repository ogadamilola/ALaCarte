package project.a_la_carte.version2.serverSide;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;


import javafx.util.StringConverter;
import project.a_la_carte.version2.ProgramController;
import project.a_la_carte.version2.WorkerView;
import project.a_la_carte.version2.classesObjects.MenuFoodItem;
import project.a_la_carte.version2.classesObjects.Order;
import project.a_la_carte.version2.interfaces.ServerViewInterface;
import project.a_la_carte.version2.serverSide.tableSystem.Bill;
import project.a_la_carte.version2.serverSide.tableSystem.Reservation;
import project.a_la_carte.version2.serverSide.tableSystem.ReservationAdapter;
import project.a_la_carte.version2.serverSide.tableSystem.Table;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Optional;


public class TableView extends StackPane implements ServerViewInterface {

    private ArrayList<Table> tables;
    private GridPane grid;

    Button back;

    private Button addButton;
    private Button removeButton;

    WorkerView workerView;
    ServerModel serverModel;

    private ArrayList<Reservation> reservations;
    private ListView<VBox> reservationListView;

    private static final String RESERVATION_FILE = "reservation.json";
    private static final String TABLE_FILE = "tables.json";

    private Button saveButton;
    private TextArea orderArea;
    private String savedOrderText;

    private TextArea noteArea;
    private String savedNoteText;

    private TextField nameField, numberOfGuestsField;
    private DatePicker datePicker;
    private TextField timeField;
    private Button addReservationButton;

    /* hey lol */

    public TableView(WorkerView view) {

        //saving reservations
        try(FileReader reader = new FileReader(RESERVATION_FILE)){
            Gson gson = new GsonBuilder()
                    .registerTypeAdapter(Reservation.class, new ReservationAdapter())
                    .create();

            Type reservationListType = new TypeToken<ArrayList<Reservation>>(){}.getType();
            reservations = gson.fromJson(reader,reservationListType);
        } catch (IOException e) {
            reservations = new ArrayList<>();
        }

        tables = new ArrayList<>();


        reservationListView= new ListView<>();

        workerView = view;
        this.setPrefSize(1000,500);

        Label title = new Label("TABLES");
        title.setFont(new Font(20));


        double r = 2;
        this.back = new Button("<");
        this.back.setShape(new Circle(r));
        this.back.setMinSize(2*r,2*r);
        this.back.setStyle("-fx-border-color: goldenrod;-fx-background-color: moccasin;\n");

        HBox backHBox = new HBox(back);
        backHBox.setPrefWidth(200);

        HBox titleHBox = new HBox(title);
        titleHBox.setPrefWidth(600);
        titleHBox.setAlignment(Pos.TOP_CENTER);

        HBox topHBox = new HBox(backHBox, titleHBox);
        topHBox.setPrefWidth(1000);
        topHBox.setStyle("-fx-border-color: black;\n"+"-fx-background-color: khaki;\n");
        topHBox.setPadding(new Insets(5));

        grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        this.getChildren().add(grid);

        addButton = new Button("Add Table");
        addButton.setOnAction(e -> addTable(serverModel.sendOrderToTable(this.workerView)));

        removeButton = new Button("Remove Table");
        removeButton.setOnAction(e -> removeLastTable());

        HBox controlButtons = new HBox(addButton, removeButton);
        controlButtons.setAlignment(Pos.CENTER);
        controlButtons.setSpacing(10);
        VBox container = new VBox(topHBox, grid, controlButtons);
        container.setSpacing(10);

        this.getChildren().add(container);

        setupReservationForm();
        setupReservationList();


        VBox tableManagementContainer = new VBox(topHBox, grid, controlButtons);
        tableManagementContainer.setSpacing(10);

        // Create a container for the reservation management UI
        VBox reservationManagementContainer = new VBox();
        reservationManagementContainer.getChildren().addAll(nameField, datePicker
                ,timeField, numberOfGuestsField, addReservationButton, reservationListView);
        reservationManagementContainer.setSpacing(10);
        reservationManagementContainer.setPadding(new Insets(10));

        // Create a SplitPane and add both sections
        SplitPane splitPane = new SplitPane();
        splitPane.getItems().addAll(tableManagementContainer, reservationManagementContainer);
        splitPane.setDividerPositions(0.5); // Adjust divider position as needed
        splitPane.setStyle("-fx-background-color: linen;\n");

        // Add the SplitPane to the main layout
        this.getChildren().add(splitPane);

    updateView();
    saveReservationList();
    }

    public void saveReservationList(){
        try(FileWriter writer = new FileWriter(RESERVATION_FILE)){
            Gson gson = new GsonBuilder()
                    .registerTypeAdapter(Reservation.class, new ReservationAdapter())
                    .setPrettyPrinting()
                    .create();
            gson.toJson(reservations,writer);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    public void addTable(Table table) {
        tables.add(table);
        updateView();
    }

    public void removeTable(Table table) {
        tables.remove(table);
        updateView();
    }


    private void updateView() {
        grid.getChildren().clear(); // Clear existing views
        int numColumns = 3;
        for (int i = 0; i < tables.size(); i++) {
            Table table = tables.get(i);
            Button tableButton = new Button("Table " + table.getNumber());
            tableButton.setStyle(table.getStatus() ? "-fx-background-color: red;" : "-fx-background-color: green;");
            tableButton.setOnAction(e -> showTableDetails(table));

            int column = i % numColumns;
            int row = i / numColumns;

            grid.add(tableButton, column, row);
        }
    }


    private void setupReservationForm() {
        nameField = new TextField();
        nameField.setPromptText("Name");
        numberOfGuestsField = new TextField();
        numberOfGuestsField.setPromptText("Number of Guests");
        datePicker = new DatePicker();
        timeField = new TextField();
        timeField.setPromptText("Time (HH:mm)");

        addReservationButton = new Button("Add Reservation");
        addReservationButton.setOnAction(e -> addReservation());

        HBox formLayout = new HBox(10, nameField, datePicker, timeField, numberOfGuestsField, addReservationButton);
        formLayout.setPadding(new Insets(10));

        this.getChildren().add(formLayout);
    }

    private void setupReservationList() {
        updateReservationListView();
        this.getChildren().add(reservationListView);
    }


    private void addReservation() {
        try {
            String name = nameField.getText();
            LocalDate date = datePicker.getValue();
            String timeInput = timeField.getText();
            int numberOfGuests = Integer.parseInt(numberOfGuestsField.getText());

            if (name.isEmpty() || date == null || timeInput.isEmpty()) {
                showAlert("All fields must be filled in.");
                return;
            }

            LocalTime time = parseTime(timeInput);
            if (time == null) {
                showAlert("Time must be in HH:mm format.");
                return;
            }

            Reservation newReservation = new Reservation();
            newReservation.setName(name);
            newReservation.setTime(LocalDateTime.of(date, time));
            newReservation.setNumberOfGuests(numberOfGuests);
            reservations.add(newReservation);
            updateReservationListView();

            saveReservationList();
            // Clear form fields
            clearReservationForm();
        } catch (NumberFormatException e) {
            showAlert("Number of guests must be a valid number.");
        }
    }

    private LocalTime parseTime(String timeInput) {
        try {
            return LocalTime.parse(timeInput, DateTimeFormatter.ofPattern("HH:mm"));
        } catch (DateTimeParseException e) {
            return null;
        }
    }

    private void clearReservationForm() {
        nameField.clear();
        datePicker.setValue(null);
        timeField.clear();
        numberOfGuestsField.clear();
    }

    private void updateReservationListView() {
        reservationListView.getItems().clear();
        for (Reservation reservation : reservations) {
            VBox reservationItem = new VBox();
            Label reservationLabel = new Label(reservation.toString());
            Button editButton = new Button("Edit");
            Button deleteButton = new Button("Delete");

            editButton.setOnAction(e -> editReservation(reservation));
            deleteButton.setOnAction(e -> deleteReservation(reservation));

            HBox buttons = new HBox(5, editButton, deleteButton);
            reservationItem.getChildren().addAll(reservationLabel, buttons);
            reservationListView.getItems().add(reservationItem);
        }
    }

    private void deleteReservation(Reservation reservation) {
        reservations.remove(reservation);
        updateReservationListView();
        saveReservationList();
    }

    private void editReservation(Reservation reservation) {
        Dialog<Reservation> dialog = new Dialog<>();
        dialog.setTitle("Edit Reservation");

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20));

        TextField editNameField = new TextField(reservation.getName());
        DatePicker editDatePicker = new DatePicker(reservation.getTime().toLocalDate());
        TextField editTimeField = new TextField(reservation.getTime().format(DateTimeFormatter.ofPattern("HH:mm")));
        TextField editNumberOfGuestsField = new TextField(String.valueOf(reservation.getNumberOfGuests()));

        grid.add(new Label("Name:"), 0, 0);
        grid.add(editNameField, 1, 0);
        grid.add(new Label("Date:"), 0, 1);
        grid.add(editDatePicker, 1, 1);
        grid.add(new Label("Time (HH:mm):"), 0, 2);
        grid.add(editTimeField, 1, 2);
        grid.add(new Label("Number of Guests:"), 0, 3);
        grid.add(editNumberOfGuestsField, 1, 3);

        dialog.getDialogPane().setContent(grid);

        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        // Process the result when OK is clicked
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == ButtonType.OK) {
                try {
                    LocalTime time = LocalTime.parse(editTimeField.getText(), DateTimeFormatter.ofPattern("HH:mm"));
                    Reservation newReservation = new Reservation();
                    newReservation.setName(editNameField.getText());
                    newReservation.setTime(LocalDateTime.of(editDatePicker.getValue(), time));
                    newReservation.setNumberOfGuests(Integer.parseInt(editNumberOfGuestsField.getText()));
                    saveReservationList();
                    return newReservation;
                } catch (DateTimeParseException | NumberFormatException e) {
                    showAlert("Invalid input. Please check the format of time and number of guests.");
                    return null;
                }
            }
            return null;
        });

        Optional<Reservation> result = dialog.showAndWait();
        result.ifPresent(newReservation -> {
            reservations.set(reservations.indexOf(reservation), newReservation);
            updateReservationListView();
            saveReservationList();
        });
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void removeLastTable() {
        if (!tables.isEmpty()) {
            tables.remove(tables.size() - 1);
            updateView();
        }
    }

    public void setServerModel(ServerModel newModel){
        this.serverModel = newModel;
    }

    public void showTableDetails(Table table) {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Table Details");
        alert.setHeaderText("Details for Table " + table.getNumber());

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        TextField occupantsField = new TextField(String.valueOf(table.getOccupants()));
        occupantsField.setPromptText("Occupants");

        CheckBox statusCheckBox = new CheckBox("Occupied");
        statusCheckBox.setSelected(table.getStatus());

        TextArea noteTextArea = new TextArea(table.getNotes());
        noteTextArea.setEditable(true);

        TextArea orderTextArea = new TextArea(table.getOrders());
        orderTextArea.setEditable(true);

        grid.add(new Label("Occupants:"), 0, 0);
        grid.add(occupantsField, 1, 0);
        grid.add(new Label("Status:"), 0, 1);
        grid.add(statusCheckBox, 1, 1);
        grid.add(new Label("Note(s):"), 0, 2);
        grid.add(noteTextArea, 1, 2);
        grid.add(new Label("Order(s):"), 0, 3);
        grid.add(orderTextArea, 1, 3);

        HBox layout = new HBox(5);
        layout.getChildren().addAll(grid);

        alert.getDialogPane().setContent(layout);

        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                table.setOccupants(Integer.parseInt(occupantsField.getText()));
                table.setStatus(statusCheckBox.isSelected());
                table.setNotes(noteTextArea.getText());
                table.setOrders(orderTextArea.getText());
                updateView();
            }
        });
    }

    public void setController(ProgramController controller){
        this.back.setOnAction(event -> {
            controller.openMenuView(this.workerView);
        });

    }
    @Override
    public void modelChanged() {
        updateView();
    }
}
