package application;

import game.Game;
import managers.InputManager;
import managers.PlayerAction;

import java.util.Timer;
import java.util.TimerTask;

public class GameController {

    public static final int REINFORCEMENT_PHASE = 0;
    public static final int ATTACK_PHASE = 1;
    public static final int FORTIFY_PHASE = 2;

    private InputManager inputManager;
    private int maxTurnTime;
                                                                                // Where should we checked the maxTurnTime and the actual game time.
                                                                                // Also we can use AnimationTimer class of java instead of the timer class.
    private Timer turnTimer;
    private BattleManager battleManager;
    private StateManager stateManager;

    public GameController(){
        turnTimer = new Timer();
        inputManager = Game.getInstance().getGameManager().getInputManager();
        this.maxTurnTime = 1000;
        this.battleManager = new BattleManager();
        this.stateManager = new StateManager();
    }

    // Timer limits each player. It must be given as a attribute to the constructor.
    public GameController(int maxTurnTime, BattleManager battleManager,StateManager stateManager){
        turnTimer = new Timer();
        this.maxTurnTime = maxTurnTime;
        this.battleManager = battleManager;
        this.stateManager = stateManager;
    }

    public void startGameLoop()
    {
        getNextPlayerAction();
    }

    public void getNextPlayerAction()
    {
        int currentPhase;
        currentPhase = stateManager.getPhase();
        inputManager.awaitPlayerAction(this, currentPhase);
    }

    public void takePlayerAction(PlayerAction playerAction)
    {
        System.out.println("Retrieved action: " + playerAction);
        if (playerAction.getPhase() != stateManager.getPhase())
        {
            //This should not happen.
            System.err.println("Wrong phase played.");
        }
        performPlayerAction(playerAction);
        Game.getInstance().updateScreen();
    }

    public void performPlayerAction(PlayerAction playerAction)
    {

        if (playerAction.getPhase() == ATTACK_PHASE)
            performAttackAction(playerAction);
        else if (playerAction.getPhase() == REINFORCEMENT_PHASE)
            performReinforcementAction(playerAction);
        else if (playerAction.getPhase() == FORTIFY_PHASE)
            performFortifyAction(playerAction);

        getNextPlayerAction();
    }

    public boolean isAttackValid(PlayerAction playerAction)
    {
        //TODO
        return true;
    }

    public void performAttackAction(PlayerAction playerAction)
    {
        if (playerAction.isEndPhase())
        {
            stateManager.nextPhase();
        }
        else
        {
            if (isAttackValid(playerAction)) {
                //battleManager.battle
            }
        }
    }

    public boolean isReinforcementValid(PlayerAction playerAction)
    {
        //TODO
        return true;
    }
    public void performReinforcementAction(PlayerAction playerAction)
    {
        if (playerAction.isEndPhase())
        {
            stateManager.nextPhase();
        }
        else
        {
            //TODO
        }
    }

    public boolean isFortifyValid(PlayerAction playerAction)
    {
        //TODO
        return true;
    }
    public void performFortifyAction(PlayerAction playerAction)
    {
        if (playerAction.isEndPhase())
        {
            stateManager.nextPhase();
        }
        else
        {
            //TODO
        }
    }

    public void startRound(){
        turnTimer.schedule(endTurn(), maxTurnTime*1000);
    }

    // changes the state to the next state.
    // end Turn because of the time limit.

    public TimerTask endTurn(){

        //stateManager.changeState();
        return null;
    }

    public void battle(){


    }
}
