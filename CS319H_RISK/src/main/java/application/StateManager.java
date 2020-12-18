package application;

public class StateManager {

    private int currentPhase;

    public StateManager()
    {
        currentPhase = GameController.REINFORCEMENT_PHASE;
    }

    public void nextPhase(){
        if (currentPhase == GameController.REINFORCEMENT_PHASE) currentPhase = GameController.ATTACK_PHASE;
        else if (currentPhase == GameController.ATTACK_PHASE) currentPhase = GameController.FORTIFY_PHASE;
        else if (currentPhase == GameController.FORTIFY_PHASE) currentPhase = GameController.REINFORCEMENT_PHASE;
    }
    public int getPhase(){
        return currentPhase;
    }
}
