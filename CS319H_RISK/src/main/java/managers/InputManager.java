package managers;

import application.Region;

import java.util.TimerTask;

public class InputManager
{
    private enum WaitingState {
        NOT_WAITING,
        ATTACK,
        ARMY_PLACEMENT,
        FORTIFY
    }

    public static final int NOT_WAITING = 0;
    public static final int WAITING_FIRST_REGION = 1;
    public static final int WAITING_SECOND_REGION = 2;
    public static final int WAITING_ARMY_COUNT = 3;

    private WaitingState waitingState;

    private Region firstRegion;
    private Region secondRegion;
    private int armyCount;

    private boolean endPhase;

    private int waitingInputType;

    public InputManager()
    {
        resetInputs();
    }

    public int getWaitingInputType()
    {
        return waitingInputType;
    }

    public String getCurrentPhase()
    {
        if (waitingState == WaitingState.NOT_WAITING) return "";
        else if (waitingState == WaitingState.ATTACK) return "Attack Phase";
        else if (waitingState == WaitingState.ARMY_PLACEMENT) return "Army Placement Phase";
        else if (waitingState == WaitingState.FORTIFY) return "Fortify Phase";
        else return "";
    }

    public Region getFirstRegion() {
        return firstRegion;
    }

    public Region getSecondRegion() {
        return secondRegion;
    }

    public int getArmyCount() {
        return armyCount;
    }

    public boolean chooseRegion(Region region)
    {
        if (waitingState == WaitingState.NOT_WAITING) return false;

        if (waitingState == WaitingState.ATTACK || waitingState == WaitingState.FORTIFY)
        {
            if (firstRegion == null)
            {
                waitingInputType = WAITING_SECOND_REGION;
                firstRegion = region;
                return true;
            }
            else if (secondRegion == null)
            {
                waitingInputType = WAITING_ARMY_COUNT;
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
                waitingInputType = WAITING_ARMY_COUNT;
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
        if (this.armyCount != 0) return false;
        this.armyCount = armyCount;
        return true;
    }

    public boolean endPhase()
    {
        endPhase = true;
        return true;
    }

    public void awaitAttackAction()
    {
        resetInputs();
        waitingInputType = WAITING_FIRST_REGION;
        waitingState = WaitingState.ATTACK;
    }

    public void awaitArmyPlacementAction()
    {
        resetInputs();
        waitingInputType = WAITING_FIRST_REGION;
        waitingState = WaitingState.ARMY_PLACEMENT;
    }

    public void awaitFortifyAction()
    {
        resetInputs();
        waitingInputType = WAITING_FIRST_REGION;
        waitingState = WaitingState.FORTIFY;
    }

    public PlayerAction getAttackAction()
    {
        if (!endPhase && (secondRegion == null || armyCount == 0)) return null;

        PlayerAction action = new PlayerAction(endPhase, firstRegion, secondRegion, armyCount);

        resetInputs();
        return action;
    }

    public PlayerAction getArmyPlacementAction()
    {
        if (!endPhase && (firstRegion == null || armyCount == 0)) return null;

        PlayerAction action = new PlayerAction(endPhase, firstRegion, secondRegion, armyCount);

        resetInputs();
        return action;
    }

    public PlayerAction getFortifyAction()
    {
        if (!endPhase && (secondRegion == null || armyCount == 0)) return null;

        PlayerAction action = new PlayerAction(endPhase, firstRegion, secondRegion, armyCount);

        resetInputs();
        return action;
    }

    private void resetInputs()
    {
        waitingState = WaitingState.NOT_WAITING;
        firstRegion = null;
        secondRegion = null;
        armyCount = 0;
        endPhase = false;
        waitingInputType = NOT_WAITING;
    }
}
