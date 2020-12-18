package application;

import game.Game;
import managers.PlayerAction;

import java.io.Serializable;

public class Human implements PlayStrategy, Serializable {

    @Override
    public void getNextAction(GameController gameController, int currentPhase) {
        Game.getInstance().getGameManager().getInputManager().awaitPlayerAction(gameController, currentPhase);
    }
}
