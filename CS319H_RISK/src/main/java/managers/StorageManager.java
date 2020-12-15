package main.java.managers;

import main.java.game.Game;

import javax.swing.filechooser.FileSystemView;
import java.io.*;
import java.nio.file.Files;
import java.util.Properties;

public class StorageManager
{
    private static final String STORAGE_FOLDER_NAME = "Risk";
    private static final String PROPERTIES_FILE_NAME = "Properties.txt";

    public StorageManager()
    {
    }

    public boolean saveGame()
    {
        //TODO
        return true;
    }

    /*
    public Match readGame()
    {
        //TODO
        return null;
    }
    */

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
        FileWriter writer = null;
        File propertiesFile = new File(getStorageFolder() + File.separator + PROPERTIES_FILE_NAME);

        if (!propertiesFile.exists())
        {
            File directory = new File(getStorageFolder());
            if (!directory.exists())
            {
                if (!directory.mkdir())
                    return false;
            }

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

    public static void main(String[] args)
    {
        StorageManager s = new StorageManager();
        Properties p = new Properties();

        p.setProperty("isim", "Ruzgar");
        System.out.println(s.saveSettings(p));

        System.out.println(s.readSettings(p));
        System.out.println(p.getProperty("isim"));
    }
}