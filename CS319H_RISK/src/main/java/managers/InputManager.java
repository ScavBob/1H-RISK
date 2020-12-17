package managers;

import application.Region;

import java.util.TimerTask;

public class InputManager
{
    public enum WaitingState {
        NOT_WAITING,
        ATTACK,
        ARMY_PLACEMENT,
        FORTIFY,
    }

    private WaitingState waitingState;

    private Region firstRegion;
    private Region secondRegion;
    private int armyCount;

    private boolean endPhase;

    private boolean waitingForUserInput;

    public InputManager()
    {
        resetInputs();
    }

    public boolean chooseRegion(Region region)
    {
        if (waitingState == WaitingState.NOT_WAITING) return false;

        if (waitingState == WaitingState.ATTACK || waitingState == WaitingState.FORTIFY)
        {
            if (firstRegion == null)
            {
                firstRegion = region;
                return true;
            }
            else if (secondRegion == null)
            {
                secondRegion = region;
                return true;
            }
            else
            {
                return false;
            }
        }

        else if (waitingState == WaitingState.ARMY_PLACEMENT)
        {
            if (firstRegion == null)
            {
                firstRegion = region;
                return true;
            }
            else
            {
                return false;
            }
        }

        return true;
    }

    public boolean chooseArmyCount(int armyCount)
    {
        if (waitingState == WaitingState.NOT_WAITING) return false;
        if (armyCount != 0) return false;

        this.armyCount = armyCount;
        return true;
    }

    public boolean endPhase()
    {
        endPhase = true;
        return true;
    }

    public PlayerAction getAttackAction()
    {
        waitingState = WaitingState.ATTACK;

        while (!endPhase && (secondRegion == null || armyCount == 0)) {
            waitingForUserInput = true;
        }

        PlayerAction action = new PlayerAction(endPhase, firstRegion, secondRegion, armyCount);
        System.out.println(action);

        resetInputs();
        return action;
    }

    public PlayerAction getArmyPlacementAction()
    {
        waitingState = WaitingState.ARMY_PLACEMENT;

        resetInputs();
        return null;
    }

    public PlayerAction getFortifyAction()
    {
        waitingState = WaitingState.FORTIFY;

        resetInputs();
        return null;
    }

    private void resetInputs()
    {
        waitingState = WaitingState.NOT_WAITING;
        firstRegion = null;
        secondRegion = null;
        armyCount = 0;
        waitingForUserInput = false;
        endPhase = false;
    }
}
