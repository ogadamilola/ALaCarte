package project.a_la_carte.version2;

import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import project.a_la_carte.version2.classesObjects.Staff;

public class SignUpView extends StackPane {
    VBox container;
    TextField fNameText;
    TextField lNameText;
    TextField idText;
    TextField sinText;
    TextField usernameText;
    TextField passwordText;

    Button createAccButton;
    Button backButton;

    SignUpView(){
        container = new VBox();
        this.setPrefSize(600,300);

        container.setStyle("-fx-border-color: black;\n");
        Label addLabel = new Label("New Manager Sign Up");
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


        Label userLabel = new Label("Username: ");
        usernameText = new TextField();
        usernameText.setPrefWidth(400);
        HBox userHBox = new HBox(userLabel,usernameText);

        Label passLabel = new Label("Password: ");
        passwordText = new TextField();
        passwordText.setPrefWidth(400);
        HBox passHBox = new HBox(passLabel, passwordText);

        createAccButton = new Button("Sign-up");
        createAccButton.setFont(new Font(20));
        createAccButton.setPrefSize(200,20);
        backButton = new Button("Back");
        backButton.setFont(new Font(20));
        backButton.setPrefSize(200,20);



        container.getChildren().addAll(addLabel,fNameHBox,lNameHBox,idHBox,sinHBox,userHBox,passHBox,createAccButton,backButton);
        this.getChildren().add(container);
    }

    public void setController(ProgramController controller){
        createAccButton.setOnAction(controller::newManager);
        backButton.setOnAction(controller::openStartUpMVC);
    }

    public TextField getfNameText() {return fNameText;}
    public TextField getlNameText() {
        return lNameText;
    }
    public TextField getSinText() {
        return sinText;
    }
    public TextField getIdText() {
        return idText;
    }

    public TextField getUsernameText() {
        return usernameText;
    }

    public TextField getPasswordText() {
        return passwordText;
    }
}
