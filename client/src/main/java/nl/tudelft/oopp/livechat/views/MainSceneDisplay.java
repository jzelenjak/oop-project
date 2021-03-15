package nl.tudelft.oopp.livechat.views;

import java.io.IOException;
import java.net.URL;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import nl.tudelft.oopp.livechat.controllers.NavigationController;
import nl.tudelft.oopp.livechat.data.User;

/**
 * Loads the MainScene.fxml file
 * And initializes the NavigationController
 */
public class MainSceneDisplay extends Application {

    /**
     * Starts the app.
     *
     * @param primaryStage the parameter
     * @throws IOException the exception
     */
    @Override
    public void start(Stage primaryStage) throws IOException {

        User.setUid();
        FXMLLoader loader = new FXMLLoader();
        URL xmlUrl = getClass().getResource("/fxml/mainScene.fxml");
        loader.setLocation(xmlUrl);
        Parent root = loader.load();

        primaryStage.setScene(new Scene(root));
        primaryStage.setTitle("App name");
        primaryStage.getIcons().add(
                new Image("file:client/src/main/resources/images/tudelftLogo.png"));
        primaryStage.show();
        NavigationController.setCurrentController(
                new NavigationController(primaryStage.getScene()));
    }

    public static void main(String[] args) {
        launch(args);
    }
}