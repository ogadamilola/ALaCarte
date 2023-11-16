package project.a_la_carte.version2.classesObjects;

import javafx.scene.control.Button;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import project.a_la_carte.version2.interfaces.*;

public class AlertButton extends Button implements AlertButtonInterface {
    Background alertBG;
    Background noAlertBG;
    public AlertButton(String name){
        double r = 2;
        this.setText(name);
        this.setShape(new Circle(r));
        this.setMinSize(3*r,2*r);
        alertBG = new Background(new BackgroundFill(Color.ORANGE,new CornerRadii(3),null));
        //If the button is unselected, leave it as it
        noAlertBG = new Background(new BackgroundFill(Color.WHITE,new CornerRadii(3),null));

        this.setStyle("-fx-border-color: black;\n");
        this.setBackground(noAlertBG);
    }
    @Override
    public void notificationYes() {
        setBackground(alertBG);
    }

    @Override
    public void notificationNo() {
        setBackground(noAlertBG);
    }
}
