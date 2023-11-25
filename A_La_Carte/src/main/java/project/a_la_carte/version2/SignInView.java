package project.a_la_carte.version2;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

public class SignInView extends StackPane {
    TextField usernameText;
    TextField passwordText;
    Button logIn;
    Button signUpButton;
    Button temp;

    SignInView(){
        VBox container = new VBox();
        container.setPrefSize(600,300);
        Label managerLabel = new Label("Manager Login");
        managerLabel.setFont(new Font(20));

        Label userLabel = new Label("Username: ");
        usernameText = new TextField();
        usernameText.setPrefWidth(400);
        HBox userHBox = new HBox(userLabel,usernameText);
        userHBox.setPrefWidth(600);
        userHBox.setAlignment(Pos.CENTER);

        Label passLabel = new Label("Password: ");
        passwordText = new TextField();
        passwordText.setPrefWidth(400);
        HBox passHBox = new HBox(passLabel,passwordText);
        passHBox.setPrefWidth(600);
        passHBox.setAlignment(Pos.CENTER);

        logIn = new Button("Log In");
        logIn.setFont(new Font(20));
        logIn.setPrefSize(200,20);

        //TODO REMOVE THIS  BUTTON WHEN DONE
        temp = new Button("Skip log in");

        signUpButton = new Button("Sign-up");
        signUpButton.setStyle("-fx-underline: true;-fx-border-color: transparent;-fx-background-color: transparent;-fx-text-fill: black;\n");
        signUpButton.setOnMouseEntered((event -> {
            signUpButton.setStyle("-fx-underline: true;-fx-border-color: transparent;-fx-background-color: transparent;-fx-text-fill: blue;\n");
        }));
        signUpButton.setOnMouseExited((event -> {
            signUpButton.setStyle("-fx-underline: true;-fx-border-color: transparent;-fx-background-color: transparent;-fx-text-fill: black;\n");
        }));

        container.setAlignment(Pos.CENTER);
        container.setSpacing(5);
        container.getChildren().addAll(managerLabel,userHBox,passHBox,logIn,signUpButton,temp);
        this.getChildren().add(container);
    }

    public void setController(ProgramController controller){
        logIn.setOnAction(controller::handleLogIn);
        signUpButton.setOnAction(controller::openSignUp);
        temp.setOnAction(controller::startManagerMainView);
    }
    public void clearFields(){
        usernameText.clear();
        passwordText.clear();
    }
    public TextField getUsernameText() {
        return usernameText;
    }

    public TextField getPasswordText() {
        return passwordText;
    }
}