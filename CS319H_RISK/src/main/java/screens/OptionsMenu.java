package screens;

import managers.StorageManager;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;

public class OptionsMenu extends Menu {

    public OptionsMenu()
    {

    }

    @Override
    public Scene getScene() {
        Group root = new Group();
        Scene scene = new Scene(root);
        setBackground(root, StorageManager.RESOURCES_FOLDER_NAME + "\\Menu\\Background.png");
        addButtons(root);
        return scene;
    }

    public void addButtons(Group root){
        addTransitionButton(root, "", 495, 511, 294, 51, StorageManager.RESOURCES_FOLDER_NAME + "\\Menu\\Back.png", new MainMenu());
    }
}
