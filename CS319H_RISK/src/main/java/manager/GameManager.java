package manager;

import application.Map;
import application.Match;
import application.Player;
import gamelauncher.Game;
import screen.GameScreen;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;

public class GameManager
{
    private InputManager inputManager;
    private StorageManager storageManager;
    private SettingsManager settingsManager;
    private MapManager mapManager;
    private Match match;
    private SoundManager soundManager;

    public GameManager()
    {
        this.storageManager = new StorageManager();
        this.inputManager = new InputManager();
        this.settingsManager = new SettingsManager();
        this.mapManager = new MapManager();
        this.soundManager = new SoundManager();
        this.match = null;

        soundManager.startPlayMusic();

        //We need to do this with a delay so that all managers are constructed.
        readSettingsWithDelay();
    }

    private void readSettingsWithDelay()
    {
        Timer readSettingsTimer = new Timer(100, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                settingsManager.readSettings();
            }
        });
        readSettingsTimer.start();
        readSettingsTimer.setRepeats(false);
    }

    public void loadMatch(File saveFile)
    {
        match = storageManager.readGame(saveFile);
        Game.getInstance().setScreen(new GameScreen());
        match.startGameLoop();
    }

    public void startMatch(int map, ArrayList<Player> players, int maxTurnTime, boolean isWorldDomination, int AILevel)
    {
        match = new Match(maxTurnTime);

        for (Player playerToAdd : players)
            match.addPlayer(playerToAdd);
        Map matchMap = Game.getInstance().getGameManager().mapManager.getMap(map);
        match.setMap(matchMap);
        
        if (isWorldDomination)
            match.assignMissionWorldDomination();
        else
            match.assignMissionSecretMission();

        match.initialize(AILevel);
        Game.getInstance().setScreen(new GameScreen());
    }

    public Match getMatch() {
        return match;
    }
    public SoundManager getSoundManager() {
        return soundManager;
    }

    public InputManager getInputManager() {
        return inputManager;
    }

    public StorageManager getStorageManager() {
        return storageManager;
    }

    public SettingsManager getSettingsManager() {
        return settingsManager;
    }

    public MapManager getMapManager() {
        return mapManager;
    }
}