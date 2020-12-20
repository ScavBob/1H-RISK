package managers;

import application.GameController;
import application.Region;
import game.Game;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Manager class that handles taking the player inputs from the screen
 * and directing them to the awaiting GameController.
 *
 * @author Ruzgar Ayan
 */
public class InputManager
{

    /**
     * These represent what kind of inputs this InputManager is waiting for right now.
     */
    private enum WaitingState {
        NOT_WAITING,
        ATTACK,
        REINFORCEMENT,
        FORTIFY
    }

    /**
     * These represent what kind of inputs this InputManager is waiting for right now in even more detail.
     * Not used currently externally, can be removed.
     */
    public static final int NOT_WAITING = 0;
    public static final int WAITING_FIRST_REGION = 1;
    public static final int WAITING_SECOND_REGION = 2;
    public static final int WAITING_ARMY_COUNT = 3;

    /**
     * This represents what kind of inputs this InputManager is waiting for right now.
     */
    private WaitingState waitingState;

    /**
     * Armies that have been selected and waiting to be directed.
     */
    private Region firstRegion;
    private Region secondRegion;

    /**
     * Army count for an action that have been selected and waiting to be directed.
     */
    private int armyCount;

    /**
     * True if the player has made a end phase request, false if the player did any other request.
     */
    private boolean endPhase;

    /**
     * Thi represents what kind of inputs this InputManager is waiting for right now in even more detail.
     * Not used currently externally, can be removed.
     */
    private int waitingInputType;

    /**
     * InputManager directs the user inputs from the screen,
     * to this specified GameController.
     */
    private GameController awaitingController;
    /**
     * This is the phase that the GameController is awaiting an action for.
     */
    private int awaitingPhase;

    /**
     * Timer is used to inform the awaiting controller in specified time intervals.
     */
    private Timer timer;

    /**
     * The time interval for which the awaiting timer is informed each time.
     */
    private static final int INFORM_TIME_INTERVAL_MS = 50;

    public InputManager()
    {
        resetInputs();

        //Start the timer that informs the controller.
        timer = new Timer(INFORM_TIME_INTERVAL_MS, new ActionListener() {
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

    /**
     * If all the required input have been taken by the screen,
     * inform the GameController by first using the corresponding methods for each phase.
     */
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

    public void resetRegions()
    {
        firstRegion = null;
        secondRegion = null;
    }

    /**
     * Sets the region as one of its attribute Regions if the phase-related conditions are satisfied.
     *
     * @param region The input region that comes from the player
     * @return true if successful, false otherwise
     */
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
                if (region != firstRegion)
                {
                    waitingInputType = WAITING_ARMY_COUNT;
                    secondRegion = region;
                    return true;
                }
                return false;
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

    /**
     * Sets the army count as one of its attributes.
     *
     * @param armyCount The number or army units that the player have chosen.
     * @return true if successful, false otherwise
     */
    public boolean chooseArmyCount(int armyCount)
    {
        if (waitingState == WaitingState.NOT_WAITING) return false;
        if (this.armyCount != 0) return false;
        this.armyCount = armyCount;
        return true;
    }

    /**
     * Sets the endPhase attribute to true.
     *
     * @return true if successful, false otherwise
     * Currently, only return true.
     */
    public boolean endPhase()
    {
        endPhase = true;
        return true;
    }

    /**
     * The maximum amount of army that can be chosen by the player in the screen
     * according to the current phase's conditions.
     *
     * @return maximum choosable army.
     */
    public int getMaxChoosableArmy(){
        if(awaitingPhase == GameController.REINFORCEMENT_PHASE){
            return Game.getInstance().getGameManager().getMatch().getCurrentPlayer().getAvailableReinforcements();
        }else if(awaitingPhase == GameController.ATTACK_PHASE){
            if(firstRegion != null)
                return Math.min(firstRegion.getUnitCount() - 1, 3);
            else
                return 0;
        }
        else {
            if(firstRegion != null)
                return firstRegion.getUnitCount() - 1;
            else
                return 0;
        }
    }

    public void awaitPlayerAction(GameController awaitingController, int awaitingPhase)
    {
        resetInputs();
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
