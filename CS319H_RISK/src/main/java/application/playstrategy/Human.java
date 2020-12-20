package application.playstrategy;

import application.controller.GameController;
import gamelauncher.Game;

import java.io.Serializable;

public class Human implements PlayStrategy, Serializable {

    @Override
    public void getNextAction(GameController gameController, int currentPhase) {
        Game.getInstance().getGameManager().getInputManager().awaitPlayerAction(gameController, currentPhase);
    }
}
