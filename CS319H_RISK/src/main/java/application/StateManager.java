package application;

public class StateManager {

    private int currentPhase;

    public StateManager()
    {
        currentPhase = GameController.ARMY_PLACEMENT_PHASE;
    }

    public void nextPhase(){
        if (currentPhase == GameController.ARMY_PLACEMENT_PHASE) currentPhase = GameController.ATTACK_PHASE;
        else if (currentPhase == GameController.ATTACK_PHASE) currentPhase = GameController.FORTIFY_PHASE;
        else if (currentPhase == GameController.FORTIFY_PHASE) currentPhase = GameController.ARMY_PLACEMENT_PHASE;
    }
    public int getPhase(){
        return currentPhase;
    }
}
