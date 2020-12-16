package managers;


import game.Game;

import java.io.File;
import java.util.Properties;

public class SettingsManager
{
    private Properties properties;
    private static final String VOLUME_NAME = "volume";
    private static final String SAVE_LOCATION_NAME = "save-location";

    private static final String DEFAULT_VOLUME = "5";

    public SettingsManager()
    {
    }

    public int getVolume(){
        readSettings();
        return Integer.parseInt( this.properties.getProperty( VOLUME_NAME ) );
    }
    public boolean setVolume(int volume){
        this.properties.setProperty(VOLUME_NAME, volume + "");
        return saveSettings();
    }

    public File getSaveLocation(){
        readSettings();
        return new File( this.properties.getProperty( SAVE_LOCATION_NAME ) );
    }
    public boolean setSaveLocation(File saveLocation){
        this.properties.setProperty(SAVE_LOCATION_NAME, saveLocation.toString());
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
            properties.setProperty(SAVE_LOCATION_NAME, defaultSaveLocation());
            return false;
        }
    }

    private String defaultSaveLocation()
    {
        return Game.getInstance().getGameManager().getStorageManager().defaultGameSaveDirectory();
    }
}