package application;

import game.Game;
import managers.PlayerAction;

import java.util.List;

public class AIMoveGenerator {

    public static void awaitAIAction(GameController controller, int phase, int level)
    {
        if (phase == GameController.REINFORCEMENT_PHASE)
            getReinforcementAction(controller, level);
        //TODO
    }

    public static void getReinforcementAction(GameController controller, int level)
    {
        Player currentPlayer = Game.getInstance().getGameManager().getMatch().getCurrentPlayer();

        PlayerAction playerAction;

        if (currentPlayer.getAvailableReinforcements() == 0) {
            playerAction = new PlayerAction(true, GameController.REINFORCEMENT_PHASE, null, null, 0);
            controller.takePlayerAction(playerAction);
        }

        int randomCount = (int)(Math.random() * currentPlayer.getAvailableReinforcements()) + 1;
        List<Region> regions = currentPlayer.getRegions();
        int randomRegion = (int)(Math.random() * regions.size());
        Region chosenRegion = regions.get(randomRegion);

        playerAction = new PlayerAction(false, GameController.REINFORCEMENT_PHASE, chosenRegion, null, randomCount);
        controller.takePlayerAction(playerAction);
    }

}
