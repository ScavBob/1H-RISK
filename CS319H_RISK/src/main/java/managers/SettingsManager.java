package main.java.managers;

import main.java.game.Game;

import java.util.Properties;

public class SettingsManager
{
    private Properties properties;
    private static final String VOLUME_NAME = "volume";

    private static final String DEFAULT_VOLUME = "5";

    public SettingsManager()
    {
        readSettings();
    }

    public int getVolume(){
        return Integer.parseInt( this.properties.getProperty( VOLUME_NAME ) );
    }
    public boolean setVolume(int volume){
        this.properties.setProperty(VOLUME_NAME, volume + "");
        return saveSettings();
    }
    private boolean saveSettings()
    {
        return Game.getInstance().getGameManager().getStorageManager().saveSettings(properties);
    }
    private boolean readSettings()
    {
        properties = new Properties();
        boolean readSuccessfully = Game.getInstance().getGameManager().
                                    getStorageManager().readSettings(properties);
        if (readSuccessfully)
            return true;
        else
        {
            properties.setProperty(VOLUME_NAME, DEFAULT_VOLUME);
            return false;
        }
    }

}