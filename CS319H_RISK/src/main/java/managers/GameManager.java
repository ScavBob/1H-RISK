package managers;

import application.Map;
import application.Match;
import application.Player;
import game.Game;

import java.util.ArrayList;

public class GameManager
{
    private InputManager inputManager;
    private StorageManager storageManager;
    private SettingsManager settingsManager;
    private MapManager mapManager;
    private Match match;

    public GameManager()
    {
        this.storageManager = new StorageManager();
        this.inputManager = new InputManager();
        this.settingsManager = new SettingsManager();
        this.mapManager = new MapManager();
        this.match = new Match();
    }

    public void startMatch(int map, ArrayList<Player> players)
    {
        for (Player playerToAdd : players)
            match.addPlayer(playerToAdd);
        Map matchMap = Game.getInstance().getGameManager().mapManager.getMap(MapManager.WORLD_MAP);
        match.setMap(matchMap);
    }

    public Match getMatch() {
        return match;
    }

    public InputManager getInputManager() {
        return inputManager;
    }
    public void setInputManager(InputManager inputManager) {
        this.inputManager = inputManager;
    }

    public StorageManager getStorageManager() {
        return storageManager;
    }
    public void setStorageManager(StorageManager storageManager) {
        this.storageManager = storageManager;
    }

    public SettingsManager getSettingsManager() {
        return settingsManager;
    }
    public void setSettingsManager(SettingsManager settingsManager) {
        this.settingsManager = settingsManager;
    }

    public MapManager getMapManager() {
        return mapManager;
    }
    public void setMapManager(MapManager mapManager) {
        this.mapManager = mapManager;
    }
}