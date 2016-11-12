package ua.edu.iyatsouba;

import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import ua.edu.iyatsouba.controller.FxmlController;
import ua.edu.iyatsouba.util.SpringFXMLLoader;


public class InitApplication extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(final Stage stage) throws Exception {
        FxmlController controller = SpringFXMLLoader.load("/fxml/main.fxml");
        Parent parent = (Parent) controller.getView();
        Scene scene = new Scene(parent);
        stage.setTitle("sound recognition");
        scene.getStylesheets().add("/css/main.css");
        stage.setScene(scene);
        stage.show();
    }
}
