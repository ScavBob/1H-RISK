package managers;

import application.GameController;
import application.Region;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class InputManager
{
    private enum WaitingState {
        NOT_WAITING,
        ATTACK,
        REINFORCEMENT,
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

    private GameController awaitingController;
    private int awaitingPhase;

    private Timer timer;

    public InputManager()
    {
        resetInputs();
        timer = new Timer(100, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                informAwaitingController();
            }
        });
        timer.start();
    }

    public int getWaitingInputType()
    {
        return waitingInputType;
    }

    public int getCurrentPhase()
    {
        return awaitingPhase;
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

    public void informAwaitingController()
    {
        PlayerAction playerAction = null;
        GameController controller = awaitingController;

        if (awaitingPhase == GameController.REINFORCEMENT_PHASE)
        {
            playerAction = getReinforcementAction();
        }
        else if (awaitingPhase == GameController.ATTACK_PHASE)
        {
            playerAction = getAttackAction();
        }
        else if (awaitingPhase == GameController.FORTIFY_PHASE)
        {
            playerAction = getFortifyAction();
        }

        if (playerAction != null)
        {
            controller.takePlayerAction(playerAction);
        }
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

        else if (waitingState == WaitingState.REINFORCEMENT)
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

    public void awaitPlayerAction(GameController awaitingController, int awaitingPhase)
    {
        resetInputs();
        armyCount = 2;
        this.awaitingController = awaitingController;
        this.awaitingPhase = awaitingPhase;

        if (awaitingPhase == GameController.REINFORCEMENT_PHASE) {
            waitingState = WaitingState.REINFORCEMENT;
        }
        else if (awaitingPhase == GameController.ATTACK_PHASE) {
        waitingState = WaitingState.ATTACK;
        }
        else if (awaitingPhase == GameController.FORTIFY_PHASE) {
            waitingState = WaitingState.FORTIFY;
        }

        waitingInputType = WAITING_FIRST_REGION;
    }

    public PlayerAction getAttackAction()
    {
        if (!endPhase && (secondRegion == null || armyCount == 0)) return null;

        PlayerAction action = new PlayerAction(endPhase, awaitingPhase, firstRegion, secondRegion, armyCount);

        resetInputs();
        return action;
    }

    public PlayerAction getReinforcementAction()
    {
        if (!endPhase && (firstRegion == null || armyCount == 0)) return null;

        PlayerAction action = new PlayerAction(endPhase, awaitingPhase, firstRegion, secondRegion, armyCount);

        resetInputs();
        return action;
    }

    public PlayerAction getFortifyAction()
    {
        if (!endPhase && (secondRegion == null || armyCount == 0)) return null;

        PlayerAction action = new PlayerAction(endPhase, awaitingPhase, firstRegion, secondRegion, armyCount);

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
        awaitingController = null;
        awaitingPhase = -1;
    }
}
