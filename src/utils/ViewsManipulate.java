package utils;

import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Created by claudio on 27/11/16.
 * Singleton Class for Views
 */
public class ViewsManipulate {
    private Stage stage;
    private Stage primaryWindows;

    private static ViewsManipulate instance;

    public static synchronized ViewsManipulate getInstance() {
        if (instance == null)
            instance = new ViewsManipulate();
        return instance;
    }

    public Stage getPrimaryWindows() {
        return primaryWindows;
    }

    public void setPrimaryWindows(Stage primaryWindows) {
        this.primaryWindows = primaryWindows;
    }

    public Initializable loadScreen(Class classVar, String fxmlLocation, String screenTitle,
                                    int width, int height) {
        Stage stage = new Stage();
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(classVar.getResource(fxmlLocation));
            Parent root = fxmlLoader.load();
            Scene scene = new Scene(root, width, height);
            stage.setTitle(screenTitle);
            stage.setScene(scene);
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(ViewsManipulate.getInstance().getPrimaryWindows());
            stage.show();
            return fxmlLoader.getController();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}