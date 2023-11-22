package project.a_la_carte.version2;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class ServerSignInView extends StackPane {

    TextField pinText;
    Button logInButton;

    ServerSignInView(){

        Label pinLabel = new Label("Enter PIN: ");
        pinText = new TextField();
        pinText.textProperty().addListener((observable,oldValue,newValue) -> {
            if(newValue.length() > 4) {
                pinText.setText(oldValue);
            }
        } );

        HBox pinHBox = new HBox(pinLabel,pinText);
        logInButton = new Button("Login");

        VBox container = new VBox(pinHBox,logInButton);
        this.getChildren().add(container);

    }

    public void setController(ProgramController controller){
        logInButton.setOnAction(controller::handleServerLogIn);

    }

    public TextField getPinText() {
        return pinText;
    }

    public Button getLogInButton() {
        return logInButton;
    }
}
