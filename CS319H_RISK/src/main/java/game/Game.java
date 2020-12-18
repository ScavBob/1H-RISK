package game;

import screens.MainMenu;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import managers.GameManager;
import screens.Screen;
import screens.UpdatableScreen;

import java.util.ArrayList;

public class Game {

    private static int WINDOW_WIDTH = 1280;
    private static int WINDOW_HEIGHT = 720;
    private static Game gameInstance = null;
    private static Stage gameStage = null;
    private GameManager gameManager = null;
    private ArrayList<UpdatableScreen> subscribedScreens;

    private Game()
    {
        gameStage = new Stage();
        gameManager = new GameManager();
        subscribedScreens = new ArrayList<>();
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

    public Stage getStage()
    {
        return gameStage;
    }

    public void setScene(Scene scene)
    {
        gameStage.setScene(scene);
    }

    public void setScreen(Screen screen)
    {
        gameStage.setScene(screen.getScene());
    }

    public void gameInit(Stage primaryStage){
        gameStage = primaryStage;
        gameStage.setScene(new MainMenu().getScene());
        gameStage.sizeToScene();
        gameStage.setTitle("RISK: A GAME OF STRATEGIC CONQUEST");
        gameStage.show();
    }

    public void subscribeForUpdate(UpdatableScreen screen)
    {
        subscribedScreens.add(screen);
    }

    public void updateScreen()
    {
        for (UpdatableScreen s: subscribedScreens)
            s.update();
    }
}
