package screens;

import managers.StorageManager;
import javafx.scene.Group;
import javafx.scene.Scene;

public class PregameMenu extends Menu{
    @Override
    public Scene getScene(){
        Group root = new Group();
        Scene scene = new Scene(root);
        setBackground(root, StorageManager.RESOURCES_FOLDER_NAME + "\\Menu\\Background.png");
        drawImage(StorageManager.RESOURCES_FOLDER_NAME + "\\PregameMenu\\ChooseMap.png", 284, 16);
        return scene;
    }

    private void addButtons(Group root) {
        addTransitionButton(root, "", 495, 561, 294, 51, StorageManager.RESOURCES_FOLDER_NAME + "\\Menu\\Back.png", new MainMenu());
    }
}
