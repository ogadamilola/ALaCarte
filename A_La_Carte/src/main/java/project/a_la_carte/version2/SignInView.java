package project.a_la_carte.version2;

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

    SignInView(){
        VBox container = new VBox();
        container.setPrefSize(600,300);
        Label managerLabel = new Label("Manager Login");
        managerLabel.setFont(new Font(20));

        Label userLabel = new Label("Username: ");
        usernameText = new TextField();
        usernameText.setPrefWidth(400);
        HBox userHBox = new HBox(userLabel,usernameText);

        Label passLabel = new Label("Password: ");
        passwordText = new TextField();
        passwordText.setPrefWidth(400);
        HBox passHBox = new HBox(passLabel,passwordText);

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

        container.getChildren().addAll(managerLabel,userHBox,passHBox,logIn,signUpButton);
        this.getChildren().add(container);
    }

    public void setController(ProgramController controller){
        logIn.setOnAction(controller::startManagerMainView);
        signUpButton.setOnAction(controller::openSignUp);
    }
}
