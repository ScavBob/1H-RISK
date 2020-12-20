package application;

import game.Game;

import java.io.Serializable;

public class StateManager implements Serializable {

    private int currentPhase;

    public StateManager()
    {
        currentPhase = GameController.REINFORCEMENT_PHASE;
    }

    public void nextPhase(){
        if (currentPhase == GameController.REINFORCEMENT_PHASE) currentPhase = GameController.ATTACK_PHASE;
        else if (currentPhase == GameController.ATTACK_PHASE) currentPhase = GameController.FORTIFY_PHASE;
        else if (currentPhase == GameController.FORTIFY_PHASE) currentPhase = GameController.REINFORCEMENT_PHASE;

        Game.getInstance().updateScreen();
    }
    public int getPhase(){
        return currentPhase;
    }
}
