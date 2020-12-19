package screens;

import javafx.scene.paint.Color;
import managers.MapManager;
import managers.StorageManager;
import javafx.scene.Group;
import javafx.scene.Scene;

public class PregameMenu extends Menu{
    @Override
    public Scene getScene(){
        Group root = new Group();
        Scene scene = new Scene(root, 1280, 720, Color.BLACK);
        setBackground(root, StorageManager.RESOURCES_FOLDER_NAME + "Menu\\Background.png");
        drawImage(StorageManager.RESOURCES_FOLDER_NAME + "PregameMenu\\ChooseMap.png", 284, 16);
        drawImage(StorageManager.RESOURCES_FOLDER_NAME + "PregameMenu\\Bilkent.png", 104, 138);
        drawImage(StorageManager.RESOURCES_FOLDER_NAME + "PregameMenu\\World.png", 711, 138);
        addButtons(root);
        return scene;
    }

    public void addButtons(Group root) {
        addTransitionButton(root, "", 495, 561, 294, 51, StorageManager.RESOURCES_FOLDER_NAME + "Menu\\Back.png", new MainMenu());
        addTransitionButton(root, "", 68,  468, 536, 37, StorageManager.RESOURCES_FOLDER_NAME + "PregameMenu\\BilkentButton.png", new GameStartMenu(MapManager.BILKENT_MAP));
        addTransitionButton(root, "", 674,  468, 536, 37, StorageManager.RESOURCES_FOLDER_NAME + "PregameMenu\\WorldButton.png", new GameStartMenu(MapManager.WORLD_MAP));
    }
}
