package gamelauncher;

import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.stage.WindowEvent;
import manager.StorageManager;
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
        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                if(Game.getInstance().getGameManager().getMatch() != null)
                    Game.getInstance().getGameManager().getStorageManager().saveGame(Game.getInstance().getGameManager().getMatch(), "lastSave.risk");
                System.exit(0);
            }
        });
        Game.getInstance().gameInit(primaryStage);
    }
}