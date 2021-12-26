package uni.finalproject;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class GUIApplication extends Application {
    private static Stage initialStage;
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(GUIApplication.class.getResource("hello-view.fxml"));
        initialStage=stage;
        Scene scene = new Scene(fxmlLoader.load(), 600, 600);
        stage.setTitle("University Registration System!");
        stage.setScene(scene);
        stage.show();
    }
    public static void showMenu(String file, String title) throws IOException{
        FXMLLoader fxmlLoader = new FXMLLoader(GUIApplication.class.getResource(file));
        Scene scene = new Scene(fxmlLoader.load(),600,600);
        Stage stage = new Stage();
        stage.setTitle(title);
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(initialStage);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
