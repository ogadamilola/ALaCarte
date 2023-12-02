package project.a_la_carte.version2;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

public class SignInView extends StackPane {
    TextField usernameText;
    PasswordField passwordText;
    Button logIn;
    Button signUpButton;

    SignInView(){
        VBox container = new VBox();
        container.setMaxSize(2000,1500);
        container.setMinSize(600,300);
        Label managerLabel = new Label("Manager Login");
        managerLabel.setFont(new Font(20));

        Label userLabel = new Label("Username: ");
        usernameText = new TextField();
        usernameText.setPrefWidth(400);
        HBox userHBox = new HBox(userLabel,usernameText);
        userHBox.setMinWidth(600);
        userHBox.setMaxWidth(1800);
        userHBox.setAlignment(Pos.CENTER);

        Label passLabel = new Label("Password: ");
        passwordText = new PasswordField();
        passwordText.setPrefWidth(400);
        HBox passHBox = new HBox(passLabel,passwordText);
        passHBox.setMinWidth(600);
        passHBox.setMaxWidth(1800);
        passHBox.setAlignment(Pos.CENTER);

        logIn = new Button("Log In");
        logIn.setFont(new Font(20));
        logIn.setPrefSize(200,20);

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
        container.getChildren().addAll(managerLabel,userHBox,passHBox,logIn,signUpButton);
        this.getChildren().add(container);
    }

    public void setController(ProgramController controller){
        logIn.setOnAction(controller::handleLogIn);
        signUpButton.setOnAction(controller::openSignUp);
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
