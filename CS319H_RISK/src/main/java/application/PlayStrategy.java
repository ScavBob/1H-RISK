package application;

import managers.PlayerAction;

public interface PlayStrategy{

    public void getNextAction(GameController gameController, int currentPhase);

}