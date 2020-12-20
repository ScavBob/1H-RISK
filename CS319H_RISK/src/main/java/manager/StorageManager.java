package manager;

import application.Match;
import gamelauncher.Game;

import javax.swing.filechooser.FileSystemView;
import java.io.*;
import java.util.Properties;

public class StorageManager
{
    public static final String RESOURCES_FOLDER_NAME = "resources\\";
    private static final String STORAGE_FOLDER_NAME = "Risk";
    private static final String SAVE_FOLDER_NAME = "Saves";
    private static final String PROPERTIES_FILE_NAME = "Properties.txt";

    public StorageManager()
    {
    }

    public boolean saveGame(Match matchToSave, String saveName)
    {
        createDirectories();

        File saveFile = new File( getGameSaveFolder() + File.separator + saveName );
        ObjectOutputStream out = null;
        try {
            out = new ObjectOutputStream(new FileOutputStream(saveFile));
            out.writeObject(matchToSave);
            out.close();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public Match readGame(File saveFile)
    {
        if (!saveFile.exists())
        {
            return null;
        }
        ObjectInputStream in = null;
        try {
            in = new ObjectInputStream(new FileInputStream(saveFile));
            Match match = (Match) in.readObject();
            in.close();
            return match;
        } catch (IOException | ClassNotFoundException e) {
            return null;
        }
    }

    public boolean readSettings(Properties properties) {
        FileReader reader = null;
        File propertiesFile = new File(getStorageFolder() + File.separator + PROPERTIES_FILE_NAME);

        try {
            reader = new FileReader(propertiesFile);
        } catch (FileNotFoundException e) {
            return false;
        }

        try {
            properties.load(reader);
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    public boolean saveSettings(Properties properties)
    {
        createDirectories();

        FileWriter writer = null;
        File propertiesFile = new File(getStorageFolder() + File.separator + PROPERTIES_FILE_NAME);

        if (!propertiesFile.exists())
        {
            try {
                if (!propertiesFile.createNewFile())
                    return false;
            } catch (IOException e) {
                return false;
            }
        }

        try {
            writer = new FileWriter(propertiesFile);
            properties.store(writer, "");
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    private String getStorageFolder()
    {
        return getDocumentsDirectory() + File.separator + STORAGE_FOLDER_NAME;
    }

    private String getDocumentsDirectory()
    {
        return FileSystemView.getFileSystemView().getDefaultDirectory().getPath();
    }

    public String defaultGameSaveDirectory()
    {
        return getStorageFolder() + File.separator + SAVE_FOLDER_NAME;
    }

    private File getGameSaveFolder()
    {
        return Game.getInstance().getGameManager().getSettingsManager().getSaveLocation();
    }

    private boolean createDirectories()
    {
        File mainDirectory = new File(getStorageFolder());
        if (!mainDirectory.exists())
        {
            if (!mainDirectory.mkdir())
                return false;
        }

        File saveDirectory = getGameSaveFolder();
        if (!saveDirectory.exists())
        {
            if (!saveDirectory.mkdir())
                return false;
        }

        return true;
    }

    //Test Main
    /*
    public static void main(String[] args)
    {
        StorageManager s = new StorageManager();
        Properties p = new Properties();

        p.setProperty("isim", "Ruzgar");
        System.out.println(s.saveSettings(p));

        System.out.println(s.readSettings(p));
        System.out.println(p.getProperty("isim"));
    }
     */
}