package screens;

import javafx.scene.Group;
import javafx.scene.Scene;

public class OptionsMenu extends Menu {

    public OptionsMenu()
    {

    }

    @Override
    public Scene getScene() {
        Group root = new Group();
        Scene scene = new Scene(root);

        return scene;
    }
}
