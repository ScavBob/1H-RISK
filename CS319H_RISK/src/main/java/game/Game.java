package main.java.game;

import main.java.managers.GameManager;

public class Game {

    private static Game gameInstance = null;

    private GameManager gameManager;

    private Game()
    {

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
}
