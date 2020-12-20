package game;

import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import screens.GameScreen;
import screens.MainMenu;
import javafx.scene.Scene;
import javafx.stage.Stage;
import managers.GameManager;
import screens.Screen;
import screens.UpdatableScreen;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

public class Game {

    private static int WINDOW_WIDTH = 1280;
    private static int WINDOW_HEIGHT = 720;
    private static Game gameInstance = null;
    private static Stage gameStage = null;
    private GameManager gameManager = null;
    private ArrayList<UpdatableScreen> subscribedScreens;
    private GameScreen currentGameScreen;

    private Game()
    {
        gameStage = new Stage();
        gameManager = new GameManager();
        subscribedScreens = new ArrayList<>();
        currentGameScreen = null;
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

    public void nextPhaseInScreen()
    {
        currentGameScreen.nextPhaseInScreen();
    }

    public void showWarningMessage(String title, String header, String content)
    {
        Platform.runLater(new Runnable() {
            public void run() {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setContentText(content);
                alert.setTitle(title);
                alert.setHeaderText(header);
                alert.showAndWait();
            }
        });
    }
    public void showInformationMessage(String title, String header, String content)
    {
        Platform.runLater(new Runnable() {
            public void run() {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setContentText(content);
                alert.setTitle(title);
                alert.setHeaderText(header);
                alert.showAndWait();
            }
        });
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

    public boolean confirmBattle(int attackingArmyCount, int defendingArmyCount, String atkRegionName, String defRegionName, boolean showButtons)
    {
        if (currentGameScreen == null) return false;
        final FutureTask query = new FutureTask(new Callable() {
            @Override
            public Object call() throws Exception {
                return currentGameScreen.confirmBattle(attackingArmyCount, defendingArmyCount, atkRegionName, defRegionName, showButtons);
            }
        });
        Platform.runLater(query);
        try {
            return (Boolean) (query.get());
        } catch (InterruptedException | ExecutionException e) {
            return false;
        }
    }

    public void setCurrentGameScreen(GameScreen gameScreen)
    {
        this.currentGameScreen = gameScreen;
    }

    public void updateScreen()
    {
        Platform.runLater(new Runnable() {
            public void run() {
                for (UpdatableScreen s: subscribedScreens)
                    s.update();
            }
        });
    }

    public void updateTimer()
    {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                currentGameScreen.updateTimer();
            }
        });
    }

    public int showBattleResult(List<Integer> attackerDice, List<Integer> defenderDice, List<Boolean> results,
                                String atkRegionName, String defRegionName,
                                boolean won, int maxChoosableArmyCount, boolean isAttackAI) {
        if (currentGameScreen == null) return -1;
        final FutureTask query = new FutureTask(new Callable() {
            @Override
            public Object call() throws Exception {
                return currentGameScreen.showBattleResults(attackerDice, defenderDice, results,
                        atkRegionName, defRegionName, won, maxChoosableArmyCount, isAttackAI);
            }
        });
        Platform.runLater(query);
        try {
            return (Integer) (query.get());
        } catch (InterruptedException | ExecutionException e) {
            return -1;
        }
    }

    public void setBattleLog(String battleLog) {
        currentGameScreen.setBattleLog(battleLog);
    }
}
