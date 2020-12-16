package game;

import screens.MainMenu;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import managers.GameManager;

public class Game{

    private static Game gameInstance = null;
    private static Stage gameStage = null;
    private GameManager gameManager = null;

    private Game()
    {
        gameStage = new Stage();

    }

    public static Game getInstance()
    {
        if (gameInstance == null)
            gameInstance = new Game();
        return gameInstance;
    }

    public GameManager getGameManager()
    {
        return gameManager;
    }

    public Stage getStage() {
        return gameStage;
    }

    public void gameInit(Stage primaryStage){
        gameStage = primaryStage;
        gameStage.setScene(new MainMenu().getScene());
        gameStage.sizeToScene();
        gameStage.setTitle("Deneme");
        gameStage.show();
    }
}
