package application;

import game.Game;

import java.io.Serializable;
import java.util.ArrayList;

import javax.lang.model.util.ElementScanner6;

public class BattleManager implements Serializable {
    private ArrayList<Player> players;

    /**
     *    Return 1 means attacker wins the battle,
     *    Return 0 means they are even,
     *    Return -1 means defender wins the battle.
     *
     * @return the coded result of the battle.
     */
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

    private int rollDice()
    {
        return (int)(Math.random() * 6) + 1;
    }

    public void initBattle(Region srcRegion, Region dstRegion, int armyCount) {
        Player currentPlayer = Game.getInstance().getGameManager().getMatch().getCurrentPlayer();
        srcRegion.setUnitCount(srcRegion.getUnitCount() - armyCount);

        int defendingArmyCount = Math.min(2, dstRegion.getUnitCount());

        //boolean battleConfirmed = Game.getInstance().confirmBattle();

        /*
        if (!battleConfirmed)
        {
            return;
        }
        else
        {


        }

         */

        //Old simple logic for test
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



