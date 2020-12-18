package application;

import game.Game;
import managers.PlayerAction;

import java.io.Serializable;

public class AI implements PlayStrategy, Serializable {
    
    //Levels are declared  int as default.
    private int level;

    @Override
    public void getNextAction(GameController gameController, int currentPhase) {
        AIMoveGenerator.awaitAIAction(gameController, currentPhase, level);
    }


}
