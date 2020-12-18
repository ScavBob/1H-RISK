package application;

import game.Game;
import managers.PlayerAction;

public class Human implements PlayStrategy{

    @Override
    public void getNextAction(GameController gameController, int currentPhase) {
        Game.getInstance().getGameManager().getInputManager().awaitPlayerAction(gameController, currentPhase);
    }
}
