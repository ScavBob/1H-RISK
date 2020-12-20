package application.playstrategy;

import application.controller.GameController;

public interface PlayStrategy {

    public void getNextAction(GameController gameController, int currentPhase);

}