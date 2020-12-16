package managers;

public class GameManager
{
    private InputManager inputManager;
    private StorageManager storageManager;
    private SettingsManager settingsManager;
    private MapManager mapManager;

    public GameManager()
    {
        this.storageManager = new StorageManager();
        this.inputManager = new InputManager();
        this.settingsManager = new SettingsManager();
        this.mapManager = new MapManager();
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