package manager.entity;

import application.controller.GameController;
import application.Player;
import application.Region;
import gamelauncher.Game;

public class PlayerAction {

    private boolean endPhase;
    private int phase;
    private Region firstRegion;
    private Region secondRegion;
    private int armyCount;

    @Override
    public String toString() {
        Player currentPlayer = Game.getInstance().getGameManager().getMatch().getCurrentPlayer();

        String ans = "";

        ans += currentPlayer.getName();

        if (endPhase)
        {
            ans += " ended the ";

            if (phase == GameController.REINFORCEMENT_PHASE)
                ans += " reinforcement phase.";
            else if (phase == GameController.ATTACK_PHASE)
                ans += " attack phase.";
            else if (phase == GameController.FORTIFY_PHASE)
                ans += " reinforcement phase.";
        }
        else
        {
            if (phase == GameController.REINFORCEMENT_PHASE) {
                ans = ans + " reinforced " + armyCount + " troops to " + firstRegion.getRegionName() + ".";
            }
            else if (phase == GameController.ATTACK_PHASE) {
                ans += " attacked from " + firstRegion.getRegionName() + " to " +  secondRegion.getRegionName() +
                        " with " + armyCount + " troops.";
            }
            else if (phase == GameController.FORTIFY_PHASE)
                ans += " reinforced " + armyCount + " troops from " + firstRegion.getRegionName() + " to " +
                        secondRegion.getRegionName() + ".";
        }

        return ans;
    }

    public PlayerAction(boolean endPhase, int phase, Region firstRegion, Region secondRegion, int armyCount) {
        this.endPhase = endPhase;
        this.phase = phase;
        this.firstRegion = firstRegion;
        this.secondRegion = secondRegion;
        this.armyCount = armyCount;
    }

    public int getPhase() {
        return phase;
    }

    public boolean isEndPhase() {
        return endPhase;
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
}
