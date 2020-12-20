package application;

import game.Game;
import javafx.application.Platform;
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
            System.out.println("GameResources has ended.");
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
        boolean successful = false;

        if (playerAction.getPhase() == ATTACK_PHASE)
            successful = performAttackAction(playerAction);
        else if (playerAction.getPhase() == REINFORCEMENT_PHASE)
            successful = performReinforcementAction(playerAction);
        else if (playerAction.getPhase() == FORTIFY_PHASE)
            successful = performFortifyAction(playerAction);

        if (successful)
        {
            Game.getInstance().setBattleLog(playerAction.toString());
        }
        Game.getInstance().updateScreen();

        getNextPlayerAction();
    }

    private void showInvalidMove(String content)
    {
        Game.getInstance().showWarningMessage("Warning", "Invalid move", content);
    }

    public boolean isAttackValid(PlayerAction playerAction)
    {
        Player currentPlayer = match.getCurrentPlayer();
        Region srcRegion = playerAction.getFirstRegion();
        Region dstRegion = playerAction.getSecondRegion();
        int armyCount = playerAction.getArmyCount();
        Map map = Game.getInstance().getGameManager().getMatch().getMap();

        if (armyCount > srcRegion.getUnitCount() || armyCount == 0)
        {
            showInvalidMove("You are trying to attack with an incorrect amount of army.");
            return false;
        }
        if (!srcRegion.getOwner().equals(currentPlayer))
        {
            showInvalidMove("You are trying to attack from someone else's region.");
            return false;
        }
        if (dstRegion.getOwner().equals(currentPlayer))
        {
            showInvalidMove("You are trying to attack your own region.");
            return false;
        }
        if (!map.isAdjacentRegion(srcRegion, dstRegion))
        {
            showInvalidMove("You are trying to attack a non-adjacent region.");
            return false;
        }

        return true;
    }

    public boolean performAttackAction(PlayerAction playerAction)
    {
        if (playerAction.isEndPhase())
        {
            stateManager.nextPhase();
            return true;
        }
        else
        {
            if (isAttackValid(playerAction)) {
                Region srcRegion = playerAction.getFirstRegion();
                Region dstRegion = playerAction.getSecondRegion();
                int armyCount = playerAction.getArmyCount();

                battleManager.initBattle(srcRegion, dstRegion, armyCount);
                return true;
            }
            else
            {
                return false;
            }
        }
    }

    public boolean isReinforcementValid(PlayerAction playerAction)
    {
        Player currentPlayer = match.getCurrentPlayer();
        int availableReinforcements = currentPlayer.getAvailableReinforcements();

        if (playerAction.getArmyCount() > availableReinforcements || playerAction.getArmyCount() <= 0)
        {
            showInvalidMove("You are trying to reinforce an incorrect amount of soldiers");
            return false;
        }
        if (!playerAction.getFirstRegion().getOwner().equals(currentPlayer))
        {
            showInvalidMove("You are trying to reinforce to someone else's region");
            return false;
        }

        return true;
    }
    public boolean performReinforcementAction(PlayerAction playerAction)
    {
        if (playerAction.isEndPhase())
        {
            stateManager.nextPhase();
            return true;
        }
        else
        {
            if (isReinforcementValid(playerAction)) {
                Region chosenRegion = playerAction.getFirstRegion();
                int armyCount = playerAction.getArmyCount();
                Game.getInstance().getGameManager().getMapManager().increaseArmyCount(chosenRegion, armyCount);
                match.getCurrentPlayer().decreaseAvailableReinforcements(armyCount);
                return true;
            }
            else
            {
                return false;
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

        if (armyCount > srcRegion.getUnitCount() || armyCount <= 0)
        {
            showInvalidMove("You are trying to fortify an incorrect amount of soldiers");
            return false;
        }
        if (!srcRegion.getOwner().equals(currentPlayer))
        {
            showInvalidMove("You are trying to fortify from someone else's region");
            return false;
        }
        if (!dstRegion.getOwner().equals(currentPlayer))
        {
            showInvalidMove("You are trying to fortify to someone else's region");
            return false;
        }
        if (!map.isConnected(srcRegion, dstRegion))
        {
            showInvalidMove("You are trying to fortify to an unconnected region");
            return false;
        }
        return true;
    }
    public boolean performFortifyAction(PlayerAction playerAction)
    {
        if (playerAction.isEndPhase())
        {
            stateManager.nextPhase();

            //After fortify, the game proceeds to the next turn.
            match.nextTurn();
            remainingTime = maxTurnTime;
            return true;
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

                //After fortify, the game proceeds to the next turn.
                match.nextTurn();
                remainingTime = maxTurnTime;
                return true;
            }
            else
            {
                return false;
            }
        }
    }

    public int getCurrentPhase() {
        return stateManager.getPhase();
    }
}
