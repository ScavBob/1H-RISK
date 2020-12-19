package application;

import game.Game;
import managers.MapManager;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

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

    public void initBattle(Region atkRegion, Region defRegion, int attackingArmyCount) {
        Player currentPlayer = Game.getInstance().getGameManager().getMatch().getCurrentPlayer();
        int defendingArmyCount = Math.min(2, defRegion.getUnitCount());

        boolean battleConfirmed = Game.getInstance().confirmBattle(attackingArmyCount, defendingArmyCount);

        if (!battleConfirmed)
        {
            System.out.println("Battle is denied.");
            return;
        }
        else
        {
            System.out.println("Battle is confirmed.");
            List<Integer> attackerDice = new ArrayList<>();
            List<Integer> defenderDice = new ArrayList<>();
            List<Boolean> results = new ArrayList<>();
            for (int i = 0; i < attackingArmyCount; i++) attackerDice.add(rollDice());
            for (int i = 0; i < defendingArmyCount; i++) defenderDice.add(rollDice());

            Collections.sort(attackerDice, Collections.reverseOrder());
            Collections.sort(defenderDice, Collections.reverseOrder());

            int attackerLoss = 0;
            int defenderLoss = 0;

            if (attackerDice.get(0) > defenderDice.get(0)) {
                defenderLoss++;
                results.add(true);
            }
            else {
                attackerLoss++;
                results.add(false);
            }

            if (defendingArmyCount == 2)
            {
                if (attackerDice.get(1) > defenderDice.get(1)) {
                    defenderLoss++;
                    results.add(true);
                }
                else {
                    attackerLoss++;
                    results.add(false);
                }
            }

            MapManager mapManager = Game.getInstance().getGameManager().getMapManager();
            mapManager.increaseArmyCount(atkRegion, -attackerLoss);
            mapManager.increaseArmyCount(defRegion, -defenderLoss);

            //Attacker has captured the region.
            if (defRegion.getUnitCount() == 0)
            {
                defRegion.setOwner(currentPlayer);
            }
            //Game.getInstance().showBattleResult(attackerDice, defenderDice, results);

        }
    }
}



