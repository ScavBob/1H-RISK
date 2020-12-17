package game;

import javafx.scene.image.Image;
import managers.StorageManager;
import javafx.application.Application;
import javafx.stage.Stage;

import java.nio.file.Paths;

public class GameLauncher extends Application {
    private final Image ICON = new Image(Paths.get(StorageManager.RESOURCES_FOLDER_NAME + "icon.png").toUri().toString());
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.getIcons().add(ICON);
        primaryStage.setResizable(false);
        Game.getInstance().gameInit(primaryStage);
    }
}