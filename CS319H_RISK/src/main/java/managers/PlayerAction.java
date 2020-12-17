package managers;

import application.Region;

public class PlayerAction {

    private boolean endPhase;
    private Region firstRegion;
    private Region secondRegion;
    private int armyCount;

    @Override
    public String toString() {
        return "PlayerAction{" +
                "endPhase=" + endPhase +
                ", firstRegion=" + firstRegion +
                ", secondRegion=" + secondRegion +
                ", armyCount=" + armyCount +
                '}';
    }

    public PlayerAction(boolean endPhase, Region firstRegion, Region secondRegion, int armyCount) {
        this.endPhase = endPhase;
        this.firstRegion = firstRegion;
        this.secondRegion = secondRegion;
        this.armyCount = armyCount;
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
