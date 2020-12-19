package application;

import game.Game;
import managers.InputManager;
import managers.PlayerAction;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.Serializable;
import javax.swing.Timer;

public class GameController implements Serializable {

    public static final int REINFORCEMENT_PHASE = 0;
    public static final int ATTACK_PHASE = 1;
    public static final int FORTIFY_PHASE = 2;

    private Match match;

    private int maxTurnTime;
    private int remainingTime;
    private Timer turnTimer;

    private BattleManager battleManager;
    private StateManager stateManager;

    public GameController(Match match, int maxTurnTime){
        this.match = match;
        this.turnTimer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                remainingTime--;
                if (remainingTime == 0)
                {
                    //send an artifical action.
                    do {
                        takePlayerAction(new PlayerAction(true, stateManager.getPhase(), null, null, 0));
                    } while (stateManager.getPhase() != REINFORCEMENT_PHASE);
                }
                Game.getInstance().updateScreen();
            }
        });
        this.maxTurnTime = maxTurnTime;
        this.remainingTime = maxTurnTime;
        this.battleManager = new BattleManager();
        this.stateManager = new StateManager();
    }

    public int getRemainingTime() {
        return remainingTime;
    }

    // Timer limits each player. It must be given as a attribute to the constructor.
    public GameController(int maxTurnTime, BattleManager battleManager,StateManager stateManager){
        //turnTimer = new Timer();
        this.maxTurnTime = maxTurnTime;
        this.battleManager = battleManager;
        this.stateManager = stateManager;
    }

    public void startGameLoop()
    {
        getNextPlayerAction();
        turnTimer.start();
    }

    public void getNextPlayerAction()
    {
        if (match.isMatchOver())
        {
            System.out.println("Game has ended.");
            return;
        }

        int currentPhase;
        currentPhase = stateManager.getPhase();
        Player currentPlayer = match.getCurrentPlayer();
        currentPlayer.getPlayerAction(this, currentPhase);
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
        match.update();
    }

    public void performPlayerAction(PlayerAction playerAction)
    {
        if (playerAction.getPhase() == ATTACK_PHASE)
            performAttackAction(playerAction);
        else if (playerAction.getPhase() == REINFORCEMENT_PHASE)
            performReinforcementAction(playerAction);
        else if (playerAction.getPhase() == FORTIFY_PHASE)
            performFortifyAction(playerAction);

        Game.getInstance().updateScreen();

        getNextPlayerAction();
    }

    public boolean isAttackValid(PlayerAction playerAction)
    {
        Player currentPlayer = match.getCurrentPlayer();
        Region srcRegion = playerAction.getFirstRegion();
        Region dstRegion = playerAction.getSecondRegion();
        int armyCount = playerAction.getArmyCount();
        Map map = Game.getInstance().getGameManager().getMatch().getMap();

<<<<<<< HEAD
=======
        //Game.getInstance().showNotification("deneme");

>>>>>>> 3c982e4b1e9be6e9277368ecbd0f24dc6cde9bec
        if (armyCount > srcRegion.getUnitCount() || armyCount == 0) return false;
        if (!srcRegion.getOwner().equals(currentPlayer)) return false;
        if (dstRegion.getOwner().equals(currentPlayer)) return false;
        if (!map.isAdjacentRegion(srcRegion, dstRegion)) return false;

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
                Region srcRegion = playerAction.getFirstRegion();
                Region dstRegion = playerAction.getSecondRegion();
                int armyCount = playerAction.getArmyCount();

                battleManager.initBattle(srcRegion, dstRegion, armyCount);
            }
            else
            {
                //TODO
                //Maybe show a message on the screen?
                System.out.println("Not a valid attack");
            }
        }
    }

    public boolean isReinforcementValid(PlayerAction playerAction)
    {
        Player currentPlayer = match.getCurrentPlayer();
        int availableReinforcements = currentPlayer.getAvailableReinforcements();

        if (playerAction.getArmyCount() > availableReinforcements) return false;
        if (!playerAction.getFirstRegion().getOwner().equals(currentPlayer)) return false;

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
            if (isReinforcementValid(playerAction)) {
                Region chosenRegion = playerAction.getFirstRegion();
                int armyCount = playerAction.getArmyCount();
                Game.getInstance().getGameManager().getMapManager().increaseArmyCount(chosenRegion, armyCount);
                match.getCurrentPlayer().decreaseAvailableReinforcements(armyCount);
            }
            else
            {
                //TODO
                //Maybe show a message on the screen?
                System.out.println("Not a valid reinforcement");
            }
        }
    }

    public boolean isFortifyValid(PlayerAction playerAction)
    {
        Player currentPlayer = Game.getInstance().getGameManager().getMatch().getCurrentPlayer();
        Region srcRegion = playerAction.getFirstRegion();
        Region dstRegion = playerAction.getSecondRegion();
        int armyCount = playerAction.getArmyCount();
        Map map = Game.getInstance().getGameManager().getMatch().getMap();

        if (armyCount > srcRegion.getUnitCount() || armyCount == 0) return false;
        if (!srcRegion.getOwner().equals(currentPlayer)) return false;
        if (!dstRegion.getOwner().equals(currentPlayer)) return false;
        if (!map.isConnected(srcRegion, dstRegion)) return false;
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
            if (isFortifyValid(playerAction))
            {
                Region srcRegion = playerAction.getFirstRegion();
                Region dstRegion = playerAction.getSecondRegion();
                int armyCount = playerAction.getArmyCount();

                //TODO
                //incrementing with negative value now.
                Game.getInstance().getGameManager().getMapManager().
                        increaseArmyCount(srcRegion, -armyCount);
                Game.getInstance().getGameManager().getMapManager().
                        increaseArmyCount(dstRegion, armyCount);

                stateManager.nextPhase();
            }
            else
            {
                System.out.println("Not a valid fortify");
                return;
            }
        }

        //After fortify, the game proceeds to the next turn.
        match.nextTurn();
        remainingTime = maxTurnTime;
    }

    public void battle(){

    }

    public int getCurrentPhase() {
        return stateManager.getPhase();
    }
}
