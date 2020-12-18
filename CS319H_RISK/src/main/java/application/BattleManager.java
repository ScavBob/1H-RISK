package application;

import game.Game;

import java.util.ArrayList;

import javax.lang.model.util.ElementScanner6;

public class BattleManager {
    private ArrayList<Player> players;

    // Return 1 means attacker wins the battle,
    // Return 0 means they are even,
    // Return -1 means defender wins the battle.

    public int battle(Player attacker, int attackerSoldiers, Player defender, int defenderSoldiers, Card location) {
        int remainingSoldier;
        if (attackerSoldiers > defenderSoldiers) {
            remainingSoldier = attackerSoldiers - defenderSoldiers;
            return 1;
        } else if (attackerSoldiers == defenderSoldiers) {
            remainingSoldier = 0;
            return 0;
        } else {
            remainingSoldier = defenderSoldiers - attackerSoldiers;
            return -1;
        }
    }

    public void performBattle(Region srcRegion, Region dstRegion, int armyCount) {
        Player currentPlayer = Game.getInstance().getGameManager().getMatch().getCurrentPlayer();
        srcRegion.setUnitCount(srcRegion.getUnitCount() - armyCount);
        if (armyCount > dstRegion.getUnitCount())
        {
            dstRegion.setOwner(currentPlayer);
            dstRegion.setUnitCount(armyCount - dstRegion.getUnitCount());
        }
        else
        {
            dstRegion.setUnitCount(dstRegion.getUnitCount() - armyCount);
        }
    }
}



