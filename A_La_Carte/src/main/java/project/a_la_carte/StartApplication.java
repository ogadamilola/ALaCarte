package project.a_la_carte;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
//import project.a_la_carte.prototype.StartupMVC; //<- For Prototype un-comment
import project.a_la_carte.version2.StartupMVC;  //<- For Version2

import java.io.IOException;

public class StartApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException{
        StartupMVC startupMVC = new StartupMVC();
        Scene projectScene = new Scene(startupMVC);
        stage.setTitle("Prototype Project");
        stage.setScene(projectScene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
