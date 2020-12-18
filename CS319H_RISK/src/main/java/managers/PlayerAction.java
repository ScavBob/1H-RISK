package managers;

import application.Region;

public class PlayerAction {

    private boolean endPhase;
    private int phase;
    private Region firstRegion;
    private Region secondRegion;
    private int armyCount;

    @Override
    public String toString() {
        return "PlayerAction{" +
                "endPhase=" + endPhase +
                ", phase=" + phase +
                ", firstRegion=" + firstRegion +
                ", secondRegion=" + secondRegion +
                ", armyCount=" + armyCount +
                '}';
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
